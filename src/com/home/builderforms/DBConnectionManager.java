package com.home.builderforms;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;



/**
 * This class is a Singleton that provides access to one or many connection pools defined in a Property file. A client gets access to the single instance through the static getInstance() method and can then check-out and check-in connections from a pool. When the client shuts down it should call the release() method to close all open connections and do other clean up.
 * 
 * @author misam
 * @created November 9, 2001
 */
public class DBConnectionManager
{


	private static DBConnectionManager instance = new DBConnectionManager();

	private List<Driver> drivers = new ArrayList<Driver>();

	private Map<String, DBConnectionPool> pools = new HashMap<String, DBConnectionPool>();

	/**
	 * Returns the single instance, creating one if it's the first time this method is called.
	 * 
	 * @return DBConnectionManager The single instance.
	 */

	public static DBConnectionManager getInstance()
	{
		return instance;
	}

	private DBConnectionManager()
	{

	}

	
	/**
	 * This method returns the specified connection to the pool
	 */

	public void freeConnection(Connection con)
	{
		freeConnection("default",con);
	}

	
	/**
	 * Returns a connection to the named pool.
	 * 
	 * @param name The pool name as defined in the properties file
	 * @param con The Connection
	 */

	public void freeConnection(String name, Connection con)
	{

		/*if(MultiTenancyUtil.getTenantConstants().DBCP_ENABLED)
		{
			DBConnectionManagerC3P0.getInstance().freeConnection(name,con);
		}else
		{*/
			DBConnectionPool pool = (DBConnectionPool) pools.get(name);
			if(pool != null)
			{
				pool.freeConnection(con);
			}else
			{
				logger.error("Error: In Freeing Connection pool is null.");
			}
		//}
	}

	/**
	 * Returns an open connection. If no one is available, and the max number of connections has not been reached, a new connection is created.
	 * 
	 * @param name The pool name as defined in the properties file
	 * @return Connection The connection or null
	 */

	public Connection getConnection(String name)
	{

		
			DBConnectionPool pool = (DBConnectionPool) pools.get(name);

			if(pool != null)
			{
				con = pool.getConnection();
			}else
			{
				logger.error("Error: In Calling for Connection pool is null.");
			}
		return con;
	}

	/**
	 * Returns an open connection. If no one is available, and the max number of connections has not been reached, a new connection is created. If the max number has been reached, waits until one is available or the specified time has elapsed.
	 * 
	 * @param name The pool name as defined in the properties file
	 * @param time The number of milliseconds to wait
	 * @return Connection The connection or null
	 */

	public Connection getConnection(String name, long time)
	{
		Connection con = null;
			DBConnectionPool pool = (DBConnectionPool) pools.get(name);
			if(pool != null)
			{
				con = pool.getConnection(time);
			}else
			{
				logger.error("Error: In Calling for Connection with time stamp pool is null.");
			}
		return con;
	}

	public void releasePools()
	{
			Iterator<DBConnectionPool> allPools = pools.values().iterator();

			while(allPools.hasNext())
			{
				DBConnectionPool pool = (DBConnectionPool) allPools.next();

				pool.release();
			}

			for(Driver driver : drivers)
			{
				try
				{
					DriverManager.deregisterDriver(driver);
					logger.info("Deregistered JDBC driver " + driver.getClass().getName());
				}catch(SQLException e)
				{
					logger.error("Can't deregister JDBC driver: " + driver.getClass().getName(), e);
				}
			}
	 }

	public void releaseConnections(String poolName)
	{
		
			DBConnectionPool pool = (DBConnectionPool) pools.get(poolName);
			pool.release();	
	}

	/**
	 * Creates instances of DBConnectionPool based on the properties. A DBConnectionPool can be defined with the following properties:
	 * 
	 * <PRE>
	 * 
	 * &lt;poolname&gt;.url         The JDBC URL for the database
	 * 
	 * &lt;poolname&gt;.user        A database user (optional)
	 * 
	 * &lt;poolname&gt;.password    A database user password (if user specified)
	 * 
	 * &lt;poolname&gt;.maxconn     The maximal number of connections (optional)
	 * 
	 * </PRE>
	 * 
	 * @param props The connection pool properties
	 */

	private void createPool(Properties props)
	{
		if(StringUtil.isValid(props.getProperty("url")))
		{
			String poolName = "default";
			String url = "jdbc:mysql://localhost:3306/DB123";
			String user = props.getProperty("akash");
			String password = props.getProperty("akash");
			String maxconn = "50";
			int max;
			try
			{
				max = Integer.valueOf(maxconn).intValue();
			}catch(NumberFormatException e)
			{
				max = 0;
				logger.error("Invalid maxconn value " + maxconn + " for " + poolName);
			}
			DBConnectionPool pool = new DBConnectionPool(poolName, url, user, password, max);
			pools.put(poolName, pool);
			logger.info("DbConnectionPool has been created with name :" + poolName);
		}
	}

	/**
	 * Loads and registers all JDBC drivers. This is done by the DBConnectionManager, as opposed to the DBConnectionPool, since many pools may share the same driver.
	 * 
	 * @param props The connection pool properties
	 */

