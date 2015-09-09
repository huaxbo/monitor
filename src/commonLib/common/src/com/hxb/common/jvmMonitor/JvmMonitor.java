package com.hxb.common.jvmMonitor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.*;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hxb.ACException;
import com.hxb.common.imp.timerTask.TaskJob;
import com.hxb.common.timerTask.*;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

public class JvmMonitor extends TaskJob implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			new ACSchedularJob()
					.addSecondlyJob("jvmMonitorJob", "jvmMonitorJobGroup",
							JvmMonitor.class, null, 30000, 10, -1);
		} catch (ACException e) {
			Logger log = Logger.getLogger(JvmMonitor.class.getName());
			log.error("出错" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JvmMonitor.printMemoryMXBean();
	}

	/**
	 * MemoryMXBean是Java虚拟机的内存系统的管理接口
	 */
	public static void printMemoryMXBean() {
		// 获得单一实例
		MemoryMXBean instance = ManagementFactory.getMemoryMXBean();
		System.out.printf("%n--%s--%n", instance.getClass().getName());
		// 返回用于对象分配的堆的当前内存
//		返回用于对象分配的堆的当前内存使用量。堆由一个或多个内存池组成。
//		返回的内存使用量中的已使用大小和已提交大小为所有堆内存池的对应值的总和，
//		而返回的内存使用量中表示堆内存设置的初始大小和最大大小则可能不等于所有堆内存池对应值的总和。 
//		返回的内存使用量中已使用内存量为活动对象和尚未回收的垃圾对象（如果有）所占用内存的总量。 


		System.out.printf("%s:%s%n", "HeapMemoryUsage", instance
				.getHeapMemoryUsage());
		// 返回java虚拟机使用的非堆内存的当前使用量
		System.out.printf("%s:%s%n", "getNonHeapMemoryUsage", instance
				.getNonHeapMemoryUsage());
		instance.gc();
	}

	/**
	 * ClassLoadingMXBean是Java虚拟机的类加载系统的管理接口 Java虚拟机具有此接口的实现类的单个实例
	 */
	public static void printClassLoadingMXBean() {
		// 获得单一实例
		ClassLoadingMXBean instance = ManagementFactory.getClassLoadingMXBean();
		System.out.printf("%n--%s--%n", instance.getClass().getName());
		// 返回当前加载Java虚拟机中的类的数量
		System.out.printf("%s:%s%n", "LoadedClassCount", instance
				.getLoadedClassCount());
		// 返回自Java虚拟机开始执行到目前已经加载的类的总数
		System.out.printf("%s:%s%n", "TotalLoadedClassCount", instance
				.getTotalLoadedClassCount());
		// 返回自Java虚拟机开始执行到目前已经卸载的类的总数
		System.out.printf("%s:%s%n", "UnloadedClassCount", instance
				.getUnloadedClassCount());
	}

	/**
	 * ThreadMXBean是Java虚拟机线程系统的管理接口 Java虚拟机具有此接口的实现类的单个实例
	 */
	public static void printThreadMXBean() {
		// 获得单一实例
		ThreadMXBean instance = ManagementFactory.getThreadMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// 返回活动线程的当前数目，包括守护线程和非守护线程
		System.out.printf("%s:%s%n", "ThreadCount", instance.getThreadCount());
		// 返回活动线程ID
		System.out.printf("%s:%n", "Thread IDs");
		long[] ids = instance.getAllThreadIds();
		for (long id : ids) {
			System.out.printf("%s; ", id);
		}
		System.out.printf("%n");
		// 返回活动守护线程的当前数目
		System.out.printf("%s:%s%n", "DaemonThreadCount", instance
				.getDaemonThreadCount());
		// 返回自从Java虚拟机启动或峰值重置以来峰值活动线程计数
		System.out.printf("%s:%s%n", "PeakThreadCount", instance
				.getPeakThreadCount());
		// 返回当前线程的总CPU时间
		System.out.printf("%s:%s%n", "CurrentThreadCpuTime", instance
				.getCurrentThreadCpuTime());
		// 返回当前线程在用户模式中执行的CPU
		System.out.printf("%s:%s%n", "CurrentThreadUserTime", instance
				.getCurrentThreadUserTime());

	}

	public static void printRuntimeMXBean() {
		RuntimeMXBean instance = ManagementFactory.getRuntimeMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		System.out.printf("%s:%s%n", "BootClassPath", instance
				.getBootClassPath());
		System.out.printf("%s:%s%n", "ClassPath", instance.getClassPath());
		System.out.printf("%s%n", "InputArguments");
		List<String> args = instance.getInputArguments();
		for (String arg : args) {
			System.out.printf("%s; ", arg);
		}
		System.out.printf("%s:%s%n", "LibraryPath", instance.getLibraryPath());
		System.out.printf("%s:%s%n", "ManagementSpecVersion", instance
				.getManagementSpecVersion());
		System.out.printf("%s:%s%n", "Name", instance.getName());
		System.out.printf("%s:%s%n", "SpecName", instance.getSpecName());
		System.out.printf("%s:%s%n", "SpecVersion", instance.getSpecVersion());
		System.out.printf("%s:%s%n", "VmlName", instance.getVmName());
		System.out.printf("%s:%s%n", "VmVendor", instance.getVmVendor());
		System.out.printf("%s:%s%n", "VmVersion", instance.getVmVersion());
		System.out.printf("%s:%s%n", "StartTime", instance.getStartTime());
		System.out.printf("%s:%s%n", "Uptime", instance.getUptime());
	}

	public static void printCompilationMXBean() {
		CompilationMXBean instance = ManagementFactory.getCompilationMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		System.out.printf("%s:%s%n", "Name", instance.getName());
		System.out.printf("%s:%s%n", "TotalCompilationTime", instance
				.getTotalCompilationTime());
	}

	public static void printGarbageCollectionMXBean() {
		List<GarbageCollectorMXBean> instances = ManagementFactory
				.getGarbageCollectorMXBeans();
		System.out.printf("%n---%s---%n", GarbageCollectorMXBean.class
				.getName());
		for (GarbageCollectorMXBean instance : instances) {
			System.out.printf("***%s:%s***%n", "Name", instance.getName());
			System.out.printf("%s:%s%n", "CollectionCount", instance
					.getCollectionCount());
			System.out.printf("%s:%s%n", "CollectionTime", instance
					.getCollectionTime());
		}

	}

	public static void printOperationSystemMXBean() {
		OperatingSystemMXBean instance = ManagementFactory
				.getOperatingSystemMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		System.out.printf("%s:%s%n", "Arch", instance.getArch());
		System.out.printf("%s:%s%n", "AvailableProcessors", instance
				.getAvailableProcessors());
		System.out.printf("%s:%s%n", "Name", instance.getName());
		System.out.printf("%s:%s%n", "Version", instance.getVersion());
	}

//	接口摘要 
//	ClassLoadingMXBean 用于 Java 虚拟机的类加载系统的管理接口。 
//	CompilationMXBean 用于 Java 虚拟机的编译系统的管理接口。 
//	GarbageCollectorMXBean 用于 Java 虚拟机的垃圾回收的管理接口。 
//	MemoryManagerMXBean 内存管理器的管理接口。 
//	MemoryMXBean Java 虚拟机内存系统的管理接口。 
//	MemoryPoolMXBean 内存池的管理接口。 
//	OperatingSystemMXBean 用于操作系统的管理接口，Java 虚拟机在此操作系统上运行。 
//	RuntimeMXBean Java 虚拟机的运行时系统的管理接口。 
//	ThreadMXBean Java 虚拟机线程系统的管理接口。 
	public static void main(String[] args) {
		JvmMonitor.printOperationSystemMXBean();
		JvmMonitor.printRuntimeMXBean();
		JvmMonitor.printClassLoadingMXBean();
		JvmMonitor.printCompilationMXBean();
		JvmMonitor.printMemoryMXBean();
		JvmMonitor.printThreadMXBean();
		JvmMonitor.printGarbageCollectionMXBean();
	}

}
