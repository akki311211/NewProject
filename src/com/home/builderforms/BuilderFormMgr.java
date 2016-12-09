package com.home.builderforms;

import com.home.builderforms.*;
import com.home.builderforms.BeanHandler;

/**
 * The BuilderFormsMgr, for maintaining references to BuilderFormFieldsDAO and BuilderFormDataDAO
 *
 * @author abhishek gupta
 * @created 2 Nov, 2011
 */
public class BuilderFormMgr {

    private static BuilderFormMgr _instance = new BuilderFormMgr();

    public static BuilderFormMgr newInstance() {
        return _instance;
    }

    public BuilderFormFieldsDAO getBuilderFormFieldsDAO() {
        return BeanHandler.getBean("base-builderformfieldsdao", BuilderFormFieldsDAO.class);
    }

    public BuilderFormDataDAO getBuilderFormDataDAO() {
        return BeanHandler.getBean("base-builderformdatadao", BuilderFormDataDAO.class);
    }

    public BuilderFormDAO getBuilderFormDAO() {
        return BeanHandler.getBean("base-builderformdao", BuilderFormDAO.class);
    }

    public BuilderFormDAOforCm getBuilderFormDAOforCm() {
        return BeanHandler.getBean("base-builderformdaoforcm", BuilderFormDAOforCm.class);
    }
}
