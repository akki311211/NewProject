/**
 * @author abhishek gupta
 * @Date 2 May 2012
 */
package com.appnetix.app.util.database;

import com.appnetix.app.util.SequenceMap;

public class DocumentMap {
    private SequenceMap msDocFieldMap			= null;
    private String name = null;
    public DocumentMap(String name, SequenceMap msDocFieldMap) {
        this.name = name;
        this.msDocFieldMap	= msDocFieldMap;
    }

    public String getFieldName() {
        return name;
    }
    public SequenceMap getDocumentFieldMap() {
        return msDocFieldMap;
    }
}