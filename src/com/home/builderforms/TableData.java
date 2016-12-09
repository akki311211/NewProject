/*
 * Copyright (c) 2001  Webrizon eSolutions Pvt. Ltd.
 * B-31, Sector 5, NOIDA. 201301, India.
 * All Rights Reserved
 *
 * @author Cyril
 *
 * @version 1.0
 * @date 21.2.2002
 * @auther	Abishek Singhal 17 July 2006 for Bugzilla Bug 18459
 */
// Filename: TableDataSet.java

package com.home.builderforms;

import java.util.*;
import com.appnetix.app.util.*;

public class TableData{

	protected int columnSize = 0;
	protected int rowSize = 0;
	protected int[] columnWidths;// Column widths in percentage....
	protected byte[] columnTypes;
	protected String[] columnCaptions;
	protected String[] columnAlignments;
	protected String[] rowCaptions;//Added by Vijayant
	protected int rowHeight = 15;
	protected int border = 0, cellPadding = 0, cellSpacing = 0, width = 100/*Table width in percentage*/;
	protected String captionBGColor = "#0D5995", tableBGColor = "#EFEFEF";
	
	private Object[] table;
	private String nullTableHeading = "";
	
	public void sortTable(int colNumber, int colType){
		
		if (colNumber < 0 || colNumber >= columnSize) return;//Do Nothing...
		Arrays.sort(table, new StringArrayComparator(colNumber, colType));
		
	}
	
	public TableData(int rowSize, int columnSize){
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		table = new Object[rowSize];
		for (int i = 0;i < rowSize; ++i) table[i] = new String[columnSize];
	}

	public void setElement(int i, int j, String value){
		if (i < 0 || i >= rowSize || j < 0 || j >= columnSize)
			return;
		else 
			((String[])(table[i]))[j] = value;
	}
		
	public String getElement(int i, int j){
		if (i < 0 || i >= rowSize || j < 0 || j >= columnSize)
			return null;
		else
			return ((String[])table[i])[j];
	}

	public int getRowSize(){
		return rowSize;
	}

	public int getColumnSize(){
		return columnSize;
	}

	public boolean setColumnWidths(int[] widths){
		
		if (widths.length > columnSize) return false;
		
		columnWidths = widths;
		
		return true;
	}

	public boolean setColumnWidth(int i, int width){
		
		if (i < 0 || i > columnSize) return false;
		
		columnWidths[i] = width;
		
		return true;
	}
			
	public int[] getColumnWidths(){
		
		return columnWidths;
		
	}
	
	public void setColumnTypes(byte[] types){
		columnTypes = types;
	}

	public void setRowHeight(int value){
		
		rowHeight = value;
	
	}

	public int getRowHeight(){
		
		return rowHeight;
	
	}

	public void setBorder(int value){
		
		border = value;
	
	}

	public int getBorder(){
		
		return border;
	
	}

	public void setWidth(int value){
		
		width = value;
	
	}

	public int getWidth(){
		
		return width;
	
	}

	public void setCellSpacing(int value){
		
		cellSpacing = value;
	
	}

	public int getCellSpacing(){
		
		return cellSpacing;
	
	}

	public void setCellPadding(int value){
		
		cellPadding = value;
	
	}

	public int getCellPadding(){
		
		return cellPadding;
	
	}

	public void setCaptionBGColor(String value){
		
		captionBGColor = value;
	
	}

	public String getCaptionBGColor(){
		
		return captionBGColor;
	
	}

	public void setTableBGColor(String value){
		
		tableBGColor = value;
	
	}

	public String getTableBGColor(){
	
		return tableBGColor;
	
	}
		

	public boolean setColumnCaptions(String[] captions){
		if (captions.length > columnSize) return false;
		columnCaptions = captions;
		return true;
	
	}
	
	public boolean setRowCaptions(String[] captions){//Added by Vijayant
		
		if (captions.length > rowSize) return false;
		rowCaptions = captions;
		return true;
	
	}
	public boolean setColumnAlignments(String[] alignments){
		
		if (alignments.length > columnSize) return false;
		columnAlignments = alignments;
		return true;
	
	}

	public boolean setColumnCaption(int i, String caption){
		
		if (i < 0 || i > columnSize) return false;
		columnCaptions[i] = caption;
		return true;
	
	}
	
	public String[] getColumnAlignments(){
	
		return columnAlignments;

	}
	
	public String[] getColumnCaptions(){
	
		return columnCaptions;

	}

	public String[] getRowCaptions(){//Added by Vijayant
	
		return rowCaptions;

	}
		
	public Object[] getTable(){
	
		return table;
	
	}

	public void setNullTableHeading(String value){
		
		nullTableHeading = value;
	
	}

	public boolean setTableElement(String value, int rowIndex, int columnIndex){
		
		if (rowIndex < 0 || rowIndex >= rowSize || columnIndex < 0 || columnIndex >= columnSize) return false;
		
		((String[])table[rowIndex])[columnIndex] = value;
		
		return true;
		
	}
	
