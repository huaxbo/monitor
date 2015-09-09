package com.hxb.common.imp.threadPool;

import com.hxb.common.threadPool.ACThreadJob;
import com.hxb.common.threadPool.ACThreadPool;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class ThreadPoolImp implements ACThreadPool {
	private String threadPoolName;
	private Thread monitorThread;
	private ArrayList<Threader> freeThreads;
	private ArrayList<Threader> busiThreads;
	private int maxThreadNum;
	private int minThreadNum;
	private int currThreadNum;
	private long freeTimeout;
	private long busyTimeout;
	private Object synObj = new Object();

	private Logger log = Logger.getLogger(MonitorThread.class.getName());

	public ThreadPoolImp(String threadPoolName, int maxThreadNum,
			int minThreadNum, long freeTimeout, long busyTimeout) {
		if (threadPoolName == null) {
			threadPoolName = "AC工作线程";
		}
		this.threadPoolName = threadPoolName;

		if (maxThreadNum <= 0) {
			maxThreadNum = 1;
		}
		if (minThreadNum > maxThreadNum) {
			minThreadNum = maxThreadNum;
		}
		this.maxThreadNum = maxThreadNum;
		this.minThreadNum = minThreadNum;
		this.currThreadNum = 0;

		this.freeTimeout = (freeTimeout * 1000L);
		this.busyTimeout = (busyTimeout * 1000L);

		this.busiThreads = new ArrayList();
		this.freeThreads = new ArrayList();

		for (int i = 0; i < this.minThreadNum; i++) {
			Threader t = new Threader(this.threadPoolName, this);
			t.start();
			this.freeThreads.add(t);
			this.currThreadNum += 1;
		}

		this.monitorThread = new MonitorThread(this);
		this.monitorThread.start();
	}

	public void SetThreadJob(ACThreadJob job) throws Exception {
		synchronized (this.synObj) {
			this.log.info("新工作到达服务(ID=" + this.threadPoolName + ")的线程池中。");
			Threader t = null;
			if (this.freeThreads.size() == 0) {
				if (this.currThreadNum < this.maxThreadNum) {
					t = new Threader(this.threadPoolName, this);
					t.start();
					this.currThreadNum += 1;
				} else {
					while (this.freeThreads.size() == 0) {
						try {
							this.log.warn("'" + this.threadPoolName
									+ "'线程池中线程数达到上限，新工作任务等待释放线程!");
							this.synObj.wait();
						} catch (Exception e) {
							this.log.error("'" + this.threadPoolName
									+ "'线程池中线程等待释放线时发生等待异常!", e);
						}
						t = (Threader) this.freeThreads.get(0);
						if (t == null)
							continue;
						this.freeThreads.remove(0);
						break;
					}

				}

			} else {
				t = (Threader) this.freeThreads.get(0);
				this.freeThreads.remove(0);
			}
			this.busiThreads.add(t);
			t.setJob(job);
		}
	}

	protected void freeThread(Threader t) {
		synchronized (this.synObj) {
			this.busiThreads.remove(t);
			this.freeThreads.add(t);
			this.synObj.notify();
		}
	}

	protected class MonitorThread extends Thread {
		private ThreadPoolImp pool;
		private Threader t;
		private Iterator it;

		public MonitorThread(ThreadPoolImp pool) {
			this.pool = pool;
		}

		// ERROR //
		public void run() {
			// Byte code:
			// 0: aload_0
			// 1: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 4: invokestatic 27
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$0
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)J
			// 7: lstore_1
			// 8: aload_0
			// 9: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 12: invokestatic 33
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$1
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)J
			// 15: aload_0
			// 16: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 19: invokestatic 27
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$0
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)J
			// 22: lcmp
			// 23: ifge +11 -> 34
			// 26: aload_0
			// 27: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 30: invokestatic 33
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$1
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)J
			// 33: lstore_1
			// 34: iconst_0
			// 35: istore_3
			// 36: aload_0
			// 37: aconst_null
			// 38: putfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 41: aload_0
			// 42: aconst_null
			// 43: putfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 46: lload_1
			// 47: invokestatic 40
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:sleep
			// (J)V
			// 50: goto +24 -> 74
			// 53: astore 4
			// 55: iconst_1
			// 56: istore_3
			// 57: goto +29 -> 86
			// 60: astore 5
			// 62: iload_3
			// 63: ifeq +8 -> 71
			// 66: iconst_0
			// 67: istore_3
			// 68: goto -32 -> 36
			// 71: aload 5
			// 73: athrow
			// 74: iload_3
			// 75: ifeq +20 -> 95
			// 78: iconst_0
			// 79: istore_3
			// 80: goto -44 -> 36
			// 83: goto +12 -> 95
			// 86: iload_3
			// 87: ifeq +8 -> 95
			// 90: iconst_0
			// 91: istore_3
			// 92: goto -56 -> 36
			// 95: aload_0
			// 96: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 99: invokestatic 44
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$2
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Ljava/lang/Object;
			// 102: dup
			// 103: astore 4
			// 105: monitorenter
			// 106: aload_0
			// 107: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 110: invokestatic 48
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$3
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Ljava/util/ArrayList;
			// 113: invokevirtual 52 java/util/ArrayList:size ()I
			// 116: aload_0
			// 117: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 120: invokestatic 58
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$4
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)I
			// 123: if_icmple +177 -> 300
			// 126: aload_0
			// 127: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 130: invokestatic 48
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$3
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Ljava/util/ArrayList;
			// 133: invokevirtual 52 java/util/ArrayList:size ()I
			// 136: aload_0
			// 137: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 140: invokestatic 58
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$4
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)I
			// 143: isub
			// 144: istore 5
			// 146: iconst_0
			// 147: istore 6
			// 149: aload_0
			// 150: aload_0
			// 151: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 154: invokestatic 48
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$3
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Ljava/util/ArrayList;
			// 157: invokevirtual 62 java/util/ArrayList:iterator
			// ()Ljava/util/Iterator;
			// 160: putfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 163: goto +125 -> 288
			// 166: iload 6
			// 168: iload 5
			// 170: if_icmpne +6 -> 176
			// 173: goto +127 -> 300
			// 176: iinc 6 1
			// 179: aload_0
			// 180: aload_0
			// 181: getfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 184: invokeinterface 66 1 0
			// 189: checkcast 72 com/automic/common/imp/threadPool/Threader
			// 192: putfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 195: invokestatic 74 java/lang/System:currentTimeMillis ()J
			// 198: aload_0
			// 199: getfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 202: getfield 80 com/automic/common/imp/threadPool/Threader:time
			// J
			// 205: lsub
			// 206: aload_0
			// 207: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 210: invokestatic 27
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$0
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)J
			// 213: lcmp
			// 214: iflt +74 -> 288
			// 217: aload_0
			// 218: getfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 221: invokeinterface 84 1 0
			// 226: aload_0
			// 227: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 230: dup
			// 231: invokestatic 87
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$5
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)I
			// 234: iconst_1
			// 235: isub
			// 236: invokestatic 90
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$6
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;I)V
			// 239: aload_0
			// 240: getfield 15
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:this$0
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 243: invokestatic 94
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$7
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Lorg/apache/log4j/Logger;
			// 246: new 98 java/lang/StringBuilder
			// 249: dup
			// 250: ldc 100
			// 252: invokespecial 102 java/lang/StringBuilder:<init>
			// (Ljava/lang/String;)V
			// 255: aload_0
			// 256: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 259: invokestatic 105
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$8
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Ljava/lang/String;
			// 262: invokevirtual 109 java/lang/StringBuilder:append
			// (Ljava/lang/String;)Ljava/lang/StringBuilder;
			// 265: ldc 113
			// 267: invokevirtual 109 java/lang/StringBuilder:append
			// (Ljava/lang/String;)Ljava/lang/StringBuilder;
			// 270: invokevirtual 115 java/lang/StringBuilder:toString
			// ()Ljava/lang/String;
			// 273: invokevirtual 119 org/apache/log4j/Logger:info
			// (Ljava/lang/Object;)V
			// 276: aload_0
			// 277: getfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 280: invokevirtual 125
			// com/automic/common/imp/threadPool/Threader:destroy ()V
			// 283: aload_0
			// 284: aconst_null
			// 285: putfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 288: aload_0
			// 289: getfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 292: invokeinterface 128 1 0
			// 297: ifne -131 -> 166
			// 300: aload_0
			// 301: aload_0
			// 302: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 305: invokestatic 132
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$9
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Ljava/util/ArrayList;
			// 308: invokevirtual 62 java/util/ArrayList:iterator
			// ()Ljava/util/Iterator;
			// 311: putfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 314: goto +112 -> 426
			// 317: aload_0
			// 318: aload_0
			// 319: getfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 322: invokeinterface 66 1 0
			// 327: checkcast 72 com/automic/common/imp/threadPool/Threader
			// 330: putfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 333: invokestatic 74 java/lang/System:currentTimeMillis ()J
			// 336: aload_0
			// 337: getfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 340: getfield 80 com/automic/common/imp/threadPool/Threader:time
			// J
			// 343: lsub
			// 344: aload_0
			// 345: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 348: invokestatic 33
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$1
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)J
			// 351: lcmp
			// 352: iflt +74 -> 426
			// 355: aload_0
			// 356: getfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 359: invokeinterface 84 1 0
			// 364: aload_0
			// 365: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 368: dup
			// 369: invokestatic 87
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$5
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)I
			// 372: iconst_1
			// 373: isub
			// 374: invokestatic 90
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$6
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;I)V
			// 377: aload_0
			// 378: getfield 15
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:this$0
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 381: invokestatic 94
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$7
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Lorg/apache/log4j/Logger;
			// 384: new 98 java/lang/StringBuilder
			// 387: dup
			// 388: ldc 100
			// 390: invokespecial 102 java/lang/StringBuilder:<init>
			// (Ljava/lang/String;)V
			// 393: aload_0
			// 394: getfield 20
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:pool
			// Lcom/automic/common/imp/threadPool/ThreadPoolImp;
			// 397: invokestatic 105
			// com/automic/common/imp/threadPool/ThreadPoolImp:access$8
			// (Lcom/automic/common/imp/threadPool/ThreadPoolImp;)Ljava/lang/String;
			// 400: invokevirtual 109 java/lang/StringBuilder:append
			// (Ljava/lang/String;)Ljava/lang/StringBuilder;
			// 403: ldc 135
			// 405: invokevirtual 109 java/lang/StringBuilder:append
			// (Ljava/lang/String;)Ljava/lang/StringBuilder;
			// 408: invokevirtual 115 java/lang/StringBuilder:toString
			// ()Ljava/lang/String;
			// 411: invokevirtual 137 org/apache/log4j/Logger:warn
			// (Ljava/lang/Object;)V
			// 414: aload_0
			// 415: getfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 418: invokevirtual 125
			// com/automic/common/imp/threadPool/Threader:destroy ()V
			// 421: aload_0
			// 422: aconst_null
			// 423: putfield 36
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:t
			// Lcom/automic/common/imp/threadPool/Threader;
			// 426: aload_0
			// 427: getfield 38
			// com/automic/common/imp/threadPool/ThreadPoolImp$MonitorThread:it
			// Ljava/util/Iterator;
			// 430: invokeinterface 128 1 0
			// 435: ifne -118 -> 317
			// 438: aload 4
			// 440: monitorexit
			// 441: goto -405 -> 36
			// 444: aload 4
			// 446: monitorexit
			// 447: athrow
			// 448: goto -412 -> 36
			// 451: astore 4
			// 453: goto -417 -> 36
			// 456: pop
			// 457: goto -421 -> 36
			//
			// Exception table:
			// from to target type
			// 46 50 53 java/lang/Exception
			// 46 60 60 finally
			// 106 441 444 finally
			// 444 447 444 finally
			// 95 448 451 java/lang/Exception
			// 95 456 456 finally
		}
	}
}