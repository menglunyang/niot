package cn.niot.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
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
		//138000100000000001.sh.beidou.cid.iot.cn
		//char [] IDstr = new char[]{'1','3','8','0','0','0','1','0','0','0','0','0','0','0','0','0','0','.','s','h','.','b','e','i',
		//		'd','o','u','.','c','i','d','.','i','o','t','.','c','n'};
		char[] IDstr = new char[]{'6', '9','5','2','0','3','6','6','0','1','0','5','5'};
		
		int[] index = new int[]{0,1,2,3,4,5,6,7,8,9,10,11};
//		index[0] = 12;
//		index[1] = 4;
//		index[2] = -1;
//		index[3] = 3;
//		index[4] = 4;
		//System.out.println(RuleFunction.CheckCodeForCommodityCode(IDstr, 13, index, 12));
		
		//NewIDstdCollisionDetect collisionDetecAlg = NewIDstdCollisionDetect.getCollisionDetectAlgorithm();
		//System.out.println(collisionDetecAlg.jsonStr2HashMap("{\"name\": \"123\",\"array\":\"abc\",\"address\":\"guangzhou\"}"));
		//System.out.println(collisionDetecAlg.generateIDString("{\"IDName\": \"XXXID\",\"Len\":\"3\",\"0\":\"2-5\",\"1\":\"A-E\",\"2\":\"h-x\"}"));
		//for (int i = 0; i < 100; i++){
		//	System.out.println(collisionDetecAlg.generateIDString("{\"IDName\": \"XXXID\",\"Len\":\"3\",\"0\":\"2-9,a-e\",\"1\":\"A-E,0-9\",\"2\":\"h-x,0-9\"}"));
		//}
		
		//RuleFuncTest.testGenerateRandomChar();

		//RuleFuncTest.testTwoByteDecimalnt();
		/*
		CompareCode13 com =  new CompareCode13();
		String s="112345";
		System.out.println(s.toCharArray());
		char[] ss = com.generateRandomEAN13();
		*/
		
		//System.out.println(ss.toString());
		//RuleFuncTest.testHouseCode_CheckCode();
		//RuleFuncTest.testFormJsonString();
		//double [] res = NormalIDstdCollisionDetect.evaluateCollisionTwoIDs();
		//System.out.println(res);
		

		double [] res = NormalIDstdCollisionDetect.evaluateCollisionTwoIDs();
		System.out.println(res[0]);
		System.out.println(res[1]);
		System.out.println(res[2]);

		//RuleFuncTest.testFormJsonString();
		ActionContext ctx = ActionContext.getContext();            
		HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String url = request.getRequestURL().toString();
		System.out.println(url);

	
		return "sucess"; //预定义常量
	} 

}