	public String toXML(){
		return toXML(false);
	}

	public String toXML(boolean noLastCol)
	{
		return toXML(false, false);
	}

	public String toXML(boolean noLastCol, boolean modifyColumnSize)
	{
		if (rowSize == 0) 
			return "\t<table/>";

		int cols = 0;

		if (noLastCol)
		{
			cols = columnSize-1;

			if (modifyColumnSize)
			{
				columnSize--;
			}
		} else
		{
			cols = columnSize;
		}

		StringBuffer output = new StringBuffer();
		output.append("\t<table>\n");
		output.append("\t\t<caption-row>\n");
		output.append("\t\t\t<caption>No.</caption>\n");
		
		for (int i = 0;i < cols; ++i)
		{
			try
			{
				if (columnCaptions[i].equals("")) ;

				output.append("\t\t\t<caption>").append(columnCaptions[i]).append("</caption>\n");
			} catch(ArrayIndexOutOfBoundsException abe)
			{
				output.append("\t\t\t<caption>").append("    ").append("</caption>\n");
			}
		}

		output.append("\t\t</caption-row>\n");
		output.append("\t\t<column-widths>\n");
		int w = ((cols > 5)?420:378)/(cols + 1)  ;
		output.append("\t\t\t<width>").append(w).append("pt</width>\n");

		for (int i = 0;i < cols; ++i)
		{
			output.append("\t\t\t<width>").append(w).append("pt</width>\n");
		}
		
		output.append("\t\t</column-widths>\n");
		output.append("\t\t<column-types>\n");

		for (int i = 0; i < cols; ++i)
		{
			try
			{
				if (columnTypes[i] == 0) ;
				output.append("\t\t\t<type>").append(columnTypes[i]).append("</type>\n");
			} catch(ArrayIndexOutOfBoundsException abe)
			{
				output.append("\t\t\t<type>").append(0).append("</type>\n");
			}
		}

		output.append("\t\t</column-types>\n");

		for (int i = 0;i < rowSize; ++i)
		{
			output.append(getRowAsXML(i));
		}

		output.append("</table>");
		return output.toString();
	}

	public String getRowAsXML(int index){
		
		StringBuffer output = new StringBuffer();
		output.append("\t\t<table-row>\n");
		output.append("\t\t\t<table-column>").append(index + 1).append("</table-column>\n");

		for (int i = 0;i < columnSize ; ++i)
		{
			try
			{
				output.append("\t\t\t<table-column>").append(((String[])table[index])[i]/*table[index][i]*/).append("</table-column>\n");
			} catch(Exception e)
			{
			}
		}

		output.append("\t\t</table-row>\n");
		return output.toString();
	}
	/*
	Added by Vijayant for Custom PDF layputs
	*/
	public String toXMLCustomised(String headerName,String preHeaderVal){
		if (rowSize == 0) return "\t<table/>";
		
		StringBuffer output = new StringBuffer();
		//One blank row.
		if(preHeaderVal!=null){
			output.append("\t<table>\n");
			//output.append("\t\t<table-row> \n");
			output.append("\t\t<caption-row> \n");
			output.append("\t\t\t<caption>");
			output.append(preHeaderVal);
			output.append("</caption>\n");
			output.append("\t\t</caption-row> \n");
		/*	output.append("\t\t<column-widths> \n");
			output.append("\t\t\t<width>300pt</width>\n");
			output.append("\t\t</column-widths> \n");
			output.append("\t\t<column-types> \n");
			output.append("\t\t\t<type> </type>\n");
			output.append("\t\t</column-types> \n");
			output.append("\t\t<table-row> \n");
			output.append("\t\t\t<table-column> ").append(" </table-column>\n");
			*///output.append(" \t\t</table-row>\n");
			output.append("\t</table>\n");
		}//end if		

		output.append("\t<table>\n");
		output.append("\t\t<caption-row>\n");
		output.append("\t\t\t<caption>").append(headerName).append("</caption>\n");
		for (int i = 0;i < columnSize; ++i)
			try{
				if (columnCaptions[i].equals("")) ;
				output.append("\t\t\t<caption>").append(columnCaptions[i]).append("</caption>\n");
			}
			catch(ArrayIndexOutOfBoundsException abe){
				output.append("\t\t\t<caption>").append("    ").append("</caption>\n");
			}
		output.append("\t\t</caption-row>\n");
		output.append("\t\t<column-widths>\n");
		
		int w = ((columnSize > 5)?420:378)/(columnWidths.length + 1)  ;
		output.append("\t\t\t<width>").append(w).append("pt</width>\n");
		for (int i = 0;i < columnSize; ++i)
			output.append("\t\t\t<width>").append(w).append("pt</width>\n");
		
		output.append("\t\t</column-widths>\n");
		output.append("\t\t<column-types>\n");
		
		for (int i = 0;i < columnSize; ++i)
			try{
				if (columnTypes[i] == 0) ;
				output.append("\t\t\t<type>").append(columnTypes[i]).append("</type>\n");
			}
			catch(ArrayIndexOutOfBoundsException abe){
				output.append("\t\t\t<type>").append(0).append("</type>\n");
			}
		output.append("\t\t</column-types>\n");
		
		for (int i = 0;i < rowSize-1; ++i)//rowSize decremented by Vijayant as its a 2D report
			output.append(getRowAsXMLCustomised(i,rowCaptions[i]));	
		//output.append(getRowAsXMLCustomised(i,"row1111"));
			
		
		output.append("</table>");
		
		return output.toString();
	}

