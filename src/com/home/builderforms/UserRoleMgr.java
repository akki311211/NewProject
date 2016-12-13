package  com.home.builderforms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.home.builderforms.AppException;
import com.home.builderforms.BaseUtils;
import com.home.builderforms.ConnectionException;
import com.home.builderforms.DBConnectionManager;
import com.home.builderforms.Debug;
import com.home.builderforms.ModuleUtil;
import com.home.builderforms.QueryUtil;
import com.home.builderforms.StringUtil;
import com.home.builderforms.StrutsUtil;
import com.home.builderforms.Info;
import com.home.builderforms.sqlqueries.SQLUtil;

public class UserRoleMgr
{
	private static Logger logger = Logger.getLogger(UserRoleMgr.class);

	private static UserRoleMgr userRoleMgr = new UserRoleMgr();

	private UserRoleMgr()
	{

	}

	public static UserRoleMgr getInstance()
	{
		return userRoleMgr;
	}

	public UserRoleMap getUserRoleMap(String userNo)
	{
		return getUserRoleMap(userNo, null);
	}

	public UserRoleMap getUserRoleMap(String userNo,Map<String,Object> otherParams)
	{
		return UserRoleMap.newInstance(userNo, getUserRoles(userNo,otherParams));
	}

	public Map<String, UserRole> getUserRoles(String userNo, Map<String,Object> otherParams)
	{
		Map<String, UserRole> roles = new HashMap<String, UserRole>();
		com.home.builderforms.sqlqueries.ResultSet rst = null;
		try
		{
			String query = "SELECT * FROM USER_ROLES WHERE USER_NO = ?";
			rst = QueryUtil.getResult(query, new Object[] { userNo });

			if(rst != null)
			{
				while(rst.next())
				{
					String id = rst.getString("ROLE_ID");
					UserRole userRole = getUserRole(rst.getString("ROLE_ID"), otherParams);
					if(userRole != null)
					{
						userRole.setRoleID(id);
						roles.put(userRole.getRoleID(), userRole);
					}
				}
			}

		}catch(Exception exp)
		{
			logger.error("Exception while creating userRoleMap for UserNo:" + userNo, exp);
		}
		return roles;
	}

	public UserRole getUserRole(String id)
	{
		return getUserRole(id, null);
	}

