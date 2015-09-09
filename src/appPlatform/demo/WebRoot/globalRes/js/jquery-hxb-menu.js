(function($){
	$(document).ready(function() {
		//框架尺寸调整
		$("div#main_div").layout();
		resize_main();
		resize_tabs();
		$(window).resize(function(){
			resize_main();
			resize_tabs();
		});
		//菜单选项底边处理
		$("div.menu_title").each(function(){
			$(this).children(".menu_item:not(:last)").addClass("menu_item_bottom");
		});
		//菜单选项样式操作
		$("div.menu_item").mouseover(function(){
			if(!$(this).hasClass("menu_item_bg_click")){
				$(this).removeClass("menu_item_bg")
					.addClass("menu_item_bg_over");				
			}
		}).mouseout(function(){
			if(!$(this).hasClass("menu_item_bg_click")){
				$(this).removeClass("menu_item_bg_over")
					.addClass("menu_item_bg");					
			}
		}).click(function(){
			if(!$(this).hasClass("menu_item_bg_click")){
				$("div.menu_item").removeClass("menu_item_bg_click").addClass("menu_item_bg");
				$(this).removeClass("menu_item_bg_over")
					.addClass("menu_item_bg_click");				
			}
			//menu click handler			
			menu_clk($(this).attr("data-linker"),$(this).text());
		});
		//tab select重置panel
		$("div#cnt_tabs").tabs(
			{onSelect:function(title,index){
				var src = "";
				if(index == 0){
					src = $("div.cnt_home:first").attr("data-linker");
				}else{
					src = $("div.menu_item:contains(" + title + ")").attr("data-linker");					
				}
				if(!src || src == ""){
					$("div#cnt_frame iframe").attr("src","../glo/globalAction!blank.action");
				}else{
					$("div#cnt_frame iframe").attr("src",src);					
				}
			}
		});
	});
	//窗口调整重置
	function resize_main(){
		$("div#main_div").layout("resize",{height:$(window).height()});
	}
	//tab panel调整
	function resize_tabs(){
		//header
		var wt = $(window).width()-$("div#left_div").width();
		var min_wt = $("div#right_div").css("min-width");
		min_wt = min_wt.substring(0,min_wt.length-2);
		if(wt < parseInt(min_wt)){
			wt = min_wt;
		}
		$("div#right_div .tabs-header").width(wt);
		$("div#right_div .tabs-header>.tabs-wrap").width(wt);
		//panel
		var ext = $("div#right_div .tabs-header ul.tabs").height();
		resize_cnt(ext);
	}
	function resize_cnt(ext){
		if(!ext){
			ext = 0;
		}
		$("div#cnt_frame iframe").attr("height",$(window).height()-ext-6);
	}
	//menu click handler
	function menu_clk(src,title){
		var cnt_tabs = $("#cnt_tabs");				
		if(cnt_tabs.tabs("exists",title)){//选择tab
			cnt_tabs.tabs("unselect",title);
			cnt_tabs.tabs("select",title);
		}else{//新增tab
			if(cnt_tabs.tabs("tabs").length == tabs_max){//关闭第一个非首页的tab
				$("#cnt_tabs").tabs("close",1);
			}
			cnt_tabs.tabs("add",{
				title:title,
				closable:true,
				cache:false
			});
		}
	}
})(jQuery);