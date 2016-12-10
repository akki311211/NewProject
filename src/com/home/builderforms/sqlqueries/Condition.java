package com.home.builderforms.sqlqueries;





import com.home.builderforms.DBUtil;

 

import com.home.builderforms.SequenceMap;

import com.home.builderforms.*;



import java.util.*;





public class Condition



{

	private ArrayList conditions;

	private ConditionTerm[] conditionTerms;

	private String operator =null;

	private DBUtil	dbUtil;



	public Condition()

	{

		this.dbUtil	  		= DBUtil.getInstance();

	}



	public Condition(String operator)

	{

		this();

		this.conditions 	= new ArrayList();

		this.operator 		= operator;

	}



	public Condition(ConditionTerm[] conditionTerms)

	{

		this();

		this.conditionTerms	= conditionTerms;

	}



	public void addCondition(Condition condition)

	{

		conditions.add(condition);

	}

	public String getString()

	{



		StringBuffer sb = new StringBuffer();



		if (operator==null)

		{

			for(int i=0;i<conditionTerms.length;i++)

			{

				sb.append(conditionTerms[i].getString()+" ");

			}

		}

		else

		{

			boolean flag = true;

			Iterator conditionsIter = conditions.iterator();

			while(conditionsIter.hasNext())

			{

				Condition nextCondition	= (Condition)conditionsIter.next();

				if (flag)

				{

					flag = false;

				}

				else

				{

					sb.append(" "+operator+" ");

				}

				sb.append(nextCondition.getString() );

			}

		}

		//sb.append(" )");

		return sb.toString();



	}



	public HashSet getTableSet()

	{

		HashSet set = new HashSet();

		if (operator == null)

		{

			for (int i = 0; i<conditionTerms.length;i++)

			{

				if (conditionTerms[i].getTermType() == ConditionTerm.FIELD )

				{

					String tableName = dbUtil.getFieldMappings(conditionTerms[i].getTableAnchor()).getTableName();

					set.add(tableName);

				}

			}

		}

		else

		{

			Iterator it = conditions.iterator();

			while (it.hasNext())

			{

				set.addAll( ((Condition)it.next()).getTableSet() );

			}

		}

		return set;

	}



	public void setTableAnchors(SequenceMap oldNewMap)

	{

		if(oldNewMap == null){

			return;

		}

		Iterator it				= oldNewMap.keys().iterator();

		while(it.hasNext()){

			String oldKey		= (String)it.next();

			String newKey		= (String)oldNewMap.get(oldKey);

			if (operator==null){

 

				for(int i=0;i<conditionTerms.length;i++){

					if(conditionTerms[i].getTableAnchor() != null &&

							conditionTerms[i].getTableAnchor().equals(oldKey)){



						conditionTerms[i].setTableAnchor(newKey);

 

					}

				}

			}else{

				Iterator conditionsIter = conditions.iterator();

				while(conditionsIter.hasNext()){

					((Condition)conditionsIter.next()).setTableAnchors(oldNewMap);

				}

			}

		}

	}



}

