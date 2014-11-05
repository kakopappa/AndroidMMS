/*
 * @(#)MMMessage.java	1.1 
 *
 * Copyright (c) Nokia Corporation 2002
 *
 */
package com.androidbridge.nokia;

import java.util.Date;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Serializable;

/**
 * The MMMEssage class represents a Multimedia Message.
 *
 */

public class MMMessage implements IMMConstants, Serializable {

  /* Internal Flags */
  private boolean flagMultipartRelated=false;

  /* Header Fields Flags */
  private boolean flagBccAvailable=false;
  private boolean flagCcAvailable=false;
  private boolean flagContentTypeAvailable=false;
  private boolean flagDateAvailable=false;
  private boolean flagDeliveryReportAvailable=false;
  private boolean flagDeliveryTimeAvailable=false;
  private boolean flagDeliveryTimeAbsolute=false;
  private boolean flagExpiryAvailable=false;
  private boolean flagExpiryAbsolute=false;
  private boolean flagFromAvailable=false;
  private boolean flagMessageClassAvailable=false;
  private boolean flagMessageIdAvailable=false;
  private boolean flagMessageTypeAvailable=false;
  private boolean flagMMSVersionAvailable=false;
  private boolean flagPriorityAvailable=false;
  private boolean flagReadReplyAvailable=false;
  private boolean flagSenderVisibilityAvailable=false;
  private boolean flagStatusAvailable=false;
  private boolean flagSubjectAvailable=false;
  private boolean flagToAvailable=false;
  private boolean flagTransactionIdAvailable=false;


  /* Header Fields */
  private byte hMessageType;
  private String hTransactionId="";
  private String hMessageId="";
  private byte hVersion=0;
  private Vector hTo = null;
  private Vector hCc = null;
  private Vector hBcc = null;
  private Date hReceivedDate;
  private MMAddress hFrom=null;
  private String hSubject="";
  private byte hMessageClass=0;
  private Date hExpiry;
  private boolean hDeliveryReport=false;
  private boolean hReadReply=false;
  private byte hSenderVisibility=0;
  private Date hDeliveryTime;
  private byte hPriority;
  private String hContentType="";
  private byte hStatus;


  /* Others variables */
  private String multipartRelatedType="";
  private String presentationId="";
  private Hashtable tableOfContents=null;


  /**
   * Check if the presentation part is available.
   *
   * return true if availale.
   */
  public boolean isPresentationAvailable() {
    return flagMultipartRelated;
  }

  /**
   * Check if the message type field is available.
   *
   * return true if availale.
   */
  public boolean isMessageTypeAvailable() {
    return flagMessageTypeAvailable;
  }

  /**
   * Check if the Delivery-Report field is available.
   *
   * return true if availale.
   */
  public boolean isDeliveryReportAvailable() {
    return flagDeliveryReportAvailable;
  }

  /**
   * Check if the Sender-Visibility field is available.
   *
   * return true if availale.
   */
  public boolean isSenderVisibilityAvailable() {
    return flagSenderVisibilityAvailable;
  }


  /**
   * Check if the Read-Reply field is available.
   *
   * return true if availale.
   */
  public boolean isReadReplyAvailable() {
    return flagReadReplyAvailable;
  }


  /**
   * Check if the Status field is available.
   *
   * return true if availale.
   */
  public boolean isStatusAvailable() {
    return flagStatusAvailable;
  }

  /**
   * Check if the transaction ID field is available.
   *
   * return true if availale.
   */
  public boolean isTransactionIdAvailable() {
    return flagTransactionIdAvailable;
  }

  /**
   * Check if the message ID field is available.
   *
   * return true if availale.
   */
  public boolean isMessageIdAvailable() {
    return flagMessageIdAvailable;
  }

  /**
   * Check if the version field is available.
   *
   * return true if availale.
   */
  public boolean isVersionAvailable() {
    return flagMMSVersionAvailable;
  }

  /**
   * Check if the date field is available.
   *
   * return true if availale.
   */
  public boolean isDateAvailable() {
    return flagDateAvailable;
  }

  /**
   * Check if sender address field is available.
   *
   * return true if availale.
   */
  public boolean isFromAvailable() {
    return flagFromAvailable;
  }

  /**
   * Check if the subject field is available.
   *
   * return true if availale.
   */
  public boolean isSubjectAvailable() {
    return flagSubjectAvailable;
  }

  /**
   * Check if the message class field is available.
   *
   * return true if availale.
   */
  public boolean isMessageClassAvailable() {
    return flagMessageClassAvailable;
  }

