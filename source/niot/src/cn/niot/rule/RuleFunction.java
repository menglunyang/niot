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

		String[] byteStrArray = parameter.split(";");
		int[] byteElement = new int[9];
		for (int i = 0; i < byteStrArray.length; i++) {
			String[] byteElementString = byteStrArray[i].split(",");
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
		}
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

	// Function: ���ò��Ϸ���������Ʒ����
	// IDstr: ��ʶ����
	// LenID: ��ʶ����ĳ���4λ
	// Index: ��������ĵ�����λ��
	// LenIndex:����Ϊ4
	// creator: zll
	public static String TabaccoMaterials(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			if (LenIndex != 4) {
				return ERR;
			}
			String categoryCode = String.valueOf(IDstr[Index[0]])
					+ String.valueOf(IDstr[Index[1]]);
			String groupCode = String.valueOf(IDstr[Index[2]])
					+ String.valueOf(IDstr[Index[3]]);
			RecoDao dao = new RecoDao();
			boolean ret = dao.getTobbacoMaterials(categoryCode, groupCode);
			if (ret) {
				return OK;
			} else {
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: ISO 7064:1983.MOD 37 36
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// Creator:�?��
	public static String MOD3736(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
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
			if (mod == (int) IDstr[Index[b]]) {
				return OK;
			} else {

				return ERR;

			}
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 413 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfo(char[] IDstr, int LenID, int[] Index,
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
			} else if (i >= 20 && i <= 23) {
				return OK;
			} else if (i >= 30 && i <= 33 || i == 90) {
				return OK;
			}

			else
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
	// creator: xjf
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
			int i = 100 * index1 + 10 * index2 + index3 + 1000 * index4;
			if (i >= 101 && i <= 104) {
				return OK;
			} else if (i >= 201 && i <= 203) {
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

	// Function: 409 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfotainass(char[] IDstr, int LenID, int[] Index,
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
			if (i >= 20 && i <= 22) {
				return OK;
			} else if (i == 30 || i == 10 || i == 90) {
				return OK;
			}

			else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 406 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfotainrate(char[] IDstr, int LenID, int[] Index,
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
			if (i >= 20 && i <= 21) {
				return OK;
			} else if (i >= 40 && i <= 43) {
				return OK;
			}

			else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}

	// Function: 403 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 2
	// creator: xjf
	public static String FireInfocamp(char[] IDstr, int LenID, int[] Index,
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
			if (i >= 10 && i <= 15) {
				return OK;
			} else if (i >= 20 && i <= 29) {
				return OK;
			} else if (i == 30 || i == 10 || i == 90) {
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
			int i = 1000 * index1 + 100 * index2 + 10 * index3 + index4;
			if (i >= 1110 && i <= 1112) {
				return OK;
			} else if (i >= 1120 && i == 1122) {
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

	// Function: 394 fire information
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// LenIndex: the number of indexes that must be 3
	// creator: xjf
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

	// Function: 205 buyidin
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
						|| IDstr[Index[2]] ==

						'E') {
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
	// Creator:�?��
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
	// Creator:�?�� 504
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
	// Creator:�?�� 504 509
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
	// Creator:�?�� 511
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
	// Creator:�?�� 512
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
	// Creator:�?�� 213
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

	// Function: 01-12 99
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���2
	// Creator:�?�� 213
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
	// Creator:�?�� 213
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
	// Creator:�?�� 214
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
	// Creator:�?�� 225
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
	// Creator:�?�� 225
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
	// Creator:�?�� 225
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
	// Creator:�?�� 225
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
	// Creator:�?�� 225
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
	// Creator:�?�� 225
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
	// Creator:�?�� 225
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

	public static String MOD1110(char[] IDstr, int LenID, int[] Index,
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
	// Creator:�?��
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
	// Creator:�?��
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
	// Creator:�?��
	public static String CommodityCodeCheck632(char[] IDstr, int LenID,
			int[] Index, int LenIndex) {
		try {
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			// MOD 16-3У���㷨���ַ��ٿռ�ʱҪ��һλ��������У��λ
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

	// Function: 00-14
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes �̶���2
	// Creator:�?��639
	public static String ZeroTO14(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		int index1 = (int) IDstr[Index[0]] - 48;
		int index2 = (int) IDstr[Index[1]] - 48;
		int Xx = 14;
		int i = 10 * index1 + index2;
		if (i >= 0 && i <= Xx) {
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
	public static String GainsConditionDetection(char[] IDstr, int LenID, int[] Index,
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
	public static String GrainsQualityStandard(char[] IDstr, int LenID, int[] Index,
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
	public static String MeasuringInstrument(char[] IDstr, int LenID, int[] Index,
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
	public static String GrainsInformation(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
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
			if (code == "10" || code == "11" || code == "19" || code == "30"){
				return OK;
			} else{
				return ERR;
			}
		} catch (Exception e) {
			return ERR;
		}
	}
	
	// Function: check the legality of the first seven numbers of a given mobile phone number 
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// creator: dgq
	public static String MobilePhoneNum(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			
			String code = "";
			int LEN_PREFIX = 7; // relate to the first 7 number of a given phone number
			for (int i = 0; i < LEN_PREFIX; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPrefixPhoneNO(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}
	
	// Function: check the legality of the first two characters of a normal vehicle number 
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// creator: dgq
	public static String VehicleNONormal(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			
			String code = "";
			int LEN_PREFIX = 2; // relate to the first 2 characters of a given normal vehicle number
			for (int i = 0; i < LEN_PREFIX; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPrefixNormalVehicleNO(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}
	
	// Function: check the legality of the first two characters of a army vehicle number 
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// creator: dgq
	public static String VehicleNOArmy(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			
			String code = "";
			int LEN_PREFIX = 2; // relate to the first 2 characters of a army vehicle number
			for (int i = 0; i < LEN_PREFIX; i++) {
				code = code.concat(String.valueOf(IDstr[Index[i]]));
			}
			
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPrefixArmyVehicleNO(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}
	
	// Function: check the legality of the last one character of a army vehicle number 
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// creator: dgq
	public static String VehicleNOArmySuffix(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			
			if (7 == LenID) { // the length of the army vehicle number is 7 
				if (IDstr[6] >= '0' && IDstr[6] <= '9') {
					return OK;
				} 	
			} else if (6 == LenID) {// the length of the army vehicle number is 6
				return OK;
			}
			
			return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}
	// Function: check the legality of the third character of a WJ vehicle number 
	// IDstr: ID string
	// LenID: the number of characters in the ID string
	// Index: the list of corresponding indexes regarding to this algorithm
	// LenIndex: the number of indexes
	// creator: dgq
	public static String VehicleNOWJ(char[] IDstr, int LenID, int[] Index,
			int LenIndex) {
		try {
			
			if (!checkInputParam(IDstr, LenID, Index, LenIndex)) {
				return ERR;
			}
			
			String code = String.valueOf(IDstr[Index[0]]);
						
			RecoDao recoDao = new RecoDao();
			boolean ret = recoDao.getPrefixWJVehicleNO(code);
			if (ret) {
				return OK;
			} else
				return ERR;
		} catch (Exception e) {
			return ERR;
		}
	}
	
}
