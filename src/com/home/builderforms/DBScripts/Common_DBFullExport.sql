DROP TABLE IF EXISTS COUNTRIES;
CREATE TABLE COUNTRIES (
  COUNTRY_ID int(11) unsigned NOT NULL default '0',
  NAME varchar(100) default NULL,
  SHOW_COUNTRY tinyint(3) unsigned default NULL,
  CURRENCY_ID int(11) default NULL,
  IS_DOMESTIC char(1) default 'N',
  COUNTRY_CODE int(5) default NULL,
  COUNTRY_ABBREV char(3) DEFAULT NULL,
  SMS_ENABLED CHAR(1) DEFAULT 'N',
  PRIMARY KEY  (COUNTRY_ID)
)  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS REGIONS;
CREATE TABLE REGIONS (
  REGION_NO int(11) unsigned NOT NULL auto_increment,
  REGION_NAME varchar(100) default NULL,
  COUNTRY_ID int(11) default NULL,
  REGION_ABBREV char(3) NOT NULL default '',
  REGISTERED char(1) default '1',
  CCFD_ABBREV char(2) default NULL,
  PRIMARY KEY  (REGION_NO),
   KEY countryID (COUNTRY_ID),
   KEY regionName(REGION_NAME)
)  DEFAULT CHARSET=utf8;