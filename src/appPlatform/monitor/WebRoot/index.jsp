<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="@com.hxb.util.Config@getConfig('systemName')"/></title>
</head>
<frameset rows="80,*,31" cols="*" frameborder="no" border="0" framespacing="0">
      <frame src="globalJsp/include/top.jsp" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" />
      <frame src="monitor/monitorWelcomeAction.action" name="mainFrame" title="mainFrame" scrolling="no"/></frame>
      <frame src="globalJsp/include/bottom.jsp" name="topFrame" scrolling="no" noresize="noresize" id="bottomFrame" title="bottomFrame" />
</frameset>
<noframes>
	<body>
		当前浏览器不支持框架,请尝试更换浏览器重新访问！
	</body>
</noframes>

</html>