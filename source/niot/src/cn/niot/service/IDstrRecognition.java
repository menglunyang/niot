package cn.niot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class IDstrRecognition {
	
	static HashMap<String, Double> rmvRuleSet;
	static HashMap<String, Double> rmvIDSet;
	static HashMap<String, ArrayList<String>> hashMapTypeToRules;// 类型对应规则
	static HashMap<String, ArrayList<String>> hashMapRuleToTypes;// 规则对应类型
	
	public HashMap<String, Double> IoTIDRecognizeAlg(String IoTid){
		//to do --初始化四个主要数据结构--
		
		HashMap<String, Double> typeProbability = new HashMap<String, Double>();
		String s = "abcd";// 待匹配
		
		while (rmvIDSet.size() != 0 && rmvRuleSet.size() != 0) {
			sortRules();
			String maxRule = getMax();
			if (match(maxRule)) {
				intersection(rmvIDSet, hashMapRuleToTypes.get(maxRule));
			} else {
				subtraction(rmvIDSet, hashMapRuleToTypes.get(maxRule));
			}
			union(maxRule);
		}
		if(rmvIDSet.size()==0){
			System.out.println(s+" doesn't belong any Type.");
		}
		else{
			System.out.print(s+" belong ");
			Iterator<String> iterator = rmvIDSet.keySet().iterator();
			if(iterator.hasNext()){
				System.out.print((String)iterator.next()+" ");
			}
			System.out.println("");
		}
		return typeProbability;
	}
	
	private static boolean match(String maxRule) {
		// TODO Auto-generated method stub
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
			p=0.0;
			String ruleName = (String) ruleikey.next();
			Set<String> idkeySet = rmvIDSet.keySet();
			Iterator<String>  idikey = idkeySet.iterator();
			while (idikey.hasNext()) {
				String idName = idikey.next();
				if (hashMapRuleToTypes.get(ruleName).indexOf(idName)>=0
						&& rmvIDSet.containsKey(idName)) {
					p = p + (double) rmvIDSet.get(idName);
				}
			}
			rmvRuleSet.put(ruleName, w(p));// p!=0 or 1
		}
	}

	private static double w(double p) {
		double q = 1 - p;
		return p * Math.log(1 / p) / Math.log(2) + q * Math.log(1 / q)
				/ Math.log(2);
	}

	// 匹配不成功，将rmvISDet - arrayList
	private static void subtraction(HashMap<String, Double> rmvIDSet,
			ArrayList<String> arrayList) {
		Iterator<String> iterator = rmvIDSet.keySet().iterator();

		while (iterator.hasNext()) {
			String temp = (String)iterator.next();

			if (arrayList.indexOf(temp) >= 0) { // 在arrayList中找到了
				//rmvIDSet.remove(temp);
				iterator.remove();
			}
		}
	}

	// 匹配成功，做交集
	private static void intersection(HashMap<String, Double> rmvIDSet,
			ArrayList<String> arrayList) {
		Iterator<String> iterator = rmvIDSet.keySet().iterator();
		
		ArrayList<String> delete_list = new ArrayList<String>();

		while (iterator.hasNext()) {
			String temp = (String)iterator.next(); // 取出rmvIDSet的值与arrayList里面的值比较

			if (arrayList.indexOf(temp) == -1) { // 在arrayList中没有找到，就删除
				//rmvIDSet.remove(temp);         //不能再遍历的时候删除
				delete_list.add(temp);
			}
		}
		
		for(String id_str : delete_list) {
			rmvIDSet.remove(id_str);
		}
	}

	// 更新rmvRuleSet
	private static void union(String delRule) {
		Iterator<String> iter = rmvIDSet.keySet().iterator();

		ArrayList<String> arrayList = new ArrayList<String>(); // 直接把每个rule全部合并成一个list，不管是否重复
		ArrayList<String> arrayList_Rules;

		while (iter.hasNext()) {
			String ID_key = (String)iter.next();

			arrayList_Rules = new ArrayList<String>();
			arrayList_Rules = hashMapTypeToRules.get(ID_key);

			for (String rule : arrayList_Rules) {
				arrayList.add(rule);
			}
		}

		Iterator<String> iterator = rmvRuleSet.keySet().iterator();

		while (iterator.hasNext()) {
			String Rule_key = iterator.next();

			if (arrayList.indexOf(Rule_key) == -1) { // 在合并的list里面没有找到，表明这个是需要删除的
				//rmvRuleSet.remove(Rule_key);
				iterator.remove();
			}
		}
		rmvRuleSet.remove(delRule);		
	}
}
