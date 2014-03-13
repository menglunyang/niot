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

	// public static Connection connection = null;
	public static RecoDao getRecoDao() {
		return recoDao;
	}

	public String getIoTID(String id) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_IOTID);
			stmt.setString(1, id);
			results = stmt.executeQuery();

			while (results.next()) {
				String res = results.getString("id") + "  "
						+ results.getString("length") + "  "
						+ results.getString("byte") + "  "
						+ results.getString("function");
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
					String[] byteStrArray = byteRule.split(";");
					for (int i = 0; i < byteStrArray.length; i++) {
						byteStrArray[i] = "IoTIDByte)(?#PARA="
								+ byteStrArray[i] + "){]";
						rules.add(byteStrArray[i]);
						rmvRuleSet.put(byteStrArray[i], 0.5);// ��rmvRuleSet���byte����
						hashMapTypeToRulesSwitchhashMapRuleToTypes(
								hashMapRuleToTypes, byteStrArray[i], idType);// hashMapTypeToRulesת��ΪhashMapRuleToTypes,����byte
					}
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

	// /���������(296)
	public boolean getAdminDivisionID(String id) {
		Connection connection = JdbcUtils.getConnection();
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// /�������͵�����ƴ���(279)
	public boolean getCountryRegionCode(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_COUNTRYREGIONCODE);
			int i = 1;
			stmt.setString(i++, code);
			stmt.setString(i++, code);
			stmt.setString(i++, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �̲ݻ�е��Ʒ������ ����ͱ��� ��3���֣���е�⹺��(7)
	public boolean getTabaccoMachineProduct(String categoryCode,
			String groupCode, String variatyCode) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOMACHINEPRODUCT);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			stmt.setString(i++, variatyCode);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��Ʒ����������Ʒ����EAN UPCǰ3λǰ׺��
	public boolean getPrefixofRetailCommodityNumber(int code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �̲ݻ�е���� ����ͱ����2���֣�ר�ü� ��¼D�еĵ�λ����(672)
	public boolean getTabaccoMachineProducer(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOMACHINEPRODUCER);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// CID����4λ�����������
	public boolean getDistrictNo(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �̲ݻ�е��Ʒ������ ��ҵ��е��׼�� �����е������룬�������Ʒ�ִ��루6��
	public boolean getTabaccoStandardPart(String categoryCode,
			String groupCode, String variatyCode) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOSTANDARDPART);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);
			stmt.setString(i++, variatyCode);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �̲ݻ�е��Ʒ�����Ϸ���ͱ��� ��6���֣�ԭ��������(4)
	public boolean getTabaccoMaterial(String categoryCode, String variatyCode) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� �����Ʒ��������(15)
	public boolean getFoodAccount(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ�豸��������루23��
	public boolean getGrainEquipment(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ��ʩ��������루24��
	public boolean getGrainEstablishment(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_GRAINESTABLISHMENT);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �̲ݻ�е��Ʒ������ ����ͱ��� ��5���֣�����Ԫ���� ��5��
	public boolean getTabaccoElectricComponent(String categoryCode,
			String groupCode) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOELECTRICCOMPONENT);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ���ȡ��һ��������������
	public String getRandomAdminDivision() {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String code = "";
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_RANDOMADMINDIVISION);
			results = stmt.executeQuery();
			while (results.next()) {
				code = results.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return code;
	}

	// ���ȡ��һ��EANUPC���
	public String getRandomEANUPC() {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String code = "";
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_RANDOMEANUPC);
			results = stmt.executeQuery();
			while (results.next()) {
				code = String.valueOf(results.getInt("code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return code;
	}

	// ���ر�׼��ϸ��Ϣ
	public String getIDDetail(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		String name = "";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_IDDETAIL);
			stmt.setString(1, code);
			results = stmt.executeQuery();
			while (results.next()) {
				name = results.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return name;
	}

	// ���ò��ϱ��� ��1���֣����ò��Ϸ���������Ʒ����(10)
	public boolean getTobbacoMaterials(String categoryCode, String groupCode) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOMATERIALS);
			int i = 1;
			stmt.setString(i++, categoryCode);
			stmt.setString(i++, groupCode);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳó��ҵ��ͳ�Ʒ��������(14)
	public boolean getFoodTrade(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ�ӹ�(18)
	public boolean getFoodEconomy(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ�ִ�ҵ��ͳ�Ʒ��������(16)
	public boolean getGrainStoreHouse(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� �������溦���������(17)
	public boolean getGrainsDiseases(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ�ӹ���1���֣��ӹ���ҵ���������(19)
	public boolean getGrainsProcess(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ�ִ���3���֣����ķ��������(20)
	public boolean getGrainsEquipment(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ�ִ���2���֣���������������(21)
	public boolean getGrainConditionDetection(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_GRAINCONDITIONDETECTION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ�ִ���1���֣��ִ���ҵ���������(22)
	public boolean getgrainsSmartWMS(String code) {
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
			if (rowcount >= 1) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ�����2���֣�������׼���������(26)
	public boolean getGrainsQualityStandard(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_GRIANQUALITYSTANDARD);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��������������������(32)
	public boolean getMeasuringInstrument(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_MEASURINGINSTRUMENT);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ���� ��1���֣�ָ����������(27)
	public boolean getGrainsIndex(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ���ӹ���Ʒ���������(28)
	public boolean getGrainsInformation(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_GRAINSINFORMATION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ���Է��������(29)
	public boolean getGrainsAttribute(String code) {
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
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ʳ��Ϣ��������� ��ʳ��������ҵ�����������������(31)
	public boolean getGrainsAdministrative(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_GRAINSADMINISTRATIVE);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ������Ʒ����ʹ���(34)
	public boolean getConstructionProducts(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_CONSTRUCTIONPRODUCTS);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �������ӵ�ͼ��ݷ��������(45)
	public boolean getElectronicMap(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_ELECTRONICMAP);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ������Ϣ������������(56)
	public boolean getGeographicInformation(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_GEOGRAPHICINFORMATION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��֯���ϱ��뻯�˲���(64)
	public boolean getTextileFabricNameCode(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TETILEFABRICNAME);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��֯�������Դ���(64)��֯��X1X2
	public boolean getPropertiesMainMaterial(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_PROPERTIESMAINMATERIAL);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��֯�������Դ���(64)��֯�첼X1X2
	public boolean getPropertiesMain(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PROPERTIESMAIN);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��֯�������Դ���(64)��ά���� X3X4
	public boolean getPropertiesFiberCharacteristics(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_PROPERTIERFIBERCHARACTERS);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��֯�������Դ���(64)X7X8����᷽̽ʽ
	public boolean getPropertiesMix(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PROPERTIESMIX);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��֯�������Դ���(64)X9X10 01-19 99
	public boolean getPropertiesFabric(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_PROPERTIESFABRIC);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��֯�������Դ���(64)X11X12
	public boolean getPropertiesDyeingandFinishing(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_PROPERTIESDYEING);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ����װ������ҵ��Ʒȫ�������ڹ���֪ʶ��2����(65)
	public boolean getGeneralManufacturingProcess(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_MANUFACTURINGPROCESS);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ȫ����Ҫ��Ʒ����������2���� ���������Ʒ(712)
	public boolean getUntransportableProduct(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_UNTRANSPORTABLEPRODUCT);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ȫ����Ҫ��Ʒ����������2���� ���������Ʒ��3λ(712)
	public boolean getLastThreeUntransportableProduct(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_LASTTHREEUNTRANSPORTABLEPRODUCT);
			int i = 1;
			stmt.setString(i, "%" + code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��·��ͨ��Ϣ�ɼ���Ϣ���������(77)
	public boolean getTrafficInformationCollection(String firstCode,
			String secondCode) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TRAFFICINFORMATIONCOLLECTION);
			int i = 1;
			stmt.setString(i++, firstCode);
			stmt.setString(i++, secondCode);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �̲���ҵ����ͳ�����Ԫ��2���� ���뼯(202)
	public boolean getTrafficOrganization(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOORGANIZATION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��Ҷ�����5������Ҷ��ɫ����(204)
	public boolean getTobaccoLeafColor(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOLEAFCOLOR);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��Ҷ�����2������Ҷ��̬����(207)
	public boolean getTobaccoLeafForm(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_TABACCOLEAFFORM);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��Ҷ�����1������Ҷ���������(208)
	public boolean getTobaccoLeafClass(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOLEAFCLASS);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ͯ�����״����(213)
	public boolean getChildrenExcrement(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_CHILDRENEXCREMENT);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �������������ࡢ��ʩ���ࡢ��Ʒ�������
	public boolean getFuneral(String id, String type) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(type);
			stmt.setString(1, id);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ���Ƶ�ʴ���(214)
	public boolean getDrinkingFrequency(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_DRINKINGFREQUENCY);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ����������(214)
	public boolean getDrinkingClass(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_DRINKINGCLASS);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ����Ƶ�ʴ���(214)
	public boolean getPhysicalActivityFrequency(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_PHYSICALACTIVITYFREQUENCY);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ������ֹ��ʽ�����(215)
	public boolean getTerminationofPregnancy(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TERMINATIONOFPREGNENCY);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ���䷽ʽ����(215)
	public boolean getModeofProduction(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_MODEOFPRODUCTION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ����ص�������(215)
	public boolean getDileveryPlace(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_DILIVERYPLACE);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ������Ϣ���Ԫֵ������17���֣��������(218)
	public boolean getHealthSupervisionObject(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HEALTHSUPERVISIONOBJECT);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ��ͨ���ߴ���(219)
	public boolean getCommunicationCode(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_COMMUNICATIONCODE);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ����ල����Ա����������(220)
	public boolean getHygieneAgencyPersonnel(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HYGIENEAGENCYPERSONNEL);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ����ල��ְ��������(220)
	public boolean getWorkerHealthSupervision(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_WORKERHEALTHSUPERVISION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �̲ݻ�е(195)
	public boolean getTobaccoMachineryID(String id) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_TABACCOMACHINEPRODUCT);
			stmt.setString(1, id);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 280-�������뵳����ش�����Ʒ��� �����ݿ�
	public boolean getPortTariff280(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF281);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 281-�����鱦��ʯ��������Ʒ���������Ʒ��� �����ݿ�
	public boolean getPortTariff281(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa281);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 281-�����鱦��ʯ���������ʷ��������Ʒ��� �����ݿ�
	public boolean getPortTariffMa281(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa281);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 282-������Ϣ��ȫ����������Ʒ��� �����ݿ�
	public boolean getPortTariffMa282(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa282);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 284-������ᾭ��Ŀ�����ʹ���� �����ݿ�
	public boolean getPortTariffMa284(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa284);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 285-����������Ϣ����ʹ���� �����ݿ�
	public boolean getPortTariffMa285(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa285);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 287-������װ����ʹ���� �����ݿ�
	public boolean getPortTariffMa287(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa287);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 288-������װ����ʹ���� �����ݿ�
	public boolean getPortTariffMa288(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa288);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 291-����ҽҩ��е����ʹ���� �����ݿ�
	public boolean getPortTariffMa291(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFFMa191);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// �̲ݻ�е�������úͼ����ļ����븽¼C���ѯ
	public boolean getPrefixoftabaccoC(int code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_tabaccoC);
			int i = 1;
			stmt.setInt(i++, code);
			stmt.setInt(i++, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	
		//268-�������뵳����ش�����Ʒ���   �����ݿ�
		public boolean getPortTariff268(String code) {
			Connection connection = JdbcUtils.getConnection();
			PreparedStatement stmt = null;
			ResultSet results = null;
			boolean ret = false;
			try{
				stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF268);
				int i = 1;
				stmt.setString(i, code);
			    results = stmt.executeQuery();
				int rowcount = 0;
				while (results.next()) {
					rowcount++;				
				}
				if(1 == rowcount){
					ret =  true;
					System.out.println("results="+results.toString());
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtils.free(null, null, connection);
			}
			return ret;
		}
		//	 270-������Ȼ�ֺ����������Ʒ���   �����ݿ�
			public boolean getPortTariff270(String code) {
				Connection connection = JdbcUtils.getConnection();
				PreparedStatement stmt = null;
				ResultSet results = null;
				boolean ret = false;
				try{
					stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF270);
					int i = 1;
					stmt.setString(i, code);
				    results = stmt.executeQuery();
					int rowcount = 0;
					while (results.next()) {
						rowcount++;				
					}
					if(1 == rowcount){
						ret =  true;
						System.out.println("results="+results.toString());
					} 
				}catch (Exception e) {
					e.printStackTrace();
				}finally{
					JdbcUtils.free(null, null, connection);
				}
				return ret;
			}
			//	 	 275-����������ҵ������������Ʒ���   �����ݿ�
					public boolean getPortTariff275(String code) {
						Connection connection = JdbcUtils.getConnection();
						PreparedStatement stmt = null;
						ResultSet results = null;
						boolean ret = false;
						try{
							stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF275);
							int i = 1;
							stmt.setString(i, code);
						    results = stmt.executeQuery();
							int rowcount = 0;
							while (results.next()) {
								rowcount++;				
							}
							if(1 == rowcount){
								ret =  true;
								System.out.println("results="+results.toString());
							} 
						}catch (Exception e) {
							e.printStackTrace();
						}finally{
							JdbcUtils.free(null, null, connection);
						}
						return ret;
					}
//				 	 276-����������Ʒ���������Ʒ���   �����ݿ�
								public boolean getPortTariff276(String code) {
									Connection connection = JdbcUtils.getConnection();
									PreparedStatement stmt = null;
									ResultSet results = null;
									boolean ret = false;
									try{
										stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF276);
										int i = 1;
										stmt.setString(i, code);
									    results = stmt.executeQuery();
										int rowcount = 0;
										while (results.next()) {
											rowcount++;				
										}
										if(1 == rowcount){
											ret =  true;
											System.out.println("results="+results.toString());
										} 
									}catch (Exception e) {
										e.printStackTrace();
									}finally{
										JdbcUtils.free(null, null, connection);
									}
									return ret;
								}					

		
		
		
				
				//395-���������Ϣ�������ʹ����  �����ݿ�
				public boolean getFireInfomation395(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF395);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				//399-���������Ϣ�������ʹ����  �����ݿ�
				public boolean getFireInfomation399(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF399);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				//403-���������Ϣ�������ʹ����  �����ݿ⣺������������
				public boolean getFireInfomation403(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF403);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				
				//409-���������Ϣ�������ʹ����  �����ݿ⣺���ѵ�����˴���
				public boolean getFireInfomation409(String code) {
					Connection connection = JdbcUtils.getConnection();
					PreparedStatement stmt = null;
					ResultSet results = null;
					boolean ret = false;
					try{
						stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF409);
						int i = 1;
						stmt.setString(i, code);
					    results = stmt.executeQuery();
						int rowcount = 0;
						while (results.next()) {
							rowcount++;				
						}
						if(1 == rowcount){
							ret =  true;
							System.out.println("results="+results.toString());
						} 
					}catch (Exception e) {
						e.printStackTrace();
					}finally{
						JdbcUtils.free(null, null, connection);
					}
					return ret;
				}
				
				
				
				

	// /���ó�����䴬�������������ԭ��312��
	public boolean getInternationalShipCode(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_INTERNATIONALSHIP);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// /�غ�����������루238��
	public boolean getCoastalAdminAreaID(String id) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_COASTALADMINAREAID);
			stmt.setString(1, id);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (rowcount == 1) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// /�������ʹ��루227��
	public boolean getWirtschaftsTypCodeID(String id) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_WIRTSCHAFTSTYPCODE);
			stmt.setString(1, id);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// /��Ⱦ����ƴ��루225��
	public boolean getInfectiousDiseasesID(String id) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_INFECTIOUSDISEASES);
			stmt.setString(1, id);
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;

	}

	// �����������������ƹ���309��
	public boolean getGeographicalCode(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;

		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_GEOGRAPHICALCODE);

			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ũҩ������Ƽ�����
	public boolean getPesticideFormulationCode(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PESTICIDECODE);

			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// ���ó��ߴ���루306��
	public boolean getPassengerCarCode(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_PASSENGERCARCODE);

			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;

	}

	public boolean getCivilAviation(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_CIVILAVIATION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getRoadTransportation32(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION32);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getRoadTransportation50(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION50);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getRoadTransportation53(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION53);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getSecurityAccounterments(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_SECURITYACCOUNTERMENTS);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getSpecialVehicle(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_SPECIALVEHICLE);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getRoadTransportation5(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION5);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getRoadTransportation41(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION41);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getRoadTransportation63(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION63);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getRoadTransportation64(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION64);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayTransportation(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYTRANSPORTATION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayTransportation4b1(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYTRANSPORTATION4B1);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayTransportation4b7(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYTRANSPORTATION4B7);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayTransportation4b9(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYTRANSPORTATION4B9);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayTransportation4c3(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYTRANSPORTATION4C3);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getPortTariff3(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF3);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getPortTariff4(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF4);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getPortTariff9(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF9);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getPortTariff25(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF25);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getPortTariff26(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF26);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getPortTariff10(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF10);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery2(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY2);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayMaintenance4(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYMAINTENANCE4);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayMaintenance3(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYMAINTENANCE4);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery3(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY3);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery4(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY4);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery5(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY5);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery6(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY6);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery7(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY7);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery8(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY8);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery9(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY9);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getMachinery10(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_MACHINERY10);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayTransportation4c6(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYTRANSPORTATION4C6);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getHighwayTransportation4b10(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_HIGHWAYTRANSPORTATION4B10);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getWaterwayTransportation(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_WATERWAYTRANSPORTATION);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	public boolean getPort(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORT);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}

	// 410-——干部职务名称代码  查表数据库
	public boolean getOfficialPositonByCode(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_OFFICIALPOSITION);
			int i = 1;
			stmt.setString(i, code);
			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
				System.out.println("results=" + results.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	public boolean getRoadTransportation60(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION60);
			int i = 1;
			stmt.setString(i, code);

			results = stmt.executeQuery();
			int rowcount = 0;
			while (results.next()) {
				rowcount++;
			}
			if (1 == rowcount) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	public boolean getRoadTransportation22(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try{
			stmt = connection.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION22);
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
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
	
	public boolean getRoadTransportation21(String code){
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try{
			stmt = connection.prepareStatement(RecoUtil.SELECT_ROADTRANSPORTATION21);
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
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.free(null, null, connection);
		}
		return ret;
	}
}
