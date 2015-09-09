package com.hxb.common.threadPool;

import com.hxb.common.imp.threadPool.ThreadPoolImp;

public class ACThreadPoolSupport {
	public ACThreadPool newThreadPool(String threadPoolName, int maxThreadNum,
			int minThreadNum, long freeTimeout, long busyTimeout) {
		return new ThreadPoolImp(threadPoolName, maxThreadNum, minThreadNum,
				freeTimeout, busyTimeout);
	}

	public void SetThreadJob(ACThreadPool pool, ACThreadJob job)
			throws Exception {
		pool.SetThreadJob(job);
	}
}