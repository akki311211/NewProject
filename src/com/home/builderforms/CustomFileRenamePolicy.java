// Copyright (C) 2002 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.
/*
 * helpdesk_int_special_characters_in_file_name   Prakash Jodha   Codebase Issue   special characters from MS-Word are still getting submitted in the file name. This cause file access issue  as file name on server and File name in Database are stored with extra characters.   
 * */
package com.home.builderforms;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.home.builderforms.IDGenerator;
import com.home.builderforms.StrutsUtil;

/**
 * Implements a renaming policy that adds increasing integers to the body of
 * any file that collides.  For example, if foo.gif is being uploaded and a
 * file by the same name already exists, this logic will rename the upload
 * foo1.gif.  A second upload by the same name would be foo2.gif.
 *
 * @author Jason Hunter
 * @version 1.0, 2002/04/30, initial revision, thanks to Yoonjung Lee
 *                           for this idea
--------------------------------------------------------------------------------------------------------------------------------
Version No.		    Date		By			Against		                    Function Changed          Comments
--------------------------------------------------------------------------------------------------------------------------------
BBEH-20110728-005		20/02/2013		Anubhav Jain		Allow comma in attachment file names
-------------------------------------------------------------------------------------------------------------------------------- 
 */
public class CustomFileRenamePolicy implements FileRenamePolicy
{
    /*public HttpServletRequest request=null;*/

   public CustomFileRenamePolicy(){

    }
    public CustomFileRenamePolicy(HttpServletRequest request){
        /*this.request=request;*/
    }
  public File rename(File f)
  {
	HttpServletRequest request=StrutsUtil.getHttpServletRequest();
    String name = f.getName();
    name = name.replace(' ','_');
    /*name = name.replace('–','_');  //helpdesk_int_special_characters_in_file_name 
    name = name.replace('�','_');  //helpdesk_int_special_characters_in_file_name
    name = name.replace('\'','_');
    name = name.replace('#','_');
    name = name.replace('?','_');
    name = name.replace('&','_');
    name = name.replace('\"','_');
    name = name.replace('%','_');
    name = name.replace(':','_');
    //name = name.replace(',','_');	P_E_MAIl
    name = name.replace(';','_');
    name = name.replace('/','_');
    name = name.replace('\\','_');
    name = name.replace('+','_');
    name = name.replace('~','_');
    name = name.replace('`','_');
    name = name.replace('=','_');
    name = name.replace('Â','_'); 
    //ZCUB-20150121-098 starts
    name = name.replace('(','_');  
    name = name.replace(')','_');  
    name = name.replace('$','_');  */
    //ZCUB-20150121-098 ends
    String body = null;
    String ext = null;
    int dot = name.lastIndexOf(".");
    if (dot != -1)
    {
      body = name.substring(0, dot);
      body = body.replace('.','_');//P_B_33742
      ext = name.substring(dot);
    }
    else
    {
      body = name;
      ext = "";
    }
	//Modified by Anuj 12-Dec-2004
	//String newName = body + IDGenerator.getNextKey() + ext;
	String newName = body + "_" + IDGenerator.getNextKey() + ext;
	f = new File(f.getParent(), newName);
        if(request!=null)
        {
            HttpSession ses = request.getSession();
            ses.setAttribute("rendomImageName", newName);
        }
//        System.out.println("newName----------------------->>>>>>>>>>>>>>>>>>"+newName);
    return f;
  }

}
