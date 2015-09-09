var $j = jQuery.noConflict();
$j(document).ready(function(){
	/**
	* jquery ajax缓存清空处理
	*/
	$j.ajaxSetup({cache:false});	
});
//
function alertMessage(mess){
	$j.messager.alert("信息提示",mess,"alert");
}
function alertWarn(mess){
	$j.messager.alert("信息提示",mess,"warn");
}
function alertError(mess){
	$j.messager.alert("信息提示",mess,"error");
}
function alertInfo(mess){
	$j.messager.alert("信息提示",mess,"info");
}
function alertQuestion(mess){
	$j.messager.alert("信息提示",mess,"question");
}
function confirmMessage(mess,succFun,params){
    $j.messager.confirm("信息提示", mess, function(r){
       if(r){
    	   succFun(params);
       }
    });
}
function statusMessage(mess){
	 $j.messager.show({
         title:'信息提示',
         msg:mess,
         showType:'slide',
         timeout:1000
     });
}
//ajax请求封装
function ajaxRequest(_url,_dataType,_type,_data,succ_fun,err_fuc){
	$j.ajax({
		url:_url,dataType:_dataType,type:_type,data:_data,
		success:function(res){
			if(res != null && res.noJQueryPriv != null){
				$j.messager.alert("信息提示",res.noJQueryPriv,"warning");
			}else if(typeof succ_fun != 'undefined'){
				succ_fun(res);	
			}			
		},error:function(ht,ts,err){
			if(typeof err_func != 'undefined'){
				err_func(ht,ts,err);
			}
		}
	});
}