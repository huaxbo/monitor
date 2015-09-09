<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<div class="menu_title" title="菜单-1">
					<div class="menu_item menu_item_bg" data-linker="demo1Action!test1.action">选项-1-1</div>
					<div class="menu_item menu_item_bg" data-linker="demo1Action!test2.action">选项-1-2</div>
					<div class="menu_item menu_item_bg" data-linker="demo1Action!test3.action">选项-1-3</div>
					<div class="menu_item menu_item_bg" data-linker="demo1Action!test4.action">选项-1-4</div>
				</div>
				<div class="menu_title" title="菜单-2">
					<div class="menu_item menu_item_bg" data-linker="demo2Action!toList.action">选项-2-1</div>
					<div class="menu_item menu_item_bg" data-linker="">选项-2-2</div>
					<div class="menu_item menu_item_bg" data-linker="">选项-2-3</div>
					<div class="menu_item menu_item_bg" data-linker="">选项-2-4</div>
				</div>
				<div class="menu_title" title="菜单-3">
					<div class="menu_item menu_item_bg" data-linker="">选项-3-1</div>
					<div class="menu_item menu_item_bg" data-linker="">选项-3-2</div>
					<div class="menu_item menu_item_bg" data-linker="">选项-3-3</div>
					<div class="menu_item menu_item_bg" data-linker="">选项-3-4</div>
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