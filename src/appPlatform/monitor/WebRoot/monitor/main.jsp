<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../globalRes/css/layout1.css">
<link rel="stylesheet" type="text/css" href="../globalRes/js/jquery_easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../globalRes/js/jquery_easyui/themes/icon.css">
<script type="text/javascript" src="../globalRes/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../globalRes/js/jquery_easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../globalRes/js/jquery_easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../globalRes/js/jquery-hxb-menu.js"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
	$j.ajax({
		cache : false
	});
	var tabs_max = 5;//tab最多数量	
</script>
</head>
<body>
	<div id="main_div">
		<div id="left_div" data-options="region:'west',split:false" title="操作菜单">
			<div class="easyui-accordion" data-options="fit:false,border:false">
				<div class="menu_title" title="<s:property value="@com.hxb.util.Config@getConfig('monitor.remote')"/>">
					<div class="menu_item menu_item_bg" data-linker="onlineAction!toList.action"><s:property value="@com.hxb.util.Config@getConfig('monitor.remote.1')"/></div>
				</div>
				<div class="menu_title" title="<s:property value="@com.hxb.util.Config@getConfig('monitor.statis')"/>">
					<div class="menu_item menu_item_bg" data-linker="demo2Action!toList.action"><s:property value="@com.hxb.util.Config@getConfig('monitor.statis.1')"/></div>
					<div class="menu_item menu_item_bg" data-linker=""><s:property value="@com.hxb.util.Config@getConfig('monitor.staits.2')"/></div>
					<div class="menu_item menu_item_bg" data-linker=""><s:property value="@com.hxb.util.Config@getConfig('monitor.statis.3')"/></div>
				</div>
				<div class="menu_title" title="<s:property value="@com.hxb.util.Config@getConfig('monitor.manager')"/>">
					<div class="menu_item menu_item_bg" data-linker="serverAction!toList.action"><s:property value="@com.hxb.util.Config@getConfig('monitor.manager.1')"/></div>
					<div class="menu_item menu_item_bg" data-linker="meterAction!toList.action"><s:property value="@com.hxb.util.Config@getConfig('monitor.manager.2')"/></div>
					<div class="menu_item menu_item_bg" data-linker=""><s:property value="@com.hxb.util.Config@getConfig('monitor.manager.3')"/></div>
				</div>
			</div>
		</div>
		<div id="right_div" data-options="region:'center'">
			<div id="cnt_tabs" class="easyui-tabs">
				<div title="首页" class="cnt_home" data-options="cache:false" data-linker="demo1Action!test5.action"></div>				
			</div>
			<div id="cnt_frame">
				<iframe src="demo1Action!test5.action" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
			</div>
		</div>			
	</div>
</body>
</html>