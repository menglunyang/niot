package cn.niot.controller;

import cn.unitTest.RuleFuncTest;

import com.opensymphony.xwork2.ActionSupport;



public class IoTIDRecognitionAction extends ActionSupport {
	

	private String code;
	

	private String status;
	

	private String data;
	private String extraData;

	private String statement;
	
	public String getData() {
		return data;
	}
	
	
	public String getExtraData() {
		return extraData;
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
	
		//this.status = "2";
		//this.data = "[{codeName:'cpc',probability:0.12},{codeName:'eCode',probability:0.88}]";
		
		this.status="5";
		//this.data = "[{'codeName':'GB/T 19251-2003_EANUCC-8asdfsa','probability':'0.2'},{'codeName':'GB/T 19251-2003_UCC-12','probability':'0.2'},{'codeName':'Ecode_1','probability':'0.2'},{'codeName':'GB/T 19251-2003_EANUCC-14','probability':'0.2'},{'codeName':'GB/T 19251-2003_EANUCC-13','probability':'0.2'}]";
		
		this.data = "[{'codeName':'abc','probability':'0.5399'},{'codeName':'UPC_E','probability':'0.0376'},{'codeName':'Ecode_5','probability':'0.0563'}] ";
		this.extraData = "{'abc':{'fullName':'abdfdfdfdfdfdfdfdfdfdfdcdefg', 'codeNum': '00132323'},'UPC_E':{'fullName':'ddfffdfd', 'codeNum': 'dfdffdf'},'Ecode_5':{'fullName':'Ecode_5fdfdf', 'codeNum': 'aaaaa'}}";
		
		
		//this.status = "1";
		//this.data = "CPCCPCCPCCPCCPCCPCCPCCPCCPCCPCCPC";
		
		//this.status = "error";
		//this.statement = "��������Ӧʱ�䳬ʱ";
		
		//this.status = "0";
		
		//System.out.println(this.status+this.data+this.statement);
		
		
		return SUCCESS;
	}

}
