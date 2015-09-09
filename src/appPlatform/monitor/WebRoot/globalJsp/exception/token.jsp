<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../globalRes/css/base.css"/>
<link rel="stylesheet" type="text/css" href="../globalRes/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="../globalRes/css/jquery.automic.bodyScroll.css"/>
<link rel="stylesheet" type="text/css" href="../globalRes/js/customscroll/jquery.mCustomScrollbar.css" />
<link rel="stylesheet" type="text/css" href="../globalRes/js/jquery_easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="../globalRes/js/jquery_easyui/themes/icon.css"/>

<script type="text/javascript" src="../globalRes/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../globalRes/js/customscroll/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="../globalRes/js/jquery_easyui/jquery.easyui.min.js"></script>

<script type="text/javascript" src="../globalRes/js/jquery-automic-global.js"></script>
<script type="text/javascript" src="../globalRes/js/jquery-automic-scroll.js"></script>
<%
	String ct = request.getContextPath();
%>
<script type="text/javascript">
	var counter = 5;
	var intv = null;
	var redit = null;
	$j(document).ready(function(){
		fun_counter();
		intv = setInterval('fun_counter()',1000);		
	});
	function fun_counter(){
		$j("div.box_photo_font p:last span:first").html("(" + counter + "s)");
		if(counter == 0){
			clearInterval(intv);	
			//$j("[name=mainFrame]",window.parent.parent.document).attr("src",'<%=ct%>');//自动跳至本系统默认页
			window.parent.location = '<%=ct%>';//自动跳至本系统默认页
			return ;
		}
		counter--;
	}
</script>
</head>
<body class="AMCustomscroll">
<div class="location">友情提示</div>
<div class="margin">
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="6" height="30" class="table_top_l">&nbsp;</td>
      <td width="1087" class="table_top_c"><div class="title ico">
          <h3 class="ico3">异常信息</h3>
        </div></td>
      <td width="6" class="table_top_r">&nbsp;</td>
    </tr>
    <tr>
      <td class="table_cen_l">&nbsp;</td>
      <td>      	
      	<div class="box">	      	
	        <div class="box_photo_mar1">
	       		<div class="box_photo_redirect"></div>
	        	<div class="box_photo_font">
		        	<p><span>自动屏蔽重复维护信息操作!</span></p>
		        	<p><span></span><span>自动跳转至初始页面!</span></p>
		        </div>
	        </div>
      	</div>
        </td>
      <td class="table_cen_r">&nbsp;</td>
    </tr>
  </table>
  <div class="box_bot"></div>
</div>
</body>
</html>