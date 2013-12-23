package cn.niot.service;

import java.util.HashMap;

public class NormalIDstdCollisionDetect {
	public static double [] evaluateCollisionTwoIDs(){
		double TotalCode = 10000;
		double Count1 = 0;
		double Count2 = 0;
		double [] res = new double [3];
		for (int i = 0; i < TotalCode; i++){
			CompareCode13 StreetCode = new CompareCode13();
			char [] IDstr1 = StreetCode.generateRandomStreetCode();
			char [] IDstr2 = StreetCode.generateRandomEAN13();
			HashMap<String, Double> typeProbability1 = IDstrRecognition.IoTIDRecognizeAlg(String.valueOf(IDstr1));
			if (typeProbability1.containsKey("EAN-13")){
				Count1 = Count1 + 1;
			}
			
			HashMap<String, Double> typeProbability2 = IDstrRecognition.IoTIDRecognizeAlg(String.valueOf(IDstr2));
			if (typeProbability2.containsKey("GB/T 23705-2009_2")){
				Count2 = Count2 + 1;
			}
		}
		
		res[0] = TotalCode - Count1;
		res[1] = Count1;
		res[2] = TotalCode;
		return res;		
	}
}
