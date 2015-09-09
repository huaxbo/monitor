<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="css/login.css"/>
<link rel="stylesheet" type="text/css" href="js/jquery_easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="js/jquery_easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="js/jquery_validate/css/jquery.validate.css" />

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery_easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery_validate/jquery.validate.js"></script>
<script type="text/javascript" src="js/jquery_validate/jquery.validate.metadata.js"></script>
<script type="text/javascript" src="js/jquery_validate/jquery.validate.message_cn.js"></script>
<script type="text/javascript" src="js/jquery-automic-global.js"></script>
<script type="text/javascript">
	$j(document).ready(function(){
		$j("form#login_form").validate({meta:"validate",
			submitHandler:function(form){				
				form.submit();	
			},
			onkeyup:false,
			debug:false,
			errorElement:"em"		
		});	
		//初始光标
		$j(":text:first").focus();
		//信息提示
		var mess = '<s:property value="#request.message"/>';
		if(mess != ''){
			$j.messager.alert("信息提示","用户名或密码不正确！","warning");
			$j(":text:last").val();
		}
		//重定向地址赋值
		var r1 = '<s:property value="#request.RefererUrl"/>';
		var r2 = '<s:property value="#parameters.RefererUrl"/>';
		$j(":hidden[name=referer]").val(r1 != '' ? r1 : r2);
		//绑定回车事件
		$j(this).keyup(function(evnt){
			if(evnt.which == 13){
				login();
			}
		});
	});
	//登录操作
	function login(){
		$j("#hide_login").click();
	} 
</script>

<style>
	em.error{display:block;margin:5px 0 0 60px;}	
</style>
</head>
<body>
<form action="loginAction!login.action" method="post" id="login_form">
	<input type="hidden" name="referer" />
	<div id="login">
    <div class="box">
        <ul>
          <li>
          	<label>用户名</label><s:textfield id="name" key="name" cssClass="{validate:{required:true}}" theme="simple"></s:textfield>
          </li>
          <li>
          	<label>密&nbsp;&nbsp;&nbsp;码</label><s:password id="passwd" key="password" cssClass="{validate:{required:true}}" theme="simple"></s:password>
          </li>
        </ul>
      <div class="login-button"><a href="javascript:login();"></a></div>
    </div>
  </div>
  <input type="submit" style="display:none" id="hide_login"/>
</form>
</body>
</html>
