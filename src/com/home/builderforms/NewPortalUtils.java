/*
 -------------------------------------------------------------------------------------------------
 Version No.		Date		By			Function Changed          Comments
 -------------------------------------------------------------------------------------------------
 P_FS_B_21504 11/12/06	Ashish Negi  create a new function  when send a template mail or campaign 															or lead email through sent mail mail 															the mail is not in well formed 
 so added a default image with email   
 P_FS_E_101	1/6/2007		Esha Rastogi	Click Here Changed 
 value modified To webpage url
 P_FS_E_102	1/9/2007		Esha Rastogi	display url added in processmailtemplate() n processmailtemplate1()
 --------------------------------------------------------------------------------------------*/
/*P_CM_B_21378	12/20/2006	Kulmeet     change	contacts associated with the Deleted Franchisee User are assigned to other user

 // D_A_E_0001	04/01/2007	 GauravG	New functions added		Functions to check default lead owner, zip code locator, task count, lead count, cm count

 P_CM_B_23013	28/03/2007	Binu Tomar  Function getDataCount() changes	 To change count quueries of store opener itmes. 
 P_FS_E_50107     5/1/2007	Ravindra								Adding regional user in assigned to Combo for FS.
 P_FS_E_61407     6/14/2007	Ravindra	getCallID()							Call Status must come from CALL_STATUS table.
 P_FS_E_70207	7/2/2007	Ravindra Verma							Chnages regarding configuration of duplicate criteria.
 P_FS_E_129      07/09/2007	Kulmeet      processAdditionalMailTemplate1()    adding two new keywords (OWNER_TITLE,Date)		
 P_FS_E_26189	8/6/2007	Ravindra								Country should be in Site Locations.	
 P_SO_B_27095  07/09/07          Suchita         getCorporateUserCounts()        to change count query for corporate user
 P_SO_E_111    16/11/2007        Suchita Agrawal        getAllCorpAndRegUser(),and added new fun getRegionalUserCounts()   to count checklist before deletion/deactivation of regional user
 P_A_E_DELETE-DEACTIVATE 30/09/2008 Chetan Sharma       Added getAllFranchiseeUsers() to get all franchise users of particular frachise.
 P_FS_E_Group_for_serach   on 22 Oct,2008 Sanjeev K For getting all group
 P_A_E_AUTOMATIC_TASKS 20/10/2008 Chetan Sharma       Added setAutomaticTask() to add tasks based on defined Rules.
 P_FS_B_53948	23/Dec/2009	Saurabh Sinha           Extra quote was appearing and map didn't get closed
 DB_FIN_Bug_55186	05Jan2010	YogeshT			Bug_55186		added new method
 P_FS_B_55673           12/01/2010       Anuj Goel           processMailTemplate()      For issue related to bug id 55673
 P_E_NewDashBoard		18-Jan-2010          Ganesh   Enchancement                 	For New DashBoard
 P_FS_Enh_12Apr10       12/04/2010    Vikram Raj             added getDateTime method to get the date and time according to the parameter send 
 P_FS_B_56785           14/04/2010    Vikram Raj           replaceAllWebPage method overridden /comment as per code review
 P_FDD_Enh_10203040     23/04/2010    Vikram Raj           modified getFDDownerinfo method to get the email id of the lead owner

 P_FS_E_26082010        28/08/2010   Vikram Raj            modified getLeadSourceCategory method to change the query based on contact type configuration
 P_E_20100914       14/09/10      Ankit Saini                Added "Assign for owner/Territory" to SetUp Campaign Triggers

 P_FS_E_26082010        28/08/2010    Vikram Raj            modified getLeadSourceCategory method to change the query based on contact type configuration
 P_FS_E_13092010        13/09/2010    Vikram Raj            added addStringToBrokerTemplate,processContactsMailTemplate,processContactsMailTemplate1,getContactsSingleField,getBrokerEmail

 P_FDD_E_2209201002     22/09/2010    Vikram Raj            added getItem23ReceiptTemplateText method to get item23receipt configured through admin
 P_FDD_E_22092010       22/09/2010    Vikram Raj            modified insertPage method to send composite schedule id on the click here link in the pdf fdd
 P_B_FS_BB_64137		04/10/2010	Alok kumar												adding email campaign in contacts
 P_ADM_E_0706            13 Oct 2010     Vartika Joshi       added remote ip and created user logs in user status logs link
 P_FIM_C_ROYALTY_FRQUENCY  20 oct 2010       Vartika Joshi               change royalty frequency text box to combo in custom report and export.
 P_AdBuilder_B_67548    20 dec 2010	Sachin Shukla        modified the function getOrderSequence()
 P_E_LeadMapPreview		17dec2010		Vivek Maurya		Enh		functions added for preview in map 			
 * P_ENH_FS_SEARCH_EXACT   28 Dec 2010      Narotam Singh   getSearchNames()
 * P_FS_B_69075			24 Jan 2011		Vivek Maurya		Bug			removed CAMPAIGN_ADDED_FOR		--getContactCampaignInfo()
 * P_FIN_B_70346		07 Feb 2011		Vivek Maurya		Bug			--added method getAgreementID() to get VERSION_ID
 P_Admin_B_70769                  11 Feb 2011             Nishant Prabhakar       Bug 70769                 added method formatPhoneNo to format phone no
 P_FS_B_68498			14Feb2011		Vivek Maurya		Bug											--added method getLeadLocations() for preferred locations
 P_FS_B_70906			16Feb2011		Vivek Maurya		Bug			getFSOwnerSMap()
 P_FIN_B_69561			17Feb2011		Vivek Maurya		Bug
 P_FIN_B_70301			28Feb2011		Vivek Maurya		Bug			--getAgreementItems()			--modified for space between label and $			
 * D_CM_B_71046         11/03/2011      vishal Lodha            for related to  bug number 71046 
 * DemoBuild_B_FS_71985	15-04-2011	Pawan Kumar	issue related to bug 71985
 * P_E_FS_DuplicateCriteria	25Apr2011	Vivek Maurya		Enh			--added methods for Duplicate Criteria
 * P_FIN_B_73264		26Apr2011		Vivek Maurya		Bug			--getAFItemMap()		--modified for calculation of Area Franchisee Fee
 P_ADMIN_B_63238		02May2011		Himanshi Gupta		Bug		---replaceCharacter()   Original Keyword getting replaced also when keyword are modified for English language
 P_FIN_B_74016			16May2011		Vivek Maurya		Bug			--added method getUploadedLineItems to retieve line item ids 
 BBEH-20110408-055 		24/06/2011        Nishant Prabhakar         for Export Settings
 P_SEARCH_APOS                24/05/2011              Narotam Singh       
 P_FS_ENH_45678       26 May 2011     Jyotsna Tiwari
 P_E_FS_RecruitmentMetrics	03Jun2011	Vivek Maurya		Enh			--added method getWhereQueryForRecruitmentMetrics 
 ACC-20101113-004               04 June,2011            Deep                    added new day feild on add task trigger page. 
 //BBEH-20110408-055_Download 7 June 2011     Nishant Prabhakar       for downloading html and zip file for template
 * B_71719   08/07/2011       Narotam Singh
 * P_DASHBOARD_B_75358	11Jun2011		Vivek Maurya		Bug			--getWhereQueryForRecruitmentMetrics	--modified for data of last 12 months

 * P_FIM_ENH_OUTLUKMAILBODY       11/june/2011             Ankit Saini               for showing outlook mail body in detailed history

 * P_FIN_B_75878		15Jun2011		Vivek Maurya		Bug			--getAgreementItems		--modified for Area Fee(s),when AF payment is yes/no
 * P_B_FS_75353         15 June 2011    Jyotsna Tiwari            Check for when no entry in MODULE_USER_PAGE_MAPPING correponding to user	
 * 
 * P_DASHBOARD_B_QueryOptimization	16Jun2011		Omindra Rana		Optimization
 *P_FS_B_71120          24/06/2011       Nishant Prabhakar         Added method getUsersDeactivated() to get all users whether they deactivated or deleted
 * B_CM_77032               09-07-2011                   Vishal Lodha       For more detail please chelck bug number 77032
 * P_ADM_ROLES_B_77395    22/07/2011     Veerpal Singh             skipRole()           While creating a new role documents related previliges should not come if scheduler is implemented
 --------------------------------------------------------------------------------------------------------------------------------
 */

