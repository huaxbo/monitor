/*    */ package com.hxb.common.imp.socketPool;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class PoolSocket
/*    */ {
/*    */   public Socket socket;
/*    */   public OutputStream out;
/*    */   public InputStream in;
/*    */   public boolean newCreate;
/*    */ 
/*    */   public PoolSocket(Socket socket, OutputStream out, InputStream in, boolean newCreate)
/*    */   {
/* 16 */     this.socket = socket;
/* 17 */     this.out = out;
/* 18 */     this.in = in;
/*    */   }
/*    */ }

/* Location:           E:\huaXinboE\ProjectsE\Year2013\006_pomf\commonLib\lib\ACCommon-base.1.0.jar
 * Qualified Name:     com.automic.common.imp.socketPool.PoolSocket
 * JD-Core Version:    0.6.0
 */