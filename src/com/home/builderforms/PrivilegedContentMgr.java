/*
 * Copyright (c) 2001  Webrizon eSolutions Pvt. Ltd.
 * B-31, Sector 5, NOIDA. 201301, India.
 * All Rights Reserved
 *
 * This Class retreives details of All Franchisee Users
 *
 * @author Pranav
 *
 * @version 1.0
 * @date 10-01-2002
 */
////BBEH_INTRANET_SMC_OPTIMIZATION 25/07/13  Balvinder Mehla			code optimization in SMC_Intranet
// Filename: UserRole.java
package com.appnetix.app.portal.role;

import java.io.Serializable;
import java.util.*;
import java.sql.*;

import com.appnetix.app.control.web.multitenancy.util.MultiTenancyUtil;
import com.home.builderforms.*;
import com.home.builderforms.sqlqueries.SQLUtil;

/**
*	This Class is a representation of the User's role.
*/
public class PrivilegedContentMgr {

	public static String NEWS = "news";
	public static String ARTWORK = "artwork";//SMC-20130604-048
	public static String DOCUMENTS = "documents";

	public static String TRAINING = "training";
	public static String TRAINING_CATEGORY = "trainingCategory";

	public static String DISCUSSION = "discussion";

	public static String FAQ = "faq";
	public static String ALBUM = "album";
	
	public static String RELEATED_LINKS = "relatedLinks";


	private PrivilegedContent content;
	private String[] roleIDs;

	public PrivilegedContent getPrivilegedContent() {
		return content;
	}

	public void setPrivilegedContent(PrivilegedContent pContent)
	{
		content = pContent;
	}

	public void setRoleIDs(String[] ids)
	{
		this.roleIDs = ids;
	}

	public String getContentQuery(String contentType)
	{
		if (contentType.equals(NEWS))
		{
			return "SELECT * FROM NEWS_FOLDERS WHERE FOLDER_NO = ?";
		} else if (contentType.equals(DOCUMENTS))
		{
			return "SELECT * FROM LIBRARY_FOLDERS WHERE FOLDER_NO = ?";
		} else if (contentType.equals(TRAINING))
		{
			return "SELECT * FROM TRAINING_COURSE WHERE COURSE_ID = ?";
		} else if (contentType.equals(TRAINING_CATEGORY))
		{
			return "SELECT * FROM TRAINING_CATEGORY WHERE CATEGORY_ID = ?";
		} else if (contentType.equals(DISCUSSION))
		{
			return "SELECT * FROM MESSAGE_BOARD_CATEGORIES WHERE BOARD_NO = ?";
		} else if (contentType.equals(FAQ))
		{
			return "SELECT * FROM SUPP_FAQ_CATEGORY WHERE CATEGORY_ID = ?";
		}
		else if(contentType.equals(ALBUM))
		{
			return "SELECT * FROM FRAN_ALBUM WHERE ALBUM_ID = ?";
		}else if(contentType.equals(RELEATED_LINKS))
		{
			return "SELECT * FROM FRAN_ALBUM WHERE ALBUM_ID = ?";
		}else if(contentType.equals(ARTWORK))//SMC-20130604-048 
		{
			return "SELECT * FROM AM_FOLDERS WHERE FOLDER_NO = ?";
		}
		return "";
	}

	/**
	 * 
	 * @param contentType
	 * @return
	 */
	public String getIdColumn(String contentType)
	{if (contentType.equals(NEWS))
	{
		return "FOLDER_NO";
	} else if (contentType.equals(DOCUMENTS))
	{
		return "FOLDER_NO";
	} else if (contentType.equals(TRAINING))
	{
		return "WHERE COURSE_ID";
	} else if (contentType.equals(TRAINING_CATEGORY))
	{
		return "CATEGORY_ID";
	} else if (contentType.equals(DISCUSSION))
	{
		return "BOARD_NO";
	} else if (contentType.equals(FAQ))
	{
		return "CATEGORY_ID";
	}
	else if(contentType.equals(ALBUM))
	{
		return "ALBUM_ID";
	}else if(contentType.equals(ARTWORK))//SMC-20130604-048 
	{
		return "FOLDER_NO";
	}
	return "";
	}
	
	
	/**
	 * 
	 * @param contentType
	 * @return
	 */
	public String getContentTableName(String contentType)
	{
		if (contentType.equals(NEWS))
		{
			return "NEWS_FOLDERS";
		} else if (contentType.equals(DOCUMENTS))
		{
			return "LIBRARY_FOLDERS";
		} else if (contentType.equals(TRAINING))
		{
			return "TRAINING_COURSE";
		} else if (contentType.equals(TRAINING_CATEGORY))
		{
			return "TRAINING_CATEGORY";
		} else if (contentType.equals(DISCUSSION))
		{
			return "MESSAGE_BOARD_CATEGORIES";
		} else if (contentType.equals(FAQ))
		{
			return "SUPP_FAQ_CATEGORY WHERE";
		}
		else if(contentType.equals(ALBUM))
		{
			return "FRAN_ALBUM";
		}else if(contentType.equals(ARTWORK))//SMC-20130604-048 
		{
			return "AM_FOLDERS";
		}
		return "";
	}
	
