package com.hxb.common.socketPool;

import java.io.IOException;
import java.io.OutputStream;

public abstract interface SocketJob {
	public abstract void out(String paramString, byte[] paramArrayOfByte,
			OutputStream paramOutputStream) throws IOException;
}