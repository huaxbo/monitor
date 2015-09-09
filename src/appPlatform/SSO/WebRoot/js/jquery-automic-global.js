var $j = jQuery.noConflict();
$j(document).ready(function(){
	/**
	* jquery ajax缓存清空处理
	*/
	$j.ajaxSetup({cache:false});
	/**
	* easyui 默认属性重置
	*/
	if($j.messager){
		var def = $j.messager.defaults;
		def.ok = "确定";
		def.cancel = "取消";
	}	
	//easyui-linkbutton	
	$j("a.easyui-linkbutton").each(function(){
		$j(this).linkbutton().addClass("ext");
	});	
});