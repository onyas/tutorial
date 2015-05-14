package com.onyas.mina.filetransferused.service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ThreadPoolService {
	private static Logger logger = Logger.getLogger(ThreadPoolService.class);
	private ThreadPoolExecutor threadPool;
	private static long excuteTimes=0;
	private static int activeQueueSize = 50;
	
	public void init(){
		int poolSize = 50;
		threadPool = new ThreadPoolExecutor(
				poolSize, poolSize, 3, TimeUnit.SECONDS,
				//缓冲队列大小   
		        new LinkedBlockingQueue(),
		        //抛弃旧的任务   
		        new ThreadPoolExecutor.DiscardPolicy());
		logger.info(" threadPool 线程池 启动 poolSize"+poolSize);
	}
	
	
	public void execute(Runnable task){
		//TODO 执行任务
	}
}
