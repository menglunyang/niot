package cn.niot.rule;

import cn.niot.dao.RecoDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleFunction {

	static String ERR = "ERR";
	static String OK = "OK";
	static int NO_LENGHT_LIMIT = -1;

	public static void main(String[] args) {
		// System.out.println("ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½!");
		// System.out.println("Hello World!");
		char[] IDstr = new char[4];
		IDstr[0] = '0';
		IDstr[1] = '2';
		IDstr[2] = '0';
		IDstr[3] = '1';
		int[] index = new int[4];
		index[0] = 0;
		index[1] = 1;
		index[2] = 2;
		index[3] = 3;
		System.out
				.println(First4CharsofAdminDivisionforCiga(IDstr, 4, index, 4));
	}

	// Function: represent a decimal integer whose value range is from 1 to 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	public static String IoTIDLength(String IDstr, int LenID, String parameter,
			int LenIndex) {
		// without length limit
		boolean flag = false;
		if (parameter.charAt(0) == '-') {// -1
			return "OK";
		} else {
			String[] lengthRanges = parameter.split(",");
			for (int i = 0; i < lengthRanges.length; i++) {
				String[] lengthMaxMin = lengthRanges[i].split("-");
				if (lengthMaxMin.length == 1) {// 1ï¿½ï¿½ï¿½ï¿½
					if (lengthMaxMin[0].equalsIgnoreCase(IDstr.length() + "")) {
						return OK;
					} else {
						return ERR;
					}
				} else {
					if (IDstr.length() >= Integer.parseInt(lengthMaxMin[0])
							&& IDstr.length() <= Integer
									.parseInt(lengthMaxMin[1])) {
						flag = true;
					}
				}
			}
		}

		if (flag) {
			return "OK";
		} else {
			return "ERR";
		}
	}

	// Function: represent a decimal integer whose value range is from 1 to 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	public static String IoTIDByte(String input, String parameter,
			String useless, String uselessToo) {

		// String[] byteStrArray = parameter.split(";");
		int[] byteElement = new int[9];
		// for (int i = 0; i < byteStrArray.length; i++) {
		String[] byteElementString = parameter.split(",");
		for (int j = 0; j < byteElementString.length; j++) {
			byteElement[j] = Integer.parseInt(byteElementString[j]);
		}
		int index = byteElement[0];
		if (input.length() <= index) {
			return "ERR";
		}
		char objChar = input.charAt(index);
		int indexChar = 0;
		if (objChar >= '0' && objChar <= '9') {
			indexChar = objChar - '0';
		} else if (objChar >= 'a' && objChar <= 'z') {
			indexChar = objChar - 'a' + 10;
		} else if (objChar >= 'A' && objChar <= 'Z') {
			indexChar = objChar - 'A' + 10 + 26;
		}

		int m = 0;
		int n = 0;
		m = indexChar / 8 + 1;
		n = indexChar % 8;

		if ((byteElement[m] & (1 << n)) == 0) {
			return "ERR";
		}
		// }
		return "OK";
	}

	private static boolean checkInputParam(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			boolean hasSig = false;
			if (LenID != IDstr.length) {
				return false;
			}
			if (LenIndex != Index.length) {
				return false;
			}
			for (int i = 0; i < LenIndex; i++) {

				if (Index[i] == -1) {
					hasSig = true;
					break;
				}

			}
			if (!hasSig) {
				for (int i = 0; i < LenIndex; i++) {
					if (Index[i] < -1 || Index[i] >= LenID) {
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Function: represent a decimal integer whose value range is from 1 to 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator: Dengguangqing
	public static String TwoByteDecimalnt(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = Index[0];
			int index2 = Index[1];

			if ((IDstr[index1] == '0') && (IDstr[index2] == '0')) {
				return ERR;
			}

			if ((IDstr[index1] < '0') || (IDstr[index1] > '9')) {
				return ERR;
			}

			if ((IDstr[index2] < '0') || (IDstr[index2] > '9')) {
				return ERR;
			}

			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: decide the cigarette subclass code according to different
	// mainclass code (2)
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// Index[0] is the index of mainclass code whose values can be int 1, 2, 3,
	// 4, 9
	// Index[1] and Index[2] are the index of subclass codes
	// LenIndex: the number of indexes that must be 3
	// creator: zll
	public static String CigaSubClassCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			// mainclass: 1 subclass: 01, 02, 99
			if (IDstr[Index[0]] == '1') {
				if (IDstr[Index[1]] == '0') {
					if (IDstr[Index[2]] == '1' || IDstr[Index[2]] == '2') {
						return OK;
					}
				} else if (IDstr[Index[1]] == '9') {
					if (IDstr[Index[2]] == '9') {
						return OK;
					}
				}
			} else if (IDstr[Index[0]] == '2') { // mainclass: 2 subclass:
				// 01-09, 10, 99
				if (IDstr[Index[1]] == '0') {
					if (IDstr[Index[2]] > '0' && IDstr[Index[2]] <= '9') {
						return OK;
					}
				} else if (IDstr[Index[1]] == '1') {
					if (IDstr[Index[2]] == '0') {
						return OK;
					}
				} else if (IDstr[Index[1]] == '9') {
					if (IDstr[Index[2]] == '9') {
						return OK;
					}
				}
			} else if (IDstr[Index[0]] == '3') { // mainclass: 3 subclass:
				// 01-08, 99
				if (IDstr[Index[1]] == '0') {
					if (IDstr[Index[2]] > '0' && IDstr[Index[2]] < '9') {
						return OK;
					}
				} else if (IDstr[Index[1]] == '9') {
					if (IDstr[Index[2]] == '9') {
						return OK;
					}
				}
			} else if (IDstr[Index[0]] == '4') { // mainclass: 4 subclass:
				// 01-09, 10, 11, 99
				if (IDstr[Index[1]] == '0') {
					if (IDstr[Index[2]] > '0' && IDstr[Index[2]] <= '9') {
						return OK;
					}
				} else if (IDstr[Index[1]] == '1') {
					if (IDstr[Index[2]] == '0' || IDstr[Index[2]] == '1') {
						return OK;
					}
				} else if (IDstr[Index[1]] == '9') {
					if (IDstr[Index[2]] == '9') {
						return OK;
					}
				}
			} else if (IDstr[Index[0]] == '9') { // mainclass: 9 subclass: 01
				if (IDstr[Index[1]] == '0' && IDstr[Index[2]] == '1') {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: There are four characters. The first two characters are
	// represented as month, the other two
	// characters are represented the date in that month.
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// Index[0] and Index[1] are the index of month
	// Index[2] and Index[3] are the index of date
	// LenIndex: the number of indexes that must be 4
	// creator: zll
	public static String MonthDate(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			int date = Integer.parseInt(String.valueOf(IDstr[Index[2]])) * 10
					+ Integer.parseInt(String.valueOf(IDstr[Index[3]]));
			if (LenIndex != 4) {
				return ERR;
			}
			if (IDstr[Index[0]] == '0') { // month: 01, 03, 05, 07, 08
				if (IDstr[Index[1]] == '1' || IDstr[Index[1]] == '3'
						|| IDstr[Index[1]] == '5' || IDstr[Index[1]] == '7'
						|| IDstr[Index[1]] == '8') {
					if (date <= 31) {
						return OK;
					}
				} else if (IDstr[Index[1]] == '4' || IDstr[Index[1]] == '6'
						|| IDstr[Index[1]] == '9') { // month: 04, 06, 09
					if (date <= 30) {
						return OK;
					}
				} else if (IDstr[Index[1]] == '2') { // month: 02
					if (date <= 29) {
						return OK;
					}
				}
			} else if (IDstr[Index[0]] == '1') { // month: 10, 12
				if (IDstr[Index[1]] == '0' || IDstr[Index[1]] == '2') {
					if (date <= 31) {
						return OK;
					}
				} else if (IDstr[Index[1]] == '1') { // month: 11
					if (date <= 30) {
						return OK;
					}
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Cigarette organization code. There are totally 2 characters.
	// Function: Cigarette organization code. There are totally 2 characters.(3)
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// Index[0] is the index of the first character of cigarette organization
	// code which is 1,2 or 9.
	// Index[1] is the index of the second character.
	// LenIndex: the number of indexes that must be 2
	// creator: zll
	public static String CigaOrgCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			if (IDstr[Index[0]] == '1') {
				if (IDstr[Index[1]] >= '0' && IDstr[Index[1]] <= '9') {
					return OK;
				}
			} else if (IDstr[Index[0]] == '2') {
				if (IDstr[Index[1]] >= '0' && IDstr[Index[1]] <= '3') {
					return OK;
				}
			} else if (IDstr[Index[0]] == '9') {
				if (IDstr[Index[1]] == '9') {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:ï¿½ï¿½Î»ï¿½ï¿½ï¿½Ò»Î»Îª1Ê±ï¿½ï¿½ï¿½Ú¶ï¿½Î»Îªï¿½ï¿½0~9ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ò»Î»Îª2Ê±ï¿½ï¿½ï¿½Ú¶ï¿½Î»Îªï¿½ï¿½0~3ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ò»Î»Îª9Ê±ï¿½ï¿½ï¿½Ú¶ï¿½Î»Îª9.
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, ï¿½Ì¶ï¿½Îª2
	public static String Count(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = Index[0];
			int index2 = Index[1];

			if (IDstr[index1] == '1') {
				if ((IDstr[index2] >= '0') && (IDstr[index2] <= '9')) {
					return OK;
				}
			}

			if (IDstr[index1] == '2') {
				if ((IDstr[index2] >= '0') && (IDstr[index2] <= '3')) {
					return OK;
				}
			}

			if (IDstr[index1] == '9') {
				if (IDstr[index2] == '9') {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Cigarette department or subordinate department code. There are
	// totally 2 characters.
	// Function: Cigarette department or subordinate department code. There are
	// totally 2 characters.(198)
	// IDstr: ID string. Code range is 00-97.
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes that must be 2
	// creator: zll
	public static String CigaDepCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int depCode = Integer.parseInt(String.valueOf(IDstr[Index[0]]))
					* 10 + Integer.parseInt(String.valueOf(IDstr[Index[1]]));
			if (depCode >= 0 && depCode <= 97) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: There are all together 6 chars of administrative division code.
	// This method is to used to
	// connect to the database and get the first 4 chars of division code.
	// IDstr: ID string, the first 4 chars of administrative division code.
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes that must be 4
	// creator: zll
	public static String First4CharsofAdminDivisionforCiga(char[] IDstr,
			int LenID, int[] Index, int LenIndex) {
		try {
			String id = "";
			String append = "00";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			if (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '0') {
				return OK;
			}
			RecoDao recoDao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			id = id.concat(append);
			boolean ret = recoDao.getAdminDivisionID(id);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 6Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½.
	// Function: 6Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ç°2Î».
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex: ï¿½ï¿½ï¿½È±ï¿½ï¿½ï¿½ï¿½ï¿½2Î»
	public static String First2CharsofAdminDivision(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String id = "";
			String append = "0000";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			RecoDao recoDao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			id = id.concat(append);
			boolean ret = recoDao.getAdminDivisionID(id);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 6Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½.(296)
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex: ï¿½ï¿½ï¿½È±ï¿½ï¿½ï¿½ï¿½ï¿½6Î»
	// creator: zll
	public static String AdminDivision(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			String id = "";
			RecoDao recoDao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			boolean ret = recoDao.getAdminDivisionID(id);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Íµï¿½ï¿½ï¿½ï¿½ï¿½Æ´ï¿½ï¿½ï¿½ÎªCPCï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½,(279)ï¿½Ð¹æ¶¨ï¿½ï¿½ï¿½ë³¤ï¿½ï¿½Îª2-3Î»ï¿½ï¿½CPCï¿½ï¿½ï¿½ï¿½Îªï¿½Úµï¿½4Î»ï¿½ï¿½0.
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Íµï¿½ï¿½ï¿½ï¿½ï¿½Æ´ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex: ï¿½ï¿½ï¿½ï¿½ï¿½Ç¶ï¿½ï¿½Ù£ï¿½Ò»ï¿½ï¿½ï¿½ï¿½4Î»
	// creator: zll
	public static String CountryRegionCodeforCPC(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			for (int i = 0; i < LenIndex - 1; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}

			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getCountryRegionCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Íµï¿½ï¿½ï¿½ï¿½ï¿½Æ´ï¿½ï¿½ë£¬(279)ï¿½Ð¹æ¶¨ï¿½ï¿½ï¿½ë³¤ï¿½ï¿½Îª2-3Î».
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Íµï¿½ï¿½ï¿½ï¿½ï¿½Æ´ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex: ï¿½ï¿½ï¿½ï¿½ï¿½Ç¶ï¿½ï¿½Ù£ï¿½Ò»ï¿½ï¿½ï¿½ï¿½2-3Î»
	// creator: zll
	public static String CountryRegionCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			for (int i = 0; i < LenIndex; i++) {
				if (Index[i] < -1 || (Index[i] >= LenID && LenID != 2)) {
					return ERR;
				} else if (Index[i] >= LenID) {
					LenIndex = LenIndex - 1;
				}
			}
			if (!(LenIndex == 2 || LenIndex == 3)) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}

			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getCountryRegionCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ì²Ý»ï¿½Ðµï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½Í±ï¿½ï¿½ï¿½ ï¿½ï¿½3ï¿½ï¿½ï¿½Ö£ï¿½ï¿½ï¿½Ðµï¿½â¹ºï¿½ï¿½(7)ï¿½Ðµï¿½5Î»ï¿½ï¿½ï¿½ï¿½.
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½Ì²Ý»ï¿½Ðµï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ï¿½Ï´ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½6Î»
	// creator: zll
	public static String TabaccoMachineProduct(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]);
			String groupCode = String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]]);
			String variatyCode = String.valueOf(IDstr[Index[3]])
					+ String.valueOf(IDstr[Index[4]]);

			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTabaccoMachineProduct(categoryCode,
					groupCode, variatyCode);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½EAN UPCÇ°3Î»Ç°×ºï¿½ï¿½.
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½Ç°×ºï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½3Î»
	// creator: zll
	public static String PrefixofRetailCommodityNumber(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			int num = 0;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			num = Integer.parseInt(code);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPrefixofRetailCommodityNumber(num);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ì²Ý»ï¿½Ðµï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½Í±ï¿½ï¿½ï¿½ï¿½2ï¿½ï¿½ï¿½Ö£ï¿½×¨ï¿½Ã¼ï¿½ ï¿½ï¿½Â¼Dï¿½ÐµÄµï¿½Î»ï¿½ï¿½ï¿½ï¿½.(672)
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½Ç°×ºï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½2Î»ï¿½ï¿½Îªï¿½ï¿½Ð´ï¿½ï¿½Ä¸
	// creator: zll
	public static String TabaccoMachineProducer(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTabaccoMachineProducer(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 4Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½.
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ãµï¿½Î»ï¿½ï¿½
	// LenIndex: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½4Î»ï¿½ï¿½Îªï¿½ï¿½ï¿½ï¿½
	// creator: zll
	public static String DistrictNo(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getDistrictNo(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: CIDï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½.
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: (18,-1),ï¿½ï¿½18Î»ï¿½Ôºï¿½ï¿½ï¿½Ö·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ê½ï¿½ï¿½Ö¤
	// LenIndex: ï¿½ï¿½ï¿½È±ï¿½Îª2
	// creator: zll
	public static String CIDRegex(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62}){2}\\.cid\\.iot\\.cn";
			int prefix = 18;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ì²ï¿½ï¿½ï¿½Òµï¿½ï¿½×¼ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ë£¬ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Æ·ï¿½Ö´ï¿½ï¿½ï¿½(6)
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ë£¨1Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ë£¨2Î»ï¿½ï¿½ï¿½ï¿½Æ·ï¿½Ö´ï¿½ï¿½ë£¨2Î»ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½È±ï¿½Îª5
	// creator: zll
	public static String TabaccoStandardPart(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]]);
			String groupCode = String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]]);
			String variatyCode = String.valueOf(IDstr[Index[3]])
					+ String.valueOf(IDstr[Index[4]]);

			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTabaccoStandardPart(categoryCode,
					groupCode, variatyCode);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ì²Ý»ï¿½Ðµï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ï¿½Ï·ï¿½ï¿½ï¿½Í±ï¿½ï¿½ï¿½ ï¿½ï¿½6ï¿½ï¿½ï¿½Ö£ï¿½Ô­ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½(4)
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ë£¨2Î»ï¿½ï¿½ï¿½ï¿½Æ·ï¿½Ö´ï¿½ï¿½ë£¨3Î»ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½È±ï¿½Îª5
	// creator: zll
	public static String TabaccoMaterial(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			String variatyCode = String.valueOf(IDstr[Index[2]])
					+ String.valueOf(IDstr[Index[3]])
					+ String.valueOf(IDstr[Index[4]]);

			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTabaccoMaterial(categoryCode, variatyCode);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½Ê»ï¿½ï¿½Ë´ï¿½ï¿½?Ö¤ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ð²ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Òµï¿½Ô¶ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Æ¥ï¿½ï¿½,ï¿½ï¿½ï¿½Ö»ï¿½ï¿½ï¿½ï¿½ï¿½Ä¸ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ä¸ï¿½ï¿½ï¿½æ¡£(55)
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½ï¿½<=16
	// creator: zll
	public static String IntFreitForwarding(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[a-zA-Z][a-zA-Z0-9]{0,15}";
			int prefix = 18;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			// ï¿½ï¿½ï¿½Ò»Î»ÎªÐ£ï¿½ï¿½Î»
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();

			int[] modIndex = new int[LenID];
			for (int i = 0; i < LenID; i++) {
				modIndex[i] = i;
			}
			String modRet = MOD112(IDstr, LenID, modIndex, LenID);
			if (ret && modRet.equals(OK)) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½Ê³ï¿½ï¿½Ï¢ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½(15)
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½È±ï¿½Îª6
	// creator: zll
	public static String FoodAccount(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getFoodAccount(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½Ê³ï¿½ï¿½Ï¢ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ê³ï¿½è±¸ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½(23)
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½È±ï¿½Îª8
	// creator: zll
	public static String GrainEquipment(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainEquipment(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½Ê³ï¿½ï¿½Ï¢ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ê³ï¿½ï¿½Ê©ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ë£¨24ï¿½ï¿½
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½È±ï¿½Îª7
	// creator: zll
	public static String GrainEstablishment(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 7) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainEstablishment(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ì²Ý»ï¿½Ðµï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½Í±ï¿½ï¿½ï¿½ ï¿½ï¿½5ï¿½ï¿½ï¿½Ö£ï¿½ï¿½ï¿½ï¿½ï¿½Ôªï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½5ï¿½ï¿½
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½È±ï¿½Îª5
	// creator: zll
	public static String TabaccoElectricComponent(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			String groupCode = String.valueOf(IDstr[Index[2]])
					+ String.valueOf(IDstr[Index[3]])
					+ String.valueOf(IDstr[Index[4]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTabaccoElectricComponent(categoryCode,
					groupCode);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:ï¿½ï¿½Î»ï¿½ï¿½ï¿½Ò»Î»Îª0Ê±ï¿½ï¿½ï¿½Ú¶ï¿½Î»Îªï¿½ï¿½0ï¿½ï¿½1ï¿½ï¿½2ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ò»Î»Îª1Ê±ï¿½ï¿½ï¿½Ú¶ï¿½Î»Îªï¿½ï¿½1ï¿½ï¿½2,6,7ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ò»Î»Îªï¿½ï¿½2ï¿½ï¿½Ê±ï¿½ï¿½ï¿½Ú¶ï¿½Î»Îªï¿½ï¿½0~5ï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, ï¿½Ì¶ï¿½Îª2
	public static String CPCTwoByte(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = Index[0];
			int index2 = Index[1];

			if (IDstr[index1] == '0') {
				if ((IDstr[index2] == '0') || (IDstr[index2] == '1')
						|| IDstr[index2] == '2') {
					return OK;
				}
			}

			if (IDstr[index1] == '1') {
				if ((IDstr[index2] == '1') || (IDstr[index2] == '2')
						|| (IDstr[index2] == '6') || (IDstr[index2] == '7')) {
					return OK;
				}
			}

			if (IDstr[index1] == '2') {
				if ((IDstr[index2] >= '0') && (IDstr[index2] <= '5')) {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ð¶ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ö½ï¿½ï¿½Ç²ï¿½ï¿½Ç´ï¿½ï¿½ï¿½Â·ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, ï¿½Ì¶ï¿½Îª2
	public static String Month(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = Index[0];
			int index2 = Index[1];

			if (IDstr[index1] == '0') {
				if ((IDstr[index2] > '0') && (IDstr[index2] <= '9')) {
					return OK;
				}
			}

			if (IDstr[index1] == '1') {
				if ((IDstr[index2] >= '0') && (IDstr[index2] <= '2')) {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ð¶ï¿½ï¿½ï¿½ï¿½ï¿½Ö½ï¿½ï¿½Ç²ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½LS/T 1704.3-2004ï¿½ï¿½1ï¿½ÐµÄ±ï¿½ï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, ï¿½Ì¶ï¿½Îª6
	public static String ClassOfGrain(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}

			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			int index4 = Index[3];
			int index5 = Index[4];
			int index6 = Index[5];

			if (IDstr[index1] == '0' && IDstr[index2] == '1') {
				if (IDstr[index3] == '0') {
					if (IDstr[index4] == '1') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] >= '0' && IDstr[index6] <= '4') {
								return OK;
							}
						}
					} else if (IDstr[index4] == '2') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] >= '0' && IDstr[index6] <= '5') {
								return OK;
							}
						}
					}
				}
			}
			if (IDstr[index1] == '0' && IDstr[index2] == '2') {
				if (IDstr[index3] == '0') {
					if (IDstr[index4] == '1') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] == '1' || IDstr[index6] == '0') {
								return OK;
							}
						}
					} else if (IDstr[index4] == '2') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] >= '0' && IDstr[index6] <= '2') {
								return OK;
							}
						}
					} else if (IDstr[index4] == '3') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] >= '0' && IDstr[index6] <= '1') {
								return OK;
							}
						}
					}
				}
			}
			if (IDstr[index1] == '0' && IDstr[index2] == '3') {
				if (IDstr[index3] == '0') {
					if (IDstr[index4] == '1') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] >= '0' && IDstr[index6] <= '8') {
								return OK;
							}
						} else if (IDstr[index5] == '1') {
							if (IDstr[index6] >= '1' && IDstr[index6] <= '2') {
								return OK;
							}
						}
					} else if (IDstr[index4] == '2') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] >= '0' && IDstr[index6] <= '8') {
								return OK;
							}
						} else if (IDstr[index5] == '1') {
							if (IDstr[index6] >= '1' && IDstr[index6] <= '3') {
								return OK;
							}
						}
					} else if (IDstr[index4] == '3') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] >= '0' && IDstr[index6] <= '8') {
								return OK;
							}
						} else if (IDstr[index5] == '1' && IDstr[index6] == '1') {
							return OK;
						}
					} else if (IDstr[index4] == '4') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] == '0' || IDstr[index6] == '1') {
								return OK;
							}
						}
					} else if (IDstr[index4] == '5') {
						if (IDstr[index5] == '0') {
							if (IDstr[index6] == '0' || IDstr[index6] == '1') {
								return OK;
							}
						}
					}

				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ð¶ï¿½2ï¿½ï¿½ï¿½Ö½ï¿½ï¿½Ç²ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½(01-07,99)
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, ï¿½Ì¶ï¿½Îª2
	public static String TwobytleCode07(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = Index[0];
			int index2 = Index[1];

			if (IDstr[index1] == '0') {
				if (IDstr[index2] >= '1' && IDstr[index2] <= '7') {
					return OK;
				}
			}

			if (IDstr[index1] == '9' && IDstr[index2] == '9') {
				return OK;
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ð¶ï¿½2ï¿½ï¿½ï¿½Ö½ï¿½ï¿½Ç²ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½(01-06,99)
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, ï¿½Ì¶ï¿½Îª2
	public static String TwobytleCode06(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = Index[0];
			int index2 = Index[1];

			if (IDstr[index1] == '0') {
				if (IDstr[index2] >= '1' && IDstr[index2] <= '6') {
					return OK;
				}
			}

			if (IDstr[index1] == '9' && IDstr[index2] == '9') {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: UCODE ï¿½ï¿½Top Level Domain Code: TLDcï¿½ï¿½È¡Öµï¿½ï¿½ï¿½ï¿½Îª"E000"ï¿½Í¡ï¿½FFFFï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, ï¿½Ì¶ï¿½Îª4
	public static String CountUcode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return "ERR";
			}
			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			int index4 = Index[3];
			if (IDstr[index1] == 'E') {
				if ((IDstr[index2] == '0') && (IDstr[index3] == '0')
						&& (IDstr[index4] == '0')) {
					return "ERR";
				}
			}
			if (IDstr[index1] == 'F') {
				if ((IDstr[index2] == 'F') && (IDstr[index3] == 'F')
						&& (IDstr[index4] == 'F')) {
					return "ERR";
				}
			}
			return "OK";
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: EPCï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½(Domain Manager)ï¿½ï¿½ï¿½ï¿½È¡ÖµÎª0xA011363ï¿½ï¿½È«0
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// creator: Wu Zhenyu
	public static String DomainManagerInEPCCheck(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			int i = 0;
			int Index_k = 0; // is 0xA011363 or not
			int Zero_k = 0; // is 0 or not
			char[] str = { 'A', '0', '1', '1', '3', '6', '3' };

			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// the length of domain manager is from 28 to 128
			if (LenIndex < 28 || LenIndex > 128) {
				return ERR;
			}

			for (i = 0; i < LenIndex; i++) {
				if (IDstr[Index[i]] == str[Index_k]) { // as long as str is in
					// the IDstr,Index_k
					// will be 7
					Index_k++;
					if (Index_k == 7) {
						return ERR;
					}
				} else {
					Index_k = 0;
				}

				if (IDstr[Index[i]] == '0') {
					Zero_k++;
				}
			}

			if (Zero_k == LenIndex) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½ï¿½ï¿½ï¿½Ç·ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½2ï¿½ï¿½Ê¼ï¿½ï¿½ï¿½Å¼ï¿½ï¿½Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ö®ï¿½Í¢Ù£ï¿½ï¿½Ù¡ï¿½3=ï¿½Ú£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½3ï¿½ï¿½Ê¼ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ö®ï¿½Í¢Û£ï¿½ï¿½ï¿½+ï¿½ï¿½=ï¿½Ü£ï¿½ï¿½Ã´ï¿½ï¿½ï¿½
	// ï¿½ï¿½ï¿½ï¿½Ú½ï¿½ï¿½ï¿½ï¿½ï¿½Îªï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ð¡ï¿½ï¿½ï¿½È¥ï¿½ÜµÃµï¿½ï¿½ï¿½Öµï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Creator:Wu Zhenyu
	public static String CheckCodeForCommodityCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			char checkcode = 0;
			int i = 0;

			// the sum of the odd and even number
			int odd_sum = 0;
			int even_sum = 0;

			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			for (i = LenIndex - 2; i >= 0; i -= 2) {
				even_sum += (IDstr[i] - 48); // ASCIIï¿½ï¿½ï¿½ï¿½ ï¿½Ö·ï¿½'0'ï¿½ï¿½Ó¦ï¿½ï¿½ï¿½ï¿½30H,Ê®ï¿½ï¿½ï¿½Æ¾ï¿½ï¿½ï¿½48
			}

			for (i = LenIndex - 3; i >= 0; i -= 2) {
				odd_sum += (IDstr[i] - 48);
			}

			if ((((even_sum * 3 + odd_sum)) % 10) == 0) {
				checkcode = 48;
			} else {
				checkcode = (char) ((10 - ((even_sum * 3 + odd_sum)) % 10) + 48);
			}

			if (checkcode == IDstr[Index[LenIndex - 1]]) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:12Î»ï¿½ï¿½ï¿½ï¿½ï¿½ë£º6Î» ï¿½ï¿½ï¿½Ã¿ï¿½ï¿½ï¿½Ê±ï¿½ä·¨ï¿½ï¿½Ê±ï¿½ï¿½Î´Öªï¿½ï¿½È«ï¿½ï¿½ï¿½ï¿½"*"ï¿½ï¿½ï¿½ï¿½Öªï¿½ï¿½ï¿½ï¿½ï¿½199***ï¿½ï¿½Öªï¿½ï¿½Ý²ï¿½Öªï¿½Â·Ý£ï¿½ï¿½ï¿½2008**ï¿½ï¿½
	// Öªï¿½ï¿½Ê±ï¿½ä£¬ï¿½ï¿½20080708ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½Ë³ï¿½ï¿½Å£ï¿½ï¿½ï¿½ï¿½ï¿½ÎªÈ«0
	// IDstr: ID string
	// LenID: the number of characters in the ID string is 26
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes is 12
	// Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedCompleteTime(char[] IDstr,
			int LenID, int[] Index, int LenIndex) {
		try {
			int i = 0;

			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 12) {
				return ERR;
			}

			// ï¿½ï¿½ï¿½ï¿½Ò»ï¿½Î³ï¿½ï¿½ï¿½"*"Ê±ï¿½ï¿½ï¿½ï¿½ï¿½Ê±ï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½Ó¸ï¿½Î»ï¿½ï¿½Ê¼ï¿½ï¿½ï¿½æ¶¼ï¿½ï¿½"*"
			for (; i < 6; i++) {
				if (IDstr[Index[i]] == '*') {
					int k = i + 1;

					while (k < 6) {
						if (IDstr[Index[k]] == '*') {
							k++;
						} else {
							return ERR;
						}
					}
				}
			}

			// ï¿½Ð¶ï¿½ ï¿½Â·ï¿½
			int[] Index_month = { Index[4], Index[5] };

			// if ((Month(IDstr, LenID, Index_month, Index_month.length)) ==
			// ERR) { // ï¿½ï¿½ï¿½ï¿½
			// // Êµï¿½ÖµÄºï¿½ï¿½ï¿½ï¿½Ð¶ï¿½ï¿½Ç·ï¿½ï¿½ï¿½ï¿½Â·ï¿½
			// return ERR;
			// }

			// xjf ï¿½Þ¸Äºï¿½
			if (IDstr[Index[4]] != '*' && Index[5] != '*') {
				if ((Month(IDstr, LenID, Index_month, Index_month.length)) == ERR) { // ï¿½ï¿½ï¿½ï¿½
					// Êµï¿½ÖµÄºï¿½ï¿½ï¿½ï¿½Ð¶ï¿½ï¿½Ç·ï¿½ï¿½ï¿½ï¿½Â·ï¿½
					return ERR;
				}
			}

			if (IDstr[Index[4]] != '*' && Index[5] == '*') {
				if ((int) IDstr[Index[4]] - 48 != 0
						|| (int) IDstr[Index[4]] - 48 != 1
						|| (int) IDstr[Index[4]] - 48 != 2) {
					return ERR;
				}
			}

			int zero_count = 0; // ï¿½ï¿½ï¿½ï¿½È«0
			for (i = 6; i < LenIndex; i++) {
				if (IDstr[Index[i]] == '0') {
					zero_count++;
					if (zero_count == 6) {
						return ERR;
					}
				} else if (IDstr[Index[i]] < '0' || IDstr[Index[i]] > '9') {
					return ERR;
				}
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:12Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½,ï¿½ï¿½6Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½6Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½(ï¿½ï¿½ê·¨),ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½È¡Ð¡ï¿½ï¿½ï¿½Ç°ï¿½ï¿½Î»ï¿½ï¿½ï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string is 26
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes is 12
	// Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedCoordinate(char[] IDstr,
			int LenID, int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 12) {
				return ERR;
			}

			// ï¿½Ð¶Ïºï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ä·ï¿½Î§
			int i = 0;
			for (; i < LenIndex; i++) {
				if (IDstr[Index[i]] < '0' || IDstr[Index[i]] > '9') {
					return ERR;
				}
			}

			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:12Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½,ï¿½ï¿½ï¿½Ã·ï¿½ï¿½Ú·ï¿½ï¿½ï¿½4Î»ï¿½Ö·ï¿½ï¿½Å»ò·¿²ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ë¡¢4Î»ï¿½ÚµØºÅ¡ï¿½4Î»ï¿½ï¿½Ë³ï¿½ï¿½ï¿½ï¿½ï¿½É£ï¿½4Î»ï¿½ï¿½Ë³ï¿½ï¿½ï¿½0001~9999
	// IDstr: ID string
	// LenID: the number of characters in the ID string is 26
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes is 12
	// Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedFenzong(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			// 4Î»ï¿½Ö·ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ë£¬ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½Òµï¿½ï¿½Ä±ï¿½ï¿½Ö»ï¿½ï¿½ï¿½ï¿½Î»
			int i = 0;
			int count = 0;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			for (; i < 4; i++) {
				if (IDstr[Index[i]] == '0') {
					count++;
					if (count == 4) {
						return ERR;
					}
				} else if (IDstr[Index[i]] < '0' || IDstr[Index[i]] > '9') {
					return ERR;
				}
			}

			// 4Î»ï¿½ÚµØºï¿½
			count = 0;
			for (i = 4; i < 8; i++) {
				if (IDstr[Index[i]] == '0') {
					count++;
					if (count == 4) {
						return ERR;
					}
				} else if (IDstr[Index[i]] > '9' || IDstr[Index[i]] < '0') {
					return ERR;
				}
			}

			// 4Î»ï¿½ï¿½Ë³ï¿½ï¿½ï¿½
			count = 0;
			for (i = 8; i < 12; i++) {
				if (IDstr[Index[i]] == '0') {
					count++;
					if (count == 4) {
						return ERR;
					}
				} else if (IDstr[Index[i]] > '9' || IDstr[Index[i]] < '0') {
					return ERR;
				}
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:12Î»ï¿½ï¿½ï¿½ï¿½ï¿½ë£¬ï¿½ï¿½ï¿½Ã·Ö·ï¿½8Î»ï¿½Ö·ï¿½Í¼ï¿½ï¿½ï¿½ï¿½Í¼ï¿½Åºï¿½4Î»ï¿½ï¿½Ë³ï¿½ï¿½ï¿½ï¿½ï¿½É£ï¿½4Î»ï¿½ï¿½Ë³ï¿½ï¿½ï¿½0001~9999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes is 12
	// Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedFenfu(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			int i = 0;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 12) {
				return ERR;
			}

			// Ç°ï¿½ï¿½Î»
			for (; i < 8; i++) {
				if (IDstr[Index[i]] < '0' || IDstr[Index[i]] > '9') {
					return ERR;
				}
			}

			// ï¿½ï¿½ï¿½ï¿½Î»Ë³ï¿½ï¿½Å£ï¿½0001~9999
			int count = 0;
			for (i = 8; i < LenIndex; i++) {
				if (IDstr[Index[i]] == '0') {
					count++;

					if (count == 4) {
						return ERR;
					}
				}
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:ï¿½ï¿½ï¿½ï¿½É»ï¿½ï¿½ï¿½Ê±ï¿½ï¿½Ë³ï¿½ï¿½ï¿½0001ï¿½ï¿½Ê¼ï¿½ï¿½0001~9999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes is 4
	// Creator:Wu Zhenyu
	public static String HouseCode_CheckUnitCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}

			int count = 0;
			for (int i = 0; i < LenIndex; i++) {
				if (IDstr[Index[i]] == '0') {
					count++;

					if (count == 4) {
						return ERR;
					}
				} else if (IDstr[Index[i]] > '9' || IDstr[Index[i]] < '0') {
					return ERR;
				}
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½Ð¼ï¿½12Î»ï¿½ï¿½ï¿½ï¿½ï¿½ë£¬Í¬Ê±ï¿½ï¿½ï¿½ï¿½ï¿½Ö·ï¿½ï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes is 12
	// Creator:Wu Zhenyu
	public static String HouseCode_CheckTwelBitCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			String[] result = {
					HouseCode_CheckBasedCompleteTime(IDstr, LenID, Index,
							LenIndex),
					HouseCode_CheckBasedCoordinate(IDstr, LenID, Index,
							LenIndex),
					HouseCode_CheckBasedFenzong(IDstr, LenID, Index, LenIndex),
					HouseCode_CheckBasedFenfu(IDstr, LenID, Index, LenIndex) };

			for (int i = 0; i < result.length; i++) {
				if (result[i] == OK) {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Ð£ï¿½ï¿½ï¿½ë£¬ï¿½ï¿½ï¿½Ý´ï¿½ï¿½ï¿½26Î»ï¿½ï¿½25Î»ï¿½ï¿½ï¿½ï¿½ï¿½ë£¬ï¿½ï¿½ï¿½Ò»Î»Ð£ï¿½ï¿½ï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Wu Zhenyu
	// public static String HouseCode_CheckCode(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// try {
	// int i = 0;
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// int result = 10 + (IDstr[0] - 48); // ï¿½ï¿½Â¼Ð£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ð¼ï¿½ï¿½Ì²ï¿½ï¿½ï¿½ï¿½Öµ
	//
	// for (i = 1; i < LenIndex; i++) {
	// if (result % 10 == 0) {
	// result = (10 * 2) % 11 + (IDstr[i] - 48);
	// } else {
	// result = ((result % 10) * 2) % 11 + (IDstr[i] - 48);
	// }
	// }
	//
	// if (result == 10) {
	// result = (10 * 2) % 11 + (IDstr[i] - 48);
	// } else {
	// result = ((result % 10) * 2) % 11;
	// }
	//
	// char checkcode;
	// if (result == 1) {
	// checkcode = 48; // 0x0
	// } else {
	// checkcode = (char) ((11 - result) + 48);
	// }
	//
	// if (checkcode == IDstr[LenID - 1]) {
	// return OK;
	// } else {
	// return ERR;
	// }
	// } catch (Exception e) {
	// return ERR;
	// }
	// }
	public static String HouseCode_CheckCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		char[] IDstrTemp = new char[LenID];
		for (int k = 0; k < LenID; k++) {
			if ('*' == IDstr[k]) {
				IDstrTemp[k] = '0';
			} else {
				IDstrTemp[k] = IDstr[k];
			}
		}
		int i = 0;
		int j = 10;
		for (i = 0; i < LenIndex - 1; i++) {
			int mode10 = (((int) IDstrTemp[Index[i]] - 48) + j) % 10;
			if (0 == mode10) {
				mode10 = 10;
			}
			j = (mode10 * 2) % 11;
		}
		if ((((int) IDstrTemp[Index[LenIndex - 1]] - 48) + j) % 10 == 1) {
			return OK;
		}
		return ERR;

	}

	// Function: Ð£ï¿½ï¿½ï¿½ã·¨ Êµï¿½ï¿½ C=MOD(11-MOD(ï¿½ï¿½Ciï¿½ï¿½Wi,11),10)
	// ï¿½ï¿½ï¿½ï¿½MODï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½ï¿½àº¯ï¿½ï¿½iï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½ï¿½ï¿½ï¿½Ö·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½ï¿½ï¿½Å£ï¿½Ciï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½iÎ»ï¿½ï¿½ï¿½ÏµÄ´ï¿½ï¿½ï¿½ï¿½Ö·ï¿½ï¿½Öµï¿½ï¿½Wiï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½iÎ»ï¿½ï¿½ï¿½ÏµÄ¼ï¿½È¨ï¿½ï¿½ï¿½Ó£ï¿½
	// ï¿½ï¿½È¨ï¿½ï¿½ï¿½ÓµÄ¹ï¿½Ê½ï¿½Ç£ï¿½2ï¿½ï¿½n-1ï¿½ï¿½ï¿½Ý³ï¿½ï¿½ï¿½11È¡ï¿½ï¿½ï¿½ï¿½nï¿½ï¿½ï¿½ï¿½ï¿½Ç¸ï¿½iï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	// ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ÖµÎª10Ê± ï¿½ï¿½ÖµÎ»X
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	public static String DeviceMOD163(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		// MOD Ð£ï¿½ï¿½ï¿½ã·¨ï¿½ï¿½ï¿½Ö·ï¿½ï¿½Ù¿Õ¼ï¿½Ê±Òªï¿½ï¿½Ò»Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½Î»
		double sum = 0; // ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½
		int i;
		int j = LenIndex - 1;
		for (i = 0; i < LenIndex - 1; i++) {
			sum = sum + (int) (IDstr[Index[i]] - 48)
					* (Math.pow(2, LenIndex - i - 1) % 11);
		}
		String check;
		System.out.println(sum);
		int mod = (int) (11 - (sum % 11));
		check = Integer.toString(mod % 10);
		System.out.println(check);
		System.out.println(mod);
		if (check.equals(Integer.toString((int) IDstr[Index[j]] - 48))) {
			return OK;
		} else {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½ï¿½ëµ°ï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ë£¨232ï¿½ï¿½ï¿½ÐµÄµï¿½ï¿½ëµ°ï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ï¿½Ì¶ï¿½ï¿½ï¿½3
	// Creator:ï¿½?ï¿½ï¿½ 232
	public static String Egg232(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			if (index1 == 1 && (index2 == 1 || index2 == 2)) {
				if (index3 > 0 && index3 < 5) {
					return OK;
				}
			}
			if (index1 == 1 && (index2 == 3)) {
				if (index3 > 0 && index3 < 8) {
					return OK;
				}
			}
			if (index1 == 2 && (index2 == 1 || index2 == 2)) {
				if (index3 > 0 && index3 < 4) {
					return OK;
				}
			}
			if (index1 == 2 && (index2 == 3)) {
				if (index3 > 0 && index3 <= 9) {
					return OK;
				}
			}
			if (index1 == 3 && (index2 == 1 || index2 == 2 || index2 == 3)) {
				if (index3 > 0 && index3 < 3) {
					return OK;
				}
			}
			if (index1 == 4 && (index2 == 1 || index2 == 2 || index2 == 3)) {
				if (index3 > 0 && index3 < 3) {
					return OK;
				}
			}
			if (index1 == 5 && (index2 == 1 || index2 == 2 || index2 == 3)) {
				if (index3 > 0 && index3 <= 9) {
					return OK;
				}
			}
			if (index1 == 6 && (index2 == 1 || index2 == 2 || index2 == 3)) {
				if (index3 > 0 && index3 < 6) {
					return OK;
				}
			}
			if (index1 == 8
					&& (index2 == 1 || index2 == 2 || index2 == 3 || index2 == 4)) {
				if (index3 > 0 && index3 < 6) {
					return OK;
				}
			}
			if (index1 == 7 && (index2 == 1 || index2 == 2 || index2 == 3)) {
				if (index3 > 0 && index3 < 5) {
					return OK;
				}
			}
			if (index1 == 9 && (index2 == 1)) {
				if (index3 > 0 && index3 < 5) {
					return OK;
				}
			}
			if (index1 == 9 && (index2 == 2)) {
				if (index3 > 0 && index3 < 9) {
					return OK;
				}
			}
			if (index1 == 9 && (index2 == 3)) {
				if (index3 > 0 && index3 < 8) {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: represent a decimal integer whose value range is from 00001 to
	// 99999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:ï¿½?ï¿½ï¿½
	public static String FiveByteDecimalnt(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			int index4 = Index[3];
			int index5 = Index[4];
			if ((IDstr[index1] == '0') && (IDstr[index2] == '0')
					&& (IDstr[index3] == '0') && (IDstr[index4] == '0')
					&& (IDstr[index5] == '0')) {
				return ERR;
			}
			if ((IDstr[index1] < '0') || (IDstr[index1] > '9')) {
				return ERR;
			}
			if ((IDstr[index2] < '0') || (IDstr[index2] > '9')) {
				return ERR;
			}
			if ((IDstr[index3] < '0') || (IDstr[index3] > '9')) {
				return ERR;
			}
			if ((IDstr[index4] < '0') || (IDstr[index4] > '9')) {
				return ERR;
			}
			if ((IDstr[index5] < '0') || (IDstr[index5] > '9')) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: represent a decimal integer whose value range is from 0001 to
	// 9999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:ï¿½?ï¿½ï¿½
	public static String FourByteDecimalnt(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			int index4 = Index[3];
			if ((IDstr[index1] == '0') && (IDstr[index2] == '0')
					&& (IDstr[index3] == '0') && (IDstr[index4] == '0')) {
				return ERR;
			}
			if ((IDstr[index1] < '0') || (IDstr[index1] > '9')) {
				return ERR;
			}
			if ((IDstr[index2] < '0') || (IDstr[index2] > '9')) {
				return ERR;
			}
			if ((IDstr[index3] < '0') || (IDstr[index3] > '9')) {
				return ERR;
			}
			if ((IDstr[index4] < '0') || (IDstr[index4] > '9')) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Êµï¿½ï¿½Ä£10ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½2ï¿½ï¿½ï¿½ï¿½Íµï¿½Ð£ï¿½ï¿½
	// ï¿½ï¿½A-Zï¿½ï¿½ï¿½ï¿½ï¿½10ï¿½ï¿½ï¿½Æµï¿½10-35ï¿½ï¿½ï¿½ï¿½ï¿½Âµï¿½10ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Âµï¿½ï¿½ï¿½ï¿½é£»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ä´ï¿½ï¿½Òµï¿½ï¿½ï¿½Ê¼Ã¿Ò»Î»ï¿½ï¿½ï¿½ï¿½2ï¿½ï¿½1ï¿½ï¿½Ñ­ï¿½ï¿½ï¿½ï¿½ï¿½sum
	// Ð£ï¿½ï¿½Î»ï¿½ï¿½ÖµÎª 10-sum%10
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:ï¿½?ï¿½ï¿½
	public static String InternationalSecurities(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			int i = 0; // ï¿½ï¿½ï¿½ï¿½ï¿½Ð¶ï¿½16ï¿½ï¿½ï¿½ï¿½
			int j; // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ä±ï¿½ï¿½ï¿½
			int b = 0;
			char a;
			a = 'A';
			for (j = 0; j < LenIndex - 1; j++) {
				for (i = 0; i < 26; i++) {
					char c = (char) (a + i);

					if ((int) IDstr[Index[j]] == c) {
						IDstr[Index[j]] = (char) (10 + i);

					}
				}
				if (IDstr[Index[j]] > 47) {
					IDstr[Index[j]] = (char) (IDstr[Index[j]] - 48);
				}
				if ((int) IDstr[Index[j]] / 10 >= 1)
					b++;
			}
			b = b + LenIndex - 1;
			int[] nums = new int[b];
			int e = 0;
			e = b - 1;
			for (j = 0; j < LenIndex - 1; j++) {
				if ((int) IDstr[Index[LenIndex - j - 2]] / 10 < 1) {
					nums[e] = (int) IDstr[Index[LenIndex - j - 2]];
					e--;
				}
				if ((int) IDstr[Index[LenIndex - j - 2]] / 10 >= 1) {
					nums[e] = (int) (IDstr[Index[LenIndex - j - 2]] % 10);
					e--;
					nums[e] = (int) Math.floor((int) IDstr[Index[LenIndex - j
							- 2]] / 10);
					e--;
				}
			}
			int f; // ï¿½ï¿½ï¿½ï¿½X2ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
			int sum = 0; // ï¿½ï¿½ï¿½Ú½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½
			int check; // ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½ï¿½Öµ
			int bb = 0;
			int ff = 0;
			for (f = 0; f < b; f++) {
				if ((b - f) % 2 != 0) {
					nums[f] = nums[f] * 2;
					if (nums[f] * 2 > 9) {
						bb++;
					}
				}
			}
			ff = bb + b - 1;
			int[] numss = new int[ff + 1];
			for (f = 0; f < b; f++) {
				if (nums[f] < 10) {
					numss[ff] = nums[f];
					ff--;
				}
				if (nums[f] >= 10) {
					numss[ff] = (int) (nums[f] % 10);
					ff--;
					numss[ff] = (int) 1;
					ff--;
				}

			}
			for (f = 0; f < bb + b; f++) {
				sum = sum + numss[f];
			}
			if (10 - sum % 10 == 10) {
				check = 0;
			} else {
				check = 10 - sum % 10;
			}

			if (check == (int) IDstr[Index[LenIndex - 1]] - 48) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ISO 7064:1983.MOD 11-2Ð£ï¿½ï¿½ï¿½ã·¨ Êµï¿½ï¿½ C=11-MOD(ï¿½ï¿½Ciï¿½ï¿½Wi,11)
	// ï¿½ï¿½ï¿½ï¿½MODï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½ï¿½àº¯ï¿½ï¿½iï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½ï¿½ï¿½ï¿½Ö·ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½ï¿½ï¿½Å£ï¿½Ciï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½iÎ»ï¿½ï¿½ï¿½ÏµÄ´ï¿½ï¿½ï¿½ï¿½Ö·ï¿½ï¿½Öµï¿½ï¿½Wiï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½iÎ»ï¿½ï¿½ï¿½ÏµÄ¼ï¿½È¨ï¿½ï¿½ï¿½Ó£ï¿½
	// ï¿½ï¿½È¨ï¿½ï¿½ï¿½ÓµÄ¹ï¿½Ê½ï¿½Ç£ï¿½2ï¿½ï¿½n-1ï¿½ï¿½ï¿½Ý³ï¿½ï¿½ï¿½11È¡ï¿½ï¿½ï¿½ï¿½nï¿½ï¿½ï¿½ï¿½ï¿½Ç¸ï¿½iï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	// ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ÖµÎª10Ê± ï¿½ï¿½ÖµÎ»X
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:ï¿½?ï¿½ï¿½
	public static String MOD112(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// ISO 7064:1983.MOD 11-2Ð£ï¿½ï¿½ï¿½ã·¨ï¿½ï¿½ï¿½Ö·ï¿½ï¿½Ù¿Õ¼ï¿½Ê±Òªï¿½ï¿½Ò»Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½Î»
			double sum = 0; // ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½
			int i, j;
			int b = LenIndex - 1;
			int a;
			a = 'A';
			for (j = 0; j < LenIndex - 1; j++) {
				for (i = 0; i < 26; i++) {
					char c = (char) (a + i);
					if ((int) IDstr[Index[j]] == c) {
						IDstr[Index[j]] = (char) (10 + i);
					}
				}
			}

			for (i = 0; i < LenIndex - 1; i++) {
				if (IDstr[Index[i]] > 47) {
					IDstr[Index[i]] = (char) (IDstr[Index[i]] - 48);
				}
				sum = sum + (int) (IDstr[Index[i]])
						* (Math.pow(2, LenIndex - i - 1) % 11);
			}
			char check;
			int mod;
			sum %= 11;
			mod = (int) (12 - sum) % 11;
			if (mod == 10) {
				check = "X".charAt(0); // Xï¿½ï¿½Ê¾10
			} else {
				String jieshou = Integer.toString(mod);
				check = jieshou.charAt(0);
			}
			System.out.println(check);
			if (check == (IDstr[Index[b]])) {

				return OK;

			} else {

				return ERR;

			}

		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Êµï¿½ï¿½Ð£ï¿½ï¿½ MOD 16-3 ï¿½ï¿½16ï¿½ï¿½ï¿½Æµï¿½ï¿½ï¿½ï¿½ï¿½ï¿½10ï¿½ï¿½ï¿½Æ£ï¿½ï¿½ï¿½ï¿½Âµï¿½10ï¿½ï¿½ï¿½Æµï¿½ï¿½ï¿½Öµï¿½ï¿½ï¿½ï¿½È¨ï¿½Ø¶ï¿½16È¡ï¿½à£»È¨ï¿½ï¿½Î»11,9,3,1ï¿½ï¿½Ñ­ï¿½ï¿½
	// È¨ï¿½ï¿½Î»1~9ï¿½ï¿½Ñ­ï¿½ï¿½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ï¿½Ì¶ï¿½ï¿½ï¿½16
	// Creator:ï¿½?ï¿½ï¿½
	public static String MOD163(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// MOD 16-3Ð£ï¿½ï¿½ï¿½ã·¨ï¿½ï¿½ï¿½Ö·ï¿½ï¿½Ù¿Õ¼ï¿½Ê±Òªï¿½ï¿½Ò»Î»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½Î»
			double sum = 0; // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Öµ
			int i;
			int w = 0; // È¨ï¿½ï¿½
			int h = 0; // Ê®ï¿½ï¿½ï¿½ï¿½
			int j = LenIndex - 1;
			for (i = 0; i < LenIndex; i++) {
				if ((LenIndex - i) % 4 == 0) {
					w = 11;
				}
				if ((LenIndex - i) % 4 == 3) {
					w = 9;
				}
				if ((LenIndex - i) % 4 == 2) {
					w = 3;
				}
				if ((LenIndex - i) % 4 == 1) {
					w = 1;
				}
				if ((int) IDstr[Index[i]] == 'A') {
					IDstr[Index[i]] = (char) 10;
				}
				if ((int) IDstr[Index[i]] == 'B') {
					IDstr[Index[i]] = (char) 11;
				}
				if ((int) IDstr[Index[i]] == 'C') {
					IDstr[Index[i]] = (char) 12;
				}
				if ((int) IDstr[Index[i]] == 'D') {
					IDstr[Index[i]] = (char) 13;
				}
				if ((int) IDstr[Index[i]] == 'E') {
					IDstr[Index[i]] = (char) 14;
				}
				if ((int) IDstr[Index[i]] == 'F') {
					IDstr[Index[i]] = (char) 15;
				}
				if (IDstr[Index[i]] > 47) {
					IDstr[Index[i]] = (char) (IDstr[Index[i]] - 48);
				}
				sum = sum + (int) IDstr[Index[i]] * w;
			}
			int cd = (int) (sum % 16);

			if (cd == 0) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Êµï¿½ï¿½Ð£ï¿½ï¿½
	// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½ï¿½ï¿½1ï¿½ï¿½Å¼ï¿½ï¿½Î»ï¿½ï¿½ï¿½ï¿½2ï¿½Äºï¿½sum
	// Ð£ï¿½ï¿½Î»ï¿½ï¿½ÖµÎª 10-sum%10
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:ï¿½?ï¿½ï¿½
	public static String MrpCheck(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			int f; // ï¿½ï¿½ï¿½ï¿½X2ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
			int sum = 0; // ï¿½ï¿½ï¿½Ú½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½
			int check; // ï¿½ï¿½ï¿½ï¿½Ð£ï¿½ï¿½ï¿½ï¿½ï¿½Öµ
			int b = 0;
			int j = 0;
			for (f = 0; f < LenIndex - 1; f++) {

				if (IDstr[Index[f]] > 47) {
					IDstr[Index[f]] = (char) (IDstr[Index[f]] - 48);
				}
				if ((f + 1) % 2 == 0) {
					IDstr[Index[f]] = (char) ((int) IDstr[Index[f]] * 2);
					if ((int) (IDstr[Index[f]]) * 2 > 9) {
						b++;
					}
				}
			}
			b = b + LenIndex - 1;
			int[] nums = new int[b];
			int e = 0;
			e = b - 1;
			for (j = 0; j < LenIndex - 1; j++) {
				System.out.print((int) IDstr[Index[j]]);
				if ((int) IDstr[Index[j]] < 10) {
					nums[e] = (int) IDstr[Index[j]];
					e--;
				}
				if ((int) IDstr[Index[j]] > 9) {
					nums[e] = (int) (IDstr[Index[j]] % 10);
					e--;
					nums[e] = (int) Math.floor((int) IDstr[Index[j]] / 10);
					e--;
				}
			}

			for (f = 0; f < b; f++) {
				sum = sum + nums[f];
			}

			System.out.print(sum);
			if (10 - sum % 10 == 10) {
				check = 0;
			} else {
				check = 10 - sum % 10;
			}
			if (check == (int) IDstr[Index[LenIndex - 1]] - 48) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:ï¿½?ï¿½ï¿½
	public static String MusicCheck(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// ï¿½ï¿½Tï¿½ï¿½Ê¼ï¿½ï¿½Index
			int S = 0;
			int S1 = 0;
			int check = 0;
			int i;
			for (i = 1; i < LenIndex; i++) {
				if (i % 9 != 0) {
					;
					S1 = (int) (IDstr[Index[i]] - 48) * (i % 9);
					S = S + S1;
				} else {
					S1 = (int) (IDstr[Index[i]] - 48) * 9;
					S = S + S1;
				}
			}
			S = S + 1;
			if (S % 10 != 0) {
				return ERR;
			}

			else {
				return OK;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: represent a decimal integer whose value range is from 001 to
	// 999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:ï¿½?ï¿½ï¿½
	public static String ThreeByteDecimalnt(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			if ((IDstr[index1] == '0') && (IDstr[index2] == '0')
					&& (IDstr[index3] == '0')) {
				return ERR;
			}
			if ((IDstr[index1] < '0') || (IDstr[index1] > '9')) {
				return ERR;
			}
			if ((IDstr[index2] < '0') || (IDstr[index2] > '9')) {
				return ERR;
			}
			if ((IDstr[index3] < '0') || (IDstr[index3] > '9')) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÖµÖ»ï¿½ï¿½Î»SR MX SM YZ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:ï¿½?ï¿½ï¿½
	public static String TwoByteSRMXSMYZ(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			if ((IDstr[Index[0]] == 'S') && (IDstr[Index[1]] == 'R')) {
				return OK;
			}

			if ((IDstr[Index[0]] == 'M') && (IDstr[Index[1]] == 'X')) {
				return OK;
			}

			if ((IDstr[Index[0]] == 'S') && (IDstr[Index[1]] == 'M')) {
				return OK;
			}
			if ((IDstr[Index[0]] == 'Y') && (IDstr[Index[1]] == 'Z')) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:ï¿½ï¿½Ö¯Æ· ï¿½ï¿½Ö¯ï¿½ï¿½ï¿½ï¿½Ö¯ï¿½ï¿½ï¿½ë¼°Ê¾ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ì¶ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½Ì¶ï¿½
	// creator: xjf
	public static String Weaves355(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int index1 = Index[0];
		try {
			String code = "";
			String regex = "[1-3][0-1]([0-9][0-9])+([0-9][0-9])+";
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if ((IDstr[index1] == '1') && (IDstr[Index[LenIndex - 1]] == '0')
					&& (IDstr[Index[LenIndex - 2]] == '0') && ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ï¿½ï¿½Æ·ï¿½ï¿½ï¿½ï¿½ ï¿½Ê²ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ê¾ï¿½ï¿½ï¿½ï¿½Ïµï¿½Ðºï¿½Îª1-16Î»ï¿½ï¿½Ê¹ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Æ¥ï¿½ï¿½(58)
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ì¶ï¿½
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:0-13Î»ÎªÈ«ï¿½ï¿½É»ï¿½ï¿½ï¿½ï¿½Ê²ï¿½ï¿½ï¿½ï¿½,LenIndexï¿½ï¿½Îª3ï¿½ï¿½ï¿½ï¿½Ò»Î»Îªï¿½ï¿½Ê¼ï¿½ï¿½Î»ï¿½ï¿½Ú¶ï¿½Î»Îªï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ø¸ï¿½ï¿½Ä´ï¿½ï¿½ï¿½,ï¿½ï¿½ï¿½ï¿½Î»Îª-1
	// creator: zll
	public static String GraiSerialNo(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			String code = "";
			int num = Index[1];
			String regex = "[a-zA-Z0-9!\"%&'()*+,-./:;<=>?_]{1,"
					+ String.valueOf(num) + "}";

			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Ò©Æ·ï¿½ï¿½ï¿½Ó¼ï¿½ï¿½ï¿½ï¿½Ó¦ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ò£¬µï¿½IDstr[1]Îª9Ê±ï¿½ï¿½Ó¦ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Îª0,1,2
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½ 20Î»
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½ï¿½Îª1ï¿½ï¿½Ö»ï¿½ï¿½Ö¤IDstr[1]ï¿½Ç·ï¿½Îª9
	// creator: zll
	public static String MedAppCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 1) {
				return ERR;
			}
			if (IDstr[1] == '9') {
				if (!(Index[0] == '0' || Index[0] == '1' || Index[0] == '2')) {
					return ERR;
				}
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}
	// Function: http://zh.wikipedia.org/zh-cn/%E5%9B%BD%E9%99%85%E6%A0%87%E5%87%86%E4%B9%A6%E5%8F%B7
	// IDstr: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½
	// LenID: ï¿½ï¿½Ê¶ï¿½ï¿½ï¿½ï¿½Ä³ï¿½ï¿½ï¿½ 20Î»
	// Index: ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Äµï¿½ï¿½ï¿½ï¿½ï¿½Î»ï¿½ï¿½
	// LenIndex:ï¿½ï¿½ï¿½ï¿½Îª1ï¿½ï¿½Ö»ï¿½ï¿½Ö¤IDstr[1]ï¿½Ç·ï¿½Îª9
	// creator: menglunyang
	public static String ISBN13(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (IDstr[0] == '9' && IDstr[1] == '7'
					&& (IDstr[2] == '8' || IDstr[2] == '9')) {
				int checkNumber = (IDstr[0] - 48) * 1 + (IDstr[1] - 48) * 3
						+ (IDstr[2] - 48) * 1 + (IDstr[3] - 48) * 3
						+ (IDstr[4] - 48) * 1 + (IDstr[5] - 48) * 3
						+ (IDstr[6] - 48) * 1 + (IDstr[7] - 48) * 3
						+ (IDstr[8] - 48) * 1 + (IDstr[9] - 48) * 3
						+ (IDstr[10] - 48) * 1 + (IDstr[11] - 48) * 3;
				checkNumber = checkNumber % 10;
				checkNumber = 10 - checkNumber;
				if (checkNumber == 10 && IDstr[12] == '0'
						|| checkNumber == IDstr[12] - 48)
					return OK;
			}
			return ERR;

<<<<<<< HEAD
	// public static String FireInfoStstion(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// String information;
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int index1 = Index[0];
	// int index2 = Index[1];
	// if ((IDstr[index1] == '1') && (IDstr[index2] == '0')) {
	// information="ÆÕÍ¨Ïû·ÀÕ¾";
	// return OK;
	// }
	// if ((IDstr[index1] == '1') && (IDstr[index2] == '1')) {
	// information="Ò»¼¶ÆÕÍ¨Ïû·ÀÕ¾";
	// return OK;
	// }
	// if ((IDstr[index1] == '1') && (IDstr[index2] == '2')) {
	// information="¶þ¼¶ÆÕÍ¨Ïû·ÀÕ¾";
	// return OK;
	// }
	// if ((IDstr[index1] == '2') && (IDstr[index2] == '0')) {
	// information="ÌØÇÚÏû·ÀÕ¾";
	// return OK;
	// }
	// if ((IDstr[index1] == '6') && (IDstr[index2] == '0')) {
	// information="Ë®ÉÏÏû·ÀÕ¾";
	// return OK;
	// }
	// if ((IDstr[index1] == '7') && (IDstr[index2] == '0')) {
	// information="º½¿ÕÏû·ÀÕ¾";
	// return OK;
	// }
	// if ((IDstr[index1] == '8') && (IDstr[index2] == '0')) {
	// information="Â½µØËÑ¾È»ùµØ";
	// return OK;
	// }
	// if ((IDstr[index1] == '9') && (IDstr[index2] == '0')) {
	// information="ÆäËûÏû·ÀÕ¾";
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }
	// 393Ïû·ÀÐÅÏ¢´úÂë
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator: fdl

	// public static String FireInfoSmoke(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// String inforfation;
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int index1 = Index[0];
	// int index2 = Index[1];
	// if ((IDstr[index1] == '1') && (IDstr[index2] == '0')) {
	// inforfation="×ÔÈ»ÅÅÑÌÏµÍ³";
	// return OK;
	// }
	// if ((IDstr[index1] == '1') && (IDstr[index2] == '1')) {
	// inforfation="¿É¿ªÆôÍâÑÌ×ÔÈ»ÅÅÑÌ";
	// return OK;
	// }
	// if ((IDstr[index1] == '2') && (IDstr[index2] == '0')) {
	// inforfation="»úÐµ·ÀÅÅÑÌÏµÍ³";
	// return OK;
	// }
	// if ((IDstr[index1] == '2') && (IDstr[index2] == '1')) {
	// inforfation="»úÐµ¼ÓÑ¹ËÍ·ç·ÀÑÌ";
	// return OK;
	// }
	// if ((IDstr[index1] == '2') && (IDstr[index2] == '2')) {
	// inforfation="»úÐµÅÅÑÌ";
	// return OK;
	// }
	// if ((IDstr[index1] == '9') && (IDstr[index2] == '0')) {
	// inforfation="ÆäËû·ÀÅÅÑÌÏµÍ³";
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }
	// 395¡ª¡ªÏû·ÀÐÅÏ¢´úÂë
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator: fdl
	public static String FireInfomation(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getFireInfomation395(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// 399¡ª¡ªÏû·ÀÐÅÏ¢´úÂë57²¿·Ö Ïû·À¹©Ë®ÉèÊ©ÖÖÀà
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator: fdl
	// public static String FireInfowatersupply(char[] IDstr, int LenID, int[]
	// Index,
	// int LenIndex){
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// try{
	// String code = "";
	// for(int i=0;i<2;i++){
	// code=code.concat(String.valueOf(IDstr[i]));
	// }
	// RecoDao recoDao = new RecoDao();
	// boolean ret = recoDao.getFireInfomation399(code);
	// if (ret) {
	// return OK;
	// } else
	// return ERR;
	// }catch (Exception e) {
	// return ERR;
	// }
	//
	// }

	// 403¡ª¡ªÏû·ÀÐÅÏ¢´úÂë53²¿·ÖÉç»áÐû´«½ÌÓý»î¶¯
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator: fdl
	// public static String FireInfocamp(char[] IDstr, int LenID, int[] Index,
	// int LenIndex){
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// try{
	// String code = "";
	// for(int i=0;i<2;i++){
	// code=code.concat(String.valueOf(IDstr[i]));
	// }
	// RecoDao recoDao = new RecoDao();
	// boolean ret = recoDao.getFireInfomation403(code);
	// if (ret) {
	// return OK;
	// } else
	// return ERR;
	// }catch (Exception e) {
	// return ERR;
	// }
	//
	// }

	// 409¡ª¡ªÏû·ÀÐÅÏ¢´úÂë48²¿·Ö£ºÏû·ÀÑµÁ·¿¼ºË´úÂë
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator: fdl
	// public static String FireInfotainass(char[] IDstr, int LenID, int[]
	// Index,
	// int LenIndex){
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// try{
	// String code = "";
	// for(int i=0;i<2;i++){
	// code=code.concat(String.valueOf(IDstr[i]));
	// }
	// RecoDao recoDao = new RecoDao();
	// boolean ret = recoDao.getFireInfomation409(code);
	// if (ret) {
	// return OK;
	// } else
	// return ERR;
	// }catch (Exception e) {
	// return ERR;
	// }
	//		
	// }
	// Function: represent a decimal integer whose value range is from 1 to 399
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator: fdl
	public static String ThreeByteDecimalnt1(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			if ((IDstr[index1] == '0') && (IDstr[index2] == '0')
					&& (IDstr[index3] == '0')) {
				return ERR;
			}
			if ((IDstr[index1] < '3') || (IDstr[index1] > '9')) {
				return ERR;
			}
			if ((IDstr[index2] < '0') || (IDstr[index2] > '9')) {
				return ERR;
			}
			if ((IDstr[index3] < '0') || (IDstr[index3] > '9')) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÊµÏÖÐ£Ñé MOD 97-10
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤13
	// Creator:zt
	public static String MOD9710(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 15) {
				return ERR;
			}
			String id1 = "";
			String id2 = "";
			// RecoDao recoDao = new RecoDao();
			for (int i = 0; i < 13; i++) {
				id1 = id1.concat(String.valueOf(IDstr[Index[i]]));
			}

			System.out.println("str id1=" + id1);

			for (int i = 13; i < 15; i++) {
				id2 = id2.concat(String.valueOf(IDstr[Index[i]]));
			}

			System.out.println("str id2=" + id2);

			double input = Double.parseDouble(id1);
			double output = Double.parseDouble(id2);

			System.out.println("int input=" + input);
			System.out.println("int output=" + output);

			if ((98 - (input * 100) % 97) == output) {
				return OK;
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 194-ÑÌ²Ý»úÐµµçÆøÅäÖÃºÍ¼¼ÊõÎÄ¼þ´úÂë¸½Â¼C±í²éÑ¯
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È3Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String tabaccoC(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			int code = IDstr[Index[0]] + IDstr[Index[1] - 96];
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPrefixoftabaccoC(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// Function: 402 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfofailities(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * index1 + index2;
			if (i >= 20 && i <= 24) {
				return OK;
			} else if (i == 29 || i == 10) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 399 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfowatersupply(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			// int Xx = 8;
			int i = 10 * index1 + index2;
			if (i >= 20 && i <= 22) {
				return OK;
			} else if (i >= 30 && i <= 33) {
				return OK;
			} else if (i == 40 || i == 10 || i == 90) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 398 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 4
	// creator: xjf
	public static String FireInfowatersource(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			int index4 = (int) IDstr[Index[3]] - 48;
			// int Xx = 8;
			int i = 1000 * index1 + 100 * index2 + 10 * index3 + index4;
			if (i >= 1110 && i <= 1112) {
				return OK;
			} else if (i >= 1120 && i <= 1122) {
				return OK;
			} else if (i == 1000 || i == 1100 || i == 1200 || i == 1300
					|| i == 1900 || i == 2000 || i == 2100 || i == 2900
					|| i == 9000) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 91-ÖÐ¹úÃºÌ¿±àÂëÏµÍ³ µÚ1-12Î»¶ÔÓ¦¹æÔò
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È 12Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:12
	// creator:fdl
	public static String CoalInterment(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 12) {
				return ERR;
			}
			if ((IDstr[Index[0]] == '0' && IDstr[Index[1]] == '2')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '3')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '4')
					|| (IDstr[Index[0]] == '1' && IDstr[Index[1]] == '9')
					|| (IDstr[Index[0]] == '5' && IDstr[Index[1]] == '0')) {
				if ((IDstr[Index[2]] == '2' && IDstr[Index[3]] == '4')
						|| (IDstr[Index[2]] == '2' && IDstr[Index[3]] == '5')
						|| (IDstr[Index[2]] == '3' && IDstr[Index[3]] == '5')
						|| (IDstr[Index[2]] == '3' && IDstr[Index[3]] == '9')
						|| (IDstr[Index[2]] == '1' && IDstr[Index[3]] == '1')
						|| (IDstr[Index[2]] == '1' && IDstr[Index[3]] == '2')
						|| (IDstr[Index[2]] == '1' && IDstr[Index[3]] == '3')
						|| (IDstr[Index[2]] == '2' && IDstr[Index[3]] == '2')
						|| (IDstr[Index[2]] == '2' && IDstr[Index[3]] == '3')) {
					if ((IDstr[Index[4]] == '0' && IDstr[Index[5]] == '1')
							|| (IDstr[Index[4]] == '0' && IDstr[Index[5]] == '2')
							|| (IDstr[Index[4]] == '0' && IDstr[Index[5]] == '9')
							|| (IDstr[Index[4]] == '1' && IDstr[Index[5]] == '0')
							|| (IDstr[Index[4]] == '4' && IDstr[Index[5]] == '9')) {
						return OK;
					}

				} else
					return ERR;

			} else
				return ERR;

			if (IDstr[Index[6]] == '0' || IDstr[Index[6]] == '1'
					|| IDstr[Index[6]] == '2' || IDstr[Index[6]] == '3'
					|| IDstr[Index[6]] == '4' || IDstr[Index[6]] == '5'
					|| IDstr[Index[6]] == '6') {
				return OK;
			}
			// else return ERR;

			if (IDstr[Index[7]] == '1' || IDstr[Index[7]] == '2'
					|| IDstr[Index[7]] == '3' || IDstr[Index[7]] == '4'
					|| IDstr[Index[7]] == '5') {
				return OK;
			}
			if ((IDstr[Index[8]] == '0' && IDstr[Index[9]] == '0')
					|| (IDstr[Index[8]] == '0' && IDstr[Index[9]] == '1')
					|| (IDstr[Index[8]] == '0' && IDstr[Index[9]] == '2')
					|| (IDstr[Index[8]] == '2' && IDstr[Index[9]] == '9')
					|| (IDstr[Index[8]] == '3' && IDstr[Index[9]] == '0')) {
				if ((IDstr[Index[10]] == '0' && IDstr[Index[11]] == '0')
						|| (IDstr[Index[10]] == '0' && IDstr[Index[11]] == '1')
						|| (IDstr[Index[10]] == '0' && IDstr[Index[11]] == '2')
						|| (IDstr[Index[10]] == '3' && IDstr[Index[11]] == '1')
						|| (IDstr[Index[10]] == '3' && IDstr[Index[11]] == '2')) {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 393 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfoSmoke(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * index1 + index2;
			if (i >= 10 && i <= 11) {
				return OK;
			} else if (i >= 20 && i <= 22) {
				return OK;
			} else if (i == 90) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 394 ExtinguishFire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String ExtinguishFire(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			int i = 100 * index1 + 10 * index2 + index3;
			if (i >= 110 && i <= 114) {
				return OK;
			} else if (i >= 119 && i <= 122) {
				return OK;
			} else if (i >= 210 && i <= 214) {
				return OK;
			} else if (i >= 310 && i <= 323) {
				return OK;
			} else if (i >= 329 && i <= 333) {
				return OK;
			} else if (i >= 410 && i <= 413) {
				return OK;
			} else if (i >= 419 && i <= 423) {
				return OK;
			} else if (i == 100 || i == 129 || i == 180 || i == 190 || i == 200
					|| i == 219 || i == 300 || i == 339 || i == 380 || i == 390
					|| i == 400 || i == 429) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 90-ÉÌÆ·ÌõÂë¡ª¡ª²ÎÓë·½Î»±àÂëÓëÌõÂë±íÊ¾ µÚ13Ð£ÑéÂë
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È 13Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:13
	// creator:fdl
	public static String CheckCodebarcode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 13) {
				return ERR;
			}
			String id1 = "";
			int sum = 0;
			int sum1 = 0;// Å¼ÊýÐòºÅÎ»ÉÏµÄÊýÖµºÍ
			int sum2 = 0;// ÆæÊýÐòºÅÎ»ÉÏµÄÊýÖµºÍ
			int dd = 0;// sumºÍÕû³ýÖÐ¼äÊý
			int code = 0;// ×îºóÒ»Î»ÑéÖ¤Âë

			for (int i = 1; i < 12; i++) {
				if (i % 2 == 1) {
					sum1 = sum1 + (IDstr[i]);
					sum1 = sum1 - 48;// ×Ö·û×ª»¯ÎªÕûÐÎ
					System.out.println((int) IDstr[i]);
				}
			}
			System.out.println("sum1=" + sum1);
			sum1 = sum1 * 3;
			for (int i = 0; i < 11; i++) {
				if (i % 2 == 0) {
					sum2 += IDstr[i];
					sum2 = sum2 - 48;// ×Ö·û×ª»¯ÎªÕûÐÎ
					System.out.println(IDstr[i]);
				}
			}

			System.out.println("sum2=" + sum2);
			sum = sum1 + sum2;
			System.out.println("sum=" + sum);
			dd = sum / 10;
			dd += 1;
			dd = dd * 10;
			code = dd - sum;
			if (code == 10) {
				if ((int) IDstr[12] - 48 == 0) {
					return OK;
				}
			}
			System.out.println("ÑéÖ¤ÂëÎ»=" + code);
			if (code == (int) IDstr[12] - 48) {
				return OK;
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 195-ÑÌ²Ý»úÐµ¡ª¡ª²úÆ·¹¤ÒÕÎÄ¼þ´úÂë±àÖÆ·½·¨ ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È3Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String Tobaccomachinery(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			int code = IDstr[Index[0]] + IDstr[Index[1] - 96];
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPrefixofRetailCommodityNumber(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// Function: 384 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfoBuild(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			// int Xx = 8;
			int i = 10 * index1 + index2;
			if (i >= 10 && i <= 14) {
				return OK;
			} else if (i >= 19 && i <= 25) {
				return OK;
			} else if (i >= 29 && i <= 32) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 381 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfoStstion(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * index1 + index2;
			if (i >= 10 && i <= 12) {
				return OK;
			} else if (i == 20 || i == 60 || i == 70 || i == 80 || i == 90) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 362
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String PositionClass(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			if (i1 == 1) { // gongwuyuan
				if (i2 >= 0 && i2 <= 4) {
					if (i3 == 1 || i3 == 2) {
						return OK;
					}
				}
				if (i2 == 5 || i2 == 6) {
					if (i3 == 0) {
						return OK;
					}
				}
				if (i2 == 9) {
					if (i3 == 9) {
						return OK;
					}
				}
			}
			if (i1 == 2) { // Ö°Ô±¼¶±ð
				if (i2 >= 1 && i2 <= 4) {
					if (i3 == 1 || i3 == 2) {
						return OK;
					}
				}
				if (i2 == 5 || i2 == 6) {
					if (i3 == 0) {
						return OK;
					}
				}
				if (i2 == 9) {
					if (i3 == 9) {
						return OK;
					}
				}
			}
			if (i1 == 4) { // ×¨Òµ¼¼ÊõÖ°Îñ¼¶±ð
				if (i2 == 1) {
					if (i3 == 1 || i3 == 2 || i3 == 0) {
						return OK;
					}
				}
				if (i2 == 2) {
					if (i3 == 0) {
						return OK;
					}
				}
				if (i2 == 3) {
					if (i3 == 0 || i3 == 4 || i3 == 5) {
						return OK;
					}
				}
				if (i2 == 9) {
					if (i3 == 9) {
						return OK;
					}
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÊµÏÖÐ£ÑéNÎ»ÊýÐ£Ñé
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:·½µ¤Àö
	public static String Mod36_37(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 15) {
				return ERR;
			}
			int[] a = new int[15];
			for (int i = 0; i < 15; i++) {
				a[14 - i] = IDstr[i] - 48;
			}
			int[] p = new int[15];
			int[] s = new int[15];
			int r = 2;
			p[0] = 0;

			for (int i = 0; i < 14; i++) {
				s[i] = p[i] + a[14 - i];
				p[i + 1] = s[i] * r;
				System.out.println("s[i]=" + s[i]);
				System.out.println("p[i]=" + p[i]);
			}

			if ((p[14] + a[0]) % 36 == 1 || (p[14] + a[0]) % 37 == 1) {
				return OK;
			}
			System.out.println("(p[14]+a[0])%36=" + (p[14] + a[0]) % 36);
			System.out.println("(p[14]+a[0])%37=" + (p[14] + a[0]) % 37);
			return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÊµÏÖÐ£Ñé6Î»ÊýÎïÁ÷±àÂë
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:·½µ¤Àö
	// Ð£ÑéÀý×ÓÂë123450
	public static String LogisticsCheck(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			int[] a = new int[6];
			int sum = 0;
			for (int i = 0; i < 6; i++) {
				a[i] = IDstr[i] - 48;
			}
			sum = 5 * a[1] + 4 * a[2] + 3 * a[3] + 2 * a[4];

			System.out.println("sum=" + sum);
			if (sum % 11 == a[5]) {
				return OK;
			}

			return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}

	// 268-¡ª¡ªÖéÈ«¹úÈË´óÕþÐ­»ú¹¹·ÖÀà´úÂë±àÖÆ·½·¨ ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È3Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String TheCenteralPartyCommitte(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 3; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff268(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 270¡ª¡ª×ÔÈ»ÔÖº¦Natural disaster
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È6Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String Naturaldisaster(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 6) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 6; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff270(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 275¡ªÎïÁ÷×÷Òµ»õÎï
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È4Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String Logisticsoperation(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff275(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 276¡ª·ÏÆúÎïÆ·»õÎï
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È4Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String Wasteproducts(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff276(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 281-¡ª¡ªÖé±¦ÓñÊ¯¼°½ðÊô²ÄÖÊ·ÖÀà´úÂë±àÖÆ·½·¨ ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È6Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String JadejewelryMaterialclassif(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 6) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 6; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariffMa281(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 216-¡ª¡ªÖÐÑëµ³Õþ»ú¹Ø´úÂë±àÖÆ·½·¨ ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È3Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// creator:fdl
	// public static String CodeHighway(char[] IDstr, int LenID, int[] Index,
	// int LenIndex){
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// >>>>>>> d13c71108acbf43f3dfd97d9e4d7bc7d72717828
	// }
	// }
	// if (i2 == 5 || i2 == 6) {
	// if (i3 == 0) {
	// return OK;
	// }
	// }
	// if (i2 == 9) {
	// if (i3 == 9) {
	// return OK;
	// }
	// }
	// }
	// if (i1 == 2) { // Ö°Ô±¼¶±ð
	// if (i2 >= 1 && i2 <= 4) {
	// if (i3 == 1 || i3 == 2) {
	// return OK;
	// }
	// }
	// if (i2 == 5 || i2 == 6) {
	// if (i3 == 0) {
	// return OK;
	// }
	// }
	// if (i2 == 9) {
	// if (i3 == 9) {
	// return OK;
	// }
	// }
	// }
	// if (i1 == 4) { // ×¨Òµ¼¼ÊõÖ°Îñ¼¶±ð
	// if (i2 == 1) {
	// if (i3 == 1 || i3 == 2 || i3 == 0) {
	// return OK;
	// }
	// }
	// if (i2 == 2) {
	// if (i3 == 0) {
	// return OK;
	// }
	// }
	// if (i2 == 3) {
	// if (i3 == 0 || i3 == 4 || i3 == 5) {
	// return OK;
	// }
	// }
	// if (i2 == 9) {
	// if (i3 == 9) {
	// return OK;
	// }
	// }
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }

	// Function: 365
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String EconomicCate(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			if (i1 == 1) {
				if (i2 >= 0 && i2 <= 7 || i2 == 9) {
					if (i3 == 0) {
						return OK;
					}
				}
				if (i2 == 4) {
					if (i3 == 1 || i3 == 2 || i3 == 3 || i3 == 9) {
						return OK;
					}
				}
				if (i2 == 5) {
					if (i3 == 1 || i3 == 9) {
						return OK;
					}
				}
				if (i2 == 7) {
					if (i3 == 1 || i3 == 2 || i3 == 3 || i3 == 4 || i3 == 5
							|| i3 == 9) {
						return OK;
					}
				}
			}
			if (i1 == 2) {
				if (i2 >= 0 && i2 <= 4 || i2 == 9) {
					if (i3 == 0) {
						return OK;
					}
				}
			}
			if (i1 == 3) {
				if (i2 >= 0 && i2 <= 4 || i2 == 9) {
					if (i3 == 0) {
						return OK;
					}
				}
			}
			if (i1 == 9) {
				if (i2 == 0) {
					if (i3 == 0) {
						return OK;
					}
				}

			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 367
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String CodeUnderCount(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			int i = 100 * i1 + 10 * i2 + i3;
			if (i >= 0 && i <= 399) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 368
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String NonMotorVehicle(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			if (i1 == 7) {
				if (i2 >= 1 && i2 <= 3) {
					if (i3 == 0 || i3 == 1 || i3 == 2) {
						return OK;
					}
				}
				if (i2 == 0) {
					if (i3 == 0) {
						return OK;
					}
				}
				if (i2 == 2) {
					if (i3 == 3) {
						return OK;
					}
				}
			}
			if (i1 == 8) {
				if (i2 == 0) {
					if (i3 == 0) {
						return OK;
					}
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 370
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String PoliticalCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + 1 * i2;
			if (i >= 1 && i <= 13) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 206
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String CodeTobacco(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			int i = 100 * i1 + 10 * i2 + i3;
			;
			if (i >= 101 && i <= 113 && i != 110) {
				return OK;
			}
			if (i == 203) {
				return OK;
			}
			if (i >= 301 && i <= 319) {
				return OK;
			}
			if (i >= 330 && i <= 341) {
				return OK;
			}
			if (i >= 401 && i <= 409) {
				return OK;
			}
			if (i >= 501 && i <= 504) {
				return OK;
			}
			if (i == 521 || i == 522) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 245
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String CodeHighway(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			if (i1 == 1) {
				if (i2 >= 0 && i2 <= 2) {
					return OK;
				}
			}
			if (i1 == 2) {
				if (i2 >= 0 && i2 <= 3) {
					return OK;
				}
			}
			if (i1 == 3) {
				if (i2 >= 0 && i2 <= 3) {
					return OK;
				}
			}
			if (i1 == 4) {
				if (i2 >= 0 && i2 <= 2) {
					return OK;
				}
			}
			if (i1 == 5 || i1 == 9) {
				if (i2 == 0) {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 246
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String CodeDiscillinary(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			if (i1 == 1) {
				if (i2 >= 0 && i2 <= 4 || i2 == 7 || i2 == 9) {
					return OK;
				}
			}
			if (i1 == 2) {
				if (i2 >= 0 && i2 <= 4) {
					return OK;
				}
				if (i2 >= 7 && i2 <= 9) {
					return OK;
				}
			}
			if (i1 == 3) {
				if (i2 >= 0 && i2 <= 7 || i2 == 9) {
					return OK;
				}
			}
			if (i1 == 4) {
				if (i2 >= 0 && i2 <= 1) {
					return OK;
				}
				if (i2 >= 7 && i2 <= 9) {
					return OK;
				}
			}
			if (i1 == 5) {
				if (i2 >= 0 && i2 <= 1) {
					return OK;
				}
				if (i2 >= 7 && i2 <= 9) {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 321
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String Climatic(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + 1 * i2;
			if (i == 11) {
				if (IDstr[Index[2]] == 'A') {
					return OK;
				}
			}
			if (i == 12) {
				if (IDstr[Index[2]] == 'A' || IDstr[Index[2]] == 'B'
						|| IDstr[Index[2]] == 'C' || IDstr[Index[2]] == 'D'
						|| IDstr[Index[2]] == 'E') {
					return OK;
				}
			}
			if (i == 13) {
				if (IDstr[Index[2]] == 'A' || IDstr[Index[2]] == 'B'
						|| IDstr[Index[2]] == 'D' || IDstr[Index[2]] == 'E') {
					return OK;
				}
			}
			if (i == 21) {
				if (IDstr[Index[2]] == 'A') {
					return OK;
				}
			}
			if (i == 22) {
				if (IDstr[Index[2]] == 'A') {
					return OK;
				}
			}
			if (i == 23) {
				if (IDstr[Index[2]] == 'A' || IDstr[Index[2]] == 'B') {
					return OK;
				}
			}
			if (i == 29) {
				if (IDstr[Index[2]] == 'A') {
					return OK;
				}
			}
			if (i == 31) {
				if (IDstr[Index[2]] == 'A' || IDstr[Index[2]] == 'B') {
					return OK;
				}
			}
			if (i == 32) {
				if (IDstr[Index[2]] == 'A') {
					return OK;
				}
			}
			if (i == 33) {
				if (IDstr[Index[2]] == 'A') {
					return OK;
				}
			}
			if (i == 41) {
				if (IDstr[Index[2]] == 'D') {
					return OK;
				}
			}
			if (i == 42) {
				if (IDstr[Index[2]] == 'A' || IDstr[Index[2]] == 'B'
						|| IDstr[Index[2]] == 'C') {
					return OK;
				}
			}
			if (i == 43) {
				if (IDstr[Index[2]] == 'A' || IDstr[Index[2]] == 'B'
						|| IDstr[Index[2]] == 'C') {
					return OK;
				}
			}
			if (i == 44) {
				if (IDstr[Index[2]] == 'B' || IDstr[Index[2]] == 'C') {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 266
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 1,2,3,4
	// creator: xjf
	public static String FamilyRelationship(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex == 1) {
				int i1 = (int) IDstr[Index[0]] - 48;
				if (i1 >= 0 && i1 <= 8) {
					return OK;
				}
			}
			if (LenIndex == 2) {
				int i1 = (int) IDstr[Index[0]] - 48;
				int i2 = (int) IDstr[Index[1]] - 48;
				int i = 10 * i1 + 1 * i2;
				if (i == 1 || i == 2) {
					return OK;
				}
				if (i == 10 || i == 12 || i == 11) {
					return OK;
				}
				if (i >= 20 && i <= 97 || i == 99) {
					return OK;
				}
			}
			if (LenIndex == 3) {
				int i1 = (int) IDstr[Index[0]] - 48;
				int i2 = (int) IDstr[Index[1]] - 48;
				int i = 10 * i1 + 1 * i2;
				int i3 = (int) IDstr[Index[2]] - 48;
				if (i == 1 || i == 2) {
					if (i3 >= 0 && i3 <= 8) {
						return OK;
					}
				}
				if (i == 10 || i == 12 || i == 11) {
					if (i3 >= 0 && i3 <= 8) {
						return OK;
					}
				}
				if (i >= 20 && i <= 97 || i == 99) {
					if (i3 >= 0 && i3 <= 8) {
						return OK;
					}
				}
			}
			if (LenIndex == 4) {
				int i1 = (int) IDstr[Index[0]] - 48;
				int i2 = (int) IDstr[Index[1]] - 48;
				int i = 10 * i1 + 1 * i2;
				int flat = 0;
				if (i == 1 || i == 2) {
					flat = 0;
				}
				if (i == 10 || i == 12 || i == 11) {
					flat = 0;
				}
				if (i >= 20 && i <= 97 || i == 99) {
					flat = 0;
				}
				if (flat == 0) {
					int i3 = (int) IDstr[Index[2]] - 48;
					int i4 = (int) IDstr[Index[3]] - 48;
					i = 10 * i3 + 1 * i4;
					if (i == 1 || i == 2 || i == 0) {
						return OK;
					}
					if (i == 10 || i == 12 || i == 11 || i == 0) {
						return OK;
					}
					if (i >= 20 && i <= 97 || i == 99 || i == 0) {
						return OK;
					}
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 417 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: zll
	public static String FirevehicleState(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 20 && i <= 27) {
				return OK;
			}
			if (i1 == 9 || i1 == 1 || (i1 >= 3 && i1 <= 7)) {
				if (i2 == 0) {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 420 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: zll
	public static String FireCauseofcasuslty(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 01 && i <= 07) {
				return OK;
			}
			if (i == 99) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 421 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String FireDisposalstate(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 01 && i <= 12) {
				return OK;
			}
			if (i == 99) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 422 423 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String FireDevelopstateORcategory(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 1) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			if (i1 >= 1 && i1 <= 6) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 422 423 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String FireRating(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 1) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			if (i1 >= 1 && i1 <= 4) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 426 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String Firereading(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 1) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			if (i1 >= 1 && i1 <= 3) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 427 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String FirerescuePlan(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 1) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			if (i1 >= 1 && i1 <= 5) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 428 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 5
	// creator: xjf
	public static String FireFightrescuePlan(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i2 = (int) IDstr[Index[2]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i3 = (int) IDstr[Index[3]] - 48;
			int i4 = (int) IDstr[Index[4]] - 48;
			int flag = 0;
			int i = 10 * i1 + i2;
			if (i0 == 0 && i3 == 0 && i4 == 0) {
				flag = 1;
			}
			if (i >= 10 && i <= 15 && flag == 1) {
				return OK;
			}
			if (i >= 19 && i <= 35 && flag == 1) {
				return OK;
			}
			if (i >= 39 && i <= 40 && flag == 1) {
				return OK;
			}
			if (i == 50 || i == 60 || i == 90 || i == 89) {
				if (flag == 1) {
					return OK;
				}
			}
			if (i >= 70 && i <= 76 && flag == 1) {
				return OK;
			}
			if (i >= 79 && i <= 85 && flag == 1) {
				return OK;
			}
			if (i0 == 1 && i3 == 0 && i4 == 0 && i == 0) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 430 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String Firealarm(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 10 && i <= 13) {
				return OK;
			}
			if (i >= 20 && i <= 23) {
				return OK;
			}
			if (i == 29) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 436 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireautomaticSystem(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 30 && i <= 32) {
				return OK;
			}
			if (i >= 20 && i <= 22) {
				return OK;
			}
			if (i >= 40 && i <= 43) {
				return OK;
			}
			if (i == 10) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 437 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String Firelocation(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 10 && i <= 12 || i == 20) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 438 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String Fireconstructionlicence(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 10 && i <= 13 || i == 90) {
				return OK;
			}
			if (i >= 19 && i <= 20) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 444 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 4
	// creator: xjf
	public static String Firesupervisionenforcement(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			int i4 = (int) IDstr[Index[3]] - 48;
			int i = 1000 * i1 + 100 * i2 + 10 * i3 + i4;
			if (i >= 1000 && i <= 1003 || i == 1099) {
				return OK;
			}
			if (i >= 2000 && i <= 2009 || i == 2099) {
				return OK;
			}
			if (i >= 3000 && i <= 3003 || i == 3099) {
				return OK;
			}
			if (i >= 4000 && i <= 4002 || i == 4099 || i == 9900) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 451 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 4
	// creator: xjf
	public static String Fireexpert(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			int i4 = (int) IDstr[Index[3]] - 48;
			int i = 1000 * i1 + 100 * i2 + 10 * i3 + i4;
			int ii = 10 * i1 + i2;
			if (i >= 100 && i <= 107) {
				return OK;
			}
			if (i >= 199 && i <= 203) {
				return OK;
			}
			if (i >= 299 && i <= 304) {
				return OK;
			}
			if (i >= 399 && i <= 407) {
				return OK;
			}
			if (i >= 499 && i <= 500 || i == 9900) {
				return OK;
			}
			if (ii >= 6 && i <= 18) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 453
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String Chemicalrisk(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			int i = 100 * i1 + 10 * i2 + 1 * i3;
			if (i >= 100 && i <= 116) {
				return OK;
			}
			if (i >= 199 && i <= 210) {
				return OK;
			}
			if (i >= 299 && i <= 301 || i == 399) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 456
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 6
	// creator: xjf
	public static String Casecharacer(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i3 = (int) IDstr[Index[2]] - 48;
			int i4 = (int) IDstr[Index[3]] - 48;
			int i5 = (int) IDstr[Index[4]] - 48;
			int i6 = (int) IDstr[Index[5]] - 48;
			int i = 10 * i1 + 1 * i2;
			int ii = i3 + i4 + i5 + i6;
			if (i >= 1 && i <= 11 && ii == 0) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 457
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String TypePractitioner(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 10 && i <= 13) {
				return OK;
			}
			if (i >= 19 && i <= 23) {
				return OK;
			}
			if (i >= 29 && i <= 30) {
				return OK;
			}
			if (i == 40 || i == 99) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 460
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String RexreationMatetial(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 10 && i <= 11) {
				return OK;
			}
			if (i >= 19 && i <= 24) {
				return OK;
			}
			if (i >= 29 && i <= 33) {
				return OK;
			}
			if (i >= 90 && i <= 93) {
				return OK;
			}
			if (i == 39 || i == 99) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 462
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String StatuPractitioner(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * i1 + i2;
			if (i >= 10 && i <= 13) {
				return OK;
			}
			if (i >= 19 && i <= 20) {
				return OK;
			}
			if (i == 90 || i == 99) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 463
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String Recreationplace(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i2 = (int) IDstr[Index[2]] - 48;
			int i = 10 * i1 + 1 * i2;
			if (i == 1 || i == 2) {
				if (IDstr[Index[0]] == 'J' || IDstr[Index[0]] == 'H') {
					return OK;
				}
			}
			if (i == 99) {
				if (IDstr[Index[0]] == 'J' || IDstr[Index[0]] == 'H') {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 464
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String PublicSecutity(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i2 = (int) IDstr[Index[2]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i = 10 * i1 + 1 * i2 + 100 * i0;
			if (i >= 110 && i <= 117) {
				return OK;
			}
			if (i >= 119 && i <= 122) {
				return OK;
			}
			if (i >= 129 && i <= 131) {
				return OK;
			}
			if (i >= 139 && i <= 142) {
				return OK;
			}
			if (i == 100 || i == 149 || i == 199 || i == 200 || i == 300
					|| i == 999) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 465.3
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 4
	// creator: xjf
	public static String Borderinfo3(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i2 = (int) IDstr[Index[2]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i3 = (int) IDstr[Index[3]] - 48;
			int i = 100 * i1 + 10 * i2 + 1000 * i0 + i3;
			int a[] = { 100, 200, 300, 400, 401, 402, 403, 404, 500, 600, 2600,
					2700, 7900, 8000, 9000 };
			for (int ii = 0; ii < a.length; ii++) {
				if (i == a[ii]) {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 465.4
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String Borderinfo4(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i = 10 * i0 + i1;
			int a[] = { 11, 21, 22, 23, 31, 32, 33, 34, 99 };
			for (int ii = 0; ii < a.length; ii++) {
				if (i == a[ii]) {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 465.5
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String Borderinfo5(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i2 = (int) IDstr[Index[2]] - 48;
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i = 100 * i0 + 10 * i1 + i2;
			int a[] = { 100, 101, 102, 199, 200, 210, 211, 212, 213, 219, 220,
					221, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 249,
					250, 251, 252, 253, 259, 299, 300, 301, 302, 303, 309 };
			for (int ii = 0; ii < a.length; ii++) {
				if (i == a[ii]) {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 465.6
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String Borderinfo6(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int i2 = (int) IDstr[Index[2]] - 48;
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i = 100 * i0 + 10 * i1 + i2;
			if (i >= 101 && i <= 108) {
				return OK;
			}
			if (i >= 201 && i <= 202) {
				return OK;
			}
			if (i >= 300 && i <= 306) {
				return OK;
			}
			if (i == 399 || i == 401 || i == 502 || i == 900) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 466.4
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FianceManage(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i = 10 * i0 + 1 * i1;
			if (i >= 41 && i <= 49) {
				return OK;
			}
			if (i >= 55 && i <= 64) {
				return OK;
			}
			if (i >= 75 && i <= 76) {
				return OK;
			}
			if (i >= 80 && i <= 84) {
				return OK;
			}
			if (i == 10 || i == 71 || i == 87 || i == 99 || i == 20 || i == 30) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 467
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
	public static String FinanceSecManageInfo(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i = i1 + 10 * i0;
			if (i >= 1 && i <= 9) {
				return OK;
			}

			if (i == 19) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 503
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String ExplosiveCivilian(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i = i1 + 10 * i0;
			if (i >= 1 && i <= 6) {
				return OK;
			}

			if (i == 99) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: represent a decimal integer whose value range is from 000001 to
	// 999999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String SixByteDecimalnt(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[2]] - 48;
			int i3 = (int) IDstr[Index[3]] - 48;
			int i4 = (int) IDstr[Index[4]] - 48;
			int i5 = (int) IDstr[Index[5]] - 48;
			int i = 100000 * i0 + 10000 * i1 + 1000 * i2 + 100 * i3 + 10 * i4
					+ i5;
			if (i >= 1 && i <= 999999) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 01-08 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 504
	public static String OneTO08(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 8;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-09 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 504 509
	public static String OneTO09(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 9;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-07 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 511
	public static String OneTO07(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 7;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-05 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 512
	public static String OneTO05(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 5;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-15 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 213
	public static String OneTO15(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 15;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-13 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 213
	public static String OneTO13(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 13;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-11 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 213
	public static String OneTO11(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 11;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;
	}

	// Function: 01-14 99 98
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 214
	public static String OneTO14(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 14;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99 || i == 98) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-39 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 225
	public static String OneTO39(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 39;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-22 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 225
	public static String OneTO22(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 22;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-10 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 225
	public static String OneTO10(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 10;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-29 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 225
	public static String OneTO29(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 29;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-21 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 225
	public static String OneTO21(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 21;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-46 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 225
	public static String OneTO46(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 46;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-72 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 225
	public static String OneTO72(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 72;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99 || i == 90) {
			return OK;
		} else
			return ERR;

	}

	// Function: ISO 7064:1983.MOD 37 36
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String MOD3736(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// ISO 7064:1983.MOD 11-2Ð£ÑéËã·¨£¬×Ö·û´®¿ª±Ù¿Õ¼äÊ±Òª¶àÒ»Î»Áô¸ø×îºó¼ÓÐ£ÑéÎ»
			double sum = 0; // ×îºóµÄÐ£ÑéÂë
			int i, j;
			int b = LenIndex - 1;
			int a;
			a = 'A';
			for (j = 0; j < LenIndex; j++) {
				for (i = 0; i < 26; i++) {
					char c = (char) (a + i);
					if ((int) IDstr[Index[j]] == c) {
						IDstr[Index[j]] = (char) (10 + i);
					}
				}
			}
			for (i = 0; i < LenIndex; i++) {
				if (IDstr[Index[i]] > 47) {
					IDstr[Index[i]] = (char) (IDstr[Index[i]] - 48);
				}
			}
			int p = 36;
			int s;
			for (i = 1; i < LenIndex; i++) {
				if ((p + (int) IDstr[Index[i - 1]]) % 36 == 0) {
					s = 36;
				} else {
					s = (p + (int) IDstr[Index[i - 1]]) % 36;
				}
				p = (s * 2) % 37;
			}
			int mod;
			mod = 37 - (p % 36);
			if (mod == (int) IDstr[Index[16]]) {
				return OK;
			} else {

				return ERR;

			}
		} catch (Exception e) {
			return ERR;
		}
	}

	public static String MOD1110(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// ISO 7064:1983.MOD 11-2Ð£ÑéËã·¨£¬×Ö·û´®¿ª±Ù¿Õ¼äÊ±Òª¶àÒ»Î»Áô¸ø×îºó¼ÓÐ£ÑéÎ»
			double sum = 0; // ×îºóµÄÐ£ÑéÂë
			int i, j;
			int b = LenIndex - 1;
			int a;
			a = 'A';
			for (j = 0; j < LenIndex; j++) {
				for (i = 0; i < 26; i++) {
					char c = (char) (a + i);
					if ((int) IDstr[Index[j]] == c) {
						IDstr[Index[j]] = (char) (10 + i);
					}
				}
			}
			for (i = 0; i < LenIndex; i++) {
				if (IDstr[Index[i]] > 47) {
					IDstr[Index[i]] = (char) (IDstr[Index[i]] - 48);
				}
			}
			int p = 10;
			int s;
			for (i = 1; i < LenIndex; i++) {
				if ((p + (int) IDstr[Index[i - 1]]) % 10 == 0) {
					s = 10;
				} else {
					s = (p + (int) IDstr[Index[i - 1]]) % 10;
				}
				p = (s * 2) % 11;
			}

			int mod;
			mod = 11 - (p % 10);
			if (mod == (int) IDstr[Index[4]]) {
				return OK;
			} else {

				return ERR;

			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: represent a decimal integer whose value range is from 0000 0001
	// to EFFF FFFF
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String VersionISAN(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			int i, j;
			int a;
			int b = 0;
			a = 'A';
			for (j = 0; j < LenIndex; j++) {
				for (i = 0; i < 26; i++) {
					char c = (char) (a + i);
					if ((int) IDstr[Index[j]] == c) {
						IDstr[Index[j]] = (char) (10 + i);
					}
				}
			}

			for (i = 0; i < LenIndex; i++) {
				if (IDstr[Index[i]] > 47) {
					IDstr[Index[i]] = (char) (IDstr[Index[i]] - 48);
				}
				if ((int) IDstr[Index[i]] < 0 || (int) IDstr[Index[i]] > 15) {
					return ERR;
				}
				if ((int) IDstr[Index[i]] == 0) {
					b++;
				}
			}
			if ((int) IDstr[Index[0]] == 15) {
				return ERR;
			}
			if (b == 8) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:622Ð£¼ìÎ»
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String VehicleIdenCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 17) {
				return ERR;
			}
			int i, j;
			int sum = 0;
			int sum1 = 0;
			for (i = 0; i < LenIndex; i++) {

				if ((int) IDstr[Index[i]] == 'A') {
					IDstr[Index[i]] = (char) 1;
				}
				if ((int) IDstr[Index[i]] == 'B') {
					IDstr[Index[i]] = (char) 2;
				}
				if ((int) IDstr[Index[i]] == 'C') {
					IDstr[Index[i]] = (char) 3;
				}
				if ((int) IDstr[Index[i]] == 'D') {
					IDstr[Index[i]] = (char) 4;
				}
				if ((int) IDstr[Index[i]] == 'E') {
					IDstr[Index[i]] = (char) 5;
				}
				if ((int) IDstr[Index[i]] == 'F') {
					IDstr[Index[i]] = (char) 6;
				}
				if ((int) IDstr[Index[i]] == 'G') {
					IDstr[Index[i]] = (char) 7;
				}
				if ((int) IDstr[Index[i]] == 'H') {
					IDstr[Index[i]] = (char) 8;
				}
				if ((int) IDstr[Index[i]] == 'J') {
					IDstr[Index[i]] = (char) 1;
				}
				if ((int) IDstr[Index[i]] == 'K') {
					IDstr[Index[i]] = (char) 2;
				}
				if ((int) IDstr[Index[i]] == 'L') {
					IDstr[Index[i]] = (char) 3;
				}
				if ((int) IDstr[Index[i]] == 'M') {
					IDstr[Index[i]] = (char) 4;
				}
				if ((int) IDstr[Index[i]] == 'N') {
					IDstr[Index[i]] = (char) 5;
				}
				if ((int) IDstr[Index[i]] == 'P') {
					IDstr[Index[i]] = (char) 7;
				}
				if ((int) IDstr[Index[i]] == 'R') {
					IDstr[Index[i]] = (char) 9;
				}
				if ((int) IDstr[Index[i]] == 'S') {
					IDstr[Index[i]] = (char) 2;
				}
				if ((int) IDstr[Index[i]] == 'T') {
					IDstr[Index[i]] = (char) 3;
				}
				if ((int) IDstr[Index[i]] == 'U') {
					IDstr[Index[i]] = (char) 4;
				}
				if ((int) IDstr[Index[i]] == 'V') {
					IDstr[Index[i]] = (char) 5;
				}
				if ((int) IDstr[Index[i]] == 'W') {
					IDstr[Index[i]] = (char) 6;
				}
				if ((int) IDstr[Index[i]] == 'X') {
					IDstr[Index[i]] = (char) 7;
				}
				if ((int) IDstr[Index[i]] == 'Y') {
					IDstr[Index[i]] = (char) 8;
				}
				if ((int) IDstr[Index[i]] == 'Z') {
					IDstr[Index[i]] = (char) 9;
				}
				if (IDstr[Index[i]] > 47) {
					IDstr[Index[i]] = (char) (IDstr[Index[i]] - 48);
				}
				j = i + 1;
				if (j < 8) {
					sum1 = (9 - j) * (int) IDstr[Index[i]];
				}
				if (j == 8) {
					sum1 = 10 * (int) IDstr[Index[i]];
				}
				if (j > 9) {
					sum1 = (19 - j) * (int) IDstr[Index[i]];
				}
				sum = sum1 + sum;
			}
			int mod = sum % 11;
			char check;
			if (mod == 10) {
				check = "X".charAt(0); // X±íÊ¾10
			} else {
				String jieshou = Integer.toString(mod);
				check = jieshou.charAt(0);
			}
			if (check == (IDstr[Index[8]])) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;

		}
	}

	// Function: Ð£¼ìÎ» ³ËÒÔ3»ò1 Çó
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤16
	// Creator:Ðí½­·å
	public static String CommodityCodeCheck632(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// MOD 16-3Ð£ÑéËã·¨£¬×Ö·û´®¿ª±Ù¿Õ¼äÊ±Òª¶àÒ»Î»Áô¸ø×îºó¼ÓÐ£ÑéÎ»
			double sum = 0; // ×îºóµÄÇóÐ£ÑéÂëÊýÖµ
			int i;
			int w = 0; // È¨ÖØ
			int h = 0; // Ê®½øÖÆ
			int j = 0;
			for (i = 0; i < LenIndex - 1; i++) {
				for (j = 0; j < LenIndex; j++) {
					if (i == LenIndex - 2 - (2 * j)) {
						w = 3;
					}
					if (i == LenIndex - 2 - (2 * j + 1)) {
						w = 1;
					}
				}
				sum = sum + (int) (IDstr[Index[i]] - 48) * w;
			}
			int cd = 10 - (int) (sum % 10);
			if (cd == 10) {
				cd = 0;
			}
			if (cd == (int) IDstr[Index[LenIndex - 1]] - 48) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 00-14 18 20 22 27 33 42
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å639
	public static String ZeroTO14(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 14;
		int i = 10 * index1 + index2;
		if (i >= 0 && i <= Xx || i == 18 || i == 20 || i == 22 || i == 27
				|| i == 33 || i == 42) {
			return OK;
		} else
			return ERR;

	}

	// Function: 00-24
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å654
	public static String ZeroTO24(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 24;
		int i = 10 * index1 + index2;
		if (i >= 0 && i <= Xx) {
			return OK;
		} else
			return ERR;
	}

	// Function: 00-60
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å654
	public static String ZeroTO60(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 60;
		int i = 10 * index1 + index2;
		if (i >= 0 && i <= Xx) {
			return OK;
		} else
			return ERR;
	}

	private static String getHexString(String hexString) {
		String hexStr = "";
		for (int i = hexString.length(); i < 4; i++) {
			if (i == hexString.length())
				hexStr = "0";
			else
				hexStr = hexStr + "0";
		}
		return hexStr + hexString;
	}

	public static String enUnicode(String content) { // ½«ºº×Ö×ª»»Îª16½øÖÆÊý
		String enUnicode = null;
		for (int i = 0; i < content.length(); i++) {
			if (i == 0) {
				enUnicode = getHexString(Integer.toHexString(content.charAt(i))
						.toUpperCase());
			} else {
				enUnicode = enUnicode
						+ getHexString(Integer.toHexString(content.charAt(i))
								.toUpperCase());
			}
		}
		return enUnicode;
	}

	// Function: 657 Ð¡Ð´×ÖÄ¸×ª2½øÖÆ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å657
	public static String Xiaoxie(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		int j;
		int i;
		String out = null;
		int aa;
		aa = 'a';
		for (j = 0; j < LenIndex; j++) {
			for (i = 0; i < 26; i++) {
				char c = (char) (aa + i);
				if ((int) IDstr[Index[j]] == c) {
					IDstr[Index[j]] = (char) (i);
				}
				if ((int) IDstr[Index[j]] == 32) {
					IDstr[Index[j]] = (char) (26);
				}
			}
		}
		int[] jie = new int[LenIndex];
		for (i = 0; i < LenIndex; i++) {
			j = (int) IDstr[Index[i]];
			String a = Integer.toString(j); // ÊäÈëÊýÖµ
			BigInteger src = new BigInteger(a); // ×ª»»ÎªBigIntegerÀàÐÍ
			String bb = src.toString(2);
			int bbb = Integer.parseInt(bb);
			jie[i] = bbb;
		}
		for (i = 0; i < LenIndex; i++) {
			j = jie[i];
			String bb = Integer.toString(j); // ÊäÈëÊýÖµ
			if (bb.length() == 1) {
				bb = "0000" + bb;
			}
			if (bb.length() == 2) {
				bb = "000" + bb;
			}
			if (bb.length() == 3) {
				bb = "00" + bb;
			}
			if (bb.length() == 4) {
				bb = "0" + bb;
			}
			if (i == 0) {
				out = bb;
			} else {
				out += bb;
			}
		}
		return OK;
	}

	// Function: 657 ´óÐ´×ÖÄ¸×ª2½øÖÆ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å657
	public static String Daxie(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		int j;
		int i;
		String out = null;
		int aa;
		aa = 'A';
		for (j = 0; j < LenIndex; j++) {
			for (i = 0; i < 26; i++) {
				char c = (char) (aa + i);
				if ((int) IDstr[Index[j]] == c) {
					IDstr[Index[j]] = (char) (i);
				}
				if ((int) IDstr[Index[j]] == 32) {
					IDstr[Index[j]] = (char) (26);
				}
			}
		}
		int[] jie = new int[LenIndex];
		for (i = 0; i < LenIndex; i++) {
			j = (int) IDstr[Index[i]];
			String a = Integer.toString(j); // ÊäÈëÊýÖµ
			BigInteger src = new BigInteger(a); // ×ª»»ÎªBigIntegerÀàÐÍ
			String bb = src.toString(2);
			int bbb = Integer.parseInt(bb);
			jie[i] = bbb;
		}
		for (i = 0; i < LenIndex; i++) {
			j = jie[i];
			String bb = Integer.toString(j); // ÊäÈëÊýÖµ
			if (bb.length() == 1) {
				bb = "0000" + bb;
			}
			if (bb.length() == 2) {
				bb = "000" + bb;
			}
			if (bb.length() == 3) {
				bb = "00" + bb;
			}
			if (bb.length() == 4) {
				bb = "0" + bb;
			}
			if (i == 0) {
				out = bb;
			} else {
				out += bb;
			}
		}
		return OK;
	}

	// Function: 657 Êý×Ö×ÖÄ¸×ª2½øÖÆ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å657
	public static String Hunpai(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		int j;
		int i;
		String out = null;
		int aa, aaa, aaaa;
		aa = 'A';
		aaa = 'a';
		aaaa = '0';
		for (j = 0; j < LenIndex; j++) {
			for (i = 0; i < 26; i++) {
				char c = (char) (aa + i);
				char cc = (char) (aaa + i);
				if ((int) IDstr[Index[j]] == c) {
					IDstr[Index[j]] = (char) (i + 10);
				}
				if ((int) IDstr[Index[j]] == cc) {
					IDstr[Index[j]] = (char) (i + 36);
				}
				if ((int) IDstr[Index[j]] == 32) {
					IDstr[Index[j]] = (char) (62);
				}
			}
			for (i = 0; i < 10; i++) {
				char ccc = (char) (aaaa + i);
				if ((int) IDstr[Index[j]] == ccc) {
					IDstr[Index[j]] = (char) (IDstr[Index[j]] - 48);
				}
			}
		}
		int[] jie = new int[LenIndex];
		for (i = 0; i < LenIndex; i++) {
			j = (int) IDstr[Index[i]];
			String a = Integer.toString(j); // ÊäÈëÊýÖµ
			BigInteger src = new BigInteger(a); // ×ª»»ÎªBigIntegerÀàÐÍ
			String bb = src.toString(2);
			int bbb = Integer.parseInt(bb);
			jie[i] = bbb;
		}
		for (i = 0; i < LenIndex; i++) {
			j = jie[i];
			String bb = Integer.toString(j); // ÊäÈëÊýÖµ
			if (bb.length() == 1) {
				bb = "0000" + bb;
			}
			if (bb.length() == 2) {
				bb = "000" + bb;
			}
			if (bb.length() == 3) {
				bb = "00" + bb;
			}
			if (bb.length() == 4) {
				bb = "0" + bb;
			}
			if (i == 0) {
				out = bb;
			} else {
				out += bb;
			}
		}
		return OK;
	}

	// Function: 657 Êý×ÖÄ£Ê½×ª2½øÖÆ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å657
	public static String figure(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {

		int j = 0;
		int i, ii = 0;
		int leg;
		String out = null; // ÓÃÓÚ½ÓÊÜ»òÅÐ¶Ï
		if (LenIndex % 3 != 0) {
			leg = LenIndex / 3 + 1;
		} else {
			leg = LenIndex / 3;
		}
		int legg = leg * 3;
		int[] jie = new int[leg];
		char[] newIDstr = new char[legg]; // ½ÓÊÜÊý×Ö
		int[] GB = new int[leg]; // ½ÓÊÜ ÌØÊâ·ûºÅ
		int[] GB1 = new int[leg]; // ½ÓÊÜÌØÊâ·ûºÅËùÔÚµÄÎ»ÖÃ
		for (i = 0; i < LenIndex; i++) {
			if (IDstr[Index[i]] > 47) {
				newIDstr[j] = (char) (IDstr[Index[i]] - 48);
				j++;
			} else {
				if (IDstr[Index[i]] == 32) {
					if ((i - 4 * ii) % 3 == 0) {
						GB[ii] = 1000;
					}
					if ((i - 4 * ii) % 3 == 1) {
						GB[ii] = 1001;
					}
					if ((i - 4 * ii) % 3 == 2) {
						GB[ii] = 1002;
					}
				}
				if (IDstr[Index[i]] == 43) {
					if ((i - 4 * ii) % 3 == 0) {
						GB[ii] = 1003;
					}
					if ((i - 4 * ii) % 3 == 1) {
						GB[ii] = 1004;
					}
					if ((i - 4 * ii) % 3 == 2) {
						GB[ii] = 1005;
					}
				}
				if (IDstr[Index[i]] == 45) {
					if ((i - 4 * ii) % 3 == 0) {
						GB[ii] = 1006;
					}
					if ((i - 4 * ii) % 3 == 1) {
						GB[ii] = 1007;
					}
					if ((i - 4 * ii) % 3 == 2) {
						GB[ii] = 1008;
					}
				}
				if (IDstr[Index[i]] == 46) {
					if ((i - 4 * ii) % 3 == 0) {
						GB[ii] = 1009;
					}
					if ((i - 4 * ii) % 3 == 1) {
						GB[ii] = 1010;
					}
					if ((i - 4 * ii) % 3 == 2) {
						GB[ii] = 1011;
					}
				}
				if (IDstr[Index[i]] == 44) {
					if ((i - 4 * ii) % 3 == 0) {
						GB[ii] = 1012;
					}
					if ((i - 4 * ii) % 3 == 1) {
						GB[ii] = 1013;
					}
					if ((i - 4 * ii) % 3 == 2) {
						GB[ii] = 1014;
					}
				}
				GB1[ii] = i;
				ii++;
			}

		}
		int bu0;
		if (j % 3 != 0) {
			leg = j / 3 + 1;
			bu0 = 3 - j % 3;
		} else {
			leg = j / 3;
			bu0 = j % 3;
		}
		int bu = leg * 3 - i;
		for (j = 0; j < bu; j++) {
			if (bu != 0) {
				newIDstr[i] = (char) (0);
				i++;
			}
		}
		int legleg = leg;
		for (i = 0; i < leg; i++) {
			jie[i] = 100 * (int) newIDstr[3 * i] + 10
					* (int) newIDstr[3 * i + 1] + (int) newIDstr[3 * i + 2];
		}
		int jj = 0;
		int[] GB2 = new int[leg]; // ½ÓÊÜÌØÊâ·ûºÅËùÔÚ10½øÖÆµÄµÄÎ»ÖÃ
		leg = 0;
		for (j = 0; j < ii; j++) {
			if (leg != 0) {
				leg = GB1[j] / 3 + 1;
				GB2[j] = leg;
			} else {
				leg = GB1[j] / 3;
				GB2[j] = leg;
				leg = 1;
			}
		}
		leg = legleg;
		int[] newgb = new int[leg + ii]; // È«ÐÂµÄÊýÖµÀ´½ÓÊÜËùÓÐµÄ10½øÖÆ
		jj = 0; // ÓÉÓÚ½ÓÊÜ£Ç£ÂÖÐµÄÎ»
		for (i = 0; i < leg + ii; i++) {
			j++;
			if (ii != 0) {
				for (j = 0; j < ii; j++) {
					if (i == GB2[j]) {
						newgb[i] = GB[jj];
						jj++;
						i++;
						newgb[i] = jie[i - jj];
						break;
					} else {
						newgb[i] = jie[i - jj];
					}
				}
			} else {
				newgb[i] = jie[i];
			}
		}
		int[] Two = new int[leg + ii]; // ½ÓÊÜ×ª»»³ÉµÄ2½øÖÆÊý×é
		for (i = 0; i < leg + ii; i++) {
			j = (int) newgb[i];
			String a = Integer.toString(j); // ÊäÈëÊýÖµ
			BigInteger src = new BigInteger(a); // ×ª»»ÎªBigIntegerÀàÐÍ
			String bb = src.toString(2);
			int bbb = Integer.parseInt(bb);
			Two[i] = bbb;
		}
		for (i = 0; i < leg + ii; i++) {
			j = Two[i];
			String bb = Integer.toString(j); // ÊäÈëÊýÖµ
			if (bb.length() == 5) {
				bb = "00000" + bb;
			}
			if (bb.length() == 6) {
				bb = "0000" + bb;
			}
			if (bb.length() == 7) {
				bb = "000" + bb;
			}
			if (bb.length() == 8) {
				bb = "00" + bb;
			}
			if (bb.length() == 9) {
				bb = "0" + bb;
			}
			if (i == 0) {
				out = bb;
			} else {
				out += bb;
			}
		}
		if (bu0 == 1) {
			out = "01" + out;
		} else if (bu0 == 2) {
			out = "10" + out;
		} else if (bu0 == 0) {
			out = "00" + out;
		} else {
			out = "11" + out;
		}
		return OK;
	}

	// Function: Ð£¼ìÎ»
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å664
	public static String Check4BitBarCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {

		int i;
		int[] newIDstr = new int[LenIndex];
		for (i = 0; i < LenIndex; i++) {
			newIDstr[i] = (int) (IDstr[Index[i]] - 48);
		}
		// ÓÃÓÚ2-µÄµÚÒ»Î»
		int a = newIDstr[1];
		int b = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a * 2 < 10) {
			b = a * 2;
		} else if (a * 2 > 9) {
			b = (a * 2 - 1) % 10;
		}
		// ÓÃÓÚ2-µÄµÚ¶þÎ»
		int a2 = newIDstr[2];
		int b2 = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a2 * 2 < 10) {
			b2 = a2 * 2;
		} else if (a2 * 2 > 9) {
			b2 = (a2 * 2 - 1) % 10;
		}
		// ÓÃÓÚ3µÄµÚÈýÎ»
		int a3 = newIDstr[3];
		int b3 = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a3 * 3 < 10) {
			b3 = a3 * 3;
		} else if (a3 * 3 > 9) {
			b3 = (a3 * 3) % 10;
		}
		// ÓÃÓÚ5-µÄµÚËÄÎ»
		int a4 = newIDstr[4];
		int b4 = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a4 * 5 < 10) {
			b4 = a4 * 5;
		} else if (a4 * 5 > 9) {
			b4 = ((a4 * 5) - (int) Math.floor((a4 * 5) / 10)) % 10;
		}
		int check;
		check = ((b + b2 + b3 + b4) * 3) % 10;
		if (check == newIDstr[0]) {
			return OK;
		} else {
			return ERR;
		}
	}

	// Function: Ð£¼ìÎ»
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å664
	public static String Check5BitBarCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {

		int i;
		int[] newIDstr = new int[LenIndex];
		for (i = 0; i < LenIndex; i++) {
			newIDstr[i] = (int) (IDstr[Index[i]] - 48);
		}
		// ÓÃÓÚ2-µÄµÚÎåÎ»
		int a = newIDstr[5];
		int b = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a * 2 < 10) {
			b = a * 2;
		} else if (a * 2 > 9) {
			b = (a * 2 - 1) % 10;
		}
		// ÓÃÓÚ2-µÄµÚ¶þÎ»
		int a2 = newIDstr[2];
		int b2 = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a2 * 2 < 10) {
			b2 = a2 * 2;
		} else if (a2 * 2 > 9) {
			b2 = (a2 * 2 - 1) % 10;
		}
		// ÓÃÓÚ5+µÄµÚÒ»Î»
		int a1 = newIDstr[1];
		int b1 = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a1 * 5 < 10) {
			b1 = a1 * 5;
		} else if (a1 * 5 > 9) {
			b1 = ((a1 * 5) + (int) Math.floor((a1 * 5) / 10)) % 10;
		}
		// ÓÃÓÚ5+µÄµÚËÄÎ»
		int a3 = newIDstr[4];
		int b3 = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a3 * 5 < 10) {
			b3 = a3 * 5;
		} else if (a3 * 5 > 9) {
			b3 = ((a3 * 5) + (int) Math.floor((a3 * 5) / 10)) % 10;
		}
		// ÓÃÓÚ5-µÄµÚÈýÎ»
		int a4 = newIDstr[3];
		int b4 = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a4 * 5 < 10) {
			b4 = a4 * 5;
		} else if (a4 * 5 > 9) {
			b4 = ((a4 * 5) - (int) Math.floor((a4 * 5) / 10)) % 10;
		}
		int check;
		check = 10 - (((b + b2 + b3 + b4 + b1)) % 10);
		// ÓÃÓÚ5-µÄÐ£¼ìÎ»
		int a0 = newIDstr[0];
		int b0 = 0; // ÓÃÓÚ½ÓÊÜ¼ÓÈ¨Öµ
		if (a0 * 5 < 10) {
			b0 = a0 * 5;
		} else if (a0 * 5 > 9) {
			b0 = ((a0 * 5) - (int) Math.floor((a0 * 5) / 10)) % 10;
		}
		if (check == b0) {
			return OK;
		} else {
			return ERR;
		}
	}

	// Function:681
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String FlavorSubstance(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			int i = ((int) IDstr[Index[1]] - 48) * 100
					+ ((int) IDstr[Index[2]] - 48) * 10
					+ ((int) IDstr[Index[3]] - 48);
			if (LenIndex == 4) {
				if ((int) IDstr[Index[0]] == 'N') {
					if (i > 0 && i < 378) {
						return OK;
					}
				} else {
					return ERR;
				}
			} else if (LenIndex == 5) {
				int j = ((int) IDstr[Index[1]] - 48) * 1000
						+ ((int) IDstr[Index[2]] - 48) * 100
						+ ((int) IDstr[Index[3]] - 48) * 10
						+ ((int) IDstr[Index[4]] - 48);

				if ((int) IDstr[Index[0]] == 'I') {
					if (j > 1000 && j < 2087) {
						return OK;
					}
				} else if ((int) IDstr[Index[0]] == 'A') {
					if (j > 3000 && j < 3212) {
						return OK;
					}
				} else {
					return ERR;
				}
			}

		} catch (Exception e) {
			return ERR;
		}
		return ERR;

	}

	// Function: 757 ±í59 Öµ 10 11 12 20
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String Highway59(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * index1 + index2;
			if (i == 10 || i == 11 || i == 12 || i == 20) {
				return OK;
			}

			else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 01-11 90
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String OneTO11and90(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int Xx = 11;
			int i = 10 * index1 + index2;
			if (i >= 01 && i <= Xx || i == 90) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 10 11 12 20 21 22 29 30
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	// public static String HighwayTransportation(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int index1 = (int) IDstr[Index[0]] - 48;
	// int index2 = (int) IDstr[Index[1]] - 48;
	// int i = 10 * index1 + index2;
	// if (i >= 10 && i <= 12) {
	// return OK;
	// } else if (i >= 20 && i <= 22 || i == 29 || i == 30) {
	// return OK;
	// } else
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }

	// Function: 10 11 12 19 20 21 22 29 30
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String HighwayTransportationB9(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int i = 10 * index1 + index2;
		if (i >= 10 && i <= 12 || i == 19) {
			return OK;
		} else if (i >= 20 && i <= 22 || i == 29 || i == 30) {
			return OK;
		} else
			return ERR;

	}

	// Function: jt/t 444--201 biao C3
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤4
	// Creator:Ðí½­·å
	public static String HighwayTransportationC3(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int index3 = (int) IDstr[Index[2]] - 48;
		int index4 = (int) IDstr[Index[3]] - 48;
		int i = 10 * index1 + index2;
		int j = 10 * index3 + index4;
		if (i >= 1 && i <= 17 && j == 0) {
			return OK;
		} else if (i == 8 && j == 10) {
			return OK;
		} else if (i == 15 && j == 20) {
			return OK;
		} else if (i == 16 && j == 10) {
			return OK;
		} else
			return ERR;

	}

	// Function: jt/t 444--201 biao C6
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String HighwayTransportationC6(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int i = 10 * index1 + index2;
		if (i >= 10 && i <= 12) {
			return OK;
		} else if (i >= 20 && i <= 23) {
			return OK;
		} else if (i >= 30 && i <= 33) {
			return OK;
		} else if (i >= 40 && i <= 42) {
			return OK;
		} else if (i == 80 || i == 90) {
			return OK;
		} else
			return ERR;

	}

	// Function: jt/t 430
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String Porttariff(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int i = (int) IDstr[Index[0]] - 48;
		int j = (int) IDstr[Index[1]] - 48;
		if (i >= 0 && i <= 5 && j == 0) {
			return OK;
		} else if (i == 3 && j >= 0 && j <= 4) {
			return OK;
		}

		else
			return ERR;

	}

	// Function: jt/t 430
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String Porttariff4(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int i = (int) IDstr[Index[0]] - 48;
		int j = (int) IDstr[Index[1]] - 48;
		if (i >= 1 && i <= 3 && j == 0) {
			return OK;
		} else if (i == 6 && j == 0) {
			return OK;
		} else if (i == 6 && j == 1) {
			return OK;
		} else if (i == 6 && j == 2) {
			return OK;
		} else if (i == 6 && j == 9) {
			return OK;
		} else
			return ERR;

	}

	// Function: jt/t 430
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤3
	// Creator:Ðí½­·å
	public static String Porttariff10(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int index3 = (int) IDstr[Index[2]] - 48;
		int i = 100 * index1 + 10 * index2 + index3;

		if (i >= 110 && i <= 112) {
			return OK;
		} else if (i >= 120 && i <= 122) {
			return OK;
		} else if (i == 100) {
			return OK;
		} else if (i == 200) {
			return OK;
		} else if (i == 300) {
			return OK;
		} else if (i == 310) {
			return OK;
		} else if (i == 320) {
			return OK;
		} else
			return ERR;
	}

	// Function: 01-24 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String OneoTO24(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 24;
		int i = 10 * index1 + index2;
		if (i >= 1 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-17
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String OneTO17(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 17;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-17,99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String OneTO17NO99(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 17;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-12
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String OneTO12No99(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 12;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-13
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å
	public static String OneTO13No99(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 13;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx) {
			return OK;
		} else
			return ERR;

	}

	// Function: 01-99 huo 001-999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	/*
	 * public static String TwoOrThree(char[] IDstr, int LenID, int[] Index, int
	 * LenIndex) { if (!checkInputParam(IDstr, LenID, Index, LenIndex)) { return
	 * ERR; } int index1 = (int) IDstr[Index[0]] - 48; int index2 = (int)
	 * IDstr[Index[1]] - 48; if (LenIndex == 2) { int i = 10 * index1 + index2;
	 * if (i >= 01 && i <= 99) { return OK; } else return ERR; } if (LenIndex ==
	 * 3) { int index3 = (int) IDstr[Index[2]] - 48; int i = 100 * index1 + 10 *
	 * index2 + index3; if (i >= 01 && i <= 999) { return OK; } else return ERR;
	 * 
	 * } return ERR;
	 * 
	 * }
	 */

	// Function: Á½Î»»òËÄÎ»
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String TwoOrFour(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		if (LenIndex == 2) {
			int i = 10 * index1 + index2;
			if (i >= 00 && i <= 99) {
				return OK;
			} else
				return ERR;
		}
		if (LenIndex == 4) {
			int index3 = (int) IDstr[Index[2]] - 48;
			int index4 = (int) IDstr[Index[3]] - 48;
			int i = 1000 * index1 + 100 * index2 + 10 * index3 + index4;
			if (i >= 00 && i <= 9999) {
				return OK;
			} else
				return ERR;

		}
		return ERR;

	}

	// Function: 910 ÖÐµÄ±í15
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table15(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int i = 10 * index1 + index2;
		if (i == 1) {
			if (LenID == 13) {
				return OK;
			} else
				return ERR;
		} else if (i == 2) {
			if (LenID == 13) {
				return OK;
			} else
				return ERR;
		} else if (i == 3) {
			if (LenID == 14) {
				return OK;
			} else
				return ERR;
		} else if (i == 4) {
			if (LenID == 13) {
				return OK;
			} else
				return ERR;
		} else if (i == 5) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 6) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else if (i == 7) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 8) {
			if (LenID == 15) {
				return OK;
			} else
				return ERR;
		} else if (i == 9) {
			if (LenID == 10) {
				return OK;
			} else
				return ERR;
		} else if (i == 10) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else if (i == 11) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else if (i == 12) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else if (i == 13) {
			if (LenID == 10) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: 910 ÖÐµÄ±í18
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table18(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = (int) IDstr[Index[0]] - 48;
		if (i == 2) {
			if (LenID == 8) {
				return OK;
			} else
				return ERR;
		} else {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;

		}
	}

	// Function: 910 ÖÐµÄ±í19
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table19(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = (int) IDstr[Index[0]] - 48;
		if (i == 1) {
			if (LenID == 10) {
				return OK;
			} else
				return ERR;
		} else if (i == 2) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: 910 ÖÐµÄ±í20
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table20(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = (int) IDstr[Index[1]] - 48;
		if (i == 1 || i == 2 || i == 5) {
			if (LenID == 13) {
				return OK;
			} else
				return ERR;
		} else if (i == 6) {
			if (LenID == 8) {
				return OK;
			} else
				return ERR;
		} else if (i == 3 || i == 4) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ· Ö®±ê×¼¼þ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table17(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int i = 10 * index1 + index2;
		if (i == 3) {
			if (LenID == 15) {
				return OK;
			} else
				return ERR;
		} else {
			if (LenID == 18) {
				return OK;
			} else
				return ERR;

		}
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table22
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table22(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = (int) IDstr[Index[0]] - 48;
		if (i == 1) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 2) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;

		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table27
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table27(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = (int) IDstr[Index[1]] - 48;
		if (i == 1) {
			if (LenID == 14) {
				return OK;
			} else
				return ERR;
		} else if (i == 2) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;

		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table28
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table28(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = (int) IDstr[Index[1]] - 48;
		if (i == 1 || i == 3) {
			if (LenID == 8) {
				return OK;
			} else
				return ERR;
		} else if (i == 2) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;

		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table29
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table29(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = (int) IDstr[Index[1]] - 48;
		if (i == 1 || i == 2 || i == 5 || i == 7) {
			if (LenID == 8) {
				return OK;
			} else
				return ERR;
		} else if (i == 3) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else if (i == 6) {
			if (LenID == 7) {
				return OK;
			} else
				return ERR;
		} else if (i == 4) {
			if (LenID == 13) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table30
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table30(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = ((int) IDstr[Index[1]] - 48) + 10
				* ((int) IDstr[Index[0]] - 48);
		if (i == 1 || i == 3) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 6) {
			if (LenID == 10) {
				return OK;
			} else
				return ERR;
		} else if (i == 4) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else if (i == 7 || i == 8) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else if (i == 9 || i == 10 || i == 11 || i == 12) {
			if (LenID == 8) {
				return OK;
			} else
				return ERR;
		} else if (i == 6) {
			if (LenID == 7) {
				return OK;
			} else
				return ERR;
		} else if (i == 5) {
			if (LenID == 6) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table31
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table31(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = ((int) IDstr[Index[1]] - 48) + 10
				* ((int) IDstr[Index[0]] - 48);
		if (i == 1 || i == 2) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else if (i == 4) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 7) {
			if (LenID == 10) {
				return OK;
			} else
				return ERR;
		} else if (i == 3 || i == 6 || i == 9) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else if (i == 5 || i == 8) {
			if (LenID == 8) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table32
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table32(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = ((int) IDstr[Index[1]] - 48) + 10
				* ((int) IDstr[Index[0]] - 48);
		if (i == 1 || i == 2) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else if (i == 4 || i == 3) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 5) {
			if (LenID == 7) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table34
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table34(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = ((int) IDstr[Index[1]] - 48) + 10
				* ((int) IDstr[Index[0]] - 48);
		if (i == 1) {
			if (LenID == 15) {
				return OK;
			} else
				return ERR;
		} else if (i == 2) {
			if (LenID == 14) {
				return OK;
			} else
				return ERR;
		} else if (i == 4) {
			if (LenID == 13) {
				return OK;
			} else
				return ERR;
		} else if (i == 5 || i == 7) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else if (i == 3 || i == 6) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 8) {
			if (LenID == 10) {
				return OK;
			} else
				return ERR;
		} else if (i == 9 || i == 10 || i == 11) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table35
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table35(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = ((int) IDstr[Index[1]] - 48) + 10
				* ((int) IDstr[Index[0]] - 48);
		if (i == 1 || i == 2) {
			if (LenID == 10) {
				return OK;
			} else
				return ERR;
		} else if (i == 3 || i == 4) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table38
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table38(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = ((int) IDstr[Index[1]] - 48) + 10
				* ((int) IDstr[Index[0]] - 48);
		if (i == 1) {
			if (LenID == 14) {
				return OK;
			} else
				return ERR;
		} else if (i == 2) {
			if (LenID == 15) {
				return OK;
			} else
				return ERR;
		} else if (i == 3) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table41
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table41(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = ((int) IDstr[Index[1]] - 48) + 10
				* ((int) IDstr[Index[0]] - 48);
		if (i == 1 || i == 2) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 3) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else if (i == 9) {
			if (LenID == 5) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: DL/T 700.2-1999µÚ¶þ²¿·Ö »úµç²úÆ·table43
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:Ðí½­·å
	public static String Table43(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int j;
		for (j = 0; j < LenID; j++) {
			if (((int) IDstr[Index[j]] - 48) >= 0
					&& ((int) IDstr[Index[j]] - 48) <= 9) {
				return OK;
			} else
				return ERR;
		}
		int i = ((int) IDstr[Index[1]] - 48) + 10
				* ((int) IDstr[Index[0]] - 48);
		if (i == 14) {
			if (LenID == 14) {
				return OK;
			} else
				return ERR;
		} else if (i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8) {
			if (LenID == 13) {
				return OK;
			} else
				return ERR;
		} else if (i == 6 || i == 12 || i == 1) {
			if (LenID == 12) {
				return OK;
			} else
				return ERR;
		} else if (i == 9 || i == 10 || i == 11 || i == 13) {
			if (LenID == 11) {
				return OK;
			} else
				return ERR;
		} else if (i == 15) {
			if (LenID == 9) {
				return OK;
			} else
				return ERR;
		} else if (i == 9) {
			if (LenID == 6) {
				return OK;
			} else
				return ERR;
		} else
			return ERR;
	}

	// Function: 01-03 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 504
	public static String OneTO03(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 3;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 99) {
			return OK;
		} else
			return ERR;

	}

	// Function:1880 road transportation
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	// public static String RoadTransportation21(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int i1 = (int) IDstr[Index[0]] - 48;
	// int i2 = (int) IDstr[Index[1]] - 48;
	// int i = 10 * i1 + i2;
	// if (i >= 20 && i <= 25) {
	// return OK;
	// }
	// if (i == 20 || i == 30) {
	// return OK;
	// }
	// if (i == 40 || i == 90) {
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }

	// Function:1880 road transportation
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	// public static String RoadTransportation22(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int i1 = (int) IDstr[Index[0]] - 48;
	// int i2 = (int) IDstr[Index[1]] - 48;
	// int i = 10 * i1 + i2;
	//
	// if (i >= 11 && i <= 12) {
	// return OK;
	// }
	// if (i >= 21 && i <= 24) {
	// return OK;
	// }
	// if (i >= 41 && i <= 42) {
	// return OK;
	// }
	// if (i == 30 || i == 50) {
	// return OK;
	// }
	// if (i == 60 || i == 90 || i == 70) {
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }

	// Function:1880 road transportation
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	// public static String RoadTransportation32(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int i1 = (int) IDstr[Index[0]] - 48;
	// int i2 = (int) IDstr[Index[1]] - 48;
	// int i = 10 * i1 + i2;
	//
	// if (i >= 10 && i <= 15) {
	// return OK;
	// }
	// if (i >= 19 && i <= 20) {
	// return OK;
	// }
	// if (i == 30 || i == 90) {
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }

	// Function:1880 road transportation
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	// public static String RoadTransportation50(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int i1 = (int) IDstr[Index[0]] - 48;
	// int i2 = (int) IDstr[Index[1]] - 48;
	// int i = 10 * i1 + i2;
	//
	// if (i >= 20 && i <= 23) {
	// return OK;
	// }
	// if (i >= 25 && i <= 26) {
	// return OK;
	// }
	// if (i == 10 || i == 90) {
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	//
	// }

	// Function:1880 road transportation
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	// public static String RoadTransportation63(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int i1 = (int) IDstr[Index[0]] - 48;
	// int i2 = (int) IDstr[Index[1]] - 48;
	// int i = 10 * i1 + i2;
	//
	// if (i >= 10 && i <= 13) {
	// return OK;
	// }
	// if (i >= 20 && i <= 23) {
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	//
	// }

	// Function:1880 road transportation
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	// public static String RoadTransportation64(char[] IDstr, int LenID,
	// int[] Index, int LenIndex) {
	// try {
	// if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
	// return ERR;
	// }
	// if (LenIndex != 2) {
	// return ERR;
	// }
	// int i1 = (int) IDstr[Index[0]] - 48;
	// int i2 = (int) IDstr[Index[1]] - 48;
	// int i = 10 * i1 + i2;
	//
	// if (i >= 10 && i <= 12) {
	// return OK;
	// }
	// if (i >= 30 && i <= 32) {
	// return OK;
	// }
	// if (i == 20 || i == 40) {
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	//
	// }

	// Function: 01-40
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:Ðí½­·å 504
	public static String OneTO42No99(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 99;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx) {
			return OK;
		} else
			return ERR;

	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³Ã³Ò×ÒµÎñÍ³¼Æ·ÖÀàÓë´úÂë(14)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª6
	// creator: zll
	public static String FoodTrade(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getFoodTrade(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³¼Ó¹¤(18)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª5
	// creator: zll
	public static String FoodEconomy(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getFoodEconomy(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³²Ö´¢ÒµÎñÍ³¼Æ·ÖÀàÓë´úÂë(16)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª8
	// creator: zll
	public static String GainStoreHouse(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainStoreHouse(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë ´¢Á¸²¡³æº¦·ÖÀàÓë´úÂë(17)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª5
	// creator: zll
	public static String GainsDiseases(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainsDiseases(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³¼Ó¹¤µÚ1²¿·Ö£º¼Ó¹¤×÷Òµ·ÖÀàÓë´úÂë(19)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª8
	// creator: zll
	public static String GainsProcess(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainsProcess(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³²Ö´¢µÚ3²¿·Ö£ºÆ÷²Ä·ÖÀàÓë´úÂë(20)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª4
	// creator: zll
	public static String GainsEquipment(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainsEquipment(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³²Ö´¢µÚ2²¿·Ö£ºÁ¸Çé¼ì²â·ÖÀàÓë´úÂë(21)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª3
	// creator: zll
	public static String GainsConditionDetection(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainConditionDetection(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³²Ö´¢µÚ1²¿·Ö£º²Ö´¢×÷Òµ·ÖÀàÓë´úÂë(22)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª5»òÕß6
	// creator: zll
	public static String GrainsSmartWMS(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 5 || LenID == 6)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getgrainsSmartWMS(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³¼ìÑéµÚ2²¿·Ö£ºÖÊÁ¿±ê×¼·ÖÀàÓë´úÂë(26)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª8
	// creator: zll
	public static String GrainsQualityStandard(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainsQualityStandard(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ¼ÆÁ¿Æ÷¾ßÃüÃûÓë·ÖÀà±àÂë(32)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª8
	// creator: zll
	public static String MeasuringInstrument(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMeasuringInstrument(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³¼ìÑé µÚ1²¿·Ö£ºÖ¸±ê·ÖÀàÓë´úÂë(27)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª8
	// creator: zll
	public static String GrainsIndex(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainsIndex(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³¼°¼Ó¹¤²úÆ··ÖÀàÓë´úÂë(28)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª7
	// creator: zll
	public static String GrainsInformation(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 7) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainsInformation(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³ÊôÐÔ·ÖÀàÓë´úÂë(29)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª3
	// creator: zll
	public static String GrainsAttribute(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainsAttribute(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³ÆóÒµ·ÖÀàÓë´úÂë(30)ÖÐµÄÇ°Á½Î»ÅÐ¶Ï£¬ÊýÖµ·¶Î§Îª10,11,19,30
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª2
	// creator: zll
	public static String GrainEnterprise(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			if (code.equals("10") || code.equals("11") || code.equals("19")
					|| code.equals("30")) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á¸Ê³ÐÅÏ¢·ÖÀàÓë±àÂë Á¸Ê³ÐÐÕþ¡¢ÊÂÒµ»ú¹¹¼°Éç»áÍÅÌå·ÖÀàÓë´úÂë(31)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª6
	// creator: zll
	public static String GrainAdministrative(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGrainsAdministrative(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ½¨Öþ²úÆ··ÖÀàºÍ´úÂë(34)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È±ØÎª5
	// creator: zll
	public static String ConstructionProducts(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getConstructionProducts(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ³ÐÔËÈË±êÊ¶·û±àÂë¹æÔò£¬Ö±½ÓÂú×ãÕýÔò±í´ïÊ½(44)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È11-17
	// creator: zll
	public static String CarrierIdentifier(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[a-zA-Z0-9]{1,7}[0-9]{3}[A-D][0-9]{6}";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µ¼º½µç×ÓµØÍ¼Êý¾Ý·ÖÀàÓë±àÂë(45)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È4
	// creator: zll
	public static String ElectronicMap(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getElectronicMap(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µç×ÓÊÕ·Ñ¹Ø¼üÐÅÏ¢±àÂë(46)£¬Ç°4Î»ÎªÁ½¸öºº×Ö\u4e00-\u9fa5
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È4
	// creator: zll
	public static String ChineseCharRegex(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "(4[E-F,e-f][0-9,A-F,a-f]{2}|[5-8,A-F,a-f][0-9,A-F,a-f]{3}|9[0-9,A-E,a-e]][0-9,A-F,a-f]{2}|9[F,f][0-9][0-9,A-F,a-f]|9[F,f][a,A][0-5]){2}";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÅÐ¶Ï2¸ö×Ö½ÚÊÇ²»ÊÇÊôÓÚ(01-53)
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, ¹Ì¶¨Îª2
	// creator: helinjia
	public static String TwobytleWeekCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = Index[0];
			int index2 = Index[1];

			if (IDstr[index1] >= '1' && IDstr[index1] <= '4') {
				if (IDstr[index2] >= '0' && IDstr[index2] <= '9') {
					return OK;
				}
			}
			
			if (IDstr[index1] == '0') {
				if (IDstr[index2] >= '1' && IDstr[index2] <= '9') {
					return OK;
				}
			}			
			if (IDstr[index1] == '5') {
				if (IDstr[index2] >= '0' && IDstr[index2] <= '3') {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µØÀíÐÅÏ¢·ÖÀàÓë±àÂë¹æÔò(56)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È5
	// creator: zll
	public static String GeographicInformation(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGeographicInformation(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µØÀíÐÅÏ¢·ÖÀàÓë±àÂë¹æÔò(56)ÖÐµÄÊôÐÔÂëÎª²»¶¨³¤Êý×Ö
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:²»¶¨³¤
	// creator: zll
	public static String GeographicPropertyRegex(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "\\d+";
			int prefix = 10;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Êý×Ö³ÇÊÐµØÀíÐÅÏ¢¹«¹²Æ½Ì¨£¬ÕýÕûÊý(62)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:²»¶¨³¤
	// creator: zll
	public static String DigitRegex(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[0-9]*[1-9][0-9]*";
			int prefix = 14;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÄÖ¯ÃæÁÏ±àÂë»¯ÏË²¿·Ö(64)·ÄÖ¯ÃæÁÏÃû³Æ´úÂë
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª5
	// creator: zll
	public static String TextileFabricNameCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTextileFabricNameCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÄÖ¯ÃæÁÏ±àÂë»¯ÏË²¿·Ö(64)·ÄÖ¯ÃæÁÏÊôÐÔ´úÂëX1X2
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª2
	// creator: zll
	public static String PropertiesMain(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPropertiesMainMaterial(code);
			boolean retMain = recoDao.getPropertiesMain(code);
			if (ret || retMain) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÄÖ¯ÃæÁÏ±àÂë»¯ÏË²¿·Ö(64)ÏËÎ¬ÌØÕ÷ X3X4
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª2
	// creator: zll
	public static String PropertiesFiberCharacteristics(char[] IDstr,
			int LenID, int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPropertiesFiberCharacteristics(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÄÖ¯ÃæÁÏÊôÐÔ´úÂë(64)X7X8ÏËÍø¹Ì½á·½Ê½,ÁíÍâ¿ÉÄÜÎª[1-3,9],[1-3,9]
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª2
	// creator: zll
	public static String PropertiesMix(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[1-3,9]{2}";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPropertiesMix(code);
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean retRegex = ma.matches();
			if (ret || retRegex) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÄÖ¯ÃæÁÏÊôÐÔ´úÂë(64)X9X10 3ÖÖ¿ÉÄÜ01-19,99 01-09,99 01-12,99£¬ËùÒÔÑ¡Ôñ×î´ó·¶Î§Âú×ã
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª2
	// creator: zll
	public static String PropertiesFabric(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPropertiesFabric(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÄÖ¯ÃæÁÏÊôÐÔ´úÂë(64)X11X12
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª2
	// creator: zll
	public static String PropertiesDyeingandFinishing(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPropertiesDyeingandFinishing(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÄÖ¯ÃæÁÏÊôÐÔ´úÂë(64)X11X12
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª9
	// creator: zll
	public static String GeneralManufacturingProcess(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 9) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGeneralManufacturingProcess(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µÀÂ·½»Í¨ÐÅÏ¢·þÎñÐÅÏ¢·ÖÀàÓë±àÂë(68)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª2
	// creator: zll
	public static String TrafficInformation(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			// Ç°Á½Î»Êý
			int index1 = (int) IDstr[0] - 48;
			int index2 = (int) IDstr[1] - 48;
			int i = 10 * index1 + index2;
			// 3,4Î»Êý
			int index3 = (int) IDstr[Index[0]] - 48;
			int index4 = (int) IDstr[Index[1]] - 48;
			int j = 10 * index3 + index4;
			if (i >= 1 && i <= 4) {
				if (j >= 1 && j <= 4 || j == 99) {
					return OK;
				}
			} else if (i == 5) {
				if (j >= 1 && j <= 5 || j == 99) {
					return OK;
				}
			} else if (i == 99) {
				if (j == 99) {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: È«¹úÖ÷Òª²úÆ··ÖÀàÓë´úÂëµÚ2²¿·Ö ²»¿ÉÔËÊä²úÆ·(712)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È1-5
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª2,0ºÍ-1
	// creator: zll
	public static String UntransportableProduct(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID >= 1 && LenID <= 5)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getUntransportableProduct(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: È«¹úÖ÷Òª²úÆ··ÖÀàÓë´úÂëµÚ2²¿·Ö ²»¿ÉÔËÊä²úÆ·ºó3Î»(712)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È1-5
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎª3
	// creator: zll
	public static String LastThreeUntransportableProduct(char[] IDstr,
			int LenID, int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getLastThreeUntransportableProduct(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µÀÂ·½»Í¨ÐÅÏ¢²É¼¯ÐÅÏ¢·ÖÀàÓë±àÂë(77)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ¹Ì¶¨³¤4
	// Creator:zll
	public static String TrafficInformationCollection(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		String firstCode = String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String secondCode = String.valueOf(IDstr[Index[2]])
				+ String.valueOf(IDstr[Index[3]]);
		RecoDao recoDao = new RecoDao();
		boolean ret = recoDao.getTrafficInformationCollection(firstCode,
				secondCode);
		if (ret) {
			return OK;
		} else
			return ERR;
	}

	// Function: ÑÌ²ÝÐÐÒµ¹¤ÉÌÍ³¼ÆÊý¾ÝÔªµÚ2²¿·Ö ´úÂë¼¯(202)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ¹Ì¶¨³¤2
	// Creator:zll
	public static String TobaccoLeafColor(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTobaccoLeafColor(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÑÌÒ¶´úÂëµÚ5²¿·ÖÑÌÒ¶ÑÕÉ«´úÂë(204)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ¹Ì¶¨³¤2
	// Creator:zll
	public static String TrafficOrganization(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTrafficOrganization(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÑÌÒ¶´úÂëµÚ2²¿·ÖÑÌÒ¶ÐÎÌ¬´úÂë(207)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ¹Ì¶¨³¤3
	// Creator:zll
	public static String TobaccoLeafForm(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTobaccoLeafForm(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÑÌÒ¶´úÂëµÚ1²¿·ÖÑÌÒ¶·ÖÀàÓë´úÂë(208)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ¹Ì¶¨³¤5
	// Creator:zll
	public static String TobaccoLeafClass(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTobaccoLeafClass(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ¶ùÍ¯´ó±ãÐÔ×´´úÂë(213)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String ChildrenExcrement(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getChildrenExcrement(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 01-11 90
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:zll
	public static String OneToEleven(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int Xx = 11;
			int i = 10 * index1 + index2;
			if (i >= 01 && i <= Xx) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Òû¾ÆÆµÂÊ´úÂë(214)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String DrinkingFrequency(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getDrinkingFrequency(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Òû¾ÆÆµÂÊ´úÂë(214)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String DrinkingClass(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getDrinkingClass(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Òû¾ÆÆµÂÊ´úÂë(214)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String PhysicalActivityFrequency(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPhysicalActivityFrequency(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÈÑÉïÖÕÖ¹·½Ê½´úÂë±í(215)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String TerminationofPregnancy(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTerminationofPregnancy(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÈÑÉïÖÕÖ¹·½Ê½´úÂë±í(215)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String ModeofProduction(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getModeofProduction(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÖÃäµØµãÀà±ð´úÂë(215)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String DileveryPlace(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getDileveryPlace(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÎÀÉúÐÅÏ¢Êý¾ÝÔªÖµÓò´úÂëµÚ17²¿·Ö£ºÎÀÉú¹ÜÀí(218)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª2-8
	// Creator:zll
	public static String HealthSupervisionObject(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID >= 2 && LenID <= 8)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHealthSupervisionObject(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ½»Í¨¹¤¾ß´úÂë(219)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String CommunicationCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getCommunicationCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÎÀÉú¼à¶½»ú¹¹ÈËÔ±±àÖÆÀà±ð´úÂë(220)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String HygieneAgencyPersonnel(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHygieneAgencyPersonnel(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÎÀÉú¼à¶½»ú¹¹ÈËÔ±±àÖÆÀà±ð´úÂë(220)
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ ³¤¶ÈÎª1-2
	// Creator:zll
	public static String WorkerHealthSupervision(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID == 1 || LenID == 2)) {
				return ERR;
			}
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getWorkerHealthSupervision(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÊµÏÖÐ£Ñé15Î»Êý17710
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:·½µ¤Àö
	// ¼ìÑéÀý×ÓÂë110108000000016
	public static String BussManaCheck(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 15) {
				return ERR;
			}
			int[] a = new int[15];
			for (int i = 0; i < 15; i++) {
				a[14 - i] = IDstr[i] - 48;
			}
			int[] p = new int[15];
			int[] s = new int[15];
			p[0] = 10;
			for (int i = 0; i < 14; i++) {
				s[i] = p[i] % 11 + a[14 - i];
				p[i + 1] = (s[i] % 10) * 2;

				if (s[i] % 10 == 0) {
					p[i + 1] = 20;
				}
				System.out.println("s[i]=" + s[i]);
				System.out.println("p[i]=" + p[i]);
			}
			p[14] = (s[13] % 10) * 2;
			System.out.println("p[14]=" + p[14]);
			s[14] = p[14] % 11 + a[0];
			System.out.println("s[14]=" + s[14]);

			if (s[14] % 10 == 1) {
				return OK;
			}

			return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}

	// 280-¡ª¡ªÖé±¦ÓñÊ¯¼°½ðÊô²úÆ··ÖÀà´úÂë±àÖÆ·½·¨ ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È5Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String JadejewelryClass(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff280(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 282-¡ª¡ªÐÅÏ¢°²È«¼¼Êõ´úÂë±àÖÆ·½·¨ ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È4Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// creator:fdl
	public static String InformationSafe(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			System.out.println(code);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariffMa282(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 280-¡ª¡ªÖÐÑëµ³Õþ»ú¹Ø´úÂë±àÖÆ·½·¨ ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È3Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// creator:fdl
	public static String CodeHighWay(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 3; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff280(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 284-¡ª¡ªÉç»á¾­¼ÃÄ¿±ê·ÖÀàºÍ´úÂë±í ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È6Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a6
	// creator:fdl
	public static String goalsocialeconomic(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		// if (LenIndex != 6) {
		// return ERR;
		// }
		try {
			String code = "";
			for (int i = 0; i < 6; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariffMa284(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// 285-¡ª¡ªÎïÁ÷ÐÅÏ¢·ÖÀàºÍ´úÂë±í ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È6Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a6
	// creator:fdl
	public static String LogisticsInf(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5 && LenIndex != 7) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < IDstr.length; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariffMa285(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// 287-¡ª¡ª·þ×°·ÖÀàºÍ´úÂë±í ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È6Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a6
	// creator:fdl
	public static String clothesclass(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 6 && LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < IDstr.length; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariffMa287(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// 288-¡ª¡ª·þ×°Ãû×Ö·ÖÀà´úÂë±àÖÆ·½·¨ ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È5Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String ClothesName(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariffMa288(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 191-¡ª¡ªÒ½Ò©Æ÷Ðµ·ÖÀàºÍ´úÂë±í ²é±íÊý¾Ý¿â
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È6Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a6
	// creator:fdl
	public static String Pharmacequipment(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 8) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < IDstr.length; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariffMa291(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÁùÎ»ÑØº£ÐÐÕþÇøÓò·ÖÀàÓë´úÂë
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÑØº£ÐÐÕþÇøÓò´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÎªÁùÎ»
	// creator: gcc
	public static String CoastalAdminAreaId(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			String id = "";
			RecoDao recoDao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			boolean ret = recoDao.getCoastalAdminAreaID(id);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÁùÎ»´¬²°µÇ¼ÇºÅ
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ´¬²°µÇ¼ÇºÅ´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÎªÁùÎ»
	// creator: gcc
	public static String InternationalShipCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 7) {
				return ERR;
			}
			String code = "";
			RecoDao recoDao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			boolean ret = recoDao.getInternationalShipCode(code);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÑØº£ÐÐÕþÇøÓò´úÂëµÄÇ°Á½Î»
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÑØº£ÐÐÕþÇøÓò´úÂëÇ°Á½Î»µÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÎªÁ½Î»
	// creator: gcc
	public static String First2CharsofCoastalAdminAreaId(char[] IDstr,
			int LenID, int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			String append = "0000";
			String id = "";
			RecoDao recoDao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			id = id.concat(append);
			boolean ret = recoDao.getCoastalAdminAreaID(id);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Á½Î»¾­¼ÃÀàÐÍ´úÂë
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ¾­¼ÃÀàÐÍ´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÎªÁ½Î»
	// creator: gcc
	public static String WirtschaftsTypCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			String id = "";
			RecoDao recoDao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			boolean ret = recoDao.getWirtschaftsTypCodeID(id);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ´«È¾²¡Ãû³Æ´úÂë²ÉÓÃ3²ã4Î»Êý×ÖË³Ðò´úÂë£¬µÚÒ»²ã1Î»Êý×Ö±íÊ¾£»µÚ¶þ²ã2Î»Êý×Ö±íÊ¾£¬µÚ3²ã1Î»Êý×Ö±íÊ¾¡£ËùÓÐÊý×ÖÉýÐòÅÅÁÐ
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ´«È¾²¡Ãû³Æ´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶È±ØÐëÎª4Î»
	// creator: gcc

	public static String InfectiousDiseases(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}

			String id = "";
			RecoDao recodao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				id = id.concat(String.valueOf(IDstr[Index[i]]));
			}
			boolean ret = recodao.getInfectiousDiseasesID(id);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: º£ÑóÕ¾ÇøÕ¾ºÅ
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃº£ÑóÕ¾ÇøºÅ´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇÁ½Î»
	// creator: gcc
	public static String OceanStationCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int Xx = 12;
			int i = 10 * index1 + index2;
			if (i >= 01 && i < Xx || i == 15 || i == 16) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µØÃû·ÖÀàÓëÀà±ð´úÂë±àÖÆ¹æÔò£¨309£©
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃµØÀíÊôÐÔ´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇËÄÎ»
	// creator: gcc
	public static String GeographicalCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			String code = "";
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGeographicalCode(code);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Å©Ò©¼ÁÐÍÃû³Æ¼°´úÂë£¨305£©
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄË÷ÒýÎ»ÖÃ³¤¶ÈÎª2-4
	// creator: gcc
	public static String PesticideFormulationCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID > 1 && LenID < 5)) {
				return ERR;
			}
			String code = "";

			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPesticideFormulationCode(code);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ³ËÓÃ³µ³ß´ç´úÂë
	// IDstr: ID String
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃÕýÔòµÄË÷ÒýÎ»ÖÃ³¤¶ÈÎª6-8
	// creator: gcc
	public static String PassengerCarCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (!(LenID > 5 && LenID < 9)) {
				return ERR;
			}
			String code = "";

			for (int i = 4; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPassengerCarCode(code);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String RoadTransportation21(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation21(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	public static String CivilAviation(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 9) {
			return ERR;
		}
		if (IDstr[0] != '2' || IDstr[1] != '7') {
			return ERR;
		}
		String code = "";
		for (int i = 0; i < 9; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		System.out.println(code);
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getCivilAviation(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String RoadTransportation22(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation22(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String RoadTransportation32(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation32(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String RoadTransportation5(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]]);
			RecoDao recoDao = new RecoDao();
			System.out.println(code);
			boolean ret = recoDao.getRoadTransportation5(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String RoadTransportation41(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation41(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String RoadTransportation50(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation50(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String RoadTransportation53(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation53(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String RoadTransportation63(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation63(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String Port(char[] IDstr, int LenID, int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPort(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String RoadTransportation60(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		if (IDstr[0] != 'A' && IDstr[0] != 'B')
			return ERR;
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]])
					+ String.valueOf(IDstr[Index[3]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation60(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String RoadTransportation64(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRoadTransportation64(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String HighwayTransportation4b1(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayTransportation4b1(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String HighwayTransportation4b7(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayTransportation4b7(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String HighwayTransportation4b9(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayTransportation4b9(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String HighwayTransportation4c3(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayTransportation4c3(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	public static String PortTariff3(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff3(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	public static String PortTariff4(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 2; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff4(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	public static String PortTariff9(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff9(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	public static String PortTariff25(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff25(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	public static String PortTariff26(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 3; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff26(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	public static String PortTariff10(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 3; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff10(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery2(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery2(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String HighwayMaintenance4(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 10) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 10; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayMaintenance4(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String HighwayMaintenance3(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 10) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 10; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayMaintenance3(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery3(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery3(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery4(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery4(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery5(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery5(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery6(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery6(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery7(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery7(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery8(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery8(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery9(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery9(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String Machinery10(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMachinery10(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	public static String HighwayTransportation4c6(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 2; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayTransportation4c6(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String WaterwayTransportation(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 3; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getWaterwayTransportation(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String HighwayTransportation4b10(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 1) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayTransportation4b10(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String HighwayTransportation(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}
		try {
			String code = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]])
					+ String.valueOf(IDstr[Index[2]]);
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayTransportation(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String TwobytleCode06and90(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 06;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx || i == 90) {
			return OK;
		} else
			return ERR;

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */
	public static String SecurityAccounterments(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (IDstr[0] != 'A' && IDstr[0] != 'B') {
			return ERR;
		}
		
		if (LenIndex != 7) {
			return ERR;
		}
		
		String code = new String(IDstr);
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getSecurityAccounterments(code);
			if (ret)
				return OK;
			else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	public static String SpecialVehicle(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (IDstr[0] != 'M')
			return ERR;
		String code = new String(IDstr);
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getSpecialVehicle(code);
			if (ret)
				return OK;
			else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	/*
	 * 2 or 3 bit code IDstr: ID string LenID: the number of characters in the
	 * ID string Index: the list of corresponding indexes regarding to this
	 * algorithm Index: the list of corresponding indexes regarding to this
	 * algorithm LenIndex: the number of indexes creator:wt
	 */
	public static String TwoOrThree(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenID == 2) {
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			if (index1 < 0 || index1 > 9 || index2 < 0 || index2 > 9)
				return ERR;
			int Xx = 99;
			int i = 10 * index1 + index2;
			if (i >= 01 && i <= Xx) {
				return OK;
			} else
				return ERR;
		} else if (LenID == 3) {
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[0]] - 48;
			int index3 = (int) IDstr[Index[0]] - 48;
			if (index1 < 0 || index1 > 9 || index2 < 0 || index2 > 9
					|| index3 < 0 || index3 > 9)
				return ERR;
			int i = 100 * index1 + 10 * index2 + index3;
			int Xx = 999;
			if (i > 01 && i <= Xx)
				return OK;
			else
				return ERR;
		} else
			return ERR;
	}

	// A1234
	// 12345
	public static String CommodityName(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (IDstr[Index[0]] == 'A') {
			for (int i = 1; i < LenIndex; i++) {
				if (IDstr[Index[i]] < '0' || IDstr[Index[i]] > '9')
					return ERR;
			}
			return OK;
		}
		if (IDstr[Index[0]] >= '0' && IDstr[Index[0]] <= '9') {
			for (int i = 1; i < LenIndex; i++) {
				if (IDstr[Index[i]] < '0' || IDstr[Index[i]] > '9')
					return ERR;
			}
			return OK;
		}
		return ERR;
	}

	// 377¡ª¡ªÉç»á¼æÖ°
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È 4Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:4
	// creator:fdl
	public static String SocialWork(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}

			if ((IDstr[Index[0]] == '0' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '2')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '6')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '4')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '5')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '7')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '8')
					|| (IDstr[Index[0]] == '0' && IDstr[Index[1]] == '9')
					|| (IDstr[Index[0]] == '1' && IDstr[Index[1]] == '0')
					|| (IDstr[Index[0]] == '1' && IDstr[Index[1]] == '0')
					|| (IDstr[Index[0]] == '4' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == '4' && IDstr[Index[1]] == '2')
					|| (IDstr[Index[0]] == '4' && IDstr[Index[1]] == '3')
					|| (IDstr[Index[0]] == '5' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == '6' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == '6' && IDstr[Index[1]] == '2')
					|| (IDstr[Index[0]] == '6' && IDstr[Index[1]] == '3')
					|| (IDstr[Index[0]] == '6' && IDstr[Index[1]] == '4')) {
				return OK;
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 386 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: lhx
	public static String FireInfoori(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			int i = 100 * index1 + 10 * index2 + index3;
			if (i >= 100 && i <= 103) {
				return OK;
			} else if (i >= 200 && i <= 202) {
				return OK;
			} else if (i >= 300 && i <= 303) {
				return OK;
			} else if (i >= 402 && i <= 404) {
				return OK;
			} else if (i >= 500 && i <= 502) {
				return OK;
			} else if (i >= 600 && i <= 603) {
				return OK;
			} else if (i >= 700 && i <= 702) {
				return OK;
			} else if (i == 199 || i == 399 || i == 400 || i == 499 || i == 599
					|| i == 699 || i == 900) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 410 Cadres job code
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 4
	// creator: lhx
	public static String OfficialPostionCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < IDstr.length; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getOfficialPositonByCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// Function: É½ÂöÉ½·åÃû³Æ´úÂë£¨297£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String MountainRangeAndPeakName(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 8) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMountainRangeAndPeakName(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÖªÊ¶²úÈ¨ÎÄÏ×ÓëÐÅÏ¢·ÖÀà¼°´úÂë£¨298£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String IntellectualProperty(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 6) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getIntellectualProperty(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÃñÓÃº½¿ÕÒµÐÅÏ¢·ÖÀàÓë´úÂë (340)
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String ClassificationOfCivilAviation(char[] CODEstr,
			int LenCODE, int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 4) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getClassificationOfCivilAviation(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ¸ßµÈÑ§Ð£±¾¿Æ¡¢×¨¿Æ×¨ÒµÃû³Æ´úÂë£¨328£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String NormalAndShortCycleSpeciality(char[] CODEstr,
			int LenCODE, int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 6) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getNormalAndShortCycleSpeciality(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ´¬²°Î¬ÐÞ±£ÑøÌåÏµ µÚ¶þ²¿·Ö£¨337£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String MaintenanceSystemPTwo(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 14) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMaintenanceSystemPTwo(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:¹ú¼ÊÃ³Ò×ºÏÍ¬´úÂë£¨326£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String CountryRegionCode1(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {

		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 2) {
			return ERR;
		}
		String code = new String(IDstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getCountryRegionCode1(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:µçÁ¦¿Æ¼¼³É¹û·ÖÀàÓë´úÂë£¨784£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String ElectricPower(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 6) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getElectricPower(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:È«¹úµçÍøÃû³Æ´úÂë£¨785£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String PowerGrid(char[] CODEstr, int LenCODE, int[] Index,
			int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 3) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerGrid(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:µçÁ¦ÐÐÒµµ¥Î»Àà±ð´úÂë£¨787£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String ElectricPowerIndustry(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 2) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getElectricPowerIndustry(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:µçÁ¦µØÀíÐÅÏ¢ÏµÍ³Í¼ÐÎ·ûºÅ·ÖÀàÓë´úÂë£¨788£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String ElectricPowerGeography(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 7) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getElectricPowerGeography(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:µçÑ¹µÈ¼¶´úÂë£¨789£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String VoltageClass(char[] CODEstr, int LenCODE, int[] Index,
			int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 7) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getVoltageClass(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:µçÁ¦Îï×Ê±àÂë µÚ¶þ²¿·Ö »úµç²úÆ·£¨909£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String PowerGoodsP2(char[] CODEstr, int LenCODE, int[] Index,
			int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 3) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerGoodsP2(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ½ðÊôÈÈ´¦Àí¹¤ÒÕ·ÖÀà¼°´úºÅ£¬¹¤ÒÕÀàÐÍÓë¹¤ÒÕÃû³Æ²ã´Î
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ±êÊ¶±àÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÎª2
	// creator: gcc
	public static String MetalHeatCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			if (index1 == 1) {
				if (index2 > 0 && index2 < 9) {
					return OK;
				}
			}

			if (index1 == 2) {
				if (index2 > 0 && index2 < 6) {
					return OK;
				}
			}

			if (index1 == 3) {
				if (index2 > 0 && index2 < 8) {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: »ù´¡µØÀíÐÅÏ¢ÒªËØ·ÖÀàÓë´úÂë
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ±êÊ¶±àÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÎªÁùÎ»
	// creator: gcc
	public static String GeographicInfoCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			String code = "";
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGeographicalInfoCode(code);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Éú²ú¹ý³ÌÎ£ÏÕºÍÓÐº¦ÒòËØ·ÖÀàÓë´úÂë(354)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex: ³¤¶È±ØÎª6
	// creator: gcc
	public static String HarmfulFactor(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHarmfulFactorCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÖÐ»ªÈËÃñ¹²ºÍ¹úÌúÂ·³µÕ¾·ÖÀàÓë´úÂë(366)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex: ³¤¶È±ØÎª6
	// creator: gcc
	public static String RailwayStationCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getRailwayStationCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Ðý×ªµç»úÕûÌå½á¹¹µÄ·À»¤µÈ¼¶£¨IP´úÂë£© ·Ö¼¶(261)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄË÷ÒýÎ»ÖÃ
	// LenIndex: ²»¶¨³¤
	// creator: gcc
	public static String ProtectionDegreeRegex(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[A-Z]*";
			int prefix = 4;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÁÖÒµµµ°¸·ÖÀàÓë´úÂë£¨237£©
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄË÷ÒýÎ»ÖÃ
	// LenIndex: ²»¶¨³¤
	// creator: gcc
	public static String ForestryClassRegex(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[1-9]*";
			int prefix = 3;

			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µØÖÊ¿ó²úÊõÓï·ÖÀàÓë´úÂë£¨241-244£©
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex: ²»¶¨³¤
	// creator: gcc
	public static String MineralRegex(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[0-9]*";
			int prefix = 6;

			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ¹ú¼Êº½ÔË»õÎï×°Ð¶·ÑÓÃºÍ´¬²°×âÁÞ·½Ê½Ìõ¿î´úÂë
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÌõ¿î´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÎªÁ½Î»
	// creator: gcc
	public static String StevedorageChartering(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			char index1 = IDstr[Index[0]];
			char index2 = IDstr[Index[1]];
			if ((index1 == 'L') && ((index2 == 'T') || (index2 == 'F'))) {
				return OK;
			}
			if (index1 == 'F') {
				if (index2 == 'I' || index2 == 'O' || index2 == 'N'
						|| index2 == 'T' || index2 == 'S' || index2 == 'A'
						|| index2 == 'D' || index2 == 'L') {
					return OK;
				}
			}
			if ((index1 == 'U') && (index2 == 'T')) {
				return OK;
			}
			if ((index1 == 'D') && (index2 == 'L')) {
				return OK;
			}
			if ((index1 == 'T') && ((index2 == 'D') || (index2 == 'W'))) {
				return OK;
			}
			if (index1 == 'V') {
				if (index2 == 'F' || index2 == 'L' || index2 == 'P'
						|| index2 == 'A') {
					return OK;
				}
			}
			if (index1 == 'A' && index2 == 'C') {
				return OK;
			}
			if (index1 == 'B' && index2 == 'C') {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÁÖÒµ×ÊÔ´·ÖÀàÓë´úÂë ÁÖÄ¾²¡º¦
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÁÖÄ¾²¡º¦µÄÎ»ÖÃ
	// LenIndex£» ³¤¶ÈÎªÁùÎ»
	// creator: gcc
	public static String TreeDiseaseCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			String code = "";
			RecoDao recoDao = new RecoDao();
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			boolean ret = recoDao.getTreeDiseaseCode(code);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÄÚºÓ´¬²°·ÖÀàÓë´úÂëÇ°ËÄÎ»£¨341-1£©
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ´¬²°´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇËÄÎ»
	// creator: gcc
	public static String NavigationShip(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			String code = "";
			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getNavigationShipCode(code);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÄÚºÓ´¬²°·ÖÀàÓë´úÂë¸½¼Ó´úÂë£¨341-2£©
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ¸½¼Ó´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇÁ½Î»
	// creator: gcc
	public static String NavigationShipAddCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;

			if (index1 == 1) {
				if ((index2 >= 0 && index2 < 8) || index2 == 9)
					return OK;
			}
			if (index1 == 2) {
				if (index2 > 0 && index2 <= 9)
					return OK;
			}

			if (index1 == 3) {
				if (index2 == 0 || index2 == 1 || index2 == 9) {
					return OK;
				}
			}
			if (index1 == 4 || index2 == 5) {
				if ((index2 >= 0 && index2 < 5) || index2 == 9)
					return OK;
			}
			if (index1 == 6) {
				if ((index2 >= 0 && index2 < 7) || index2 == 9)
					return OK;
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µÚ46²¿·Ö£ºÏû·ÀÑµÁ··ÖÀàÓë´úÂë£¨411£©
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÏû·ÀÑµÁ··ÖÀàÓë´úÂë´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇÁùÎ»
	// creator: gcc
	public static String FireTrainCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}

			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			int index4 = (int) IDstr[Index[3]] - 48;
			int index5 = (int) IDstr[Index[4]] - 48;
			int index6 = (int) IDstr[Index[5]] - 48;
			int Xx = 10 * index1 + index2;
			int Yy = 10 * index3 + index4;
			int Zz = 10 * index5 + index6;
			if ((Xx > 0 && Xx < 5) || Xx == 99) {
				if (Yy == 0 && Zz == 0)
					return OK;
			}
			if (Xx == 1) {
				if ((Yy > 0 && Yy < 7) || Yy == 99) {
					if (Zz == 0)
						return OK;
				}
			}
			if (Xx == 2) {
				if (Yy == 1) {
					if (Zz >= 0 && Zz < 11)
						return OK;
				}
			}
			if (Xx == 2 || Xx == 3) {
				if (Yy == 2) {
					if (Zz >= 0 && Zz < 4)
						return OK;
				}
			}
			if (Xx == 2 || Xx == 3) {
				if (Yy == 3) {
					if (Zz >= 0 && Zz < 3)
						return OK;
				}
			}
			if (Xx == 2 || Xx == 3) {
				if (Yy == 99) {
					if (Zz == 0)
						return OK;
				}
			}
			if (Xx == 3) {
				if (Yy == 1) {
					if (Zz >= 0 && Zz < 15)
						return OK;
				}
			}
			if (Xx == 4) {
				if ((Yy > 0 && Yy < 8) || Yy == 99)
					if (Zz == 0)
						return OK;
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µÚ33²¿·Ö£ºÆð»ðÔ­Òò·ÖÀàÓë´úÂë£¨425£©
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÆð»ðÔ­Òò·ÖÀàÓë´úÂë´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇÁùÎ»
	// creator: gcc
	public static String FireCauseCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}

			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			int index4 = (int) IDstr[Index[3]] - 48;
			int index5 = (int) IDstr[Index[4]] - 48;
			int index6 = (int) IDstr[Index[5]] - 48;
			int Xx = 10 * index1 + index2;
			int Yy = 10 * index3 + index4;
			int Zz = 10 * index5 + index6;
			if ((Xx > 0 && Xx < 10) || Xx == 98 || Xx == 99) {
				if (Yy == 0 && Zz == 0)
					return OK;
			}
			if (Xx == 1) {
				if (Yy == 1) {
					if ((Zz >= 0 && Zz < 7) || Zz == 99)
						return OK;
				}
			}
			if (Xx == 1) {
				if (Yy == 3) {
					if ((Zz >= 0 && Zz < 3) || Zz == 99)
						return OK;
				}
			}
			if (Xx == 1) {
				if (Yy == 2 || Yy == 99) {
					if (Zz == 0)
						return OK;
				}
			}
			if (Xx == 2) {
				if (Yy == 1 || Yy == 5) {
					if ((Zz >= 0 && Zz < 4) || Zz == 99)
						return OK;
				}
			}
			if (Xx == 2) {
				if (Yy == 2 || Yy == 3) {
					if ((Zz >= 0 && Zz < 5) || Zz == 99)
						return OK;
				}
			}
			if (Xx == 2) {
				if (Yy == 4) {
					if ((Zz >= 0 && Zz < 9) || Zz == 99)
						return OK;
				}
			}
			if (Xx == 2) {
				if (Yy == 99) {
					if (Zz == 0)
						return OK;
				}
			}
			if (Xx == 3) {
				if (Yy > 0 && Yy < 12 || Yy == 99) {
					if (Zz == 0)
						return OK;
				}
			}
			if (Xx == 4 || Xx == 6) {
				if (Yy > 0 && Yy < 4 || Yy == 99) {
					if (Zz == 0)
						return OK;
				}
			}
			if (Xx == 5) {
				if (Yy == 1 || Yy == 2 || Yy == 99) {
					if (Zz == 0)
						return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: µÚ28²¿·Ö£ºÏû·À³ö¾¯ÊÂ¼þ·ÖÀàÓë´úÂë£¨425£©
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÏû·À³ö¾¯ÊÂ¼þ·ÖÀàÓë´úÂë´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇËÄÎ»
	// creator: gcc
	public static String FireForceCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}

			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			int index4 = (int) IDstr[Index[3]] - 48;
			int Xx = 10 * index3 + index4;

			if ((index1 > 0 && index1 < 6) || index1 == 9) {
				if (index2 == 0 && Xx == 0)
					return OK;
			}
			if (index1 == 1) {
				if (index2 == 1 || index2 == 3) {
					if ((Xx >= 0 && Xx < 6) || Xx == 99)
						return OK;
				}
			}
			if (index1 == 1) {
				if (index2 == 2) {
					if ((Xx >= 0 && Xx < 8) || Xx == 99)
						return OK;
				}
			}
			if (index1 == 1 || index1 == 2 || index1 == 9) {
				if (index2 == 9) {
					if (Xx == 0)
						return OK;
				}
			}
			if (index1 == 2) {
				if (index2 == 2 || index2 == 4) {
					if (Xx >= 0 && Xx < 7 || Xx == 99)
						return OK;
				}
			}
			if (index1 == 2) {
				if (index2 == 1) {
					if ((Xx >= 0 && Xx < 9) || Xx == 99)
						return OK;
				}
			}
			if (index1 == 2) {
				if (index2 == 3) {
					if ((Xx >= 0 && Xx < 3) || Xx == 99)
						return OK;
				}
			}
			if (index1 == 2) {
				if (index2 == 5) {
					if ((Xx >= 0 && Xx < 6) || Xx == 99)
						return OK;
				}
			}
			if (index1 == 2) {
				if (index2 == 6) {
					if ((Xx >= 0 && Xx < 8) || Xx == 99)
						return OK;
				}
			}
			if (index1 == 4) {
				if (index2 > 0 && index2 < 4 || index2 == 9) {
					if (Xx == 0)
						return OK;
				}
			}
			if (index1 == 5) {
				if (index2 > 0 && index2 < 6 || index2 == 9) {
					if (Xx == 0)
						return OK;
				}
			}
			if (index1 == 9) {
				if (index2 == 1) {
					if (Xx == 0)
						return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 412 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 4
	// creator: gcc
	public static String FireInfotrain(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[1]] - 48;
			int index2 = (int) IDstr[Index[2]] - 48;
			int index3 = (int) IDstr[Index[3]] - 48;
			int index4 = (int) IDstr[Index[0]] - 48;
			// int Xx = 8;
			int i = 100 * index1 + 10 * index2 + index3 + 1000 * index4;
			if (i >= 100 && i <= 104) {
				return OK;
			} else if (i >= 200 && i <= 203) {
				return OK;
			} else if (i == 299 || i == 99) {
				return OK;
			}

			else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 419 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: gcc
	public static String ServiceState(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			if ((index1 > 0 && index1 < 8) || index1 == 9) {
				if (index2 == 0) {
					return OK;
				}
			}
			if (index1 == 2) {
				if (index2 > 0 && index2 < 8) {
					return OK;
				}
			}
			return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 457
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: gcc
	public static String PractitionerType(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int i = 10 * index1 + index2;
			if (i >= 10 && i <= 13) {
				return OK;
			}
			if (i >= 19 && i <= 23) {
				return OK;
			}
			if (i >= 29 && i <= 30 || i == 40 || i == 99) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: GA/T 556.4
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ½ðÈÚµ¥Î»Àà±ð´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇÁ½Î»
	// creator: gcc
	public static String FinancialCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;

			if (index1 == 1 || index1 == 2 || index1 == 3 || index1 == 6
					|| index1 == 8) {
				if (index2 == 0) {
					return OK;
				}
			}
			if (index1 == 4) {
				if (index2 > 0 && index2 <= 9) {
					return OK;
				}
			}
			if (index1 == 5) {
				if (index2 > 4 && index2 <= 9) {
					return OK;
				}
			}
			if (index1 == 6) {
				if (index2 > 0 && index2 <= 4) {
					return OK;
				}
			}
			if (index1 == 7) {
				if (index2 == 1 || index2 == 5 || index2 == 6) {
					return OK;
				}
			}
			if (index1 == 8) {
				if ((index2 > 0 && index2 <= 4) || index2 == 7) {
					return OK;
				}
			}
			if (index1 == 9) {
				if (index2 == 9) {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: GA/T 556.3
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃ½ð¿âÀà±ð´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇÁ½Î»
	// creator: gcc
	public static String TreasuryClass(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;

			if (index1 == 0) {
				if (index2 > 0 && index2 <= 9) {
					return OK;
				}
			}

			if (index1 == 1) {
				if (index2 == 9) {
					return OK;
				}
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: GA/T 556.1
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÖ°ÎñÀà±ð´úÂëµÄÎ»ÖÃ
	// LenIndex: ³¤¶ÈÊÇÁ½Î»
	// creator: gcc
	public static String JobClassificationCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}

			char index1 = IDstr[Index[0]];
			char index2 = IDstr[Index[1]];
			int index3 = (int) IDstr[Index[2]] - 48;
			int index4 = (int) IDstr[Index[3]] - 48;
			int Xx = 10 * index3 + index4;
			if (index1 == 'F' && index2 == 'F') {
				if ((Xx > 0 && Xx < 4) || Xx == 9) {
					return OK;
				}
			}
			if (index1 == 'F' && index2 == 'Z') {
				if (Xx == 1) {
					return OK;
				}
			}

			if ((index1 == 'Z' || index1 == 'C') && index2 == 'F') {
				if (Xx > 0 && Xx < 4) {
					return OK;
				}
			}

			if ((index1 == 'D' || index1 == 'X') && index2 == 'F') {
				if (Xx > 0 && Xx < 3) {
					return OK;
				}
			}

			if (((index1 == 'D' || index1 == 'X') && index2 == 'Y')
					|| (index1 == 'Z' && index2 == 'Z')) {
				if (Xx > 0 && Xx < 10) {
					return OK;
				}
			}

			if (index1 == 'S' && index2 == 'X') {
				if (Xx > 2 && Xx < 9) {
					return OK;
				}
			}

			if (index1 == 'S' && index2 == 'F') {
				if ((Xx > 0 && Xx < 13) || Xx == 19) {
					return OK;
				}
			}

			if (index1 == 'D' && index2 == 'Z') {
				if ((Xx > 0 && Xx < 13) || Xx == 19 || Xx == 14) {
					return OK;
				}
			}

			if (index1 == 'X' && index2 == 'Z') {
				if (Xx > 0 && Xx < 11) {
					return OK;
				}
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ³£ÓÃÖ¤¼þ´úÂë£¨470£©
	// IDstr: ID string
	// LenID: ±êÊ¶±àÂë
	// Index: ±êÊ¶±àÂëµÄ³¤¶È
	// LenIndex: µ÷ÓÃË÷ÒýÎ»ÖÃ³¤¶ÈÎª3
	// creator: gcc
	public static String TravleDocumentCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			String code = "";

			for (int i = 0; i < LenIndex; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTravelCode(code);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:¹«°²²¿Ïû·À¾ÖºÍÊ¡¼¶¹«°²Ïû·À×Ü¶Ó´úÂë(474)
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String ProvinceAdminCode(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 2) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getProvinceAdminCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:ÁÐ¹Üµ¥Î»´úÂë(474)
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String AdminDivision1(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 9) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getAdminDivision1(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ¹ú¼ÊÃ³Ò×ºÏÍ¬´úÂë±àÖÆ¹æÔò×Ô¶¨Òå±àÂëÕýÔòÆ¥Åä,Êý×Ö»òÕß×ÖÄ¸£¬Êý×ÖÔÚ×ÖÄ¸ºóÃæ¡£(326)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È<=9
	// creator: yzc
	public static String DraftingRulesForCodes(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[A-Z][A-Z0-9]{0,8}";
			int prefix = 8;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: »·¾³ÐÅÏ¢·ÖÀàÓë´úÂëÕýÔòÆ¥Åä,Êý×Ö(776)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎÞÇî
	// creator: yzc
	public static String EnvironmentalInformation(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[0-9][1-9]*";
			int prefix = 8;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ·ÏË®Àà±ð´úÂëÕýÔòÆ¥Åä,Êý×Ö(782)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶ÈÎÞÇî
	// creator: yzc
	public static String Wastewater(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[0-9][1-9]*";
			int prefix = 5;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÒøÐÐ±íÊ¾´úÂë·ÖÖ§»ú¹¹´úÂëÕýÔòÆ¥Åä,Êý×Ö£¬×ÖÄ¸(332)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È<=4
	// creator: yzc
	public static String BankCodes(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[1-9A-W,Y,Z]{0,3}";
			int prefix = 8;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÊéÖ¤ÎïÖ¤ÖÖÀà´úÂë£¨538£©
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:yzc
	public static String DocumentEnvidence(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			if ((IDstr[index1] < '0') || (IDstr[index1] > '3')) {
				return ERR;
			}
			if ((IDstr[index2] < '0') || (IDstr[index2] > '9')) {
				return ERR;
			}
			if ((IDstr[index3] < '0') || (IDstr[index3] > '9')) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÊÜº¦µ¥Î»ÐÐÒµ·ÖÀà´úÂë£¨532£©
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:yzc
	public static String EconomicCasesUnit(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			if ((IDstr[index1] < '0') || (IDstr[index1] > '9')) {
				return ERR;
			}
			if ((IDstr[index2] < '0') || (IDstr[index2] > '9')) {
				return ERR;
			}
			if ((IDstr[index3] < '0') || (IDstr[index3] > '9')) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ×÷°¸ÊÖ¶Î´úÂë£¨539£©
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:yzc
	public static String CodesOfMakingCases(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 3) {
				return ERR;
			}
			int index1 = Index[0];
			int index2 = Index[1];
			int index3 = Index[2];
			if ((IDstr[index1] < '0') || (IDstr[index1] > '8')) {
				return ERR;
			}
			if ((IDstr[index2] < '0') || (IDstr[index2] > '9')) {
				return ERR;
			}
			if ((IDstr[index3] < '0') || (IDstr[index3] > '9')) {
				return ERR;
			}
			return OK;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 01-48
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes ¹Ì¶¨³¤2
	// Creator:yzc
	public static String OneTO48(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 48;
		int i = 10 * index1 + index2;
		if (i >= 01 && i <= Xx) {
			return OK;
		} else
			return ERR;

	}

	// Function:¹«Â·Â·Ïß±êÊ¶¹æÔòºÍ¹úµÀ±àºÅÕýÔòÆ¥Åä,Êý×Ö£¬×ÖÄ¸(598)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È<=3
	// creator: yzc
	public static String NationalTrunkHighway(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[0-9,w][0-9,w][1-9]{0,2}";
			int prefix = 2;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	public static String ParamCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		if (index1 < 0 || index1 > 9 || index2 < 0 || index2 > 9)
			return ERR;
		char c = IDstr[Index[2]];
		int index3 = IDstr[Index[2]] - 48;
		if (LenIndex == 4) {
			if (c == '.') {
				int index4 = (int) IDstr[Index[3]] - 48;
				if (index4 >= 0 && index4 <= 9)
					return OK;
			}
			if (index3 >= 0 && index3 <= 9) {
				int index4 = (int) IDstr[Index[3]] - 48;
				if (index4 > 0 && index4 < 9)
					return OK;
			}
		}
		if (LenIndex == 5) {
			if (c == '.') {
				int index4 = (int) IDstr[Index[3]] - 48;
				int index5 = (int) IDstr[Index[4]] - 48;
				if (index4 >= 0 && index4 <= 9 && index5 > 0 && index5 < 9)
					return OK;
			}
		}
		return ERR;

	}

	public static String ParamCode6(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		if (index1 < 0 || index1 > 9 || index2 < 0 || index2 > 9)
			return ERR;
		char c = IDstr[Index[2]];
		int index3 = IDstr[Index[2]] - 48;
		if (LenIndex == 6) {
			if (c == '.') {
				int index4 = (int) IDstr[Index[3]] - 48;
				int index5 = (int) IDstr[Index[4]] - 48;
				int index6 = (int) IDstr[Index[5]] - 48;
				if (index4 >= 0 && index4 <= 9 && index5 >= 0 && index5 <= 9
						&& index6 >= 0 && index6 <= 9)
					return OK;
			}
			if (LenIndex == 7) {
				if (index3 >= 0 && index3 <= 9) {
					char d = IDstr[Index[3]];
					if (d == '.') {
						int index5 = (int) IDstr[Index[4]] - 48;
						int index6 = (int) IDstr[Index[5]] - 48;
						int index7 = (int) IDstr[Index[6]] - 48;
						if (index5 >= 0 && index5 <= 9 && index6 >= 0
								&& index6 <= 9 && index7 >= 0 && index7 <= 9)
							return OK;
					}
				}
			}

		}
		return ERR;
	}

	/*
	 * Ç°4Î»ÊÇÊý×Ö£¬ºó2Î»ÊÇÐ¡Êý 1234.12 author:wt
	 */
	public static String ParamCode7(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		for (int i = 0; i < 4; i++) {
			int index = (int) IDstr[Index[i]] - 48;
			if (index < 0 || index > 9)
				return ERR;
		}
		char c = IDstr[Index[4]];
		if (c == '.') {
			int index1 = (int) IDstr[Index[5]] - 48;
			int index2 = (int) IDstr[Index[5]] - 48;
			if (index1 >= 0 && index1 <= 9 && index2 >= 0 && index2 <= 9)
				return OK;
		}
		return ERR;
	}

	/*
	 * ¿ÉÄÜÊÇ1,2,3,6Î»µÄÊý×Ö
	 * 
	 * author:wt
	 */
	public static String ParamCode17(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		switch (LenIndex) {
		case 1: {
			int index = (int) IDstr[Index[0]] - 48;
			if (index >= 0 && index <= 9)
				return OK;
		}
		case 2: {
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			if (index1 >= 0 && index1 <= 9 && index2 >= 0 && index2 <= 9)
				return OK;
		}
		case 3: {
			for (int i = 0; i < 3; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			return OK;
		}
		case 6: {
			for (int i = 0; i < 6; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			return OK;
		}
		default:
			return OK;
		}
	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ6Î»»ò7Î»µÄÊý×Ö author£ºwt
	 */
	public static String ParamCode19(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 6) {
			for (int i = 0; i < 6; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			return OK;
		}
		if (LenIndex == 7) {
			for (int i = 0; i < 7; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			return OK;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ4Î»»ò8Î»»ò9µÄÊý×Ö author£ºwt
	 */
	public static String ParamCode20(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 4) {
			for (int i = 0; i < 4; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			return OK;
		}
		if (LenIndex == 8) {
			for (int i = 0; i < 8; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			return OK;
		}
		if (LenIndex == 9) {
			for (int i = 0; i < 9; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			return OK;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ8Î»»ò6Î»Êý×Ö µ±Îª8Î»Ê±£¬ÂëµÄµÚÒ»Î»ÊÇ1 µ±Îª6Î»Ê±£¬ÂëµÄµÚÒ»Î»ÊÇ2 author£ºwt
	 */
	public static String ParamCode22(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 8) {
			for (int i = 0; i < 8; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '1')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 6) {
			for (int i = 0; i < 6; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '2')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ10Î»»ò8Î»Êý×Ö µ±Îª10Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ01 µ±Îª8Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ02 author£ºwt
	 */
	public static String ParamCode27(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 10) {
			for (int i = 0; i < 10; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '1')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 8) {
			for (int i = 0; i < 8; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '2')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ4Î»»ò5Î»Êý×Ö µ±Îª4Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ01»ò03 µ±Îª5Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ02 author£ºwt
	 */
	public static String ParamCode28(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 4) {
			for (int i = 0; i < 4; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && (IDstr[1] == '1' || IDstr[1] == '3'))
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 5) {
			for (int i = 0; i < 5; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '2')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ3,4,5,9Î»Êý×Ö µ±Îª3Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ06 µ±Îª4Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ01,02,05,07
	 * µ±Îª5Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ03 µ±Îª9Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ04 author£ºwt
	 */
	public static String ParamCode29(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 3) {
			for (int i = 0; i < 3; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '6')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 4) {
			for (int i = 0; i < 4; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0'
					&& (IDstr[1] == '1' || IDstr[1] == '2' || IDstr[1] == '5' || IDstr[1] == '7'))
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 5) {
			for (int i = 0; i < 5; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '3')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 9) {
			for (int i = 0; i < 9; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '4')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ2,3,4,5,6,7,8Î»Êý×Ö µ±Îª2Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ05 µ±Îª3Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ06
	 * µ±Îª4Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ09,10,11,12 µ±Îª5Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ07,08 µ±Îª6Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ02
	 * µ±Îª7Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ01,03 µ±Îª8Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ04 author£ºwt
	 */
	public static String ParamCode30(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 2) {
			for (int i = 0; i < 2; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '5')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 3) {
			for (int i = 0; i < 3; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '6')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 4) {
			for (int i = 0; i < 4; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '9' || IDstr[0] == '1'
					&& IDstr[1] == '0' || IDstr[0] == '1' && IDstr[1] == '1'
					|| IDstr[0] == '1' && IDstr[1] == '2')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 5) {
			for (int i = 0; i < 5; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && (IDstr[1] == '7' || IDstr[1] == '8'))
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 6) {
			for (int i = 0; i < 6; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '2')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 7) {
			for (int i = 0; i < 7; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && (IDstr[1] == '1' || IDstr[1] == '3'))
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 8) {
			for (int i = 0; i < 8; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '4')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ4,5,6,7,8Î»Êý×Ö µ±Îª4Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ05,08 µ±Îª5Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ03,06,09
	 * µ±Îª6Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ07 µ±Îª7Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ04 µ±Îª8Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ01,02 author£ºwt
	 */
	public static String ParamCode31(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 4) {
			for (int i = 0; i < 4; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0'
					&& (IDstr[1] == '1' || IDstr[1] == '2' || IDstr[1] == '5' || IDstr[1] == '7'))
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 5) {
			for (int i = 0; i < 5; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '3')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 9) {
			for (int i = 0; i < 9; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '4')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ3,7,8Î»Êý×Ö µ±Îª3Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ05 µ±Îª7Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ03,04
	 * µ±Îª8Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ01,02 author£ºwt
	 */
	public static String ParamCode32(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 3) {
			for (int i = 0; i < 3; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '5')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 7) {
			for (int i = 0; i < 7; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && (IDstr[1] == '3' || IDstr[1] == '4'))
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 8) {
			for (int i = 0; i < 8; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && (IDstr[1] == '1' || IDstr[1] == '2'))
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ5,6,7,8,9,10,11Î»Êý×Ö µ±Îª5Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ09,10,11 µ±Îª6Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ08
	 * µ±Îª7Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ03,06 µ±Îª8Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ05,07 µ±Îª9Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ04
	 * µ±Îª10Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ02 µ±Îª11Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ01 author£ºwt
	 */
	public static String ParamCode34(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 5) {
			for (int i = 0; i < 5; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '9' || IDstr[0] == '1'
					&& IDstr[1] == '0' || IDstr[0] == '1' && IDstr[1] == '1')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 6) {
			for (int i = 0; i < 6; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '8')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 7) {
			for (int i = 0; i < 7; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && (IDstr[1] == '3' || IDstr[1] == '6'))
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 8) {
			for (int i = 0; i < 8; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && (IDstr[1] == '5' || IDstr[1] == '7'))
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 9) {
			for (int i = 0; i < 9; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '4')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 10) {
			for (int i = 0; i < 10; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '2')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 11) {
			for (int i = 0; i < 11; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '1')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ5,6Î»Êý×Ö µ±Îª5Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ3,4 µ±Îª6Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ1,2 author£ºwt
	 */
	public static String ParamCode35(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 5) {
			for (int i = 0; i < 5; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '3' || IDstr[0] == '4')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 6) {
			for (int i = 0; i < 6; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '1' || IDstr[0] == '2')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ7,9,10Î»Êý×Ö µ±Îª7Î»Ê±£¬ÂëµÄµÚ1Î»ÊÇ3 µ±Îª9Î»Ê±£¬ÂëµÄµÚ1Î»ÊÇ1 µ±Îª10Î»Ê±£¬ÂëµÄµÚ1Î»ÊÇ2 author£ºwt
	 */
	public static String ParamCode38(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 7) {
			for (int i = 0; i < 7; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '3')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 9) {
			for (int i = 0; i < 9; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '1')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 10) {
			for (int i = 0; i < 10; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '2')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ6,7Î»Êý×Ö µ±Îª6Î»Ê±£¬ÂëµÄµÚ1Î»ÊÇ1»ò2 µ±Îª7Î»Ê±£¬ÂëµÄµÚ1Î»ÊÇ3 author£ºwt
	 */
	public static String ParamCode41(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex == 6) {
			for (int i = 0; i < 6; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '1' || IDstr[0] == '2')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 7) {
			for (int i = 0; i < 7; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '3')
				return OK;
			else
				return ERR;
		}

		return ERR;

	}

	/*
	 * Ð£Ñé×îºóµÄÂë£¬¿ÉÄÜÊÇ6,7Î»Êý×Ö µ±Îª3Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ15 µ±Îª5Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ09,10,11,13
	 * µ±Îª6Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ01,06,12 µ±Îª7Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ02,03,04,05,07,08 µ±Îª8Î»Ê±£¬ÂëµÄµÚ1,2Î»ÊÇ14
	 * author£ºwt
	 */
	public static String ParamCode43(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex == 3) {
			for (int i = 0; i < 3; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '1' && IDstr[1] == '5')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 5) {
			for (int i = 0; i < 5; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '9' || IDstr[0] == '1'
					&& IDstr[1] == '0' || IDstr[0] == '1' && IDstr[1] == '1'
					|| IDstr[0] == '1' && IDstr[1] == '3')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 6) {
			for (int i = 0; i < 6; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '1' || IDstr[0] == '0'
					&& IDstr[1] == '6' || IDstr[0] == '1' && IDstr[1] == '2')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 7) {
			for (int i = 0; i < 7; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '0' && IDstr[1] == '2' || IDstr[0] == '0'
					&& IDstr[1] == '3' || IDstr[0] == '0' && IDstr[1] == '4'
					|| IDstr[0] == '0' && IDstr[1] == '5' || IDstr[0] == '0'
					&& IDstr[1] == '7' || IDstr[0] == '0' && IDstr[1] == '8')
				return OK;
			else
				return ERR;
		}
		if (LenIndex == 8) {
			for (int i = 0; i < 8; i++) {
				int index = (int) IDstr[Index[i]] - 48;
				if (index < 0 || index > 9)
					return ERR;
			}
			if (IDstr[0] == '1' && IDstr[1] == '4')
				return OK;
			else
				return ERR;
		}
		return ERR;

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials44(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials44(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials45(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials45(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials46(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials46(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials47(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials47(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials49(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials49(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials50(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials50(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials51(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials51(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials52(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials51(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials53(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials53(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * IDstr: ID string LenID: the number of characters in the ID string Index:
	 * the list of corresponding indexes regarding to this algorithm Index: the
	 * list of corresponding indexes regarding to this algorithm LenIndex: the
	 * number of indexes creator:wt
	 */

	public static String PowerMaterials54(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPowerMaterials54(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// 509»¥ÁªÍøÍøÉÏ·þÎñÓªÒµ³¡Ëù¡ª¡ªµÚÎå²¿·Ö
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È4Î»
	// Index: µ÷ÓÃÑéÖ¤Ëã·¨µÄË÷ÒýÎ»ÖÃ
	// LenIndex:a3
	// creator:fdl
	public static String InternetWebService(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTariff509(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 966-¹ú¼Ò¹¤³Ì½¨Éè±ê×¼ÌåÏµ±àÂëÍ³Ò»¹æÔò fdl
	// [A1 A2 A3 B1 C1 D1 E1 F1 G1 H1 J1 K1 L1 M1 N1 P1 Q1 R1]Á½Î»¹ØÁª
	public static String projectbuild(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}

			if ((IDstr[Index[0]] == 'A' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'A' && IDstr[Index[1]] == '2')
					|| (IDstr[Index[0]] == 'A' && IDstr[Index[1]] == '3')
					|| (IDstr[Index[0]] == 'B' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'C' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'D' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'E' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'F' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'G' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'H' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'J' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'K' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'L' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'M' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'N' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'P' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'Q' && IDstr[Index[1]] == '1')
					|| (IDstr[Index[0]] == 'R' && IDstr[Index[1]] == '1')) {
				return OK;
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// ·ÅÉäÔ´±àÂë¹æÔò ºËÔªËØ¹ú¼Ò´úÂë fdl
	public static String NuclearelementNation(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 2; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortNuclearelementNation(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// ·ÅÉäÔ´±àÂë¹æÔò fdl
	public static String Nuclearelements(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 2; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortNuclearelements(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// ¹¤ÉÌÐÐÕþ¹ÜÀí×¢²áºÅ±àÖÆ¹æÔò fdl
	public static String BusinessAdminis(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			String aa = null;
			aa = AdminDivision(IDstr, LenIndex, Index, LenIndex);
			if (aa.equals(OK)) {
				return OK;
			}
			String code = "";
			for (int i = 0; i < 6; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			System.out.println("code=" + code);
			if (code.equals("100000")) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Æû³µ±ê×¼¼þ²úÆ·±àºÅ¹æÔò
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: (13,-1),´Ó13Î»ÒÔºóµÄ×Ö·û´®½øÐÐÕýÔò±í´ïÊ½ÑéÖ¤
	// LenIndex: ³¤¶È±ØÎª2
	// creator: fdl
	public static String CarProduct(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[1-6,9][0-4,6,9,A-N,P-Y]{0,1}";
			int prefix = 13;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			// ×îºóÒ»Î»ÎªÐ£ÑéÎ»
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();

			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Æû³µ²úÆ·Áã²¿¼þ±ß±àÂë¹æÔò
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index:4
	// LenIndex: ³¤¶È±ØÎª4
	// creator: fdl
	public static String CarProductCompnent(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortCarProductCompnent(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// GB/T 23733ÖÐ¹ú±ê×¼ÒôÀÖ×÷Æ·±àÂë fdl
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index:15
	// LenIndex: ³¤¶È±ØÎª15
	// creator: fdl
	public static String StandardMusicCheckCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 15) {
				return ERR;
			}
			int i;
			int[] newIDstr = new int[LenIndex];
			for (i = 0; i < LenIndex - 4; i++) {
				newIDstr[i] = (int) (IDstr[Index[i + 4]] - 48);
				System.out.println("newIDstr[i]=" + newIDstr[i]);
			}

			int sum = 0;
			for (int j = 1; j < 10; j++) {
				sum = 1 + sum + i * newIDstr[i];
			}
			sum = sum + newIDstr[10];

			int check;
			check = sum % 10;
			if (check == 0) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: TCL½ðÄÜµç³Ø±àÂë¹æÔò
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index:4
	// LenIndex: ³¤¶È±ØÎª4
	// creator: fdl
	public static String TCLBatteryProduct(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 4; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortTCLBatteryProduct(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: TCL½ðÄÜµç³Ø±àÂë¹æÔò¡ª¡ªµÚÈý¼¶±àÂë
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index:4
	// LenIndex: ³¤¶È±ØÎª4
	// creator: fdl
	public static String ProductCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (IDstr[0] == 2) {
			try {
				String code = "";
				String regex = "[0-9]{0,-1}";
				for (int i = 0; i < LenIndex - 1; i++) {
					code = code.concat(String.valueOf(IDstr[i]));
				}
				Pattern pa = Pattern.compile(regex);
				Matcher ma = pa.matcher(code);
				boolean ret = ma.matches();
				if (ret) {
					return OK;
				} else
					System.out.println("Æ¥ÅäµÚ¶þÌõ¹æÔò");
				return ERR;
			} catch (Exception e) {
				return ERR;
			}
		}
		if (IDstr[0] == 1) {
			if (LenIndex != 4) {
				return ERR;
			}
			try {
				String code = "";
				for (int i = 0; i < 4; i++) {
					code = code.concat(String.valueOf(IDstr[i]));
				}
				RecoDao recoDao = new RecoDao();
				boolean ret = recoDao.getPortProductCode(code);
				if (ret) {
					System.out.println("Æ¥ÅäµÚÒ»Ìõ¹æÔò");
					return OK;
				} else
					return ERR;
			} catch (Exception e) {
			}
		}
		return ERR;
	}

	// Function: 504 µÚ10²¿·Ö£º·þÎñÀàÐÍ¼°ÄÚÈÝ´úÂë
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 4
	// creator: lhx
	public static String ServiceContentCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			int index4 = (int) IDstr[Index[3]] - 48;
			int code = index1 * 1000 + index2 * 100 + index3 * 10 + index4;
			System.out.println("code=" + code);
			if ((code >= 0 && code <= 11) || code == 999)
				return OK;
			else if ((code >= 1000 && code <= 1010) || code == 1999)
				return OK;
			else if ((code >= 2000 && code <= 2180) || code == 2999)
				return OK;
			else if (code == 9999)
				return OK;
			else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}

	}

	// Function: 516 µÚ1²¿·Ö£º½»Í¨Î¥·¨ÐÐÎª·ÖÀà´úÂë
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 4
	// creator: lhx
	public static String TraViolativeActionCode(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}
		try {
			int index1 = (int) IDstr[Index[0]] - 48;
			int index2 = (int) IDstr[Index[1]] - 48;
			int index3 = (int) IDstr[Index[2]] - 48;
			int index4 = (int) IDstr[Index[3]] - 48;
			int code = index1 * 1000 + index2 * 100 + index3 * 10 + index4;
			if ((index1 >= 1 && index1 <= 8) && (index2 >= 0 && index2 <= 7)) {
				if (code >= 1001 && code <= 1073)
					return OK;
				else if (code >= 1101 && code <= 1110)
					return OK;
				else if (code >= 1201 && code <= 1239)
					return OK;
				else if (code >= 1301 && code <= 1339)
					return OK;
				else if (code >= 1601 && code <= 1609)
					return OK;
				else if (code >= 1701 && code <= 1709)
					return OK;
				else if (code >= 2001 && code <= 2055)
					return OK;
				else if (code >= 3001 && code <= 3030)
					return OK;
				else if (code >= 4001 && code <= 4009)
					return OK;
				else if (code >= 4201 && code <= 4203)
					return OK;
				else if (code >= 4301 && code <= 4310)
					return OK;
				else if (code >= 4601 && code <= 4606)
					return OK;
				else if (code >= 5001 && code <= 5067)
					return OK;
				else
					return ERR;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	// Function: 516-2 represent a decimal integer whose value range is from 1
	// to
	// 9999999999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:lhx
	public static String TenByteDecimalnt(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 10) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;
			int i2 = (int) IDstr[Index[2]] - 48;
			int i3 = (int) IDstr[Index[3]] - 48;
			int i4 = (int) IDstr[Index[4]] - 48;
			int i5 = (int) IDstr[Index[5]] - 48;
			int i6 = (int) IDstr[Index[6]] - 48;
			int i7 = (int) IDstr[Index[7]] - 48;
			int i8 = (int) IDstr[Index[8]] - 48;
			int i9 = (int) IDstr[Index[9]] - 48;

			long i = 1000000000 * i0 + 100000000 * i1 + 10000000 * i2 + 1000000
					* i3 + 100000 * i4 + i5 * 10000 + i6 * 1000 + i7 * 100 + i8
					* 10 + i9;
			if (i >= 1 && i <= 9999999999l) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:510 ¾ÓÃñÉí·ÝÖ¤²ÄÁÏ¼°ËùÓÐÈí¼þ¡¢Éè±¸´úÂë
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 7
	// Creator:lhx
	public static String IDcardByMaterial(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 7) {
				return ERR;
			}
			String code = "";
			for (int i = 0; i < 3; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}

			int i0 = (int) IDstr[Index[3]] - 48;
			int i1 = (int) IDstr[Index[4]] - 48;
			int i2 = (int) IDstr[Index[5]] - 48;
			int i3 = (int) IDstr[Index[6]] - 48;
			long i = 1000 * i0 + 100 * i1 + 10 * i2 + i3;
			if (code != null && code.equals("JCL")) {
				if (i == 110 || i == 120 || i == 190 || i == 0220 || i == 290
						|| i == 0310 || i == 0320 || i == 0330 || i == 390
						|| i == 0410 || i == 490 || i == 0510 || i == 0520
						|| i == 0530 || i == 590 || i == 610 || i == 690
						|| i == 710 || i == 720 || i == 730 || i == 740
						|| i == 750 || i == 760 || i == 790 || i == 210)
					return OK;
				else
					return ERR;
			} else if (code != null && code.equals("JGC")) {
				if (i == 110 || i == 120 || i == 130 || i == 140 || i == 190
						|| i == 210 || i == 290)
					return OK;
				else
					return ERR;

			} else if (code != null && code.equals("JRJ")) {
				if (i == 110 || i == 120 || i == 130 || i == 140 || i == 150
						|| i == 190 || i == 210 || i == 290 || i == 310
						|| i == 390 || i == 410 || i == 420 || i == 490
						|| i == 590)
					return OK;
				else
					return ERR;
			} else if (code != null && code.equals("JCS")) {
				if (i == 110 || i == 120 || i == 190 || i == 130)
					return OK;
				else
					return ERR;
			} else if (code != null && code.equals("JGS")) {
				if (i == 110 || i == 150 || i == 190 || i == 210 || i == 220
						|| i == 290 || i == 310 || i == 320 || i == 390
						|| i == 490 || i == 590 || i == 120)
					return OK;
				else
					return ERR;
			} else if (code != null && code.equals("JAS")) {
				if (i == 110 || i == 120 || i == 130 || i == 190 || i == 210
						|| i == 230 || i == 220 || i == 290 || i == 240
						|| i == 310 || i == 320 || i == 390)
					return OK;
				else
					return ERR;
			} else if (code != null && code.equals("JJS")) {
				if (i == 110 || i == 120 || i == 190 || i == 210 || i == 220
						|| i == 290)
					return OK;
				else
					return ERR;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÐÅÓÃÖ÷Ìå±íÊ¾¹æ·¶Ð£ÑéÂë ÀàËÆMOD112
	// ÆäÖÐMOD£­±íÊ¾ÇóÓàº¯Êý£»i£­±íÊ¾´úÂë×Ö·û´Ó×óÖÁÓÒÎ»ÖÃÐòºÅ£»Ci£­±íÊ¾µÚiÎ»ÖÃÉÏµÄ´úÂë×Ö·ûµÄÖµ£»Wi£­±íÊ¾µÚiÎ»ÖÃÉÏµÄ¼ÓÈ¨Òò×Ó£¬
	// ¼ÓÈ¨Òò×ÓµÄ¹«Ê½ÊÇ£º2µÄn-1´ÎÃÝ³ýÒÔ11È¡ÓàÊý£¬n¾ÍÊÇÄÇ¸öi£¬´ÓÓÒÏò×óÅÅÁÐ
	// µ±Ð£¼ìµÄÖµÎª10Ê± ¸³ÖµÎ»X.µ±Ð£¼ìµÄÖµÎª11Ê± ¸³ÖµÎ»0
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:YZC
	public static String MOD11(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// ISO 7064:1983.MOD 11-2Ð£ÑéËã·¨£¬×Ö·û´®¿ª±Ù¿Õ¼äÊ±Òª¶àÒ»Î»Áô¸ø×îºó¼ÓÐ£ÑéÎ»
			double sum = 0; // ×îºóµÄÐ£ÑéÂë
			int i, j;
			int b = LenIndex - 1;
			int a;
			a = 'A';
			for (j = 0; j < LenIndex - 1; j++) {
				for (i = 0; i < 26; i++) {
					char c = (char) (a + i);
					if ((int) IDstr[Index[j]] == c) {
						IDstr[Index[j]] = (char) (10 + i);
					}
				}
			}

			for (i = 0; i < LenIndex - 1; i++) {
				if (IDstr[Index[i]] > 47) {
					IDstr[Index[i]] = (char) (IDstr[Index[i]] - 48);
				}
				sum = sum + (int) (IDstr[Index[i]])
						* (Math.pow(2, LenIndex - i - 1) % 11);
			}
			char check;
			int mod;
			sum %= 11;
			mod = (int) (11 - sum) % 11;
			if (mod == 10) {
				check = "X".charAt(0); // X±íÊ¾10
			} else {
				String jieshou = Integer.toString(mod);
				check = jieshou.charAt(0);
			}
			System.out.println(check);
			if (check == (IDstr[Index[b]])) {

				return OK;

			} else {

				return ERR;

			}

		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÐÅÓÃÖ÷Ìå±êÊ¶¹æ·¶ÕýÔòÆ¥Åä,Êý×Ö(603)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È<=2
	// creator: yzc
	public static String CreditIdentifiers(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[0-9]{0,1}";
			int prefix = 13;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÂÖÌ¥ÆøÃÅ×ì¼°ÆäÁã²¿¼þµÄ±êÊ¶·½·¨ÕýÔòÆ¥Åä,×ÖÄ¸(615)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È<=1
	// creator: yzc
	public static String TubesValves(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[C]{0,}";
			int prefix = 4;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ÂÖÌ¥ÆøÃÅ×ì¼°ÆäÁã²¿¼þµÄ±êÊ¶·½·¨ÕýÔòÆ¥Åä,×ÖÄ¸(615)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È<=1
	// creator: yzc
	public static String TubesValves1(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[C]{0,}";
			int prefix = 3;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:ÉÌÆ·ÌõÂë Ó¦ÓÃ±êÊ¶·û£¨632£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String BarCodeForCommodity(char[] CODEstr, int LenCODE,
			int[] Index, int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 2 || LenIndex != 3 || LenIndex != 4) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getBarCodeForCommodity(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: Ê¶±ð¿¨ ·¢¿¨Õß±êÊ¶ µÚÒ»²¿·ÖÕýÔòÆ¥Åä,Êý×Ö(635)
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:³¤¶È<=12
	// creator: yzc
	public static String IdentificationCardsP1(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			String code = "";
			String regex = "[0-9]{0,11}";
			int prefix = 6;
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (Index[0] != prefix) {
				return ERR;
			}
			for (int i = Index[0]; i < LenID - 1; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	/*
	 * Ð£ÑéÇ°4Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase70(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {

		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "43", "44", "45", "46", "47", "48", "49", "50", "51",
				"52", "53" };
		for (int i = 0; i < 11; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;

	}

	/*
	 * Ð£ÑéÇ°4Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase71(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {

		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 4) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayDatabase71(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase66(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "11", "12", "13", "14", "15", "16", "17", "18", "24",
				"31", "41", "42", "43", "44", "51", "52", "53", "54", "55",
				"56", "57", "61", "62", "90" };
		for (int i = 0; i < 24; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase65(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "11", "12", "13", "17", "18", "19" };
		for (int i = 0; i < 6; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase59(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "10", "11", "12", "20" };
		for (int i = 0; i < 4; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * 757 author:wt
	 */
	public static String HighwayDatabase47(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 3) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayDatabase47(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * 757 author:wt
	 */
	public static String HighwayDatabase46(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayDatabase46(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase26(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "00", "01", "02", "03", "04", "05", "06", "07", "08",
				"09", "10", "11", "12", "13", "14", "15", "90" };
		for (int i = 0; i < 17; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase25(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "00", "01", "02", "03", "04", "05", "06", "07", "08",
				"09", "10", "11", "12", "90" };
		for (int i = 0; i < 14; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase24(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "10", "11", "12", "13", "14", "15", "16", "17", "18",
				"20", "21", "22", "23", "24", "25", "26", "27", "30", "31",
				"32", "40", "50", "60", "61", "62", "63", "70", "80", "90" };
		for (int i = 0; i < 29; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase18(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "10", "11", "12", "20", "21", "22", "23", "30", "31",
				"32", "33", "40", "41", "42", "50", "90" };
		for (int i = 0; i < 16; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * 757 author:wt
	 */
	public static String HighwayDatabase17(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}

		String code = "";
		for (int i = 0; i < LenIndex; i++) {
			code = code.concat(String.valueOf(IDstr[Index[i]]));
		}
		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHighwayDatabase17(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase16(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "10", "11", "12", "13", "20", "21", "22", "30", "31",
				"32", "40", "41", "42", "43", "44", "45" };
		for (int i = 0; i < 16; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase13(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "10", "11", "12", "20", "21", "22", "23", "24", "25",
				"26", "27", "28", "29", "40", "41", "42", "43", "44", "45",
				"46", "47", "90" };
		for (int i = 0; i < 22; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase8(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "10", "11", "12", "13", "14", "15", "16", "17", "18",
				"19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
				"29", "30", "40", "41", "42", "50", "60", "70", "80", "90" };
		for (int i = 0; i < 29; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase7(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "GG", "GS", "GX", "GY", "GZ", "SS", "SX", "SY", "SZ",
				"XX", "XY", "XZ", "YY", "YZ" };
		for (int i = 0; i < 14; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	/*
	 * Ð£ÑéÇ°2Î»ÊÇ²»ÊÇÕâÐ©Êý×Ö 757 author:wt
	 */
	public static String HighwayDatabase6(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 2) {
			return ERR;
		}
		String code = "" + String.valueOf(IDstr[Index[0]])
				+ String.valueOf(IDstr[Index[1]]);
		String[] data = { "10", "11", "20", "21", "30", "31", "32", "33", "34",
				"35", "36", "40", "41", "50", "51", "60", "61", "70", "71",
				"72", "73", "74", "75", "76", "90", "91" };
		for (int i = 0; i < 26; i++) {
			if (code.equals(data[i]))
				return OK;
		}
		return ERR;
	}

	// Function:ÖÐ¹úÊ¯ÓÍÌìÈ»Æø×Ü¹«Ë¾Æó¡¢ÊÂÒµµ¥Î»´úÂë£¨763£©
	// CODEstr: ±êÊ¶±àÂë
	// LenCODE: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:
	// Creator:YZC
	public static String GassCompany(char[] CODEstr, int LenCODE, int[] Index,
			int LenIndex) {

		if (!checkInputParam(CODEstr, LenCODE, Index, LenIndex)) {
			return ERR;
		}

		if (LenIndex != 7) {
			return ERR;
		}
		String code = new String(CODEstr);

		try {
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getGassCompany(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	/*
	 * ÑéÖ¤±ê×¼11µÄÇ°ÁùÎ»ÊÇ²»ÊÇÔÚÊý¾Ý¿âÖÐ
	 * 
	 * author:wt
	 */
	public static String HydrologicData(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 6) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < IDstr.length; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getHydrologicData(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}

	}

	/*
	 * ÑéÖ¤±ê×¼12µÄÇ°9Î»ÊÇ²»ÊÇÔÚÊý¾Ý¿âÖÐ
	 * 
	 * author:wt
	 */
	public static String MeatandVegetable(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 9) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < IDstr.length; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMeatandVegetable(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}
	
	//ÖÐ¹ú¶¯Îï·ÖÀà´úÂë fdl
	// Function: represent a decimal integer whose value range is from 010 to 999
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator: fdl
	public static String ChinaAnimal(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 16) {
				return ERR;
			}

			if ((IDstr[Index[0]] == 'A' && IDstr[Index[1]] == 'G')
					|| (IDstr[Index[0]] == 'A' && IDstr[Index[1]] == 'M')
					|| (IDstr[Index[0]] == 'A' && IDstr[Index[1]] == 'V')
					|| (IDstr[Index[0]] == 'M' && IDstr[Index[1]] == 'A')
					|| (IDstr[Index[0]] == 'P' && IDstr[Index[1]] == 'S')
					|| (IDstr[Index[0]] == 'R' && IDstr[Index[1]] == 'P')
					) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}
	
	// Function: É­ÁÖÀàÐÍ±àÂë¹æÔò
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index:5
	// LenIndex: ³¤¶È±ØÎª5
	// creator: fdl
	public static String ForestTypes(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
			return ERR;
		}
		if (LenIndex != 5) {
			return ERR;
		}
		try {
			String code = "";
			for (int i = 0; i < 5; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPortForestTypes(code);
			if (ret) {
				return OK;
			} else
				System.out.println("ERR000");
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}
	

	// Function:654 ÑÌ¾íÏäÓÃÌõÂë±êÇ© ×éÖ¯»ú¹¹ÀàÐÍ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 2
	// Creator:lhx
	public static String OrganizationCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;

			int i = 10 * i0 + i1;
			if ((i >= 10 && i <= 23) || i == 99) {
				return OK;
			}
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:654 ÑÌ¾íÏäÓÃÌõÂë±êÇ© ×éÖ¯ËùÊôÊ¡¡¢×ÔÖÎÇø¡¢Ö±Ï½ÊÐ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 2
	// Creator:lhx
	public static String Province(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;

			int i = 10 * i0 + i1;
			if (i >= 11 && i <= 15) {
				return OK;
			} else if (i >= 21 && i <= 23)
				return OK;
			else if (i >= 31 && i <= 37)
				return OK;
			else if (i >= 41 && i <= 46)
				return OK;
			else if (i >= 50 && i <= 54)
				return OK;
			else if (i >= 61 && i <= 65)
				return OK;
			else if (i == 71 || i == 81 || i == 82 || i == 00)
				return OK;

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:654 ÑÌ¾íÏäÓÃÌõÂë±êÇ© ×éÖ¯ËùÊôÊÐ¡¢µØÇø
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 2
	// Creator:lhx
	public static String City(char[] IDstr, int LenID, int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;

			int i = 10 * i0 + i1;
			if (i >= 0 && i <= 70) {
				return OK;
			} else if (i == 90)
				return OK;

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:658 ¶þÎ»ÌõÐÎÂëÍø¸ñ¾ØÕóÂë Ð¡Ð´×ÖÄ¸Ä£Ê½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 5
	// Creator:lhx
	public static String Letter(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 5) {
				return ERR;
			}
			String code = "";

			for (int k = 0; k < 5; k++) {
				if (IDstr[k] == '0' || IDstr[k] == '1') {
					code = code.concat(String.valueOf(IDstr[k]));
				} else
					return ERR;
			}
			BigInteger src = new BigInteger(code, 2);
			if (src.intValue() >= 0 && src.intValue() <= 26)
				return OK;
			else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:658 ¶þÎ»ÌõÐÎÂëÍø¸ñ¾ØÕóÂë Êý×Ö×ÖÄ¸»ìºÏÄ£Ê½
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 6
	// Creator:lhx
	public static String DigitAndLetter(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			String code = "";

			for (int k = 0; k < 6; k++) {
				if (IDstr[k] == '0' || IDstr[k] == '1') {
					code = code.concat(String.valueOf(IDstr[k]));
				} else
					return ERR;
			}
			BigInteger src = new BigInteger(code, 2);
			int traCode = src.intValue();
			if (traCode >= 48 && traCode <= 57)
				return OK;
			else if (traCode >= 65 && traCode <= 90)
				return OK;
			else if (traCode >= 97 && traCode <= 122)
				return OK;
			else if (traCode == 32)
				return OK;
			else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:700 Â½ÉúÒ°Éú¶¯ÎïÒß²¡·ÖÀà
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 10
	// Creator:lhx
	public static String AnimalDisease(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 10) {
				return ERR;
			}
			String code = "";

			for (int k = 0; k < 10; k++) {

				code = code.concat(String.valueOf(IDstr[k]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getAnimalDiseaseByCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:704º£ÑóÐÅÏ¢·ÖÀàÓë´úÂë3,4Á½Î»¶¨Òå
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 2
	// Creator:lhx
	public static String OceanInfoMid(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			int i1 = (int) IDstr[Index[1]] - 48;
			int i0 = (int) IDstr[Index[0]] - 48;

			int i = 10 * i0 + i1;
			if (i >= 1 && i <= 14) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:698 È«¹úÎÀÉúÐÐÒµÒ½ÁÆÆ÷Ðµ¡¢ÒÇÆ÷Éè±¸·ÖÀà
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 8
	// Creator:lhx
	public static String MedicalInstru(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 8) {
				return ERR;
			}
			String code = "";

			for (int k = 0; k < 8; k++) {

				code = code.concat(String.valueOf(IDstr[k]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getMedicalInstrumentByCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}

	// Function:728(1)ÖÐÒ½¼²²¡·ÖÀà
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 6
	// Creator:lhx
	public static String TCMDisease(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			String code = "";

			for (int k = 0; k < 6; k++) {

				code = code.concat(String.valueOf(IDstr[k]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTCMdiseaseByCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}
	// Function:728(2)ÖÐÒ½¼²²¡Ö¢×´
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 6
	// Creator:lhx
	public static String TCMFeature(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 6) {
				return ERR;
			}
			String code = "";

			for (int k = 0; k < 6; k++) {

				code = code.concat(String.valueOf(IDstr[k]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getTCMFeatureByCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}
	// Function:706,708µØÖÊ·ÖÀà
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 2
	// Creator:lhx
	public static String DZClassify(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			String code = "";

			for (int k = 0; k < 2; k++) {

				code = code.concat(String.valueOf(IDstr[k]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getDZClassifyByCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}
	// Function:710µØÖÊ·ÖÀà
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes 2
	// Creator:lhx
	public static String DZClassify710(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 2) {
				return ERR;
			}
			String code = "";

			for (int k = 0; k < 2; k++) {

				code = code.concat(String.valueOf(IDstr[k]));
			}
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getDZClassify710ByCode(code);
			if (ret) {
				return OK;
			} else
				return ERR;

		} catch (Exception e) {
			return ERR;
		}
	}
	// Function: 722 ¹ú¼ÊÃ³Ò×¼ÆÁ¿µ¥Î»
	// IDstr: ±êÊ¶±àÂë
	// LenID: ±êÊ¶±àÂëµÄ³¤¶È
	// Index: µ÷ÓÃÕýÔòµÄµÄË÷ÒýÎ»ÖÃ
	// LenIndex:²»¶¨³¤
	// creator: lhx
	public static String MeasureUnit(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			String code = "";
			String regex = "[0-9,A-Z]{2,3}";
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			
			for (int i = 0; i < LenID; i++) {
				code = code.concat(String.valueOf(IDstr[i]));
			}
			Pattern pa = Pattern.compile(regex);
			Matcher ma = pa.matcher(code);
			boolean ret = ma.matches();
			if (ret) {
				return OK;
			} else
				return ERR;
=======
>>>>>>> menglunyang-master
		} catch (Exception e) {
			return ERR;
		}
	}
}
