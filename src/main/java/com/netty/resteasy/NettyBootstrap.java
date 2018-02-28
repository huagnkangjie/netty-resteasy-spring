package com.netty.resteasy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.netty.resteasy.controler.HomeControler;
import com.netty.resteasy.controler.TestComponent;
import com.netty.resteasy.controler.TestControler;
import com.netty.resteasy.util.SpringContextUtil;

/**
 * @描述： 用于启动netty 服务，初始化resteasy的资源
 * 
 * spring的手动加载和注入，目前暂没对数据库操作，只是单纯的实现了下netty + resteasy的简单功能。
 * 
 * netty的核心功能强大的并发并未做测试
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月27日
 * @version v1.0.
 * 
 */
public class NettyBootstrap {
	
	private static Logger logger = LoggerFactory.getLogger(NettyBootstrap.class);

	private static int PORT = 6879;
	
	private static String URI = "/resteasy";
	
	//当前核心工作数（最好是当前运行环境可用进程数的2倍）
	private final static int ioWorkerCount	= Runtime.getRuntime().availableProcessors() * 2; 
	
	//核心的工作线程数
	private final static int executorThreadCount	= 16;
	
	
	public static void main(String[] args) {
		
		init();
		
		NettyJaxrsServer netty = new NettyJaxrsServer();
        netty.setPort(PORT);
//        netty.setDeployment(getResteasyDeployment()); //手动注入所有的rest资源
        netty.setDeployment(setAutotActualResourceClasses()); //自动注入所用的才@control
        netty.setIoWorkerCount(ioWorkerCount);
        netty.setExecutorThreadCount(executorThreadCount);
        netty.setRootResourcePath(URI);
        netty.start();
        
        logger.debug("netty server is started: {}", PORT);
        
	}
	
	public static void init(){
		
		try {
			/**
			 * 手动加载的形式
			 */
			ApplicationContext ac = new ClassPathXmlApplicationContext("root-context.xml");
			TestComponent test = ac.getBean(TestComponent.class);
			System.out.println(ac);
			test.print();
		} catch (Exception e) {
			logger.error("", e);
		}
		
		
		try {
			/**
			 * 通过配置文件的形式注入
			 */
			ApplicationContext acs = SpringContextUtil.getApplicationContext();
			TestControler test = acs.getBean(TestControler.class);
			test.print();
		} catch (Exception e) {
			logger.error("", e);
		}
		
		
		
	}
	
	public static ResteasyDeployment getResteasyDeployment(){
		ResteasyDeployment rdp = new  ResteasyDeployment();
		rdp.setActualResourceClasses(setActualResourceClasses());
		return rdp;
	}
	
	
	/**
	 * 
	 * 手动获取所有映射的资源
	 * @return
	 */
	private static List<Class> setActualResourceClasses() {
        final List<Class> actualResourceClasses = new ArrayList<>();
        actualResourceClasses.add(HomeControler.class);
        return actualResourceClasses;
    }
	
	
	/**
	 * 自动注入所有的controller
	 * @return
	 */
	public static ResteasyDeployment setAutotActualResourceClasses(){
		ResteasyDeployment rdp = new  ResteasyDeployment();
		ApplicationContext ac = SpringContextUtil.getApplicationContext();
		Collection<Object> controllers = ac.getBeansWithAnnotation(Controller.class).values();
		rdp.getResources().addAll(controllers);
		return rdp;
	}
	
	
}
