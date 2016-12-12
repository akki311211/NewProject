package com.home.builderforms;

import com.home.builderforms.BuilderFormDataDAO;
import com.home.builderforms.BuilderFormFieldsDAO;
import com.home.builderforms.BeanHandler;

/**
 *  The BuilderFormsMgr, for maintaining references to BuilderFormFieldsDAO and BuilderFormDataDAO 
 *
 *@author abhishek gupta
 *@created   Nov 14, 2011
 */
public class BuilderFormsMgr {

    public BuilderFormFieldsDAO getBuilderFormFieldsDAO() {
        return BeanHandler.getBean("base-builderformfieldsdao", BuilderFormFieldsDAO.class);
    }

    public BuilderFormDataDAO getBuilderFormDataDAO() {
        return BeanHandler.getBean("base-builderformdatadao", BuilderFormDataDAO.class);
    }
}
