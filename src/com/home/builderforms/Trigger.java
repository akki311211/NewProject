/*
 * Created on Sep 5, 2003
 *
 */
package com.home.builderforms;

import com.home.builderforms.StringUtil;

/**
 * @author Vaibhav Sinha
 */
public class Trigger {
	boolean mbInsertionTrigger		= false;
	boolean mbModificationTrigger	= false;
	boolean mbDeletionTrigger		= false;
	boolean mbFieldWiseTrigger		= false;

	public Trigger(
					boolean pbInsertion,
					boolean pbModification,
					boolean pbDeletion
					){
		mbInsertionTrigger			= pbInsertion;
		mbModificationTrigger		= pbModification;
		mbDeletionTrigger			= pbDeletion;							
	}
	public Trigger(
					boolean pbInsertion,
					boolean pbModification,
					boolean pbDeletion,
					boolean pbFieldWiseTrigger
					){
		mbInsertionTrigger			= pbInsertion;
		mbModificationTrigger		= pbModification;
		mbDeletionTrigger			= pbDeletion;
		mbFieldWiseTrigger			= pbFieldWiseTrigger;
	}
	public Trigger(
					String psInsertion,
					String psModification,
					String psDeletion,
					String psFieldWise
					){
		mbInsertionTrigger			= StringUtil.boolValue(psInsertion);
		mbModificationTrigger		= StringUtil.boolValue(psModification);
		mbDeletionTrigger			= StringUtil.boolValue(psDeletion);							
		mbFieldWiseTrigger			= StringUtil.boolValue(psFieldWise);							
	}
	public boolean insertionTrigger(){
		return mbInsertionTrigger;
	}
	public boolean modificationTrigger(){
		return mbModificationTrigger;
	}
	public boolean deletionTrigger(){
		return mbDeletionTrigger;
	}
	public boolean fieldWiseTrigger(){
		return mbFieldWiseTrigger;
	}
}