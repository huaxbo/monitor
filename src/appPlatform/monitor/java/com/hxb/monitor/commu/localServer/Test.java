package com.hxb.monitor.commu.localServer;

import java.util.Hashtable;

import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.local.common.LocalCode;
import com.hxb.commu.remote.protocol.ver_test.VerTestCode;
import com.hxb.commu.remote.protocol.ver_test.cd_11.Param_11;
import com.hxb.global.util.SerializeUtil;

public class Test {

	public static void main(String[] args){
		LocalServerFac.build("1", "127.0.0.1", 7080, "commu");
		
		Test lsf = new Test();
		
		for(int i=1;i<3;i++){
			TestTask t = lsf.new TestTask(i);
			t.start();
		}
	}
	
	public class TestTask extends Thread{
		int idx;
		public TestTask(int idx){
			this.idx = idx;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				if(idx == 1){
					CommandAttach ca1 = new CommandAttach();
					ca1.setFuncCode(LocalCode.local_onLines);
					String rlt1 = LocalServerManager.sendCommand("1", ca1);					
					System.out.println(rlt1);
					
					CommandAttach ca = (CommandAttach)SerializeUtil.getInstance().xml2Obj(rlt1, new Class[]{CommandAttach.class});
					String[] arr = (String[])ca.getReceiptObj();
					
					System.out.println(arr.length);
				}else{									
					CommandAttach ca = new CommandAttach();		
	
					ca.setMeterId("100191000" + idx);
					
					ca.setFuncCode(VerTestCode.cd_11);
					ca.setCommandId("11111" + idx);
					
					Param_11 p11 = new Param_11();
					p11.setParam1("p1" + idx);
					p11.setParam2("p2" + idx);
					Hashtable<String,Object> param = new Hashtable<String,Object>(0);
					param.put(Param_11.KEY, p11);
					ca.setSendParams(param);
					
					String rlt = LocalServerManager.sendCommand("1", ca);
					
					System.out.println(rlt);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{}
		}
		
		
	}

}
