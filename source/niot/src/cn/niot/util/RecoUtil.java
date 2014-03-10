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
	// �̲ݻ�е�������úͼ����ļ����븽¼C���ѯ
	public static final String SELECT_tabaccoC = "select * from tabaccoC where code=?";

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

	// EANUPC�������һ����¼
	public static final String SELECT_RANDOMEANUPC = "select floor(rand()*(endcode-begincode)+begincode) as code from (select * from EANUPC where rowno >= (select floor(rand()*(max(rowno)-min(rowno))) + min(rowno) from EANUPC) limit 1) t";

	public static final String SELECT_TYPEANDRULES = "select * from iotid";

	// ���ò��ϱ��� ��1���֣����ò��Ϸ���������Ʒ����(10)
	public static final String SELECT_TABACCOMATERIALS = "select * from tabaccomaterials where categorycode=? and groupcode=?";

	// ��ʳ��Ϣ��������� ��ʳó��ҵ��ͳ�Ʒ��������(14)
	public static final String SELECT_FOODTRADE = "select * from foodtradestatistics where id=?";

	// ��ʳ��Ϣ��������� ��ʳ�ִ�ҵ��ͳ�Ʒ��������(16)
	public static final String SELECT_GRAINSTOREHOUSE = "select * from grainstorehouse where id=?";

	// ��ʳ��Ϣ��������� �������溦���������(17)
	public static final String SELECT_GRAINSDISEASES = "select * from grainsdiseases where id=?";

	// ��ʳ��Ϣ��������� ��ʳ�ӹ�(18)
	public static final String SELECT_FOODECONOMY = "select * from grainstechnicaleconomy where id=?";

	// ��ʳ��Ϣ��������� ��ʳ�ӹ���1���֣��ӹ���ҵ���������(19)
	public static final String SELECT_GRAINSPROCESS = "select * from grainsprocess where id=?";

	// ��ʳ��Ϣ��������� ��ʳ�ִ���3���֣����ķ��������(20)
	public static final String SELECT_GRAINSEQUIPMENT = "select * from grainsequipment where id=?";

	// ��ʳ��Ϣ��������� ��ʳ�ִ���2���֣���������������(21)
	public static final String SELECT_GRAINCONDITIONDETECTION = "select * from grainconditiondetection where id=?";

	// ��ʳ��Ϣ��������� ��ʳ�ִ���1���֣��ִ���ҵ���������(22)
	public static final String SELECT_GRAINSSMARTWMS = "select * from grainsSmartWMS where id like ?";

	// ��ʳ��Ϣ��������� ��ʳ�����2���֣�������׼���������(26)
	public static final String SELECT_GRIANQUALITYSTANDARD = "select * from grainsqualitystandard where id=?";

	// ��������������������(32)
	public static final String SELECT_MEASURINGINSTRUMENT = "select * from measuringinstrument where code=?";

	// ��ʳ��Ϣ��������� ��ʳ���� ��1���֣�ָ����������(27)
	public static final String SELECT_GRAINSINDEX = "select * from grainsindex where id=?";

	// ��ʳ��Ϣ��������� ��ʳ���ӹ���Ʒ���������(28)
	public static final String SELECT_GRAINSINFORMATION = "select * from grainsinformation where id=?";

	// ��ʳ��Ϣ��������� ��ʳ���Է��������(29)
	public static final String SELECT_GRAINSATTRIBUTE = "select * from grainsattribute where id=?";

	// ��ʳ��Ϣ��������� ��ʳ��������ҵ�����������������(31)
	public static final String SELECT_GRAINSADMINISTRATIVE = "select * from grainsadministrative where id=?";

	// ������Ʒ����ʹ���(34)
	public static final String SELECT_CONSTRUCTIONPRODUCTS = "select * from constructionproducts where id=?";

	// �������ӵ�ͼ��ݷ��������(45)
	public static final String SELECT_ELECTRONICMAP = "select * from electronicmap where id=?";

	// ������Ϣ������������(56)
	public static final String SELECT_GEOGRAPHICINFORMATION = "select * from geographicinformation where id=?";

	// ��֯���ϱ��뻯�˲���(64)
	public static final String SELECT_TETILEFABRICNAME = "select * from textilefabricnamecode where id=?";

	// ��֯�������Դ���(64)X1X2
	public static final String SELECT_PROPERTIESMAINMATERIAL = "select * from propertiesmainmaterial where id=?";

	// ��֯�������Դ���(64)��֯�첼X1X2
	public static final String SELECT_PROPERTIESMAIN = "select * from propertiesmain where id=?";

	// ��֯�������Դ���(64)��ά���� X3X4
	public static final String SELECT_PROPERTIERFIBERCHARACTERS = "select * from propertiesfibercharacteristics where id=?";

	// ��֯�������Դ���(64)X7X8����᷽̽ʽ
	public static final String SELECT_PROPERTIESMIX = "select * from propertiesmixed where id=?";

	// ��֯�������Դ���(64)X9X10 01-19 99
	public static final String SELECT_PROPERTIESFABRIC = "select * from propertiesfabric where id=?";

	// ��֯�������Դ���(64)X11X12
	public static final String SELECT_PROPERTIESDYEING = "select * from propertiesdyeingandfinishing where id=?";

	// ����װ������ҵ��Ʒȫ�������ڹ���֪ʶ��2����(65)
	public static final String SELECT_MANUFACTURINGPROCESS = "select * from generalmanufacturingprocess where id=?";

	// ȫ����Ҫ��Ʒ����������2���� ���������Ʒ(712)
	public static final String SELECT_UNTRANSPORTABLEPRODUCT = "select * from untransportableproduct where id=?";

	// ȫ����Ҫ��Ʒ����������2���� ���������Ʒ��3λ(712)
	public static final String SELECT_LASTTHREEUNTRANSPORTABLEPRODUCT = "select * from untransportableproduct where length(id)=5 and id like ?";

	// ��·��ͨ��Ϣ�ɼ���Ϣ���������(77)
	public static final String SELECT_TRAFFICINFORMATIONCOLLECTION = "select * from trafficinformationcollection where firstcode=? and secondcode=?";

	// �̲���ҵ����ͳ�����Ԫ��2���� ���뼯(202)
	public static final String SELECT_TABACCOORGANIZATION = "select * from tobaccoorganization where id=?";

	// ��Ҷ�����5������Ҷ��ɫ����(204)
	public static final String SELECT_TABACCOLEAFCOLOR = "select * from tobaccoleafcolor where id=?";

	// ��Ҷ�����2������Ҷ��̬����(207)
	public static final String SELECT_TABACCOLEAFFORM = "select * from tobaccoleafform where id=?";

	// ��Ҷ�����1������Ҷ���������(208)
	public static final String SELECT_TABACCOLEAFCLASS = "select * from tobaccoleafclass where id=?";

	// ��ͯ�����״����(213)
	public static final String SELECT_CHILDRENEXCREMENT = "select * from childrenexcrement where id=?";

	// ���Ƶ�ʴ���(214)
	public static final String SELECT_DRINKINGFREQUENCY = "select * from drinkingfrequency where id=?";

	// ����������(214)
	public static final String SELECT_DRINKINGCLASS = "select * from drinkingclass where id=?";

	// ����Ƶ�ʴ���(214)
	public static final String SELECT_PHYSICALACTIVITYFREQUENCY = "select * from physicalactivityfrequency where id=?";

	// ������ֹ��ʽ�����(215)
	public static final String SELECT_TERMINATIONOFPREGNENCY = "select * from terminationofpregnancy where id=?";

	// ���䷽ʽ����(215)
	public static final String SELECT_MODEOFPRODUCTION = "select * from modeofproduction where id=?";

	// ����ص�������(215)
	public static final String SELECT_DILIVERYPLACE = "select * from deliveryplace where id=?";

	// ������Ϣ���Ԫֵ������17���֣��������(218)
	public static final String SELECT_HEALTHSUPERVISIONOBJECT = "select * from healthsupervisionobject where id=?";

	// ��ͨ���ߴ���(219)
	public static final String SELECT_COMMUNICATIONCODE = "select * from communicationmediacode where id=?";

	// ����ල����Ա����������(220)
	public static final String SELECT_HYGIENEAGENCYPERSONNEL = "select * from hygieneagencypersonnel where id=?";

	// ����ල��ְ��������(220)
	public static final String SELECT_WORKERHEALTHSUPERVISION = "select * from workerhealthsupervision where id=?";

	// �����������һ����¼
	public static final String SELECT_FUNERALSERVICE = "select * from funeralservice where id=?";

	// ������ʩ�����һ����¼
	public static final String SELECT_FUNERALFACILITIES = "select * from funeralfacilities where id=?";

	// ������ʩ��Ʒ��һ����¼
	public static final String SELECT_SUPPLIES = "select * from funeralsupplies where id=?";

	//268���뵳�����
	public static final String SELECT_PORTTARIFF268 = "select * from TheCenteralPartyCommitte where code=?";
	//270��Ȼ�ֺ�
	public static final String SELECT_PORTTARIFF270 = "select * from Naturaldisaster where code=?";
	//275������ҵ����
	public static final String SELECT_PORTTARIFF275 = "select * from Logisticsoperation where code=?";
	//276������Ʒ
	public static final String SELECT_PORTTARIFF276 = "select * from Wasteproducts where code=?";
	// 280���뵳�����
	public static final String SELECT_PORTTARIFF280 = "select * from TheCenteralPartyCommitte where code=?";
	// 281-�����鱦��ʯ��������Ʒ���������Ʒ��� �����ݿ�
	public static final String SELECT_PORTTARIFF281 = "select * from JadejewelryClass where code=?";
	// 281-�����鱦��ʯ���������ʷ��������Ʒ��� �����ݿ�
	public static final String SELECT_PORTTARIFFMa281 = "select * from JadejewelryMaterialclassif where code=?";
	// 282-������Ϣ��ȫ����������Ʒ��� �����ݿ�
	public static final String SELECT_PORTTARIFFMa282 = "select * from InformationSafe where code=?";
	// 284-������ᾭ��Ŀ�����ʹ���� �����ݿ�
	public static final String SELECT_PORTTARIFFMa284 = "select * from goalsocialeconomic where code=?";
	// 287_������Ϣ����
	public static final String SELECT_PORTTARIFFMa285 = "select * from LogisticsInf where code=?";
	// 287_��װ����
	public static final String SELECT_PORTTARIFFMa287 = "select * from clothesclass where code=?";
	// 288_��װ���ַ���
	public static final String SELECT_PORTTARIFFMa288 = "select * from ClothesName where code=?";
	// 191_ҽҩ��е����
	public static final String SELECT_PORTTARIFFMa191 = "select * from Pharmacequipment where code=?";

	//395_�����Ϣ����
	public static final String SELECT_PORTTARIFF395 = "select * from FireInfomation  where code=?";
	//399_�����Ϣ����
	public static final String SELECT_PORTTARIFF399 = "select * from FireInfowatersupply  where code=?";
	//403_�����Ϣ����
	public static final String SELECT_PORTTARIFF403 = "select * from FireInfocamp  where code=?";
	//409_�����Ϣ����
	public static final String SELECT_PORTTARIFF409 = "select * from FireInfotainass  where code=?";	
	
	
	
	
	
	//410_�ɲ�ְλ��ƴ���
	public static final String SELECT_OFFICIALPOSITION="select *from officialposition where code=?";
	//û��ƥ��ɹ��κ�һ�ֱ�ʶ
	public static final int NO_ID_MATCHED = 0;
	// CoastalAdminAreaId
	public static final String SELECT_COASTALADMINAREAID = "select * from CoastalAdminAreaId where id=?";

	// infectiousDieases code
	public static final String SELECT_INFECTIOUSDISEASES = "select * from Infectiousdiseases where id=?";

	// WirtschaftsTypCode
	public static final String SELECT_WIRTSCHAFTSTYPCODE = "select * from WirtschaftsTypCode where id=?";

	// ũҩ������Ƽ����루305��
	public static final String SELECT_PESTICIDECODE = "select * from PesticideFormulationCode where code=?";

	// ���ó��ߴ���루306��
	public static final String SELECT_PASSENGERCARCODE = "select * from passengerCarCode where code=?";

	// �����������������ƹ���309��
	public static final String SELECT_GEOGRAPHICALCODE = "select * from GeographicalCode where code=?";

	// ���ó�����䴬����Ƽ��������ԭ��312��
	public static final String SELECT_INTERNATIONALSHIP = "select * from InternationalShipCode where code=?";
	
	//wt
	public static final String SELECT_ROADTRANSPORTATION21 = "select * from roadtransportation21 where code=?";
	
	public static final String SELECT_ROADTRANSPORTATION22 = "select * from roadtransportation22 where code=?";

	public static final String SELECT_ROADTRANSPORTATION32 = "select * from roadtransportation32 where code=?";

	public static final String SELECT_ROADTRANSPORTATION50 = "select * from roadtransportation50 where code=?";
	
	public static final String SELECT_ROADTRANSPORTATION53 = "select * from roadtransportation53 where code=?";

	public static final String SELECT_ROADTRANSPORTATION5 = "select * from roadtransportation5 where code=?";
	
	public static final String SELECT_ROADTRANSPORTATION41 = "select * from roadtransportation41 where code=?";
	
	public static final String SELECT_ROADTRANSPORTATION63 = "select * from roadtransportation63 where code=?";
	
	public static final String SELECT_ROADTRANSPORTATION64 = "select * from roadtransportation64 where code=?";
	
	public static final String SELECT_HIGHWAYTRANSPORTATION4B1 = "select * from highwaytransportation4b1 where code=?";

	public static final String SELECT_HIGHWAYTRANSPORTATION4B7 = "select * from highwaytransportation4b7 where code=?";

	public static final String SELECT_HIGHWAYTRANSPORTATION4B9 = "select * from highwaytransportation4b9 where code=?";

	public static final String SELECT_HIGHWAYTRANSPORTATION4C3 = "select * from highwaytransportation4c3 where code=?";

	public static final String SELECT_PORTTARIFF3 = "select * from porttariff3 where code=?";

	public static final String SELECT_PORTTARIFF4 = "select * from porttariff4 where code=?";

	public static final String SELECT_PORTTARIFF9 = "select * from porttariff9 where code=?";

	public static final String SELECT_PORTTARIFF25 = "select * from porttariff25 where code=?";

	public static final String SELECT_PORTTARIFF26 = "select * from porttariff26 where code=?";

	public static final String SELECT_PORTTARIFF10 = "select * from porttariff10 where code=?";

	public static final String SELECT_MACHINERY2 = "select * from machinery2 where code=?";

	public static final String SELECT_HIGHWAYMAINTENANCE4 = "select * from highwaymaintenance4 where code=?";

	public static final String SELECT_HIGHWAYMAINTENANCE3 = "select * from highwaymaintenance3 where code=?";

	public static final String SELECT_MACHINERY3 = "select * from machinery3 where code=?";

	public static final String SELECT_MACHINERY4 = "select * from machinery4 where code=?";

	public static final String SELECT_MACHINERY5 = "select * from machinery5 where code=?";

	public static final String SELECT_MACHINERY6 = "select * from machinery6 where code=?";

	public static final String SELECT_MACHINERY7 = "select * from machinery7 where code=?";

	public static final String SELECT_MACHINERY8 = "select * from machinery8 where code=?";

	public static final String SELECT_MACHINERY9 = "select * from machinery9 where code=?";

	public static final String SELECT_MACHINERY10 = "select * from machinery10 where code=?";

	public static final String SELECT_HIGHWAYTRANSPORTATION4C6 = "select * from highwaytransportation4c6 where code=?";

	public static final String SELECT_WATERWAYTRANSPORTATION = "select * from waterwaytransportation where code=?";

	public static final String SELECT_HIGHWAYTRANSPORTATION4B10 = "select * from highwaytransportation4b10 where code=?";

	public static final String SELECT_HIGHWAYTRANSPORTATION = "select * from highwaytransportation where code=?";

	public static final String SELECT_ROADTRANSPORTATION60 = "select * from roadtransportation60 where code=?";
	
	public static final String SELECT_PORT = "select * from port where code=?";

	public static final String SELECT_SECURITYACCOUNTERMENTS = "select * from securityaccounterments where code=?";

	
	public static final String SELECT_SPECIALVEHICLE = "select * from specialvehicle where code=?";

	public static final String SELECT_MOUNTAINRANGEANDPEAKNAME = "select * from mountainrangeandpeakname where code=?";

	public static final String SELECT_CIVILAVIATION = "select * from civilaviation where code=?";


	// ƥ��ɹ�һ�ֱ�ʶ
	public static final int ONE_ID_MATCHED = 1;

	// ������ϸ��Ϣ
	public static final String SELECT_IDDETAIL = "select * from iotid join iotdetail on iotdetail.did=iotid.id and iotid.id=?";

	// ǰ��ҳ����ʾ��׼��ƶ����ַ����󳤶�
	public static final int DISPLAYLENGTH = 9;

	// ���URL��ַ
	public static String getURLParam(String paramName) {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String url = request.getContextPath();
		String parameter = request.getParameter(paramName);
		return parameter;
	}

	// �޸ı�׼IDΪ���
	public static HashMap<String, Double> replaceIotId(
			HashMap<String, Double> map) {
		HashMap<String, Double> newMap = new HashMap<String, Double>();
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			RecoDao dao = new RecoDao();
			String name = dao.getIDDetail(key);
			newMap.put(name, map.get(key));
		}
		return newMap;
	}
}
