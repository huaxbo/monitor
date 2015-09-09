(function($){
	$.extend($.fn.validatebox.defaults.rules, {
		maxLength:{
			validator:function(value,param){
				return value.length <= param[0];
			},message:'Please enter at most {0} characters.'
		},
		minLength : {
			validator : function(value, param) {
				return value.length >= param[0];
			},message : 'Please enter at least {0} characters.'
		},
		equalTo : {
			validator : function(value, param) {
				if($(param[0]).size() > 0){
					return value == $(param[0]).val();	
				}				
				return true;				
			},message : 'Do not match the value.'
		},
		digits:{
			validator:function(value,param){
				return /^\d+$/.test(value);
			},message:'Please input the digits.'
		},
		number:{
			validator:function(value,param){
				return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
			},message:'Please input the numbers.'
		},
		ip:{
			validator:function(value,param){
				return /^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/.test(value);
			},message:'Please input the IP.'
		}
	});
})(jQuery);

