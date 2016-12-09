/**
 * @author abhishek gupta
 * @Date 2 May 2012
 */
package com.appnetix.app.util.database;

import com.appnetix.app.util.SequenceMap;

public class DocumentTable {
    private SequenceMap msTableFieldMap			= null;
    private String name = null;
    public DocumentTable(String name, SequenceMap msFieldMap) {
        this.name = name;
        this.msTableFieldMap	= msFieldMap;
    }

    public String getFieldName() {
        return name;
    }
    public SequenceMap getFieldMap() {
        return msTableFieldMap;
    }
}