	/**
	 * 
	 * @param contentType
	 * @return
	 */
	//BBEH_INTRANET_SMC_OPTIMIZATION:-method is optimized 
	public String getContentNameColumn(String contentType)
	{
		
		if ((contentType.equals(NEWS))||(contentType.equals(DOCUMENTS))||(contentType.equals(ARTWORK)))
		{
			return "FOLDER_NAME";
		} else if ((contentType.equals(TRAINING_CATEGORY))||(contentType.equals(FAQ)))
		{
			return "CATEGORY_NAME";
		} else if (contentType.equals(TRAINING))
		{
			return "COURSE_NAME";
		}  else if (contentType.equals(DISCUSSION))
		{
			return "BOARD_NAME";
		} 
		else if (contentType.equals(ALBUM))
		{
			return "ALBUM_NAME";
		}

		return "";
	
	}

	
	/**
	 * 
	 * @param contentType
	 * @return
	 */
	//BBEH_INTRANET_SMC_OPTIMIZATION:-method is optimized 
	public String getParentColumn(String contentType)
	{
		if ((contentType.equals(NEWS))||(contentType.equals(DOCUMENTS))||(contentType.equals(ARTWORK)))
		{
			return "PARENT_FOLDER_NO";
		}  else if ((contentType.equals(TRAINING))||(contentType.equals(TRAINING_CATEGORY))||(contentType.equals(FAQ)))
		{
			return null;
		}else if (contentType.equals(DISCUSSION))
		{
			return "PARENT_BOARD_NO";
		}
		return "";
	}

	public String getContentDisplayName(String contentType)
	{
		if (contentType.equals(NEWS))
		{
			return "News folder(s)";
		} else if (contentType.equals(DOCUMENTS))
		{
			return "Document folder(s)";
		} else if (contentType.equals(TRAINING))
		{
			return "Training Courses";
		} else if (contentType.equals(TRAINING_CATEGORY))
		{
			return "Training Category";
		} else if (contentType.equals(DISCUSSION))
		{
			return "Discussion Forums";
		} else if (contentType.equals(FAQ))
		{
			return "FAQ Categories";
		}
		else if (contentType.equals(ALBUM))
		{
				return "Album";
		}else if (contentType.equals(ARTWORK))
		{
			return "Artwork folder(s)";
	}
		return "";
	}
	//BBEH_INTRANET_SMC_OPTIMIZATION:-method is optimized 

    public String getContentParentDisplayName(String contentType)
	{
		
		if ((contentType.equals(NEWS))||(contentType.equals(DOCUMENTS))||(contentType.equals(FAQ)))
		{
			return "Parent Folder";
		} else if ((contentType.equals(TRAINING))||(contentType.equals(TRAINING_CATEGORY)))
		{
			return null;
		} else if (contentType.equals(DISCUSSION))
		{
			return "Discussion Forums";
		} 

		return "";
	
	}

	public PrivilegedContent getPrivilegedContent(String id)
	{
		PreparedStatement pstmt = null ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		ResultSet rst = null ;
		if (id != null)
		{
			this.content					=	new PrivilegedContent();
			content.setPrivilegedContentID(id);
			Connection con = null;
			try{
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
				pstmt		= con.prepareStatement("SELECT * FROM PRIVILEGED_CONTENT WHERE PRIVILEGED_CONTENT_ID = ?");
				pstmt.setString(1, id);
				rs		= pstmt.executeQuery();
				if (rs.next())
				{
				  int is	=	(int)rs.getInt("IS_ADMIN");
				  content.setAdminFlag(is);
				  content.setContentID(rs.getString("CONTENT_ID"));
				  content.setContentType(rs.getString("CONTENT_TYPE"));
				  st=con.prepareStatement(getContentQuery(content.getContentType()));
				  st.setString(1, rs.getString("CONTENT_ID"));
				  rst= st.executeQuery();
				  if (rst.next())
				  {
					  content.setContentName(rst.getString(getContentNameColumn(content.getContentType())));
				  }
				 }
				//rs.close();
			}
			catch (Exception exp){
					Debug.print(exp);
					Debug.println("Exception. Unable to get Privileged Content with id : " + id);

			}
			finally {
				try
				{
					SQLUtil.closePstmt(pstmt);
					SQLUtil.closePstmt(st);
					SQLUtil.closeResultSet(rs);
					SQLUtil.closeResultSet(rst);
					DBConnectionManager.getInstance().freeConnection(con);
				}
				catch(Exception exp)
				{
					Debug.print(exp);
				}
			}
		}
			return content;
	}

