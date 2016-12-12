/**
 * @author abhishek gupta
 * @Date Dec 2012
 */
package com.home.builderforms;

import java.util.HashMap;

import com.home.builderforms.SequenceMap;

public class QuestionOption {
    private SequenceMap msTableFieldMap			= null;
    private String name = null;
    public QuestionOption(String name, SequenceMap msFieldMap) {
        this.name = name;
        this.msTableFieldMap	= msFieldMap;
    }

    public String getFieldName() {
        return name;
    }
    public SequenceMap getFieldMap() {
        return msTableFieldMap;
    }
    public String getKeyValue(String key) {
        return (String)msTableFieldMap.get(key);
    }
    public SequenceMap getAttributesMap() {
        return msTableFieldMap;
    }
}