/*
 * @(#)MMDecoder.java	1.1 build
 *
 * Copyright (c) Nokia Corporation 2002
 *
 */

package com.androidbridge.nokia;

import java.util.Date;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * The MMDecoder class decodes an array of bytes representing a Multimedia Message (MM)
 * according with the specification.
 * The class can be used to obtain a MMMessage object from which you can access
 * to each field and content of the MM.
 *
 */

public class MMDecoder implements IMMConstants {

  static final boolean FLAG_DEBUG=false;
  private MMMessage m_Message = null;
  private int m_i=0;
  private boolean m_bMultipartRelated = false;
  private boolean m_bMessageAvailable = false;
  private boolean m_bHeaderDecoded = false;
  private byte[] m_In;


  // ------------------------------------------------------------- BASIC RULES
  private long readMultipleByteInt(int length) {
    long value=0L;
    int start=m_i;
    int end=m_i+length-1;

    for (int ii=end, weight=1; ii>=start; ii--, weight*=256) {
      int bv=unsignedByte(m_In[ii]);
      value =value + bv*weight;
    }

    m_i=end+1;

    return value;
  }

  private String readTextString() {
    String value="";

    if (m_In[m_i]==0x22) {
      // in this case it's a "Quoted-string"
       m_i++;
    }

    while (m_In[m_i]>0) {
      value=value+(char)m_In[m_i++];
    }

    m_i++;

    return value;
  }

  private int readUintvar() {
    int value=0;
    int bv=unsignedByte(m_In[m_i]);

    if (bv<0x80) {
      value=bv;
      m_i++;
    } else { // In this case the format is "Variable Length Unsigned Integer"
      boolean flag=true;
      short count=0, inc=0;

      // Count the number of byte needed for the number
      while (flag) {
	flag=(m_In[m_i+count]&0x80)==0x80;
	count++;
      }

      inc=count;
      count--;

      int weight=1;
      while (count>=0) {
	bv=decodeByte(m_In[m_i+count])*weight;
        weight*=128;
        value=value+bv;
	count--;
      }

       m_i+=inc;
    }

    return value;
  }

  private int readValueLength() {
    int length=0;
    int temp = m_In[m_i++];

    if (temp < 31) {
      length=temp;
    } else
    if (temp == 31) {
      length = readUintvar();
    }

    return length;
  }


  private String readWellKnownMedia() {
    String value="";
    switch (decodeByte(m_In[m_i])) {

      case 0x00: value= "*/*"; break;
      case 0x01: value= "text/*"; break;
      case 0x02: value= "text/html"; break;
      case 0x03: value= "text/plain"; break;
      case 0x04: value= "text/x-hdml"; break;
      case 0x05: value= "text/x-ttml"; break;
      case 0x06: value= "text/x-vCalendar"; break;
      case 0x07: value= "text/x-vCard"; break;
      case 0x08: value= "text/vnd.wap.wml"; break;
      case 0x09: value= "text/vnd.wap.wmlscript"; break;
      case 0x0A: value= "text/vnd.wap.channel"; break;
      case 0x0B: value= "multipart/*"; break;
      case 0x0C: value= "multipart/mixed"; break;
      case 0x0D: value= "multipart/form-data"; break;
      case 0x0E: value= "multipart/byteranges"; break;
      case 0x0F: value= "multipart/alternative"; break;
      case 0x10: value= "application/*"; break;
      case 0x11: value= "application/java-vm"; break;
      case 0x12: value= "application/x-www-form-urlencoded"; break;
      case 0x13: value= "application/x-hdmlc"; break;
      case 0x14: value= "application/vnd.wap.wmlc"; break;
      case 0x15: value= "application/vnd.wap.wmlscriptc"; break;
      case 0x16: value= "application/vnd.wap.channelc"; break;
      case 0x17: value= "application/vnd.wap.uaprof"; break;
      case 0x18: value= "application/vnd.wap.wtls-ca-certificate"; break;
      case 0x19: value= "application/vnd.wap.wtls-user-certificate"; break;
      case 0x1A: value= "application/x-x509-ca-cert"; break;
      case 0x1B: value= "application/x-x509-user-cert"; break;
      case 0x1C: value= "image/*"; break;
      case 0x1D: value= "image/gif"; break;
      case 0x1E: value= "image/jpeg"; break;
      case 0x1F: value= "image/tiff"; break;
      case 0x20: value= "image/png"; break;
      case 0x21: value= "image/vnd.wap.wbmp"; break;
      case 0x22: value= "application/vnd.wap.multipart.*"; break;
      case 0x23: value= "application/vnd.wap.multipart.mixed"; break;
      case 0x24: value= "application/vnd.wap.multipart.form-data"; break;
      case 0x25: value= "application/vnd.wap.multipart.byteranges"; break;
      case 0x26: value= "application/vnd.wap.multipart.alternative"; break;
      case 0x27: value= "application/xml"; break;
      case 0x28: value= "text/xml"; break;
      case 0x29: value= "application/vnd.wap.wbxml"; break;
      case 0x2A: value= "application/x-x968-cross-cert"; break;
      case 0x2B: value= "application/x-x968-ca-cert"; break;
      case 0x2C: value= "application/x-x968-user-cert"; break;
      case 0x2D: value= "text/vnd.wap.si"; break;
      case 0x2E: value= "application/vnd.wap.sic"; break;
      case 0x2F: value= "text/vnd.wap.sl"; break;
      case 0x30: value= "application/vnd.wap.slc"; break;
      case 0x31: value= "text/vnd.wap.co"; break;
      case 0x32: value= "application/vnd.wap.coc"; break;
      case 0x33: value= "application/vnd.wap.multipart.related";
               m_bMultipartRelated = true;
               break;
      case 0x34: value= "application/vnd.wap.sia"; break;
      case 0x35: value= "text/vnd.wap.connectivity-xml"; break;
      case 0x36: value= "application/vnd.wap.connectivity-wbxml"; break;

    }

    m_i++;

    return value;
  }


