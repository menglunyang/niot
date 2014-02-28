package cn.niot.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;

import cn.niot.dao.*;
import cn.niot.rule.RuleFunction;
import cn.niot.service.*;
import cn.unitTest.RuleFuncTest;
@SuppressWarnings({ "serial", "unused" })
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
//		if (iotID.equals("123456")){
//			Msg = "                   "+ iotID + " is ID type A";     // 逻辑运算
//		} else{
//			Msg = "                   "+ iotID + " is not ID type A";     // 逻辑运算
//		}
	
//		char[] IDstr = new char[3];
//		IDstr[0] = '1';
//		IDstr[1] = '0';
//		IDstr[2] = '6';
//		IDstr[3] = '4';
//		IDstr[4] = '5';
//		IDstr[5] = '0';
		
//		IDstr[6] = '0';
//		IDstr[7] = '0';
//		IDstr[8] = '0';
//		IDstr[9] = '0';
//		IDstr[10] = '0';
//		IDstr[11] = '0';
//		IDstr[12] = '0';
//		
//		IDstr[13] = '1';
//		IDstr[14] = '6';

//		int[] index = new int[3];
//		index[0] = 0;
//		index[1] = 1;
//		index[2] = 2;
//		index[3] = 3;
//		index[4] = 4;
//		index[5] = 5;
//		index[6] = 6;
//		index[7] = 7;
//		index[8] = 8;
//		index[9] = 9;
//		index[10] = 10;
//		index[11] = 11;
//		index[12] = 12;
//		index[13] = 13;
//		index[14] = 14;
		
		System.out.println("start");
		
//		System.out.println(RuleFunction.MOD9710(IDstr, 15, index, 15));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式 
		System.out.println(df.format(new Date()));
//		System.out.println(RuleFunction.DeviceMOD163(IDstr, 12, index, 12));
//		System.out.println(RuleFunction.CoalInterment(IDstr, 12, index, 12));
//		System.out.println(RuleFunction.CheckCodebarcode(IDstr, 13, index, 13));
//		System.out.println(RuleFunction.Tobaccomachinery(IDstr, 12, index, 12));
//		System.out.println(RuleFunction.BussManaCheck(IDstr, 15, index, 15));
//		System.out.println(RuleFunction.Mod36_37(IDstr, 15, index, 15));
//		System.out.println(RuleFunction.LogisticsCheck(IDstr, 6, index, 6));
//		System.out.println(RuleFunction.TheCenteralPartyCommitte(IDstr, 3, index, 3));
		
		String tr1 = new String("040399");
		char[] IDstr1 = new char[6];
		for (int i = 0; i < 6; i++) {
			IDstr1[i] = tr1.charAt(i);
		}
		int[] index = new int[6];
		index[0]=0;
		index[1]=1;
		index[2]=2;
		index[3]=3;
		index[4]=4;
		index[5]=5;

		String result = RuleFunction.JadejewelryMaterialclassif(IDstr1,6,index,6);
		System.out.println("result="+result.toString());
		return "sucess";
		
		
		
		
		//System.out.println(RuleFunction.GraiSerialNo(IDstr, 5, index, 3));
		
		//NewIDstdCollisionDetect collisionDetecAlg = NewIDstdCollisionDetect.getCollisionDetectAlgorithm();
		//System.out.println(collisionDetecAlg.jsonStr2HashMap("{\"name\": \"123\",\"array\":\"abc\",\"address\":\"guangzhou\"}"));
		//System.out.println(collisionDetecAlg.generateIDString("{\"IDName\": \"XXXID\",\"Len\":\"3\",\"0\":\"2-5\",\"1\":\"A-E\",\"2\":\"h-x\"}"));
		//for (int i = 0; i < 100; i++){
		//	System.out.println(collisionDetecAlg.generateIDString("{\"IDName\": \"XXXID\",\"Len\":\"3\",\"0\":\"2-9,a-e\",\"1\":\"A-E,0-9\",\"2\":\"h-x,0-9\"}"));
		//}
		
//		RuleFuncTest.testTwoByteDecimalnt();
		
//		return "sucess"; s//预定义常量
	} 

}