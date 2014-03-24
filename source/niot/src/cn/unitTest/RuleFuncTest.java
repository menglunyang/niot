package cn.unitTest;

import static org.junit.Assert.assertEquals;

import javax.swing.JOptionPane;

import cn.niot.rule.RuleFunction;
import cn.niot.service.NewIDstdCollisionDetect;

import cn.niot.service.*;

@SuppressWarnings("unused")
public class RuleFuncTest {
	static int i = 0;

	public static void UnitTestEqual(String str1, String str2) {

		if (str1.equals(str2)) {
			JOptionPane.showMessageDialog(null, "Unit Test Passed!");
			System.out.println("Unit Test Passed!");
		} else {
			JOptionPane.showMessageDialog(null, "Unit Test Failed!");
			System.out.println("Unit Test Failed!");
		}
		return;
	}

	public static void testGenerateRandomChar() {
		for (int i = 0; i < 100; i++) {
			String byterule = "[0,1]";
			char res = NewIDstdCollisionDetect.generateRandomChar(byterule);
			System.out.println(res);
		}

	}

	public static void testFormJsonString() {
		String len = "8";
		String valueRange = "1-1:0-9;4-6:a-z;7-8:A-Z;";
		String resJasonStr = NewIDstdCollisionDetect.formJsonString(len,
				valueRange);
		System.out.println(resJasonStr);
		// UnitTestEqual(resJasonStr, "ERR");
	}

	// 13, LY/T 1627-2005
	public static void testTwoByteDecimalnt() {
		int[] index1 = { 0, 1 };
		char[] IDstr1 = { '0', '1' };
		UnitTestEqual(RuleFunction.TwoByteDecimalnt(IDstr1, 2, index1, 2), "OK");

		int[] index2 = { 0, 1 };
		char[] IDstr2 = { '9', '9' };
		UnitTestEqual(RuleFunction.TwoByteDecimalnt(IDstr2, 2, index2, 2), "OK");

		int[] index3 = { 0, 1 };
		char[] IDstr3 = { '5', '9' };
		UnitTestEqual(RuleFunction.TwoByteDecimalnt(IDstr3, 2, index3, 2), "OK");

		int[] index4 = { 0, 1 };
		char[] IDstr4 = { 'a', 'b' };
		UnitTestEqual(RuleFunction.TwoByteDecimalnt(IDstr4, 2, index4, 2),
				"ERR");

	}

	// 2, YC/T 414-2011_1
	public static void testCigaSubClassCode() {
		int[] index1 = { 0, 1, 2 };
		char[] IDstr1 = { '0', '0', '0' };
		UnitTestEqual(RuleFunction.CigaSubClassCode(IDstr1, 3, index1, 3),
				"ERR");

		int[] index2 = { 0, 1, 2 };
		char[] IDstr2 = { '9', '9', '9' };
		UnitTestEqual(RuleFunction.CigaSubClassCode(IDstr2, 3, index2, 3),
				"ERR");

		int[] index3 = { 0, 1, 2 };
		char[] IDstr3 = { '1', '0', '2' };
		UnitTestEqual(RuleFunction.CigaSubClassCode(IDstr3, 3, index3, 3), "OK");

		int[] index4 = { 0, 1, 2 };
		char[] IDstr4 = { '4', '9', '9' };
		UnitTestEqual(RuleFunction.CigaSubClassCode(IDstr4, 3, index4, 3), "OK");

	}

	// 2, YC/T 414-2011_1
	public static void testMonthDate() {
		int[] index1 = { 0, 1, 2, 3 };
		char[] IDstr1 = { '0', '0', '0', '0' };
		UnitTestEqual(RuleFunction.MonthDate(IDstr1, 4, index1, 4), "ERR");

		int[] index2 = { 0, 1, 2, 3 };
		char[] IDstr2 = { '0', '2', '3', '0' };
		UnitTestEqual(RuleFunction.MonthDate(IDstr2, 4, index2, 4), "ERR");

		int[] index3 = { 0, 1, 2 };
		char[] IDstr3 = { '1', '0', '2', '9' };
		UnitTestEqual(RuleFunction.MonthDate(IDstr3, 4, index3, 3), "ERR");

		int[] index4 = { 0, 1, 2, 3 };
		char[] IDstr4 = { '1', '2', '3', '1' };
		UnitTestEqual(RuleFunction.MonthDate(IDstr4, 4, index4, 4), "OK");
		int[] index5 = { 0, 1, 2, 3 };
		char[] IDstr5 = { '0', '2', '2', '9' };
		UnitTestEqual(RuleFunction.MonthDate(IDstr5, 4, index5, 4), "OK");

	}

	// 3, YC/T 400-2011
	public static void testCigaOrgCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		int[] index1 = { 0, 1 };
		char[] IDstr1 = { '1', '0' };
		UnitTestEqual(RuleFunction.CigaOrgCode(IDstr1, 2, index1, 2), "OK");

		int[] index2 = { 0, 1, };
		char[] IDstr2 = { '9', '9' };
		UnitTestEqual(RuleFunction.CigaOrgCode(IDstr2, 2, index2, 2), "OK");

		int[] index3 = { 0, 1, 2 };
		char[] IDstr3 = { '0', '0', '0' };
		UnitTestEqual(RuleFunction.CigaOrgCode(IDstr3, 3, index3, 3), "ERR");