  // ------------------------------------------------------- MMS Header Encoding

  private String readContentTypeValue() {
    int bv=unsignedByte(m_In[m_i]);
    String value="";

    if (bv>=0x80) { /* Constrained-media - Short Integer*/
      // Short-integer: the assigned number of the well-known encoding is
      // small enough to fit into Short-integer
      value=readWellKnownMedia();
    }
    else /* Constrained-media - Extension-media*/
    if (bv >= 0x20 && bv < 0x80) {
      value = readTextString();
    }
    else /* Content-general-form */
    if (bv < 0x20) {
      int valueLength = readValueLength();
      bv=unsignedByte(m_In[m_i]);
      if (bv>=0x80) { //Well-known-media
	int i2=m_i;
        value=readWellKnownMedia();
        if (value.equals("application/vnd.wap.multipart.related")) {
          bv=decodeByte(m_In[m_i]);
          if (bv==WKPA_TYPE) { // Type of the multipart/related
            m_i++;
            m_Message.setMultipartRelatedType( readTextString() );
            bv=decodeByte(m_In[m_i]);
            if (bv==WKPA_START) { // Start (it is the pointer to the presentetion part)
              m_i++;
	      m_Message.setPresentationId( readTextString() );
            }
          }
        }

	m_i=i2+valueLength;
      } else {
	int i2=m_i;
        value=readTextString();
	m_i=i2+valueLength;
      }
    }
    return(value);
  }

  // ------------------------------------------------------------------ MMS Body
  private void readMMBodyMultiPartRelated() {
    int n=0;
    int c_headerLen=0,c_dataLen=0;
    String c_type="",c_id="";
    byte [] c_buf;
    int nEntries = m_In[m_i++];

    while (n<nEntries) {
      c_headerLen=readUintvar();
      c_dataLen=readUintvar();
      int freeze_i=m_i;
      c_type=readContentTypeValue();
      int c_typeLen=m_i-freeze_i;

      c_id="A"+n;
      if (c_headerLen-c_typeLen>0) {
        if ((decodeByte(m_In[m_i])==HFN_CONTENT_LOCATION) ||
           (decodeByte(m_In[m_i])==HFN_CONTENT_ID) ) {
            m_i++;
	    c_id=readTextString();
        }
      }

      MMContent mmc=new MMContent();
      mmc.setType(c_type);
      mmc.setContentId(c_id);
      mmc.setContent(m_In,m_i,c_dataLen);
      m_Message.addContent(mmc);
      m_i+=c_dataLen;

      n++;
    }
  }

  private void readMMBodyMultiPartMixed() {
    int n=0;
    int c_headerLen=0,c_dataLen=0;
    String c_type="",c_id="";
    byte [] c_buf;
    int nEntries = m_In[m_i++];

    while (n<nEntries) {
      c_headerLen=readUintvar();
      c_dataLen=readUintvar();
      c_type=readContentTypeValue();
      c_id="A"+n;
      if (unsignedByte(m_In[m_i])==0x8E) {
	m_i++;
        c_id=readTextString();
      }

      if (FLAG_DEBUG) System.out.println("c_type=("+c_type+") c_headerLen=("+c_headerLen+") c_dataLen=("+c_dataLen+") c_id=("+c_id+")");

      MMContent mmc=new MMContent();
      mmc.setType(c_type);
      mmc.setContentId(c_id);
      mmc.setContent(m_In,m_i,c_dataLen);
      m_Message.addContent(mmc);
      m_i+=c_dataLen;


      n++;
    }

  }

