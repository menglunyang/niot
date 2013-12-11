package cn.niot.controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.niot.dao.*;
import cn.niot.rule.RuleFunction;
import cn.niot.service.*;
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
		//138000100000000001.sh.beidou.cid.iot.cn
		char [] IDstr = new char[6];
		IDstr[0] = '0';
		IDstr[1] = '1';
		IDstr[2] = '0';
		IDstr[3] = '0';
		IDstr[4] = '1';
		IDstr[5] = 'X';
//		IDstr[0] = '1';
//		IDstr[1] = '3';
//		IDstr[2] = '8';
//		IDstr[3] = '.';
//		IDstr[4] = 's';
//		IDstr[5] = 'h';
//		IDstr[6] = '.';
//		IDstr[7] = 'b';
//		IDstr[8] = 'e';
//		IDstr[9] = 'i';
//		IDstr[10] = 'd';
//		IDstr[11] = 'o';
//		IDstr[12] = 'u';
//		IDstr[13] = '.';
//		IDstr[14] = 'c';
//		IDstr[15] = 'i';
//		IDstr[16] = 'd';
//		IDstr[17] = '.';
//		IDstr[18] = 'i';
//		IDstr[19] = 'o';
//		IDstr[20] = 't';
//		IDstr[21] = 'u';
//		IDstr[22] = 'c';
//		IDstr[23] = 'n';
		
		int [] index = new int[2];
		index[0] = 2;
		index[1] = -1;
//		index[2] = 2;
//		index[3] = 3;
//		index[4] = 4;
		System.out.println(RuleFunction.IntFreitForwarding(IDstr, 6, index, 2));
		
		CollisionDetectAlgorithm collisionDetecAlg = CollisionDetectAlgorithm.getCollisionDetectAlgorithm();
		//System.out.println(collisionDetecAlg.jsonStr2HashMap("{\"name\": \"123\",\"array\":\"abc\",\"address\":\"guangzhou\"}"));
		//System.out.println(collisionDetecAlg.generateIDString("{\"IDName\": \"XXXID\",\"Len\":\"3\",\"0\":\"2-5\",\"1\":\"A-E\",\"2\":\"h-x\"}"));
		for (int i = 0; i < 100; i++){
			System.out.println(collisionDetecAlg.generateIDString("{\"IDName\": \"XXXID\",\"Len\":\"3\",\"0\":\"2-9,a-e\",\"1\":\"A-E,0-9\",\"2\":\"h-x,0-9\"}"));
		}
	
		return "sucess"; //预定义常量
	} 

}