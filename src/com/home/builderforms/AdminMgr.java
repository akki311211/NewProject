package com.home.builderforms;

import com.home.builderforms.FsLeadStatusDAO;
import com.home.builderforms.FsLeadSource2DAO;
import com.home.builderforms.UsersDAO;
import com.home.builderforms.FsLeadSource3DAO;

/**
 *  The AdminMgr, for maintaining references to adminDAO and other specific DAOs
 *
 *@author
 *@created    July 27, 2004
 */
public class AdminMgr {

    private static AdminMgr _instance = new AdminMgr();

    private AdminMgr() {
    }

    public static AdminMgr newInstance() {
        return _instance;
    }

    public FsLeadStatusDAO getFsLeadStatusDAO() {
        return BeanHandler.getBean("common-fsleadstatusdao", FsLeadStatusDAO.class);
    }

    public FsLeadSource2DAO getFsLeadSource2DAO() {
        return BeanHandler.getBean("common-fsleadsource2dao", FsLeadSource2DAO.class);
    }

    public FsLeadSource3DAO getFsLeadSource3DAO() {
        return BeanHandler.getBean("common-fsleadsource3dao", FsLeadSource3DAO.class);
    }

    public UsersDAO getUsersDAO() {
        return BeanHandler.getBean("common-usersdao", UsersDAO.class);
    }

}