  // ---------------------------------------------------------------- MMS Header
  private void readMMHeader() {
    boolean flag_continue=true;

    while (flag_continue && m_i<m_In.length) {
      byte currentByte=decodeByte(m_In[m_i++]);

      switch (currentByte) {
         case FN_MESSAGE_TYPE:
             if (FLAG_DEBUG) System.out.println("FN_MESSAGE_TYPE (0C)");
             m_Message.setMessageType(m_In[m_i]);
             m_i++;
             break;
         case FN_TRANSACTION_ID:
             if (FLAG_DEBUG) System.out.println("FN_TRANSACTION_ID (18)");
             m_Message.setTransactionId(readTextString());
             break;
         case FN_MESSAGE_ID:
             if (FLAG_DEBUG) System.out.println("FN_MESSAGE_ID (0B)");
             m_Message.setMessageId(readTextString());
             break;
         case FN_STATUS:
             if (FLAG_DEBUG) System.out.println("FN_STATUS (15)");
             m_Message.setMessageStatus(m_In[m_i]);
             m_i++;
             break;
         case FN_MMS_VERSION:
             if (FLAG_DEBUG) System.out.println("FN_MMS_VERSION (0D)");
	     m_Message.setVersion(m_In[m_i]);
             m_i++;
             break;
         case FN_TO:
             if (FLAG_DEBUG) System.out.println("FN_TO (17)");
	     m_Message.addToAddress(new String (readTextString()) );
             break;
         case FN_CC:
             if (FLAG_DEBUG) System.out.println("FN_CC (02)");
	     m_Message.addCcAddress(new String (readTextString()) );
             break;
         case FN_BCC:
             if (FLAG_DEBUG) System.out.println("FN_BCC (01)");
	     m_Message.addBccAddress(new String (readTextString()) );
             break;

         case FN_DATE:
             {
             if (FLAG_DEBUG) System.out.println("FN_DATE (05)");
	     int length=m_In[m_i++];
             long msecs=readMultipleByteInt(length)*1000;
             m_Message.setDate( new Date(msecs) );
             }
             break;

        case FN_DELIVERY_REPORT:
             {
    	     if (FLAG_DEBUG) System.out.println("FN_DELIVERY_REPORT (06)");
             int value = unsignedByte(m_In[m_i++]);
             if (value==0x80)
                m_Message.setDeliveryReport(true);
             else
                m_Message.setDeliveryReport(false);
             break;
             }
         case FN_SENDER_VISIBILITY:
             if (FLAG_DEBUG) System.out.println("FN_STATUS (14)");
             m_Message.setSenderVisibility(m_In[m_i]);
             m_i++;
             break;
        case FN_READ_REPLY:
             {
             if (FLAG_DEBUG) System.out.println("FN_READ_REPLY (10)");
             int value = unsignedByte(m_In[m_i++]);
             if (value==0x80)
                m_Message.setReadReply(true);
             else
                m_Message.setReadReply(false);
             break;
             }

        case FN_FROM:
	     {
             if (FLAG_DEBUG) System.out.println("FN_FROM (09)");
	     int valueLength = m_In[m_i++];
	     int addressPresentToken = unsignedByte(m_In[m_i++]);
	     if (addressPresentToken == 0x80) { // Address-present-token
                m_Message.setFrom(new String (readTextString()) );
	     }
	     }
             break;
        case FN_SUBJECT:
             if (FLAG_DEBUG) System.out.println("FN_SUBJECT (16)");
             m_Message.setSubject( readTextString() );
             break;
        case FN_MESSAGE_CLASS:
             if (FLAG_DEBUG) System.out.println("FN_MESSAGE_CLASS (0A)");
             m_Message.setMessageClass(m_In[m_i++]);
             break;
        case FN_EXPIRY:
             {
             if (FLAG_DEBUG) System.out.println("FN_EXPIRY (08)");
             int valueLength=readValueLength();
             int tokenType=unsignedByte(m_In[m_i++]);
	     long expiry=0;

             if (tokenType==128) { // Absolute-token
               int length=m_In[m_i++];
               expiry=readMultipleByteInt(length)*1000;
	       m_Message.setExpiryAbsolute(true);
             }

             if (tokenType==129) { // Relative-token
	       m_Message.setExpiryAbsolute(false);
               // Read the Delta-seconds-value
               if (valueLength>3) { // Long Integer
                 int length=m_In[m_i++];
                 expiry=readMultipleByteInt(length)*1000;
               }
               else { // Short Integhet (1 OCTECT)
                 int b=m_In[m_i]&0x7F;
                 expiry=b*1000;
                 m_i++;
               }
               }
	      m_Message.setExpiry( new Date(expiry) );
             }
             break;
        case FN_DELIVERY_TIME:
             {
             if (FLAG_DEBUG) System.out.println("FN_DELIVERY_TIME (07)");
             int valueLength=readValueLength();
             int tokenType=unsignedByte(m_In[m_i++]);
	     long deliveryTime=0;

             if (tokenType==128) { // Absolute-token
	       m_Message.setDeliveryTimeAbsolute(true);
               int length=m_In[m_i++];
               deliveryTime=readMultipleByteInt(length)*1000;
             }

             if (tokenType==129) { // Relative-token
	       m_Message.setDeliveryTimeAbsolute(false);
               // Read the Delta-seconds-value
               if (valueLength>3) { // Long Integer
                 int length=m_In[m_i++];
                 deliveryTime=readMultipleByteInt(length)*1000;
               }
               else { // Short Integhet (1 OCTECT)
                 int b=m_In[m_i]&0x7F;
                 deliveryTime=b*1000;
                 m_i++;
               }
               }
	      m_Message.setDeliveryTime( new Date(deliveryTime) );
             }
	     break;
        case FN_PRIORITY:
    	     if (FLAG_DEBUG) System.out.println("FN_PRIORITY (0F)");
             m_Message.setPriority( m_In[m_i++] );
             break;
        case FN_CONTENT_TYPE:
           if (FLAG_DEBUG) System.out.println("FN_CONTENT_TYPE (04)");
           m_Message.setContentType( readContentTypeValue() );
           flag_continue=false;
           break;

      }
    }
  }

