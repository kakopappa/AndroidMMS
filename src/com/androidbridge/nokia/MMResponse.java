/*
 * @(#)MMResponse.java	1.1 
 *
 * Copyright (c) Nokia Corporation 2002
 *
 */

package com.androidbridge.nokia;

import java.io.*;
import java.util.*;

/**
 * The MMResponse class provides methods to get the response returned by the MMSC.
 *
 */


public class MMResponse {

  private Hashtable hHeaders;
  private int responseCode;
  private String responseMessage;
  private String contentType;
  private int contentLength;
  private byte[] buffer;

  public MMResponse() {
    hHeaders = new Hashtable();
  }

  protected void setResponseCode(int responseCode){
    this.responseCode = responseCode;
  }

  protected void setResponseMessage(String responseMessage){
    this.responseMessage = responseMessage;
  }

  protected void addHeader(String HKey,String HValue){
    hHeaders.put(HKey.toUpperCase(),HValue);
  }

 /**
  * Gets the response code returned by the MMSC
  *
  */
  public int getResponseCode(){
    return responseCode;
  }

 /**
  * Gets the response message returned by the MMSC
  *
  */
  public String getResponseMessage(){
    return responseMessage;
  }

 /**
  * This method returns an header value contained in the response returned by the MMSC
  *
  * param key the name of the header field
  */
  public String getHeaderValue(String key){
    return (String)hHeaders.get((String)key);
  }

 /**
  * Returns an all the field (key) contained in the response header
  *
  */
  public Enumeration getHeadersList(){
    return hHeaders.keys();
  }

  protected void setContentType(String ct){
    contentType = ct;
  }

 /**
  * This method returns the content type of the response
  *
  */
  public String getContentType(){
    return contentType;
  }

  protected void setContentLength(int i){
    contentLength = i;
  }

 /**
  * This method returns the content length of the response
  *
  */
  public int getContentLength(){
    return contentLength;
  }

  protected void setContent(byte[] buf){
   buffer = buf;
  }

 /**
  * This method returns the content of the response
  *
  */
  public byte[] getContent(){
    return buffer;
  }

}