package com.home.builderforms;



import com.home.builderforms.base.BaseNewPortalUtils;
import com.home.builderforms.information.Info;
import com.home.builderforms.sqlqueries.ResultSet;
import org.apache.log4j.Logger;

/**
 * 
 * Name - NewPortalUtils.java
 * 
 * Directory position - com.home.builderforms.financials
 * 
 * 
 * author Pritish
 * 
 * @version 1.1.1.1,
 * 
 *          date 15/09/2003
 * 
 *          List/Names of JSPs referenced by this JSP
 * 
 *          budgetform.jsp
 * @Revision History :
 * @author : Bipul kumar/ 1.37/ march 29 2006/ Email template changes
 *         ------------
 *         ----------------------------------------------------------
 *         ----------------------------------------------------- Version No.
 *         Date By Against Methods Changed Comments
 *         ------------------------------
 *         ----------------------------------------
 *         ------------------------------------------------------ P_FS_E_RRIR
 *         12/12/06 Y.NagaRaja Round Robbing getOwnerNames(); To assign Round
 *         Robin users for Regions in Region P_FS_B_35775 28/07/2008 Ravindra
 *         Verma Date Format was wrong in mail for $DATE$ keyword. P_FO_B_39641
 *         14/10/2008 Vivek Sharma Reassign custom profile users at the time of
 *         deactivate/delete any users. P_SUPPORT_B_41965 19/12/2008 Abhishek
 *         Rastogi getReportedId() to get the ID of the Ticket Reporter
 *         P_FS_E_1000003 8-july-2009 GV Giving flexibilty to client two set 2
 *         different Duplicate criteria BB63_P_E_StatusCategory 24/12/2009
 *         Ganesh Categorizing each status under P_B_37200 28-Dec-2009 Santosh
 *         Gupta getAreaIdForIntContry() Area Id not updating correctly on the
 *         basis of zip DemoBuild_E_Admin 04-Jan-2010 Santosh Gupta
 *         getConfigureWebFormDisclaimerInfo() Added new method
 *         getConfigureWebFormDisclaimerInfo() to fetch all the data related to
 *         Captcha and Web Form Disclaimer P_ADMIN_B_55682 11/01/2010 Anuj Goel
 *         For issue related to bug id 55682 P_FS_B_55705 11/01/2010 Nikhil
 *         Verma For issue related to bug id 55705 P_B_55840 14-Jan-2010 Santosh
 *         Gupta getElectronicDetailInfo() Added new method
 *         getElectronicDetailInfo to fetch the details of View Electronic
 *         Details link P_E_SO_45354 31-mar-2010 Nikhil Verma skiprole()
 *         additionalRole are displaying in configuration
 *         ------------------------
 *         ----------------------------------------------
 *         ---------------------------------------------- Version No. Date By
 *         Against Function Changed Comments
 *         ------------------------------------
 *         ----------------------------------
 *         ---------------------------------------------- P_AD_E_70023
 *         10-Oct-2009 Saurabh Sinha If new document is not loaded then the page
 *         would take less time to get executed & changes made get reflected. 2.
 *         i)"Save" button is changed to "Save & Exit".ii) On clicking
 *         "Arrange Field Sequence" the value in combo appears its in format
 *         (textFieldValue [textFieldName]). 3. ArtWork has an action to change
 *         Sequence directly and modify value of its field. 4. On clicking
 *         continue button, both functionality (apply and continue) will be
 *         performed.. BB_FS_E_1905 19-may-2010 Ganesh webpage url link with
 *         autopopulated content through mails BB_59240 27-may-2010 Ganesh
 *         population homephone and home extension
 *         STG_DashBoard_Query_Optimization 17-Aug-2010 Santosh Gupta Added new
 *         methods
 *         getTotalLeadCount(),get12MDeadLeadCount(),get12MKilledLeadCount
 *         (),getYTDTotalLeadCount(),getYTDKilledLeadCount() to get the lead
 *         count for different criteria
 * 
 *         //P_E_11OCT2010_Configure_Captivate 11-Oct-2010 Vivek Maurya Method
 *         will return captivate source id,area code for work phone,captivate
 *         campaign template id,captivate campaign template,captivate subject
 *         P_FS_ENH_45678 26 May 2011 Jyotsna Tiwari P_FS_ENH_02062011 2 June
 *         2011 Jyotsna Tiwari fs Tab selection through configure module start
 *         up New_Change_Base_Build 6 June 2011 Vikas Singh franchisee's photo
 *         show up on the upper left hand side in Smart Connect P_FS_B_75429
 *         10/06/2011 Nishant Prabhakar Added condition in
 *         getDefaultSubModuleURL
 *         ------------------------------------------------
 *         --------------------------------------------------------------------
 *         --
 *         --------------------------------------------------------------------
 *         ---------------------------------------------------------------
 */
/* Updated by kulmeet for removing exclaimation sign @20 july 06 */


public class NewPortalUtils extends BaseNewPortalUtils {
	public static Logger logger = com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil.getTenantLogger(NewPortalUtils.class);
	
	
}

