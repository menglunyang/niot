package cn.niot.controller;

import com.opensymphony.xwork2.ActionSupport;

public class RFIDOrScanerInputAction extends ActionSupport {
	private String InputType;
	
	private String code;

	/**
	 * @param inputType the inputType to set
	 */
	public void setInputType(String inputType) {
		InputType = inputType;
	}

	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("RFID comming...");
		System.out.println("type is "+this.InputType);
		this.code = null;
		return super.execute();
	}





	

}
