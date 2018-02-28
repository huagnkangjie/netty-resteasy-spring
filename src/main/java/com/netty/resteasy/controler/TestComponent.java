package com.netty.resteasy.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月27日
 * @version v1.0.
 * 
 */
@Component
public class TestComponent {

	
	@Autowired
	ApplicationContext	ac;
	
	public void print(){
		System.out.println("this is a test @Component ApplicationContext :" + ac.getId());
	}
}
