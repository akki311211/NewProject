    /*
 *P_ADMIN_FDD_E16082010 16/08/2010 mukesh sundriyal add two new fields entry for CONFIGURE_FDD_TEMPLATE table.
 *P_E_LeadMapPreview	16/12/2010	Vivek Maurya	Enh		added for BUSINESS_NAME
 *P_E_FIN_UPGRADE		28/12/2010	Vivek Maurya	Enh
 BBEH-20110408-055 		24/06/2011  Nishant Prabhakar Enh       for Export Settings
 P_FRANONLINE_B_74847   26 May 2011  Shashank Kukreti		adding SCRAP_TITLE field for franbuzz FRAN_SCRAP table.
 *ENH_71BBFCNE12                  20June2011              Veerpal Singh                     Enh                    Upload Logo for invoice is one time activity
 */

package com.home.builderforms;

import com.home.builderforms.BaseFieldNames;
public class FieldNames extends BaseFieldNames
{
	public static String CAMPAIGN_START_TIME="campaignStartTime";   //BB-20150319-268(FIM Campaign Center) starts
    public static String TEMPLATE_START_TIME="templateStartTime";
    public static String REMAINING_TEMPLATE_START_TIME="remainingTemplateStartTime";
    public static String ARCHIVED_ON = "archivedOn";
	public static final String INCLUDE_IN_CAMPAIGN = "includeInCampaign";   //BB-20150319-268(FIM Campaign Center) ends
	public static String META_TITLE = "metaTitle";//added by vinay gupta for meta  title

	

}

