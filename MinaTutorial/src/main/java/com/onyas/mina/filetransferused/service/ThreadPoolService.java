package com.onyas.mina.filetransferused.service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ThreadPoolService {
	private static Logger logger = Logger.getLogger(ThreadPoolService.class);
	private static ThreadPoolExecutor threadPool;
	private static long excuteTimes=0;
	private static int activeQueueSize = 50;
	private static ThreadPoolService threadPoolService;
	
	private ThreadPoolService(){}
	
	public static synchronized ThreadPoolService getInstance(){
		if(threadPoolService==null){
			threadPoolService = new ThreadPoolService();
			int poolSize = 50;
			threadPool = new ThreadPoolExecutor(
					poolSize, poolSize, 3, TimeUnit.SECONDS,
					//缓冲队列大小   
			        new LinkedBlockingQueue(),
			        //抛弃旧的任务   
			        new ThreadPoolExecutor.DiscardPolicy());
			logger.info(" threadPool 线程池 启动 poolSize"+poolSize);
		}
		return threadPoolService;
	}
	
	public void execute(Runnable task){
		if(threadPool.getQueue().size()<activeQueueSize){
			threadPool.execute(task);
		}else{
			logger.info(" threadPool  队列数超过"+activeQueueSize);
		}
	}
	
	public ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolExecutor threadPool) {
		this.threadPool = threadPool;
	}
}
