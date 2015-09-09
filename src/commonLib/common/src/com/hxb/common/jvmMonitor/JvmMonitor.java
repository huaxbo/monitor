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
			log.error("����" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JvmMonitor.printMemoryMXBean();
	}

	/**
	 * MemoryMXBean��Java��������ڴ�ϵͳ�Ĺ���ӿ�
	 */
	public static void printMemoryMXBean() {
		// ��õ�һʵ��
		MemoryMXBean instance = ManagementFactory.getMemoryMXBean();
		System.out.printf("%n--%s--%n", instance.getClass().getName());
		// �������ڶ������Ķѵĵ�ǰ�ڴ�
//		�������ڶ������Ķѵĵ�ǰ�ڴ�ʹ����������һ�������ڴ����ɡ�
//		���ص��ڴ�ʹ�����е���ʹ�ô�С�����ύ��СΪ���ж��ڴ�صĶ�Ӧֵ���ܺͣ�
//		�����ص��ڴ�ʹ�����б�ʾ���ڴ����õĳ�ʼ��С������С����ܲ��������ж��ڴ�ض�Ӧֵ���ܺ͡� 
//		���ص��ڴ�ʹ��������ʹ���ڴ���Ϊ��������δ���յ�������������У���ռ���ڴ�������� 


		System.out.printf("%s:%s%n", "HeapMemoryUsage", instance
				.getHeapMemoryUsage());
		// ����java�����ʹ�õķǶ��ڴ�ĵ�ǰʹ����
		System.out.printf("%s:%s%n", "getNonHeapMemoryUsage", instance
				.getNonHeapMemoryUsage());
		instance.gc();
	}

	/**
	 * ClassLoadingMXBean��Java������������ϵͳ�Ĺ���ӿ� Java��������д˽ӿڵ�ʵ����ĵ���ʵ��
	 */
	public static void printClassLoadingMXBean() {
		// ��õ�һʵ��
		ClassLoadingMXBean instance = ManagementFactory.getClassLoadingMXBean();
		System.out.printf("%n--%s--%n", instance.getClass().getName());
		// ���ص�ǰ����Java������е��������
		System.out.printf("%s:%s%n", "LoadedClassCount", instance
				.getLoadedClassCount());
		// ������Java�������ʼִ�е�Ŀǰ�Ѿ����ص��������
		System.out.printf("%s:%s%n", "TotalLoadedClassCount", instance
				.getTotalLoadedClassCount());
		// ������Java�������ʼִ�е�Ŀǰ�Ѿ�ж�ص��������
		System.out.printf("%s:%s%n", "UnloadedClassCount", instance
				.getUnloadedClassCount());
	}

	/**
	 * ThreadMXBean��Java������߳�ϵͳ�Ĺ���ӿ� Java��������д˽ӿڵ�ʵ����ĵ���ʵ��
	 */
	public static void printThreadMXBean() {
		// ��õ�һʵ��
		ThreadMXBean instance = ManagementFactory.getThreadMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// ���ػ�̵߳ĵ�ǰ��Ŀ�������ػ��̺߳ͷ��ػ��߳�
		System.out.printf("%s:%s%n", "ThreadCount", instance.getThreadCount());
		// ���ػ�߳�ID
		System.out.printf("%s:%n", "Thread IDs");
		long[] ids = instance.getAllThreadIds();
		for (long id : ids) {
			System.out.printf("%s; ", id);
		}
		System.out.printf("%n");
		// ���ػ�ػ��̵߳ĵ�ǰ��Ŀ
		System.out.printf("%s:%s%n", "DaemonThreadCount", instance
				.getDaemonThreadCount());
		// �����Դ�Java������������ֵ����������ֵ��̼߳���
		System.out.printf("%s:%s%n", "PeakThreadCount", instance
				.getPeakThreadCount());
		// ���ص�ǰ�̵߳���CPUʱ��
		System.out.printf("%s:%s%n", "CurrentThreadCpuTime", instance
				.getCurrentThreadCpuTime());
		// ���ص�ǰ�߳����û�ģʽ��ִ�е�CPU
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

//	�ӿ�ժҪ 
//	ClassLoadingMXBean ���� Java ������������ϵͳ�Ĺ���ӿڡ� 
//	CompilationMXBean ���� Java ������ı���ϵͳ�Ĺ���ӿڡ� 
//	GarbageCollectorMXBean ���� Java ��������������յĹ���ӿڡ� 
//	MemoryManagerMXBean �ڴ�������Ĺ���ӿڡ� 
//	MemoryMXBean Java ������ڴ�ϵͳ�Ĺ���ӿڡ� 
//	MemoryPoolMXBean �ڴ�صĹ���ӿڡ� 
//	OperatingSystemMXBean ���ڲ���ϵͳ�Ĺ���ӿڣ�Java ������ڴ˲���ϵͳ�����С� 
//	RuntimeMXBean Java �����������ʱϵͳ�Ĺ���ӿڡ� 
//	ThreadMXBean Java ������߳�ϵͳ�Ĺ���ӿڡ� 
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