		int[] index4 = { 0, 1 };
		char[] IDstr4 = { '3', '9' };
		UnitTestEqual(RuleFunction.CigaOrgCode(IDstr4, 2, index4, 2), "ERR");

	}

	// 函数Count与函数CigaOrgCode功能相同，Regex表中并没有用到Count函数
	public static void testCount() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		int[] index1 = { 0, 1 };
		char[] IDstr1 = { '1', '0' };
		UnitTestEqual(RuleFunction.Count(IDstr1, 2, index1, 2), "OK");

		int[] index2 = { 0, 1, };
		char[] IDstr2 = { '9', '9' };
		UnitTestEqual(RuleFunction.Count(IDstr2, 2, index2, 2), "OK");

		int[] index3 = { 0, 1 };
		char[] IDstr3 = { '2', '1' };
		UnitTestEqual(RuleFunction.Count(IDstr3, 2, index3, 2), "OK");

		int[] index4 = { 0, 1 };
		char[] IDstr4 = { '3', '9' };
		UnitTestEqual(RuleFunction.Count(IDstr4, 2, index4, 2), "ERR");

	}

	// 3, YC/T 400-2011
	public static void testCigaDepCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());
		int[] index1 = { 0, 1 };
		char[] IDstr1 = { '0', '0' };
		UnitTestEqual(RuleFunction.CigaDepCode(IDstr1, 2, index1, 2), "OK");

		int[] index2 = { 0, 1, };
		char[] IDstr2 = { '9', '7' };
		UnitTestEqual(RuleFunction.CigaDepCode(IDstr2, 2, index2, 2), "OK");

		int[] index3 = { 0, 1 };
		char[] IDstr3 = { '2', '1' };
		UnitTestEqual(RuleFunction.CigaDepCode(IDstr3, 2, index3, 2), "OK");

		int[] index4 = { 0, 1 };
		char[] IDstr4 = { '9', '9' };
		UnitTestEqual(RuleFunction.CigaDepCode(IDstr4, 2, index4, 2), "ERR");

		int[] index5 = { 0, 1, 2 };
		char[] IDstr5 = { '0', '1', '2' };
		UnitTestEqual(RuleFunction.CigaDepCode(IDstr5, 3, index5, 3), "OK");
	}

	// 3, YC/T 400-2011
	public static void testFirst4CharsofAdminDivisionforCiga() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());
		int[] index1 = { 0, 1, 2, 3 };
		char[] IDstr1 = { '0', '0', '0', '0' };

		UnitTestEqual(RuleFunction.First4CharsofAdminDivisionforCiga(IDstr1, 4,
				index1, 4), "OK");

		int[] index2 = { 0, 1, 2, 3 };
		char[] IDstr2 = { '1', '3', '0', '1' };
		UnitTestEqual(RuleFunction.First4CharsofAdminDivisionforCiga(IDstr2, 4,
				index2, 4), "OK");

		int[] index3 = { 0, 1, 2, 3 };
		char[] IDstr3 = { '1', '0', '0', '1' };
		UnitTestEqual(RuleFunction.First4CharsofAdminDivisionforCiga(IDstr3, 4,
				index3, 4), "ERR");

		int[] index4 = { 0, 1, 2, 3 };
		char[] IDstr4 = { '1', '1', '0', '1' };
		UnitTestEqual(RuleFunction.First4CharsofAdminDivisionforCiga(IDstr4, 4,
				index4, 4), "OK");
	}

	// 46, GB/T 28422-2012_2
	public static void testFirst2CharsofAdminDivision() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());
		int[] index1 = { 0, 1 };
		char[] IDstr1 = { '0', '0' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.First2CharsofAdminDivision(IDstr1, 2,
				index1, 2), "ERR");

		int[] index2 = { 0, 1 };
		char[] IDstr2 = { '1', '3' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.First2CharsofAdminDivision(IDstr2, 2,
				index2, 2), "OK");
		int[] index6 = { 0, 1 };
		char[] IDstr6 = { '1', '0' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.First2CharsofAdminDivision(IDstr6, 2,
				index6, 2), "ERR");
		int[] index7 = { 0, 1 };
		char[] IDstr7 = { '7', '2' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.First2CharsofAdminDivision(IDstr7, 2,
				index7, 2), "ERR");

		int[] index3 = { 0, 1 };
		char[] IDstr3 = { '8', '3' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.First2CharsofAdminDivision(IDstr3, 2,
				index3, 2), "ERR");
		int[] index4 = { 0, 1 };
		char[] IDstr4 = { '9', '9' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.First2CharsofAdminDivision(IDstr4, 2,
				index4, 2), "ERR");
	}

	// 3, YC/T 400-2011
	public static void testAdminDivision() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());
		int[] index1 = { 0, 1, 2, 3, 4, 5 };
		char[] IDstr1 = { '0', '0', '0', '0', '0', '0' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.AdminDivision(IDstr1, 6, index1, 6), "ERR");

		int[] index2 = { 0, 1, 2, 3, 4, 5 };
		char[] IDstr2 = { '1', '1', '0', '0', '0', '0' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.AdminDivision(IDstr2, 6, index2, 6), "ERR");

	}

	// cpc
	public static void testCountryRegionCodeforCPC() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());
		int[] index1 = { 0, 1, 2, 3 };
		char[] IDstr1 = { 'U', 'S', '0', '0' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.CountryRegionCodeforCPC(IDstr1, 4, index1, 4), "OK");

		int[] index2 = { 0, 1, 2, 3 };
		char[] IDstr2 = { 'U', 'S', 'A', '0' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.CountryRegionCodeforCPC(IDstr2, 4, index2, 4), "OK");

		int[] index3 = { 0, 1, 2, 3 };
		char[] IDstr3 = { 'U', 'S', 'A', '1' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.CountryRegionCodeforCPC(IDstr3, 4, index3, 4), "ERR");
		int[] index4 = { 0, 1, 2, 3 };
		char[] IDstr4 = { 'U', 'B', 'A', '0' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.CountryRegionCodeforCPC(IDstr4, 4, index4, 4), "ERR");

	}

	// 55, GB/T 26319-2010_1
	public static void testCountryRegionCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());
		String tr1 = new String("BLZ");
		char[] IDstr1 = new char[tr1.length()];
		for (int i = 0; i < tr1.length(); i++) {
			IDstr1[i] = tr1.charAt(i);
		}
		int[] index1 = { 0, 1, 2 };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CountryRegionCode(IDstr1, 3, index1, 3),
				"OK");

		String tr2 = new String("US");
		char[] IDstr2 = new char[tr2.length()];
		for (int i = 0; i < tr2.length(); i++) {
			IDstr2[i] = tr2.charAt(i);
		}
		int[] index2 = { 0, 1 };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CountryRegionCode(IDstr2, 2, index2, 2),
				"ERR");

		String tr3 = new String("US0");
		char[] IDstr3 = new char[tr3.length()];
		for (int i = 0; i < tr3.length(); i++) {
			IDstr3[i] = tr3.charAt(i);
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CountryRegionCode(IDstr3, 3, index1, 3),
				"ERR");

	}

	// 7, YC/T 213.3-2006
	public static void testTabaccoMachineProduct() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 5;

		int[] index1 = new int[j];
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		int[] index2 = { 0, 1, 2, 3 };

		// 定义IDstr
		String tr1 = new String("A0101");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("A0127");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("0101");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j - 1; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoMachineProduct(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoMachineProduct(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoMachineProduct(IDstr3, 4, index2, 4),
				"ERR");

	}

	// 9, YC/T 209.2-2008
	public static void testPrefixofRetailCommodityNumber() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 3; // 定义长度
		int[] index1 = new int[j];
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		String tr1 = new String("859");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("485");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("892");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j - 1; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.PrefixofRetailCommodityNumber(IDstr1, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.PrefixofRetailCommodityNumber(IDstr2, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.PrefixofRetailCommodityNumber(IDstr3, j,
				index1, j), "ERR");

	}

	// 4, YC/T 213.6-2006
	public static void testTabaccoMachineProducer() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 2; // 定义长度
		int[] index1 = new int[j];
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("DF");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("AB");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("WH");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j - 1; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(
				RuleFunction.TabaccoMachineProducer(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(
				RuleFunction.TabaccoMachineProducer(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(
				RuleFunction.TabaccoMachineProducer(IDstr3, j, index1, j), "OK");

	}

	// CID
	public static void testDistrictNo() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 4; // 定义长度
		int[] index1 = new int[j];
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("0010");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0011");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.DistrictNo(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.DistrictNo(IDstr2, j, index1, j), "ERR");

	}

	// CID
	public static void testCIDRegex() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 39; // 定义长度
		int[] index1 = new int[21];
		;
		for (int i = 0; i < 21; i++) {
			index1[i] = i + 18;
		}

		// 定义IDstr
		String tr1 = new String("123123123123123123.kkkk.kkkk.cid.iot.cn");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}
		System.out.print(IDstr1[18]);
		;
		String tr2 = new String("123123123123123123.k-0k.kkkk.cid.iot.cn");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("123123123123123123.k-0k.kkkk.cid.iot.vn");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CIDRegex(IDstr1, j, index1, 21), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CIDRegex(IDstr2, j, index1, 21), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CIDRegex(IDstr3, j, index1, 21), "ERR");

	}

	// 6, YC/T 213.4-2006
	public static void testTabaccoStandardPart() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 5; // 定义长度
		int[] index1 = new int[j];
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("00201");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("00101");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoStandardPart(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoStandardPart(IDstr2, j, index1, j),
				"ERR");

	}

	// 4, YC/T 213.6-2006
	public static void testTabaccoMaterial() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 5; // 定义长度
		int[] index1 = new int[j];
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("01001");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("99999");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoMaterial(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoMaterial(IDstr2, j, index1, j), "ERR");

	}

	// 55, GB/T 26319-2010_1
	public static void testIntFreitForwarding() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 23; // 定义长度
		int[] index1 = new int[5];
		;
		for (int i = 0; i < 5; i++) {
			index1[i] = i + 18;
		}

		// [a-zA-Z][a-zA-Z0-9]{0,15}"
		// 定义IDstr
		String tr1 = new String("123123123123123123.kkkk");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}
		System.out.print(IDstr1[18]);
		;
		String tr2 = new String("123123123123123123.k-00");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("123123123123123123.5555");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.IntFreitForwarding(IDstr1, j, index1, 5),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.IntFreitForwarding(IDstr2, j, index1, 5),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.IntFreitForwarding(IDstr3, j, index1, 5),
				"ERR");

	}

	// 15, LS/T 1711-2004
	public static void testFoodAccount() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 6; // 定义长度
		int[] index1 = new int[j];
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("100901");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("111111");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FoodAccount(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FoodAccount(IDstr2, j, index1, j), "ERR");

	}

	// 23, LS/T 1706-2004
	public static void testGrainEquipment() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 8; // 定义长度
		int[] index1 = new int[j];
		;
		int[] index2 = { 0, 1, 2, 3, 4, 5 };
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("11010102");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("106706");
		char[] IDstr2 = new char[6];
		for (int i = 0; i < 6; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.GrainEquipment(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.GrainEquipment(IDstr2, 6, index2, 6), "ERR");

	}

	// 24, LS/T 1705-2004
	public static void testGrainEstablishment() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 7; // 定义长度
		int[] index1 = new int[j];
		;
		int[] index2 = { 0, 1, 2, 3, 4, 5 };
		;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("11010102");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("106706");
		char[] IDstr2 = new char[6];
		for (int i = 0; i < 6; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.GrainEstablishment(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.GrainEstablishment(IDstr2, 6, index2, 6),
				"ERR");

	}

	// 5, YC/T 213.5-2006
	public static void testTabaccoElectricComponent() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 5; // 定义长度
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("01010");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("01011");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoElectricComponent(IDstr1, j, index1,
				j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoElectricComponent(IDstr2, j, index1,
				j), "ERR");

	}

	// cpc
	public static void testCPCTwoByte() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 2; // 定义长度
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("20");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("14");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CPCTwoByte(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CPCTwoByte(IDstr2, j, index1, j), "ERR");

	}

	// 55,GB/T 26319-2010_1
	public static void testMonth() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 2; // 定义长度
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("12");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("14");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("05");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Month(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Month(IDstr2, j, index1, j), "ERR");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Month(IDstr3, j, index1, j), "OK");

	}

	// 25,LS/T 1704.3-2004
	public static void testClassOfGrain() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 6; // 定义长度
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("030100");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("030109");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ClassOfGrain(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ClassOfGrain(IDstr2, j, index1, j), "ERR");

	}

	// 43, GB/T 28923.1-2012_2
	public static void testTwobytleCode07() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 2; // 定义长度
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("07");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("99");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("10");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.TwobytleCode07(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TwobytleCode07(IDstr2, j, index1, j), "OK");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.TwobytleCode07(IDstr3, j, index1, j), "ERR");
	}

	// Ucode
	public static void testCountUcode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 4; // 定义长度
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("E000");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("ssFF");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("10ss");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CountUcode(IDstr1, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CountUcode(IDstr2, j, index1, j), "OK");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CountUcode(IDstr3, j, index1, j), "OK");
	}

	// 未在Redex中找到DomainManagerInEPCCheck函数
	public static void testDomainManagerInEPCCheck() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 4; // 定义长度
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("E000");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("ssFF");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("10ss");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.DomainManagerInEPCCheck(IDstr1, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.DomainManagerInEPCCheck(IDstr2, j, index1, j), "OK");
		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.DomainManagerInEPCCheck(IDstr3, j, index1, j), "OK");
	}

	// 58, GB/T 23833-2009_1-4
	public static void testCheckCodeForCommodityCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=4;//定义长度

		// 定义IDstr
		String tr1 = new String("06901234567892");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0AS901234567892");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("0690123456789X");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CheckCodeForCommodityCode(IDstr1, j, index1,
				j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CheckCodeForCommodityCode(IDstr2, j, index1,
				j), "ERR");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CheckCodeForCommodityCode(IDstr3, j, index1,
				j), "ERR");
	}

	// 未在Redex中找到HouseCode_CheckBasedCompleteTime函数
	public static void testHouseCode_CheckBasedCompleteTime() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 12; // 定义长度

		// 定义IDstr
		String tr1 = new String("200807080101");
		// int j=tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("201013010101");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("2010**070101");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}
		String tr4 = new String("2010*1070101");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}
		String tr5 = new String("2010**000000");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedCompleteTime(IDstr1, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedCompleteTime(IDstr2, j,
				index1, j), "ERR");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedCompleteTime(IDstr3, j,
				index1, j), "OK");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedCompleteTime(IDstr4, j,
				index1, j), "ERR");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedCompleteTime(IDstr5, j,
				index1, j), "ERR");
	}

	// 未在Redex中找到HouseCode_CheckBasedCoordinate函数
	public static void testHouseCode_CheckBasedCoordinate() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 12; // 定义长度

		// 定义IDstr
		String tr1 = new String("010101020202");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0AS000020202");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedCoordinate(IDstr1, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedCoordinate(IDstr2, j,
				index1, j), "ERR");

	}

	// 未在Redex中找到HouseCode_CheckBasedFenzong函数
	public static void testHouseCode_CheckBasedFenzong() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 12; // 定义长度

		// 定义IDstr
		String tr1 = new String("010101020202");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0AS000020202");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("000000020202");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedFenzong(IDstr1, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedFenzong(IDstr2, j,
				index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedFenzong(IDstr3, j,
				index1, j), "ERR");

	}

	// 未在Redex中找到HouseCode_CheckBasedFenfu函数
	public static void testHouseCode_CheckBasedFenfu() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 12; // 定义长度

		// 定义IDstr
		String tr1 = new String("010101020202");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0AS000020202");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("000000000000");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedFenfu(IDstr1, j, index1,
				j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedFenfu(IDstr2, j, index1,
				j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckBasedFenfu(IDstr3, j, index1,
				j), "ERR");

	}

	// 33, JGJ/T 246-2012
	public static void testHouseCode_CheckUnitCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 4; // 定义长度

		// 定义IDstr
		String tr1 = new String("0000");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("5858");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("9999");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.HouseCode_CheckUnitCode(IDstr1, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.HouseCode_CheckUnitCode(IDstr2, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.HouseCode_CheckUnitCode(IDstr3, j, index1, j), "OK");

	}

	// 33, JGJ/T 246-2012
	public static void testHouseCode_CheckTwelBitCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 12; // 定义长度

		// 定义IDstr
		String tr1 = new String("2010**898900");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("2010*1898900");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("000000000000");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckTwelBitCode(IDstr1, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckTwelBitCode(IDstr2, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckTwelBitCode(IDstr3, j,
				index1, j), "OK");

	}

	// 217, WS 364.2-2011 公民身份证号
	public static void testMOD112() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("130322199004062618");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("140121199009309012");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD112(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD112(IDstr2, j, index1, j), "OK");

	}

	// 未在Redex中找到TwoByteSRMXSMYZ函数
	public static void testTwoByteSRMXSMYZ() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("SR");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("SS");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.TwoByteSRMXSMYZ(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TwoByteSRMXSMYZ(IDstr2, j, index1, j), "ERR");

	}

	// 355, GB/T 13774-92
	public static void testWeaves355() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("11010100");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("11010101");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Weaves355(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Weaves355(IDstr2, j, index1, j), "ERR");

	}

	// 185, CY/T 58.2-2009
	public static void testThreeByteDecimalnt() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("000");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("999");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("001");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}
		String tr4 = new String("555");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt(IDstr1, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt(IDstr2, j, index1, j),
				"OK");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt(IDstr3, j, index1, j),
				"OK");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt(IDstr4, j, index1, j),
				"OK");

	}

	// 60, GB/T 23733-2009
	public static void testMusicCheck() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("T0345246801");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("T0345246802");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.MusicCheck(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MusicCheck(IDstr2, j, index1, j), "ERR");

	}

	// 61, GB/T 23732-2009
	public static void testMOD163() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("0A920021223F3320");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0A920021223F3321");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD163(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD163(IDstr2, j, index1, j), "ERR");

	}

	// 71, GB/T 21076-2007
	public static void testInternationalSecurities() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("US3838831051");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("US3838831052");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("JP3788600009");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.InternationalSecurities(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.InternationalSecurities(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction
				.InternationalSecurities(IDstr3, j, index1, j), "OK");
	}

	// 192, YC/T 393-2011
	public static void testFiveByteDecimalnt() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("00001");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("00000");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("55555");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FiveByteDecimalnt(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FiveByteDecimalnt(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FiveByteDecimalnt(IDstr3, j, index1, j),
				"OK");
	}

	// 232, SB/T 10639-2011
	public static void testEgg232() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("111");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("118");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("932");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Egg232(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Egg232(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Egg232(IDstr3, j, index1, j), "OK");
	}

	// 163, GB 18240.6-2004分类与编码通用术语105——机器编码的结构和表示形式
	public static void testDeviceMOD163() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("789021234561");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("789021234562");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.DeviceMOD163(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.DeviceMOD163(IDstr2, j, index1, j), "ERR");

	}

	// 185, CY/T 58.2-2009
	public static void testMrpCheck() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("1160634520086296");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("1160634520086295");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.MrpCheck(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MrpCheck(IDstr2, j, index1, j), "ERR");

	}

	// 58, GB/T 23833-2009_1-4
	public static void testGraiSerialNo() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		// int j=12;//定义长度

		// 定义IDstr
		String tr1 = new String("0123");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0123");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		int[] index1 = { 3, 1, -1 };

		int[] index2 = { 1, 2, -1 };

		System.out.print(i++);
		UnitTestEqual(RuleFunction.GraiSerialNo(IDstr1, j, index1, 3), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.GraiSerialNo(IDstr2, j, index2, 3), "ERR");

	}

	// 33, JGJ/T 246-2012
	public static void testHouseCode_CheckCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 26;// 定义长度
		// 定义IDstr
		String tr1 = new String("1101010102006005000109*001");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.HouseCode_CheckCode(IDstr1, j, index1, j),
				"OK");
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////

	// 266, GB/T 4761-2008
	// public static void testFamilyRelationship() {
	// JOptionPane.showMessageDialog(null, Thread.currentThread()
	// .getStackTrace()[1].getMethodName());
	// System.out.println(Thread.currentThread().getStackTrace()[1]
	// .getMethodName());
	//
	// String tr1 = new String("8");
	// int j = tr1.length();
	// char[] IDstr1 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr1[i] = tr1.charAt(i);
	// }
	//
	// String tr2 = new String("98");
	// int j1 = tr2.length();
	// char[] IDstr2 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr2[i] = tr2.charAt(i);
	// }
	//
	// String tr3 = new String("108");
	// int j2 = tr3.length();
	// char[] IDstr3 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr3[i] = tr3.charAt(i);
	// }
	//	
	// String tr4 = new String("1200");
	// int j3 = tr4.length();
	// char[] IDstr4 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr4[i] = tr4.charAt(i);
	// }
	//
	// String tr5 = new String("9712");
	// int j4 = tr5.length();
	// char[] IDstr5 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr5[i] = tr5.charAt(i);
	// }
	//	
	// int[] index1 = new int[j];
	//
	// for (int i = 0; i < j; i++) {
	// index1[i] = i;
	// }
	//	
	// int[] index2 = new int[j1];
	//
	// for (int i = 0; i < j1; i++) {
	// index2[i] = i;
	// }
	// int[] index3 = new int[j2];
	//
	// for (int i = 0; i < j2; i++) {
	// index3[i] = i;
	// }
	// int[] index4 = new int[j3];
	//
	// for (int i = 0; i < j3; i++) {
	// index4[i] = i;
	// }
	// int[] index5 = new int[j4];
	//
	// for (int i = 0; i < j4; i++) {
	// index5[i] = i;
	// }
	//
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.FamilyRelationship(IDstr1, j, index1, j),
	// "OK");
	//
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.FamilyRelationship(IDstr2, j1, index2, j1),
	// "ERR");
	//
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.FamilyRelationship(IDstr3, j2, index3, j2),
	// "OK");
	//	
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.FamilyRelationship(IDstr4, j3, index4, j3),
	// "OK");
	//	
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.FamilyRelationship(IDstr5, j4, index5, j4),
	// "OK");
	// }

	// 321, GB/T 17297-1998
	public static void testClimatic() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("11A");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("12E");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("42B");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("44C");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("45B");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Climatic(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Climatic(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Climatic(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Climatic(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Climatic(IDstr5, j, index1, j), "OK");
	}

	// 70, GB/T 21379-2008 路段交通管理属性分类与编码
	public static void testCodeHighway() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("90");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("09");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("12");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("24");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("33");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeHighway(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeHighway(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeHighway(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeHighway(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeHighway(IDstr5, j, index1, j), "OK");
	}

	// 206, YC/T 210.3-2006
	public static void testCodeTobacco() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("110");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("099");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("315");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("410");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("505");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeTobacco(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeTobacco(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeTobacco(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeTobacco(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CodeTobacco(IDstr5, j, index1, j), "OK");
	}

	// 368, GB 918.2-1989
	public static void testNonMotorVehicle() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("800");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("702");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("723");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("700");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("710");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.NonMotorVehicle(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.NonMotorVehicle(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.NonMotorVehicle(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.NonMotorVehicle(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.NonMotorVehicle(IDstr5, j, index1, j), "OK");
	}

	// 365, GB/T 12402——2000
	public static void testEconomicCate() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("900");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("180");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("159");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("250");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("390");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.EconomicCate(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.EconomicCate(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.EconomicCate(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.EconomicCate(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.EconomicCate(IDstr5, j, index1, j), "OK");
	}

	// 281, GB/T 25071-2010
	public static void testJadejewelryMaterialclassif() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("010115");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("080000");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("080001");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("050699");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("060200");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.JadejewelryMaterialclassif(IDstr1, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.JadejewelryMaterialclassif(IDstr2, j,
				index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.JadejewelryMaterialclassif(IDstr3, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.JadejewelryMaterialclassif(IDstr4, j,
				index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.JadejewelryMaterialclassif(IDstr5, j,
				index1, j), "OK");
	}

	// 276, GB/T 27610-2011
	public static void testWasteproducts() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("1390");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("4800");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("4799");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("8110");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("8111");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Wasteproducts(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Wasteproducts(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Wasteproducts(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Wasteproducts(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Wasteproducts(IDstr5, j, index1, j), "OK");
	}

	// 275, GB/T 27923-2011
	public static void testLogisticsoperation() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("1390");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("4800");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("4799");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("8110");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("8111");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Logisticsoperation(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Logisticsoperation(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Logisticsoperation(IDstr3, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Logisticsoperation(IDstr4, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Logisticsoperation(IDstr5, j, index1, j),
				"OK");
	}

	// 270, GB/T 28921-2012
	public static void testNaturaldisaster() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("011100");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("039900");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("049999");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("030000");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("040600");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Naturaldisaster(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Naturaldisaster(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Naturaldisaster(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Naturaldisaster(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Naturaldisaster(IDstr5, j, index1, j), "OK");
	}

	// 268, GB/T 4657-2008
	public static void testTheCenteralPartyCommitte() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("100");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("103");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("245");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("798");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("790");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.TheCenteralPartyCommitte(IDstr1, j, index1,
				j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TheCenteralPartyCommitte(IDstr2, j, index1,
				j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TheCenteralPartyCommitte(IDstr3, j, index1,
				j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TheCenteralPartyCommitte(IDstr4, j, index1,
				j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TheCenteralPartyCommitte(IDstr5, j, index1,
				j), "OK");
	}

	// 物流编码
	public static void testLogisticsCheck() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("000000");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("000001");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("011113");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("012348");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("112348");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.LogisticsCheck(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.LogisticsCheck(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.LogisticsCheck(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.LogisticsCheck(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.LogisticsCheck(IDstr5, j, index1, j), "OK");
	}

	// GB/T 23730.1-2009中国标准视听作品号 第1部分:视听作品标识符
	// public static void testMod36_37() {
	// JOptionPane.showMessageDialog(null, Thread.currentThread()
	// .getStackTrace()[1].getMethodName());
	// System.out.println(Thread.currentThread().getStackTrace()[1]
	// .getMethodName());
	//
	// String tr1 = new String("102");
	// int j = tr1.length();
	// char[] IDstr1 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr1[i] = tr1.charAt(i);
	// }
	//
	// String tr2 = new String("199");
	// char[] IDstr2 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr2[i] = tr2.charAt(i);
	// }
	//
	// String tr3 = new String("252");
	// char[] IDstr3 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr3[i] = tr3.charAt(i);
	// }
	//	
	// String tr4 = new String("435");
	// char[] IDstr4 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr4[i] = tr4.charAt(i);
	// }
	//
	// String tr5 = new String("520");
	// char[] IDstr5 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr5[i] = tr5.charAt(i);
	// }
	//	
	// int[] index1 = new int[j];
	//
	// for (int i = 0; i < j; i++) {
	// index1[i] = i;
	// }
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.Mod36_37(IDstr1, j, index1, j),
	// "OK");
	//
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.Mod36_37(IDstr2, j, index1, j),
	// "ERR");
	//
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.Mod36_37(IDstr3, j, index1, j),
	// "OK");
	//	
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.Mod36_37(IDstr4, j, index1, j),
	// "OK");
	//	
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.Mod36_37(IDstr5, j, index1, j),
	// "OK");
	// }

	// 362, GB/T 12407-2008
	public static void testPositionClass() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("102");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("199");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("252");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("435");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("520");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.PositionClass(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.PositionClass(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.PositionClass(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.PositionClass(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.PositionClass(IDstr5, j, index1, j), "OK");
	}

	// 381, GA/T 974.8-2011
	public static void testFireInfoStstion() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("09");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("11");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("60");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("65");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("90");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoStstion(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoStstion(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoStstion(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoStstion(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoStstion(IDstr5, j, index1, j), "OK");
	}

	// 384, GA/T 974.71-2011
	public static void testFireInfoBuild() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("09");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("13");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("18");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("30");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("35");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoBuild(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoBuild(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoBuild(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoBuild(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoBuild(IDstr5, j, index1, j), "OK");
	}

	// 194, YC/T 391-2011
	public static void testTobaccomachinery() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("401");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("475");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("511");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("620");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("708");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Tobaccomachinery(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Tobaccomachinery(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Tobaccomachinery(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Tobaccomachinery(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Tobaccomachinery(IDstr5, j, index1, j), "OK");
	}

	// 90, GB/T 16828-2007商品条码——参与方位编码与条码表示
	public static void testCheckCodebarcode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("6901234567892");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("6901234567894");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("0123456789463");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("8520369741052");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("6548523697412");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CheckCodebarcode(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CheckCodebarcode(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CheckCodebarcode(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CheckCodebarcode(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CheckCodebarcode(IDstr5, j, index1, j), "OK");
	}

	// 394, GA/T 974.61-2011
	public static void testExtinguishFire() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("109");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("122");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("413");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("314");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("429");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.ExtinguishFire(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ExtinguishFire(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ExtinguishFire(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ExtinguishFire(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ExtinguishFire(IDstr5, j, index1, j), "OK");
	}

	// 393, GA/T 974.62-2011
	public static void testFireInfoSmoke() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("10");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("12");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("19");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("22");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("90");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoSmoke(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoSmoke(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoSmoke(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoSmoke(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfoSmoke(IDstr5, j, index1, j), "OK");
	}

	// 
	public static void testCoalInterment() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("000000000000");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("022401650000");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("503949013032");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("053510012902");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("501010240231");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CoalInterment(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CoalInterment(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CoalInterment(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CoalInterment(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.CoalInterment(IDstr5, j, index1, j), "OK");
	}

	// 398, GA/T 974.58-2011
	public static void testFireInfowatersource() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("1119");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("1122");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("1110");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("1900");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("9000");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersource(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersource(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersource(IDstr3, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersource(IDstr4, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersource(IDstr5, j, index1, j),
				"OK");
	}

	// 399, GA/T 974.57-2011
	public static void testFireInfowatersupply() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("19");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("22");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("32");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("40");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("10");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersupply(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersupply(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersupply(IDstr3, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersupply(IDstr4, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfowatersupply(IDstr5, j, index1, j),
				"OK");
	}

	// 402, GA/T 974.54-2011
	public static void testFireInfofailities() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("19");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("22");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("25");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("29");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("10");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfofailities(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfofailities(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfofailities(IDstr3, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfofailities(IDstr4, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfofailities(IDstr5, j, index1, j),
				"OK");
	}

	// 194
	public static void testtabaccoC() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("201");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("205");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("800");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("501");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("605");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.tabaccoC(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.tabaccoC(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.tabaccoC(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.tabaccoC(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.tabaccoC(IDstr5, j, index1, j), "OK");
	}

	// 80, GB/T 19632-2005 全国殡葬服务、设施和用品代码 有问题
	public static void testMOD9710() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("311001110010062");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("311001101030090");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("311001313010013");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("311001213030076");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("311001101030093");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD9710(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD9710(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD9710(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD9710(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MOD9710(IDstr5, j, index1, j), "OK");
	}

	// 城市市政综合监管信息系统 单元网格划分与编码规则
	public static void testThreeByteDecimalnt1() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("000");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("300");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("999");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("45a");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("256");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt1(IDstr1, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt1(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt1(IDstr3, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt1(IDstr4, j, index1, j),
				"OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.ThreeByteDecimalnt1(IDstr5, j, index1, j),
				"OK");
	}

	// 395, GA/T 974.60-2011
	public static void testFireInfomation() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("0332");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0801");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("0215");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("0111");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("0313");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfomation(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfomation(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfomation(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfomation(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfomation(IDstr5, j, index1, j), "OK");
	}

	// 194 有问题
	public static void testTobaccoTech() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("44");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("45");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("49");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("50");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("00");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.TobaccoTech(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TobaccoTech(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TobaccoTech(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TobaccoTech(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TobaccoTech(IDstr5, j, index1, j), "OK");
	}

	// 80, GB/T 19632-2005 全国殡葬服务、设施和用品代码
	public static void testFuneralInterment() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("1170000");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("1170501");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("2040000");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("3029900");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("3990000");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FuneralInterment(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FuneralInterment(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FuneralInterment(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FuneralInterment(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FuneralInterment(IDstr5, j, index1, j), "OK");
	}

	// 403, GA/T 974.53-2011
	public static void testFireInfocamp() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("10");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("13");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("19");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		String tr4 = new String("29");
		char[] IDstr4 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr4[i] = tr4.charAt(i);
		}

		String tr5 = new String("90");
		char[] IDstr5 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr5[i] = tr5.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfocamp(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfocamp(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfocamp(IDstr3, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfocamp(IDstr4, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfocamp(IDstr5, j, index1, j), "OK");
	}

	// 188
	public static void testLittlecode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("02");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("21");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("06");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.Littlecode(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Littlecode(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.Littlecode(IDstr3, j, index1, j), "OK");
	}

	// 406, GA/T 974.50-2011
	public static void testFireInfotainrate() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("20");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("22");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("44");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfotainrate(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfotainrate(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfotainrate(IDstr3, j, index1, j), "OK");
	}

	// 409, GA/T 974.48-2011
	public static void testFireInfotainass() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("20");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("19");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("23");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfotainass(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfotainass(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfotainass(IDstr3, j, index1, j), "OK");
	}

	// 413, GA/T 974.44-2011
	public static void testFireInfo() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("14");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("19");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("90");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfo(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfo(IDstr2, j, index1, j), "ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FireInfo(IDstr3, j, index1, j), "OK");
	}

	// 10, YC/T 209.1-2006
	public static void testTabaccoMaterials() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("0408");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0200");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("9999");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoMaterials(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoMaterials(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TabaccoMaterials(IDstr3, j, index1, j), "OK");
	}

	// /////////////////////////////////////////////////////////////////////////

	// MedSupervise
	public static void testMedAppCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("89123456789078500694");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("89123456789078520694");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("88123456789078523094");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = { 15 };

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MedAppCode(IDstr1, j, index1, 1), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MedAppCode(IDstr2, j, index1, 1), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.MedAppCode(IDstr3, j, index1, 1), "ERR");
	}

	// 9, YC/T 209.2-2008
	// public static void testN14() {
	// JOptionPane.showMessageDialog(null, Thread.currentThread()
	// .getStackTrace()[1].getMethodName());
	// System.out.println(Thread.currentThread().getStackTrace()[1]
	// .getMethodName());
	//
	// String tr1 = new String("G69012345678971022140000000012");
	// int j = tr1.length();
	// char[] IDstr1 = new char[j];
	// for (int i = 0; i < j; i++) {
	// IDstr1[i] = tr1.charAt(i);
	// }
	//
	// int[] index1 = new int[j];
	// for(int i = 0; i < j; i++)
	// index1[i] = i;
	//
	// System.out.print(i++);
	// UnitTestEqual(RuleFunction.N14(IDstr1, j, index1, j), "ERR");
	//
	// }

	// 43, GB/T 28923.1-2012_1
	public static void testTwobytleCode06() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		int j = 2; // 定义长度
		int[] index1 = new int[j];
		;
		// int [] index2 = {0,1,2,3,4,5};;
		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}

		// 定义IDstr
		String tr1 = new String("06");
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("99");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("00");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.TwobytleCode07(IDstr1, j, index1, j), "OK");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.TwobytleCode07(IDstr2, j, index1, j), "OK");
		System.out.print(i++);
		UnitTestEqual(RuleFunction.TwobytleCode07(IDstr3, j, index1, j), "OK");
	}

	// 466, GA/T 556.5-2007
	public static void testFourByteDecimalnt() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());

		String tr1 = new String("1111");
		int j = tr1.length();
		char[] IDstr1 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr1[i] = tr1.charAt(i);
		}

		String tr2 = new String("0000");
		char[] IDstr2 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr2[i] = tr2.charAt(i);
		}

		String tr3 = new String("9999");
		char[] IDstr3 = new char[j];
		for (int i = 0; i < j; i++) {
			IDstr3[i] = tr3.charAt(i);
		}

		int[] index1 = new int[j];

		for (int i = 0; i < j; i++) {
			index1[i] = i;
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.FourByteDecimalnt(IDstr1, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FourByteDecimalnt(IDstr2, j, index1, j),
				"ERR");

		System.out.print(i++);
		UnitTestEqual(RuleFunction.FourByteDecimalnt(IDstr3, j, index1, j),
				"OK");
	}
}
