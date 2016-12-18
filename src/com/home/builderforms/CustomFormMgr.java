package com.home.builderforms;

import com.home.builderforms.CustomFormFieldsDAO;
import com.home.builderforms.CustomFormDAO;
import com.home.builderforms.BeanHandler;

/**
 *  The CustomFormsMgr, for maintaining references to CustomFormFieldsDAO and CustomFormDataDAO 
 *
 *@author     Pritish
 *@created    Aug 28, 2004
 *
 *REF NO:71BBFCNE01 ADDED BY NIPUN ON 22-JUNE-2011 INVOICE CUSTOM FIELDS
 *BBEH_CLUSTER_ENVIRONMENT  05/06/2013      Balvinder Mehla    Class is serialized For cluster Environment.
 */
public class CustomFormMgr implements java.io.Serializable {

    private static CustomFormMgr _instance = new CustomFormMgr();

    private CustomFormMgr() {
    }

    public static CustomFormMgr newInstance() {
        return _instance;
    }

    public CustomFormFieldsDAO getCustomFormFieldsDAO() {
        return BeanHandler.getBean("common-customformfieldsdao", CustomFormFieldsDAO.class);
    }

    /**
  * @return    The CustomFormDAO value
  */
    public CustomFormDAO getCustomFormDAO() {
        return BeanHandler.getBean("common-customformdao", CustomFormDAO.class);
    }

}