	public UserRole getUserRole(String id, Map<String,Object> otherParams)
	{
		UserRole role = null;
		com.home.builderforms.sqlqueries.ResultSet rs = null;

		if(StringUtil.isValid(id))
		{

			role = UserRole.newInstance();
			role.setRoleID(id);

			try
			{
				String query = "SELECT * FROM ROLE WHERE ROLE_ID = ?";

				rs = QueryUtil.getResult(query, new Object[] { id });

				String isAdmin = null;

				if(rs != null && rs.next())
				{
					role.setRoleName(rs.getString("NAME"));
					role.setAdminFlag(Integer.parseInt(rs.getString("IS_ADMIN")));
					role.setDefaultFlag(Integer.parseInt(rs.getString("IS_DEFAULT")));
					role.setAssignedTo(rs.getString("ASSIGNED_TO"));
					role.setFranchiseeNo(rs.getString("FRANCHISEE_NO"));
					isAdmin = rs.getString("IS_ADMIN");
				}
				if(false && otherParams !=null && otherParams.get("isPremium")!=null)
				{ 	String priv="";
						String isTrial =otherParams.containsKey("isTrial")?(String)otherParams.get("isTrial"):"false";
						String isPaid = otherParams.containsKey("nonTrialPremium")?(String)otherParams.get("nonTrialPremium"):"false";
						String fromViewAll = otherParams.containsKey("fromViewAll")?(String)otherParams.get("fromViewAll"):"false";
						if( "true".equals(otherParams.get("isPremium")) && "true".equals("false"))
						{ 
							query = "SELECT IS_CONTENT,PRIVILEGE_ID FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND ( IS_CONTENT = 0 || IS_CONTENT = 1)";
						}
						else
						if( "true".equals(otherParams.get("isPremium")) && "false".equals("false"))
						{ 
							if("true".equals(isTrial) && "false".equals(isPaid))
							{
								query = "SELECT IS_CONTENT,PRIVILEGE_ID FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND ( IS_CONTENT = 0 || IS_CONTENT = 1)";
							}
							else if("true".equals(isPaid) && "true".equals(fromViewAll))
							{
								String allPriv=(String)otherParams.get("allPrivileges");
								query = "SELECT * FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND IS_CONTENT = 0 AND PRIVILEGE_ID IN ("+chopmethod(allPriv)+")";
	
							}else{
									priv=SQLUtil.getColumnValue("ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING", "BASIC_PRIVILEGES", "PP_ID",  "false");
									priv=chopmethod(priv);
									priv=priv+",";
									priv+=SQLUtil.getColumnValue("ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING", "PREMIUM_PREVILEGES", "PP_ID",(String)otherParams.get("optedPackageId"));
									query = "SELECT * FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND IS_CONTENT = 0 AND PRIVILEGE_ID IN ("+chopmethod(priv)+")";
								}
						}
						else if("true".equals(otherParams.get("fromPremium")))
						{
							if("false".equals("false")){
								priv= SQLUtil.getColumnValue("ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING", "PREMIUM_PREVILEGES", "PP_ID",(String)otherParams.get("optedPackageId"));
								query="SELECT * FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND IS_CONTENT = 0 AND PRIVILEGE_ID " +
								"IN ("+priv+")";
							}else{
									//query="SELECT * FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND IS_CONTENT = 0 AND PRIVILEGE_ID IN ( SELECT PRIVILEGE_ID FROM ROLE_PRIVILEGES WHERE ROLE_ID ="+("2".equals(isAdmin)?_baseConstants.ZCB_REGIONAL_ROLE_ID:_baseConstants.ZCB_ROLE_ID)+" AND  IS_CONTENT = 0)";
								query ="";
							}
						}
						else
						{
							//priv= SQLUtil.getColumnValue("ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING", "BASIC_PRIVILEGES", "PP_ID", _baseConstants.ZCB_ROLE_ID);
							priv = null;
							query="SELECT * FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND IS_CONTENT = 0 AND " +
							"PRIVILEGE_ID IN ("+priv+" )";
						}
					}
                else
                {
                			query = "SELECT IS_CONTENT,PRIVILEGE_ID FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND ( IS_CONTENT = 0 || IS_CONTENT = 1)";
                }
				
				/*if(ModuleUtil.cmImplemented() && !_baseConstants.IS_ACCOUNT_ENABLED && StringUtil.isValid(_baseConstants.ACCOUNT_PRIVILEGES)){//P_CM_B_68658
					
					query+=" AND PRIVILEGE_ID NOT IN("+_baseConstants.ACCOUNT_PRIVILEGES+")";
				}*/
				
				
				rs = QueryUtil.getResult(query, new Object[] { id });

				Map<String, Privilege> privileges = new HashMap<String, Privilege>();
				Privilege privilege;
				String privId, moduleCode;
				Map<String, PrivilegedContent> contents = new HashMap<String, PrivilegedContent>();
				PrivilegedContentMgr cMgr = new PrivilegedContentMgr();
				PrivilegedContent privilegeContent;
				ModuleMap modules = null;
				if(rs != null)
				{
					
					PrivilegeMap privilegeMap = null;
					Privileges _privileges = null;
					if(otherParams != null && otherParams.containsKey("privileges"))
						_privileges = (Privileges)otherParams.get("privileges");
					
					if(_privileges !=null)
					{
						privilegeMap = _privileges.getPrivileges();
						modules = _privileges.getModules();
					}
					else
					{
						privilegeMap = PrivilegesMgr.getInstance().getPrivilegeMap();
						modules = PrivilegesMgr.getInstance().getModuleMap();
					}
					while(rs.next())
					{
						privId = rs.getString("PRIVILEGE_ID");
						
						if("0".equals(rs.getString("IS_CONTENT")))
						{	
							privilege = privilegeMap.getPrivilege(privId);
							if(privilege != null)
							{
								moduleCode = privilege.getModuleCode();
								if(modules.getModules().containsKey(privilege.getModuleID()) && (!StringUtil.isValid(moduleCode) || ModuleUtil.isModuleImplemented(moduleCode)))
								{
									privileges.put(privId, privilege);
								}
							}

						}else if("1".equals(rs.getString("IS_CONTENT")))
						{
							privilegeContent = cMgr.getPrivilegedContent(privId);
							contents.put(privId, privilegeContent);
						}
					}
				}
				role.setContents(contents);
				role.setPrivileges(privileges);
			}catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error("Exception while preparing UserRole", ex);
			}
		}
		return role;
	}
	
	public LinkedHashMap<String, List<Privilege>> getRoleBasePrivilege(UserRole role)
	{
		return getRoleBasePrivilege(role, null);
	}
	
	public LinkedHashMap<String, List<Privilege>> getRoleBasePrivilege(UserRole role,Map<String,Object>otherParams)
	{
		LinkedHashMap<String, List<Privilege>> roleMap = new LinkedHashMap<String, List<Privilege>>();
		if(role != null)
		{	
			try
			{	
				ModuleMap moduleMap = null;
				if(otherParams !=null)
					moduleMap = ((Privileges)otherParams.get("privileges")).getModules();
				else
					moduleMap = PrivilegesMgr.getInstance().getPrivileges().getModules();
				
				List<Privilege> PrivilegesList = null;
				Map<String,Privilege> _privilges  = role.getPrivileges();
				Iterator<Module> moduleItr = moduleMap.getAll();
				while(moduleItr.hasNext())
				{	
					boolean isPriv = false;
					PrivilegesList = new ArrayList<Privilege>();
					Module	module = (Module) moduleItr.next();
					String moduleId = module.getModuleID();
					List<String> privIds =moduleMap.getModulePrivilege(moduleId);
					Iterator<String> privItr = privIds.iterator();
					while(privItr.hasNext())
					{
						String privId = privItr.next();
						if(_privilges.containsKey(privId))
						{    
							Privilege priv = _privilges.get(privId);
							//if(moduleId.equals(priv.getModuleID()) && role.isAdminRole()==priv.isAdminPrivilege())
							if(moduleId.equals(priv.getModuleID()) && priv.getPrivilegeFor().contains(role.isAdminRole()))
							{
								PrivilegesList.add(priv);
								isPriv = true;
							}
						}
					}
					if(isPriv)
					{
						roleMap.put(moduleId, PrivilegesList);
					}
				}
			}
			catch(Exception e)
			{
				logger.error("Exception while getting privileges by role", e);
			}
		}
		return roleMap;

	}

	public String getDefaultRoleID(String userNo)
	{
		Connection con = null;
		String roleID = "";
		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			int isAdmin = 0;
			boolean isCorporate = BaseUtils.isCorporateUser(userNo);
			if(isCorporate)
			{
				isAdmin = 1;
			}
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM ROLE WHERE IS_DEFAULT = 1 AND IS_ADMIN = ?");
			pstmt.setInt(1, isAdmin);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				roleID = rs.getString("ROLE_ID");
			}
			SQLUtil.closeStmt(pstmt);
			SQLUtil.closeResultSet(rs);
		}catch(Exception exp)
		{
			logger.error("Exception. Unable to get Default UserRole", exp);
		}finally
		{
			DBConnectionManager.getInstance().freeConnection(con);
		}
		return roleID;
	}

	public boolean isRole(String roleName)
	{
		boolean flag = false;
		if(roleName != null)
		{
			Connection con = null;
			try
			{
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM ROLE WHERE NAME = ?");
				pstmt.setString(1, roleName.toLowerCase());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					flag = true;
				}
				SQLUtil.closeStmt(pstmt);
				SQLUtil.closeResultSet(rs);

			}catch(Exception exp)
			{
				logger.error("Exception. Unable to validate Role Name : " + roleName, exp);
			}finally
			{
				DBConnectionManager.getInstance().freeConnection(con);
			}
		}
		return flag;
	}

	public boolean isAnotherRoleWithName(String roleName, String roleID)
	{
		boolean flag = false;
		if(roleName != null && roleID != null)
		{
			Connection con = null;
			try
			{
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM ROLE WHERE LOWER(NAME) LIKE ? AND ROLE_ID <> ?");
				pstmt.setString(1, roleName.toLowerCase());
				pstmt.setString(2, roleID);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					flag = true;
				}
				// rs.close();
				SQLUtil.closeStmt(pstmt);
				SQLUtil.closeResultSet(rs);

			}catch(Exception exp)
			{
				logger.error("Exception. Unable to validate Role Name : " + roleName, exp);
			}finally
			{
				DBConnectionManager.getInstance().freeConnection(con);
			}
		}
		return flag;
	}

	public String createNewUserRole(UserRole role)
	{
		String id = "-1";
		Connection con = null;
		try
		{
			// System.out.println("inside createNewUserRole()............UserRoleMgr.java");
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO ROLE (NAME, IS_ADMIN,FRANCHISEE_NO) VALUES(?,?,?)");
			// System.out.println("inside createNewUserRole()............UserRoleMgr.java=" + role.getRoleName());
			pstmt.setString(1, role.getRoleName());
			pstmt.setInt(2, role.isAdminRole());
			pstmt.setString(3, role.getFranchiseeNo());
			pstmt.executeUpdate();
			SQLUtil.closeStmt(pstmt);

			Statement st1 = con.createStatement();
			ResultSet rs = st1.executeQuery("SELECT LAST_INSERT_ID() lid FROM ROLE");
			if(rs.next())
			{
				id = rs.getString("lid");
			}
			SQLUtil.closeStmt(st1);
			SQLUtil.closeResultSet(rs);

			// System.out.println("id :::::" + id);
			// / code for inserting Data into MvnforumGroup Table
			String isAdmin = "";
			PreparedStatement pstmtIsAdmin = con.prepareStatement("SELECT IS_ADMIN FROM ROLE WHERE ROLE_ID = ?");
			pstmtIsAdmin.setString(1, id);
			ResultSet rsIsAdmin = pstmtIsAdmin.executeQuery();
			while(rsIsAdmin.next())
			{
				isAdmin = rsIsAdmin.getString("IS_ADMIN");
				// System.out.println("isAdmin===" + isAdmin);
			}
			SQLUtil.closeStmt(pstmtIsAdmin);
			SQLUtil.closeResultSet(rsIsAdmin);

			if(ModuleUtil.intranetImplemented())
			{
				PreparedStatement pstmtMvnFrmGrp = con.prepareStatement("INSERT INTO mvnforumGroups (GroupID, GroupOwnerID, GroupOwnerName, GroupName) VALUES(?,?,?,?)");
				pstmtMvnFrmGrp.setString(1, id);
				// System.out.println("id :::::11111111111111");
				pstmtMvnFrmGrp.setString(2, isAdmin);
				if(!isAdmin.equals("") && isAdmin.equals("1"))
					pstmtMvnFrmGrp.setString(3, "Admin");
				else
					pstmtMvnFrmGrp.setString(3, "No Admin User");
				pstmtMvnFrmGrp.setString(4, role.getRoleName());
				// System.out.println("id ::::: 333333333333333");
				pstmtMvnFrmGrp.executeUpdate();
				SQLUtil.closeStmt(pstmtMvnFrmGrp);
			}
			// SQLUtil.closeResultSet(rsIsAdmin);
			// //
			// System.out.println("id :::::444444444444444444444444");
			PreparedStatement st = con.prepareStatement("INSERT INTO ROLE_PRIVILEGES (ROLE_ID, PRIVILEGE_ID, IS_CONTENT) VALUES(?, ?, ?)");
			Iterator privIt = role.getPrivileges().values().iterator();
			while(privIt != null && privIt.hasNext())
			{
				Privilege priv = (Privilege) privIt.next();
				st.setString(1, id);
				// System.out.println("id==== " + id);
				// System.out.println("privilege id==== " + priv.getPrivilegeID());
				st.setString(2, priv.getPrivilegeID());
				st.setInt(3, 0);
				st.executeUpdate();
			}
			SQLUtil.closeStmt(st);
			// SQLUtil.closeResultSet(rsIsAdmin);
			/* Iterator contentIt = role.getContents().values().iterator(); while (contentIt != null && contentIt.hasNext()) { PrivilegedContent privContent = (PrivilegedContent)contentIt.next(); st.setString(1, id); st.setString(2, privContent.getPrivilegedContentID()); st.setInt(3, 1); st.executeUpdate(); } */
			con.commit();
			// System.out.println("id :::::555555555555555");
		}

		catch(Exception exp)
		{
			try
			{
				con.rollback();
			}
			catch(SQLException se)
			{
				logger.error("Exception while roolback",se);
			}
			logger.error("Exception. Unable to create new UserRole",exp);
		}finally
		{
			DBConnectionManager.getInstance().freeConnection(con);
		}
		return id;
	}

	/**
	 * Assign Forum Catagories, which are viewable to all users, to new added Role
	 * 
	 * @param roleId the roleId of new added Role
	 * @exception ConnectionException, SQLException if a database access error occurs
	 */
	public boolean assignCatagoryToNewRole(String roleId)
	{
		Connection con;
		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			con.setAutoCommit(false);
		}catch(SQLException exp)
		{
			exp.printStackTrace();
			return false;
		}
		PreparedStatement pstmtCategoryGroup = null;
		Statement st1 = null;
		try
		{
			st1 = con.createStatement();
			ResultSet rs = st1.executeQuery("SELECT CategoryID FROM mvnforumCategory WHERE ViewAll = 'Y'");
			Vector viewAllCategories = new Vector();
			while(rs.next())
			{
				viewAllCategories.addElement(rs.getString("CategoryID"));
			}

			if(viewAllCategories.size() != 0)
			{
				pstmtCategoryGroup = con.prepareStatement("INSERT INTO  mvnforumCategoryGroup(CategoryID, GroupID) VALUES (?,?)");
				for(int count = 0; count < viewAllCategories.size(); count++)
				{
					pstmtCategoryGroup.setInt(1, Integer.parseInt((String) viewAllCategories.elementAt(count)));
					pstmtCategoryGroup.setInt(2, Integer.parseInt(roleId));
					pstmtCategoryGroup.executeUpdate();
				}
			}
			con.commit();

			return true;
		}catch(SQLException e)
		{
			e.printStackTrace();
			try
			{
				con.rollback();
				//DBConnectionManager.getInstance().freeConnection(con);
				Debug.print(e);
				return false;
			}catch(Exception exp)
			{
				return false;
			}

		}finally
		{
			try
			{
				if(pstmtCategoryGroup != null)
				{
					pstmtCategoryGroup.close();
					pstmtCategoryGroup = null;
				}
				if(st1 != null)
				{
					st1.close();
					st1 = null;
				}
				if(con != null)
				{
					DBConnectionManager.getInstance().freeConnection(con);
				}
			}catch(Exception exp)
			{
				return false;
			}
		}

	}

	public boolean addRoleContent(UserRole role, String contentType, String[] contentIDs)
	{
		boolean flag = true;
		Connection con = null;
		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			con.setAutoCommit(false);

			// SMC-20130604-048:starts
			removeExistingRoleContent(role, contentType);
			// String maxString = SQLUtil.getColumnValue("ROLE_PRIVILEGES", "MAX(RPID)", "1","1");
			String maxString = SQLUtil.getColumnValue("ROLE_PRIVILEGES", "MAX(RPID)", "1", "1"); // For Product_Seperation_BL By Amar Singh.
			long maxValue = 1;
			if(StringUtil.isValid(maxString))
			{
				maxValue = Long.parseLong(maxString);
			}
			// SMC-20130604-048:ends

			if(contentIDs != null)
			{
				PreparedStatement st = con.prepareStatement("INSERT INTO ROLE_PRIVILEGES (RPID,ROLE_ID, PRIVILEGE_ID, IS_CONTENT) VALUES(?, ?, ?,?)");
				for(int i = 0; i < contentIDs.length; i++)
				{
					st.setLong(1, ++maxValue);
					st.setString(2, role.getRoleID());
					st.setString(3, contentIDs[i]);
					st.setInt(4, 1);
					st.executeUpdate();
				}
				SQLUtil.closeStmt(st);
				// SQLUtil.closeResultSet(rsIsAdmin);
			}

			con.commit();

		}

		catch(Exception exp)
		{
			flag = false;
			try
			{
				con.rollback();
			}catch(SQLException se)
			{
				Debug.print(se);
			}
			Debug.print(exp);
			Debug.println("Exception. Unable to add UserRole content");
		}finally
		{
			try
			{
				DBConnectionManager.getInstance().freeConnection(con);
			}catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return flag;
	}

	public boolean removeExistingRoleContent(UserRole role, String contentType)
	{
		boolean flag = true;
		Connection con = null;
		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			con.setAutoCommit(false);
			String pContentIDs = "";
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM PRIVILEGED_CONTENT WHERE CONTENT_TYPE = ?");
			pstmt.setString(1, contentType);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				pContentIDs = pContentIDs + rs.getString("PRIVILEGED_CONTENT_ID");
				if(!rs.isLast())
				{
					pContentIDs = pContentIDs + ",";
				}
			}
			SQLUtil.closeStmt(pstmt);
			SQLUtil.closeResultSet(rs);
			if(pContentIDs != null && !pContentIDs.equals(""))
			{
				PreparedStatement st1 = con.prepareStatement("DELETE FROM ROLE_PRIVILEGES WHERE ROLE_ID = " + role.getRoleID() + " AND IS_CONTENT = 1 AND PRIVILEGE_ID IN (" + pContentIDs + ")");

				st1.executeUpdate();
				SQLUtil.closeStmt(pstmt);
				// SQLUtil.closeResultSet(rsIsAdmin);

			}

			con.commit();

		}

		catch(Exception exp)
		{
			flag = false;
			try
			{
				con.rollback();
			}catch(SQLException se)
			{
				Debug.print(se);
			}
			Debug.print(exp);
			Debug.println("Exception. Unable to remove UserRole content");
		}finally
		{
			try
			{
				DBConnectionManager.getInstance().freeConnection(con);
			}catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return flag;
	}

	public boolean modifyUserRole(UserRole role)
	{
		boolean flag = true;
		Connection con = null;
		try
		{
			// System.out.println("inside .....modifyUserRole()...UserRoleMapper.java");
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			con.setAutoCommit(false);

			PreparedStatement pstmt = con.prepareStatement("UPDATE ROLE SET NAME = ?, IS_ADMIN = ? WHERE ROLE_ID = ?");
			pstmt.setString(1, role.getRoleName());
			pstmt.setInt(2, role.isAdminRole());
			pstmt.setString(3, role.getRoleID());
			pstmt.executeUpdate();

			PreparedStatement pstmtmvnforum = null;
			if(ModuleUtil.intranetImplemented())
			{

				pstmtmvnforum = con.prepareStatement("UPDATE mvnforumGroups SET GroupName = ?, GroupOwnerID = ?, GroupOwnerName=?  WHERE GroupID = ?");
				pstmtmvnforum.setString(1, role.getRoleName());
				int ownerId = role.isAdminRole();
				pstmtmvnforum.setInt(2, role.isAdminRole());
				if(ownerId == 1)
					pstmtmvnforum.setString(3, "Admin");
				else
					pstmtmvnforum.setString(3, "No Admin User");
				pstmtmvnforum.setString(4, role.getRoleID());
				pstmtmvnforum.executeUpdate();
			}
			PreparedStatement st1 = con.prepareStatement("DELETE FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND IS_CONTENT = 0");
			st1.setString(1, role.getRoleID());
			st1.executeUpdate();

			PreparedStatement st = con.prepareStatement("INSERT INTO ROLE_PRIVILEGES (ROLE_ID, PRIVILEGE_ID, IS_CONTENT) VALUES(?, ?, ?)");
			Iterator privIt = role.getPrivileges().values().iterator();
			while(privIt != null && privIt.hasNext())
			{
				Privilege priv = (Privilege) privIt.next();
				st.setString(1, role.getRoleID());
				st.setString(2, priv.getPrivilegeID());
				st.setInt(3, 0);
				st.executeUpdate();

				if(priv.getPrivilegeID().equals("10123"))
				{
					updateRole(role.getRoleID());
				}
			}

			/* Iterator contentIt = role.getContents().values().iterator(); while (contentIt != null && contentIt.hasNext()) { PrivilegedContent privContent = (PrivilegedContent)contentIt.next(); st.setString(1, role.getRoleID()); st.setString(2, privContent.getPrivilegedContentID()); st.setInt(3, 1); st.executeUpdate(); } */

			con.commit();
			SQLUtil.closeStmt(st);
			// SQLUtil.closeResultSet(rsIsAdmin);
			SQLUtil.closeStmt(st1);
			// SQLUtil.closeResultSet(rsIsAdmin);
			SQLUtil.closeStmt(pstmtmvnforum);
			// SQLUtil.closeResultSet(rsIsAdmin);
			SQLUtil.closeStmt(pstmt);
			// SQLUtil.closeResultSet(rsIsAdmin);

		}

		catch(Exception exp)
		{
			flag = false;
			try
			{
				con.rollback();
			}catch(SQLException se)
			{
				Debug.print(se);
			}
			Debug.print(exp);
			Debug.println("Exception. Unable to create new UserRole");
		}finally
		{
			try
			{
				DBConnectionManager.getInstance().freeConnection(con);
			}catch(Exception exp)
			{
				Debug.print(exp);
			}
		}
		return flag;

	}
	/**
	 * P_DIV_REG_EMP_ADMIN_PRIV
	 * checks whether the role is assigned to a user of different division
	 * @param roleId
	 * @param flag
	 * @return
	 */
