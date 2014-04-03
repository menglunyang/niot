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
	// information="��ͨ����վ";
	// return OK;
	// }
	// if ((IDstr[index1] == '1') && (IDstr[index2] == '1')) {
	// information="һ����ͨ����վ";
	// return OK;
	// }
	// if ((IDstr[index1] == '1') && (IDstr[index2] == '2')) {
	// information="������ͨ����վ";
	// return OK;
	// }
	// if ((IDstr[index1] == '2') && (IDstr[index2] == '0')) {
	// information="��������վ";
	// return OK;
	// }
	// if ((IDstr[index1] == '6') && (IDstr[index2] == '0')) {
	// information="ˮ������վ";
	// return OK;
	// }
	// if ((IDstr[index1] == '7') && (IDstr[index2] == '0')) {
	// information="��������վ";
	// return OK;
	// }
	// if ((IDstr[index1] == '8') && (IDstr[index2] == '0')) {
	// information="½���ѾȻ���";
	// return OK;
	// }
	// if ((IDstr[index1] == '9') && (IDstr[index2] == '0')) {
	// information="��������վ";
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }
	// 393������Ϣ����
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
	// inforfation="��Ȼ����ϵͳ";
	// return OK;
	// }
	// if ((IDstr[index1] == '1') && (IDstr[index2] == '1')) {
	// inforfation="�ɿ���������Ȼ����";
	// return OK;
	// }
	// if ((IDstr[index1] == '2') && (IDstr[index2] == '0')) {
	// inforfation="��е������ϵͳ";
	// return OK;
	// }
	// if ((IDstr[index1] == '2') && (IDstr[index2] == '1')) {
	// inforfation="��е��ѹ�ͷ����";
	// return OK;
	// }
	// if ((IDstr[index1] == '2') && (IDstr[index2] == '2')) {
	// inforfation="��е����";
	// return OK;
	// }
	// if ((IDstr[index1] == '9') && (IDstr[index2] == '0')) {
	// inforfation="����������ϵͳ";
	// return OK;
	// }
	// return ERR;
	// } catch (Exception e) {
	// return ERR;
	// }
	// }
	// 395����������Ϣ����
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

	// 399����������Ϣ����57���� ������ˮ��ʩ����
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

	// 403����������Ϣ����53����������������
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

	// 409����������Ϣ����48���֣�����ѵ�����˴���
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

	// Function: ʵ��У�� MOD 97-10
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���13
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

	// 194-�̲ݻ�е�������úͼ����ļ����븽¼C���ѯ
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���3λ
	// Index: ������֤�㷨������λ��
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

	// 91-�й�ú̿����ϵͳ ��1-12λ��Ӧ����
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ��� 12λ
	// Index: ������֤�㷨������λ��
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

	// 90-��Ʒ���롪�����뷽λ�����������ʾ ��13У����
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ��� 13λ
	// Index: ������֤�㷨������λ��
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
			int sum1 = 0;// ż�����λ�ϵ���ֵ��
			int sum2 = 0;// �������λ�ϵ���ֵ��
			int dd = 0;// sum�������м���
			int code = 0;// ���һλ��֤��

			for (int i = 1; i < 12; i++) {
				if (i % 2 == 1) {
					sum1 = sum1 + (IDstr[i]);
					sum1 = sum1 - 48;// �ַ�ת��Ϊ����
					System.out.println((int) IDstr[i]);
				}
			}
			System.out.println("sum1=" + sum1);
			sum1 = sum1 * 3;
			for (int i = 0; i < 11; i++) {
				if (i % 2 == 0) {
					sum2 += IDstr[i];
					sum2 = sum2 - 48;// �ַ�ת��Ϊ����
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
			System.out.println("��֤��λ=" + code);
			if (code == (int) IDstr[12] - 48) {
				return OK;
			}

			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// 195-�̲ݻ�е������Ʒ�����ļ�������Ʒ��� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���3λ
	// Index: ������֤�㷨������λ��
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
			if (i1 == 2) { // ְԱ����
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
			if (i1 == 4) { // רҵ����ְ�񼶱�
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

	// Function: ʵ��У��Nλ��У��
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:������
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

	// Function: ʵ��У��6λ����������
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:������
	// У��������123450
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

	// 268-������ȫ���˴���Э�������������Ʒ��� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���3λ
	// Index: ������֤�㷨������λ��
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

	// 270������Ȼ�ֺ�Natural disaster
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���6λ
	// Index: ������֤�㷨������λ��
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

	// 275��������ҵ����
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���4λ
	// Index: ������֤�㷨������λ��
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

	// 276��������Ʒ����
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���4λ
	// Index: ������֤�㷨������λ��
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

	// 281-�����鱦��ʯ���������ʷ��������Ʒ��� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���6λ
	// Index: ������֤�㷨������λ��
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

	// 216-�������뵳�����ش�����Ʒ��� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���3λ
	// Index: ������֤�㷨������λ��
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
	// if (i1 == 2) { // ְԱ����
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
	// if (i1 == 4) { // רҵ����ְ�񼶱�
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
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 504
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 504 509
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 511
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 512
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 213
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 213
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 213
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 214
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 225
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 225
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 225
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 225
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 225
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 225
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 225
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
	// Creator:����
	public static String MOD3736(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// ISO 7064:1983.MOD 11-2У���㷨���ַ������ٿռ�ʱҪ��һλ��������У��λ
			double sum = 0; // ����У����
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
			// ISO 7064:1983.MOD 11-2У���㷨���ַ������ٿռ�ʱҪ��һλ��������У��λ
			double sum = 0; // ����У����
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
	// Creator:����
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

	// Function:622У��λ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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
				check = "X".charAt(0); // X��ʾ10
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

	// Function: У��λ ����3��1 ��
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���16
	// Creator:����
	public static String CommodityCodeCheck632(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// MOD 16-3У���㷨���ַ������ٿռ�ʱҪ��һλ��������У��λ
			double sum = 0; // ������У������ֵ
			int i;
			int w = 0; // Ȩ��
			int h = 0; // ʮ����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����639
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����654
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����654
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

	public static String enUnicode(String content) { // ������ת��Ϊ16������
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

	// Function: 657 Сд��ĸת2����
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���2
	// Creator:����657
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
			String a = Integer.toString(j); // ������ֵ
			BigInteger src = new BigInteger(a); // ת��ΪBigInteger����
			String bb = src.toString(2);
			int bbb = Integer.parseInt(bb);
			jie[i] = bbb;
		}
		for (i = 0; i < LenIndex; i++) {
			j = jie[i];
			String bb = Integer.toString(j); // ������ֵ
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

	// Function: 657 ��д��ĸת2����
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���2
	// Creator:����657
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
			String a = Integer.toString(j); // ������ֵ
			BigInteger src = new BigInteger(a); // ת��ΪBigInteger����
			String bb = src.toString(2);
			int bbb = Integer.parseInt(bb);
			jie[i] = bbb;
		}
		for (i = 0; i < LenIndex; i++) {
			j = jie[i];
			String bb = Integer.toString(j); // ������ֵ
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

	// Function: 657 ������ĸת2����
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���2
	// Creator:����657
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
			String a = Integer.toString(j); // ������ֵ
			BigInteger src = new BigInteger(a); // ת��ΪBigInteger����
			String bb = src.toString(2);
			int bbb = Integer.parseInt(bb);
			jie[i] = bbb;
		}
		for (i = 0; i < LenIndex; i++) {
			j = jie[i];
			String bb = Integer.toString(j); // ������ֵ
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

	// Function: 657 ����ģʽת2����
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����657
	public static String figure(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {

		int j = 0;
		int i, ii = 0;
		int leg;
		String out = null; // ���ڽ��ܻ��ж�
		if (LenIndex % 3 != 0) {
			leg = LenIndex / 3 + 1;
		} else {
			leg = LenIndex / 3;
		}
		int legg = leg * 3;
		int[] jie = new int[leg];
		char[] newIDstr = new char[legg]; // ��������
		int[] GB = new int[leg]; // ���� �������
		int[] GB1 = new int[leg]; // ��������������ڵ�λ��
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
		int[] GB2 = new int[leg]; // ���������������10���Ƶĵ�λ��
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
		int[] newgb = new int[leg + ii]; // ȫ�µ���ֵ���������е�10����
		jj = 0; // ���ڽ��ܣǣ��е�λ
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
		int[] Two = new int[leg + ii]; // ����ת���ɵ�2��������
		for (i = 0; i < leg + ii; i++) {
			j = (int) newgb[i];
			String a = Integer.toString(j); // ������ֵ
			BigInteger src = new BigInteger(a); // ת��ΪBigInteger����
			String bb = src.toString(2);
			int bbb = Integer.parseInt(bb);
			Two[i] = bbb;
		}
		for (i = 0; i < leg + ii; i++) {
			j = Two[i];
			String bb = Integer.toString(j); // ������ֵ
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

	// Function: У��λ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���2
	// Creator:����664
	public static String Check4BitBarCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {

		int i;
		int[] newIDstr = new int[LenIndex];
		for (i = 0; i < LenIndex; i++) {
			newIDstr[i] = (int) (IDstr[Index[i]] - 48);
		}
		// ����2-�ĵ�һλ
		int a = newIDstr[1];
		int b = 0; // ���ڽ��ܼ�Ȩֵ
		if (a * 2 < 10) {
			b = a * 2;
		} else if (a * 2 > 9) {
			b = (a * 2 - 1) % 10;
		}
		// ����2-�ĵڶ�λ
		int a2 = newIDstr[2];
		int b2 = 0; // ���ڽ��ܼ�Ȩֵ
		if (a2 * 2 < 10) {
			b2 = a2 * 2;
		} else if (a2 * 2 > 9) {
			b2 = (a2 * 2 - 1) % 10;
		}
		// ����3�ĵ���λ
		int a3 = newIDstr[3];
		int b3 = 0; // ���ڽ��ܼ�Ȩֵ
		if (a3 * 3 < 10) {
			b3 = a3 * 3;
		} else if (a3 * 3 > 9) {
			b3 = (a3 * 3) % 10;
		}
		// ����5-�ĵ���λ
		int a4 = newIDstr[4];
		int b4 = 0; // ���ڽ��ܼ�Ȩֵ
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

	// Function: У��λ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���2
	// Creator:����664
	public static String Check5BitBarCode(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {

		int i;
		int[] newIDstr = new int[LenIndex];
		for (i = 0; i < LenIndex; i++) {
			newIDstr[i] = (int) (IDstr[Index[i]] - 48);
		}
		// ����2-�ĵ���λ
		int a = newIDstr[5];
		int b = 0; // ���ڽ��ܼ�Ȩֵ
		if (a * 2 < 10) {
			b = a * 2;
		} else if (a * 2 > 9) {
			b = (a * 2 - 1) % 10;
		}
		// ����2-�ĵڶ�λ
		int a2 = newIDstr[2];
		int b2 = 0; // ���ڽ��ܼ�Ȩֵ
		if (a2 * 2 < 10) {
			b2 = a2 * 2;
		} else if (a2 * 2 > 9) {
			b2 = (a2 * 2 - 1) % 10;
		}
		// ����5+�ĵ�һλ
		int a1 = newIDstr[1];
		int b1 = 0; // ���ڽ��ܼ�Ȩֵ
		if (a1 * 5 < 10) {
			b1 = a1 * 5;
		} else if (a1 * 5 > 9) {
			b1 = ((a1 * 5) + (int) Math.floor((a1 * 5) / 10)) % 10;
		}
		// ����5+�ĵ���λ
		int a3 = newIDstr[4];
		int b3 = 0; // ���ڽ��ܼ�Ȩֵ
		if (a3 * 5 < 10) {
			b3 = a3 * 5;
		} else if (a3 * 5 > 9) {
			b3 = ((a3 * 5) + (int) Math.floor((a3 * 5) / 10)) % 10;
		}
		// ����5-�ĵ���λ
		int a4 = newIDstr[3];
		int b4 = 0; // ���ڽ��ܼ�Ȩֵ
		if (a4 * 5 < 10) {
			b4 = a4 * 5;
		} else if (a4 * 5 > 9) {
			b4 = ((a4 * 5) - (int) Math.floor((a4 * 5) / 10)) % 10;
		}
		int check;
		check = 10 - (((b + b2 + b3 + b4 + b1)) % 10);
		// ����5-��У��λ
		int a0 = newIDstr[0];
		int b0 = 0; // ���ڽ��ܼ�Ȩֵ
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
	// Creator:����
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

	// Function: 757 ��59 ֵ 10 11 12 20
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���4
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���3
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:����
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
	// Creator:����
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

	// Function: ��λ����λ
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: 910 �еı�15
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: 910 �еı�18
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: 910 �еı�19
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: 910 �еı�20
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒ ֮��׼��
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable22
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable27
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable28
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable29
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable30
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable31
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable32
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable34
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable35
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable38
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable41
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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

	// Function: DL/T 700.2-1999�ڶ����� �����Ʒtable43
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:����
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 504
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
	// LenIndex: the number of indexes �̶���2
	// Creator:���� 504
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

	// Function: ��ʳ��Ϣ��������� ��ʳó��ҵ��ͳ�Ʒ��������(14)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ6
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

	// Function: ��ʳ��Ϣ��������� ��ʳ�ӹ�(18)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ5
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

	// Function: ��ʳ��Ϣ��������� ��ʳ�ִ�ҵ��ͳ�Ʒ��������(16)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ8
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

	// Function: ��ʳ��Ϣ��������� �������溦���������(17)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ5
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

	// Function: ��ʳ��Ϣ��������� ��ʳ�ӹ���1���֣��ӹ���ҵ���������(19)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ8
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

	// Function: ��ʳ��Ϣ��������� ��ʳ�ִ���3���֣����ķ��������(20)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ4
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

	// Function: ��ʳ��Ϣ��������� ��ʳ�ִ���2���֣���������������(21)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ3
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

	// Function: ��ʳ��Ϣ��������� ��ʳ�ִ���1���֣��ִ���ҵ���������(22)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ5����6
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

	// Function: ��ʳ��Ϣ��������� ��ʳ�����2���֣�������׼���������(26)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ8
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

	// Function: ��������������������(32)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ8
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

	// Function: ��ʳ��Ϣ��������� ��ʳ���� ��1���֣�ָ����������(27)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ8
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

	// Function: ��ʳ��Ϣ��������� ��ʳ���ӹ���Ʒ���������(28)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ7
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

	// Function: ��ʳ��Ϣ��������� ��ʳ���Է��������(29)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ3
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

	// Function: ��ʳ��Ϣ��������� ��ʳ��ҵ���������(30)�е�ǰ��λ�жϣ���ֵ��ΧΪ10,11,19,30
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ2
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

	// Function: ��ʳ��Ϣ��������� ��ʳ��������ҵ���������������������(31)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ6
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

	// Function: ������Ʒ����ʹ���(34)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:���ȱ�Ϊ5
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

	// Function: �����˱�ʶ���������ֱ������������ʽ(44)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����11-17
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

	// Function: �������ӵ�ͼ���ݷ��������(45)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����4
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

	// Function: �����շѹؼ���Ϣ����(46)��ǰ4λΪ��������\u4e00-\u9fa5
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����4
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

	// Function: �ж�2���ֽ��ǲ�������(01-53)
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes, �̶�Ϊ2
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

	// Function: ������Ϣ������������(56)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����5
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

	// Function: ������Ϣ������������(56)�е�������Ϊ����������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:������
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

	// Function: ���ֳ��е�����Ϣ����ƽ̨��������(62)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:������
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

	// Function: ��֯���ϱ��뻯�˲���(64)��֯�������ƴ���
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ5
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

	// Function: ��֯���ϱ��뻯�˲���(64)��֯�������Դ���X1X2
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ2
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

	// Function: ��֯���ϱ��뻯�˲���(64)��ά���� X3X4
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ2
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

	// Function: ��֯�������Դ���(64)X7X8�����᷽̽ʽ,�������Ϊ[1-3,9],[1-3,9]
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ2
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

	// Function: ��֯�������Դ���(64)X9X10 3�ֿ���01-19,99 01-09,99 01-12,99������ѡ�����Χ����
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ2
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

	// Function: ��֯�������Դ���(64)X11X12
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ2
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

	// Function: ��֯�������Դ���(64)X11X12
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ9
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

	// Function: ��·��ͨ��Ϣ������Ϣ���������(68)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ2
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
			// ǰ��λ��
			int index1 = (int) IDstr[0] - 48;
			int index2 = (int) IDstr[1] - 48;
			int i = 10 * index1 + index2;
			// 3,4λ��
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

	// Function: ȫ����Ҫ��Ʒ����������2���� ���������Ʒ(712)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���1-5
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ2,0��-1
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

	// Function: ȫ����Ҫ��Ʒ����������2���� ���������Ʒ��3λ(712)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���1-5
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ3
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

	// Function: ��·��ͨ��Ϣ�ɼ���Ϣ���������(77)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� �̶���4
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

	// Function: �̲���ҵ����ͳ������Ԫ��2���� ���뼯(202)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� �̶���2
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

	// Function: ��Ҷ�����5������Ҷ��ɫ����(204)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� �̶���2
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

	// Function: ��Ҷ�����2������Ҷ��̬����(207)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� �̶���3
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

	// Function: ��Ҷ�����1������Ҷ���������(208)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� �̶���5
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

	// Function: ��ͯ�����״����(213)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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
	// LenIndex: the number of indexes �̶���2
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

	// Function: ����Ƶ�ʴ���(214)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: ����Ƶ�ʴ���(214)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: ����Ƶ�ʴ���(214)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: ������ֹ��ʽ�����(215)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: ������ֹ��ʽ�����(215)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: ����ص�������(215)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: ������Ϣ����Ԫֵ������17���֣���������(218)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ2-8
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

	// Function: ��ͨ���ߴ���(219)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: �����ල������Ա����������(220)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: �����ල������Ա����������(220)
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������ĵ�����λ�� ����Ϊ1-2
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

	// Function: ʵ��У��15λ��17710
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:������
	// ����������110108000000016
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

	// 280-�����鱦��ʯ��������Ʒ���������Ʒ��� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���5λ
	// Index: ������֤�㷨������λ��
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

	// 282-������Ϣ��ȫ����������Ʒ��� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���4λ
	// Index: ������֤�㷨������λ��
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

	// 280-�������뵳�����ش�����Ʒ��� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���3λ
	// Index: ������֤�㷨������λ��
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

	// 284-������ᾭ��Ŀ�����ʹ���� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���6λ
	// Index: ������֤�㷨������λ��
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

	// 285-����������Ϣ����ʹ���� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���6λ
	// Index: ������֤�㷨������λ��
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

	// 287-������װ����ʹ���� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���6λ
	// Index: ������֤�㷨������λ��
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

	// 288-������װ���ַ��������Ʒ��� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���5λ
	// Index: ������֤�㷨������λ��
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

	// 191-����ҽҩ��е����ʹ���� ������ݿ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���6λ
	// Index: ������֤�㷨������λ��
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

	// Function: ��λ�غ�����������������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �����غ�������������λ��
	// LenIndex: ����Ϊ��λ
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

	// Function: ��λ�����ǼǺ�
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ô����ǼǺŴ����λ��
	// LenIndex: ����Ϊ��λ
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

	// Function: �غ�������������ǰ��λ
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �����غ������������ǰ��λ��λ��
	// LenIndex: ����Ϊ��λ
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

	// Function: ��λ�������ʹ���
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���þ������ʹ����λ��
	// LenIndex: ����Ϊ��λ
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

	// Function: ��Ⱦ�����ƴ������3��4λ����˳����룬��һ��1λ���ֱ�ʾ���ڶ���2λ���ֱ�ʾ����3��1λ���ֱ�ʾ������������������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ô�Ⱦ�����ƴ����λ��
	// LenIndex: ���ȱ���Ϊ4λ
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

	// Function: ����վ��վ��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ú���վ���Ŵ����λ��
	// LenIndex: ��������λ
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

	// Function: ������������������ƹ���309��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���õ������Դ����λ��
	// LenIndex: ��������λ
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

	// Function: ũҩ�������Ƽ����루305��
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: �������������λ�ó���Ϊ2-4
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

	// Function: ���ó��ߴ����
	// IDstr: ID String
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: �������������λ�ó���Ϊ6-8
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

	// 377��������ְ
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ��� 4λ
	// Index: ������֤�㷨������λ��
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

	// Function: ɽ��ɽ�����ƴ��루297��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function: ֪ʶ��Ȩ��������Ϣ���༰���루298��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function: ���ú���ҵ��Ϣ��������� (340)
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function: �ߵ�ѧУ���ơ�ר��רҵ���ƴ��루328��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function: ����ά�ޱ�����ϵ �ڶ����֣�337��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function:����ó�׺�ͬ���루326��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function:�����Ƽ��ɹ���������루784��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function:ȫ���������ƴ��루785��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function:������ҵ��λ�����루787��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function:����������Ϣϵͳͼ�η��ŷ�������루788��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function:��ѹ�ȼ����루789��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function:�������ʱ��� �ڶ����� �����Ʒ��909��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function: �����ȴ����շ��༰���ţ����������빤�����Ʋ��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ñ�ʶ�����λ��
	// LenIndex: ����Ϊ2
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

	// Function: ����������ϢҪ�ط��������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ñ�ʶ�����λ��
	// LenIndex: ����Ϊ��λ
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

	// Function: ��������Σ�պ��к����ط��������(354)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex: ���ȱ�Ϊ6
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

	// Function: �л����񹲺͹���·��վ���������(366)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex: ���ȱ�Ϊ6
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

	// Function: ��ת�������ṹ�ķ����ȼ���IP���룩 �ּ�(261)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �������������λ��
	// LenIndex: ������
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

	// Function: ��ҵ������������루237��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �������������λ��
	// LenIndex: ������
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

	// Function: ���ʿ�������������루241-244��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex: ������
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

	// Function: ���ʺ��˻���װж���úʹ������޷�ʽ�������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ������������λ��
	// LenIndex: ����Ϊ��λ
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

	// Function: ��ҵ��Դ��������� ��ľ����
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ������ľ������λ��
	// LenIndex�� ����Ϊ��λ
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

	// Function: �ںӴ������������ǰ��λ��341-1��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ô��������λ��
	// LenIndex: ��������λ
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

	// Function: �ںӴ�����������븽�Ӵ��루341-2��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ø��Ӵ����λ��
	// LenIndex: ��������λ
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

	// Function: ��46���֣�����ѵ����������루411��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ѵ���������������λ��
	// LenIndex: ��������λ
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

	// Function: ��33���֣����ԭ���������루425��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �������ԭ��������������λ��
	// LenIndex: ��������λ
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

	// Function: ��28���֣����������¼���������루425��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: �������������¼��������������λ��
	// LenIndex: ��������λ
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
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ý��ڵ�λ�������λ��
	// LenIndex: ��������λ
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
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ���ý���������λ��
	// LenIndex: ��������λ
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
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ����ְ���������λ��
	// LenIndex: ��������λ
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

	// Function: ����֤�����루470��
	// IDstr: ID string
	// LenID: ��ʶ����
	// Index: ��ʶ����ĳ���
	// LenIndex: ��������λ�ó���Ϊ3
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

	// Function:�����������ֺ�ʡ�����������ܶӴ���(474)
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function:�йܵ�λ����(474)
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function: ����ó�׺�ͬ������ƹ����Զ����������ƥ��,���ֻ�����ĸ����������ĸ���档(326)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����<=9
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

	// Function: ������Ϣ�������������ƥ��,����(776)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:��������
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

	// Function: ��ˮ����������ƥ��,����(782)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:��������
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

	// Function: ���б�ʾ�����֧������������ƥ��,���֣���ĸ(332)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����<=4
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

	// Function: ��֤��֤������루538��
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

	// Function: �ܺ���λ��ҵ������루532��
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

	// Function: �����ֶδ��루539��
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
	// LenIndex: the number of indexes �̶���2
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

	// Function:��··�߱�ʶ����͹����������ƥ��,���֣���ĸ(598)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����<=3
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
	 * ǰ4λ�����֣���2λ��С�� 1234.12 author:wt
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
	 * ������1,2,3,6λ������
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
	 * У�������룬������6λ��7λ������ author��wt
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
	 * У�������룬������4λ��8λ��9������ author��wt
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
	 * У�������룬������8λ��6λ���� ��Ϊ8λʱ����ĵ�һλ��1 ��Ϊ6λʱ����ĵ�һλ��2 author��wt
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
	 * У�������룬������10λ��8λ���� ��Ϊ10λʱ����ĵ�1,2λ��01 ��Ϊ8λʱ����ĵ�1,2λ��02 author��wt
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
	 * У�������룬������4λ��5λ���� ��Ϊ4λʱ����ĵ�1,2λ��01��03 ��Ϊ5λʱ����ĵ�1,2λ��02 author��wt
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
	 * У�������룬������3,4,5,9λ���� ��Ϊ3λʱ����ĵ�1,2λ��06 ��Ϊ4λʱ����ĵ�1,2λ��01,02,05,07
	 * ��Ϊ5λʱ����ĵ�1,2λ��03 ��Ϊ9λʱ����ĵ�1,2λ��04 author��wt
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
	 * У�������룬������2,3,4,5,6,7,8λ���� ��Ϊ2λʱ����ĵ�1,2λ��05 ��Ϊ3λʱ����ĵ�1,2λ��06
	 * ��Ϊ4λʱ����ĵ�1,2λ��09,10,11,12 ��Ϊ5λʱ����ĵ�1,2λ��07,08 ��Ϊ6λʱ����ĵ�1,2λ��02
	 * ��Ϊ7λʱ����ĵ�1,2λ��01,03 ��Ϊ8λʱ����ĵ�1,2λ��04 author��wt
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
	 * У�������룬������4,5,6,7,8λ���� ��Ϊ4λʱ����ĵ�1,2λ��05,08 ��Ϊ5λʱ����ĵ�1,2λ��03,06,09
	 * ��Ϊ6λʱ����ĵ�1,2λ��07 ��Ϊ7λʱ����ĵ�1,2λ��04 ��Ϊ8λʱ����ĵ�1,2λ��01,02 author��wt
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
	 * У�������룬������3,7,8λ���� ��Ϊ3λʱ����ĵ�1,2λ��05 ��Ϊ7λʱ����ĵ�1,2λ��03,04
	 * ��Ϊ8λʱ����ĵ�1,2λ��01,02 author��wt
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
	 * У�������룬������5,6,7,8,9,10,11λ���� ��Ϊ5λʱ����ĵ�1,2λ��09,10,11 ��Ϊ6λʱ����ĵ�1,2λ��08
	 * ��Ϊ7λʱ����ĵ�1,2λ��03,06 ��Ϊ8λʱ����ĵ�1,2λ��05,07 ��Ϊ9λʱ����ĵ�1,2λ��04
	 * ��Ϊ10λʱ����ĵ�1,2λ��02 ��Ϊ11λʱ����ĵ�1,2λ��01 author��wt
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
	 * У�������룬������5,6λ���� ��Ϊ5λʱ����ĵ�1,2λ��3,4 ��Ϊ6λʱ����ĵ�1,2λ��1,2 author��wt
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
	 * У�������룬������7,9,10λ���� ��Ϊ7λʱ����ĵ�1λ��3 ��Ϊ9λʱ����ĵ�1λ��1 ��Ϊ10λʱ����ĵ�1λ��2 author��wt
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
	 * У�������룬������6,7λ���� ��Ϊ6λʱ����ĵ�1λ��1��2 ��Ϊ7λʱ����ĵ�1λ��3 author��wt
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
	 * У�������룬������6,7λ���� ��Ϊ3λʱ����ĵ�1,2λ��15 ��Ϊ5λʱ����ĵ�1,2λ��09,10,11,13
	 * ��Ϊ6λʱ����ĵ�1,2λ��01,06,12 ��Ϊ7λʱ����ĵ�1,2λ��02,03,04,05,07,08 ��Ϊ8λʱ����ĵ�1,2λ��14
	 * author��wt
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

	// 509���������Ϸ���Ӫҵ�����������岿��
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���4λ
	// Index: ������֤�㷨������λ��
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

	// 966-���ҹ��̽����׼��ϵ����ͳһ���� fdl
	// [A1 A2 A3 B1 C1 D1 E1 F1 G1 H1 J1 K1 L1 M1 N1 P1 Q1 R1]��λ����
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

	// ����Դ������� ��Ԫ�ع��Ҵ��� fdl
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

	// ����Դ������� fdl
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

	// ������������ע��ű��ƹ��� fdl
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

	// Function: ������׼����Ʒ��Ź���
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: (13,-1),��13λ�Ժ���ַ�������������ʽ��֤
	// LenIndex: ���ȱ�Ϊ2
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
			// ���һλΪУ��λ
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

	// Function: ������Ʒ�㲿���߱������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index:4
	// LenIndex: ���ȱ�Ϊ4
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

	// GB/T 23733�й���׼������Ʒ���� fdl
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index:15
	// LenIndex: ���ȱ�Ϊ15
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

	// Function: TCL���ܵ�ر������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index:4
	// LenIndex: ���ȱ�Ϊ4
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

	// Function: TCL���ܵ�ر�����򡪡�����������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index:4
	// LenIndex: ���ȱ�Ϊ4
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
					System.out.println("ƥ��ڶ�������");
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
					System.out.println("ƥ���һ������");
					return OK;
				} else
					return ERR;
			} catch (Exception e) {
			}
		}
		return ERR;
	}

	// Function: 504 ��10���֣��������ͼ����ݴ���
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

	// Function: 516 ��1���֣���ͨΥ����Ϊ�������
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

	// Function:510 �������֤���ϼ�����������豸����
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

	// Function: ���������ʾ�淶У���� ����MOD112
	// ����MOD����ʾ���ຯ����i����ʾ�����ַ���������λ����ţ�Ci����ʾ��iλ���ϵĴ����ַ���ֵ��Wi����ʾ��iλ���ϵļ�Ȩ���ӣ�
	// ��Ȩ���ӵĹ�ʽ�ǣ�2��n-1���ݳ���11ȡ������n�����Ǹ�i��������������
	// ��У���ֵΪ10ʱ ��ֵλX.��У���ֵΪ11ʱ ��ֵλ0
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
			// ISO 7064:1983.MOD 11-2У���㷨���ַ������ٿռ�ʱҪ��һλ��������У��λ
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
			mod = (int) (11 - sum) % 11;
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

	// Function: ���������ʶ�淶����ƥ��,����(603)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����<=2
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

	// Function: ��̥�����켰���㲿���ı�ʶ��������ƥ��,��ĸ(615)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����<=1
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

	// Function: ��̥�����켰���㲿���ı�ʶ��������ƥ��,��ĸ(615)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����<=1
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

	// Function:��Ʒ���� Ӧ�ñ�ʶ����632��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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

	// Function: ʶ�� �����߱�ʶ ��һ��������ƥ��,����(635)
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:����<=12
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
	 * У��ǰ4λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ4λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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
	 * У��ǰ2λ�ǲ�����Щ���� 757 author:wt
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

	// Function:�й�ʯ����Ȼ���ܹ�˾����ҵ��λ���루763��
	// CODEstr: ��ʶ����
	// LenCODE: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
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
	 * ��֤��׼11��ǰ��λ�ǲ��������ݿ���
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
	 * ��֤��׼12��ǰ9λ�ǲ��������ݿ���
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
	
	//�й����������� fdl
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
	
	// Function: ɭ�����ͱ������
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index:5
	// LenIndex: ���ȱ�Ϊ5
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
	

	// Function:654 �̾����������ǩ ��֯��������
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

	// Function:654 �̾����������ǩ ��֯����ʡ����������ֱϽ��
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

	// Function:654 �̾����������ǩ ��֯�����С�����
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

	// Function:658 ��λ��������������� Сд��ĸģʽ
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

	// Function:658 ��λ��������������� ������ĸ���ģʽ
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

	// Function:700 ½��Ұ�������߲�����
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

	// Function:704������Ϣ���������3,4��λ����
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

	// Function:698 ȫ��������ҵҽ����е�������豸����
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

	// Function:728(1)��ҽ��������
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
	// Function:728(2)��ҽ����֢״
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
	// Function:706,708���ʷ���
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
	// Function:710���ʷ���
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
	// Function: 722 ����ó�׼�����λ
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���
	// Index: ��������ĵ�����λ��
	// LenIndex:������
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
