package cn.niot.util;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.niot.dao.RecoDao;

import com.opensymphony.xwork2.ActionContext;

public class RecoUtil {
	public static final String JNDI_NAME = "java:comp/env/jdbc/IoTDataSource";
	public static final String SELECT_IOTID = "select * from iotid where id=?";
	public static final String COLUMNNAME = "columnName";
	public static final String ID_LEN = "Len";
	public static final String ID_NAME = "IDName";
	public static final int INTERVAL_WIDTH = 2;
	public static final int COUNT_NUMBER_CHARS = 64;

	// administrative division
	public static final String SELECT_ADMINDIVISION = "select * from admindivision where id=?";

	// country and region code
	public static final String SELECT_COUNTRYREGIONCODE = "select * from countryregioncode where twocharcode=? or threecharcode=? or numcode=?";

	// 烟草机械产品用物料
	public static final String SELECT_TABACCOMACHINEPRODUCT = "select * from tabaccomachineproduct where categorycode=? and groupcode=? and variatycode=?";

	// 商品条码零售商品编码EAN UPC前3位前缀码
	public static final String SELECT_EANUPC = "select * from EANUPC where begincode<=? and endcode>=?";

	// 烟草机械物料 分类和编码第2部分：专用件 附录D中的单位编码(672)
	public static final String SELECT_TABACCOMACHINEPRODUCER = "select * from tabaccomachineproducer where id=? limit 1";

	// CID调用4位数字行政区号
	public static final String SELECT_DISTRICTNO = "select * from districtno where id=?";

	// 烟草机械产品用物料 企业机械标准件 编码中的类别代码，组别代码和品种代码（6）
	public static final String SELECT_TABACCOSTANDARDPART = "select * from tabaccostandardpart where categorycode=? and groupcode=? and variatycode=?";

	// 烟草机械产品用物料分类和编码 第6部分：原、辅材料(4)
	public static final String SELECT_TABACCOMATERIAL = "select * from tabaccomaterial where categorycode=? and variatycode=?";

	// 粮食信息分类与编码 财务会计分类与代码(15)
	public static final String SELECT_FOORDACCOUNT = "select * from foodaccount where id=?";

	// 粮食信息分类与代码 粮食设备分类与代码（23）
	public static final String SELECT_GRAINEQUIPMENT = "select * from grainequipment where id=?";

	// 粮食信息分类与编码 粮食设施分类与编码（24）
	public static final String SELECT_GRAINESTABLISHMENT = "select * from grainestablishment where id=?";

	// 烟草机械产品用物料 分类和编码 第5部分：电器元器件 （5）
	public static final String SELECT_TABACCOELECTRICCOMPONENT = "select * from tabaccoelectriccomponent where categorycode=? and groupcode=?";

	// 行政区划代码随机取一条记录
	public static final String SELECT_RANDOMADMINDIVISION = "select * from admindivision where id>=convert(floor(((SELECT MAX(convert(Id,signed)) FROM admindivision)-(SELECT MIN(convert(Id,signed)) FROM admindivision)) * rand() + (SELECT MIN(convert(Id,signed)) FROM admindivision)),char(6)) limit 1";
	
	//EANUPC代码随机一条记录
	public static final String SELECT_RANDOMEANUPC = "select floor(rand()*(endcode-begincode)+begincode) as code from (select * from EANUPC where rowno >= (select floor(rand()*(max(rowno)-min(rowno))) + min(rowno) from EANUPC) limit 1) t";
	
	public static final String SELECT_TYPEANDRULES="select * from iotid";
	
	//、没有匹配成功任何一种标识
	public static final int NO_ID_MATCHED = 0;
	
	//、匹配成功一种标识
	public static final int ONE_ID_MATCHED = 1;

	// 编码详细信息
	public static final String SELECT_IDDETAIL = "select * from iotid join iotdetail on iotdetail.did=iotid.id and iotid.id=?;";
	
	
	//获得URL地址
	public static String getURLParam(String paramName){
		ActionContext ctx = ActionContext.getContext();            
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String url = request.getContextPath();
		String parameter = request.getParameter(paramName);
		return parameter;
	}
	
	//修改标准ID为名称
	public static HashMap<String, Double> replaceIotId(HashMap<String, Double> map){
		HashMap<String, Double> newMap = new HashMap<String, Double>();
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next().toString();
			RecoDao dao = new RecoDao();
			String name = dao.getIDDetail(key);
			newMap.put(name, map.get(key));
		}
		return newMap;
	}
}
