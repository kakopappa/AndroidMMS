/*
 * @(#)MMSenderException.java	1.1
 *
 * Copyright (c) Nokia Corporation 2002
 *
 */

package com.androidbridge.nokia;

/**
 * Thrown when an error occurs sending a Multimedia Message
 *
 */


public class MMSenderException extends Exception {

  public MMSenderException(String errormsg) {
    super(errormsg);
  }

}