public boolean divisionalUserRoleCheck(String roleId,boolean flag)
{
	boolean success=true;
	String query;
	if(StringUtil.isvalidInteger(roleId))
	{
		com.home.builderforms.sqlqueries.ResultSet rs = null;
		if(flag==true){
			query="SELECT * FROM USER_ROLES WHERE ROLE_ID= ? AND USER_NO NOT  IN (SELECT USER_NO FROM USERS_DIVISIONS_MAPPING WHERE DIVISION_ID IN( ?));";
			}
		else{
			 query=  "SELECT * FROM USER_ROLES WHERE ROLE_ID= ? AND USER_NO NOT  IN (SELECT C.USER_NO FROM FRANCHISEE_USERS FU,";
			query= query + "FRANCHISEE F,USERS C LEFT JOIN LOCATION_DIVISIONS_MAPPING LDM ON C.FRANCHISEE_NO=LDM.FRANCHISEE_NO,";
			query= query + "FIM_OWNERS FO,OWNERS OW,FIM_USERS FM WHERE FM.FRANCHISEE_NO = F.FRANCHISEE_NO AND";
			query= query + " C.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND OW.OWNER_ID=FO.FRANCHISE_OWNER_ID AND";
			query= query + " OW.FRANCHISEE_NO= FU.FRANCHISEE_NO AND FM.FRANCHISEE_USER_NO=FU.FRANCHISEE_USER_NO AND C.IS_DELETED='N' AND";
			query= query + "   F.STATUS!='0' AND FU.STATUS='1' AND C.STATUS!='0'   AND LDM.DIVISION_ID IN(?));";
		}
		try
		{
			rs = QueryUtil.getResult(query,new String[] { roleId,(String)StrutsUtil.getHttpSession().getAttribute("divisionIds") } );
			if(rs != null)
			{
				while(rs.next())
				{
					 String userNo = rs.getString("USER_NO");
					 success=false;
				}
			}
		}catch(Exception exp)
		{
			logger.error("Exception while getting Users corresponding to role with id:" + roleId, exp);
		}
	}
	return success;
}
/**
 * P_DIV_REG_EMP_ADMIN_PRIV
 * checks whether the role is assigned to a user of different area
 * @param roleId
 * @param flag
 * @return
 */
