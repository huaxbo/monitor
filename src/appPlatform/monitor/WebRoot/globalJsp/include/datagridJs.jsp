<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="../globalRes/js/jquery-hxb-datagrid.js"></script>

<script type="text/javascript">		
	//绑定列表查询数据源
	function list(){
		return '<s:property value="actionName"/>!list.action';
	}
	//执行查询操作
	function search(){
		var f = $j("form#search_form").get(0);
		f.submit();
	}
	//添加
	function add(){
		$j("div#detail_win").window("open")
			.window("refresh","<s:property value="actionName"/>!toAdd.action");
	}	
	//编辑
	function edit(r){
		$j("div#detail_win").window("open")
			.window("refresh","<s:property value="actionName"/>!toEdit.action?param_id=" + r.id);
	}
	//保存
	function save(){
		var f = $j("form#eform").get(0);		
		f.action = '<s:property value="actionName"/>';
		if(f.elements['po.id'].value == ''){
			f.action = f.action + '!add.action';
		}else{
			f.action = f.action + '!edit.action';
			opt = 'edit';
		}
		//表单提交
		$j("form#eform").form('submit',{
			onSubmit:function(){
				var vd = $j(this).form("validate");
				if(vd){
					if(typeof checkForm != 'undefined'){
						var cf = checkForm(this);
						if(cf == undefined){
							return false;
						}					
						return cf;
					}
				}						
				return vd;
			},success:function(data){
				var dobj = eval('(' + data + ')');  				
				statusMessage(dobj.message);	
				$j("div#detail_win").window("close");
				$j("table.easyui-datagrid").datagrid("reload");
			}
		});		
	}
	//删除
	function del(rs){
		var ids = '';
		for(var i=0;i<rs.length;i++){
			ids += rs[i].id;
			ids += ',';
		}
		var f = $j("form#hiddenForm").get(0);		
		f.action = '<s:property value="actionName"/>!delete.action';
		$j("form#hiddenForm").form('submit',{
			queryParams:{param_id:ids},
			success:function(data){
				var dobj = eval('(' + data + ')');  				
				statusMessage(dobj.message);
				$j("div#detail_win").window("close");
				$j("table.easyui-datagrid").datagrid("reload");
			}
		});	
	}
</script>
</head>
<body>

</body>
</html>