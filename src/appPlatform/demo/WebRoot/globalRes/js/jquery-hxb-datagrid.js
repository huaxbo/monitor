/**
 * easyui-datagrid查询条件处理 
 */;
 jQuery(function($) {	
	$(window).resize(function(){//自动调整表格宽度
		var wt = $(window).width();
		wt -= getSize($("div.margin").css("margin-left"));
		wt -= getSize($("div.margin").css("margin-right"));
		if(wt<783){//最小尺寸定义
			wt = 783;
		}
		$("table.easyui-datagrid")
			.datagrid("resize",{width:wt});		
	});
	$("table.easyui-datagrid").datagrid({//绑定表格查询地址及参数
		"url" : list(),
		"queryParams" : buildCons(),
		onBeforeLoad : function() {
			var opt = $(this).datagrid("options");
			opt.queryParams = buildCons();
		}
	});	
	// 构建参数json
	function buildCons() {
		var params = new Array();
		$("form#hiddenForm [name^=con_]:hidden").each(
				function() {
					params.push("\"" + $(this).attr("name") + "\":" + "\""
							+ $(this).val() + "\"");
				});
		return $.parseJSON("{" + params.join(",") + "}");
	}
	//获取尺寸
	function getSize(mg){
		if(mg && mg.indexOf("px")>-1){
			return parseInt(mg.substring(0,mg.indexOf("px")));
		}else{
			return 0;
		}
	}
});
//添加
function toAdd(){
	add();
}
//编辑
function toEdit(){
	var rs = $j("table.easyui-datagrid").datagrid("getChecked");
	if(rs.length==0){
		alertWarn("请选择编辑的记录!");
	}else if(rs.length>1){
		confirmMessage("是否编辑第一条记录？",edit,rs[0]);
	}else{
		edit(rs[0]);
	}
}
//删除
function toDel(){
	var rs = $j("table.easyui-datagrid").datagrid("getChecked");
	if(rs.length==0){
		alertWarn("请选择删除的记录!");
	}else{
		del(rs);
	}
}