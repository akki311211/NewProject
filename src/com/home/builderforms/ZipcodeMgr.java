package com.home.builderforms;

import com.home.builderforms.ZipCodeDAO;
import com.home.builderforms.AssignCorpDAO;
import com.home.builderforms.AssignZipCodeDAO;
import com.home.builderforms.BeanHandler;

public class ZipcodeMgr {

    private static ZipcodeMgr _instance = new ZipcodeMgr();

    private ZipcodeMgr() {
    }

    public static ZipcodeMgr newInstance() {
        return _instance;
    }

    public ZipCodeDAO getZipCodeDAO() {
        return BeanHandler.getBean("common-zipcodedao", ZipCodeDAO.class);
    }

    public AssignZipCodeDAO getAssignZipCodeDAO() {
        return BeanHandler.getBean("common-assignzipcodedao", AssignZipCodeDAO.class);
    }

    public AssignCorpDAO getAssignCorpDAO() {
        return BeanHandler.getBean("common-assigncorpdao", AssignCorpDAO.class);
    }
}
