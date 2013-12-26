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
import com.opensymphony.xwork2.ActionContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 
* @Title: RespCode.java 
* @Package cn.niot.zt 
* @Description:
* @author Zhang Tao
* @date 2013-12-3 
* @version V1.0
 */



public class IoTIDRecognitionAction extends ActionSupport {

	private String code;

	private String status;

	private String data;


	private String statement;
	

	private String extraData;

	private String Msg;


	public String getData() {
		return data;
	}

	public String getStatus() {
		return status;
	}

	public String getStatement() {
		return statement;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public String getExtraData() {
		return extraData;
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
/*	
	public String execute() throws Exception
	{
		System.out.println(this.code);
		String IoTcode = replaceBlank(this.code);
		//HashMap<String, Double> typeProbability = RecoUtil.replaceIotId(IDstrRecognition.IoTIDRecognizeAlg(IoTcode));	
		HashMap<String, Double> typeProbability = IDstrRecognition.IoTIDRecognizeAlg(IoTcode);
		typeProbability = RecoUtil.replaceIotId(typeProbability);
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
				JSONObject jsonObject = new JSONObject();
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
		
		//this.status="5";
		//this.data = "[{'codeName':'GB/T 19251-2003_EANUCC-8','probability':'0.2'},{'codeName':'GB/T 19251-2003_UCC-12','probability':'0.2'},{'codeName':'Ecode_1','probability':'0.2'},{'codeName':'GB/T 19251-2003_EANUCC-14','probability':'0.2'},{'codeName':'GB/T 19251-2003_EANUCC-13','probability':'0.2'}]";
		

		
		
		//this.status = "1";
		//this.data = "CPC";
		
		//this.status = "error";

		
		//this.status = "0";
		
		System.out.println(this.status+"\n"+this.data+"\n"+this.statement);
		
		

		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\\t|\\r|\\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
*/
	public String execute() throws Exception {
		String IoTcode = null;
		if (this.code != null) {
			IoTcode = replaceBlank(this.code);
		}

		if (IoTcode != null) {
			HashMap<String, Double> typeProbability = IDstrRecognition.IoTIDRecognizeAlg(IoTcode);
			HashMap<String, Double> ChineseName_Pro = RecoUtil.replaceIotId(typeProbability);
			
			JSONObject jsonObjectRes = IDstrRecognition.getTwoNamesByIDCode(typeProbability);	
			this.extraData = jsonObjectRes.toString();
			
			int len = ChineseName_Pro.size();
			if (RecoUtil.NO_ID_MATCHED == len) {
				this.status = String.valueOf(RecoUtil.NO_ID_MATCHED);
			} else if (RecoUtil.ONE_ID_MATCHED == len) {
				Iterator iterator = ChineseName_Pro.keySet().iterator();
				while (iterator.hasNext()) {
					Object key = iterator.next();
					this.data = String.valueOf(key);
					this.status = String.valueOf(RecoUtil.ONE_ID_MATCHED);
				}
			} else {
				this.status = String.valueOf(len);

				JSONArray jsonArray = new JSONArray();
				Iterator iterator2 = ChineseName_Pro.keySet().iterator();
				while (iterator2.hasNext()) {
					Object key = iterator2.next();
					JSONObject jsonObject = new JSONObject();
					double probability = ChineseName_Pro.get(key);
					jsonObject.put("codeName", String.valueOf(key));
					jsonObject.put("probability", String.valueOf(probability));
					if (!jsonArray.add(jsonObject)) {
						System.out.println("ERROR! jsonArray.add(jsonObject)");
					}
					this.data = jsonArray.toString();
				}
			}
		}
		return SUCCESS;
	}
}
