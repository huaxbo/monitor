package com.hxb.global.wsClient;

public class WsCommand {

	/**
	 * 报文1
	 * 完整预警记录报文，包括了行政区预警、测站预警和测站监测信息，具体包括表27、28、29、30、31的中的数据内容 
	 */
	public static final String ALLWarnRecordRepor = "ALLWarnRecordReport" ;

	/**
	 * 报文2
	 * 行政区预警信息报文，包括了表28、29的数据。
	 */
	public static final String ADWarnRecordRepor = "ADWarnRecordReport" ;
	
	/**
	 * 报文3
	 * 测站预警信息报文，包括了表27的数据。
	 */
	public static final String STCDWarnRecordRepor = "STCDWarnRecordReport" ;
	
	/**
	 * 报文4
	 * 测站监测信息报文，包括了表31的数据。
	 */
	public static final String STCDRealDataReport = "STCDRealDataReport" ;
	
	/**
	 * 报文5
	 * 预警消息报文，包括了表32的数据。
	 */
	public static final String MessageInforReport = "MessageInforReport" ;
	
	/**
	 * 报文6
	 * 响应报文，包括了表34、35的数据。
	 */
	public static final String ResponseReport = "ResponseReport" ;
	
	/**
	 * 报文7
	 * 响应反馈报文，包括了表36的数据。
	 */
	public static final String RespFeedBackReport = "RespFeedBackReport" ;
	
	/**
	 * 报文8
	 * 灾害统计报文，包括了表37的数据。
	 */
	public static final String DisasterStatisticsReport = "DisasterStatisticsReport" ;
	
	
}
