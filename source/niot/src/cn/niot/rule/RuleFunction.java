package cn.niot.rule;

import cn.niot.dao.RecoDao;

public class RuleFunction {
	
	static String ERR = "ERR";
	static String OK = "OK";
	public static void main(String[] args) {
		//System.out.println("ƒ„∫√ ¿ΩÁ!");
		//System.out.println("Hello World!");
		char [] IDstr = new char[4];
		IDstr[0] = '0';
		IDstr[1] = '2';
		IDstr[2] = '0';
		IDstr[3] = '1';
		int [] index = new int[4];
		index[0] = 0;
		index[1] = 1;
		index[2] = 2;
		index[3] = 3;
		System.out.println(First4CharsofAdminDivisionforCiga(IDstr, 4, index, 4));
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
	
	//Function: Cigarette department or subordinate department code. There are totally 2 characters.
	//IDstr: ID string. Code range is 00-97.
	//LenID: the number of characters in the ID string 
	//Index: the list of corresponding indexes regarding to this algorithm
	//LenIndex: the number of indexes that must be 2
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
				id = id.concat(String.valueOf(IDstr[i]));
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
}