	public boolean createNewPrivilegedContent()
	{
		boolean flag = true;
		Connection con = null;
		PreparedStatement pstmt = null ;
		PreparedStatement roleSt = null ;
		Statement st1 = null ;
		ResultSet rs = null ;
		try{
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
				con.setAutoCommit(false);
				//SeniorHelpers-20150515-812 starts
				//pstmt	= con.prepareStatement("INSERT INTO PRIVILEGED_CONTENT (CONTENT_ID, CONTENT_TYPE , IS_ADMIN) VALUES(?,?,?)");
				pstmt	= con.prepareStatement("INSERT INTO PRIVILEGED_CONTENT (CONTENT_ID, CONTENT_TYPE , IS_ADMIN,PRIVILEGED_CONTENT_ID) VALUES(?,?,?,?)");
				//SeniorHelpers-20150515-812 ends
				pstmt.setString(1, content.getContentID());
				pstmt.setString(2, content.getContentType());
				pstmt.setInt(3, content.isAdmin());
				//SeniorHelpers-20150515-812 starts
				String randomNumber	= IDGenerator.getNextKey();
				pstmt.setString(4,randomNumber);
				//SeniorHelpers-20150515-812 ends
				pstmt.executeUpdate();
				//SeniorHelpers-20150515-812 starts
				/*st1		= con.createStatement();
				rs		= st1.executeQuery("SELECT LAST_INSERT_ID() lid FROM PRIVILEGED_CONTENT");
				String id						=	"";
				if (rs.next())
				{
					id							= rs.getString("lid");
				}
				 */
				//SeniorHelpers-20150515-812 ends
				roleSt		= con.prepareStatement("INSERT INTO ROLE_PRIVILEGES (ROLE_ID, PRIVILEGE_ID, IS_CONTENT) VALUES(?, ?, ?)");
				String[] newRoleIDs	= checkRoleIDsForAdmin();
				for (int i = 0; i < newRoleIDs.length ; i++ )
				{
					roleSt.setString(1, newRoleIDs[i]);
					//SeniorHelpers-20150515-812 starts
					//roleSt.setString(2, id);
					roleSt.setString(2, randomNumber);
					//SeniorHelpers-20150515-812 ends
					roleSt.setInt(3, 1);
					roleSt.executeUpdate();
				}

				con.commit();

			}

			catch (Exception exp){
					flag = false;
					try
					{
						con.rollback();
					}
					catch (SQLException se)
					{
						Debug.print(se);
					}
					Debug.print(exp);
					Debug.println("Exception. Unable to create new Privileged Content");
			}
			finally {
				try
				{
					SQLUtil.closeResultSet(rs);
					SQLUtil.closePstmt(pstmt);
					SQLUtil.closePstmt(roleSt);
					SQLUtil.closeStmt(st1);
					DBConnectionManager.getInstance().freeConnection(con);
				}
				catch(Exception exp)
				{
					Debug.print(exp);
				}
			}
		  return flag;

	}


	public boolean modifyRolesForContent(String contentID, String contentType)
	{
		boolean flag = true;
		Connection con = null;
		PreparedStatement pstmt = null ;
		PreparedStatement  pstmt1 = null ;
		PreparedStatement  roleSt = null ;
		ResultSet  rs = null ;
		try{
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
				con.setAutoCommit(false);
				pstmt	= con.prepareStatement("SELECT * FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = ? AND CONTENT_ID = ?");
				pstmt.setString(1, contentType);
				pstmt.setString(2, contentID);
				Debug.println("SELECT * FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = "+contentType +"AND CONTENT_ID ="+contentID);
				rs	= pstmt.executeQuery();
				if (rs.next())
				{
					String id						= rs.getString("PRIVILEGED_CONTENT_ID");
					if (id != null && !id.equals(""))
					{
						pstmt1		= con.prepareStatement("DELETE FROM ROLE_PRIVILEGES WHERE PRIVILEGE_ID = ? AND IS_CONTENT = 1");
						pstmt1.setString(1, id);
						pstmt1.executeUpdate();
					/*	System.out.println(PrivilegedContentMgr.DISCUSSION+contentType+contentType.equals(PrivilegedContentMgr.DISCUSSION));

						if( contentType.equals(PrivilegedContentMgr.DISCUSSION) ){
						PreparedStatement pstmt2		= con.prepareStatement("UPDATE MESSAGE_BOARD_CATEGORIES SET IS_A_PRIVILEGE = 1 WHERE BOARD_NO= ?");
						Debug.println("INSIDE -->UPDATE MESSAGE_BOARD_CATEGORIES SET IS_A_PRIVILEGE = 1 WHERE BOARD_NO="+id);
						pstmt2.setString(1, id);
						pstmt2.executeUpdate();
						}*/


						roleSt		= con.prepareStatement("INSERT INTO ROLE_PRIVILEGES (ROLE_ID, PRIVILEGE_ID, IS_CONTENT) VALUES(?, ?, ?)");
						String[] newRoleIDs	= checkRoleIDsForAdmin();
//						System.out.println("ROLE ID");
						for (int i = 0; i < newRoleIDs.length ; i++ )
						{
//							System.out.println(newRoleIDs[i]);
							roleSt.setString(1, newRoleIDs[i]);
							roleSt.setString(2, id);
							roleSt.setInt(3, 1);
							roleSt.executeUpdate();
						}
					}
				}

				con.commit();
				Debug.println("Forum Modified Successfully");

			}

			catch (Exception exp){
					flag = false;
					try
					{
						con.rollback();
					}
					catch (SQLException se)
					{
						Debug.print(se);
					}
					Debug.print(exp);
					Debug.println("Exception. Unable to create new Privileged Content");
			}
			finally {
				try
				{
					SQLUtil.closePstmt(pstmt);
					SQLUtil.closePstmt(pstmt1);
					SQLUtil.closePstmt(roleSt);
					SQLUtil.closeResultSet(rs);
					DBConnectionManager.getInstance().freeConnection(con);
				}
				catch(Exception exp)
				{
					Debug.print(exp);
				}
			}
		  return flag;

	}



