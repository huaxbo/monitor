<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<s:include value="../../../globalJsp/include/linker.jsp"></s:include>
<s:include value="../../../globalJsp/include/datagridJs.jsp"></s:include>
<script type="text/javascript">
	//表单校验
	function checkForm(f){
		
		return true;
	}
</script>
</head>

<body class="AMCustomscroll">
<s:include value="title.jsp"></s:include>
<div class="margin">
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">    
    <tr>
      <td>
      <div class="box">
       <form id="search_form" action="<s:property value='actionName'/>!toList.action" method="post" enctype="multipart/form-data">
       		<s:iterator id="hps" value="formProperties" status="hst">
		 		<input type="hidden" name="<s:property value="key"/>" value="<s:property value="value"/>"/>
		 	</s:iterator>
       		<input type="file" id="imp_excel" name="uploadSingle" 
       			style="display:none" accept="application/vnd.ms-excel"/>
         <div class="box_rela">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="table_tit_l">&nbsp;</td>
                <td class="table_tit_c"><div class="box_tit">查询条件</div></td>
                <td class="table_tit_r">&nbsp;</td>
              </tr>
            </table>           
	            <div class="box_bor">
	            	<div class="box_inquiryt">     
	               		<s:include value="condition.jsp"></s:include>                                 
	                	<div class="box_inquiry_line"></div>
	              </div>  
	              <div class="box_search box_search_only">
	              	<div class="buttons">
		              	<a href="javascript:search();">
		                  <div class="buttons_ico"><img src="../globalRes/images/ico/button_ico.png"/></div>
		                  <span>查询</span>
		                 </a>	
	              	</div>
	              </div>             
	              <div class="box_more" style="display:none">
	                <div class="button_more"><a href="#">更多选项</a></div>
	              </div>
	              </div>
	          </div>
	        </form>           
          </div>                         
          <div class="box_rela">
         	<table id="datas" class="easyui-datagrid" title="数据列表"
         		data-options="fitColumns:true,pagination:true,rownumbers:true,
         			singleSelect:true,checkOnSelect:false,selectOnCheck:false,remoteSort:false,toolbar:toolbar,height:364">
         		<thead>
         			<tr>
         				<th data-options="field:'ck',checkbox:true"></th>
         				<th data-options="field:'id',hidden:true,width:25">id</th>
         				<th data-options="field:'meterNm',sortable:true,align:'center',width:25">测控设备名称</th>
         				<th data-options="field:'meterId',sortable:true,align:'center',width:25">测控设备ID</th>
         				<th data-options="field:'meterProNm',sortable:true,align:'center',width:25">通讯协议</th>
         				<th data-options="field:'serverNm',sortable:true,align:'center',width:25">通讯服务</th>
         			</tr>
         		</thead>
         	
         	</table>   
          </div>
      </td>
    </tr>
  </table>
</div>
 <div id="detail_win" class="easyui-window AMCustomscroll" title="信息维护" data-options="closed:true" style="width:700px;height:400px;overflow:hidden;padding:0;margin:0;"></div>
 <div id="toolbar">
	<a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
	<a href="javascript:toEdit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
	<a href="javascript:toDel();" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
 </div>
 <form id="hiddenForm" action="<s:property value='actionName'/>!list.action" method="post" >
 	<s:iterator id="hps" value="allProperties" status="hst">
 		<input type="hidden" name="<s:property value="key"/>" value="<s:property value="value"/>"/>
 	</s:iterator>
 </form>
</body>
</html>