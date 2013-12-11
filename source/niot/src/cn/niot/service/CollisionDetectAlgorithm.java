package cn.niot.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.sf.json.JSONObject;
import cn.niot.util.*;

public class CollisionDetectAlgorithm {
	private static CollisionDetectAlgorithm collisionDetectAlg = new CollisionDetectAlgorithm();
	private static Random r1 = new Random(1000);//指定种子数字
	
	public static CollisionDetectAlgorithm getCollisionDetectAlgorithm() {
		return collisionDetectAlg;
	}
	
	public String generateIDString(String JsonString) {
		String IDstring = "";
		HashMap<String, Object> map = jsonStr2HashMap(JsonString);
		int len = 0;
		try{
			if (map.containsKey(RecoUtil.ID_LEN)){
				len = Integer.parseInt((String)map.get(RecoUtil.ID_LEN));
			}
		} catch(Exception e){
			
		}
		
		char instr[] = new char[len];
		for (int i = 0; i < len; i++){
			instr[i] = 0;
		}
		
		Iterator iterator = map.keySet().iterator();                
        while (iterator.hasNext()) {    
	        Object key = iterator.next(); 
	        Object value = map.get(key); 
	        try{
	        	if (key.equals(RecoUtil.ID_LEN) || key.equals(RecoUtil.ID_NAME)){
	        		continue;
	        	}
	        	int index = Integer.parseInt((String)key);
	        	String byteRule = (String)value;
	        	
	        	//生成一个随机字符//
	        	char resChar = generateRandomChar(byteRule);
	        	instr[index] = resChar;
	        }catch (Exception e){
	        	
	        }
        }
        
        IDstring = String.valueOf(instr);
		
		return IDstring;
	}
	
	public HashMap<String, Object> jsonStr2HashMap(String JsonString){
		HashMap<String, Object> map = new HashMap<String, Object>();		
		JSONObject jsonObject = JSONObject.fromObject(JsonString);
		for(Iterator iter = jsonObject.keys(); iter.hasNext();){
			String key = (String)iter.next();
			Object value = jsonObject.getString(key);
			map.put(key, value);
		}
		return map;
	}
	
	public char generateRandomChar(String strByteRule){
		char resChar = '0';
		char [] resChars = new char [RecoUtil.COUNT_NUMBER_CHARS];
		HashMap<String, Object> map = new HashMap<String, Object>();
		String ByteRule = strByteRule;
		ByteRule = ByteRule.replace("[","");
		ByteRule = ByteRule.replace("]","");
		String [] subString = ByteRule.split(",");
		for(String ele: subString){
			String element = ele;
			if (element.contains("-")){				
				int index = element.indexOf("-");
				char char_begin = element.charAt(index - 1);
				char char_end = element.charAt(index + 1);				
				char temp;
				
				if (((char_begin >= 48 && char_begin <= 57) && (char_end >= 48 && char_end <= 57)) ||
					((char_begin >= 65 && char_begin <= 90) && (char_end >= 65 && char_end <= 90)) ||
					((char_begin >= 97 && char_begin <= 122) && (char_end >= 97 && char_end <= 122))){
					for (temp = char_begin; temp <= char_end; temp++){
						map.put(String.valueOf(temp),"");
					}
				} else {
					map.put(String.valueOf(element.charAt(0)), "");				
				}
			}
		}
		
		//traverse the hash map
		int i = 0;
		Iterator iterator_1 = map.keySet().iterator();    
		while (iterator_1.hasNext()) {    
			Object key = iterator_1.next();
			resChars[i] = key.toString().charAt(0);
			i++; 
		}
		//加入随机算法
		resChar = randomizeArray(resChars, i);
        
		return resChar;
	} 
	
	public char randomizeArray(char [] charList, int len){
		char [] resCharArray = new char [len];
		char resChar = 'a';
		int i = 0;
		for (i = 0; i < len; i++){
			resCharArray[i] = charList[i];
		}
		
		int index = 0;
		char tmp = '0';
	    for(i = 0; i < len; i++)  
	    {	    	
	    	do {
	    		
	    		//double random = Math.random();
	    		double random = r1.nextDouble();
		        index = (int) Math.floor(random * (len - i)); 
		        if (index >= len){
		        	index = len - 1;
		        }
		        
		        if (index < 0){
		        	index = 0;
		        }
	    	}while (index == i); 
	    	
	    	tmp = resCharArray[i];  
            resCharArray[i] = resCharArray[index];  
            resCharArray[index] = tmp;	    		  
	    } 
	    double rand = r1.nextDouble();
        int index_res = (int) Math.floor(rand * len); 
        if (index_res >= len){
        	index_res = len - 1;
        }
        
        if (index_res < 0){
        	index_res = 0;
        }
	    resChar = resCharArray[index_res];	    
		return resChar;
	}
	
}
