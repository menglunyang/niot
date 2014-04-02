package cn.niot.rule;

import cn.niot.dao.RecoDao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleFunction {

	static String ERR = "ERR";
	static String OK = "OK";
	static int NO_LENGHT_LIMIT = -1;

	public static void main(String[] args) {
		// System.out.println("�������!");
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
				if (lengthMaxMin.length == 1) {// 1����
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

	// Function:��λ���һλΪ1ʱ���ڶ�λΪ��0~9������һλΪ2ʱ���ڶ�λΪ��0~3������һλΪ9ʱ���ڶ�λΪ9.
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, �̶�Ϊ2
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

	// Function: 6λ���������.
	// Function: 6λ����������ǰ2λ.
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������������λ��
	// LenIndex: ���ȱ�����2λ
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

	// Function: 6λ���������.(296)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������������λ��
	// LenIndex: ���ȱ�����6λ
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

	// Function: �������͵�����ƴ���ΪCPC�������,(279)�й涨���볤��Ϊ2-3λ��CPC����Ϊ�ڵ�4λ��0.
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �����������͵�����ƴ����λ��
	// LenIndex: �����Ƕ��٣�һ����4λ
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

	// Function: �������͵�����ƴ��룬(279)�й涨���볤��Ϊ2-3λ.
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �����������͵�����ƴ����λ��
	// LenIndex: �����Ƕ��٣�һ����2-3λ
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

	// Function: �̲ݻ�е��Ʒ������ ����ͱ��� ��3���֣���е�⹺��(7)�е�5λ����.
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �����̲ݻ�е��Ʒ�����ϴ����λ��
	// LenIndex: ������6λ
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

	// Function: ��Ʒ����������Ʒ����EAN UPCǰ3λǰ׺��.
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ����ǰ׺���λ��
	// LenIndex: ������3λ
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

	// Function: �̲ݻ�е���� ����ͱ����2���֣�ר�ü� ��¼D�еĵ�λ����.(672)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ����ǰ׺���λ��
	// LenIndex: ������2λ��Ϊ��д��ĸ
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

	// Function: 4λ�������.
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ����������õ�λ��
	// LenIndex: ������4λ��Ϊ����
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

	// Function: CID������������.
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: (18,-1),��18λ�Ժ���ַ����������ʽ��֤
	// LenIndex: ���ȱ�Ϊ2
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

	// Function: �̲���ҵ��׼����������������룬�������Ʒ�ִ���(6)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���������루1λ���������루2λ����Ʒ�ִ��루2λ����λ��
	// LenIndex:���ȱ�Ϊ5
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

	// Function: �̲ݻ�е��Ʒ�����Ϸ���ͱ��� ��6���֣�ԭ��������(4)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���������루2λ����Ʒ�ִ��루3λ����λ��
	// LenIndex:���ȱ�Ϊ5
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

	// Function: ��ʻ��˴��?֤��ʶ������в���������ҵ�Զ����������ƥ��,���ֻ�����ĸ����������ĸ���档(55)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����<=16
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
			// ���һλΪУ��λ
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

	// Function: ��ʳ��Ϣ��������� �����Ʒ��������(15)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ6
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

	// Function: ��ʳ��Ϣ��������� ��ʳ�豸���������(23)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ8
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

	// Function: ��ʳ��Ϣ��������� ��ʳ��ʩ��������루24��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ7
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

	// Function: �̲ݻ�е��Ʒ������ ����ͱ��� ��5���֣�����Ԫ���� ��5��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ5
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

	// Function:��λ���һλΪ0ʱ���ڶ�λΪ��0��1��2������һλΪ1ʱ���ڶ�λΪ��1��2,6,7������һλΪ��2��ʱ���ڶ�λΪ��0~5��
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, �̶�Ϊ2
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

	// Function: �ж������ֽ��ǲ��Ǵ���·�
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, �̶�Ϊ2
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

	// Function: �ж�����ֽ��ǲ�������LS/T 1704.3-2004��1�еı���
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, �̶�Ϊ6
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

	// Function: �ж�2���ֽ��ǲ�������(01-07,99)
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, �̶�Ϊ2
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

	// Function: �ж�2���ֽ��ǲ�������(01-06,99)
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, �̶�Ϊ2
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

	// Function: UCODE ��Top Level Domain Code: TLDc��ȡֵ����Ϊ"E000"�͡�FFFF��
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, �̶�Ϊ4
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

	// Function: EPC��������������(Domain Manager)����ȡֵΪ0xA011363��ȫ0
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

	// Function: �����Ƿ����� ���2��ʼ���ż��λ������֮�͢٣��١�3=�ڣ������3��ʼ�������λ������֮�ۣ͢���+��=�ܣ��ô���
	// ����ڽ�����Ϊ�������С���ȥ�ܵõ���ֵ��
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
				even_sum += (IDstr[i] - 48); // ASCII���� �ַ�'0'��Ӧ����30H,ʮ���ƾ���48
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

	// Function:12λ�����룺6λ ���ÿ���ʱ�䷨��ʱ��δ֪��ȫ����"*"����֪�����199***��֪��ݲ�֪�·ݣ���2008**��
	// ֪��ʱ�䣬��20080708�� ����λ��˳��ţ�����Ϊȫ0
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

			// ����һ�γ���"*"ʱ�����ʱ�����λ�����У��Ӹ�λ��ʼ���涼��"*"
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

			// �ж� �·�
			int[] Index_month = { Index[4], Index[5] };

			// if ((Month(IDstr, LenID, Index_month, Index_month.length)) ==
			// ERR) { // ����
			// // ʵ�ֵĺ����ж��Ƿ����·�
			// return ERR;
			// }

			// xjf �޸ĺ�
			if (IDstr[Index[4]] != '*' && Index[5] != '*') {
				if ((Month(IDstr, LenID, Index_month, Index_month.length)) == ERR) { // ����
					// ʵ�ֵĺ����ж��Ƿ����·�
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

			int zero_count = 0; // ����ȫ0
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

	// Function:12λ������,��6λ���������6λ����������(��귨),��������ȡС���ǰ��λ����
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

			// �жϺ������ķ�Χ
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

	// Function:12λ������,���÷��ڷ���4λ�ַ��Ż򷿲������롢4λ�ڵغš�4λ��˳�����ɣ�4λ��˳���0001~9999
	// IDstr: ID string
	// LenID: the number of characters in the ID string is 26
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes is 12
	// Creator:Wu Zhenyu
	public static String HouseCode_CheckBasedFenzong(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			// 4λ�ַ��� ���������룬���ĵ����ҵ��ı��ֻ����λ
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

			// 4λ�ڵغ�
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

			// 4λ��˳���
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

	// Function:12λ�����룬���÷ַ�8λ�ַ�ͼ����ͼ�ź�4λ��˳�����ɣ�4λ��˳���0001~9999
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

			// ǰ��λ
			for (; i < 8; i++) {
				if (IDstr[Index[i]] < '0' || IDstr[Index[i]] > '9') {
					return ERR;
				}
			}

			// ����λ˳��ţ�0001~9999
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

	// Function:����ɻ���ʱ��˳���0001��ʼ��0001~9999
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

	// Function: �м�12λ�����룬ͬʱ�����ַ���
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

	// У���룬���ݴ���26λ��25λ�����룬���һλУ����
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
	// int result = 10 + (IDstr[0] - 48); // ��¼У��������м��̲����ֵ
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

	// Function: У���㷨 ʵ�� C=MOD(11-MOD(��Ci��Wi,11),10)
	// ����MOD����ʾ���ຯ��i����ʾ�����ַ��������λ����ţ�Ci����ʾ��iλ���ϵĴ����ַ��ֵ��Wi����ʾ��iλ���ϵļ�Ȩ���ӣ�
	// ��Ȩ���ӵĹ�ʽ�ǣ�2��n-1���ݳ���11ȡ����n�����Ǹ�i��������������
	// ��У���ֵΪ10ʱ ��ֵλX
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	public static String DeviceMOD163(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		// MOD У���㷨���ַ��ٿռ�ʱҪ��һλ��������У��λ
		double sum = 0; // ����У����
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

	// Function: ���뵰��Ʒ��������루232���еĵ��뵰��Ʒ�����
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���3
	// Creator:�?�� 232
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
	// Creator:�?��
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
	// Creator:�?��
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

	// Function: ʵ��ģ10����λ��2����͵�У��
	// ��A-Z�����10���Ƶ�10-35�����µ�10��������µ����飻��������Ĵ��ҵ���ʼÿһλ����2��1��ѭ�����sum
	// У��λ��ֵΪ 10-sum%10
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:�?��
	public static String InternationalSecurities(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			int i = 0; // �����ж�16����
			int j; // ��������ı���
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
			int f; // ����X2���������
			int sum = 0; // ���ڽ���У����
			int check; // ����У�����ֵ
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

	// Function: ISO 7064:1983.MOD 11-2У���㷨 ʵ�� C=11-MOD(��Ci��Wi,11)
	// ����MOD����ʾ���ຯ��i����ʾ�����ַ��������λ����ţ�Ci����ʾ��iλ���ϵĴ����ַ��ֵ��Wi����ʾ��iλ���ϵļ�Ȩ���ӣ�
	// ��Ȩ���ӵĹ�ʽ�ǣ�2��n-1���ݳ���11ȡ����n�����Ǹ�i��������������
	// ��У���ֵΪ10ʱ ��ֵλX
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:�?��
	public static String MOD112(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// ISO 7064:1983.MOD 11-2У���㷨���ַ��ٿռ�ʱҪ��һλ��������У��λ
			double sum = 0; // ����У����
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
				check = "X".charAt(0); // X��ʾ10
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

	// Function: ʵ��У�� MOD 16-3 ��16���Ƶ������10���ƣ����µ�10���Ƶ���ֵ����Ȩ�ض�16ȡ�ࣻȨ��λ11,9,3,1��ѭ��
	// Ȩ��λ1~9��ѭ��
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���16
	// Creator:�?��
	public static String MOD163(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// MOD 16-3У���㷨���ַ��ٿռ�ʱҪ��һλ��������У��λ
			double sum = 0; // ������У������ֵ
			int i;
			int w = 0; // Ȩ��
			int h = 0; // ʮ����
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

	// Function: ʵ��У��
	// ����������λ����1��ż��λ����2�ĺ�sum
	// У��λ��ֵΪ 10-sum%10
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:�?��
	public static String MrpCheck(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			int f; // ����X2���������
			int sum = 0; // ���ڽ���У����
			int check; // ����У�����ֵ
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
	// Creator:�?��
	public static String MusicCheck(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// ��T��ʼ��Index
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
	// Creator:�?��
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

	// Function: ֵֻ��λSR MX SM YZ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:�?��
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

	// Function:��֯Ʒ ��֯����֯���뼰ʾ�� ��������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ��� ���̶�
	// Index: ��������ĵ�����λ��
	// LenIndex:���̶�
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

	// Function: ��Ʒ���� �ʲ�����������ʾ����ϵ�к�Ϊ1-16λ��ʹ���������ƥ��(58)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ��� ���̶�
	// Index: ��������ĵ�����λ��
	// LenIndex:0-13λΪȫ��ɻ����ʲ����,LenIndex��Ϊ3����һλΪ��ʼ��λ��ڶ�λΪ������ظ��Ĵ���,����λΪ-1
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

	// Function: ҩƷ���Ӽ����Ӧ������򣬵�IDstr[1]Ϊ9ʱ��Ӧ�������Ϊ0,1,2
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ��� 20λ
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ1��ֻ��֤IDstr[1]�Ƿ�Ϊ9
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
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ��� 20λ
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ1��ֻ��֤IDstr[1]�Ƿ�Ϊ9
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

		} catch (Exception e) {
			return ERR;
		}
	}
}
