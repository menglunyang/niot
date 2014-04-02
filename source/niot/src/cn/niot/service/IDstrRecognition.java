package cn.niot.service;

import java.text.DateFormat;
import java.util.*;
import java.lang.reflect.*;

import net.sf.json.JSONObject;

import cn.niot.dao.*;
import cn.niot.util.JdbcUtils;
import cn.niot.util.RecoUtil;

public class IDstrRecognition {
	static String DEBUG = "OFF";//the value of DEBUG can be "ON" or "OFF"
	static String DEBUG_RES = "OFF";//the value of DEBUG_RES can be "ON" or "OFF"
	static String DEBUG_LINE = "OFF";//the value of DEBUG_LINE can be "ON" or "OFF"
	static String DEBUG_TIME = "OFF";//the value of DEBUG_TIME can be "ON" or "OFF"
	static int line = 0;
	
	static HashMap<String, Double> rmvRuleSet;
	static HashMap<String, Double> rmvIDSet;
	static HashMap<String, ArrayList<String>> hashMapTypeToRules;// ���Ͷ�Ӧ����
	static HashMap<String, ArrayList<String>> hashMapRuleToTypes;// �����Ӧ����
	
	
	public static void readDao(int type){
		hashMapTypeToRules = new HashMap<String, ArrayList<String>>();
		hashMapRuleToTypes = new HashMap<String, ArrayList<String>>();// �����Ӧ����
		rmvRuleSet = new HashMap<String, Double>();// rmvRuleSet<������Ȩ��>
		rmvIDSet = new HashMap<String, Double>();
		RecoDao dao = RecoDao.getRecoDao();
		if(type==0){
		hashMapTypeToRules = dao.DBreadTypeAndRules(rmvRuleSet, rmvIDSet,
				hashMapRuleToTypes);
		}
		else{
			hashMapTypeToRules = dao.HibernateDBreadTypeAndRules(rmvRuleSet, rmvIDSet, hashMapRuleToTypes);
		}
		
	}
	
