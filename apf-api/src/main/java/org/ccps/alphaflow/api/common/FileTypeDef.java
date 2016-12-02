package org.ccps.alphaflow.api.common;

/*
 * Title:        ECM
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:
 * @author: Anthony Han
 * @version 4.0
 */
import java.util.*;

public class FileTypeDef {

  private static HashMap filExtFileIdMap;
  private static HashMap fileIdImageNameMap;
  private static HashMap file24ImgNameMap;

  private static HashMap fileIdLogoNameMap;
  private static HashMap fileIdBigImageNameMap;

  private FileTypeDef() {
  }

  public static final int NUM_OF_FILE_TYPE_IN_DB = 10;
  public static final int NUM_OF_FILE_TYPE_INDEXED = 7;

  // file type id
  public static final int OTHER_ID = 0;
  public static final int IMAGE_ID = 1;

  public static final int FOLDER_ID = 10;

  public static final int LINK_ID = 12;
  public static final int INTERNAL_LINK_ID = 13;
  public static final int LINKFOLDER_ID = 14;

  public static final int WORKPLACE_ID = 15;

  public static final int WF_LINK_ID = 16;

  public static final int IMAGE_FILE_ID = 20;
  public static final int TEXT_FILE_ID = 21;
  public static final int HTML_FILE_ID = 22;
  public static final int XML_FILE_ID = 23;
  public static final int PDF_FILE_ID = 24;
  public static final int WORD_FILE_ID = 25;
    //add by edison 2009.8.10
    public static final int WORD07_FILE_ID = 40;
  public static final int EXCEL_FILE_ID = 26;
  public static final int PPT_FILE_ID = 27;
  public static final int MHT_FILE_ID = 28;
  public static final int ZIP_FILE_ID = 29;
  public static final int RAR_FILE_ID = 30;
  public static final int DOC_FILE_ID = 31;

  public static final int PDF_FILE_ID_HTML = 32;
  public static final int PDF_FILE_ID_SWF = 33;
  public static final int PDF_FILE_ID_TEXT = 34;

  public static final int EXCEL07_FILE_ID = 41;
  public static final int PPT07_FILE_ID = 42;

  public static final int NO_PREVIEW = 50;
  public static final int REPORT_FILE_ID = 51;
  //add by zhengjiefeng   20130311
  public static final int ASX_FILE_ID = 52;
  public static final int ASF_FILE_ID = 53;
  public static final int MPG_FILE_ID = 54;
  public static final int WMV_FILE_ID = 55;
  public static final int _3GP_FILE_ID = 56;
  public static final int MP4_FILE_ID = 57;
  public static final int MOV_FILE_ID = 58;
  public static final int AVI_FILE_ID = 59;
  public static final int FLV_FILE_ID = 60;
  
  public static final int WMV9_FILE_ID = 61;
  public static final int RM_FILE_ID = 62;
  public static final int RMVB_FILE_ID = 63;
  
  
  // file type name
  public static final String FILE = "FILE";
  public static final String OTHER = "OTHER";
  public static final String IMAGE  = "IMAGE";
  public static final String FOLDER = "FOLDER";
  public static final String LINKFOLDER = "LINKFOLDER";
  public static final String WORKPLACE = "WP";

  public static final String WF_LINK = "WF_LINK";

  public static final String IMAGE_FILE = "image";
  public static final String TEXT_FILE = "text";
  public static final String HTML_FILE = "html";
  public static final String XML_FILE = "xml";
  public static final String PDF_FILE = "pdf";
  public static final String WORD_FILE = "ms word";
    public static final String WORD07_FILE="ms word07";
  public static final String EXCEL_FILE = "ms excel";
  public static final String PPT_FILE = "powerpoint";
  public static final String MHT_FILE = "web archive";
  public static final String REPORT_FILE = "report file";

