package com.chsi.knowledge.htgl.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;
import com.chsi.knowledge.action.base.AjaxAction;
import com.chsi.knowledge.index.service.KnowIndexService;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.service.RobotService;
import com.chsi.knowledge.service.RobotSolrIndexService;
import com.chsi.knowledge.util.ManageCacheUtil;
import com.chsi.knowledge.util.SearchUtil;
import com.chsi.knowledge.vo.KnowListVO;
import com.chsi.knowledge.vo.RobotQAListVO;
import com.chsi.knowledge.vo.SearchVO;
import com.chsi.search.client.SearchServiceClient;
import com.chsi.search.client.SearchServiceClientFactory;
import com.chsi.search.client.vo.KnowledgeVO;
import com.chsi.search.client.vo.RobotQABean;
import com.chsi.search.common.indexdata.RobotIndexData;
import com.opensymphony.xwork2.Action;
/**
 * 后台管理 机器人配置
 * @author zhangzh
 *
 */
public class RobotSetAction extends AjaxAction{

    /**
     * 
     */
    private static final long serialVersionUID = -8744022807636131205L;
    
    private RobotService robotService;
    private RobotSolrIndexService robotSolrIndexService;
    private String text;
    private String type;
    private int curPage;
    private String[] qs;
    private String qs1;
    private String qs2;
    private String qs3;
    private File file;

    private String callback;
    private String id;
    private String q;
    private String[] a;
    private int num;
    
    private Map<RobotQSetData, List<RobotASetData>> qaSet;
    
    public String upload(){
        return SUCCESS;
    }
    public String updateCommit() throws Exception{
        if(file!=null){
            robotSolrIndexService.ImportDialogue(file);
            return SUCCESS;
        }else{
            return ERROR;
        }
        
    }
    public String synchronizationIndex() throws Exception{
        robotSolrIndexService.deleteKnowIndexBySolr();
        robotSolrIndexService.updateAllRobotIndex();
        System.out.println("更新索引成功！");
        return SUCCESS;
    }
    
    public String listIndex() throws Exception {
        return SUCCESS;
    }
    
    public void listBasic() throws IOException{
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        if(ValidatorUtil.isNull(qs1)&&ValidatorUtil.isNull(qs2)&&ValidatorUtil.isNull(qs3)){
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
            return;
        }
        qs = new String[3];
        qs[0] = qs1;
        qs[1] = qs2;
        qs[2] = qs3;
        
        List<RobotQABean> listBean = robotService.getRobotBasicConf(qs);
        ajaxMessage.setO(listBean);
        writeCallbackJSON(callback);
    }
    

    public void list() throws Exception {
        ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);

        if(ValidatorUtil.isNull(text)){
            text = "*:*";
        }
        if(text!=null){
            if("".equals(text.trim())){
                text = "*:*";
            }else{
                text = text.trim();
            }
        }
        
        RobotQAListVO<RobotQABean> listBean = robotService.searchRobotConf(text, (curPage - 1) * Constants.PAGE_SIZE, Constants.PAGE_SIZE);
        RobotQABean robotQABean = new RobotQABean();
        RobotQAListVO<RobotQABean> result = new RobotQAListVO<RobotQABean>(listBean.getRobotBean(), listBean.getPagination());
        
        ajaxMessage.setO(result);
        writeCallbackJSON(callback);
        
//        qaSet = robotService.getRobotQASet(null);
//        return SUCCESS;
    }

    public String add() throws Exception {
        if(!ValidatorUtil.isNull(q) && a!=null && a.length>0) {
            RobotQSetData robotQSetData = new RobotQSetData();
            RobotIndexData robotIndexData = new RobotIndexData();
            robotQSetData.setQ(q);
            robotQSetData.setNum(num);
            robotService.save(robotQSetData);
            robotIndexData.setId(robotQSetData.getId());
            robotIndexData.setQ(q);
            List<String> anser = new ArrayList<String>();
            for(String str:a) {
                if(!ValidatorUtil.isNull(str)) {
                    RobotASetData robotASetData = new RobotASetData();
                    robotASetData.setqId(robotQSetData.getId());
                    robotASetData.setA(str);
                    robotService.save(robotASetData);
                    anser.add(str);
                }
            }
            robotIndexData.setA(anser);
            robotIndexData.setNum(num);
            SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
            searchClient.updateRobotQA(robotIndexData);
        }
        return SUCCESS;
    }
    
    //更新首页
    public String updateIndex() throws Exception {
        if(!ValidatorUtil.isNull(id)) {
            qaSet = robotService.getRobotQASet(id);
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
    
    //更新操作
    public String update() throws Exception {
        if(!ValidatorUtil.isNull(id) && !ValidatorUtil.isNull(q) && a!=null && a.length>0) {//更新分解为：新增再删除原来的
            RobotQSetData robotQSetData = new RobotQSetData();
            RobotIndexData robotIndexData = new RobotIndexData();
            robotQSetData.setQ(q);
            robotQSetData.setNum(num);
            robotService.save(robotQSetData);
            robotIndexData.setId(robotQSetData.getId());
            robotIndexData.setQ(q);
            List<String> anser = new ArrayList<String>();
            for(String str:a) {
                if(!ValidatorUtil.isNull(str)) {
                    RobotASetData robotASetData = new RobotASetData();
                    robotASetData.setqId(robotQSetData.getId());
                    robotASetData.setA(str);
                    robotService.save(robotASetData);
                    anser.add(str);
                }
            }
            robotIndexData.setA(anser);
            robotIndexData.setNum(num);
            robotService.deleteRobotQASet(id);
            SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
            searchClient.updateRobotQA(robotIndexData);
            searchClient.deleteRobotQA(id);
            ManageCacheUtil.removeRobotABySpecialQ(q);
        }
        return SUCCESS;
    }
    
    public String delete() throws Exception {
        if(!ValidatorUtil.isNull(id)) {
            robotService.deleteRobotQASet(id);
            ajaxMessage.setFlag(Constants.AJAX_FLAG_SUCCESS);
        } else {
            ajaxMessage.setFlag(Constants.AJAX_FLAG_ERROR);
        }
        writeJSON(ajaxMessage);
        return NONE;
    }

    public RobotService getRobotService() {
        return robotService;
    }

    public void setRobotService(RobotService robotService) {
        this.robotService = robotService;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String[] getA() {
        return a;
    }

    public void setA(String[] a) {
        this.a = a;
    }

    public Map<RobotQSetData, List<RobotASetData>> getQaSet() {
        return qaSet;
    }

    public void setQaSet(Map<RobotQSetData, List<RobotASetData>> qaSet) {
        this.qaSet = qaSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String[] getQs() {
        return qs;
    }

    public void setQs(String[] qs) {
        this.qs = qs;
    }

    public String getQs1() {
        return qs1;
    }

    public void setQs1(String qs1) {
        this.qs1 = qs1;
    }

    public String getQs2() {
        return qs2;
    }

    public void setQs2(String qs2) {
        this.qs2 = qs2;
    }

    public String getQs3() {
        return qs3;
    }

    public void setQs3(String qs3) {
        this.qs3 = qs3;
    }

    public RobotSolrIndexService getRobotSolrIndexService() {
        return robotSolrIndexService;
    }

    public void setRobotSolrIndexService(RobotSolrIndexService robotSolrIndexService) {
        this.robotSolrIndexService = robotSolrIndexService;
    }
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }

}
