package cn.niot.rule;

import cn.niot.dao.RecoDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleFunction {
	
	static String ERR = "ERR";
	static String OK = "OK";
	public static void main(String[] args) {
		//TODO:
	}
	
	//Function: represent a decimal integer whose value range is from 1 to 99 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String TwoByteDecimalnt(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex < 2)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];

		if ((IDstr[index1] == '0') && (IDstr[index2] == '0'))
		{
			return ERR;
		}
		
		if ((IDstr[index1] < '0') || (IDstr[index1] > '9'))
		{
			return ERR;
		}
		
		if ((IDstr[index2] < '0') || (IDstr[index2] > '9'))
		{
			return ERR;
		}

		return OK;
		
	}
	
	//Function: decide the cigarette subclass code according to different mainclass code 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//		 Index[0] is  the index of mainclass code whose values can be int 1, 2, 3, 4, 9
	//		 Index[1] and Index[2] are the index of subclass codes
	//LenIndex: the number of indexes that must be 3
	//creator: zll
	public static String CigaSubClassCode(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		
		if(LenIndex != 3){
			return ERR;
		}
		//mainclass: 1 subclass: 01, 02, 99
		if(IDstr[Index[0]] == '1'){
			if(IDstr[Index[1]] == '0'){
				if(IDstr[Index[2]] == '1' || IDstr[Index[2]] == '2'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '9'){
				if(IDstr[Index[2]] == '9'){
					return OK;
				}
			}
		}else if(IDstr[Index[0]] == '2'){	//mainclass: 2 subclass: 01-09, 10, 99
			if(IDstr[Index[1]] == '0'){
				if(IDstr[Index[2]] > '0' && IDstr[Index[2]] <= '9'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '1'){
				if(IDstr[Index[2]] == '0'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '9'){
				if(IDstr[Index[2]] == '9'){
					return OK;
				}
			}
		}else if(IDstr[Index[0]] == '3'){	//mainclass: 3 subclass: 01-08, 99
			if(IDstr[Index[1]] == '0'){
				if(IDstr[Index[2]] > '0' && IDstr[Index[2]] < '9'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '9'){
				if(IDstr[Index[2]] == '9'){
					return OK;
				}
			}
		}else if(IDstr[Index[0]] == '4'){	//mainclass: 4 subclass: 01-09, 10, 11, 99
			if(IDstr[Index[1]] == '0'){
				if(IDstr[Index[2]] > '0' && IDstr[Index[2]] <= '9'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '1'){
				if(IDstr[Index[2]] == '0' || IDstr[Index[2]] == '1'){
					return OK;
				}
			}else if(IDstr[Index[1]] == '9'){
				if(IDstr[Index[2]] == '9'){
					return OK;
				}
			}
		}else if(IDstr[Index[0]] == '9'){	//mainclass: 9 subclass: 01
			if(IDstr[Index[1]] == '0' && IDstr[Index[2]] == '1'){
				return OK;
			}
		}
		return ERR;
	}
	
	//Function: There are four characters. The first two characters are represented as month, the other two 
	//characters are represented the date in that month.
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//		 Index[0] and Index[1] are the index of month
	//		 Index[2] and Index[3] are the index of date
	//LenIndex: the number of indexes that must be 4
	//creator: zll
	public static String MonthDate(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		try{
			int date = Integer.parseInt(String.valueOf(IDstr[Index[2]])) * 10 + Integer.parseInt(String.valueOf(IDstr[Index[3]]));
			if(LenIndex != 4){
				return ERR;
			}
			if(IDstr[Index[0]] == '0'){	//month: 01, 03, 05, 07, 08
				if(IDstr[Index[1]] == '1' || IDstr[Index[1]] == '3' || IDstr[Index[1]] == '5' ||
					IDstr[Index[1]] == '7' || IDstr[Index[1]] == '8'){
					if(date <= 31){
						return OK;
					}
				}else if(IDstr[Index[1]] == '4' || IDstr[Index[1]] == '6' || IDstr[Index[1]] == '9'){	//month: 04, 06, 09
					if(date <= 30){
						return OK;
					}
				}else if(IDstr[Index[1]] == '2'){	//month: 02
					if(date <= 29){
						return OK;
					}
				}
			}else if(IDstr[Index[0]] == '1'){	//month: 10, 12
				if(IDstr[Index[1]] == '0' || IDstr[Index[1]] == '2'){
					if(date <= 31){
						return OK;
					}
				}else if(IDstr[Index[1]] == '1'){	//month: 11
					if(date <= 30){
						return OK;
					}
				}
			}
			return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: Cigarette organization code. There are totally 2 characters.
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//		 Index[0] is the index of the first character of cigarette organization code which is 1,2 or 9.
	//		 Index[1] is the index of the second character.
	//LenIndex: the number of indexes that must be 2
	//creator: zll
	public static String CigaOrgCode(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 2){
				return ERR;
			}
			if(IDstr[Index[0]] == '1'){
				if(IDstr[Index[1]] >= '0' && IDstr[Index[1]] <= '9'){
					return OK;
				}
			}else if(IDstr[Index[0]] == '2'){
				if(IDstr[Index[1]] >= '0' && IDstr[Index[1]] <= '3'){
					return OK;
				}
			}else if(IDstr[Index[0]] == '9'){
				if(IDstr[Index[1]] == '9'){
					return OK;
				}
			}
			return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:两位数，第一位为1时，第二位为（0~9）；第一位为2时，第二位为（0~3）；第一位为9时，第二位为9. 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, 固定为2
	public static String Count(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2){
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		
		if (IDstr[index1] == '1'){
			if ((IDstr[index2] >= '0') && (IDstr[index2] <= '9')){
				return OK;
			}
		}
		
		if (IDstr[index1] == '2'){
			if ((IDstr[index2] >= '0') && (IDstr[index2] <= '3')){
				return OK;
			}     
		}
		
		if (IDstr[index1] == '9'){
			if (IDstr[index2] == '9'){
				return  OK;
			}			
		}
		
		return ERR;
	}
	
	//Function: Cigarette department or subordinate department code. There are totally 2 characters.
	//IDstr: ID string. Code range is 00-97.
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes that must be 2
	//creator: zll
	public static String CigaDepCode(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 2){
				return ERR;
			}
			int depCode = Integer.parseInt(String.valueOf(IDstr[Index[0]])) * 10 + Integer.parseInt(String.valueOf(IDstr[Index[1]]));
			if(depCode >= 0 && depCode <= 97){
				return OK;
			}else{
				return ERR;
			}
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: There are all together 6 chars of administrative division code. This method is to used to
	//connect to the database and get the first 4 chars of division code. 
	//IDstr: ID string, the first 4 chars of administrative division code. 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes that must be 4
	//creator: zll
	public static String First4CharsofAdminDivisionforCiga(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String id = "";
			String append = "00";
			if(LenIndex != 4){
				return ERR;
			}
			if(IDstr[Index[0]] == '0' && IDstr[Index[1]] == '0'){
				return OK;
			}
			RecoDao recoDao = new RecoDao();
			for(int i = 0; i < LenIndex; i++){
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			id = id.concat(append);
			boolean ret  = recoDao.getAdminDivisionID(id);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: 6位行政区划代码的前2位.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用行政区划代码的位置
	//LenIndex: 长度必须是2位
	public static String First2CharsofAdminDivision(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String id = "";
			String append = "0000";
			if(LenIndex != 2){
				return ERR;
			}
			RecoDao recoDao = new RecoDao();
			for(int i = 0; i < LenIndex; i++){
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			id = id.concat(append);
			boolean ret  = recoDao.getAdminDivisionID(id);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}

	//Function: 6位行政区划代码.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用行政区划代码的位置
	//LenIndex: 长度必须是6位
	//creator: zll
	public String AdminDivision(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 6 ){
				return ERR;
			}
			String id = "";
			RecoDao recoDao = new RecoDao();
			for(int i = 0; i < LenIndex; i++){
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			boolean ret  = recoDao.getAdminDivisionID(id);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: 世界各国和地区名称代码为CPC编码调用,(279)中规定编码长度为2-3位，CPC编码为在第4位加0.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用世界各国和地区名称代码的位置
	//LenIndex: 长度是多少，一定是4位
	//creator: zll
	public static String CountryRegionCodeforCPC(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 4 ){
				return ERR;
			}
			for(int i = 0; i < LenIndex - 1; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getCountryRegionCode(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: 世界各国和地区名称代码，(279)中规定编码长度为2-3位.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用世界各国和地区名称代码的位置
	//LenIndex: 长度是多少，一定是2-3位
	//creator: zll
	public static String CountryRegionCode(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(!(LenIndex == 2 || LenIndex == 3)){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getCountryRegionCode(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: 烟草机械产品用物料 分类和编码 第3部分：机械外购件(7)中的5位编码.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用烟草机械产品用物料代码的位置
	//LenIndex: 长度是6位
	//creator: zll
	public static String TabaccoMachineProduct(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 5){
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]);
			String groupCode = String.valueOf(IDstr[Index[1]]) + String.valueOf(IDstr[Index[2]]);
			String variatyCode = String.valueOf(IDstr[Index[3]]) + String.valueOf(IDstr[Index[4]]);
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoMachineProduct(categoryCode, groupCode, variatyCode);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function: 商品条码零售商品编码EAN UPC前3位前缀码.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用前缀码的位置
	//LenIndex: 长度是3位
	//creator: zll
	public static String PrefixofRetailCommodityNumber(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			int num = 0;
			if(LenIndex != 3){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			num = Integer.parseInt(code);
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getPrefixofRetailCommodityNumber(num);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	//Function:  烟草机械物料 分类和编码第2部分：专用件 附录D中的单位编码.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用前缀码的位置
	//LenIndex: 长度是2位，为大写字母
	//creator: zll
	public static String TabaccoMachineProducer(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 2){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoMachineProducer(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  4位行政区号.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用行政区好的位置
	//LenIndex: 长度是4位，为数字
	//creator: zll
	public static String DistrictNo(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 4){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getDistrictNo(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	//Function:  CID满足的域名规则.
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: (18,-1),从18位以后的字符串进行正则表达式验证
	//LenIndex: 长度必为2
	//creator: zll
	public static String CIDRegex(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			String regex = "(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62}){2}\\.cid\\.iot\\.cn";
			int prefix = 18;
			
			if(Index[0] != prefix){
				return ERR;
			}
			for(int i = Index[0]; i < LenID; i++){
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret  = ma.matches();
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  烟草企业标准件编码所需的类别代码，组别代码和品种代码
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用类别代码（1位），组别代码（2位）和品种代码（2位）的位置
	//LenIndex:长度必为5
	//creator: zll
	public static String TabaccoStandardPart(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 5){
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]);
			String groupCode = String.valueOf(IDstr[Index[1]]) + String.valueOf(IDstr[Index[2]]);
			String variatyCode = String.valueOf(IDstr[Index[3]]) + String.valueOf(IDstr[Index[4]]);
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoStandardPart(categoryCode, groupCode, variatyCode);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  烟草机械产品用物料分类和编码 第6部分：原、辅材料
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用类别代码（2位）和品种代码（3位）的位置
	//LenIndex:长度必为5
	//creator: zll
	public static String TabaccoMaterial(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 5){
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]) + String.valueOf(IDstr[Index[1]]);
			String variatyCode = String.valueOf(IDstr[Index[2]]) + String.valueOf(IDstr[Index[3]]) + String.valueOf(IDstr[Index[4]]);
			
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoMaterial(categoryCode, variatyCode);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  国际货运代理单证标识符编码中不定长的企业自定义编码正则匹配,数字或者字母，数字在字母后面。
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用正则的的索引位置
	//LenIndex:长度<=16
	//creator: zll
	public static String IntFreitForwarding(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			String regex = "[a-zA-Z][a-zA-Z0-9]{0,15}";
			int prefix = 2;//18;
			
			if(Index[0] != prefix){
				return ERR;
			}
			//最后一位为校验位
			for(int i = Index[0]; i < LenID - 1; i++){
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret  = ma.matches();
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  粮食信息分类与编码 财务会计分类与代码(15)
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用正则的的索引位置
	//LenIndex:长度必为6
	//creator: zll
	public static String FoodAccount(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 6){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getFoodAccount(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:两位数，第一位为0时，第二位为（0，1，2）；第一位为1时，第二位为（1，2,6,7）；第一位为（2）时，第二位为（0~5） 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, 固定为2
	public static String CPCTwoByte(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2){
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		
		if (IDstr[index1] == '0'){
			if ((IDstr[index2] == '0')||(IDstr[index2] =='1')||IDstr[index2] == '2'){
				return OK;
			}
		}
		
		if (IDstr[index1] == '1'){
			if ((IDstr[index2] == '1') ||(IDstr[index2] == '2')||(IDstr[index2] == '6')||(IDstr[index2] == '7')){
				return OK;
			}     
		}
		
		if (IDstr[index1] == '2'){
			if ((IDstr[index2] >= '0')&&(IDstr[index2] <= '5')){
				return  OK;
			}			
		}
		
		return ERR;
	}
	
	//Function: 判断两个字节是不是代表月份 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, 固定为2
	public static String Month(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		
		if (IDstr[index1] == '0'){
			if ((IDstr[index2] > '0') && (IDstr[index2] <= '9')){
				return OK;
			}			
		}
		
		if (IDstr[index1] == '1'){
			if ((IDstr[index2] >= '0') && (IDstr[index2] <= '2')){
				return OK;
			}			
		}
		return ERR;		
	}
	//Function: 判断六个字节是不是属于LS/T 1704.3-2004表1中的编码
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, 固定为6
	public static String ClassOfGrain1 (char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 6)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		int index3 = Index[2];
		int index4 = Index[3];
		int index5 = Index[4];
		int index6 = Index[5];

		
		
		if (IDstr[index1] == '0' && IDstr[index2] == '1'){
			if (IDstr[index3] == '0'){
				if (IDstr[index4] == '1' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '4'){
							return OK;
						}
					}
				}
				else if (IDstr[index4] == '2' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '5'){
							return OK;
						}
					}
				}
			}
		}
		if (IDstr[index1] == '0' && IDstr[index2] == '2'){
			if (IDstr[index3] == '0'){
				if (IDstr[index4] == '1' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] == '1'|| IDstr[index6] == '0'){
							return OK;
						}
					}
				}
				else if (IDstr[index4] == '2' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '2'){
							return OK;
						}
					}
				}
				else if(IDstr[index4] == '3' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '1'){
							return OK;
						}
					}
				}
			}
		}
		if (IDstr[index1] == '0' && IDstr[index2] == '3'){
			if (IDstr[index3] == '0'){
				if (IDstr[index4] == '1' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '8'){
							return OK;
						}
					}
					else if (IDstr[index5] == '1' ){
						if (IDstr[index6] >= '1'&& IDstr[index6] <= '2'){
							return OK;
						}					
					}
				}
				else if (IDstr[index4] == '2' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '8'){
							return OK;
						}
					}
					else if (IDstr[index5] == '1' ){
						if (IDstr[index6] >= '1'&& IDstr[index6] <= '3'){
							return OK;
						}					
					}
				}
				else if (IDstr[index4] == '3' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] >= '0'&& IDstr[index6] <= '8'){
							return OK;
						}
					}
					else if(IDstr[index5] == '1'&& IDstr[index6] == '1'){
						return OK;
					}
				}
				else if (IDstr[index4] == '4' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] == '0'|| IDstr[index6] == '1'){
							return OK;
						}
					}
				}
				else if (IDstr[index4] == '5' ){
					if(IDstr[index5] == '0' ){
						if (IDstr[index6] == '0'|| IDstr[index6] == '1'){
							return OK;
						}
					}
				}
				
			}
		}
		return ERR;
	}
	
	//Function: 判断2个字节是不是属于(01-07,99)
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, 固定为2
	public static String TwobytleCode07 (char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
	
		if  (IDstr[index1] == '0' ){
			if (IDstr[index2] >= '1' && IDstr[index2] <= '7'){
				return OK;
			}
		}
		
		if  (IDstr[index1] == '9' && IDstr[index2] == '9'){
				return OK;
			}
		
	 return ERR;		
	}
	
	//Function: 判断2个字节是不是属于(01-06,99)
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, 固定为2
	public static String TwobytleCode06 (char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
	
		if  (IDstr[index1] == '0' ){
			if (IDstr[index2] >= '1' && IDstr[index2] <= '6'){
				return OK;
			}
		}
		
		if  (IDstr[index1] == '9' && IDstr[index2] == '9'){
				return OK;
			}		
		return ERR;	 
	}
	
	//Function: UCODE 的Top Level Domain Code: TLDc的取值不可为"E000"和“FFFF”
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes, 固定为2
	public static String CountUcode(char [] IDstr, int LenID, int [] Index, int LenIndex){
		if (LenIndex != 4){
			return "ERR";
		}		
		int index1 = Index[0];
		int index2 = Index[1];
		int index3 = Index[2];
		int index4 = Index[3];		
		if (IDstr[index1] =='E'){
			if ((IDstr[index2] == '0') && (IDstr[index3] == '0')&&(IDstr[index4] == '0')){
				return "ERR";
			}
		}		
		if (IDstr[index1] == 'F'){
			if ((IDstr[index2] == 'F') && (IDstr[index3] == 'F')&&(IDstr[index4] == 'F')){
				return "ERR";
			}     
		}		
		return "OK";
	}

	//Function: EPC编码的域名管理者(Domain Manager)域不能取值为0xA011363及全0
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String DomainManagerInEPCCheck(char [] IDstr, int LenID,
		int [] Index, int LenIndex) {
		int i = 0;
		int Index_k = 0;	//is 0xA011363 or not
		int Zero_k = 0;         //is 0 or not
		char[] str = {'A','0','1','1','3','6','3'};
			
		//the length of domain manager is from 28 to 128
		if(LenIndex<28 || LenIndex>128) {
			return ERR;         
		}
			
		for(i=0;i<LenIndex;i++) {
			if(IDstr[Index[i]] == str[Index_k]) {       //as long as str is in the IDstr,Index_k will be 7
				Index_k++;
				if(Index_k == 7){
					return ERR;
				}
			} else {
				Index_k = 0;
			}
				
			if(IDstr[Index[i]] == '0') {
				Zero_k++;
			}
		}
			
		if(Zero_k == LenIndex) {
			return ERR;
		}			
		return OK;
	}
	
	
}
