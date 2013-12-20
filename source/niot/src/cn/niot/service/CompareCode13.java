package cn.niot.service;

import cn.niot.dao.RecoDao;

//编码62街巷或小区编码与EAN-13进行比较，两个编码长度都是13
public class CompareCode13 {
	//匹配次数
	private int randomNum = 10000;
	
	//单字节随机，按照规则返回char数组
	private char[] randomCodeRegex(String[] regexArray){
		char[] charArray = new char[regexArray.length];
		NewIDstdCollisionDetect detect = new NewIDstdCollisionDetect();
		for(int i = 0; i < regexArray.length; i++){
			charArray[i] = detect.generateRandomChar(regexArray[i]);
		}
		return charArray;
	}
	
	//随机得到一条行政区划代码数据
	private String randomAdminDivision(){
		String code = "";
		try{
			RecoDao dao = new RecoDao();
			code = dao.getRandomAdminDivision();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
	//随机得到一条EANUPC国家代码数据
	private String randomRetailCommunityNumber(){
		String code = "";
		try{
			RecoDao dao = new RecoDao();
			code = dao.getRandomEANUPC();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	//EAN-13校验算法
	private char checkCommodityCode(char[] eanCode){
		char checkcode = 0;
		int i = 0;

		// the sum of the odd and even number
		int odd_sum = 0;
		int even_sum = 0;
		
		for (i = eanCode.length - 1; i >= 0; i -= 2) {
			even_sum += (eanCode[i] - 48); // ASCII码中 字符'0'对应的是30H,十进制就是48
		}

		for (i = eanCode.length - 2; i >= 0; i -= 2) {
			odd_sum += (eanCode[i] - 48);
		}

		if ((((even_sum * 3 + odd_sum)) % 10) == 0) {
			checkcode = 48;
		} else {
			checkcode = (char) ((10 - ((even_sum * 3 + odd_sum)) % 10) + 48);
		}
		return checkcode;
	}
	
	//编号62的随机编码
	public char[] generateRandomStreetCode(){
		String[] regexArray = new String[]{"[0-3]", "[0-9]", "[0-9]", "[0,1]", "[0-9]", "[0-9]", "[0-9]"};
		char[] charArray = new char[regexArray.length];
		char[] adminDivisionArray = new char[6];
		String adminDivisionCode = randomAdminDivision();
		
		charArray = randomCodeRegex(regexArray);
		System.out.println(adminDivisionCode.toCharArray());
		adminDivisionArray =  adminDivisionCode.toCharArray();
		char[] streetCode = new char[adminDivisionArray.length + charArray.length];
		System.arraycopy(adminDivisionArray, 0, streetCode, 0, adminDivisionArray.length);
		System.arraycopy(charArray, 0, streetCode, adminDivisionArray.length, charArray.length);
		return streetCode;
	}

	//EAN-13随机编码
	public char[] generateRandomEAN13(){
		String[] regexArray = new String[]{"[0-9]", "[0-9]", "[0-9]", "[0-9]" ,"[0-9]", "[0-9]" ,"[0-9]", "[0-9]", "[0-9]"};
		String commodityNum = randomRetailCommunityNumber();
		char[] charArray = randomCodeRegex(regexArray);
		char[] eanArray =  commodityNum.toCharArray();
		
		char[] eanCode = new char[eanArray.length + charArray.length + 1];
		System.arraycopy(eanArray, 0, eanCode, 0, eanArray.length);
		System.arraycopy(charArray, 0, eanCode, eanArray.length, charArray.length);
		
		char checkSum = checkCommodityCode(eanCode);
		eanCode[eanCode.length - 1] = checkSum;
		return eanCode;
	}
}
