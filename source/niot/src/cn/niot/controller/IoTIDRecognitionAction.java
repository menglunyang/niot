package cn.niot.controller;

import cn.unitTest.RuleFuncTest;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
* @Title: RespCode.java 
* @Package cn.niot.zt 
* @Description:前后台数据传递用例 
* @author Zhang Tao
* @date 2013-12-3 上午 
* @version V1.0
 */

public class IoTIDRecognitionAction extends ActionSupport {
	
	 /**
	  * 用户从前台传递来的要查询的编码
	  */
	private String code;
	
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
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String execute() throws Exception
	{
		System.out.println(this.code);
//		
//		逻辑处理。。。。
//		逻辑处理。。。。
//		逻辑处理。。。。
//		逻辑处理。。。。
//		逻辑处理。。。。
//		逻辑处理。。。。
//		
		//this.status = "2";
		//this.data = "[{codeName:'cpc',probability:0.12},{codeName:'eCode',probability:0.88}]";
		
		//this.status="5";
		//this.data = "[{'codeName':'GB/T 19251-2003_EANUCC-8','probability':'0.2'},{'codeName':'GB/T 19251-2003_UCC-12','probability':'0.2'},{'codeName':'Ecode_1','probability':'0.2'},{'codeName':'GB/T 19251-2003_EANUCC-14','probability':'0.2'},{'codeName':'GB/T 19251-2003_EANUCC-13','probability':'0.2'}]";
		

		
		
		this.status = "1";
		this.data = "CPC";
		
		//this.status = "error";
		//this.statement = "服务器响应时间超时";
		
		//this.status = "0";
		
		//System.out.println(this.status+this.data+this.statement);
		
		
		return SUCCESS;
	}

}
