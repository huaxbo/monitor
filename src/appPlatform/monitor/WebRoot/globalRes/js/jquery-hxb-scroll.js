jQuery(function($){
	//加载class=AMCustomscrol块的滚动条插件
	//添加滚动条块，必须设置overflow-x:hidden;overflow-y:auto;	
	$(".AMCustomscroll").each(function(){
		if(!$(this).hasClass("mCustomScrollbar")){//避免重复绑定
			$(this).mCustomScrollbar({
				scrollButtons:{enable:true},
				theme:'dark-2',
				horizontalScroll:false,
				autoHideScrollbar:true
			});				 			
		}
	});	
});
/**
*异步加载内容
*前提：必须初始化完成滚动条控件
*/
function synCustomscroll(_selector,_url){
	(function($){
		if($(_selector).find(".mCSB_container").size()<1){
			$(_selector).load(_url,function(){
					updateCustomscroll($(_selector).closest(".AMCustomscroll"));
			});
		}else{
			$(_selector + " .mCSB_container").load(_url,function(){
					updateCustomscroll(_selector);
			});
		}
	})(jQuery);	
}
//更新滚动条
function updateCustomscroll(_$selector){
	(function($){
		if(!(_$selector instanceof jQuery)){
			_$selector = $(_$selector);	
		}
		if(_$selector.hasClass("mCustomScrollbar")){
			_$selector.mCustomScrollbar("update"); //update scrollbar according to newly loaded content
			_$selector.mCustomScrollbar("scrollTo","top",{scrollInertia:200}); //scroll to top
		}			
	})(jQuery);	
}
//滚动条更新
function reCustomscroll(_selector){
	(function($){
		$(_selector).mCustomScrollbar("update"); //update scrollbar according to newly loaded content
		$(_selector).mCustomScrollbar("scrollTo","top",{scrollInertia:200}); //scroll to top	
	})(jQuery);	
}