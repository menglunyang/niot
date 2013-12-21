package cn.unitTest;

import static org.junit.Assert.assertEquals;

import javax.swing.JOptionPane;

import cn.niot.rule.RuleFunction;
import cn.niot.service.NewIDstdCollisionDetect;

import cn.niot.service.*;
public class RuleFuncTest {
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
	
	public static void testGenerateRandomChar( ){
		for (int i = 0; i < 100; i++){
			String byterule = "[0,1]";
			char res = NewIDstdCollisionDetect.generateRandomChar(byterule);
			System.out.println(res);
		}
		
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
		UnitTestEqual(RuleFunction.TwoByteDecimalnt(IDstr4, 2, index4, 2), "ERR");
	}

}