  /**
   * Check if the expiry date/time field is available.
   *
   * return true if availale.
   */
  public boolean isExpiryAvailable() {
    return flagExpiryAvailable;
  }

  /**
   * Check if the delivery date/time field is available.
   *
   * return true if availale.
   */
  public boolean isDeliveryTimeAvailable() {
    return flagDeliveryTimeAvailable;
  }

  /**
   * Check if the priority date/time field is available.
   *
   * return true if availale.
   */
  public boolean isPriorityAvailable() {
    return flagPriorityAvailable;
  }

  /**
   * Check if the content type field is available.
   *
   * return true if availale.
   */
  public boolean isContentTypeAvailable() {
    return flagContentTypeAvailable;
  }

  /**
   * Check if there is at least one receiver is specified.
   *
   * return true if at least one receiver is specified.
   */
  public boolean isToAvailable() {
    return flagToAvailable;
  }

  /**
   * Check if there is at least one BCC receiver is specified.
   *
   * return true if at least one BCC receiver is specified.
   */
  public boolean isBccAvailable() {
    return flagBccAvailable;
  }

  /**
   * Check if there is at least one CC receiver is specified.
   *
   * return true if at least one CC receiver is specified.
   */
  public boolean isCcAvailable() {
    return flagCcAvailable;
  }

  /**
   * Returns the type of the message (Mandatory).
   *
   * return the type of the message.
   * IMMConstants.MESSAGE_TYPE_M_SEND_REQ.
   * IMMConstants.MESSAGE_TYPE_M_DELIVERY_IND.
   */
  public byte getMessageType() {
    return hMessageType;
  }

  /**
   * Sets the type of the message (Mandatory).
   * Specifies the transaction type.
   *
   * param value the type of the message.
   * IMMConstants.MESSAGE_TYPE_M_SEND_REQ.
   * IMMConstants.MESSAGE_TYPE_M_DELIVERY_IND.
   */
  public void setMessageType(byte value) {
    hMessageType = value;
    flagMessageTypeAvailable = true;
  }

  /**
   * Retrieves the Message ID (Mandatory).
   * Identifier of the message. From Send request, connects delivery report to sent message in MS.
   *
   * return the message ID.
   */
  public String getMessageId() {
    return hMessageId;
  }

  /**
   * Sets the message ID (Mandatory).
   * Identifier of the message. From Send request, connects delivery report to sent message in MS.
   *
   * param value the message ID.
   */
  public void setMessageId(String value) {
    hMessageId = value;
    flagMessageIdAvailable = true;
  }


  /**
   * Retrieves the Message Status (Mandatory).
   * The status of the message.
   *
   * return the message Status.
   */
  public byte getMessageStatus() {
    return hStatus;
  }

  /**
   * Sets the message Status (Mandatory).
   * The status of the message.
   *
   * param value the message Status.
   */
  public void setMessageStatus(byte value) {
    hStatus = value;
    flagStatusAvailable = true;
  }


  /**
   * Retrieves the transaction ID (Mandatory).
   * It is a unique identifier for the message and it identifies the M-Send.req
   * and the corresponding reply only.
   *
   * return the transaction ID.
   */
  public String getTransactionId() {
    return hTransactionId;
  }

  /**
   * Sets the transaction ID (Mandatory).
   * It is a unique identifier for the message and it identifies the M-Send.req
   * and the corresponding reply only.
   *
   * param value the trensaction ID.
   */
  public void setTransactionId(String value) {
    hTransactionId = value;
    flagTransactionIdAvailable = true;
  }

  /**
   * Retrieves the MMS version number as a String(Mandatory).
   *
   * return the version as a String. The only supported value are "1.0" and "1.1".
   */
  public String getVersionAsString() {
   int ver_major= ((byte)(hVersion<<1))>>5 ;
   int ver_minor= ((byte)(hVersion<<4))>>4 ;
   String result=ver_major+"."+ver_minor;

   return result;
  }

  /**
   * Retrieves the MMS version number (Mandatory).
   *
   * return the version. The only supported value are 
   * IMMConstants.MMS_VERSION_10 and IMMConstants.MMS_VERSION_11
   */
  public byte getVersion() {
    return hVersion;
  }

  /**
   * Sets the MMS version number (Mandatory).
   *
   * param value the only supported value are 
   * IMMConstants.MMS_VERSION_10 and IMMConstants.MMS_VERSION_11
   */
  public void setVersion(byte value) {
    hVersion = value;
    flagMMSVersionAvailable = true;
  }


