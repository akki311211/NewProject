/**
 * @AUDIT_ENHANCEMENT_CHANGES
 * @author abhishek gupta
 * @Date 28 Jan 2012
 */
package com.home.builderforms;

public class HeaderColumn implements Cloneable {
    private String name			= null;
    private String value		= null;
    private String isOwner		= "no";

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public HeaderColumn(String name, String value, String isOwner) {
        this.name		= name;
        this.value		= value;
        this.isOwner	= isOwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    public String getIsOwner() {
        return isOwner;
    }
}