package com.hxb.monitor.commu.autoTask.autoRlt;

import java.util.List;

import org.apache.log4j.Logger;

import com.hxb.commu.remote.protocol.ProtocolData;
import com.hxb.global.po.TbCsTempAuto;
import com.hxb.global.po.TbCsTempFailed;
import com.hxb.global.service.util.SpringContextUtil;
import com.hxb.global.util.SerializeUtil;
import com.hxb.monitor.service.temp.TempAllBusi;
import com.hxb.monitor.util.MonitorConstant;

public class AutoRltTask extends Thread {

	private Logger log = Logger.getLogger(AutoRltTask.class);
	private static AutoRltTask task;//single instance
	private TempAllBusi busi;
	/**
	 * 
	 */
	private AutoRltTask(){}
	
	public static AutoRltTask getSingleOne(){
		if(task == null){
			task = new AutoRltTask();
			task.busi = (TempAllBusi)SpringContextUtil.getSpringBean("tempAllBusi");
		}
		
		return task;
	}
	
	/* 
	 * task execute
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){			
			if(busi != null){
				log.info("===��ʼ���������ϱ��������");
				List<Object> autoRlts =	busi.getSome(TbCsTempAuto.class);
				while(autoRlts.size()>0){
					String[] ids = new String[autoRlts.size()];
					//��¼����
					for(int i=0;i<autoRlts.size();i++){
						TbCsTempAuto rlt = (TbCsTempAuto)autoRlts.get(i);
						ids[i] = rlt.getId();
						Object arlt = SerializeUtil.getInstance().xml2Obj(rlt.getData(),
								new Class[]{ProtocolData.class});
						//�����ϱ������⴦��
						try{
							AutoRltHandler.getSingleOne().execute(arlt);
						}catch(Exception e){//���ֽ������ʧ�ܼ�¼
							busi.saveFailed(new TbCsTempFailed(null,rlt.getData(), rlt.getTs()));
						}finally{}
					}					
					//ɾ���Ѵ����¼
					busi.delSome(TbCsTempAuto.class, ids);
					//����Ƿ��д������¼
					try {
						sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					autoRlts = null;//clean autoRlts
					autoRlts = busi.getSome(TbCsTempAuto.class);
				}
				log.info("===�������������ϱ��������");
					try {
						log.info("===���������ϱ��������ȴ�...");
						sleep(MonitorConstant.max_scan_autoResult * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{}
			}
		}
	}
}