public boolean regionalUserRoleCheck(String roleId,boolean flag)
{
	boolean success=true;
	String query;

	if(StringUtil.isvalidInteger(roleId))
	{
		com.home.builderforms.sqlqueries.ResultSet rs = null;
	if(flag){
		 query= "SELECT * FROM USER_ROLES WHERE ROLE_ID= ? AND USER_NO NOT  IN (SELECT U.USER_NO FROM USERS U LEFT JOIN FRANCHISEE F ON U.FRANCHISEE_NO=F.FRANCHISEE_NO  WHERE F.AREA_ID IN(?))";
			}
	else
	{
		query=  "SELECT * FROM USER_ROLES WHERE ROLE_ID= ? AND USER_NO NOT  IN (SELECT C.USER_NO FROM FRANCHISEE_USERS FU,";
		query= query + "FRANCHISEE F,USERS C LEFT JOIN LOCATION_DIVISIONS_MAPPING LDM ON C.FRANCHISEE_NO=LDM.FRANCHISEE_NO,";
		query= query + "FIM_OWNERS FO,OWNERS OW,FIM_USERS FM WHERE FM.FRANCHISEE_NO = F.FRANCHISEE_NO AND";
		query= query + " C.USER_IDENTITY_NO = FU.FRANCHISEE_USER_NO AND OW.OWNER_ID=FO.FRANCHISE_OWNER_ID AND";
		query= query + " OW.FRANCHISEE_NO= FU.FRANCHISEE_NO AND FM.FRANCHISEE_USER_NO=FU.FRANCHISEE_USER_NO AND C.IS_DELETED='N' AND";
		query= query + "   F.STATUS!='0' AND FU.STATUS='1' AND C.STATUS!='0'   AND F.AREA_ID IN(?));";
	}
	try
		{
			rs = QueryUtil.getResult(query.toString(),new String[] { roleId,(String)StrutsUtil.getHttpSession().getAttribute("regionId") } );
			if(rs != null)
			{
				while(rs.next())
				{
					 String userNo = rs.getString("USER_NO");
					 success=false;
				}
			}
		}catch(Exception exp)
		{
			logger.error("Exception while getting Users corresponding to role with id:" + roleId, exp);
		}
	}
	return success;
}
	public boolean deleteUserRole(String roleId)
	{
		boolean success = false;
		if(StringUtil.isvalidInteger(roleId))
		{
			List<String> queries = new ArrayList<String>();
			queries.add("DELETE FROM ROLE WHERE ROLE_ID =" + roleId);
			queries.add("DELETE FROM mvnforumGroups WHERE GroupID =" + roleId);
			queries.add("DELETE FROM mvnforumCategoryGroup WHERE GroupID =" + roleId);
			queries.add("DELETE FROM mvnforumGroupForum WHERE GroupID =" + roleId);
			queries.add("DELETE FROM ROLE_PRIVILEGES WHERE ROLE_ID =" + roleId);
			/*//BB-20151201-455  Starts
			if(MultiTenancyUtil.getTenantConstants().IS_AD_INTEGRATION_ENABLED){
				 queries.add("DELETE FROM AD_GROUP_ROLE_MAPPING WHERE ROLE_ID =" + roleId);
	        }//BB-20151201-455  Ends
			try
			{
				int execCount = QueryUtil.batchUpdate(queries, null);
				if(MultiTenancyUtil.getTenantConstants().IS_AD_INTEGRATION_ENABLED){//BB-20151201-455
				success = execCount == 6 ? true : false;
				}else{
				success = execCount == 5 ? true : false;
				}
			}catch(AppException appEx)
			{
				logger.error("Exception while deleting a role with Id:" + roleId, appEx);
			}*/
		}
		return success;
	}

	public HashMap<String, String> getUsersForThisRole(String roleID)
	{
		HashMap<String, String> users = new HashMap<String, String>();
		if(StringUtil.isvalidInteger(roleID))
		{
			com.home.builderforms.sqlqueries.ResultSet rs = null;
			try
			{
				rs = QueryUtil.getResult("SELECT * FROM USERS, USER_ROLES WHERE ROLE_ID = ? AND USERS.USER_NO = USER_ROLES.USER_NO", new String[] { roleID });
				if(rs != null)
				{
					while(rs.next())
					{
						String userNo = rs.getString("USER_NO");
						users.put(userNo, userNo);
					}
				}

			}catch(Exception exp)
			{
				logger.error("Exception while getting Users corresponding to role with id:" + roleID, exp);
			}
		}
		return users;
	}

	public HashMap<String, String> getUsersForThisRole(String roleID, String franchiseeNo)
	{
		HashMap<String, String> users = new HashMap<String, String>();
		if(StringUtil.isvalidInteger(roleID))
		{
			com.home.builderforms.sqlqueries.ResultSet rs = null;
			try
			{
				StringBuilder query = new StringBuilder("SELECT DISTINCT USERS.USER_NO FROM USERS, USER_ROLES,FIM_USERS WHERE ROLE_ID = ? AND USERS.USER_NO = USER_ROLES.USER_NO AND USER_IDENTITY_NO=FRANCHISEE_USER_NO");

				if(StringUtil.isValid(franchiseeNo) && !"1".equals(franchiseeNo))
				{
					String muid = "";
					muid = getMuidForFranchiseeNo(franchiseeNo);
					muid = StringUtil.toCommaSeparatedWithQuotes(muid.split(","));
					query.append(" AND (USERS.FRANCHISEE_NO=").append(franchiseeNo).append(" OR FIM_USERS.MUID_VALUE IN (").append(muid).append("))");
				}

				rs = QueryUtil.getResult(query.toString(), new String[] { roleID });
				if(rs != null)
				{
					while(rs.next())
					{
						String userNo = rs.getString("USER_NO");
						users.put(userNo, userNo);
					}
				}

			}catch(Exception exp)
			{
				logger.error("Exception while getting Users corresponding to role with id:" + roleID, exp);
			}
		}
		return users;
	}

	/**
	 * Check Role exists for a particular role level or not?
	 * 
	 * @param roleName
	 * @param isAdmin
	 * @return
	 */
	public boolean isRole(String roleName, int isAdmin)
	{
		boolean roleExists = false;

		if(StringUtil.isValid(roleName))
		{
			com.home.builderforms.sqlqueries.ResultSet rs = QueryUtil.getResult("SELECT * FROM ROLE WHERE LOWER(NAME) LIKE ? AND IS_ADMIN=?", new Object[] { roleName.toLowerCase(), isAdmin });

			try
			{
				if(rs != null && rs.next())
				{
					roleExists = true;
				}
			}catch(Exception exp)
			{
				logger.error("Exception while checking this role name:'" + roleName + "' existance in the system", exp);
			}
		}

		return roleExists;
	}

	// to check Role Exists or Not for a Particular Role Level in modify case
	public boolean isAnotherRoleWithName(String roleName, String roleID, int isAdmin)
	{
		boolean flag = false;
		if(roleName != null && roleID != null)
		{
			Connection con = null;
			try
			{
				con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM ROLE WHERE LOWER(NAME) LIKE ? AND ROLE_ID <> ? AND IS_ADMIN=?");
				pstmt.setString(1, roleName.toLowerCase());
				pstmt.setString(2, roleID);
				pstmt.setInt(3, isAdmin);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					flag = true;
				}
				// rs.close();
				SQLUtil.closeStmt(pstmt);
				SQLUtil.closeResultSet(rs);

			}catch(Exception exp)
			{
				Debug.print(exp);
				Debug.println("Exception. Unable to validate Role Name : " + roleName);

			}finally
			{
				try
				{
					DBConnectionManager.getInstance().freeConnection(con);
				}catch(Exception exp)
				{
					Debug.print(exp);
				}
			}
		}
		return flag;
	}

	public void updateRole(String roleID)
	{
		String user_no = "";
		Connection con = null;
		PreparedStatement ptmt1 = null;
		PreparedStatement ptmt2 = null;
		ResultSet rs = null;

		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			ptmt1 = con.prepareStatement("SELECT * FROM mvnforumMemberGroup WHERE GroupID= ?");
			ptmt2 = con.prepareStatement("UPDATE mvnforumMemberPermission SET Permission= ? WHERE MemberID =?");
			ptmt1.setString(1, roleID);
			rs = ptmt1.executeQuery();
			while(rs.next())
			{
				user_no = rs.getString("MemberID");
				ptmt2.setString(1, "100");
				ptmt2.setString(2, user_no);
				ptmt2.executeUpdate();
			}
		}catch(Exception exp)
		{
			Debug.print(exp);
		}finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
				if(ptmt1 != null)
				{
					ptmt1.close();
					ptmt1 = null;
				}
				if(ptmt2 != null)
				{
					ptmt2.close();
					ptmt2 = null;
				}
				DBConnectionManager.getInstance().freeConnection(con);
			}catch(Exception ep)
			{
				Debug.print(ep);
			}
		}
	}

	// P_INT_B_54391 function added
	/**
	 * assign all forums to the newly create role if forums can be viewed by all users
	 * 
	 * @param roleId
	 * @return true if successfully assigned
	 */
	public boolean assignForumToNewRole(String roleId, int isAdmin)
	{
		Connection con;
		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			con.setAutoCommit(false);
		}catch(SQLException exp)
		{
			exp.printStackTrace();
			return false;
		}
		PreparedStatement pstmtForumGroup = null;
		Statement st1 = null;
		// bug#71240,start
		int[] perm;
		if(isAdmin == 1)
		{
			perm = new int[] { 105, 2000, 2001, 2100, 2101, 2102, 2103, 2104, 2108, 2109 };// P_INTRANET_E_FORUMPERMISSIONS_1001
		}else
		{
			perm = new int[] { 105, 2100, 2101, 2102, 2103, 2104, 2108, 2109 };
		}
		// bug#71240,end
		try
		{
			st1 = con.createStatement();
			ResultSet rs = st1.executeQuery("SELECT ForumID FROM mvnforumForum WHERE ViewAll = 'Y'");
			Vector viewAllForums = new Vector();
			while(rs.next())
			{
				viewAllForums.addElement(rs.getString("ForumID"));
			}

			if(viewAllForums.size() != 0)
			{
				pstmtForumGroup = con.prepareStatement("INSERT INTO  mvnforumGroupForum(GroupID,ForumID, Permission) VALUES (?,?,?)");
				for(int count = 0; count < viewAllForums.size(); count++)
				{
					for(int j = 0; j < perm.length; j++)
					{
						pstmtForumGroup.setInt(1, Integer.parseInt(roleId));
						pstmtForumGroup.setInt(2, Integer.parseInt((String) viewAllForums.elementAt(count)));
						pstmtForumGroup.setInt(3, perm[j]);// P_INTRANET_E_FORUMPERMISSIONS_1001
						pstmtForumGroup.executeUpdate();
					}
				}
			}
			con.commit();

			return true;
		}catch(SQLException e)
		{
			e.printStackTrace();
			try
			{
				con.rollback();
				//DBConnectionManager.getInstance().freeConnection(con);
				Debug.print(e);
				return false;
			}catch(Exception exp)
			{
				return false;
			}

		}finally
		{
			try
			{
				if(pstmtForumGroup != null)
				{
					pstmtForumGroup.close();
					pstmtForumGroup = null;
				}
				if(st1 != null)
				{
					st1.close();
					st1 = null;
				}
				if(con != null)
				{
					DBConnectionManager.getInstance().freeConnection(con);
				}
			}catch(Exception exp)
			{
				return false;
			}
		}

	}

	public String getMuidForFranchiseeNo(String franchiseeNo)
	{
		StringBuilder muids = new StringBuilder();
		com.home.builderforms.sqlqueries.ResultSet result = null;
		String query = "SELECT DISTINCT MUID_VALUE FROM FIM_USERS WHERE FRANCHISEE_NO IN(" + franchiseeNo + ")";// P_ADMIN_B_26820
		try
		{
			result = QueryUtil.getResult(query, null);
			while(result.next())
			{
				muids.append(result.getString("MUID_VALUE")).append(",");
			}
			if(StringUtil.isValid(muids.toString()))
			{
				muids.deleteCharAt(muids.length() - 1);
			}
		}catch(Exception e)
		{
			Debug.print(e);
			Debug.println("Exception inside getMuidForFranchisee()");
		}finally
		{
			QueryUtil.releaseResultSet(result);
			query = null;
		}
		return muids.toString();
	}

	public void updateModuleListforBasePlan(String moduleList)
	{
		Connection con = null;
		try
		{
			con = DBConnectionManager.getInstance().getConnection(Constants.TENANT_NAME, 2000);
			PreparedStatement pstmt = con.prepareStatement("UPDATE MODULES_CG_BASE SET MODULE_LIST = ? WHERE ROLE_ID = 256");
			pstmt.setString(1, moduleList);
			pstmt.executeUpdate();
			SQLUtil.closeStmt(pstmt);

		}catch(Exception exp)
		{
			Debug.print(exp);

		}finally
		{
			try
			{
				DBConnectionManager.getInstance().freeConnection(con);
			}catch(Exception exp)
			{
				Debug.print(exp);
			}
		}

	}
	
	 public String createNewCgRole(UserRole role,String basic,String premium,String planCode,String type,String yearPlanCode)
 	{
 				String id =	"-1";
 			if(!StringUtil.isValid(planCode)){
 					planCode="";
 			}
 			if(!StringUtil.isValid(basic)){
 					basic="";
 			}else{
 				basic=chopmethod(basic);
 			}
 			if(!StringUtil.isValid(premium)){
 					premium="";
 			}else{
 				premium=chopmethod(premium);
 			}
 			try
 			{
 					String query = "INSERT INTO ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING (PP_ID,PACKAGE_NAME, BASIC_PRIVILEGES,PREMIUM_PREVILEGES,PLAN_CODE,STATUS,TYPE,YEAR_PLAN_CODE) VALUES(?, ?, ?, ? , ? ,?,?,?)";
 				      String value[] = {role.getRoleID(), role.getRoleName(), basic, premium , planCode, "NA",type,yearPlanCode};
					QueryUtil.executeInsert(query.toString(), value);
 			}
 			catch (Exception exp)
 			{
 				Debug.print(exp);
 			}
 	      return id;
 	}

     public void  modifyCgRole(UserRole role,String basic,String premium,String planCode,String type,String yearPlanCode)
 	{
			if(!StringUtil.isValid(planCode)){
					planCode="";
			}
			if(!StringUtil.isValid(basic)){
					basic="";	
			}else{
					basic=chopmethod(basic);
			}
			if(!StringUtil.isValid(premium)){
					premium="";
			}else{
				premium=chopmethod(premium);
			}
			String compulsaryBasic=SQLUtil.getColumnValue("MASTER_DATA_FOR_CG", "DATA_VALUE", "DATA_TYPE","1111111111");
			basic=compulsaryBasic+basic;
			try
			{
				if("basic".equals(type))
				{
					String oldBasic=SQLUtil.getColumnValue("ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING", "BASIC_PRIVILEGES", "PP_ID", role.getRoleID());
					List<String> basicList = new ArrayList<String>(Arrays.asList(basic.split(",")));
					List<String> oldbasicList = new ArrayList<String>(Arrays.asList(oldBasic.split(",")));
					basicList.removeAll(oldbasicList);
					List<String> batchQueries = new ArrayList<String>();
					String query="";
					Map<String,HashMap>smap=SQLUtil.getMultipleRow("ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING", new String[]{"PP_ID","BASIC_PRIVILEGES","PREMIUM_PREVILEGES"}, "1", "1");
					query="  UPDATE ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING " +
							" SET PREMIUM_PREVILEGES ='"+premium+"',BASIC_PRIVILEGES='"+basic+"' " +
							" WHERE PP_ID='"+role.getRoleID()+"'";
					batchQueries.add(query);
					String prm="";
					List<String> prmList =new ArrayList<String>();
					for(int i=1;i<smap.size();i++)
					{
					      prm=(String)smap.get(i+"").get("PREMIUM_PREVILEGES");
					      String []prmArray=prm.split(",");
					      String tempString="";
					      prmList=StringUtil.convertToArrayList(prmArray);
					   //   prmList=Arrays.asList(prmArray);
						prmList.removeAll(basicList);
						prm="";
						 for (int j=0;j<prmList.size();j++)
					      {
					    	  		tempString	=  prmList.get(j).trim();
					    	  		prm+=tempString+",";
					      }
						 if(StringUtil.isValid(prm)){
						prm=prm.substring(0, prm.length()-1);
						 }
						query="UPDATE ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING  SET PREMIUM_PREVILEGES ='"+prm+"',BASIC_PRIVILEGES='"+basic+"' " +
								" WHERE PP_ID='"+(String)smap.get(i+"").get("PP_ID")+"'";
						batchQueries.add(query);
					}
					QueryUtil.batchUpdate(batchQueries, null);
				}
				else
				{
					Info updateInfo=new Info();
					updateInfo.set("BASIC_PRIVILEGES",basic);
					updateInfo.set("PREMIUM_PREVILEGES",premium);
					updateInfo.set("PACKAGE_NAME",role.getRoleName());
					updateInfo.set("PLAN_CODE",planCode);
					updateInfo.set("YEAR_PLAN_CODE",yearPlanCode);
					SQLUtil.updateTableData(" ZCUBATOR_CG_PRIVILEGES_PLAN_MAPPING", "PP_ID", role.getRoleID(), updateInfo);
				}
			}
			catch (Exception exp)
			{
					Debug.print(exp);
					Debug.println("Exception. Unable to create new UserRole");
			}
 	}

	
	public String chopmethod(String str) {
        if (str.length() > 0 && str.charAt(str.length()-1)==',') {
          str = str.substring(0, str.length()-1);
        }
        return str;
    }
	
	//BB-20151201-455  Starts
		public void addRoleGroup(String roleID, String[] groupValues) {
			try{
				String query="INSERT INTO AD_GROUP_ROLE_MAPPING (ROLE_ID, GROUP_ID) VALUES(?,?)";
				for(String gid:groupValues){
					if(StringUtil.isValidNew(gid)){
					QueryUtil.executeInsert(query.toString(), new String[]{roleID,gid});
					}
				}
			}
			catch (Exception exp){
				Debug.println("\n Exception. Unable to insert role group mapping addRoleGroup() method");
			}
		}

		public void modifyRoleGroup(String roleID, String[] groupValues) {
	            modifyRoleGroup( roleID, groupValues, null) ;
	        }

	        public void modifyRoleGroup(String roleID, String[] groupValues, String fromWhere) {
			try{
	                      if(StringUtil.isValidNew(fromWhere) && "forSyncing".equalsIgnoreCase(fromWhere) && groupValues!=null && groupValues.length>0)
	                      {
	                          SQLUtil.deleteRecord("AD_GROUP_ROLE_MAPPING", "GROUP_ID", groupValues[0]);
	                      } else {
	                          SQLUtil.deleteRecord("AD_GROUP_ROLE_MAPPING", "ROLE_ID", roleID);
	                      }
	                      if(groupValues!=null && groupValues.length>0 && StringUtil.isValidNew(roleID)){
					addRoleGroup(roleID,groupValues);
			      }
			}
			catch (Exception exp){
				Debug.println("\n\n Exception. in UserRoleMgr>> modifyRoleGroup() UserRole");
			}
		}

		public void getUserRoles(PrivilegeMap privilegeMap, String associatedRoles,Map<String, UserRole> roles,String userNo) {
			UserRoleMgr	roleMgr		= new UserRoleMgr();
			com.home.builderforms.sqlqueries.ResultSet		rst					= null;
			String		id						= null;
			UserRole	userRole				= null;
			try{
				String query="SELECT ROLE_ID FROM USER_ROLES WHERE USER_NO = ?";
				rst = QueryUtil.getResult(query, new String[]{userNo});
				while (rst.next()) {
					id					=	rst.getString("ROLE_ID");
					userRole			=	roleMgr.getUserRoles(id, privilegeMap);
					userRole.setRoleID(id);
					roles.put(userRole.getRoleID(), userRole);
					Debug.println("Role : " + userRole.getRoleName());
				}

				if(StringUtil.isValidNew(associatedRoles)){
					StringTokenizer stoken  = new StringTokenizer(associatedRoles,",");
					String token  = "";
					while(stoken.hasMoreTokens()){
						token  = stoken.nextToken();
						id					=	token;
						userRole			=	roleMgr.getUserRoles(id, privilegeMap);
						userRole.setRoleID(id);
						roles.put(userRole.getRoleID(), userRole);
					}
				}
			}
			catch (Exception exp){
				Debug.print(exp);
				Debug.println("Exception. in getUserRoles");
			}
		}

		public UserRole getUserRoles(String id, PrivilegeMap privilegeMap) {
			UserRole role				=	UserRole.newInstance();
			if (id != null) {
				com.home.builderforms.sqlqueries.ResultSet rs	= null;
				role.setRoleID(id);
				try{
					String query="SELECT NAME,IS_ADMIN,IS_DEFAULT,ASSIGNED_TO FROM ROLE WHERE ROLE_ID = ?";
					rs = QueryUtil.getResult(query, new String[]{id});  
					if (rs.next()){
						role.setRoleName(rs.getString("NAME"));
						role.setAdminFlag(Integer.parseInt(rs.getString("IS_ADMIN")));
						role.setDefaultFlag(Integer.parseInt(rs.getString("IS_DEFAULT")));
						role.setAssignedTo(rs.getString("ASSIGNED_TO"));
					}
					HashMap privileges				=	new	HashMap();
					rs=null;
					query="SELECT PRIVILEGE_ID FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND IS_CONTENT = 0";
					rs = QueryUtil.getResult(query, new String[]{id}); 
					while (rs.next()) {
						String privId = rs.getString("PRIVILEGE_ID");
						Privilege privilege = privilegeMap.getPrivilege(privId);
						privileges.put(privId, privilege);
					}
					role.setPrivileges(privileges);
					rs=null;
					HashMap contents				=	new	HashMap();
					query="SELECT PRIVILEGE_ID FROM ROLE_PRIVILEGES WHERE ROLE_ID = ? AND IS_CONTENT = 1";
					rs = QueryUtil.getResult(query, new String[]{id}); 
					PrivilegedContentMgr	cMgr	= new PrivilegedContentMgr();
					while (rs.next()) {
						String privId = rs.getString("PRIVILEGE_ID");
						PrivilegedContent privilegeContent = cMgr.getPrivilegedContent(privId);
						contents.put(privId, privilegeContent);
					}
					role.setContents(contents);
				}
				catch (Exception exp){
					Debug.print(exp);
					Debug.println("Exception. in getUserRoles with id : " + id);

				}
			}
			return role;
		}//BB-20151201-455  Ends
}