	public String getRowAsXMLCustomised(int index,String rowCaption){
		
		StringBuffer output = new StringBuffer();
		output.append("\t\t<table-row>\n");
		output.append("\t\t\t<table-column>").append(rowCaption).append("</table-column>\n");
		for (int i = 0;i < columnSize ; ++i){
			try{
				output.append("\t\t\t<table-column>").append(((String[])table[index])[i]/*table[index][i]*/)
			      	      .append("</table-column>\n");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		output.append("\t\t</table-row>\n");
		
		return output.toString();
	}

	
	public String toHTML(){
		
		StringBuffer output = new StringBuffer();
		
		output.append("<TABLE WIDTH=\"").append(width).append("%\" ALIGN=\"center\" CELLPADDING=\"").append(cellPadding)
		      .append("\" CELLSPACING=\"").append(cellPadding)
		      .append("\" BORDER=\"").append(border).append("\">");
		
		if (rowSize != 0){
		
			output.append(getCaptionRowAsHTML());
			for (int i = 0;i < rowSize; ++i)
				output.append(getRowAsHTML(i));
			
		}
		else{
			
			output.append("<TR ALIGN=\"CENTER\" HEIGHT=\"").append(rowHeight).append("\"><TD WIDTH=\"100%\" COLSPAN=\"")
			      .append(columnSize + 1).append("\" BGCOLOR=\"").append(captionBGColor)
			      .append("\"><FONT FACE=\"Verdana\" SIZE=\"2\" COLOR=\"BLACK\"><B>").append(nullTableHeading)
			      .append("</B></FONT></TD></TR>").append("<TR ALIGN=\"CENTER\" HEIGHT=\"").append(rowHeight)
			      .append("\"><TD WIDTH=\"100%\" COLSPAN=\"").append(columnSize + 1)
			      .append("\"><FONT FACE=\"Verdana\" SIZE=\"2\" COLOR=\"RED\">No Data Available!!</FONT></TD></TR>")
			      .append("<TR ALIGN=\"CENTER\" HEIGHT=\"").append(rowHeight)
			      .append("\"><TD WIDTH=\"100%\" COLSPAN=\"").append(columnSize + 1)
			      .append("\"><HR></TD></TR>");
			
		}
		
		output.append("</TABLE>");
		
		return output.toString();
	}

	public String getCaptionRowAsHTML(){
		
		StringBuffer output = new StringBuffer();

		output.append("<TR ALIGN=\"CENTER\" HEIGHT=\"").append(rowHeight)
		      .append("\"><TD WIDTH=\"2%\" BGCOLOR=\"").append(captionBGColor)
		      .append("\">&nbsp;</TD>");
		
		String alignment = "LEFT";
		for (int i = 0;i < columnSize ; ++i){
			output.append("<TD ALIGN=\"").append(columnAlignments[i]);/*.append("\" WIDTH=\"");	
			output.append(tmpWidth).append("%\")*/output.append("\" BGCOLOR=\"").append(captionBGColor)
			      .append("\">").append("<FONT FACE=\"Verdana\" size=\"2\"><B>");
			
			output.append(columnCaptions[i]);
			output.append("</B></FONT></TD>");
		}
		
		output.append("</TR>");
		
		return output.toString();
	}

	public String getRowAsHTML(int index){
		
		StringBuffer output = new StringBuffer();
		
		output.append("<TR ALIGN=\"CENTER\" HEIGHT=\"").append(rowHeight).append("\"><TD WIDTH=\"2%\">&nbsp;</TD>");
		String alignment = "LEFT";
		for (int i = 0;i < columnSize ; ++i){
			output.append("<TD ALIGN=\"").append(columnAlignments[i]);/*.append("\" WIDTH=\"").append(tmpWidth)
			      .append("%")*/output.append("\" BGCOLOR=\"").append(tableBGColor).append("\">").append("<FONT FACE=\"Verdana\" size=\"2\" class='tb_sub_hdr_b' >")
		              .append(((String[])table[index])[i]/*table[index][i]*/).append("</FONT></TD>");
		}
		
		output.append("</TR>");
		
		output.append("<TR ALIGN=\"CENTER\"><TD COLSPAN=\"").append(columnSize + 1).append("\" WIDTH=\"100%\" ALIGN=\"CENTER\"><HR></TD></TR>");
		
		return output.toString();
	}
			
}
