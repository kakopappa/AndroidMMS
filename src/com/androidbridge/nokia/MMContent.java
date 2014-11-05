/*
 * @(#)MMContent.java	1.1
 *
 * Copyright (c) Nokia Corporation 2002
 *
 */


package com.androidbridge.nokia;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/**
 * The MMContent class represent a generic entry of a Multimedia Message.
 *
 */

public class MMContent implements Serializable {
  private String m_sType="";
  private String m_sContentId="";
  private ByteArrayOutputStream m_byteArray;
  private int m_iLength=0;

 /**
   * Sets the type of the entry.
   *
   * param value it's a valid content types. See WAP-203-WSP (WAP Forum)
   *             Examples are: text/plain, image/jpeg, image/gif, etc.
   *             You can use also some constants like:
   *             CT_TEXT_HTML, CT_TEXT_PLAIN, CT_TEXT_WML, CT_IMAGE_GIF,
   *             CT_IMAGE_JPEG, CT_IMAGE_WBMP, CT_APPLICATION_SMIL, etc.
   *             (these constants are defined in the interface IMMConstants)
   */
  public void setType(String value) {
    m_sType=value;
  }

 /**
   * Retrieves the type of the entry. Examples are: text/plain, image/jpeg, image/gif, etc.
   *
   * return the type of the entry.
   */
  public String getType() {
    return m_sType;
  }

 /**
   * Sets the ID of the entry.
   *
   */
  public void setContentId(String value) {
    m_sContentId=value;
  }

 /**
   * Retrieves the ID of the entry.
   *
   * return the ID
   */
  public String getContentId() {
    return m_sContentId;
  }

  /**
   * Retrieves the length in bytes of the entry.
   *
   * return the length
   */
  public int getLength() {
    return m_iLength;
  }

  /**
   * Writes <code>len</code> bytes from the specified byte array starting at offset off.
   *
   * param buf the data
   * param off the start offset in the data.
   * param len the number of bytes to write.
   */
  public void setContent(byte buf[],int off, int len)
  {
    m_iLength=len;
    m_byteArray=new ByteArrayOutputStream(len);
    m_byteArray.write(buf,off,len);
  }

  /**
   * Retrieves the array of byte representing the entry.
   *
   * return the array of byte
   */
  public byte [] getContent() {
    return m_byteArray.toByteArray();
  }

  /**
   * Retrieves the String representing the entry.
   *
   * return buffer as a String object
   */
  public String getContentAsString() {
    return m_byteArray.toString();
  }

  /**
   * Stores the entry into a file.
   *
   * param filename the name of the file.
   */
  public void saveToFile(String filename) throws IOException {
    FileOutputStream f=null;

    try {
      f=new FileOutputStream(filename);
    } catch(FileNotFoundException ioErr) {
      System.err.println(ioErr);
      System.exit(200);
    }

      m_byteArray.writeTo(f);
  }

  /**
   * Creates the object representing the content.
   */
  public MMContent() {
  }

}