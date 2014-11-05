/*
 * @(#)IMMConstants.java	1.1
 *
 * Copyright (c) Nokia Corporation 2002
 *
 */

package com.androidbridge.nokia;

/**
 * The IMMConstants interface contains all the constants that are useful
 * for the management of a Multimedia Message.
 */

public interface IMMConstants {

  static final byte MMS_VERSION_10 = (byte)0x90; // V1.0

 /* Field Names Costants */
  static final byte FN_BCC=0x01;
  static final byte FN_CC=0x02;
  static final byte FN_CONTENT_TYPE=0x04;
  static final byte FN_DATE=0x05;
  static final byte FN_DELIVERY_REPORT=0x06;
  static final byte FN_DELIVERY_TIME=0x07;
  static final byte FN_EXPIRY=0x08;
  static final byte FN_FROM=0x09;
  static final byte FN_MESSAGE_CLASS=0x0A;
  static final byte FN_MESSAGE_ID=0x0B;
  static final byte FN_MESSAGE_TYPE=0x0C;
  static final byte FN_MMS_VERSION=0x0D;
  static final byte FN_PRIORITY=0x0F;
  static final byte FN_READ_REPLY=0x10;
  static final byte FN_SENDER_VISIBILITY=0x14;
  static final byte FN_STATUS=0x15;
  static final byte FN_SUBJECT=0x16;
  static final byte FN_TO=0x17;
  static final byte FN_TRANSACTION_ID=0x18;


  static final byte ADDRESS_TYPE_UNKNOWN=0;
  static final byte ADDRESS_TYPE_PLMN=1;
  static final byte ADDRESS_TYPE_IPV4=2;
  static final byte ADDRESS_TYPE_IPV6=3;
  static final byte ADDRESS_TYPE_EMAIL=4;

  static final byte MESSAGE_TYPE_M_SEND_REQ=(byte)0x80;
  static final byte MESSAGE_TYPE_M_DELIVERY_IND=(byte)0x86;

  static final byte MESSAGE_STATUS_EXPIRED =(byte)0x80;
  static final byte MESSAGE_STATUS_RETRIEVED =(byte)0x81;
  static final byte MESSAGE_STATUS_REJECTED =(byte)0x82;
  static final byte MESSAGE_STATUS_DEFERRED =(byte)0x83;
  static final byte MESSAGE_STATUS_UNRECOGNISED =(byte)0x84;

  static final byte SENDER_VISIBILITY_HIDE =(byte)0x80;
  static final byte SENDER_VISIBILITY_SHOW =(byte)0x81;

  static final byte MESSAGE_CLASS_PERSONAL =(byte)0x80;
  static final byte MESSAGE_CLASS_ADVERTISEMENT =(byte)0x81;
  static final byte MESSAGE_CLASS_INFORMATIONAL = (byte)0x82;
  static final byte MESSAGE_CLASS_AUTO = (byte)0x83;

  static final byte PRIORITY_LOW = (byte) 0x80;
  static final byte PRIORITY_NORMAL = (byte) 0x81;
  static final byte PRIORITY_HIGH = (byte) 0x82;


  /* Header Field Name of the Content (Entry)*/
  static final byte HFN_CONTENT_LOCATION=0x0E;
  static final byte HFN_CONTENT_ID=0x40;

  /* Well-known Parameter Assignments */
  static final byte WKPA_TYPE=0x09;
  static final byte WKPA_START=0x0A;

  /* Content Types Strings*/
  static final String CT_TEXT_HTML="text/html";
  static final String CT_TEXT_PLAIN="text/plain";
  static final String CT_TEXT_WML="text/vnd.wap.wml";
  static final String CT_IMAGE_GIF="image/gif";
  static final String CT_IMAGE_JPEG="image/jpeg";
  static final String CT_IMAGE_TIFF="image/tiff";
  static final String CT_IMAGE_PNG="image/png";
  static final String CT_IMAGE_WBMP="image/vnd.wap.wbmp";
  static final String CT_APPLICATION_MULTIPART_MIXED="application/vnd.wap.multipart.mixed";
  static final String CT_APPLICATION_MULTIPART_RELATED="application/vnd.wap.multipart.related";
  static final String CT_APPLICATION_SMIL="application/smil";

}