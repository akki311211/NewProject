package com.home.builderforms;



import java.util.ArrayList;



public class ResultList

{

    private ArrayList list;

    private String[] columnArray;



    public ResultList(ArrayList list,

                      String[] columnArray)

    {

        this.list			= list;

        this.columnArray	= columnArray;

    }



    public ArrayList getList()

    {

        return list;

    }



    public String[] getColumnArray()

    {

        return columnArray;

    }





}