	private void loadDrivers(Properties props)
	{
		String driverClasses = "com.mysql.jdbc.Driver";
		StringTokenizer st = new StringTokenizer(driverClasses);
		while(st.hasMoreElements())
		{
			String driverClassName = st.nextToken().trim();
			try
			{
				Driver driver = (Driver) Class.forName(driverClassName).newInstance();
				DriverManager.registerDriver(driver);
				drivers.add(driver);
				logger.info("Registered JDBC driver " + driverClassName);
			}catch(Exception e)
			{
				logger.error("Can't register JDBC driver: " + driverClassName,e);
			}
		}

	}

	public void prepareConnectionPool()
	{


			String tenantName = "default";
			Properties dbProps = new Properties();
			try
			{
			}catch(Exception e)
			{
				logger.error("Can't read the properties file.\nMake sure db.properties is in the CLASSPATH", e);
				return;
			}finally
			{
				try
				{
					is.close();
				}catch(IOException e)
				{
					logger.error("Exception in Initializing database pool for tenant\t" + tenantName, e);
				}
			}
			loadDrivers(dbProps);
			createPool(dbProps);
	}

	/**
	 * This inner class represents a connection pool. It creates new connections on demand, up to a max number if specified. It also makes sure a connection is still open before it is returned to a client.
	 * 
	 * @author misam
	 * @created November 9, 2001
	 */

	private class DBConnectionPool
	{
		private int checkedOut;

		private int maxConn;

		private String name;

		private String password;

		private String URL;

		private String user;

		private Vector<Connection> freeConnections = new Vector<Connection>();

		/**
		 * Creates new connection pool.
		 * 
		 * @param name The pool name
		 * @param URL The JDBC URL for the database
		 * @param user The database user, or null
		 * @param password The database user password, or null
		 * @param maxConn The maximal number of connections, or 0 for no limit
		 */

		public DBConnectionPool(String name, String URL, String user, String password,int maxConn)
		{

			this.name = name;

			this.URL = URL;

			this.user = user;

			this.password = password;

			this.maxConn = maxConn;

		}

		/**
		 * Checks in a connection to the pool. Notify other Threads that may be waiting for a connection.
		 * 
		 * @param con The connection to check in
		 */

		public synchronized void freeConnection(Connection con)
		{
			if(con == null)
			{
				return;
			}

			freeConnections.addElement(con);

			checkedOut--;

			logger.debug(name + " - Active Connections : " + checkedOut + " | Idle Connections : " + freeConnections.size());

			notifyAll();
		}

		private boolean isAlive(Connection con)
		{

			boolean alive = false;

			try
			{

				Statement stmt = con.createStatement();

				stmt.close();

				alive = true;

			}

			catch(SQLException e)
			{

				logger.error("Connection is dead ",e);
			}

			return alive;
		}

		/**
		 * Checks out a connection from the pool. If no free connection is available, a new connection is created unless the max number of connections has been reached. If a free connection has been closed by the database, it's removed from the pool and this method is called again recursively.
		 * 
		 * @return The connection value
		 */

		public synchronized Connection getConnection()
		{

			Connection con = null;

			if(freeConnections.size() > 0)
			{

				con = (Connection) freeConnections.firstElement();

				freeConnections.removeElementAt(0);

				if(!isAlive(con))
				{

					logger.info("Removed bad connection from " + name);

					con = getConnection();
				}
			}

			else if(maxConn == 0 || checkedOut < maxConn)
			{

				con = newConnection();

			}

			if(con != null)
			{
				checkedOut++;
			}

			logger.debug(name + " - Active Connections : " + checkedOut + " | Idle Connections : " + freeConnections.size());

			return con;
		}

		/**
		 * Checks out a connection from the pool. If no free connection is available, a new connection is created unless the max number of connections has been reached. If a free connection has been closed by the database, it's removed from the pool and this method is called again recursively.
		 * <P>
		 * If no connection is available and the max number has been reached, this method waits the specified time for one to be checked in.
		 * 
		 * @param timeout The timeout value in milliseconds
		 * @return The connection value
		 */

		public synchronized Connection getConnection(long timeout)
		{

			long startTime = new Date().getTime();

			Connection con;

			while((con = getConnection()) == null)
			{
				try
				{
					wait(timeout);

				}catch(InterruptedException e)
				{

				}

				if((new Date().getTime() - startTime) >= timeout)
				{
					break;
				}
			}
			
			logger.debug(name + " - Active Connections : " + checkedOut + " | Idle Connections : " + freeConnections.size());
			
			return con;
		}

		/**
		 * Closes all available connections.
		 */
		public synchronized void release()
		{
			Enumeration<Connection> allConnections = freeConnections.elements();

			while(allConnections.hasMoreElements())
			{
				Connection con = (Connection) allConnections.nextElement();
				try
				{
					con.close();
					logger.info("Closed connection for pool " + name);
					
				}catch(SQLException e)
				{
					logger.error("Can't close connection for pool " + name, e);
				}
			}
			freeConnections.removeAllElements();
			checkedOut = 0;
		}

		/**
		 * Creates a new connection, using a user_id and password if specified.
		 * 
		 * @return Return Value
		 */
		private Connection newConnection()
		{
			Connection con = null;
			try
			{
				if(user == null)
				{
					con = DriverManager.getConnection(URL);
				}else
				{
					con = DriverManager.getConnection(URL, user, password);
				}

			}catch(SQLException e)
			{
				logger.error("Can't create a new connection for " + URL, e);
			}
			return con;
		}

	}
}
