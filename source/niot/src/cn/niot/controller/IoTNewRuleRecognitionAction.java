package cn.niot.controller;

import java.util.HashMap;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.niot.dao.RecoDao;
import cn.niot.service.IDstrRecognition;
import cn.niot.service.NewIDstdCollisionDetect;
import cn.niot.util.RecoUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 
* @Title: RespRul.java 
* @Package cn.niot.zt 
* @Description:前锟斤拷台锟斤拷荽锟斤拷锟斤拷锟斤拷锟�
* @author Zhang Tao
* @date 2013-12-16 锟斤拷锟斤拷 
* @version V1.0
 */

public class IoTNewRuleRecognitionAction extends ActionSupport {
	
	 /**
	  * 锟矫伙拷锟斤拷前台锟斤拷锟斤拷锟斤拷锟侥憋拷锟诫长锟斤拷
	  */
	private String len;
	 /**
	  * 锟矫伙拷锟斤拷前台锟斤拷锟斤拷锟斤拷锟侥革拷位取值锟斤拷围
	  */
	private String valueRange;
	
	 /**
	  * 锟斤拷锟筋。
	  * 锟斤拷锟斤拷锟斤拷锟斤拷锟截革拷前台锟侥诧拷询状态锟斤拷
	  * 锟斤拷锟斤拷锟饺≈碉拷直锟轿拷锟斤拷锟�锟斤拷锟斤拷锟斤拷1锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷1锟斤拷锟斤拷锟斤拷锟絜rror锟斤拷
	  */
	private String status;
	
	 /**
	  * 锟斤拷status取值为锟斤拷1锟斤拷锟斤拷锟竭达拷锟节★拷1锟斤拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷锟筋。
	  * 锟斤拷锟斤拷锟斤拷锟斤拷锟截革拷前台锟侥憋拷锟斤拷锟斤拷息锟斤拷
	  * 锟斤拷status取值为锟斤拷1锟斤拷时锟斤拷data锟芥储锟斤拷询锟斤拷锟侥憋拷锟斤拷锟斤拷疲锟斤拷锟斤拷锟絛ata="CPC",
	  * 锟斤拷status取值为锟斤拷锟斤拷1锟斤拷锟斤拷锟斤拷时锟斤拷data锟芥储锟斤拷锟斤拷锟斤拷锟斤拷约锟斤拷锟斤拷锟斤拷锟绞ｏ拷
	  * 锟斤拷锟斤拷data = "[{codeName:'cpc',probability:0.12},{codeName:'eCode',probability:0.88}]";
	  */
	private String data;
	
	 /**
	  * 锟斤拷status=="error"时锟斤拷锟斤拷锟筋。
	  * 锟斤拷锟斤拷锟斤拷锟斤拷锟截革拷前台锟侥达拷锟斤拷锟斤拷息锟斤拷
	  * 锟斤拷status=="error"时锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷息锟斤拷值锟斤拷statement锟斤拷
	  * 锟斤拷锟斤拷statement=="锟斤拷锟斤拷锟斤拷锟斤拷应锟斤拷时"锟斤拷之锟襟传递革拷前台
	  */
	private String statement;
	
	private String extraData;	
	