  private MMAddress decodeAddress(String value) {
    byte type = ADDRESS_TYPE_UNKNOWN;
    String address=new String(value);

    int sep = value.indexOf(47); // the character "/"
    if(sep != -1) {
       address = value.substring(0, sep);
       if(value.endsWith("PLMN"))
         type = ADDRESS_TYPE_PLMN;
       else
       if(value.endsWith("IPv4"))
         type = ADDRESS_TYPE_IPV4;
       else
       if(value.endsWith("IPv6"))
          type = ADDRESS_TYPE_IPV6;
    }
    else {
     int at = address.indexOf(64); // the character "@"
     if(at != -1)
	type = ADDRESS_TYPE_EMAIL;
    }
    MMAddress result = new MMAddress(address,type);
    return result;
  }

  /**
   * Adds a new receiver of the Multimedia Message. The message can have
   * more than one receiver but at least one.
   *
   * param value is the string representing the address of the receiver. It has
   * to be specified in the full format i.e.: +358990000005/TYPE=PLMN or
   * joe@user.org or 1.2.3.4/TYPE=IPv4. 
   *
   */
  public void addToAddress(String value) {
    MMAddress addr = decodeAddress(value);
    hTo.add(addr);
    flagToAvailable = true;
  }

  /**
   * Adds a new receiver in the CC (Carbon Copy) field of the Multimedia Message. The message can have
   * more than one receiver in the CC field.
   *
   * param value is the string representing the address of the CC receiver. It has
   * to be specified in the full format i.e.: +358990000005/TYPE=PLMN or
   * joe@user.org or 1.2.3.4/TYPE=IPv4. 
   *
   */
  public void addCcAddress(String value) {
    MMAddress addr = decodeAddress(value);
    hCc.add(addr);
    flagCcAvailable = true;
  }

  /**
   * Adds a new receiver in the BCC (Blind Carbon Copy) field of the Multimedia Message. The message can have
   * more than one receiver in the BCC field.
   *
   * param value is the string representing the address of the BCC receiver. It has
   * to be specified in the full format i.e.: +358990000005/TYPE=PLMN or
   * joe@user.org or 1.2.3.4/TYPE=IPv4.
   *
   */
  public void addBccAddress(String value) {
    MMAddress addr = decodeAddress(value);
    hBcc.add(addr);
    flagBccAvailable = true;
  }

  /**
   * Clears all the receivers of the Multimedia Message.
   *
   */
  public void clearTo() {
    hTo.clear();
    flagToAvailable = false;
  }

  /**
   * Clears all the carbon copy receivers of the Multimedia Message.
   *
   */
  public void clearCc() {
    hCc.clear();
    flagCcAvailable = false;
  }

  /**
   * Clears all the blind carbon copy receivers of the Multimedia Message.
   *
   */
  public void clearBcc() {
    hBcc.clear();
    flagBccAvailable = false;
  }

  /**
   * Retrieve all the receivers of the Multimedia Message.
   *
   * return a vector of MMAddress objects.
   *
   */
  public Vector getTo() {
    return hTo;
  }

  /**
   * Retrieve all the CC receivers of the Multimedia Message.
   *
   * return a vector of MMAddress objects.
   *
   */
  public Vector getCc() {
    return hCc;
  }

  /**
   * Retrieve all the BCC receivers of the Multimedia Message.
   *
   * return a vector of MMAddress objects.
   *
   */
  public Vector getBcc() {
    return hBcc;
  }

  /**
   * Retrieves the arrival time of the message at the MMS Proxy-Relay (Optional).
   * MMS Proxy-Relay will generate this field when not supplied by terminal.
   *
   * return the arrival date.
   *
   */
  public Date getDate() {
    return hReceivedDate;
  }

  /**
   * Sets the sending time of the message at the MMS Proxy-Relay (Optional).
   *
   * param value the date.
   *
   */
  public void setDate(Date value) {
    hReceivedDate = value;
    flagDateAvailable=true;
  }

  /**
   * Retrieves the address of the message sender (Mandatory).
   *
   * return the address.
   *
   */
  public MMAddress getFrom() {
    return hFrom;
  }

  /**
   * Sets the address of the message sender (Mandatory).
   *
   * param value is the string representing the address of the sender. It has
   * to be specified in the full format i.e.: +358990000005/TYPE=PLMN or
   * joe@user.org or 1.2.3.4/TYPE=IPv4.
   *
   */
  public void setFrom(String value) {
    hFrom = new MMAddress( decodeAddress(value) );
    flagFromAvailable=true;
  }

