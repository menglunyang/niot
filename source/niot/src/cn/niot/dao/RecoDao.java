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
	 * 参数也是返回值 返回hashMapTypeToRules、rmvRuleSet、rmvIDSet、hashMapRuleToTypes
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
				ArrayList<String> rules = new ArrayList<String>();// 函数返回值中的ArrayList
				String idType = results.getString("id");
				String lengthRule = results.getString("length");
				String byteRule = results.getString("byte");
				String functionRules = results.getString("function");
				double priorProbability = results.getDouble("priorProbability");
				if (lengthRule.length() != 0) {
					lengthRule = "IoTIDLength)(?#PARA=" + lengthRule + "){]";
					rules.add(lengthRule);// eg.length8)(?#PARA=8){]
					// 函数名字叫length8,参数8
					rmvRuleSet.put(lengthRule, 0.5);// 向rmvRuleSet添加length规则
					hashMapTypeToRulesSwitchhashMapRuleToTypes(
							hashMapRuleToTypes, lengthRule, idType);// hashMapTypeToRules转换为hashMapRuleToTypes,处理length
				}
				if (byteRule.length() != 0) {
					String[] byteStrArray = byteRule.split(";");
					for (int i = 0; i < byteStrArray.length; i++) {
						byteStrArray[i] = "IoTIDByte)(?#PARA="
								+ byteStrArray[i] + "){]";
						rules.add(byteStrArray[i]);
						rmvRuleSet.put(byteStrArray[i], 0.5);// 向rmvRuleSet添加byte规则
						hashMapTypeToRulesSwitchhashMapRuleToTypes(
								hashMapRuleToTypes, byteStrArray[i], idType);// hashMapTypeToRules转换为hashMapRuleToTypes,处理byte
					}
				}
				rmvIDSet.put(idType, priorProbability);// 向rmvRuleSet添加ID,先验概率0.5
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
		if (hashMapRuleToTypes.get(rule) == null) {// hashMapTypeToRules转换为hashMapRuleToTypes,处理function
			types.add(idType);
			hashMapRuleToTypes.put(rule, types);
		} else {
			types = hashMapRuleToTypes.get(rule);
			types.add(idType);
			hashMapRuleToTypes.put(rule, types);
		}

	}

	// /行政区划代码(296)
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

	// /世界各国和地区名称代码(279)
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

	// 烟草机械产品用物料 分类和编码 第3部分：机械外购件(7)
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

	// 商品条码零售商品编码EAN UPC前3位前缀码
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

	// 烟草机械物料 分类和编码第2部分：专用件 附录D中的单位编码(672)
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

	// CID调用4位数字行政区号
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

	// 烟草机械产品用物料 企业机械标准件 编码中的类别代码，组别代码和品种代码（6）
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

	// 烟草机械产品用物料分类和编码 第6部分：原、辅材料(4)
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

	// 粮食信息分类与编码 财务会计分类与代码(15)
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

	// 粮食信息分类与代码 粮食设备分类与代码（23）
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

	// 粮食信息分类与编码 粮食设施分类与编码（24）
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

	// 烟草机械产品用物料 分类和编码 第5部分：电器元器件 （5）
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

	// 随机取出一条行政区划代码数据
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

	// 随机取出一条EANUPC数据
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

	// 返回标准详细信息
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

	// 烟用材料编码 第1部分：烟用材料分类代码与产品代码(10)
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

	// 粮食信息分类与编码 粮食贸易业务统计分类与代码(14)
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

	// 粮食信息分类与编码 粮食加工(18)
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

	// 粮食信息分类与编码 粮食仓储业务统计分类与代码(16)
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

	// 粮食信息分类与编码 储粮病虫害分类与代码(17)
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

	// 粮食信息分类与编码 粮食加工第1部分：加工作业分类与代码(19)
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

	// 粮食信息分类与编码 粮食仓储第3部分：器材分类与代码(20)
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

	// 粮食信息分类与编码 粮食仓储第2部分：粮情检测分类与代码(21)
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

	// 粮食信息分类与编码 粮食仓储第1部分：仓储作业分类与代码(22)
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

	// 粮食信息分类与编码 粮食检验第2部分：质量标准分类与代码(26)
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

	// 计量器具命名与分类编码(32)
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

	// 粮食信息分类与编码 粮食检验 第1部分：指标分类与代码(27)
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

	// 粮食信息分类与编码 粮食及加工产品分类与代码(28)
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

	// 粮食信息分类与编码 粮食属性分类与代码(29)
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

	// 粮食信息分类与编码 粮食行政、事业机构及社会团体分类与代码(31)
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

	// 建筑产品分类和代码(34)
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

	// 导航电子地图数据分类与编码(45)
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

	// 地理信息分类与编码规则(56)
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

	// 纺织面料编码化纤部分(64)
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

	// 纺织面料属性代码(64)机织物X1X2
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

	// 纺织面料属性代码(64)非织造布X1X2
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

	// 纺织面料属性代码(64)纤维特征 X3X4
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

	// 纺织面料属性代码(64)X7X8纤网固结方式
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

	// 纺织面料属性代码(64)X9X10 01-19 99
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

	// 纺织面料属性代码(64)X11X12
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

	// 面向装备制造业产品全生命周期工艺知识第2部分(65)
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

	// 全国主要产品分类与代码第2部分 不可运输产品(712)
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

	// 全国主要产品分类与代码第2部分 不可运输产品后3位(712)
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

	// 道路交通信息采集信息分类与编码(77)
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

	// 烟草行业工商统计数据元第2部分 代码集(202)
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

	// 烟叶代码第5部分烟叶颜色代码(204)
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

	// 烟叶代码第2部分烟叶形态代码(207)
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

	// 烟叶代码第1部分烟叶分类与代码(208)
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

	// 儿童大便性状代码(213)
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

	// 查找殡葬服务分类、设施分类、用品分类代码
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

	// 饮酒频率代码(214)
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

	// 饮酒种类代码(214)
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

	// 身体活动频率代码(214)
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

	// 妊娠终止方式代码表(215)
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

	// 分娩方式代码(215)
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

	// 分娩地点类别代码(215)
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

	// 卫生信息数据元值域代码第17部分：卫生管理(218)
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

	// 交通工具代码(219)
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

	// 卫生监督机构人员编制类别代码(220)
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

	// 卫生监督机构职工类别代码(220)
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

	// 烟草机械(195)
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

	// 280-——中央党政机关代码编制方法 查表数据库
	public boolean getPortTariff280(String code) {
		Connection connection = JdbcUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet results = null;
		boolean ret = false;
		try {
			stmt = connection.prepareStatement(RecoUtil.SELECT_PORTTARIFF280);
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

	// 281-——珠宝玉石及金属产品分类代码编制方法 查表数据库
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

	// 281-——珠宝玉石及金属材质分类代码编制方法 查表数据库
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

	// 282-——信息安全技术代码编制方法 查表数据库
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

	// 284-——社会经济目标分类和代码表 查表数据库
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

	// 285-——物流信息分类和代码表 查表数据库
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

	// 287-——服装分类和代码表 查表数据库
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

	// 288-——服装分类和代码表 查表数据库
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

	// 291-——医药器械分类和代码表 查表数据库
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

	// 烟草机械电气配置和技术文件代码附录C表查询
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
	// 291-——医药器械分类和代码表 查表数据库
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

}
