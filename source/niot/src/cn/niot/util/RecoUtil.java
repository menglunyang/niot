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

	// �̲ݻ�е��Ʒ������
	public static final String SELECT_TABACCOMACHINEPRODUCT = "select * from tabaccomachineproduct where categorycode=? and groupcode=? and variatycode=?";

	// ��Ʒ����������Ʒ����EAN UPCǰ3λǰ׺��
	public static final String SELECT_EANUPC = "select * from EANUPC where begincode<=? and endcode>=?";

	// �̲ݻ�е���� ����ͱ����2���֣�ר�ü� ��¼D�еĵ�λ����(672)
	public static final String SELECT_TABACCOMACHINEPRODUCER = "select * from tabaccomachineproducer where id=? limit 1";

	// CID����4λ�����������
	public static final String SELECT_DISTRICTNO = "select * from districtno where id=?";

	// �̲ݻ�е��Ʒ������ ��ҵ��е��׼�� �����е������룬�������Ʒ�ִ��루6��
	public static final String SELECT_TABACCOSTANDARDPART = "select * from tabaccostandardpart where categorycode=? and groupcode=? and variatycode=?";

	// �̲ݻ�е��Ʒ�����Ϸ���ͱ��� ��6���֣�ԭ��������(4)
	public static final String SELECT_TABACCOMATERIAL = "select * from tabaccomaterial where categorycode=? and variatycode=?";

	// ��ʳ��Ϣ��������� �����Ʒ��������(15)
	public static final String SELECT_FOORDACCOUNT = "select * from foodaccount where id=?";

	// ��ʳ��Ϣ��������� ��ʳ�豸��������루23��
	public static final String SELECT_GRAINEQUIPMENT = "select * from grainequipment where id=?";

	// ��ʳ��Ϣ��������� ��ʳ��ʩ��������루24��
	public static final String SELECT_GRAINESTABLISHMENT = "select * from grainestablishment where id=?";

	// �̲ݻ�е��Ʒ������ ����ͱ��� ��5���֣�����Ԫ���� ��5��
	public static final String SELECT_TABACCOELECTRICCOMPONENT = "select * from tabaccoelectriccomponent where categorycode=? and groupcode=?";

	// ������������ȡһ����¼
	public static final String SELECT_RANDOMADMINDIVISION = "select * from admindivision where id>=convert(floor(((SELECT MAX(convert(Id,signed)) FROM admindivision)-(SELECT MIN(convert(Id,signed)) FROM admindivision)) * rand() + (SELECT MIN(convert(Id,signed)) FROM admindivision)),char(6)) limit 1";
	
	//EANUPC�������һ����¼
	public static final String SELECT_RANDOMEANUPC = "select floor(rand()*(endcode-begincode)+begincode) as code from (select * from EANUPC where rowno >= (select floor(rand()*(max(rowno)-min(rowno))) + min(rowno) from EANUPC) limit 1) t";
	
	public static final String SELECT_TYPEANDRULES = "select * from iotid";
	
	//���ò��ϱ��� ��1���֣����ò��Ϸ���������Ʒ����(10)
	public static final String SELECT_TABACCOMATERIALS = "select * from tabaccomaterials where categorycode=? and groupcode=?";
	
	//��ʳ��Ϣ��������� ��ʳó��ҵ��ͳ�Ʒ��������(14)
	public static final String SELECT_FOODTRADE = "select * from foodtradestatistics where id=?";
	
	//��ʳ��Ϣ��������� ��ʳ�ִ�ҵ��ͳ�Ʒ��������(16)
	public static final String SELECT_GRAINSTOREHOUSE = "select * from grainstorehouse where id=?";
	
	//��ʳ��Ϣ��������� �������溦���������(17)
	public static final String SELECT_GRAINSDISEASES = "select * from grainsdiseases where id=?";
	
	//��ʳ��Ϣ��������� ��ʳ�ӹ�(18)
	public static final String SELECT_FOODECONOMY = "select * from grainstechnicaleconomy where id=?";
	
	//��ʳ��Ϣ��������� ��ʳ�ӹ���1���֣��ӹ���ҵ���������(19)
	public static final String SELECT_GRAINSPROCESS = "select * from grainsprocess where id=?";
	
	//��ʳ��Ϣ��������� ��ʳ�ִ���3���֣����ķ��������(20)
	public static final String SELECT_GRAINSEQUIPMENT = "select * from grainsequipment where id=?";
	
	//��ʳ��Ϣ��������� ��ʳ�ִ���2���֣���������������(21)
	public static final String SELECT_GRAINCONDITIONDETECTION = "select * from grainconditiondetection where id=?";
	
	//��ʳ��Ϣ��������� ��ʳ�ִ���1���֣��ִ���ҵ���������(22)
	public static final String SELECT_GRAINSSMARTWMS = "select * from grainsSmartWMS where id like ?";
	
	//��ʳ��Ϣ��������� ��ʳ�����2���֣�������׼���������(26)
	public static final String SELECT_GRIANQUALITYSTANDARD = "select * from grainsqualitystandard where id=?";
	
	//��������������������(32)
	public static final String SELECT_MEASURINGINSTRUMENT = "select * from measuringinstrument where code=?";
	
	//��ʳ��Ϣ��������� ��ʳ���� ��1���֣�ָ����������(27)
	public static final String SELECT_GRAINSINDEX = "select * from grainsindex where id=?";
	
	//��ʳ��Ϣ��������� ��ʳ���ӹ���Ʒ���������(28)
	public static final String SELECT_GRAINSINFORMATION = "select * from grainsinformation where id=?";
	
	//��ʳ��Ϣ��������� ��ʳ���Է��������(29)
	public static final String SELECT_GRAINSATTRIBUTE = "select * from grainsattribute where id=?"; 
	
	//select the first 7 numbers of a phone number
	public static final String SELECT_PHONENUMBER = "select * from phonenumber where MobileNumber=?";
	
	//select the first 2 characters of a normal vehicle NO
	public static final String SELECT_NORMALVEHICLENO = "select * from vehiclenonormal where vehicleprefix=?";
	
	//select the first 2 characters of a army vehicle NO
	public static final String SELECT_ARMYVEHICLENO = "select * from vehiclenoarmy where vehicleprefix=?";
	
	//select the first 2 characters of a WJ vehicle NO
	public static final String SELECT_WJVEHICLENO = "select * from vehiclenowj where vehicleprefix=?";
	
	//��û��ƥ��ɹ��κ�һ�ֱ�ʶ
	public static final int NO_ID_MATCHED = 0;
	
	//��ƥ��ɹ�һ�ֱ�ʶ
	public static final int ONE_ID_MATCHED = 1;

	// ������ϸ��Ϣ
	public static final String SELECT_IDDETAIL = "select * from iotid join iotdetail on iotdetail.did=iotid.id and iotid.id=?";
	
	// ǰ��ҳ����ʾ��׼��ƶ����ַ����󳤶�
	public static final int DISPLAYLENGTH = 9;
	
	//���URL��ַ
	public static String getURLParam(String paramName){
		ActionContext ctx = ActionContext.getContext();            
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String url = request.getContextPath();
		String parameter = request.getParameter(paramName);
		return parameter;
	}
	
	//�޸ı�׼IDΪ���
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
