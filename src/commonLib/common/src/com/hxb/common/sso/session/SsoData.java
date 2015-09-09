package com.hxb.common.sso.session;

import java.io.Serializable;

public class SsoData  implements Serializable {

	private static final long serialVersionUID = 366408037054077042L;
	public Integer order ;//命令字
	public String sessionId ;//会话ID
	public Object data ;//通信的数据
}
