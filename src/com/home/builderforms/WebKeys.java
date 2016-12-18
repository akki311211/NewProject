package com.home.builderforms;

/**
 *  This class contains all the keys that are used to store data in the
 *  different scopes of web-tier. These values are the same as those used in the
 *  JSP pages (useBean tags).
 *
 *@author     misam
 *@created    November 9, 2001
 */
public class WebKeys {

    /**
     *   Field
     */
    public static final String ModelManagerKey						= "mm";
    /**
     *   Field
     */
    public static final String LanguageKey							= "language";

    /**
     *   Field
     */
    public static final String ScreenManagerKey						= "screenManager";
    /**
     *   Field
     */
    public static final String RequestProcessorKey					= "rp";
    /**
     *   Field
     */
    public static final String SystemVariableManagerKey				= "systemVariableManger";

    public static final String WebControllerKey						= "webController";
    /**
     *   Field
     */
    public static final String CurrentScreen							= "currentScreen";
    /**
     *   Field
     */
    public static final String MissingFormDataKey						= "missingFormData";
    /**
     *   Field
     */
    public static final String ReturnMessageKey						= "returnMessage";
    /**
     *   Field
     */
    public static final String ErrorPageKey							= "errorPage";
    /**
     *   Field
     */
    public static final String ErrorMessageKey						= "errorMessage";

    public static final String UserWebImplKey							= "user";

    public static final String selectedURLKey							= "selectedURL";

    public static final String UserIDKey								= "userIDKey";

    public static final String UserNameKey							= "userNameKey";
    public static final String UserInfoKey							= "userInfoKey";
	public static final String UserLevelKey							= "userLevel";
	public static final String UserNoKey								= "userNo";

	public static final String FranchiseeNoKey						= "franchiseeNo";

    public static final String SummaryDisplayFieldsKey				= "summaryDisplayFields";
    public static final String DetailsDisplayFieldsKey				= "detailsDisplayFields";
    public static final String ReturnPageKey							= "returnPage";

	public static final String PreferencesKey							= "preferences";

	public static final String TableMappingsKey						= "tableMappings";

	public static final String TableVsUrlMappingsKey					= "tableVsUrlMappings";
	
	public static final String TableFieldMappingsKey					= "tableFieldMappings";

	public static final String SQLQueriesMapKey						= "sqlQueriesMap";

    public static final String AddParameterPresentKey					= "addParameterPresent";

	public static final String AddParameterKey						= "addParameter";

	public static final String DisplayDataKey							= "displayData";

	//APPLICATION SPECIFIC KEYS
	public static final String SalesReportFactoryKey					= "salesReportFactory";
	public static final String ModulesKey								= "modules";

	public static final String FinancialWebImplKey					= "financial";
	public static final String UPSBaseWebImplKey					= "upsbase";
	public static final String UPSRoyaltyWebImplKey				= "upsroyalty";

	public static final String StoreChecklistWebImplKey				= "storeChecklist";
	public static final String StoreEquipmentChecklistWebImplKey				= "storeEquipmentChecklist";
	public static final String StoreDocumentChecklistWebImplKey				= "storeDocumentChecklist";
	public static final String UsersWebImplKey				= "users";
	public static final String TriggerWebImplKey						= "trigger";
	public static final String CustomFormWebImplKey					= "customizeForm";
}
