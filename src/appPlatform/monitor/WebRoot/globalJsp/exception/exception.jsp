<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<s:include value="../include/linker.jsp"></s:include>
<script type="text/javascript">
	$j(document).ready(function(){
		$j("table.Pbox_table tr:first").toggle(function(){
				$j(this).next("tr").show();
				updateCustomscroll("body");
			},function(){
				$j(this).next("tr").hide();
				updateCustomscroll("body");
			});
	});
</script>
</head>
<body class="AMCustomscroll">
<div class="location">友情提示</div>
<div class="margin">
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="6" height="30" class="table_top_l">&nbsp;</td>
      <td width="1087" class="table_top_c"><div class="title ico"><h3 class="ico6">异常信息</h3></div></td>
      <td width="6" class="table_top_r">&nbsp;</td>
    </tr>
    <tr>
      <td class="table_cen_l">&nbsp;</td>
      <td>
       <div class="box">
          <div class="box_rela">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="table_tit_l">&nbsp;</td>
                <td class="table_tit_c"><div class="box_tit"><span class="titico6"></span>异常信息</div></td>
                <td class="table_tit_r">&nbsp;</td>
              </tr>
            </table>           
            <div class="box_bor" style="padding:0px;">
              <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Pbox_table common_table">
                <tr class="td_bor" title="点击查看更多...">
                  <th width="20%" class="td_bor">异常信息：</th>
                  <td width="80%" class="td_bor"><s:property value="exception"/></td> 
                </tr>
                <tr class="td_bor" style="display:none">
                  <td width="100%" class="td_bor" colspan="2"><s:property value="exceptionStack"/></td> 
                </tr>                             
              </table>
            </div>
          </div>
        </div>
      </td>
      <td class="table_cen_r">&nbsp;</td>
    </tr>
  </table>
  <div class="box_bot2"></div>
</div>	
</html>