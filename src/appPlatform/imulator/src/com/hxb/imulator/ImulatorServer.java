package com.hxb.imulator;


import java.util.Calendar;

import com.hxb.common.timerTask2.TimerDispatcher;
import com.hxb.common.timerTask2.inter.TaskImpl;
import com.hxb.imulator.client.TcpClient;
import com.hxb.imulator.client.TcpHandler;
import com.hxb.imulator.config.Centers;
import com.hxb.imulator.config.Meters;
import com.hxb.imulator.config.Params;
import com.hxb.imulator.protocol.ProtocolDriver;
import com.hxb.util.DateTime;

public class ImulatorServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[][] meters = Meters.getAll();		
		for(String[] meter:meters){			
			MeterTask mt = new MeterTask(meter);
			mt.start();
		}		
	}
}

/**
 * @author Administrator
 * ����豸����
 */
class MeterTask extends Thread{
	
	private String[] meter;
	private TcpClient tc;
	private ProtocolDriver pd;
	
	
	public MeterTask(String[] meter){
		
		this.meter = meter;			
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//����Զ������
		String centerId = meter[2];
		String[] center = Centers.getCenter(centerId);
		try {
			tc = new TcpClient(center[0],Integer.parseInt(center[1]),TcpHandler.getSingleOne(meter));
			//��������ע�ᱨ
			pd = (ProtocolDriver)Class.forName(meter[1]).newInstance();
			byte[] bts = pd.createRegister(meter[0]);
			tc.send(bts);
			
			//��������ά������
			new MeterHeart().start();		
			
			//������ʱ������
			TimerDispatcher td = TimerDispatcher.getOne();
			if(td.canDispatcher()){
				td.startDispatcher(DateTime.dateFrom_yyyy_MM_dd_HH(DateTime.yyyy_MM_dd_HH()),
						Calendar.MINUTE, 60, new TaskImpl() {
							@Override
							public void work() {
								// TODO Auto-generated method stub
								try {
									tc.send(pd.createReport(meter[0]));
								} catch (Exception e) {
									// TODO Auto-generated catch block
								}finally{}
							}
						});
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
				
	}	
	
	/**
	 * @author Administrator
	 * �豸��������
	 */
	class MeterHeart extends Thread{
		
		/**
		 * @param tc
		 */
		public MeterHeart(){
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try {
					sleep(Params.heart_interval * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{}
				
				//��������
				if(tc != null){
					try {
						ProtocolDriver pd = (ProtocolDriver)Class.forName(meter[1]).newInstance();
						byte[] bts = pd.createHeart(meter[0]);
						tc.send(bts);					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{}
				}
			}
		}		
	}
	
}