  /**
   * Retrieves the subject of the Multimedia Message (Optional).
   *
   * return the subject.
   *
   */
  public String getSubject() {
    return hSubject;
  }

  /**
   * Sets the subject of the Multimedia Message (Optional).
   *
   * param value the subject.
   *
   */
  public void setSubject(String value) {
    hSubject = new String(value);
    flagSubjectAvailable=true;
  }

  /**
   * Retrieves the message class of the Multimedia Message (Optional).
   *
   * return One of the following values:
   * MESSAGE_CLASS_PERSONAL, MESSAGE_CLASS_ADVERTISEMENT,
   * MESSAGE_CLASS_INFORMATIONAL, MESSAGE_CLASS_AUTO
   *
   */
  public byte getMessageClass() {
    return hMessageClass;
  }

  /**
   * Sets the message class of the Multimedia Message (Optional).
   *
   * param value is the message class. It can have one of the following values:
   * MESSAGE_CLASS_PERSONAL, MESSAGE_CLASS_ADVERTISEMENT,
   * MESSAGE_CLASS_INFORMATIONAL, MESSAGE_CLASS_AUTO
   *
   */
  public void setMessageClass(byte value) {
    hMessageClass = value;
    flagMessageClassAvailable = true;
  }

  /**
   * Retrieves the content type of the Multimedia Message (Mandatory).
   *
   * return the content type.
   */
  public String getContentType() {
    return hContentType;
  }

  /**
   * Sets the content type of the Multimedia Message (Mandatory).
   *
   * param value is the content type. The standard for interoperability supports
   * one of the following values:
   * CT_APPLICATION_MULTIPART_MIXED, CT_APPLICATION_MULTIPART_RELATED
   *
   */
  public void setContentType(String value) {
    hContentType = new String( value );
    flagContentTypeAvailable = true;
  }

  /**
   * Retrieves the number of contents of of the Multimedia Message (Mandatory).
   *
   * return the number of contents.
   */
  public int getNumContents() {
    return tableOfContents.size();
  }

  /**
   * Sets the content ID of the content containing the presentation part of the
   * Multimedia Message (Mandatory when the content type of the Multimedia Message is CT_APPLICATION_MULTIPART_RELATED).
   *
   * param value is the content ID.
   *
   */
  public void setPresentationId(String value) {
    presentationId = value;
  }

  /**
   * Sets the type of the presentation part.(Mandatory when the content type of the Multimedia Message is CT_APPLICATION_MULTIPART_RELATED).
   *
   * param value the type of the presentation part. The standard for interoperability supports
   * only the  value: CT_APPLICATION_SMIL
   *
   */
  public void setMultipartRelatedType(String value) {
    flagMultipartRelated = true;
    multipartRelatedType = value;
  }

  /**
   * Retrieves the type of the presentation part.
   *
   * return the type of the presentation part.
   */
  public String getMultipartRelatedType() {
    if (flagMultipartRelated) {
      return multipartRelatedType;
    } else
      return null;
  }

  /**
   * Adds a content to the message.
   *
   * param mmc is the content to add.
   *
   */
  public void addContent(MMContent mmc) {
    tableOfContents.put(mmc.getContentId(),mmc);
  }

  /**
   * Retrieves the content referring to the presentation part.
   *
   * return the presentation content.
   */
  public MMContent getPresentation() {
    if ((flagMultipartRelated==true) && (getNumContents()>0))
       return (MMContent) tableOfContents.get(presentationId);
    else
       return null;
  }

  /**
   * Retrieves the content ID referring to the presentation part.
   *
   * return the content ID.
   */
  public String getPresentationId() {
    if ((flagMultipartRelated==true) && (getNumContents()>0))
       return presentationId;
    else
       return null;
  }

  /**
   * Retrieves the content having the specified id.
   *
   * return the content.
   * param id is the id of the content to be retrieved.
   */
  public MMContent getContent(String id) {
    return (MMContent) tableOfContents.get(id);
  }

  /**
   * Retrieves the content at the position index.
   *
   * return the content.
   * param index is the index of the content to be retrieved.
   */
  public MMContent getContent(int index) {
    MMContent value=null;
    int j=0;
    for (Enumeration e = tableOfContents.elements() ; (e.hasMoreElements() && j<=index) ;j++)
    {
      value=(MMContent)e.nextElement();
    }
    return value;
  }

