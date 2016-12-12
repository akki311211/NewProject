/**
 * @author abhishek gupta
 * @Date 2 May 2012
 */
package com.home.builderforms;

import com.home.builderforms.SequenceMap;

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