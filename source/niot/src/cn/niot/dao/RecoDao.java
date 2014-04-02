package cn.niot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import cn.niot.util.JdbcUtils;

import cn.niot.util.*;

public class RecoDao {
	private static RecoDao recoDao = new RecoDao();
	//public static Connection connection = null;
	public static RecoDao getRecoDao() {
		return recoDao;
	}
	
	public String getIoTID(String id){		
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_IOTID);
			stmt.setString(1, id);
			results = stmt.executeQuery();

			while (results.next()) {
				String res = results.getString("id") + "  " + results.getString("length") + "  " + results.getString("byte") + "  " + results.getString("function");
				System.out.println(res);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return "ok";
	}
	
	/*
	 * ����Ҳ�Ƿ���ֵ ����hashMapTypeToRules��rmvRuleSet��rmvIDSet��hashMapRuleToTypes
	 */
	public HashMap<String, ArrayList<String>> DBreadTypeAndRules(
			HashMap<String, Double> rmvRuleSet,
			HashMap<String, Double> rmvIDSet,
			HashMap<String, ArrayList<String>> hashMapRuleToTypes) {
		HashMap<String, ArrayList<String>> hashMapTypeToRules = new HashMap<String, ArrayList<String>>();
		
		Connection connection = JdbcUtils.getConnection();
		
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TYPEANDRULES);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				ArrayList<String> rules = new ArrayList<String>();// �����ֵ�е�ArrayList
				String idType = results.getString("id");
				String lengthRule = results.getString("length");
				String byteRule = results.getString("byte");
				String functionRules = results.getString("function");
				double priorProbability = results.getDouble("priorProbability");
				if (lengthRule.length() != 0) {
					lengthRule = "IoTIDLength)(?#PARA=" + lengthRule + "){]";
					rules.add(lengthRule);// eg.length8)(?#PARA=8){]
					// �������ֽ�length8,����8
					rmvRuleSet.put(lengthRule, 0.5);// ��rmvRuleSet���length����
					hashMapTypeToRulesSwitchhashMapRuleToTypes(
							hashMapRuleToTypes, lengthRule, idType);// hashMapTypeToRulesת��ΪhashMapRuleToTypes,����length
				}
				if (byteRule.length() != 0) {
					byteRule = "IoTIDByte)(?#PARA=" + byteRule + "){]";
					rules.add(byteRule);
					rmvRuleSet.put(byteRule, 0.5);// ��rmvRuleSet���byte����
					hashMapTypeToRulesSwitchhashMapRuleToTypes(
							hashMapRuleToTypes, byteRule, idType);// hashMapTypeToRulesת��ΪhashMapRuleToTypes,����byte
				}
				rmvIDSet.put(idType, priorProbability);// ��rmvRuleSet���ID,�������0.5
				ArrayList<String> types = new ArrayList<String>();

