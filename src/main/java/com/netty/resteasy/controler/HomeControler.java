package com.netty.resteasy.controler;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月27日
 * @version v1.0.
 * 
 */
@Path("/home")
@Consumes(value = {"application/json;charset=UTF-8"})
@Produces(value = {"application/json;charset=UTF-8"})
@Controller
public class HomeControler {
	
	private static Logger logger = LoggerFactory.getLogger(HomeControler.class);
	
	@Autowired
	ApplicationContext	ac;

	 @POST
	 @Path(value = "/test")
	 public String test(){
		 
		 logger.debug("spring  :" + ac);
		 
		 logger.debug("excute a netty test");
		 
		 return "{\"msg\":\"this is a netty test\"}";
		 
	 }
}
