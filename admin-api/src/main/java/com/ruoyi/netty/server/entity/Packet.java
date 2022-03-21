package com.ruoyi.netty.server.entity;


public class Packet {

  private static final long serialVersionUID = -5248069984631225347L;

  private final byte head;
  private final short sid;
  private final int cmd;
  private final byte[] bytes;
  
  public Packet(byte head, int cmd, byte[] bytes) {
    this(head, (short)0, cmd, bytes);
  }

  public Packet(byte head, short sid, int cmd, byte[] bytes){
    this.head = head;
    this.sid = sid;
    this.cmd = cmd;
    this.bytes = bytes;
  }

  public byte getHead() {
    return this.head;
  }

  public int getCmd() {
    return this.cmd;
  }

  public byte[] getBytes() {
    return this.bytes;
  }

  public short getSid() {
    return this.sid;
  }
}
