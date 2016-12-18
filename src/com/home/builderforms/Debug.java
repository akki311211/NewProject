/*
 * Copyright (c) 2001  Webrizon eSolutions Pvt Ltd
 * B-31, Sector 5, NOIDA. 201301, India.
 * All Rights Reserved
 */
package com.home.builderforms;
/**
 * This class is just a helper class to make it handy
 * to print out debug statements
 */

import com.ibm.xml.parser.Parser;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.util.Calendar;

public final class Debug
{
    public static final boolean debuggingOn = false;

    private static Logger logger = Logger.getLogger(Debug.class);

    public static String logFile, logFile1, logFile2, logFile3, logFile4, exceptionFile, errorPageExceptionsFile, errorCodeFile;

    private static BufferedWriter bfr, bfr1, bfr2, bfr3, bfr4;

    private static FileOutputStream fos, fos1;

    private static PrintStream ps, ps1;

    private static int num = 0;

    public static boolean isInited;

    /**
     * This is the first method which is called in this class. It creates and initializes
     * <p/>
     * the various log files being used in the portal.
     */

    public static void init(String path)
    {
        errorCodeFile = path + "/lib/errors.xml";

        if (!isInited)
        {
            try
            {
                String[] logFiles = BaseUtils.getLogFiles();

                logFile = logFiles[0];
                bfr = new BufferedWriter(new FileWriter(logFile, true));
                logFile1 = logFiles[1];

                bfr1 = new BufferedWriter(new FileWriter(logFile1, true));
                logFile2 = logFiles[2];

                bfr2 = new BufferedWriter(new FileWriter(logFile2, true));
                logFile3 = logFiles[3];

                bfr3 = new BufferedWriter(new FileWriter(logFile3, true));
                logFile4 = logFiles[4];

                bfr4 = new BufferedWriter(new FileWriter(logFile4, true));
                exceptionFile = logFiles[5];

                fos = new FileOutputStream(exceptionFile, true);
                ps = new PrintStream(fos);
                errorPageExceptionsFile = logFiles[6];

                fos1 = new FileOutputStream(errorPageExceptionsFile, true);
                ps1 = new PrintStream(fos1);
            } catch (Exception e)
            {
                logger.error("-------Exception In Initializing Log File---------", e);
            }
            isInited = true;
        }
    }

    private static void write(BufferedWriter bfr, String msg)
    {
        Calendar cal = Calendar.getInstance();
        String date = "" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "-" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        try
        {
            bfr.write(num + ".\t" + date + "\t" + msg);
            num++;
        } catch (Exception e)
        {
            logger.error("-------Exception while writting in log file---------", e);
        }
    }

    public synchronized static void print(String msg)
    {
        if (debuggingOn)
        {
            try
            {
                write(bfr, msg);
                bfr.flush();
            } catch (Exception e)
            {
                logger.error("-------Exception while writting in log file---------", e);
            }
        }
    }

    public synchronized static void print(String msg, int level)
    {
        if (debuggingOn)
        {

            try
            {
                write(bfr, msg);
                bfr.flush();
                switch (level)
                {
                    case 1:
                    {
                        write(bfr1, msg);
                        bfr1.flush();
                        break;
                    }
                    case 2:
                    {
                        write(bfr2, msg);
                        bfr2.flush();
                        break;
                    }
                    default:
                    {
                        write(bfr3, msg);
                        bfr3.flush();
                        break;
                    }
                }
            } catch (Exception e)
            {
                logger.error("-------Exception while writting in log file---------", e);
            }
        }
    }

    public synchronized static void println(String msg)
    {
        if (debuggingOn)
        {
            try
            {
                write(bfr, msg);
                bfr.newLine();
                bfr.flush();
            } catch (Exception e)
            {
                logger.error("-------Exception while writting in log file---------", e);
            }
        }
    }