	public String getData() {
		return data;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getStatement() {
		return statement;
	}
	
	public String getExtraData() {
		return extraData;
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
		
		System.out.println(System.currentTimeMillis());
		
		int nDisplayLen = 9;
		String resJasonStr = NewIDstdCollisionDetect.formJsonString(this.len, this.valueRange);
		HashMap<String, Double> HashMapID2Probability = NewIDstdCollisionDetect.computeCollisionRate(resJasonStr);
		HashMap<String, Double> ShortName_Probability = new HashMap<String, Double>();
		JSONObject jsonObjectRes = IDstrRecognition.getTwoNamesByIDCode(HashMapID2Probability,ShortName_Probability);	
		this.extraData = jsonObjectRes.toString();
		
//////////////////////////////////////////////////////////////////////		
		JSONArray jsonArray = new  JSONArray();
		double probability = 0;
    	int len = ShortName_Probability.size();
    	if (RecoUtil.NO_ID_MATCHED == len){
    		this.status = String.valueOf(RecoUtil.NO_ID_MATCHED);
    	} else if (RecoUtil.ONE_ID_MATCHED == len){
    		Iterator iterator = ShortName_Probability.keySet().iterator();                
            while (iterator.hasNext()) {    
				Object key = iterator.next();
				this.status = String.valueOf(RecoUtil.ONE_ID_MATCHED);
				JSONObject jsonObject = new  JSONObject();
				probability = ShortName_Probability.get(key);
				String IDstr = String.valueOf(key);
				if (IDstr.length() > nDisplayLen){
					jsonObject.put("codeName",IDstr.substring(0, nDisplayLen));
				} else {
					jsonObject.put("codeName",IDstr);
				}
				jsonObject.put("CollisionRatio",String.valueOf(probability));
				this.data = jsonObject.toString();
            } 
    	} else {
    		this.status = String.valueOf(len);
            Iterator iterator2 = ShortName_Probability.keySet().iterator();                
            while (iterator2.hasNext()) {    
				Object key = iterator2.next();  				
				JSONObject jsonObject = new  JSONObject();
				probability = ShortName_Probability.get(key);
				String IDstr = String.valueOf(key);
				if (IDstr.length() > nDisplayLen){
					jsonObject.put("codeName",IDstr.substring(0, nDisplayLen));
				} else {
					jsonObject.put("codeName",IDstr);
				}				
				jsonObject.put("CollisionRatio",String.valueOf(probability));
				if (!jsonArray.add(jsonObject)){
					System.out.println("ERROR! jsonArray.add(jsonObject)");
				}
				this.data = jsonArray.toString();
            }
    	}
    	
    	// for test only!
    	/*
    	this.status = String.valueOf(2);        
        JSONArray jsonArray1 = new  JSONArray();
        JSONObject jsonObject = new  JSONObject();             
        jsonObject.put("codeName","b");
		jsonObject.put("CollisionRatio",String.valueOf(0.3));
		jsonArray1.add(jsonObject);
		JSONObject jsonObject2 = new  JSONObject();
		jsonObject2.put("codeName","a");

		jsonObject2.put("CollisionRatio",String.valueOf(0.7));
=======
		jsonObject2.put("probability",String.valueOf(0.7));

//		
		this.status = "7";
		this.data = "[{codeName:'cpc',CollisionRatio:0.12},{codeName:'eCode',CollisionRatio:0.88},{codeName:'fCode',CollisionRatio:0.80},{codeName:'mFS',CollisionRatio:0.48},{codeName:'pdAF',CollisionRatio:0.18},{codeName:'Fnme',CollisionRatio:0.88},{codeName:'qqrf',CollisionRatio:0.56}]";
		
		this.status = "1";
		this.data = "{codeName:'cpc',CollisionRatio:0.12}";
		
		//this.status = "error";
		//this.statement = "锟斤拷锟斤拷锟斤拷锟斤拷应时锟戒超时";
		
		//this.status = "0";

		
		jsonArray1.add(jsonObject2);
		this.data = jsonArray1.toString();
		*/
	
		//this.status = "7";
		//this.data = "[{codeName:'cpc',CollisionRatio:0.12},{codeName:'eCode',CollisionRatio:0.88},{codeName:'fCode',CollisionRatio:0.80},{codeName:'mFS',CollisionRatio:0.48},{codeName:'pdAF',CollisionRatio:0.18},{codeName:'Fnme',CollisionRatio:0.88},{codeName:'qqrf',CollisionRatio:0.56}]";
		
    	//this.status = "1";
		//this.data = "{codeName:'cpc',CollisionRatio:0.12}";
		//this.extraData = "{'cpc':{'codeNum':'Ecode_5','fullName':'鐗╄仈缃戠粺涓�紪鐮�(Entity code, Ecode) V鍨�}}";
    	System.out.println(System.currentTimeMillis());
    	
		System.out.println(this.status+"\n"+this.data+"\n"+this.statement);
		System.out.println("\n"+this.extraData);
		return SUCCESS;
	}
}
