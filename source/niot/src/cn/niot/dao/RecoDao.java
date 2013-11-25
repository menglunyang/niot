package cn.niot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cn.niot.util.*;

public class RecoDao {
	private static RecoDao recoDao = new RecoDao();
	private DataSource ds;
	
	private RecoDao() throws IllegalStateException {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(RecoUtil.JNDI_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	
	public static RecoDao getRecoDao() {
		return recoDao;
	}
	
	public String getIoTID(String id){
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection
					.prepareStatement(RecoUtil.SELECT_IOTID);
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();

			while (results.next()) {
				String res = results.getString("id") + "  " + results.getString("length") + "  " + results.getString("byte") + "  " + results.getString("function");
				System.out.println(res);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException se) {
				}
			}
		}
		return "ok";
	}
	
	
	
}
