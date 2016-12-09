/**
* This class is used as a comparator for sorting of Double values
*
* @version 20 May 2009
* @author  Saurabh Vaish
*/

package com.home.builderforms;
import java.util.Comparator;

public class DoubleComparator implements Comparator{
	
   	public final int compare(Object obj1, Object obj2){
			double b1 = Double.valueOf(obj1.toString());
			double b2 = Double.valueOf(obj2.toString());
			int flag=0;
			if(b1 == b2)
				{
					flag = 0;
				}
				else if(b1  > b2)
				{
					flag = 1;
				}
				else if(b1 < b2)
				{
					flag = -1;
				}
			return flag;
    	}

    	public final boolean equals(Object o2){
		if(o2 instanceof DoubleComparator)
		{
			return this.getClass().getName().equals(o2.getClass().getName());
		}
		else
		{
			throw new ClassCastException("the two object are not of same class");
		}

		}

    	public DoubleComparator(){
		}
}

