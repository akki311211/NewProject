package com.home.builderforms;



/**

 *   Class defining a customField

 *

 *@author     misam

 *@created    November 9, 2001

 */

public class CustomField {



    private String fieldName;



    private String dbColumnName;



    private String displayName;



    private String displayColumnWidth;



    private boolean orderBy;





    /**

     *  Constructor for the CustomField object

     *

     *@param  fieldName            Parameter

     *@param  dbColumnName         Parameter

     *@param  displayName          Parameter

     *@param  displayColumnWidth   Parameter

     *@param  orderBy              Parameter

     */

    public CustomField(String fieldName,

            String dbColumnName,

            String displayName,

            String displayColumnWidth,

            boolean orderBy) {

        this.fieldName = fieldName;

        this.dbColumnName = dbColumnName;

        this.displayName = displayName;

        this.displayColumnWidth = displayColumnWidth;

        this.orderBy = orderBy;

    }





    /**

     *  Constructor for the CustomField object

     *

     *@param  fieldName      Parameter

     *@param  dbColumnName   Parameter

     *@param  displayName    Parameter

     */

    public CustomField(String fieldName,

            String dbColumnName,

            String displayName) {

        this.fieldName = fieldName;

        this.dbColumnName = dbColumnName;

        this.displayName = displayName;

    }





    /**

     *  Gets the fieldName attribute of the CustomField object

     *

     *@return    The fieldName value

     */

    public String getFieldName() {

        return fieldName;

    }





    /**

     *  Gets the dbColumnName attribute of the CustomField object

     *

     *@return    The dbColumnName value

     */

    public String getDbColumnName() {

        return dbColumnName;

    }





    /**

     *  Gets the displayName attribute of the CustomField object

     *

     *@return    The displayName value

     */

    public String getDisplayName() {

        return displayName;

    }





    /**

     *  Gets the displayColumnWidth attribute of the CustomField object

     *

     *@return    The displayColumnWidth value

     */

    public String getDisplayColumnWidth() {

        return displayColumnWidth;

    }





    /**

     *  Gets the orderBy attribute of the CustomField object

     *

     *@return    The orderBy value

     */

    public boolean isOrderBy() {

        return orderBy;

    }



    public boolean equals(Object o)

    {

		return (o instanceof CustomField && ((CustomField)o).getFieldName().equals(this.fieldName) );

	}



}

