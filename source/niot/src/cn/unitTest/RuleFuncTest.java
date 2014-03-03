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
		char[] IDstr4 = { '4', '1', '1' };
		UnitTestEqual(RuleFunction.CigaSubClassCode(IDstr4, 3, index4, 3), "OK");

	}

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
	}

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

	}

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
		char[] IDstr2 = { '1', '3', '0', '3', '0', '0' };
		System.out.print(i++);
		UnitTestEqual(RuleFunction.AdminDivision(IDstr2, 6, index2, 6), "OK");

	}

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

	// CountryRegionCode
	public static void testCountryRegionCode() {
		JOptionPane.showMessageDialog(null, Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1]
				.getMethodName());
		String tr1 = new String("USA");
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
				"OK");

		String tr3 = new String("US0");
		char[] IDstr3 = new char[tr3.length()];
		for (int i = 0; i < tr3.length(); i++) {
			IDstr3[i] = tr3.charAt(i);
		}
		System.out.print(i++);
		UnitTestEqual(RuleFunction.CountryRegionCode(IDstr3, 3, index1, 3),
				"ERR");

	}

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

		String tr2 = new String("Z0101");
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

		// int [] index2 = {0,1,2,3};
		// 定义IDstr
		String tr1 = new String("000");
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
}
