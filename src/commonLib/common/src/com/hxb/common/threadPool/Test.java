package com.hxb.common.threadPool;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ACThreadPoolSupport tps =  new ACThreadPoolSupport();
		ACThreadPool tp = tps.newThreadPool("testPool", 1, 5, 1, 3);
		try {
						
			tps.SetThreadJob(tp,new ACThreadJob() {
				@Override
				public void execute() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("0::" + System.currentTimeMillis());
					Thread.sleep(10*1000);
				}
			});
			
			tps.SetThreadJob(tp,new ACThreadJob() {
				@Override
				public void execute() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("1::" + System.currentTimeMillis());
					Thread.sleep(1*1000);
				}
			});
			tps.SetThreadJob(tp,new ACThreadJob() {
				@Override
				public void execute() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("2::" + System.currentTimeMillis());
					Thread.sleep(1*1000);
				}
			});
			tps.SetThreadJob(tp,new ACThreadJob() {
				@Override
				public void execute() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("3::" + System.currentTimeMillis());
					Thread.sleep(10*1000);
				}
			});
			tps.SetThreadJob(tp,new ACThreadJob() {
				@Override
				public void execute() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("4::" + System.currentTimeMillis());
					Thread.sleep(10*1000);
				}
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
		
	}

}
