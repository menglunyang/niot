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
	
	//Function: decide the cigarette subclass code according to different mainclass code (2)
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
	
	//Function: Cigarette organization code. There are totally 2 characters.(3)
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
	
	//Function: Cigarette department or subordinate department code. There are totally 2 characters.(198)
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

	//Function: 6位行政区划代码.(296)
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
	//Function:  烟草机械物料 分类和编码第2部分：专用件 附录D中的单位编码.(672)
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
	
	//Function:  烟草企业标准件编码所需的类别代码，组别代码和品种代码(6)
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
	
	//Function:  烟草机械产品用物料分类和编码 第6部分：原、辅材料(4)
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
	
	//Function:  国际货运代理单证标识符编码中不定长的企业自定义编码正则匹配,数字或者字母，数字在字母后面。(55)
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用正则的的索引位置
	//LenIndex:长度<=16
	//creator: zll
	public static String IntFreitForwarding(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			String regex = "[a-zA-Z][a-zA-Z0-9]{0,15}";
			int prefix = 18;
			
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
			
			int[] modIndex = new int[LenID];
			for(int i = 0; i < LenID; i++){
				modIndex[i] = i;
			}
			String modRet = MOD112(IDstr, LenID, modIndex, LenID);
			if(ret && modRet.equals(OK)){
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
	//Function:  粮食信息分类与代码 粮食设备分类与代码(23)
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用正则的的索引位置
	//LenIndex:长度必为8
	//creator: zll
	public static String GrainEquipment(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 8){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getGrainEquipment(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  粮食信息分类与编码 粮食设施分类与编码（24）
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用正则的的索引位置
	//LenIndex:长度必为7
	//creator: zll
	public static String GrainEstablishment(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			String code = "";
			if(LenIndex != 7){
				return ERR;
			}
			for(int i = 0; i < LenIndex; i++){
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getGrainEstablishment(code);
			if(ret){
				return OK;
			}else
				return ERR;
		}catch(Exception e){
			return ERR;
		}
	}
	
	//Function:  烟草机械产品用物料 分类和编码 第5部分：电器元器件 （5）
	//IDstr: 标识编码
	//LenID: 标识编码的长度 
	//Index: 调用正则的的索引位置
	//LenIndex:长度必为5
	//creator: zll
	public static String TabaccoElectricComponent(char [] IDstr, int LenID, int [] Index, int LenIndex){
		try{
			if(LenIndex != 5){
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]) + String.valueOf(IDstr[Index[1]]);
			String groupCode = String.valueOf(IDstr[Index[2]]) + String.valueOf(IDstr[Index[3]]) + String.valueOf(IDstr[Index[4]]);
			RecoDao recoDao = new RecoDao();
			boolean ret  = recoDao.getTabaccoElectricComponent(categoryCode, groupCode);
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
	public static String ClassOfGrain (char [] IDstr, int LenID, int [] Index, int LenIndex)
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
	
	//Function: 检验是否属于  序号2开始求出偶数位上数字之和①；①×3=②；从序号3开始求出奇数位上数字之和③；②+③=④；用大于	
	//或等于结果④且为整数倍的最小数减去④得到的值。
	//IDstr: ID string 
	//LenID: the number of characters in the ID string
	//Creator:Wu Zhenyu
	public static String CheckCodeForCommodityCode(char [] IDstr, int LenID, int [] Index, int LenIndex) {		
		char checkcode = 0;
		int i = 0;
			
		// the sum of the odd and even number
		int odd_sum = 0;            
		int even_sum = 0;
			
		for(i=LenIndex-1;i>=0;i-=2) {
			even_sum += (IDstr[i] - 48);           //ASCII码中 字符'0'对应的是30H,十进制就是48
		}
			
		for(i=LenIndex-2;i>=0;i-=2) {
			odd_sum += (IDstr[i] - 48);
		}
			
		if((((even_sum*3 + odd_sum))%10) == 0)
		{
			checkcode = 48;
		} else {
			checkcode =(char)((10 - ((even_sum*3 + odd_sum))%10) + 48);
		}
		
		if(checkcode == IDstr[LenID-1]) {
			return OK;
		} else {
			return ERR;
		}
	}
	
	//Function:12位幢编码：6位 采用竣工时间法，时间未知，全部用"*"；仅知年代，如199***；知年份不知月份，如2008**；
	//		          知道时间，如20080708， 后六位幢顺序号，不能为全0
	//IDstr: ID string 
	//LenID: the number of characters in the ID string is 26
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes is 12
	//Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedCompleteTime(char [] IDstr, int LenID,
			int [] Index, int LenIndex) {
		int i = 0;
		
		if(LenIndex != 12) {
			return ERR;
		}
		
		//当第一次出现"*"时，代表时间的六位代码中，从该位开始后面都是"*"
		for(;i<6;i++) {
			if(IDstr[Index[i]] == '*') {
				int k = i+1;
				
				while(k<6) {
					if(IDstr[Index[k]] == '*') {
						k++;
					} else {
						return ERR;
					}
				}
			}
		}
		
		//判断   月份
		int[] Index_month = {Index[4],Index[5]};
		if((Month(IDstr,LenID,Index_month,Index_month.length)) == ERR) {       //江峰 实现的函数，判断是否是月份
			return ERR;
		}
		
		int zero_count = 0;    //不能全0
		for(i=6;i<LenIndex;i++) {
			if(IDstr[Index[i]] == '0') {
				zero_count++;
				if(zero_count == 6) {
					return ERR;
				}
			} else if(IDstr[Index[i]]<'0' || IDstr[Index[i]]>'9') {
				return ERR;
			}
		}
		return OK;
	}
	
	//Function:12位幢编码,由6位横坐标码与6位纵坐标码组成(坐标法),横纵坐标均取小数点前六位整数   
	//IDstr: ID string 
	//LenID: the number of characters in the ID string is 26
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes is 12
	//Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedCoordinate(char [] IDstr, int LenID,
			int [] Index, int LenIndex) {
		if(LenIndex!=12) {
			return ERR;
		}
		
		//判断横纵坐标的范围
		int i = 0;
		for(;i<LenIndex;i++) {
			if(IDstr[Index[i]]<'0' || IDstr[Index[i]]>'9'){
				return ERR;
			}
		}
		
		return OK;
	}
	
	//Function:12位幢编码,采用分宗法，4位街坊号或房产分区代码、4位宗地号、4位幢顺序号组成，4位幢顺序号0001~9999
	//IDstr: ID string 
	//LenID: the number of characters in the ID string is 26
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes is 12
	//Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedFenzong(char [] IDstr, int LenID,
			int [] Index, int LenIndex) {
		//4位街坊号                房产分区代码，在文档中找到的编号只有两位
		int i = 0;
		int count = 0;
		for(;i<4;i++) {
			if(IDstr[Index[i]] == '0') {
				count++;
				if(count == 4) {
					return ERR;
				}
			}
			else if(IDstr[Index[i]]<'0' || IDstr[Index[i]]>'9') {
				return ERR;
			}
		}
		
		//4位宗地号
		count = 0;
		for(i=4;i<8;i++) {
			if(IDstr[Index[i]] == '0') {
				count++;
				if(count == 4) {
					return ERR;
				}
			} else if(IDstr[Index[i]]>'9' || IDstr[Index[i]]<'0') {
				return ERR;
			}
		}
		
		//4位幢顺序号
		count = 0;
		for(i=8;i<12;i++) {
			if(IDstr[Index[i]] == '0') {
				count++;
				if(count == 4) {
					return ERR;
				}
			} else if(IDstr[Index[i]]>'9' || IDstr[Index[i]]<'0') {
				return ERR;
			}
		}
		
		return OK;
	}
	
	//Function:12位幢编码，采用分幅法，8位分幅图分丘图号和4位幢顺序号组成，4位幢顺序号0001~9999
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes is 12
	//Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedFenfu(char [] IDstr, int LenID,
			int [] Index, int LenIndex) {
		int i = 0;
		
		if(LenIndex != 12) {
			return ERR;
		}
		
		//前八位
		for(;i<8;i++) {
			if(IDstr[Index[i]]<'0' || IDstr[Index[i]]>'9') {
				return ERR;
			}
		}
		
		//后四位顺序号，0001~9999
		int count = 0;
		for(i=8;i<LenIndex;i++) {
			if(IDstr[Index[i]] == '0') {
				count++;
				
				if(count == 4) {
					return ERR;
				}
			}
		}
		return OK;
	}
	
	//Function:按生成户的时间顺序从0001开始，0001~9999
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes is 4
	//Creator:Wu Zhenyu
	public static String HouseCode_CheckUnitCode(char [] IDstr, int LenID,
			int [] Index, int LenIndex) {
		if(LenIndex!=4) {
			return ERR;
		}
		
		int count = 0;
		for(int i=0;i<LenIndex;i++) {
			if(IDstr[Index[i]] == '0') {
				count++;
				
				if(count == 4) {
					return ERR;
				}
			} else if(IDstr[Index[i]]>'9' || IDstr[Index[i]]<'0') {
				return ERR;
			}
		}
		return OK;
	}
		
	//Function: 中间12位幢代码，同时用四种方法
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes is 12
	//Creator:Wu Zhenyu
	public static String HouseCode_CheckTwelBitCode(char [] IDstr, int LenID,
			int [] Index, int LenIndex) {
		String[] result = {
				HouseCode_CheckBasedCompleteTime(IDstr, LenID, Index, LenIndex),
				HouseCode_CheckBasedCoordinate(IDstr, LenID, Index, LenIndex),
				HouseCode_CheckBasedFenzong(IDstr, LenID, Index, LenIndex),
				HouseCode_CheckBasedFenfu(IDstr, LenID, Index, LenIndex)
		};
		
		for(int i=0;i<result.length;i++) {
			if(result[i] == OK) {
				return OK;
			}
		}
		
		return ERR;
	}
	
	//校验码，房屋代码26位，25位本体码，最后一位校验码
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	//Creator:Wu Zhenyu
	public static String HouseCode_CheckCode(char [] IDstr, int LenID,
			int [] Index, int LenIndex) {
		int i = 0;                            
		int result = 10 + (IDstr[0]-48);		//记录校验码计算中间过程产生的值
		
		for(i=1;i<LenIndex;i++ ) {
			if(result%10 == 0) {
				result = (10*2)%11 + (IDstr[i]-48);
			} else {
				result = ((result%10)*2)%11 + (IDstr[i]-48);
			}
		}
		
		if(result == 10) {
			result = (10*2)%11 + (IDstr[i]-48);
		} else {
			result = ((result%10)*2)%11;
		}
		
		char checkcode;
		if(result == 1) {
			checkcode = 48;        //0x0
		} else {
			checkcode = (char)((11-result)+48);
		}		
		
		if(checkcode == IDstr[LenID-1]) {
			return OK;
		} else {
			return ERR;
		}
	}
	
	  //Function: 校验算法 实现 C=11-MOD(∑Ci×Wi,10)
	//其中MOD－表示求余函数；i－表示代码字符从左至右位置序号；Ci－表示第i位置上的代码字符的值；Wi－表示第i位置上的加权因子，
	//加权因子的公式是：2的n-1次幂除以11取余数，n就是那个i，从右向左排列
	//当校检的值为10时 赋值位X
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String DeviceMOD163(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		  // MOD 校验算法，字符串开辟空间时要多一位留给最后加校验位    
	    double sum = 0; // 最后的校验码
	    int i;
	    int j=LenIndex-1;
	    for(i=0;i<LenIndex-1;i++){
	    	sum=sum+Index[i]*(Math.pow(2, LenIndex-i-1)%11);
	    }
		String check;
		int mod=(int) ((11-sum)%10); 
		check=Integer.toString(mod);
	    if(check.equals(Integer.toString(Index[j]))){
			return OK;
			}
			else {
			return ERR;
			}
	}
	
	//Function: represent a decimal integer whose value range is from 00001 to 99999 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String FiveByteDecimalnt(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 5)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		int index3 = Index[2];
		int index4 = Index[3];
		int index5 = Index[4];

		if ((IDstr[index1] == '0') && (IDstr[index2] == '0')&& (IDstr[index3] == '0')&& (IDstr[index4] == '0')&& (IDstr[index5] == '0'))
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
		if ((IDstr[index3] < '0') || (IDstr[index3] > '9'))
		{
			return ERR;
		}
		if ((IDstr[index4] < '0') || (IDstr[index4] > '9'))
		{
			return ERR;
		}
		if ((IDstr[index5] < '0') || (IDstr[index5] > '9'))
		{
			return ERR;
		}

		return OK;		
	}
	
	 //Function: represent a decimal integer whose value range is from 0001 to 9999 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String FourByteDecimalnt(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 4)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		int index3 = Index[2];
		int index4 = Index[3];

		if ((IDstr[index1] == '0') && (IDstr[index2] == '0')&& (IDstr[index3] == '0')&& (IDstr[index4] == '0'))
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
		if ((IDstr[index3] < '0') || (IDstr[index3] > '9'))
		{
			return ERR;
		}
		if ((IDstr[index4] < '0') || (IDstr[index4] > '9'))
		{
			return ERR;
		}

		return OK;		
	}
	
	//Function: 实现模10“隔位乘2”求和的校验  
	//即A-Z换算成10进制的10-35，对新的10进制组成新的数组；对新数组的从右到左开始每一位乘以2或1的循环求和sum
	//校验位的值为 10-sum%10
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes

	public static String InternationalSecurities(char [] IDstr, int LenID, int [] Index, int LenIndex) {
	 int i=0;//用于判断16进制
	 int j;//用于数组的遍历
	 int b=0;
	 int a;
	 a='A';
	  for(j=0;j<LenIndex-1;j++){
		 for(i=0;i<26;i++){
			 char c=(char)(a+i);
			 if(Index[j]==c){
				 Index[j] =10+i;					
			 }
		 }
		 if(Index[j]/10>=1)
			 b++;
	  }
	   b=b+LenIndex-1;
		 int [] nums = new int [b];
		 int e=0;
		  e=b-1;
	  for(j=0;j<LenIndex-1;j++){		
		  if(Index[LenIndex-j-2]/10<1){	
			  nums[e] = Index[LenIndex-j-2];
			  e--;
		  }
		  if(Index[LenIndex-j-2]/10>=1){	  
			  nums[e]=Index[LenIndex-j-2]%10;
			  e--;
			  nums[e] =(int) Math.floor(Index[LenIndex-j-2]/10);
			  e--;
				  }
		
		 
	  }
	  int f;//用于X2的数组遍历
	  int sum=0;//用于接受校验码
	  int check;//用于校验码的值
	  for(f=0;f<b;f++){
		  if((b-f)%2!=0){
		sum=sum+nums[f]*2;
	  }
		  else
	    sum=sum+nums[f]*1;	  
	  }	  
	  if(10-sum%10==10){
		  check=0;
	  }
	  else {
		  check=10-sum%10; 
	  }
	  if(check==Index[LenIndex-1]){
		  return OK;
	  }
	  else{
		  return ERR;
	  }
	}
	
	  //Function: ISO 7064:1983.MOD 11-2校验算法 实现 C=11-MOD(∑Ci×Wi,11)
	//其中MOD－表示求余函数；i－表示代码字符从左至右位置序号；Ci－表示第i位置上的代码字符的值；Wi－表示第i位置上的加权因子，
	//加权因子的公式是：2的n-1次幂除以11取余数，n就是那个i，从右向左排列
	//当校检的值为10时 赋值位X
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String MOD112(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		  // ISO 7064:1983.MOD 11-2校验算法，字符串开辟空间时要多一位留给最后加校验位   
	    double sum = 0; // 最后的校验码
	    int i;
	    int j=LenIndex-1;
	    for(i=0;i<LenIndex-1;i++){
	    	sum=sum+Index[i]*(Math.pow(2, LenIndex-i-1)%11);
	    }
		String check;
		int mod;
	    sum %= 11;
		mod=(int)(12-sum)%11;
		check=Integer.toString(mod);
	    if (mod==10)   
	    {
	      check ="X";   // X表示10
	    } 
	    if(check.equals(Integer.toString(Index[j]))){
				return OK;
			}
			else {
				return ERR;
			}
	}
	
	//Function: 实现校验 MOD 16-3  即16进制的数换算成10进制，对新的10进制的数值乘以权重对16取余；权重位11,9,3,1的循环
	//权重位1~9的循环
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String MOD163(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		  // MOD 16-3校验算法，字符串开辟空间时要多一位留给最后加校验位   
	    double sum = 0; // 最后的求校验码数值
	    int i;
	    int w = 0;//权重
	    int h=0;//十进制
	    int j=LenIndex-1;
	    for(i=0;i<LenIndex;i++){
	    	if((LenIndex-i)%4==0){
	    		w=11;
	    	}
	    	if((LenIndex-i)%4==3){
	    		w=9;
	    	}
	    	if((LenIndex-i)%4==2){
	    		w=3;
	    	}
	    	if((LenIndex-i)%4==1){
	    		w=1;
	    	}
	    	if(Index[i]=='A'){
	    		Index[i]=10;
	    	}
	    	if(Index[i]=='B'){
	    		Index[i]=11;
	    	}
	    	if(Index[i]=='C'){
	    		Index[i]=12;
	    	}
	    	if(Index[i]=='D'){
	    		Index[i]=13;
	    	}
	    	if(Index[i]=='E'){
	    		Index[i]=14;
	    	}
	    	if(Index[i]=='F'){
	    		Index[i]=15;
	    	}
	    	sum=sum+Index[i]*w;
	    }
	    int cd=(int) (sum%16);
		if(cd==10){
    		cd='A';
    	}
		if(cd==11){
    		cd='B';
    	}
		if(cd==12){
    		cd='C';
    	}
		if(cd==13){
    		cd='D';
    	}
		if(cd==14){
    		cd='E';
    	}
		if(cd==15){
    		cd='F';
    	}
	    if(cd==Index[j]){
	    	return OK;
	    }
	    else {
	    	return ERR;
	    }
	}
	
	//Function: 实现校验  
	//即数组奇数位乘以1与偶数位乘以2的和sum
	//校验位的值为 10-sum%10
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String 	MrpCheck(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		  int f;//用于X2的数组遍历
		  int sum=0;//用于接受校验码
		  int check;//用于校验码的值
		  for(f=0;f<LenIndex-1;f++){
			  if((f+1)%2!=0){
			sum=sum+Index[f]*1;
		  }
			  else{
		    sum=sum+Index[f]*2;	  
			  }
		  }	  
		  if(10-sum%10==10){
		  check=0;
		  }
		  else {
		  check=10-sum%10; 
		  }
		  if(check==Index[LenIndex-1]){
			  return OK;
		  }
		  else{
			  return ERR;
		  }
	}
	
	//Function: 实现校验  S = 1+di*wi  i=1...9 权重乘以数值之和加一；检验数为10-(S%10)  当检验数为10时，赋值位0；
	//权重为1~9的循环
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String MusicCheck(char [] IDstr, int LenID, int [] Index, int LenIndex) {
		//从T开始算Index  
		int S=0;
		int S1=0;
		int check=0;
		int i;
		for(i=1;i<LenIndex-1;i++){
			if(i%9!=0){
			S1=Index[i]*(i%9);
			S=S+S1;
			}
			else{
				S1=Index[i]*9;
				S=S+S1;
			}
		}
		S=S+1;
		if(S%9!=0){
		check=10-(S%10);
		}
		else 
			check=0;
		if(check==Index[10]){
			return OK;
		}
		else {
			return ERR;
		}	
	}
	
	  //Function: represent a decimal integer whose value range is from 001 to 999 
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String ThreeByteDecimalnt(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 3)
		{
			return ERR;
		}
		
		int index1 = Index[0];
		int index2 = Index[1];
		int index3 = Index[2];

		if ((IDstr[index1] == '0') && (IDstr[index2] == '0')&& (IDstr[index3] == '0'))
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
		if ((IDstr[index3] < '0') || (IDstr[index3] > '9'))
		{
			return ERR;
		}

		return OK;
		
	}
	
	 //Function: 值只能位SR MX SM YZ
	//IDstr: ID string 
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes
	public static String TwoByteSRMXSMYZ(char [] IDstr, int LenID, int [] Index, int LenIndex)
	{
		if (LenIndex != 2)
		{
			return ERR;
		}
		if ((Index[0]=='S')&&(Index[1] =='R'))
		{
			return OK;
		}
		
		if ((Index[0]=='M')&&(Index[1] =='X'))
		{
			return OK;
		}
		
		if ((Index[0]=='S')&&(Index[1] =='M'))
		{
			return OK;
		}
		if ((Index[0]=='Y')&&(Index[1] =='Z'))
		{
			return OK;
		}

		return ERR;
		
	}
}
