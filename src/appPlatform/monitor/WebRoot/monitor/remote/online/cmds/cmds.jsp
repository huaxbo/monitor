<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="../globalRes/js/jquery-hxb-tabs.js"></script>
<script type="text/javascript">
//构建在线状态
function onlineHandler(v,r,i){
	if(v){
		var img = new Array();
		img.push('<img class="img_btn" src="../globalRes/images/ico/');
		if(v == '<s:property value="@com.hxb.monitor.commu.protocol.CommandConstant@ONLINE_STATUS_ON"/>'){
			img.push('data_ico_3.png');//在线状态
		}else if(v == '<s:property value="@com.hxb.monitor.commu.protocol.CommandConstant@ONLINE_STATUS_UN"/>'){
			img.push('data_ico_4.png');//离线状态
		}
		img.push('"/>');
		return img.join("");
	}
}
//构建远程命令
function commandHandler(v,r,i){
	if(v){
		var img = new Array();
		img.push('<a id="opt_' + r.id + '" href=javascript:');
		img.push('commandOpt(');
		img.push('\'' + v + '\'');
		img.push(',\'' + r.id + '\'');
		img.push(')>');
		img.push('<img class="img_btn" src="../globalRes/images/ico/warnResponse.gif"/>');
		img.push('</a>');
		return img.join("");
	}
}
//远程命令操作
function commandOpt(v,id){
	$j("a.tip_a").tooltip("hide");
	var opt = $j("a#opt_" + id);
	if(!opt.hasClass("tip_a")){
		opt.addClass("tip_a");
		var cnto = $j("div#" + v);
		var cnt = cnto.clone(true);
		cnt.show();
		cnt.wrap("<div></div>");
		opt.tooltip({
			content:$j("<div width=" + (cnto.width() + 6) + "px;'></div>"),
			onUpdate:function(content){
				content.panel({
					title:'远程命令',
					closable:true,
					content:cnt.parent("div:first").html(),
					onClose:function(){
						opt.tooltip("hide");
						content.panel("open");
					}
				});
			},
			position:'left',
			showEvent:'click',
			onShow:function(){				
				$j(this).unbind();
			}
		});	 
	}		
	opt.tooltip("show");
}
</script>
<style type="text/css">
	.img_btn{width:15px;height:15px;cursor:pointer;}
	.command_divs{width:264px;height:200px;margin:2px 0 0 2px;padding:0;}
	.command_div{width:130px;height:26px;text-align:center;padding-top:3px;cursor:pointer;float:left;}
</style>
</head>
<body>
	<div id="ver_test" class="command_divs" style="display:none">
		<div class="tab_tr">
           <ul>
            	<li onclick="changeTab(this)"><a href="#">遥测</a></li>	
            	<li onclick="changeTab(this)"><a href="#">设置</a></li>
            	<li onclick="changeTab(this)"><a href="#">命令</a></li>	
            </ul>	
        </div>
        <div class="tab_cnt" title="遥测">
        	<div class="command_div">遥测实时值1</div>
        	<div class="command_div">遥测实时值2</div>
        	<div class="command_div">遥测实时值3</div>
        	<div class="command_div">遥测实时值4</div>
        </div>
        <div class="tab_cnt" title="设置">
        	<div class="command_div">遥测实时值21</div>
        	<div class="command_div">遥测实时值22</div>
        	<div class="command_div">遥测实时值23</div>
        	<div class="command_div">遥测实时值24</div>
        </div>
        <div class="tab_cnt" title="命令">
        	<div class="command_div">遥测实时值31</div>
        	<div class="command_div">遥测实时值32</div>
        	<div class="command_div">遥测实时值33</div>
        	<div class="command_div">遥测实时值34</div>
        </div>              
	</div>
</body>
</html>