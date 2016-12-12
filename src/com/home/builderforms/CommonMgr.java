package com.home.builderforms;

import com.home.builderforms.BeanHandler;
import com.home.builderforms.CommonSyncFieldManipulator;
import com.home.builderforms.CommonTabularSectionManipulator;


public class CommonMgr {

	private static CommonMgr _instance = new CommonMgr();
	
	private CommonMgr()
	{	
	}
	
	public static CommonMgr newInstance()
	{
		return _instance;
	}
	
	//P_Enh_Sync_Fields starts
	public CommonSyncFieldManipulator getCommonSyncFieldManipulatorObj() {
		return BeanHandler.getBean("common-commonSyncFieldManipulator", CommonSyncFieldManipulator.class);
	}
	//P_Enh_Sync_Fields ends
	//P_Enh_FormBuilder_Tabular_Section starts
	public CommonTabularSectionManipulator getCommonTabularSectionManipulatorObj() {
		return BeanHandler.getBean("common-commonTabularSectionManipulator", CommonTabularSectionManipulator.class);
	}
	//P_Enh_FormBuilder_Tabular_Section ends
	/*public CommonFsDAO getCommonFsDAO() 
	{
		return BeanHandler.getBean("common-commonfsdao", CommonFsDAO.class);
	}
	
	public CommonCmDAO getCommonCmDAO()
	{
		return BeanHandler.getBean("common-commoncmdao", CommonCmDAO.class);
	}
	
	public CommonFinancialsDAO getCommonFinancialsDAO()
	{
		return BeanHandler.getBean("common-commonfinancialsdao",CommonFinancialsDAO.class);
	}
	
	public CommonSupplierDAO getCommonSupplierDAO()
	{
		return BeanHandler.getBean("common-commonsupplierdao", CommonSupplierDAO.class);
	}
	
	public CommonAdBuilderDAO getCommonAdBuilderDAO()
	{
		return BeanHandler.getBean("common-commonadbuilderdao", CommonAdBuilderDAO.class);
	}
	
	public CommonTrainingDAO getCommonTrainingDAO()
	{
		return BeanHandler.getBean("common-commontrainingdao", CommonTrainingDAO.class);
	}
	
	public CommonFimDAO getCommonFimDAO()
	{
		return BeanHandler.getBean("common-commonfimdao", CommonFimDAO.class);
	}
	
	public CommonDAO getCommonDAO()
	{
		return BeanHandler.getBean("common-commondao",CommonDAO.class);
	}
	//SYSTEM_WIDE_SEARCH Shivam starts
	public SystemSearchDAO getSystemSearchDAO()
	{
		return BeanHandler.getBean("common-systemsearchdao",SystemSearchDAO.class);
	}
	//SYSTEM_WIDE_SEARCH Shivam ends
	public TwillioSmsDao getTwillioSmsDAO()
	{
		return BeanHandler.getBean("common-twillioSmsdao",TwillioSmsDao.class);
	}
	
	public VzaarDAO getVzaarDAO()
	{
		return BeanHandler.getBean("common-vzaardao", VzaarDAO.class);
	}*/
}
