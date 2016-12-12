package com.appnetix.app.components.masterdatamgr.manager;

import com.appnetix.app.components.masterdatamgr.manager.dao.*;
import com.appnetix.app.control.web.base.BeanHandler;

public class MasterDataMgr {

    private static MasterDataMgr _instance = new MasterDataMgr();

    private MasterDataMgr() {
    }

    public static MasterDataMgr newInstance() {
        return _instance;
    }

    /**
     *  Gets the masterDataDAO attribute of the MasterDataMgr object
     */
    public MasterDataDAO getMasterDataDAO() {
        return BeanHandler.getBean("base-masterdatadao", MasterDataDAO.class);
    }
}
