package cn.niot.util;


public class RecoUtil {
	public static final String JNDI_NAME = "java:comp/env/jdbc/IoTDataSource";
	public static final String SELECT_IOTID = "select * from iotid where id=?";
	public static final String COLUMNNAME = "columnName";
	
	//administrative division
	public static final String SELECT_ADMINDIVISION = "select * from admindivision where id=?";
	
	//country and region code
	public static final String SELECT_COUNTRYREGIONCODE = "select * from countryregioncode where twocharcode=? or threecharcode=? or numcode=?";
}