	public String[] checkRoleIDsForAdmin()
	{
		if (roleIDs != null)
		{
			String[] newRoleIDs	= new String[roleIDs.length + 1];
			for (int i = 0; i < roleIDs.length ; i++)
			{
				if (roleIDs[i] != null && roleIDs[i].equals("1"))
				{
					return roleIDs;
				} else {
					newRoleIDs[i] = roleIDs[i];
				}
			}
			newRoleIDs[roleIDs.length] = "1";
			return newRoleIDs;
		}
		return (new String[] {"1"});
	}

	public boolean deletePrivilegedContent()
	{
		if (content.getPrivilegedContentID() == null)
		{
			return false;
		} else {
			return deletePrivilegedContent(content.getPrivilegedContentID());
		}
	}

	public boolean deletePrivilegedContent(String id)
	{
		boolean flag = true;
		Connection con = null;
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		try{
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
				con.setAutoCommit(false);


				st1			= con.prepareStatement("DELETE FROM ROLE_PRIVILEGES WHERE PRIVILEGE_ID = ? AND IS_CONTENT=1");
				st1.setString(1, id);
				st1.executeUpdate();

				st2			= con.prepareStatement("DELETE FROM PRIVILEGED_CONTENT WHERE PRIVILEGED_CONTENT_ID = ?");
				st2.setString(1, id);
				st2.executeUpdate();

				con.commit();

			}

			catch (Exception exp){
					flag = false;
					try
					{
						con.rollback();
					}
					catch (SQLException se)
					{
						Debug.print(se);
					}
					Debug.print(exp);
					Debug.println("Exception. Unable to delete Privileged Content");
			}
			finally {
				try
				{
					SQLUtil.closePstmt(st1);
					SQLUtil.closePstmt(st2);
					DBConnectionManager.getInstance().freeConnection(con);
				}
				catch(Exception exp)
				{
					Debug.print(exp);
				}
			}
		  return flag;

	}

	public boolean deletePrivilegedContent(String content_id,String content_type)
	{
		boolean flag = true;
		String id="";;
		Connection con = null;
		PreparedStatement st = null ;
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		try{
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);

				st	= con.prepareStatement("SELECT PRIVILEGED_CONTENT_ID FROM PRIVILEGED_CONTENT WHERE  CONTENT_ID= ? AND CONTENT_TYPE = ? AND IS_ADMIN=0");
				st.setString(1, content_id);
                st.setString(2, content_type);

				rs = st.executeQuery();

				if (rs.next())
				{
					id = rs.getString(1);

 				}

				con.setAutoCommit(false);


				st2	= con.prepareStatement("DELETE FROM PRIVILEGED_CONTENT WHERE PRIVILEGED_CONTENT_ID = ?");
				st2.setString(1, id);
				st2.executeUpdate();

				st1	= con.prepareStatement("DELETE FROM ROLE_PRIVILEGES WHERE PRIVILEGE_ID = ? AND IS_CONTENT=1");
				st1.setString(1, id);
				st1.executeUpdate();

				con.commit();

			}

			catch (Exception exp){
					flag = false;
					try
					{
						con.rollback();
					}
					catch (SQLException se)
					{
						Debug.print(se);
					}
					Debug.print(exp);
					Debug.println("Exception. Unable to delete Privileged Content");
			}
			finally {
				try
				{
					SQLUtil.closePstmt(st);
					SQLUtil.closePstmt(st1);
					SQLUtil.closePstmt(st2);
					SQLUtil.closeResultSet(rs);
					DBConnectionManager.getInstance().freeConnection(con);
				}
				catch(Exception exp)
				{
					Debug.print(exp);
				}
			}
		  return flag;

	}