	public static HashMap<String, Double> IoTIDRecognizeAlg(String s){	
		//ȫ�ֲ���ʱ��
		//System.out.println(System.currentTimeMillis());
		HashMap<String, Double> typeProbability = new HashMap<String, Double>();
//		hashMapTypeToRules = new HashMap<String, ArrayList<String>>();// ���Ͷ�Ӧ����
//		hashMapRuleToTypes = new HashMap<String, ArrayList<String>>();// �����Ӧ����
//		rmvRuleSet = new HashMap<String, Double>();// rmvRuleSet<������Ȩ��>
//		rmvIDSet = new HashMap<String, Double>();// rmvIDSet<�������������>
		
		long timeDaoBegin = 0,timeDao =0,timeSortRulesBegin=0,timeSortRules=0,timeMatchBegin=0,timeMatch=0,timeSubtractionBegin=0,timeSubtraction=0,timeUnionBegin=0,timeUnion=0;
		

//		RecoDao dao = RecoDao.getRecoDao();
//		if("ON"==DEBUG_TIME)timeDaoBegin=System.currentTimeMillis();
//		hashMapTypeToRules = dao.HibernateDBreadTypeAndRules(rmvRuleSet, rmvIDSet,
//				hashMapRuleToTypes);
//		if("ON"==DEBUG_TIME)timeDao=System.currentTimeMillis()-timeDaoBegin;
		ArrayList<String> rulesList;

		while (rmvIDSet.size() != 0 && rmvRuleSet.size() != 0) {
			if("ON"==DEBUG_TIME)timeSortRulesBegin=System.currentTimeMillis();
			sortRules();
			if("ON"==DEBUG_TIME)timeSortRules=System.currentTimeMillis()-timeSortRulesBegin;
			String maxRule = getMax();
			String[] splitRules = maxRule.split("\\)\\(\\?\\#PARA=");// ��ȡ������
			String[] splitParameter = splitRules[1].split("\\)\\{\\]");// ��ȡ����
			if ("ON" == DEBUG){
				System.out.print("matching " + splitRules[0] + "("
						+ splitParameter[0] + ").");
			}
			if("ON"==DEBUG_TIME)timeMatchBegin=System.currentTimeMillis();
			if (match(splitRules[0], splitParameter[0], s)) {
				// intersection(rmvIDSet, hashMapRuleToTypes.get(maxRule));
				if("ON"==DEBUG_TIME)timeMatch=System.currentTimeMillis()-timeMatchBegin;
				if ("ON" == DEBUG){
					System.out.println("OK");
				}				
			} else {
				if("ON"==DEBUG_TIME)timeMatch=System.currentTimeMillis()-timeMatchBegin;
				if ("ON" == DEBUG){
					System.out.println("ERR");
				}	
				if("ON"==DEBUG_TIME)timeSubtractionBegin=System.currentTimeMillis();
				subtraction(rmvIDSet, hashMapRuleToTypes.get(maxRule));
				if("ON"==DEBUG_TIME)timeSubtraction=System.currentTimeMillis()-timeSubtractionBegin;
			}
			if("ON"==DEBUG_TIME)timeUnionBegin=System.currentTimeMillis();
			union(maxRule);
			if("ON"==DEBUG_TIME)timeUnion=System.currentTimeMillis()-timeUnionBegin;
		}
		if("ON"==DEBUG_TIME)System.out.println("����ݿ���ʱ��"+timeDao+",SortRules��ʱ��"+timeSortRules+",Match��ʱ��"+timeMatch+",Subtraction��ʱ��"+timeSubtraction+",Union��ʱ��"+timeUnion);
		Date now = new Date();
	    DateFormat d1 = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG); //Ĭ�����ԣ�����µ�Ĭ�Ϸ��MEDIUM��񣬱��磺2008-6-16 20:54:53��
	    if ("ON" == DEBUG){
	    	System.out.print(d1.format(now)+":");
	    }
	    
