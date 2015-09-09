<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>	
	</head>
	<body >
		<form id="eform" method="post" enctype="multipart/form-data">
			<s:hidden key="po.id"></s:hidden>
			<s:hidden key="po.deptId" value="1"></s:hidden>
			<div class="form">
				<table>
					<tr>
						<td class="item">编号</td>
						<td class="item-value">
							<s:textfield key="po.cd" cssClass="input_form_required easyui-validatebox"
								data-options="validType:{digits:true,maxLength:[20]}"/>
						</td>
					</tr>
					<tr>
						<td class="item">姓名</td>
						<td class="item-value">
							<s:textfield key="po.name" cssClass="input_form_required easyui-validatebox" 
								data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td class="item">年龄</td>
						<td class="item-value">
							<s:textfield key="po.age" cssClass="input_form easyui-validatebox"
								data-options="validType:{digits:true}"/>
						</td>
					</tr>
					<tr>
						<td class="item">住址</td>
						<td class="item-value"><s:textfield id="tt" key="po.address" cssClass="input_form"/></td>
					</tr>
					<tr>
						<td class="item">入学时间</td>
						<td class="item-value">
							<s:textfield key="po.dt_str" cssClass="input_form_required easyui-validatebox"
								data-options="required:true"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readonly:true})"/>
						</td>
					</tr>					
					<tr>
						<td colspan="2" class="itemButton">
							<a href="#" class="easyui-linkbutton" 
								data-options="iconCls:'icon-save',width:100" onclick="javascript:save()">保存</a>
						</td>
					</tr>		
				</table>	
			</div>   
		</form>
		
	</body>
</html>