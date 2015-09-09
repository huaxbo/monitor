<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../../globalRes/css/base.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../globalRes/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
	$j(document).ready(function(){
		$j("ul.nav li").click(function(){
			//1级功能点击效果处理
			$j("ul.nav li").removeClass("nav_a");
			$j(this).addClass("nav_a");				
			//1级导航跳转
			var url = $j(this).children("a").first().attr("href");
			var f = $j("form[name='hidden_form']").get(0);
			f.target = "mainFrame";
			f.action = url;
			f.submit();
			return false;//禁止链接自动提交
		});		
		//默认触发第一个导航功能
		$j("ul li:first").triggerHandler("click");
	});			
	
</script>
</head>
<body class="min">
<div class="header">
<div class="header_bg">
  <div class="header_logo"></div>
  <div class="msg">
    <div class="msgt"> <span class="Help"><a href="#">帮助</a></span> <span class="Quit"><a href="#">退出</a></span> </div>
    <div class="msgt_ft">欢迎您：管理员！</div>
  </div>
  <div class="header_nav">
    <ul class="nav">
    	<s:iterator id="subs" value="@com.automic.pomf.util.subSyses.SubSysesUtil@subSyses" status="st">
	     <li>
	      	<a href="<s:property value="sysContext"/><s:property value="sysPath"/>">
	      		<span class="<s:property value="sysMarker"/> nav_margin"></span>
	      		<span class="title"><s:property value="sysTitle"/></span>
	      	</a>
	      </li>
    	</s:iterator>
    </ul>
  </div>
  </div>
</div>
<form name="hidden_form" method="post"></form>
</body>
</html>