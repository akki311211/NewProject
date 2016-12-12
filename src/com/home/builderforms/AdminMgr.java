package com.appnetix.app.components.adminmgr.manager;


import com.appnetix.app.components.addmaker.manager.dao.AdBuilderVendorDAO;
import com.appnetix.app.components.addmaker.manager.dao.EmailTemplatesDAO;
import com.appnetix.app.components.adminmgr.StoreDAO;
import com.appnetix.app.components.adminmgr.manager.dao.AdminConfigurDAO;
import com.appnetix.app.components.adminmgr.manager.dao.BillingShippingAddressDAO;
import com.appnetix.app.components.adminmgr.manager.dao.CallDAO;
import com.appnetix.app.components.adminmgr.manager.dao.ChangeUserTypeDAO;
import com.appnetix.app.components.adminmgr.manager.dao.CmContactHotActivityDAO;
import com.appnetix.app.components.adminmgr.manager.dao.CmContactMarketingCodeDAO;
import com.appnetix.app.components.adminmgr.manager.dao.CmWebServiceDAO;
import com.appnetix.app.components.adminmgr.manager.dao.ConfigSSODAO;
import com.appnetix.app.components.adminmgr.manager.dao.ConfigurationDAO;
import com.appnetix.app.components.adminmgr.manager.dao.ConfigureFileDAO;
import com.appnetix.app.components.adminmgr.manager.dao.ConfigureTabDAO;
import com.appnetix.app.components.adminmgr.manager.dao.DefaultEmailDAO;
import com.appnetix.app.components.adminmgr.manager.dao.DivisionsDAO;
import com.appnetix.app.components.adminmgr.manager.dao.ExchangeRateDAO;
import com.appnetix.app.components.adminmgr.manager.dao.FacebookConfigurDAO;
import com.appnetix.app.components.adminmgr.manager.dao.FimTransferDAO;
import com.appnetix.app.components.adminmgr.manager.dao.FsLeadMarketingCodeDAO;
import com.appnetix.app.components.adminmgr.manager.dao.FsLeadOwnerDAO;
import com.appnetix.app.components.adminmgr.manager.dao.FsLeadPopserverInfoDAO;
import com.appnetix.app.components.adminmgr.manager.dao.FsLeadQualificationDAO;
import com.appnetix.app.components.adminmgr.manager.dao.FsLeadRatingDAO;
import com.appnetix.app.components.adminmgr.manager.dao.FsLeadSource2DAO;
import com.appnetix.app.components.adminmgr.manager.dao.FsLeadSource3DAO;
import com.appnetix.app.components.adminmgr.manager.dao.FsLeadStatusDAO;
import com.appnetix.app.components.adminmgr.manager.dao.MessageSignatureDAO;
import com.appnetix.app.components.adminmgr.manager.dao.PLDAO;
import com.appnetix.app.components.adminmgr.manager.dao.SkillDAO;
import com.appnetix.app.components.adminmgr.manager.dao.UsersDAO;
import com.appnetix.app.components.adminmgr.manager.dao.WebServicesDAO;
import com.appnetix.app.components.adminmgr.manager.dao.memberProfileDao;
import com.appnetix.app.components.adminmgr.manager.dao.ADGroupsDAO;
import com.appnetix.app.control.web.base.BeanHandler;
import com.appnetix.app.components.adminmgr.manager.dao.SMSCampaignDAO;

/**
 *  The AdminMgr, for maintaining references to adminDAO and other specific DAOs
 *
 *@author
 *@created    July 27, 2004
 */
public class AdminMgr {

    private static AdminMgr _instance = new AdminMgr();

    private AdminMgr() {
    }

    public static AdminMgr newInstance() {
        return _instance;
    }

    public CmWebServiceDAO getCmWebServiceDAO() {
        return BeanHandler.getBean("common-cmwebservicedao", CmWebServiceDAO.class);
    }

    public WebServicesDAO getWebServicesDAO() {
        return BeanHandler.getBean("common-webservicesdao", WebServicesDAO.class);
    }

    public ConfigSSODAO getConfigSSODAO() {
        return BeanHandler.getBean("common-configssodao", ConfigSSODAO.class);
    }

    public FsLeadStatusDAO getFsLeadStatusDAO() {
        return BeanHandler.getBean("common-fsleadstatusdao", FsLeadStatusDAO.class);
    }

    public SkillDAO getSkillDAO() {
        return BeanHandler.getBean("common-skilldao", SkillDAO.class);
    }

    public FsLeadOwnerDAO getFsLeadOwnerDAO() {
        return BeanHandler.getBean("common-fsleadownerdao", FsLeadOwnerDAO.class);
    }

    public FsLeadSource2DAO getFsLeadSource2DAO() {
        return BeanHandler.getBean("common-fsleadsource2dao", FsLeadSource2DAO.class);
    }

