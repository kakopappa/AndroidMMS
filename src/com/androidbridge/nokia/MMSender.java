/*
 * @(#)MMSender.java	1.1
 *
 * Copyright (c) Nokia Corporation 2002 *
 */

package com.androidbridge.nokia;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * The MMSender class provides methods to send Multimedia Messages to a predefined
 * Multimedia Service Center (MMSC).
 *
 */

public class MMSender {

  private String m_sUrl; // the URL of the MMSC
  private Hashtable hHeader;

  public MMSender() {
    hHeader = new Hashtable();
  }

  /**
   * Add a new Header and Value in the HTTP Header
   *
   * @param header header name
   * @param value  header value
   */
  public void addHeader(String header,String value) {

    String str = (String)hHeader.get(header);
    if (str!=null)
      str+=","+value;
    else
      str = value;
    hHeader.put(header,str);
  }

  /**
   * Clear the HTTP Header
   *
   */
  public void clearHeader() {
    hHeader.clear();
  }

  /**
   * Sets the Multimedia Messaging Service Center (MMSC) address
   *
   * @param value the URL 
   */
  public void setMMSCURL(String value) {
    m_sUrl=value;
  }

  /**
   * Gets the Multimedia Messaging Service Center address.
   *
   * @return the address
   */
  public String getMMSCURL() {
    return m_sUrl;
  }

  /**
   * Sends a Multimedia Message having a MMMessage object
   *
   * @param mmMsg the Multimedia Message object
   */
  public void send(MMMessage mmMsg, boolean isProxySet,
          String proxyHost, int proxyPort) throws MMSenderException{

    MMEncoder encoder=new MMEncoder();
    encoder.setMessage(mmMsg);
    try {
      encoder.encodeMessage();
    } catch (MMEncoderException e) {
      throw new MMSenderException("An error occurred encoding the Multimedia Message for sending.");
    }

    byte [] buf=encoder.getMessage();

    send(buf, isProxySet, proxyHost, proxyPort);
  }

  /**
   * Sends a Multimedia Message having an array of bytes representing the message.
   *
   * @param buf the array of bytes representing the Multimedia Message.
   */
  public MMResponse send(byte[] buf, boolean isProxySet,
          String proxyHost, int proxyPort) throws MMSenderException{


    MMResponse hResponse = send(buf,204, isProxySet, proxyHost, proxyPort);

    return hResponse;
  }

  /**
   * Sends a Multimedia Message having an array of bytes representing the message
   * and specifying the success code returned by the MMSC.
   *
   * @param buf the array of bytes representing the Multimedia Message.
   * @param successcode the success code returned by the MMSC. Generally it is 204.
   */
  public MMResponse send(byte[] buf, int successcode, boolean isProxySet,
          String proxyHost, int proxyPort) throws MMSenderException {

    URL url = null;
    HttpURLConnection connection = null;
    OutputStream out = null;
    int responseCode = 0;
    String responseMessage = "";
    MMResponse hResponse = null;

    try {
      url = new URL(m_sUrl);
    } catch (MalformedURLException e) {
      throw new MMSenderException(e.getMessage());
    }

    // Set-up proxy
    if(isProxySet)
    {
    	Properties systemProperties = System.getProperties();
    	systemProperties.setProperty("http.proxyHost",proxyHost);
    	systemProperties.setProperty("http.proxyPort",String.valueOf(proxyPort));
    }
    
    // Decode the header of the Multimedia Message
    MMDecoder decoder=new MMDecoder();
    decoder.setMessage(buf);
    try {
      decoder.decodeHeader();
    } catch (MMDecoderException e) {
      throw new MMSenderException(e.getMessage());
    }

    MMMessage mmMsg=decoder.getMessage();

    try {
      connection = (HttpURLConnection)url.openConnection();
    } catch (IOException e) {
      throw new MMSenderException(e.getMessage());
    }
    // use the URL connection for output
    connection.setDoOutput(true);

    // Sends the request

    Enumeration eHeader = hHeader.keys();
    while (eHeader.hasMoreElements()){
      String strHeaderKey = (String)eHeader.nextElement();
      String value = (String)hHeader.get(strHeaderKey);
      connection.setRequestProperty(strHeaderKey,value);


    }

    
    connection.setRequestProperty("Content-Type","application/vnd.wap.mms-message");
    connection.setRequestProperty("Content-Length",Integer.toString(buf.length) );

    try {
      try {
        out = connection.getOutputStream();
        out.write(buf);
        out.flush();
      } catch (ConnectException e) {
        System.out.println(e.getMessage());
      } finally {
        if (out != null)
           out.close();
      }

      // Wait for the response
      responseCode = connection.getResponseCode();
      responseMessage = connection.getResponseMessage();


      hResponse = new MMResponse();
      
      hResponse.setResponseCode(responseCode);
      hResponse.setResponseMessage(responseMessage);
      hResponse.setContentLength(connection.getContentLength());
      hResponse.setContentType(connection.getContentType());
      if (connection.getContentLength()>=0) {
      byte[] buffer = new byte[connection.getContentLength()];

      DataInputStream i = new DataInputStream(connection.getInputStream());
      int b=0;
      int index =0;
      while ((b=i.read())!=-1) {
        buffer[index] = (byte)b;
        index++;
      }

      hResponse.setContent(buffer);
      } else {
            hResponse.setContent(null);
      }

      //Extract all headers in the HTTP response
      int iField = 1;
      boolean iteration = true;
      while (iteration){
        String strHeaderFieldKey = connection.getHeaderFieldKey(iField);
        if (strHeaderFieldKey!=null) {
          String strHeaderField = connection.getHeaderField(iField);
          hResponse.addHeader(strHeaderFieldKey,strHeaderField);
          iField++;
        } else
            iteration = false;
      }


    } catch (IOException e) {
      e.printStackTrace();
      throw new MMSenderException(e.getMessage());
    }
    // close the connection
    connection.disconnect();
    return hResponse;
  }


}