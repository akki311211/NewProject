package com.appnetix.app.components.locationmgr.manager;

import com.appnetix.app.components.locationmgr.manager.dao.LocationsDAO;
import java.sql.*;
import com.appnetix.app.components.locationmgr.manager.dao.*;
import com.home.builderforms.Constants;
import com.home.builderforms.information.Info;
import com.appnetix.app.control.web.base.BeanHandler;

public class LocationMgr {

    private static LocationMgr _instance = new LocationMgr();

    private LocationMgr() {
    }

    public static LocationMgr newInstance() {
        return _instance;
    }

    public LocationsDAO getLocationsDAO() {
        return BeanHandler.getBean("common-locationsdao", LocationsDAO.class);
    }
}
