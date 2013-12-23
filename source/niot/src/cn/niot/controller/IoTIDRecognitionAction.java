package cn.niot.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.unitTest.RuleFuncTest;
import cn.niot.service.*;
import cn.niot.util.RecoUtil;

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
	
	public String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\\t|\\r|\\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	public String execute() throws Exception
	{
		System.out.println(this.code);
		String IoTcode = replaceBlank(this.code);
		HashMap<String, Double> typeProbability = IDstrRecognition.IoTIDRecognizeAlg(IoTcode);
		int len = typeProbability.size();
    	if (RecoUtil.NO_ID_MATCHED == len){
    		this.status = String.valueOf(RecoUtil.NO_ID_MATCHED);
    	} else if (RecoUtil.ONE_ID_MATCHED == len){
    		Iterator iterator = typeProbability.keySet().iterator();                
            while (iterator.hasNext()) {    
				Object key = iterator.next();    
				this.data = String.valueOf(key); 
				this.status = String.valueOf(RecoUtil.ONE_ID_MATCHED);
            } 
    	} else {
    		this.status = String.valueOf(len);
            
            JSONArray jsonArray = new  JSONArray();
            Iterator iterator2 = typeProbability.keySet().iterator();                
            while (iterator2.hasNext()) {    
				Object key = iterator2.next();  				
				JSONObject jsonObject = new  JSONObject();
				double probability = typeProbability.get(key);
				jsonObject.put("codeName",String.valueOf(key));
				jsonObject.put("probability",String.valueOf(probability));
				if (jsonArray.add(jsonObject)){
					System.out.println("ERROR! jsonArray.add(jsonObject)");
				}
				this.data = jsonArray.toString();
            }
    	}

//		
		//this.status = "2";
		//this.data = "[{codeName:'cpc',probability:0.12},{codeName:'eCode',probability:0.88}]";
		
		//this.status = "1";
		//this.data = "CPC";
		
		//this.status = "error";
		//this.statement = "服务器响应时间超时";
		
		//this.status = "0";
		
		System.out.println(this.status+"\n"+this.data+"\n"+this.statement);
		
		
		return SUCCESS;
	}

}
