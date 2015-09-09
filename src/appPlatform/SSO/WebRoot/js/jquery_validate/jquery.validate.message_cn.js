jQuery.extend(jQuery.validator.messages, {
	required: "必须填写",
	remote: "请修正该字段",
	email: "非法电子邮件",
	url: "非法网址",
	date: "非法日期",
	dateISO: "非法日期 (ISO).",
	number: "只能输入数字",
	digits: "只能输入整数",
	creditcard: "非法信用卡号",
	equalTo: "输入内容不一致",
	accept: "非法后缀",
	maxlength: jQuery.validator.format("长度超过{0}"),
	minlength: jQuery.validator.format("长度不足{0}"),
	rangelength: jQuery.validator.format("长度介于{0}和{1}之间"),
	range: jQuery.validator.format("范围介于{0}和{1}之间"),
	max: jQuery.validator.format("大于{0}"),
	min: jQuery.validator.format("小于{0}")
}); 