  /**
   * Retrieves the expiry date of the message (Optional).
   *
   * return the expiry date/time.
   */
  public Date getExpiry() {
    return hExpiry;
  }

  /**
   * Sets the length of time the message will be stored in the MMS Proxy-Relay
   * or time to delete the message (Optional). This field can have two format,
   * either absolute or interval depending on how it is set with the method
   * setExpiryAbsolute().
   *
   * param value is the expiry date/time.
   */
  public void setExpiry(Date value) {
    hExpiry = value;
    flagExpiryAvailable = true;
  }

  /**
   * Sets the format of the expiry date/time.
   *
   * param value if true the expiry date is absolute, else is
   * intended as an interval.
   */
  public void setExpiryAbsolute (boolean value) {
    flagExpiryAbsolute = value;
  }

  /**
   * Returns information about the format of the expiry date/time.
   *
   * return true if the expiry date/time is absolute,
   * false if it is intended as an interval.
   */
  public boolean isExpiryAbsolute() {
    return flagExpiryAbsolute;
  }

  /**
   * Retrieves the delivery-report flag (Optional).
   *
   * return  true if the user wants the delivery report.
   */
  public boolean getDeliveryReport() {
    return hDeliveryReport;
  }

  /**
   * Retrieves the sender-visibility flag (Optional).
   *
   * return  0x80 if the user wants the sender visibility setting to Hide.
   * 0x81 if the user wants the sender visibility setting to Show.
   */
  public byte getSenderVisibility() {
    return hSenderVisibility;
  }

  /**
   * Retrieves the read reply flag (Optional).
   *
   * return  true if the user wants the read reply.
   */
  public boolean getReadReply() {
    return hReadReply;
  }

  /**
   * Retrieves the delivery date/time of the message (Optional).
   *
   * return the delivery date/time.
   */
  public Date getDeliveryTime() {
    return hDeliveryTime;
  }

  /**
   * Specify whether the user wants a delivery report from each recipient (Optional).
   *
   * param value true if the user wants the delivery report.
   */
  public void setDeliveryReport(boolean value) {
    hDeliveryReport = value;
    flagDeliveryReportAvailable = true;
  }

  /**
   * Specify whether the user wants sender visibility (Optional).
   *
   * return  0x80 if the user wants the sender visibility setting to Hide.
   * 0x81 if the user wants the sender visibility setting to Show.
   */
  public void setSenderVisibility(byte value) {
    hSenderVisibility = value;
    flagSenderVisibilityAvailable = true;
  }

  /**
   * Specify whether the user wants a read report from each recipient as a new message(Optional).
   *
   * param value true if the user wants the read report.
   */
  public void setReadReply(boolean value) {
    hReadReply = value;
    flagReadReplyAvailable = true;
  }

  /**
   * Sets the date/time of the desired delivery(Optional).
   * Indicates the earliest possible delivery of the message to the recipient.
   * This field can have two format,
   * either absolute or interval depending on how it is set with the method
   * setDeliveryTimeAbsolute().
   *
   * param value the delivery date/time.
   */
  public void setDeliveryTime(Date value) {
    hDeliveryTime = value;
    flagDeliveryTimeAvailable = true;
  }

  /**
   * Sets the format of the delivery date/time.
   *
   * param value if true the delivery date/time is absolute, else is
   * intended as an interval.
   */
  public void setDeliveryTimeAbsolute (boolean value) {
    flagDeliveryTimeAbsolute = value;
  }

  /**
   * Returns information about the format of the delivery date/time.
   *
   * return true if the delivery date/time is absolute,
   * false if it is intended as an interval.
   */
  public boolean isDeliveryTimeAbsolute() {
    return flagDeliveryTimeAbsolute;
  }

  /**
   * Retrieves the priority of the message for the recipient (Optional).
   *
   * return the priority. It can be one of the following the values:
   * PRIORITY_LOW, PRIORITY_NORMAL, PRIORITY_HIGH
   *
   */
  public byte getPriority() {
    return hPriority;
  }

  /**
   * Sets the priority of the message for the recipient (Optional).
   *
   * param value One of the following the values:
   * PRIORITY_LOW, PRIORITY_NORMAL, PRIORITY_HIGH
   *
   */
  public void setPriority(byte value) {
    hPriority = value;
    flagPriorityAvailable = true;
  }


  public MMMessage() {
    tableOfContents = new Hashtable();
    hTo = new Vector();
    hCc = new Vector();
    hBcc = new Vector();
  }

}