/**
 * @author abhishek gupta
 * @Date 23 Jul 2012
 */
package com.home.builderforms;

import java.util.Iterator;

import com.home.builderforms.SequenceMap;

public class Notes {
	private String notes			 = null;
	private String msHeading		 = null;
	private String content			 = null;
	private SequenceMap notesAttrMap = null;
	
	public Notes(String notes, SequenceMap notesAttrMap, String msHeading,String content) {
		this.notes			= notes;
		this.msHeading		= msHeading;
		this.content		= content;
		this.notesAttrMap	= notesAttrMap;
	}

	public String getNotesName() {
		return notes;
	}
	
	public String getHeading() {
		return msHeading;
	}
	
	public String getContent() {
		return content;
	}
	public SequenceMap getNotesMap() {
		return notesAttrMap;
	}
}