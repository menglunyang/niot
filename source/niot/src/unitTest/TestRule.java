package unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.niot.rule.RuleFunction;

public class TestRule {

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	@Test
	public void testTwoByteDecimalnt() {
		int [] index1 = {0,1};
		char [] IDstr1 = {'0','1'};
		assertEquals(RuleFunction.TwoByteDecimalnt(IDstr1, 2, index1, 2),"OK");
		
		int [] index2 = {0,1};
		char [] IDstr2 = {'9','9'};
		assertEquals(RuleFunction.TwoByteDecimalnt(IDstr2, 2, index2, 2),"OK");
		
		int [] index3 = {0,1};
		char [] IDstr3 = {'5','9'};
		assertEquals(RuleFunction.TwoByteDecimalnt(IDstr3, 2, index3, 2),"OK");
		
		int [] index4 = {0,1};
		char [] IDstr4 = {'a','b'};
		assertEquals(RuleFunction.TwoByteDecimalnt(IDstr4, 2, index4, 2),"ERR");
		
	}

	@Test
	public void testCigaSubClassCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testMonthDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testCigaOrgCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testCigaDepCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testFirst4CharsofAdminDivisionforCiga() {
		int [] index2 = {0,1,2,3};
		char [] IDstr2 = {'9','9','9','9','9','9'};
		assertEquals(RuleFunction.First4CharsofAdminDivisionforCiga(IDstr2, 6, index2, 4),"OK");
	}

	@Test
	public void testAdminDivision() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountryRegionCodeforCPC() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountryRegionCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testTabaccoMachineProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrefixofRetailCommodityNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testTabaccoMachineProducer() {
		fail("Not yet implemented");
	}

	@Test
	public void testDistrictNo() {
		fail("Not yet implemented");
	}

	@Test
	public void testCIDRegex() {
		fail("Not yet implemented");
	}

	@Test
	public void testCPCTwoByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testMonth() {
		fail("Not yet implemented");
	}

	@Test
	public void testClassOfGrain1() {
		fail("Not yet implemented");
	}

	@Test
	public void testTwobytleCode07() {
		fail("Not yet implemented");
	}

	@Test
	public void testTwobytleCode06() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountUcode() {
		fail("Not yet implemented");
	}

	@Test
	public void testDomainManagerInEPCCheck() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckCodeForCommodityCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testHouseCode_CheckBasedCompleteTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testHouseCode_CheckBasedCoordinate() {
		fail("Not yet implemented");
	}

	@Test
	public void testHouseCode_CheckBasedFenzong() {
		fail("Not yet implemented");
	}

	@Test
	public void testHouseCode_CheckBasedFenfu() {
		fail("Not yet implemented");
	}

	@Test
	public void testHouseCode_CheckUnitCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testHouseCode_CheckTwelBitCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testHouseCode_CheckCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeviceMOD163() {
		fail("Not yet implemented");
	}

	@Test
	public void testFiveByteDecimalnt() {
		fail("Not yet implemented");
	}

	@Test
	public void testFourByteDecimalnt() {
		fail("Not yet implemented");
	}

	@Test
	public void testInternationalSecurities() {
		fail("Not yet implemented");
	}

	@Test
	public void testMOD112() {
		fail("Not yet implemented");
	}

	@Test
	public void testMOD163() {
		fail("Not yet implemented");
	}

	@Test
	public void testMrpCheck() {
		fail("Not yet implemented");
	}

	@Test
	public void testMusicCheck() {
		fail("Not yet implemented");
	}

	@Test
	public void testThreeByteDecimalnt() {
		fail("Not yet implemented");
	}

	@Test
	public void testTwoByteSRMXSMYZ() {
		fail("Not yet implemented");
	}

}
