package cn.niot.util;


public class RecoUtil {
	public static final String JNDI_NAME = "java:comp/env/jdbc/IoTDataSource";
	public static final String SELECT_IOTID = "select * from iotid where id=?";
	public static final String COLUMNNAME = "columnName";
	
	//administrative division
	public static final String SELECT_ADMINDIVISION = "select * from admindivision where id=?";
	
	//country and region code
	public static final String SELECT_COUNTRYREGIONCODE = "select * from countryregioncode where twocharcode=? or threecharcode=? or numcode=?";
	

	public static final String ID_LEN = "Len";
	public static final String ID_NAME = "IDName";
	public static final int INTERVAL_WIDTH = 2;
	public static final int COUNT_NUMBER_CHARS = 64;

	//烟草机械产品用物料
	public static final String SELECT_TABACCOMACHINEPRODUCT = "select * from tabaccomachineproduct where categorycode=? and groupcode=? and variatycode=?";
	
	//商品条码零售商品编码EAN UPC前3位前缀码
	public static final String SELECT_EANUPC = "select * from EANUPC where begincode<=? and endcode>=?";
	
	//烟草机械物料 分类和编码第2部分：专用件 附录D中的单位编码
	public static final String SELECT_TABACCOMACHINEPRODUCER = "select * from tabaccomachineproducer where id=?";
	
	//CID调用4位数字行政区号
	public static final String SELECT_DISTRICTNO = "select * from districtno where id=?";

}
