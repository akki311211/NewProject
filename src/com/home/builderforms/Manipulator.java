package com.home.builderforms;

/*
 * Interface Name	- Manipulator
 * Location		- com.appnetix.app.manipulator
 * @author		- Amit Gokhru
 * @version		- 1.1.1.1, 
 * date			- 28/12/2003

*/
import com.home.builderforms.SequenceMap;

import javax.servlet.http.HttpServletRequest;
import com.home.builderforms.MultipartRequest;


public interface Manipulator{

public void manipulate(SequenceMap eventMap, HttpServletRequest request);

public void manipulate(SequenceMap eventMap, MultipartRequest request);

};
