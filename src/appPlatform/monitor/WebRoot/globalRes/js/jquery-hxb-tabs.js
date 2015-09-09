jQuery(function($){
	$("div.tab_tr li:first").click();
});
//
function changeTab(tb){
	(function($){	
		var idx = $(tb).prevAll("li").size();
		$(tb).siblings("li").removeClass("active");
		$(tb).addClass("active");
		$(tb).closest("div.tab_tr").siblings("div.tab_cnt:eq(" + idx + ")")
			.show().siblings("div.tab_cnt").hide();	
	})(jQuery);
}
