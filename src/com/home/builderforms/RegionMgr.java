package com.appnetix.app.components.regionmgr.manager;

import com.appnetix.app.components.regionmgr.manager.dao.CountriesDAO;
import com.appnetix.app.components.regionmgr.manager.dao.RegionsDAO;
import com.appnetix.app.control.web.base.BeanHandler;

public class RegionMgr {

    private static RegionMgr _instance = new RegionMgr();

    private RegionMgr() {
    }

    public static RegionMgr newInstance() {
        return _instance;
    }

    /**
     *  Gets the regionDAO attribute of the regionMgr object
     *
     *@return    The CountriesDAO value
     */
    public CountriesDAO getCountriesDAO() {
        return BeanHandler.getBean("common-countriesdao", CountriesDAO.class);
    }

    /**
     *  Gets the regionDAO attribute of the regionMgr object
     *
     *@return    The RegionDAO value
     */
    public RegionsDAO getRegionsDAO() {
        return BeanHandler.getBean("common-regionsdao", RegionsDAO.class);
    }
}
