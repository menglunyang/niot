package cn.niot.controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.niot.dao.*;

public class RecoAction extends ActionSupport {
	/**
	 * @return
	 */
	private String iotID;
	
	
	public String getIotID() {
		return iotID;
	}

	public void setIotID(String iotID) {
		this.iotID = iotID;
	}
	
	private String Msg;    // 添加一个变量


	public String getMsg() {    // 添加getter
	return Msg;
	}
	public String execute() {
		if (iotID.equals("123456")){
			Msg = "                   "+ iotID + " is ID type A";     // 逻辑运算
		} else{
			Msg = "                   "+ iotID + " is not ID type A";     // 逻辑运算
		}
		
		RecoDao recodao = RecoDao.getRecoDao();
		recodao.getIoTID("1");
		
	
		return "sucess"; //预定义常量
	} 

}