/* returns a hashmap containing submaps(HashMap) for all parent folders/ forums..
 * which contain respective PrivilegeContents Within them. If there are no submaps then
 * an object of PrivilegeContent is there instead of subMap.
 */
	public HashMap getPrivilegedContents(String contentType)
	{
		HashMap contentMap					= new HashMap();
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		ResultSet rst = null;
		ResultSet rst2  = null;
		try{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			pstmt		= con.prepareStatement("SELECT * FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = ?");
			pstmt.setString(1, contentType);
			rs	= pstmt.executeQuery();
			while (rs.next())
			{
			  PrivilegedContent content		= new PrivilegedContent();
			  String id						= rs.getString("PRIVILEGED_CONTENT_ID");
			  content.setPrivilegedContentID(id);
			  int is						= rs.getInt("IS_ADMIN");
			  content.setAdminFlag(is);
			  content.setContentID(rs.getString("CONTENT_ID"));
			  content.setContentType(rs.getString("CONTENT_TYPE"));
		  st	=con.prepareStatement(getContentQuery(content.getContentType()));
			  st.setString(1, rs.getString("CONTENT_ID"));
			  rst		= st.executeQuery();
			  String parentContentNo		= "";
			  if (rst.next())
			  {
				content.setContentName(rst.getString(getContentNameColumn(contentType)));
				if (getParentColumn(contentType) != null)
				{
					parentContentNo				= rst.getString(getParentColumn(contentType));
					st2				= con.prepareStatement(getContentQuery(content.getContentType()));
					st2.setString(1, parentContentNo);
					rst2	= st2.executeQuery();
					if (rst2.next())
					{
						content.setContentParentName(rst2.getString(getContentNameColumn(contentType)));
					}
				}
			  }
			  if (getParentColumn(contentType) != null)
			  {
					HashMap subMap				= (HashMap)contentMap.get(parentContentNo);
					if (subMap == null)
					{
						subMap	= new HashMap();
						subMap.put(id, content);
						contentMap.put(parentContentNo, subMap);
					} else {
						subMap.put(id, content);
					}
			  } else {
				contentMap.put(id, content);
			  }
			}

		}
		catch (Exception exp){
				Debug.print(exp);
				Debug.println("Exception. Unable to get Privileged Contents of type : " + contentType);

		}
		finally {
			try
			{
				SQLUtil.closeResultSet(rs);
				SQLUtil.closeResultSet(rst);
				SQLUtil.closeResultSet(rst2);
				SQLUtil.closePstmt(pstmt);
				SQLUtil.closePstmt(st);
				SQLUtil.closePstmt(st2);
    				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return contentMap;
	}
	/*
	 * returns a hashmap containing all PrivilegeContents for a contentType.
	 */
		public HashMap getPrivilegedContentsForType(String contentType)
		{
			return getPrivilegedContentsForType(contentType,null);
		}
		/**
		 *SMC-20130604-048
		 */
		public HashMap getPrivilegedContentsForType(String contentType,String roleID)
		{
		HashMap contentMap					= new HashMap();
		Connection con = null;
		PreparedStatement pstmt	 = null ;
		PreparedStatement st = null ;
		PreparedStatement st2 = null ;
		ResultSet rs = null ;
		ResultSet rst = null;
		ResultSet rst2 = null;
		try{
			StringBuilder query =new StringBuilder();
			
			if(StringUtil.isValid(roleID)){//SMC-20130604-048
				query.append("SELECT PC.* FROM PRIVILEGED_CONTENT PC LEFT JOIN  ROLE_PRIVILEGES RP ON RP.PRIVILEGE_ID=PC.PRIVILEGED_CONTENT_ID WHERE CONTENT_TYPE = ?  AND RP.ROLE_ID=?");
			}else{
				query.append("SELECT * FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = ?");
			}
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			pstmt			= con.prepareStatement(query.toString());//SMC-20130604-048
			pstmt.setString(1, contentType);
			
			if(StringUtil.isValid(roleID)){//SMC-20130604-048
				pstmt.setString(2,roleID );	
			}
			
			 rs					= pstmt.executeQuery();
			while (rs.next())
			{
			  PrivilegedContent content		= new PrivilegedContent();
			  String id						= rs.getString("PRIVILEGED_CONTENT_ID");
			  content.setPrivilegedContentID(id);
			  int is						= rs.getInt("IS_ADMIN");
			  content.setAdminFlag(is);
			  content.setContentID(rs.getString("CONTENT_ID"));
			  content.setContentType(rs.getString("CONTENT_TYPE"));
			  st	= con.prepareStatement(getContentQuery(content.getContentType()));
			  st.setString(1, rs.getString("CONTENT_ID"));
			  rst					= st.executeQuery();
			  String parentContentNo		= "";
			  if (rst.next())
			  {
				content.setContentName(rst.getString(getContentNameColumn(contentType)));
				if (getParentColumn(contentType) != null)
				{
					parentContentNo				= rst.getString(getParentColumn(contentType));
					st2	= con.prepareStatement(getContentQuery(content.getContentType()));
					st2.setString(1, parentContentNo);
					rst2		= st2.executeQuery();
					if (rst2.next())
					{
						content.setContentParentName(rst2.getString(getContentNameColumn(contentType)));
					}
				}
				contentMap.put(content.getContentID(), content);
			  }
			}
		}
		catch (Exception exp){
				Debug.print(exp);
				Debug.println("Exception. Unable to get Privileged Contents of type : " + contentType);

		}
		finally {
			try
			{
				SQLUtil.closeResultSet(rs);
				SQLUtil.closeResultSet(rst);
				SQLUtil.closeResultSet(rst2);
				SQLUtil.closePstmt(pstmt);
				SQLUtil.closePstmt(st);
				SQLUtil.closePstmt(st2);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return contentMap;
	}

/*
 * returns a hashmap containing all PrivilegeContents for a contentType.
 */
	public HashMap getPrivilegedContentsCollection(String contentType)
	{
		HashMap contentMap					= new HashMap();
		HashMap orderedMap					= new HashMap();

		Connection con = null;
		try{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			contentMap = getPrivilegedContentsForType(contentType);
			Iterator contentIt = contentMap.keySet().iterator();
			while (contentIt.hasNext())
			{
				String contentId = (String)contentIt.next();
				String contentHierarchy = getContentHierarchyReverseCS(contentId, contentType);
				//Debug.println(contentHierarchy);
				StringTokenizer strTokens = new StringTokenizer(contentHierarchy, ",");
				HashMap currentMap = new HashMap();
				currentMap = orderedMap;
				while (strTokens.hasMoreTokens())
				{
					String contentIDH	= strTokens.nextToken();
					PrivilegedContent content = (PrivilegedContent)currentMap.get(contentIDH);
					if (content == null)
					{
						content = (PrivilegedContent)contentMap.get(contentIDH);
						currentMap.put(contentIDH, content);
					}
					currentMap = content.getSubContents();
				}

			}
		}
		catch (Exception exp){
				Debug.print(exp);
				Debug.println("Exception. Unable to get Privileged Contents of type : " + contentType);

		}
		finally {
			try
			{
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return orderedMap;
	}


	public Vector getRolesForContent(String contentID, String contentType)
	{
		Vector roleMap					= new Vector();
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		Statement st = null ;
		ResultSet rst = null ;
		try{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			pstmt			= con.prepareStatement("SELECT * FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = ? AND CONTENT_ID = ?");
			pstmt.setString(1, contentType);
			pstmt.setString(2, contentID);
			rs		= pstmt.executeQuery();
			if (rs.next())
			{
				String id						= rs.getString("PRIVILEGED_CONTENT_ID");
				if (id != null && !id.equals(""))
				{
					st	= con.createStatement();
					rst	= st.executeQuery("SELECT DISTINCT(ROLE_ID) RID FROM ROLE_PRIVILEGES WHERE IS_CONTENT = 1 AND PRIVILEGE_ID = " + id);
					while (rst.next())
					{
						String roleID				= rst.getString("RID");

						roleMap.addElement(roleID);
					}
					//rst.close();
				}

			}

			//rs.close();
		}
		catch (Exception exp){
				Debug.print(exp);
				Debug.println("Exception. Unable to get Privileged Contents of type : " + contentType);

		}
		finally {
			try
			{
				SQLUtil.closePstmt(pstmt);
				SQLUtil.closeStmt(st);
				SQLUtil.closeResultSet(rs);
				SQLUtil.closeResultSet(rst);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return roleMap;
	}

	public String getContentHierarchyCS(String contentID, String contentType)
	{
		String contentHierarchy					= "";
		PreparedStatement st = null ;
		ResultSet rst = null ;
		if (isPrivilegedContent(contentID, contentType))
		{
			contentHierarchy					= contentID;
		}
		Connection con = null;
		try
		{
			con		= DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			st		= con.prepareStatement(getContentQuery(contentType));
			st.setString(1, contentID);
			rst		= st.executeQuery();
			String parentContentNo				= "";
			if (getParentColumn(contentType) != null && rst.next())
			{
				parentContentNo					= rst.getString(getParentColumn(contentType));
				while (parentContentNo != null && !parentContentNo.equals(""))
				{
					if (isPrivilegedContent(parentContentNo, contentType))
					{
						contentHierarchy		= contentHierarchy + "," + parentContentNo;
					}
					st.setString(1, parentContentNo);
					rst							= st.executeQuery();
					if (rst.next())
					{
						parentContentNo			= rst.getString(getParentColumn(contentType));
					} else {
						parentContentNo			= null;
					}
				}
			}
		}
		catch (Exception e)
		{
			Debug.print(e);
			Debug.println("Exception while getting Content Hierarchy");
		}
		finally {
			try
			{
				SQLUtil.closePstmt(st);
				SQLUtil.closeResultSet(rst);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		
		//Debug.println("Content Hierarchy CS " + contentHierarchy);
		return contentHierarchy;
	}

public String getContentHierarchyReverseCS(String contentID, String contentType)
	{
		String contentHierarchy					= "";
		PreparedStatement st	= null ;
		ResultSet rst = null ;
		if (isPrivilegedContent(contentID, contentType))
		{
			contentHierarchy					= contentID;
		}
		Connection con = null;
		try
		{
			con									= DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			st	= con.prepareStatement(getContentQuery(contentType));
			st.setString(1, contentID);
			rst	= st.executeQuery();
			String parentContentNo				= "";
			if (getParentColumn(contentType) != null && rst.next())
			{
				parentContentNo					= rst.getString(getParentColumn(contentType));
				while (parentContentNo != null && !parentContentNo.equals(""))
				{
					if (isPrivilegedContent(parentContentNo, contentType))
					{
						contentHierarchy		= parentContentNo  + "," +  contentHierarchy;
					}
					st.setString(1, parentContentNo);
					rst							= st.executeQuery();
					if (rst.next())
					{
						parentContentNo			= rst.getString(getParentColumn(contentType));
					} else {
						parentContentNo			= null;
					}
				}
			}
		}
		catch (Exception e)
		{
			Debug.print(e);
			Debug.println("Exception while getting Content Hierarchy");
		}
		finally {
			try
			{
				SQLUtil.closePstmt(st) ;
				SQLUtil.closeResultSet(rst) ;
				DBConnectionManager.getInstance().freeConnection(con) ;
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		
		//Debug.println("Content Hierarchy CS " + contentHierarchy);
		return contentHierarchy;
	}

	public String getPrivilegedContentHierarchyCS(String contentID, String contentType)
	{
		String contentHierarchy					= "";
		PreparedStatement st = null ;
		ResultSet rst = null ;
		if (isPrivilegedContent(contentID, contentType))
		{
			contentHierarchy					= getPrivilegedContentID(contentID, contentType);
		}
		Connection con = null;
		try
		{
			con									= DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			st	= con.prepareStatement(getContentQuery(contentType));
			st.setString(1, contentID);
			rst		= st.executeQuery();
			String parentContentNo				= "";
			if (getParentColumn(contentType) != null && rst.next())
			{
				parentContentNo					= rst.getString(getParentColumn(contentType));
				while (parentContentNo != null && !parentContentNo.equals(""))
				{
					if (isPrivilegedContent(parentContentNo, contentType))
					{
						contentHierarchy		= contentHierarchy + "," + getPrivilegedContentID(parentContentNo, contentType);
					}
					st.setString(1, parentContentNo);
					rst							= st.executeQuery();
					if (rst.next())
					{
						parentContentNo			= rst.getString(getParentColumn(contentType));
					} else {
						parentContentNo			= null;
					}
				}
			}
		}
		catch (Exception e)
		{
			Debug.print(e);
			Debug.println("Exception while getting Content Hierarchy");
		}
		finally {
			try
			{
				SQLUtil.closePstmt(st);
				SQLUtil.closeResultSet(rst);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}

		//Debug.println("Content Hierarchy CS " + contentHierarchy);
		return contentHierarchy;
	}

	public boolean isPrivilegedContent(String contentID, String contentType)
	{
		Connection con = null;
		ResultSet rs = null ;
		PreparedStatement pstmt = null ;
		boolean isPC = false;
		try{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			pstmt		= con.prepareStatement("SELECT * FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = ? AND CONTENT_ID = ?");
			pstmt.setString(1, contentType);
			pstmt.setString(2, contentID);
			rs	= pstmt.executeQuery();
			if (rs.next())
			{
				isPC = true;
			}

			//rs.close();

		}
		catch (Exception exp){
				Debug.print(exp);
				Debug.println("Exception. Unable to get Privileged Contents of type : " + contentType);

		}
		finally {
			try
			{
				SQLUtil.closePstmt(pstmt);
				SQLUtil.closeResultSet(rs);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return isPC;
	}

	public String getPrivilegedContentID(String contentID, String contentType)
	{
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String pcID = "";
		try{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			pstmt		= con.prepareStatement("SELECT * FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = ? AND CONTENT_ID = ?");
			pstmt.setString(1, contentType);
			pstmt.setString(2, contentID);
			rs	= pstmt.executeQuery();
			if (rs.next())
			{
				pcID = rs.getString("PRIVILEGED_CONTENT_ID");
			}

			//rs.close();

		}
		catch (Exception exp){
				Debug.print(exp);
				Debug.println("Exception. Unable to get Privileged Contents of type : " + contentType);

		}
		finally {
			try
			{
				SQLUtil.closePstmt(pstmt);
				SQLUtil.closeResultSet(rs);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return pcID;
	}


	public boolean insertPrivilige(String val,int id)
	{
		Connection con = null;
		String pcID = "";
		boolean check = false;
		PreparedStatement pstmt = null ;
		try{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			pstmt			= con.prepareStatement("UPDATE MESSAGE_BOARD_CATEGORIES SET IS_A_PRIVILEGE = ? WHERE BOARD_NO= ?");
			pstmt.setInt(1,Integer.parseInt(val));
			pstmt.setInt(2,id);
			pstmt.executeUpdate();
			check=true;
		}
		catch (Exception exp){
				Debug.print(exp);
//				Debug.println("Exception. Unable to get Privileged Contents of type : " + contentType);

		}
		finally {
			try
			{
				SQLUtil.closePstmt(pstmt);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return check;
	}
	public Vector getRolesForAllCourses(String contentID) {
		return getRolesForAllCourses(contentID,null);
	}
public Vector getRolesForAllCourses(String contentID,String fromWhere) {
		Vector roleMap					= new Vector();
		Connection con = null;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		Statement st = null ;
		ResultSet rst = null ;
		try{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			if("relatedLink".equals(fromWhere))
				pstmt			= con.prepareStatement("SELECT GROUP_CONCAT(PRIVILEGED_CONTENT_ID) AS PRIVILEGED_CONTENT_ID FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = 'relatedLink' AND CONTENT_ID = ?");
			else				
				pstmt			= con.prepareStatement("SELECT GROUP_CONCAT(PRIVILEGED_CONTENT_ID) AS PRIVILEGED_CONTENT_ID FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = 'training' AND CONTENT_ID IN (SELECT COURSE_ID FROM TRAINING_COURSE WHERE FLAG_DELETE=0 AND CATEGORY_ID=?)");
			
			pstmt.setString(1, contentID);
			rs		= pstmt.executeQuery();
			String id = "";
			if(rs.next())
			{
				id = rs.getString("PRIVILEGED_CONTENT_ID");
				if (id != null && !id.equals(""))
				{
					st	= con.createStatement();
					rst	= st.executeQuery("SELECT DISTINCT(ROLE_ID) RID FROM ROLE_PRIVILEGES WHERE IS_CONTENT = 1 AND PRIVILEGE_ID IN ("+id+") ");
					while (rst.next())
					{
						String roleID	= rst.getString("RID");
						roleMap.addElement(roleID);
					}
				}
			}
			//rs.close();
		}
		catch (Exception exp){
				exp.printStackTrace();
				Debug.print(exp);
				Debug.println("Exception. Unable to get Roles ForAllCourses : ");
		}
		finally {
			try
			{
				SQLUtil.closePstmt(pstmt);
				SQLUtil.closeStmt(st);
				SQLUtil.closeResultSet(rs);
				SQLUtil.closeResultSet(rst);
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return roleMap;
	}


/*
 *SMC-20130604-048
 * returns a hashmap containing all PrivilegeContents for a contentType.
 */
	public HashMap getRolePrivilegedContentsCollection(String contentType,String roleId)
	{
		HashMap contentMap					= new HashMap();
		HashMap orderedMap					= new HashMap();
		Connection con = null;
		try{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME,2000);
			contentMap = getPrivilegedContentsForType(contentType,roleId);
			Iterator contentIt = contentMap.keySet().iterator();
			while (contentIt.hasNext())
			{
				String contentId = (String)contentIt.next();
				String contentHierarchy = getContentHierarchyReverseCS(contentId, contentType);
				//Debug.println(contentHierarchy);
				StringTokenizer strTokens = new StringTokenizer(contentHierarchy, ",");
				HashMap currentMap = new HashMap();
				currentMap = orderedMap;
				while (strTokens.hasMoreTokens())
				{
					String contentIDH	= strTokens.nextToken();
					PrivilegedContent content = (PrivilegedContent)currentMap.get(contentIDH);
					if (content == null)
					{
						content = (PrivilegedContent)contentMap.get(contentIDH);
						currentMap.put(contentIDH, content);
					}
					currentMap = content.getSubContents();
				}

			}
		}
		catch (Exception exp){
				Debug.print(exp);
				Debug.println("Exception. Unable to get Privileged Contents of type : " + contentType);

		}
		finally {
			try
			{
				DBConnectionManager.getInstance().freeConnection(con);
			}
			catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return orderedMap;
	}
}
