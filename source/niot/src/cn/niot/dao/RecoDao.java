package cn.niot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import cn.niot.util.JdbcUtils;

import cn.niot.util.*;

public class RecoDao {
	private static RecoDao recoDao = new RecoDao();
	
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
	
	///行政区划代码(296)
	public boolean getAdminDivisionID(String id){
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
	
	///世界各国和地区名称代码(279)
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
	
	//烟草机械产品用物料 分类和编码 第3部分：机械外购件(7)
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
	
	//商品条码零售商品编码EAN UPC前3位前缀码
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
	
	//烟草机械物料 分类和编码第2部分：专用件 附录D中的单位编码(672)
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
	
	//CID调用4位数字行政区号
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
	
	//烟草机械产品用物料 企业机械标准件 编码中的类别代码，组别代码和品种代码（6）
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
	
	//烟草机械产品用物料分类和编码 第6部分：原、辅材料(4)
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
	
	//粮食信息分类与编码 财务会计分类与代码(15)
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
	
	//粮食信息分类与代码 粮食设备分类与代码（23）
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
	
	//粮食信息分类与编码 粮食设施分类与编码（24）
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
	
	//烟草机械产品用物料 分类和编码 第5部分：电器元器件 （5）
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
	
	//随机取出一条行政区划代码数据
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
	
	//随机取出一条EANUPC数据
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
}
