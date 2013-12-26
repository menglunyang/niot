package cn.niot.controller;

import com.opensymphony.xwork2.ActionSupport;



public class IoTNewRuleRecognitionAction extends ActionSupport {
	

	private String len;

	private String valueRange;
	

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

		this.status = "7";
		//this.data = "[{codeName:'cpc',CollisionRatio:0.12},{codeName:'eCode',CollisionRatio:0.88},{codeName:'fCode',CollisionRatio:0.80},{codeName:'mFS',CollisionRatio:0.48},{codeName:'pdAF',CollisionRatio:0.18},{codeName:'Fnme',CollisionRatio:0.88},{codeName:'qqrf',CollisionRatio:0.56}]";
		
		this.data = "[{'codeName':'abc','CollisionRatio':'0.5399'},{'codeName':'UPC_E','CollisionRatio':'0.0376'},{'codeName':'Ecode_5','CollisionRatio':'0.0563'}] ";
		this.extraData = "{'abc':{'fullName':'abdfdfdfdfdfdfdfdfdfdfdcdefg', 'codeNum': '00132323'},'UPC_E':{'fullName':'ddfffdfd', 'codeNum': 'dfdffdf'},'Ecode_5':{'fullName':'Ecode_5fdfdf', 'codeNum': 'aaaaa'}}";
		//this.data = "[{'codeName':'abcdefghijklmn','CollisionRatio':'0.5399'},{'codeName':'UPC_E','CollisionRatio':'0.0376'}] ";
		
		
		
		//this.status = "1";
		//this.data = "{codeName:'cpc',CollisionRatio:0.12}";
		
		//this.status = "error";
		//this.statement = "��������Ӧʱ�䳬ʱ";
		
		//this.status = "0";
		
		
		System.out.println(this.status+this.data+this.statement);
		return SUCCESS;
	}

}
