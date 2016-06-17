package com.chsi.knowledge.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.chsi.framework.util.FileUtil;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.vo.KnowledgeVO;
import com.chsi.knowledge.vo.SearchVO;

/**
 * 字节转换工具类
 * 
 * @author weiqc <a href="mailto:weiqc@chsi.com.cn">weiqc</a>
 * 
 */
public class ConvertUtil {
    public static Object byteArrayToObject(byte[] byteArray) {
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }

    public static byte[] objectToByteArray(Object o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            return baos.toByteArray();
        } catch (Exception e) {

        }
        return null;
    }

    public static byte[] fileToByteArray(File file) {
        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[FileUtil.blockSize];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return buffer;
    }
    
    public static List<SearchVO> know2SearchVO(List<KnowledgeData> list) {
        List<SearchVO> result = new ArrayList<SearchVO>();
        for(KnowledgeData data:list) {
            result.add(new SearchVO(data.getSystemDatas(), data.getId(), data.getArticle().getTitle(), ""));
        }
        return result;
    }
    
    public static List<KnowledgeVO> know2KnowledgeVO(List<KnowledgeData> list) {
        List<KnowledgeVO> result = new ArrayList<KnowledgeVO>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for(KnowledgeData data:list) {
            Calendar cal = data.getUpdateTime();
            if(cal==null){
                cal = data.getCreateTime();
            }
            result.add(new KnowledgeVO(data.getSystemDatas(), data.getId(), data.getArticle().getTitle(), data.getArticle().getContent(), sdf.format(cal.getTime())));
        }
        return result;
    }
}