    public FsLeadSource3DAO getFsLeadSource3DAO() {
        return BeanHandler.getBean("common-fsleadsource3dao", FsLeadSource3DAO.class);
    }

    public FsLeadPopserverInfoDAO getFsLeadPopserverInfoDAO() {
        return BeanHandler.getBean("common-fsleadpopserverinfodao", FsLeadPopserverInfoDAO.class);
    }

    public ChangeUserTypeDAO getChangeUserTypeDAO() {
        return BeanHandler.getBean("common-changeusertypedao", ChangeUserTypeDAO.class);
    }

    public DefaultEmailDAO getDefaultEmailDAO() {
        return BeanHandler.getBean("common-defaultemaildao", DefaultEmailDAO.class);
    }

    public BillingShippingAddressDAO getBillingShippingAddressDAO() {
        return BeanHandler.getBean("common-billingshippingaddressdao", BillingShippingAddressDAO.class);
    }

    public ConfigurationDAO getConfigurationDAO() {
        return BeanHandler.getBean("common-configurationdao", ConfigurationDAO.class);
    }

    public UsersDAO getUsersDAO() {
        return BeanHandler.getBean("common-usersdao", UsersDAO.class);
    }

    public CallDAO getCallDAO() {
        return BeanHandler.getBean("common-calldao", CallDAO.class);
    }

    public FimTransferDAO getFimTransferlDAO() {
        return BeanHandler.getBean("common-fimtransferdao", FimTransferDAO.class);
    }

    public PLDAO getPLDAO() {
        return BeanHandler.getBean("common-pldao", PLDAO.class);
    }

    public FsLeadRatingDAO getFsLeadRatingDAO() {
        return BeanHandler.getBean("common-fsleadratingdao", FsLeadRatingDAO.class);
    }

    public FsLeadQualificationDAO getFsLeadQualificationDAO() {
        return BeanHandler.getBean("common-fsleadqualificationdao", FsLeadQualificationDAO.class);
    }

    public ExchangeRateDAO getExchangeRateDAO() {
        return BeanHandler.getBean("common-exchangeratedao", ExchangeRateDAO.class);
    }

    public ConfigureFileDAO getConfigureFileDAO() {
        return BeanHandler.getBean("common-configurefiledao", ConfigureFileDAO.class);
    }

    public ConfigureTabDAO getConfigureTabDAO() {
        return BeanHandler.getBean("common-configuretabdao", ConfigureTabDAO.class);
    }

    public memberProfileDao getMPDAO() {
        return BeanHandler.getBean("common-memberprofiledao", memberProfileDao.class);
    }

    public FsLeadMarketingCodeDAO getFsLeadMarketingCodeDAO() {
        return BeanHandler.getBean("common-fsleadmarketingcodedao", FsLeadMarketingCodeDAO.class);
    }

    public AdminConfigurDAO getAdminConfigurDAO() {
        return BeanHandler.getBean("common-adminconfigurdao", AdminConfigurDAO.class);
    }

    public FacebookConfigurDAO getFacebookConfigurDAO() {
        return BeanHandler.getBean("common-facebookconfigurdao", FacebookConfigurDAO.class);
    }

    public CmContactMarketingCodeDAO getCMLeadMarketingCodeDAO() {
        return BeanHandler.getBean("common-cmcontactmarketingcodedao", CmContactMarketingCodeDAO.class);
    }

    public StoreDAO getStoreDAO() {
        return BeanHandler.getBean("common-storedao", StoreDAO.class);
    }

    /**
     *  Gets the MessageSignatureDAO attribute of the MessageMgr object
     *
     *@return    The MessageSignatureDAO value
     */
    public MessageSignatureDAO getMessageSignatureDAO() {
        return BeanHandler.getBean("common-messagesignaturedao", MessageSignatureDAO.class);
    }

    public CmContactHotActivityDAO getCmContactHotActivityDAO() {
        return BeanHandler.getBean("common-cmcontacthotactivitydao", CmContactHotActivityDAO.class);
    }

    public AdBuilderVendorDAO getAdBuilderVendorDAO() {
        return BeanHandler.getBean("common-adbuildervendordao", AdBuilderVendorDAO.class);
    }

    public EmailTemplatesDAO getEmailTemplatesDAO() {
        return BeanHandler.getBean("common-emailtemplatesdao", EmailTemplatesDAO.class);
    }
  //P_E_SMS_NOTIFICATION
    public SMSCampaignDAO getSMSCampaignDAO() {
        return BeanHandler.getBean("common-smscampaigndao", SMSCampaignDAO.class);
    }
    public DivisionsDAO getDivisionsDAO() {
        return BeanHandler.getBean("common-divisionsdao", DivisionsDAO.class);
    }
    //BB-20151201-455  Starts
    public ADGroupsDAO getADGroupsDAO() {
        return BeanHandler.getBean("common-adgroupsdao", ADGroupsDAO.class);
    }
    //BB-20151201-455  Ends
}