  private byte decodeByte(byte value) {
    return ( (byte)(value & 0x7F) );
  }

  private int unsignedByte(byte value) {
    if (value<0) {
      return (value+256);
    } else
      return value;
  }

 /**
  * Resets the Decoder object.
  *
  */
  public void reset() {
    m_Message = null;
    m_bMultipartRelated = false;
    m_bMessageAvailable = false;
    m_bHeaderDecoded = false;
    m_In = null;
  }

 /**
  * Sets the buffer representing the Multimedia Message to be decoded.
  */
  public void setMessage(byte buf[]) {
    m_Message = new MMMessage();
    m_In = buf;
    m_bMessageAvailable = true;
  }

 /**
  * Decodes the header of the Multimedia Message. After calling this method
  * a MMMessage object, containing just the information related to the header and
  * without the contents, can be obtained by calling getMessage().
  * This method has to be called after setMessage(byte buf[).
  */
  public void decodeHeader() throws MMDecoderException {
    if (m_bMessageAvailable) {
      readMMHeader();
      m_bHeaderDecoded = true;
    } else {
      throw new MMDecoderException("Message unavailable. You must call setMessage(byte[] buf) before calling this method.");
    }
  }

 /**
  * Decodes the body of the Multimedia Message. This method has to be called
  * after the method decodeHeader()
  */
  public void decodeBody() throws MMDecoderException {
    if (!m_bHeaderDecoded)
      throw new MMDecoderException("You must call decodeHeader() before calling decodeBody()");

    if ((m_Message.getContentType()).compareTo("application/vnd.wap.multipart.related")==0)
       readMMBodyMultiPartRelated();
    else
       readMMBodyMultiPartMixed();
  }

 /**
  * Decodes the whole Multimedia Message. After calling this method
  * a MMMessage object, containing the information related to the header and
  * the all contents, can be obtained by calling the method getMessage().
  * This method has to be called after setMessage(byte buf[).
  */
  public void decodeMessage() throws MMDecoderException {
    decodeHeader();
    if (m_Message.getMessageType()==IMMConstants.MESSAGE_TYPE_M_SEND_REQ)
      decodeBody();
  }

  public MMDecoder(byte buf[]) {
    setMessage(buf);
  }

  public MMDecoder() {
  }

 /**
  * Retrieves the MMMessage object. This method has to be called after the calling
  * of methods decodeMessage() or decodeHeader().
  *
  * @return An object representing the decoded Multimedia Message
  */
  public MMMessage getMessage() {
    if (m_bHeaderDecoded)
      return m_Message;
   else
      return null;
  }
}