    public synchronized static void println(String msg, int level)
    {
        if (debuggingOn)
        {

            try
            {
                write(bfr, msg);
                bfr.newLine();
                bfr.flush();
                switch (level)
                {
                    case 1:
                    {
                        write(bfr1, msg);
                        bfr1.newLine();
                        bfr1.flush();
                        break;
                    }
                    case 2:
                    {
                        write(bfr2, msg);
                        bfr2.newLine();
                        bfr2.flush();
                        break;
                    }
                    default:
                    {
                        write(bfr3, msg);
                        bfr3.newLine();
                        bfr3.flush();
                        break;
                    }
                }
            } catch (Exception e)
            {
                logger.error("-------Exception while writting in log file---------", e);
            }
        }
    }

    public synchronized static void print(Exception e, String msg)
    {
        print((Throwable) e, msg);
    }

    public synchronized static void print(Exception e)
    {
        print(e, null);
    }

    /**
     * This method will write the complete stack trace of the exception to exceptions.log file
     */
    public synchronized static void print(Throwable t, String msg)
    {
        if (debuggingOn)
        {
            Calendar cal = Calendar.getInstance();
            String date = "" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "-" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
            ps.print(date + "\n");
            t.printStackTrace(ps);
            ps.flush();
        }
    }

    /**
     * This method will write the complete stack trace of the exception to exceptions.log file
     */
    public synchronized static void print(Throwable t)
    {
        print(t, null);
    }

    /**
     * This page prints the complete stack trace of the exceptions caught in the Error Page
     */
    public synchronized static void printErrorPageException(Throwable t, String msg)
    {
        if (debuggingOn)
        {
            Calendar cal = Calendar.getInstance();
            String date = "" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + "-" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
            ps1.print(date + " : " + msg + "\n");
            t.printStackTrace(ps1);
            ps1.print("\n");
            ps1.flush();
        }
    }

    /**
     * This method writes the details of the error specified by the given error code
     * <p/>
     * to the franchise4.log directory in the logs directory
     */
    public synchronized static void printErrorCode(String code)
    {
        if (debuggingOn)
        {
            InputStream is = null;
            try
            {
                is = new FileInputStream(errorCodeFile);
            } catch (Exception e)
            {
                logger.error("-------Exception while reading errors.xml template file---------", e);
            }
            Parser parser = new Parser(errorCodeFile);
            Document doc = parser.readStream(is);
            if (parser.getNumberOfErrors() > 0)
            {
                logger.info("-------Exception while reading errors.xml template file---------");
            }
            try
            {
                Node rootNode = doc.getDocumentElement();  // The root element  <ErrorCodes>
                NodeList list = rootNode.getChildNodes();
                for (int count = 0; count < list.getLength(); count++)
                {
                    Node childNode = list.item(count);
                    if (childNode.getNodeType() == Node.TEXT_NODE)
                    {   // Ignore text nodes
                        continue;
                    }
                    Node errorChild = childNode.getFirstChild();  // childNode is the <error> node
                    while (!(errorChild.getNodeName().equals("errorCode")))
                    {
                        errorChild = errorChild.getNextSibling();
                    }
                    String errorCode = errorChild.getFirstChild().getNodeValue();
                    if (errorCode.equals(code))
                    {
                        NodeList childNodes = childNode.getChildNodes();   // Get all the child nodes of error Node
                        for (int count1 = 0; count1 < childNodes.getLength(); count1++)
                        {
                            Node childNode1 = childNodes.item(count1);
                            if (childNode1.getNodeType() == Node.TEXT_NODE)
                            {  // Ignore text nodes and errorCode node
                                continue;
                            }
                            String childNodeName = childNode1.getNodeName();
                            String childNodeValue = childNode1.getFirstChild().getNodeValue();
                            bfr4.write(childNodeName.toUpperCase());
                            bfr4.write("\t");
                            bfr4.write(childNodeValue);
                            bfr4.newLine();
                        }
                        break;
                    }
                }
                bfr4.write("----------------------");
                bfr4.newLine();
                bfr4.flush();
            } catch (Exception e)
            {
                logger.error("-------Exception while writting in log file---------", e);
            }
        }
    }
}
