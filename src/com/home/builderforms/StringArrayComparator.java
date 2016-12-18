package com.home.builderforms;

import java.util.Comparator;

import java.lang.NumberFormatException;



public class StringArrayComparator implements Comparator{

	

	public static final int NUMERIC = 0;

	public static final int STRING = 1;

	

	private int colNumber;

	private int colType = StringArrayComparator.STRING;



    	public final int compare(Object obj1, Object obj2){

		if (colType == StringArrayComparator.NUMERIC)

			try{

				return (int)(Double.parseDouble(((String[])obj2)[colNumber]) - Double.parseDouble(((String[])obj1)[colNumber]));

			}

			catch(NumberFormatException nfe){

				return ((String[])obj2)[colNumber].compareTo(((String[])obj1)[colNumber]);

			}

		else return ((String[])obj1)[colNumber].compareTo(((String[])obj2)[colNumber]);

    	}



    	public final boolean equals(Object obj1, Object obj2){

		if (colType == StringArrayComparator.NUMERIC)

			try{

				return (Double.parseDouble(((String[])obj1)[colNumber]) == Double.parseDouble(((String[])obj2)[colNumber]));

			}

			catch(NumberFormatException nfe){

				return ((String[])obj1)[colNumber].equals(((String[])obj2)[colNumber]);

			}

		else return ((String[])obj1)[colNumber].equals(((String[])obj2)[colNumber]);

	}



    	public StringArrayComparator(int colNumber, int colType){

		this.colNumber = colNumber;

		this.colType = (colType > 0)?1:0;

	}

}