	    double totalProbabity = 0;
		if (rmvIDSet.size() == 0) {
			if ("ON" == DEBUG){
				System.out.println(s + " doesn't belong any Type.");
			}			
		} else {
			if ("ON" == DEBUG_RES){
				System.out.print(s + " belong to:");
			}			
			Iterator<String> iterator = rmvIDSet.keySet().iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				totalProbabity = totalProbabity + rmvIDSet.get(key);
				if ("ON" == DEBUG_RES){
					System.out.print((String) key + " ");
				}				
			}
			if ("ON" == DEBUG_RES){
				System.out.println("");
			}			
		}
		if ("ON" == DEBUG_LINE){
			line = line + 1;
			System.out.println(line);
		}
		Iterator<String> iterator2 = rmvIDSet.keySet().iterator();
		while (iterator2.hasNext()) {
			Object key2 = iterator2.next();
			double probability = rmvIDSet.get(key2) / totalProbabity;
			typeProbability.put(String.valueOf(key2), probability);
		}
		//System.out.println(System.currentTimeMillis());
		return typeProbability;
	}
	
	public static JSONObject getTwoNamesByIDCode(HashMap<String, Double> HashMapID2Probability,HashMap<String, Double> ShortName_Probability ){
		Iterator iterator_t = HashMapID2Probability.keySet().iterator();
		HashMap<String, String>IDCode_ChineseName = new HashMap<String, String>();
		HashMap<String, String>IDCode_ShortName = new HashMap<String, String>();
		ShortName_Probability.clear();
		
		RecoDao dao = new RecoDao();
		int nAppendedIndex = 0;
		while(iterator_t.hasNext()){
			String key_IDstd = iterator_t.next().toString();
			double probability = HashMapID2Probability.get(key_IDstd);
			String ChineseName = dao.getIDDetail(key_IDstd);
			IDCode_ChineseName.put(key_IDstd, ChineseName);
			
			char [] ShortName = new char[RecoUtil.DISPLAYLENGTH];
			int nIndex = 0;
			for (int i = 0; i < key_IDstd.length(); i++){
				char charTemp = key_IDstd.charAt(i);
				if ((charTemp >= '0' && charTemp <= '9') ||
					(charTemp >= 'a' && charTemp <= 'z') ||
					(charTemp >= 'A' && charTemp <= 'Z')){
					ShortName[nIndex] = charTemp;
					nIndex++;
					if (nIndex >= RecoUtil.DISPLAYLENGTH){
						break;
					}
				}
			}
			String CurShortName = (String.valueOf(ShortName)).trim();
			if (ShortName_Probability.containsKey(CurShortName)){
				String ResShortName = CurShortName + String.valueOf(nAppendedIndex);
				nAppendedIndex++;
				IDCode_ShortName.put(key_IDstd, ResShortName);
				ShortName_Probability.put(ResShortName, probability);
			} else {
				IDCode_ShortName.put(key_IDstd, CurShortName);
				ShortName_Probability.put(CurShortName, probability);
			}			
		}
//////////////////////////////////////////////////////////////////////
		JSONObject jsonObjectRes = new  JSONObject();
		
		Iterator iterator_temp = HashMapID2Probability.keySet().iterator();
		while(iterator_temp.hasNext()){
			String key_IDstd = iterator_temp.next().toString();
			String ChineseName = (IDCode_ChineseName.get(key_IDstd)).toString();
			String ShortName = (IDCode_ShortName.get(key_IDstd)).toString();
			JSONObject jsonObject2 = new  JSONObject();
			jsonObject2.put("fullName", ChineseName);
			jsonObject2.put("codeNum", key_IDstd);
			jsonObjectRes.put(ShortName, jsonObject2);
		}
		return jsonObjectRes;
	}
	
	private static boolean match(String maxRule, String parameter, String input) {
		try {
			String[] arg = new String[2];
			arg[0] = parameter;
			arg[1] = input;
			Object result = "";
			Object[] argOther = new Object[4];
			Class[] c = new Class[4];
			Class ruleFunctionClass = Class
					.forName("cn.niot.rule.RuleFunction");

			if (maxRule.equals("IoTIDByte")) {
				argOther[0] = input;
				argOther[1] = parameter;
				argOther[2] = "";
				argOther[3] = "";
				c[0] = argOther[0].getClass();
				c[1] = argOther[1].getClass();
				c[2] = argOther[2].getClass();
				c[3] = argOther[3].getClass();
			} else if (maxRule.equals("IoTIDLength")) {
				argOther[0] = input;
				argOther[1] = 0;
				argOther[2] = parameter;
				argOther[3] = 0;
				c[0] = String.class;
				c[1] = int.class;
				c[2] = String.class;
				c[3] = int.class;
			} else {
				argOther[0] = input.toCharArray();
				argOther[1] = input.length();
				String[] splitString = parameter.split(",");
				int[] index = new int[splitString.length];
				for (int i = 0; i < splitString.length; i++) {
					index[i] = Integer.parseInt(splitString[i]);
				}
				argOther[2] = index;
				argOther[3] = index.length;
				c[0] = char[].class;
				c[1] = int.class;
				c[2] = int[].class;
				c[3] = int.class;
			}
			Method method = ruleFunctionClass.getMethod(maxRule, c);
			result = method.invoke(null, argOther);
			if (result == "OK")
				return true;
			if (result == "ERR")
				return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("RuleFunction.java file can not find " + maxRule
					+ " method,error");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private static String getMax() {
		Set<String> keySet = rmvRuleSet.keySet();
		Iterator ikey = keySet.iterator();
		String maxName = (String) ikey.next();
		String nextName = "";
		double max = rmvRuleSet.get(maxName);
		double next = 0;

		while (ikey.hasNext()) {
			nextName = (String) ikey.next();
			next = rmvRuleSet.get(nextName);
			if (next > max) {
				max = next;
				maxName = nextName;
			}
		}
		return maxName;
	}

	private static void sortRules() {
		// TODO Auto-generated method stub
		double p = 0.0;
		Set<String> rulekeySet = rmvRuleSet.keySet();
		Iterator<String> ruleikey = rulekeySet.iterator();
		while (ruleikey.hasNext()) {
			p = 0.0;
			String ruleName = (String) ruleikey.next();
			Set<String> idkeySet = rmvIDSet.keySet();
			Iterator<String> idikey = idkeySet.iterator();
			while (idikey.hasNext()) {
				String idName = idikey.next();
				if (hashMapRuleToTypes.get(ruleName).indexOf(idName) >= 0
						&& rmvIDSet.containsKey(idName)) {
					p = p + (double) rmvIDSet.get(idName);
				}
			}
			if (p == 0 || p == 1) {
				System.out.println(ruleName + " p is 0 or 1,error!");
			}
			if (p > 1 || p < 0) {
				System.out.println(ruleName + " p is not in 1~0 range,error!");
			}
			rmvRuleSet.put(ruleName, w(p));// p!=0 or 1
		}
	}

	private static double w(double p) {
		double q = 1 - p;
		return p * Math.log(1 / p) / Math.log(2) + q * Math.log(1 / q)
				/ Math.log(2);
	}

	// ƥ�䲻�ɹ�����rmvISDet - arrayList
	private static void subtraction(HashMap<String, Double> rmvIDSet,
			ArrayList<String> arrayList) {
		Iterator<String> iterator = rmvIDSet.keySet().iterator();

		while (iterator.hasNext()) {
			String temp = (String) iterator.next();

			if (arrayList.indexOf(temp) >= 0) { // ��arrayList���ҵ���
				// rmvIDSet.remove(temp);
				iterator.remove();
			}
		}
	}

	// ƥ��ɹ���������
	private static void intersection(HashMap<String, Double> rmvIDSet,
			ArrayList<String> arrayList) {
		Iterator<String> iterator = rmvIDSet.keySet().iterator();

		ArrayList<String> delete_list = new ArrayList<String>();

		while (iterator.hasNext()) {
			String temp = (String) iterator.next(); // ȡ��rmvIDSet��ֵ��arrayList�����ֵ�Ƚ�

			if (arrayList.indexOf(temp) == -1) { // ��arrayList��û���ҵ�����ɾ��
				// rmvIDSet.remove(temp); //�����ٱ����ʱ��ɾ��
				delete_list.add(temp);
			}
		}

		for (String id_str : delete_list) {
			rmvIDSet.remove(id_str);
		}
	}

	// ����rmvRuleSet
	private static void union(String delRule) {
		Iterator<String> iter = rmvIDSet.keySet().iterator();

		ArrayList<String> arrayList = new ArrayList<String>(); // ֱ�Ӱ�ÿ��ruleȫ���ϲ���һ��list�������Ƿ��ظ�
		ArrayList<String> arrayList_Rules;

		while (iter.hasNext()) {
			String ID_key = (String) iter.next();

			arrayList_Rules = new ArrayList<String>();
			arrayList_Rules = hashMapTypeToRules.get(ID_key);

			for (String rule : arrayList_Rules) {
				arrayList.add(rule);
			}
		}

		Iterator<String> iterator = rmvRuleSet.keySet().iterator();

		while (iterator.hasNext()) {
			String Rule_key = iterator.next();

			if (arrayList.indexOf(Rule_key) == -1) { // �ںϲ���list����û���ҵ��������������Ҫɾ���
				// rmvRuleSet.remove(Rule_key);
				iterator.remove();
			}
		}
		rmvRuleSet.remove(delRule);
	}
}