  public static int getTypeId(String fileExt) {
     if (filExtFileIdMap == null) {
       filExtFileIdMap = new HashMap();
       filExtFileIdMap.put("gif",String.valueOf(IMAGE_FILE_ID));
       filExtFileIdMap.put("jpeg",String.valueOf(IMAGE_FILE_ID));
       filExtFileIdMap.put("jpg",String.valueOf(IMAGE_FILE_ID));
       filExtFileIdMap.put("tif",String.valueOf(IMAGE_FILE_ID));
       filExtFileIdMap.put("doc",String.valueOf(WORD_FILE_ID));
       filExtFileIdMap.put("rtf",String.valueOf(WORD_FILE_ID));
       filExtFileIdMap.put("txt",String.valueOf(TEXT_FILE_ID));
       filExtFileIdMap.put("htm",String.valueOf(HTML_FILE_ID));
       filExtFileIdMap.put("html",String.valueOf(HTML_FILE_ID));
       filExtFileIdMap.put("xml",String.valueOf(XML_FILE_ID));
       filExtFileIdMap.put("pdf",String.valueOf(PDF_FILE_ID));
       filExtFileIdMap.put("xls",String.valueOf(EXCEL_FILE_ID));
       filExtFileIdMap.put("xlsx",String.valueOf(EXCEL07_FILE_ID));
       filExtFileIdMap.put("ppt",String.valueOf(PPT_FILE_ID));
       filExtFileIdMap.put("pptx",String.valueOf(PPT07_FILE_ID));
       filExtFileIdMap.put("mht",String.valueOf(MHT_FILE_ID));
       //add by edison 2009.8.10
       filExtFileIdMap.put("docx",String.valueOf(WORD07_FILE_ID));
       filExtFileIdMap.put("zip",String.valueOf(ZIP_FILE_ID));
       filExtFileIdMap.put("rar",String.valueOf(RAR_FILE_ID));
       filExtFileIdMap.put("rptdesign", String.valueOf(REPORT_FILE_ID));
       //add by zhengjiefeng 2013.3.11
       filExtFileIdMap.put("asx", String.valueOf(ASX_FILE_ID));
       filExtFileIdMap.put("asf", String.valueOf(ASF_FILE_ID));
       filExtFileIdMap.put("mpg", String.valueOf(MPG_FILE_ID));
       filExtFileIdMap.put("wmv", String.valueOf(WMV_FILE_ID));
       filExtFileIdMap.put("3gp", String.valueOf(_3GP_FILE_ID));
       filExtFileIdMap.put("mp4", String.valueOf(MP4_FILE_ID));
       filExtFileIdMap.put("mov", String.valueOf(MOV_FILE_ID));
       filExtFileIdMap.put("avi", String.valueOf(AVI_FILE_ID));
       filExtFileIdMap.put("flv", String.valueOf(FLV_FILE_ID));
       
       filExtFileIdMap.put("wmv9", String.valueOf(WMV9_FILE_ID));
       filExtFileIdMap.put("rm", String.valueOf(RM_FILE_ID));
       filExtFileIdMap.put("rmvb", String.valueOf(RMVB_FILE_ID));
       
     }

     int typeId = OTHER_ID;
     Object obj = filExtFileIdMap.get(fileExt.toLowerCase());
     if (obj != null)
        typeId = Integer.parseInt((String)obj);

     return typeId;
  }

  public static int getTypeIdByName(String file_name) {
    String ext = "";
    if ( file_name == null)
      return OTHER_ID;

    int ldx = file_name.lastIndexOf('.');
    if(ldx > 0) {
      ext = file_name.substring(ldx + 1);
    }
    return getTypeId(ext);
  }

/*  public static String get24ImageName(int typeId) {}
  // image name
  public static String getImageName(int typeId) {}

  public static String getLogoName(int typeId) {}

  public static String getMailAttchName(int typeId) {}


      public static String getPortalImageName(int typeId) {}*/

}
