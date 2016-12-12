

package com.home.builderforms.sqlqueries;



import java.sql.*;

import java.io.*;

import java.util.*;

import oracle.jdbc.driver.*;

import com.home.builderforms.*;

 




import oracle.sql.*;







public class ClobUtil

{




	public static Clob getClob (Connection conn,String inString) throws SQLException

	{

		CLOB clob = null;

		// Create a Statement

		Statement stmt = conn.createStatement ();

		java.sql.ResultSet rset = null;

		try

		{

			// Drop the table if it exists

			try {

			stmt.execute ("SELECT * FROM CLOB_TABLE WHERE 0 > 1");

			}

			catch (SQLException e) {

				stmt.execute ("CREATE TABLE CLOB_TABLE (UNIQUE_ID NUMBER, DATA CLOB)");

				stmt.execute("COMMIT");

			}





			// create a clob entry in the table

			String uniqueID  = IDGenerator.getNextKey();

			stmt.execute("INSERT INTO CLOB_TABLE values ( "+uniqueID+", empty_clob())");

			stmt.execute("COMMIT");

			String cmd = "SELECT DATA FROM CLOB_TABLE WHERE UNIQUE_ID = "+uniqueID+" FOR UPDATE";

			rset = stmt.executeQuery(cmd);

			if (rset.next())

			{

				clob = (CLOB)(rset.getClob(1));

				Writer out = clob.getCharacterOutputStream();

				try{

					out.write(inString);

				}finally{
					if(out != null){
						try{
							out.close();
							out = null;
						}catch(Exception e){
						}
					}

				}

			}

			stmt.execute("DELETE FROM CLOB_TABLE WHERE UNIQUE_ID = "+uniqueID);

			return clob;

		}

		catch(IOException ie)

		{


			return null;

		}

		finally

		{
			if(rset != null){
				try{
					rset.close();
					rset = null;
				}
				catch(Exception e){
				}
			}

			if(stmt != null){
				try{
					stmt.close();
					stmt = null;
				}
				catch(Exception e){
				}
			}
			//rset.close();

			//stmt.close();

		}

	}



	public static String getStringFromClob(Clob clob)

	 {

		String retString = "";

		if (clob == null) return retString;

		try

		{

			if (clob.length() > 0)

			{

				retString = clob.getSubString(1,(int)clob.length());

			}

		}

		catch(Exception e)

		{


		}

		return retString;

	}





}

