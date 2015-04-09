package com.chsi.knowledge.action.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.pojo.KnowTagRelationData;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.SystemData;
import com.chsi.knowledge.pojo.TagData;
import com.chsi.knowledge.service.KnowTagRelationService;
import com.chsi.knowledge.service.KnowledgeService;
import com.chsi.knowledge.service.SystemService;
import com.chsi.knowledge.service.TagService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.news.type.ArticleStatusType;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.SimpleDateFormat;
/**
 * 导入数据类
 * @author chenjian
 *
 */
public class UploadAction extends AjaxAction {
 
    private static final long serialVersionUID = 1L;
    private TagService tagService;
    private KnowledgeService knowledgeService;
    private SystemService systemService;
    private KnowTagRelationService knowTagRelationService;

    private File upload;
    private String uploadContentType; // 文件的内容类型
    private String uploadFileName; // 上传文件名
    
    private void saveData(String[][] result){
        // 保存系统
        SystemData system = new SystemData("zb", "全国征兵网", "预征报名，兵役登记");
        systemService.save(system);
        /**
         * 插标签
         */
        Set set = new HashSet();
        for (int i = 0; i < result.length; i++) {
            set.add(result[i][0]);
        }
        Iterator it = set.iterator();
        TagData tagData = null;
        List<TagData> list = new ArrayList<TagData>();
        while (it.hasNext()) {
            String name = it.next().toString();
            tagData = new TagData(null, system, name, name, 0);
            tagService.saveOrUpdate(tagData);
            list.add(tagData);
        }  
        /**
         * 插知识代码
         */
        KnowledgeData knowledgeData = null;   
        for (int i = 0; i < result.length; i++) {
            TagData tagData2 = null;
            for (TagData t : list) {
                if (t.getName().equals(result[i][0]))
                    tagData2 = t;
            }
            knowledgeData = new KnowledgeData(null, result[i][1], null, 0, Integer.parseInt(result[i][4]), 
                                            KnowledgeStatus.WSH, "16a669296a704688b2cbf38fef310811", Calendar.getInstance(),
                                            null, null);
            //保存知识
            knowledgeService.save(knowledgeData, result[i][2], result[i][3], ArticleStatusType.WAITTING, "00", "16a669296a704688b2cbf38fef310811");
            //保存知识与标签关系
            KnowTagRelationData knowTagRelationData = new KnowTagRelationData(null,knowledgeData,tagData2);
            knowTagRelationService.save(knowTagRelationData);
        } 
        
    }
    
    public String delete() throws Exception{
        ManageCacheUtil.removeSystem("zb");
        ManageCacheUtil.removeTagList("zb");
        List<TagData> t=tagService.get();
        for(TagData tag: t)
        ManageCacheUtil.removeKnowTag(tag.getId());
        
        List<KnowledgeData> list= knowledgeService.get();
        CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        for(KnowledgeData k : list)
        cmsServiceClient.deleteArticle(k.getCmsId());
        return SUCCESS;
    }
    
    public String upload() throws Exception {
        String[][] result = getData(upload, 1);
        saveData(result);
        return SUCCESS;
    }

    /**
     * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
     * @param file
     *            读取数据的源Excel
     * @param ignoreRows
     *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
     * @return 读出的Excel中数据的内容
     * @throws FileNotFoundException
     * @throws IOException
     */

    public  String[][] getData(File file, int ignoreRows)
    throws FileNotFoundException, IOException {
        List<String[]> result = new ArrayList<String[]>();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
        file));
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                boolean hasValue = false;
                for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        // 注意：一定要设成这个，否则可能会出现乱码
                        // cell).setEncoding(HSSFCell.ENCODING_UTF_16);
                        switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                if (date != null) {
                                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                } else {
                                    value = "";
                                }
                            } else {
                                value = new DecimalFormat("0").format(cell.getNumericCellValue());
                            }
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            // 导入时如果为公式生成的数据则无值
                            if (!cell.getStringCellValue().equals("")) {
                                value = cell.getStringCellValue();
                            } else {
                                value = cell.getNumericCellValue() + "";
                            }
                            break;
                        case HSSFCell.CELL_TYPE_BLANK:
                            break;
                        case HSSFCell.CELL_TYPE_ERROR:
                            value = "";
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            value = (cell.getBooleanCellValue() == true ? "Y"  : "N");
                            break;
                        default:
                            value = "";
                        }
                    }
                    if (columnIndex == 0 && value.trim().equals("")) {
                        break;
                    }
                    values[columnIndex] = rightTrim(value);
                    hasValue = true;
                }
                if (hasValue) {
                    result.add(values);
                }
            }
        }
        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;

    }

    /**
     * 去掉字符串右边的空格
     * @param str
     *            要处理的字符串
     * @return 处理后的字符串
     */
    public static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }



    public TagService getTagService() {
        return tagService;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public KnowledgeService getKnowledgeService() {
        return knowledgeService;
    }

    public void setKnowledgeService(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public KnowTagRelationService getKnowTagRelationService() {
        return knowTagRelationService;
    }

    public void setKnowTagRelationService(
            KnowTagRelationService knowTagRelationService) {
        this.knowTagRelationService = knowTagRelationService;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    
    
}
