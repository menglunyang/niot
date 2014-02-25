package cn.niot.controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.niot.dao.*;
import cn.niot.rule.RuleFunction;
import cn.niot.service.*;
import cn.unitTest.RuleFuncTest;
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
	
		char[] IDstr = new char[12];
		IDstr[0] = '9';
		IDstr[1] = '2';
		IDstr[2] = '0';
		IDstr[3] = '1';
		IDstr[4] = '2';
		IDstr[5] = '3';
		
		IDstr[6] = '4';
		
		IDstr[7] = '5';
		IDstr[8] = '6';
		IDstr[9] = '7';
		IDstr[10] = '8';
		IDstr[11] = '0';
//		IDstr[12] = '0';
//		
//		IDstr[13] = '9';
//		IDstr[14] = '3';
		
		int[] index = new int[12];
		index[0] = 0;
		index[1] = 1;
		index[2] = 2;
		index[3] = 3;
		index[4] = 4;
		index[5] = 5;
		index[6] = 6;
		index[7] = 7;
		index[8] = 8;
		index[9] = 9;
		index[10] = 10;
		index[11] = 11;
//		index[12] = 12;
//		index[13] = 13;
//		index[14] = 14;
		
		System.out.println("start");
		System.out.println("zz");
		System.out.println(RuleFunction.DeviceMOD163(IDstr, 12, index, 12));
		
		//System.out.println(RuleFunction.GraiSerialNo(IDstr, 5, index, 3));
		
		//NewIDstdCollisionDetect collisionDetecAlg = NewIDstdCollisionDetect.getCollisionDetectAlgorithm();
		//System.out.println(collisionDetecAlg.jsonStr2HashMap("{\"name\": \"123\",\"array\":\"abc\",\"address\":\"guangzhou\"}"));
		//System.out.println(collisionDetecAlg.generateIDString("{\"IDName\": \"XXXID\",\"Len\":\"3\",\"0\":\"2-5\",\"1\":\"A-E\",\"2\":\"h-x\"}"));
		//for (int i = 0; i < 100; i++){
		//	System.out.println(collisionDetecAlg.generateIDString("{\"IDName\": \"XXXID\",\"Len\":\"3\",\"0\":\"2-9,a-e\",\"1\":\"A-E,0-9\",\"2\":\"h-x,0-9\"}"));
		//}
		
		RuleFuncTest.testTwoByteDecimalnt();
		
		return "sucess"; //预定义常量
	} 

}