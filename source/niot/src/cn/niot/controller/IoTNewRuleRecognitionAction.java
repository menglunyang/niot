package cn.niot.controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
* @Title: RespRul.java 
* @Package cn.niot.zt 
* @Description:前后台数据传递用例 
* @author Zhang Tao
* @date 2013-12-16 下午 
* @version V1.0
 */

public class IoTNewRuleRecognitionAction extends ActionSupport {
	
	 /**
	  * 用户从前台传递来的编码长度
	  */
	private String len;
	 /**
	  * 用户从前台传递来的各位取值范围
	  */
	private String valueRange;
	
	 /**
	  * 必填。
	  * 服务器返回给前台的查询状态，
	  * 允许的取值分别为：“0”，“1”，“大于1的整数”，“error”
	  */
	private String status;
	
	 /**
	  * 当status取值为“1”或者大于“1”的整数时，必填。
	  * 服务器返回给前台的编码信息，
	  * 当status取值为“1”时，data存储查询到的编码名称，例如data="CPC",
	  * 当status取值为大于1的整数时，data存储编码名称以及定义概率，
	  * 例如data = "[{codeName:'cpc',probability:0.12},{codeName:'eCode',probability:0.88}]";
	  */
	private String data;
	
	 /**
	  * 当status=="error"时，必填。
	  * 服务器返回给前台的错误信息，
	  * 当status=="error"时，将错误信息赋值给statement，
	  * 例如statement=="服务器响应超时"，之后传递给前台
	  */
	private String statement;
	
	public String getData() {
		return data;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getStatement() {
		return statement;
	}
	
	public void setLen(String len)
	{
		this.len = len;
	}
	
	public void setValueRange(String valueRange)
	{
		this.valueRange = valueRange;
	}
	
	public String execute() throws Exception
	{
		System.out.println(this.len+"!!!!!"+this.valueRange);
//		
//		逻辑处理。。。。
//		逻辑处理。。。。
//		逻辑处理。。。。
//		逻辑处理。。。。
//		逻辑处理。。。。
//		逻辑处理。。。。
//		
		//this.status = "7";
		//this.data = "[{codeName:'cpc',CollisionRatio:0.12},{codeName:'eCode',CollisionRatio:0.88},{codeName:'fCode',CollisionRatio:0.80},{codeName:'mFS',CollisionRatio:0.48},{codeName:'pdAF',CollisionRatio:0.18},{codeName:'Fnme',CollisionRatio:0.88},{codeName:'qqrf',CollisionRatio:0.56}]";
		
		this.status = "1";
		this.data = "{codeName:'cpc',CollisionRatio:0.12}";
		
		//this.status = "error";
		//this.statement = "服务器响应时间超时";
		
		//this.status = "0";
		
		
		System.out.println(this.status+this.data+this.statement);
		return SUCCESS;
	}

}