				String[] splitFunctionRules = functionRules
						.split("\\(\\?\\#ALGNAME=");
				for (int i = 0; i < splitFunctionRules.length; i++) {
					if (splitFunctionRules[i].length() != 0) {
						rules.add(splitFunctionRules[i]);
						rmvRuleSet.put(splitFunctionRules[i], 0.5);
						hashMapTypeToRulesSwitchhashMapRuleToTypes(
								hashMapRuleToTypes, splitFunctionRules[i],
								idType);
					}
				}
				hashMapTypeToRules.put(idType, rules);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return hashMapTypeToRules;
	}
	
	private void hashMapTypeToRulesSwitchhashMapRuleToTypes(
			HashMap<String, ArrayList<String>> hashMapRuleToTypes, String rule,
			String idType) {
		ArrayList<String> types = new ArrayList<String>();
		if (hashMapRuleToTypes.get(rule) == null) {// hashMapTypeToRulesת��ΪhashMapRuleToTypes,����function
			types.add(idType);
			hashMapRuleToTypes.put(rule, types);
		} else {
			types = hashMapRuleToTypes.get(rule);
			types.add(idType);
			hashMapRuleToTypes.put(rule, types);
		}

	}
	
	///���������(296)
	public boolean getAdminDivisionID(String id){
		Connection	connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_ADMINDIVISION);
			stmt.setString(1, id);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	///�������͵�����ƴ���(279)
	public boolean getCountryRegionCode(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_COUNTRYREGIONCODE);
			int i = 1;
			stmt.setString(i++, code);
			stmt.setString(i++, code);
			stmt.setString(i++, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е��Ʒ������ ����ͱ��� ��3���֣���е�⹺��(7)
	public boolean getTabaccoMachineProduct(String categoryCode, String groupCode, String variatyCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOMACHINEPRODUCT);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			stmt.setString(i++, variatyCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��Ʒ����������Ʒ����EAN UPCǰ3λǰ׺��
	public boolean getPrefixofRetailCommodityNumber(int code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_EANUPC);
			int i = 1;
			stmt.setInt(i++, code);
			stmt.setInt(i++, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е���� ����ͱ����2���֣�ר�ü� ��¼D�еĵ�λ����(672)
	public boolean getTabaccoMachineProducer(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOMACHINEPRODUCER);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//CID����4λ�����������
	public boolean getDistrictNo(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_DISTRICTNO);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е��Ʒ������ ��ҵ��е��׼�� �����е������룬�������Ʒ�ִ��루6��
	public boolean getTabaccoStandardPart(String categoryCode, String groupCode, String variatyCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOSTANDARDPART);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			stmt.setString(i++, variatyCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е��Ʒ�����Ϸ���ͱ��� ��6���֣�ԭ��������(4)
	public boolean getTabaccoMaterial(String categoryCode, String variatyCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOMATERIAL);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, variatyCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� �����Ʒ��������(15)
	public boolean getFoodAccount(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_FOORDACCOUNT);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�豸��������루23��
	public boolean getGrainEquipment(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINEQUIPMENT);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ��ʩ��������루24��
	public boolean getGrainEstablishment(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINESTABLISHMENT);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//�̲ݻ�е��Ʒ������ ����ͱ��� ��5���֣�����Ԫ���� ��5��
	public boolean getTabaccoElectricComponent(String categoryCode, String groupCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOELECTRICCOMPONENT);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//���ȡ��һ��������������
	public String getRandomAdminDivision(){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String code = "";
		try{
			stmt = connection.prepareStatement(RecoUtil.SELECT_RANDOMADMINDIVISION);
			results = stmt.executeQuery();
			while(results.next()){
				code = results.getString("id");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {  
			JdbcUtils.free(null, null, connection);
		}
		return code;
	}
	
	//���ȡ��һ��EANUPC���
	public String getRandomEANUPC(){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String code = "";
		try{
			stmt = connection.prepareStatement(RecoUtil.SELECT_RANDOMEANUPC);
			results = stmt.executeQuery();
			while(results.next()){
				code = String.valueOf(results.getInt("code"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {  
			JdbcUtils.free(null, null, connection);
		}
		return code;
	}
	
	//���ر�׼��ϸ��Ϣ
	public String getIDDetail(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String name = "";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try{
			stmt = connection.prepareStatement(RecoUtil.SELECT_IDDETAIL);
			stmt.setString(1, code);
			results = stmt.executeQuery();
			while(results.next()){
				name = results.getString("name");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {  
			JdbcUtils.free(null, null, connection);
		}
		return name;
	}
	
	//���ò��ϱ��� ��1���֣����ò��Ϸ���������Ʒ����(10)
	public boolean getTobbacoMaterials(String categoryCode, String groupCode){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOMATERIALS);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳó��ҵ��ͳ�Ʒ��������(14)
	public boolean getFoodTrade(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_FOODTRADE);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�ӹ�(18)
	public boolean getFoodEconomy(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_FOODECONOMY);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�ִ�ҵ��ͳ�Ʒ��������(16)
	public boolean getGrainStoreHouse(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINSTOREHOUSE);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� �������溦���������(17)
	public boolean getGrainsDiseases(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINSDISEASES);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�ӹ���1���֣��ӹ���ҵ���������(19)
	public boolean getGrainsProcess(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINSPROCESS);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�ִ���3���֣����ķ��������(20)
	public boolean getGrainsEquipment(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINSEQUIPMENT);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�ִ���2���֣���������������(21)
	public boolean getGrainConditionDetection(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINCONDITIONDETECTION);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�ִ���1���֣��ִ���ҵ���������(22)
	public boolean getgrainsSmartWMS(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINSSMARTWMS);
			int i = 1;
			stmt.setString(i, code + "%");
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(rowcount >= 1){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ�����2���֣�������׼���������(26)
	public boolean getGrainsQualityStandard(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRIANQUALITYSTANDARD);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��������������������(32)
	public boolean getMeasuringInstrument(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MEASURINGINSTRUMENT);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ���� ��1���֣�ָ����������(27)
	public boolean getGrainsIndex(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINSINDEX);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ���ӹ���Ʒ���������(28)
	public boolean getGrainsInformation(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINSINFORMATION);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//��ʳ��Ϣ��������� ��ʳ���Է��������(29)
	public boolean getGrainsAttribute(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_GRAINSATTRIBUTE);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//function: query the table phonenumber and thus check the legality of the prefix of a given phone number (7 numbers)
	//creator: dgq
	public boolean getPrefixPhoneNO(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PHONENUMBER);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//function: query the table vehiclenonormal and thus check the legality of the prefix of a normal vehicle character (2 characters)
	//creator: dgq
	public boolean getPrefixNormalVehicleNO(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_NORMALVEHICLENO);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//function: query the table vehiclenomarmy and thus check the legality of the prefix of a normal vehicle character (2 characters)
	//creator: dgq
	public boolean getPrefixArmyVehicleNO(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_ARMYVEHICLENO);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	//function: query the table vehiclenomwj and thus check the legality of the third character of a WJ vehicle character
	//creator: dgq
	public boolean getPrefixWJVehicleNO(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_WJVEHICLENO);
			int i = 1;
			stmt.setString(i, code);
			
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;				
			}
			if(1 == rowcount){
				ret =  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
}
