/*
 * @(#)MMAddress.java	1.1
 *
 * Copyright (c) Nokia Corporation 2002
 *
 */

package com.androidbridge.nokia;

import java.io.Serializable;

/**
 * The MMAddress class represents a generic address for a sender or a receiver
 * of a Multimedia Message.
 *
 */

public class MMAddress implements IMMConstants, Serializable {

  private String address;
  private byte type;

  /**
   * Creates a new and empty MM address
   */
  public MMAddress() {
    super();
    address = null;
    type = -1;
  }

  /**
   * Creates a MM address specifying the address and the type.
   *
   * param addr the string representing the address
   * param type the type of the address. It can be:
   * ADDRESS_TYPE_UNKNOWN, ADDRESS_TYPE_PLMN,ADDRESS_TYPE_IPV4,
   * ADDRESS_TYPE_IPV6, ADDRESS_TYPE_EMAIL 
   *
   */
  public MMAddress(String addr, byte type) {
    super();
    setAddress(addr, type);
  }

  /**
   * Creates a new MM address initialising it to the value passed as parameter.
   */
  public MMAddress(MMAddress value) {
    super();
    setAddress(value.address, value.type);
  }

  /**
   * Sets MM address value specifying the address and the type.
   *
   * param addr the string representing the address
   * param type the type of the address. It can be:
   * ADDRESS_TYPE_UNKNOWN, ADDRESS_TYPE_PLMN,ADDRESS_TYPE_IPV4,
   * ADDRESS_TYPE_IPV6, ADDRESS_TYPE_EMAIL 
   *
   */
  public void setAddress(String addr, byte type) {
    if (addr != null) {
      this.address = new String(addr);
      this.type = type;
    }
  }

  /**
   * Retrieves the MM address value.
   *
   */
  public String getAddress() {
    return address;
  }

  /**
   * Retrieves the MM address value in the full format. For example: +358990000066/TYPE=PLMN,
   * joe@user.org, 1.2.3.4/TYPE=IPv4
   *
   */
  public String getFullAddress() {
    switch (type) {
      case ADDRESS_TYPE_PLMN : return address+"/TYPE=PLMN";
      case ADDRESS_TYPE_IPV4 : return address+"/TYPE=IPv4";
      case ADDRESS_TYPE_IPV6 : return address+"/TYPE=IPv6";
      default: return address;
    }
  }

  /**
   * Retrieves the MM address type.
   *
   * return the type of the address. It can be:
   * ADDRESS_TYPE_UNKNOWN, ADDRESS_TYPE_PLMN,ADDRESS_TYPE_IPV4,
   * ADDRESS_TYPE_IPV6, ADDRESS_TYPE_EMAIL 
   *
   */
  public byte getType() {
    return type;
  }
}
