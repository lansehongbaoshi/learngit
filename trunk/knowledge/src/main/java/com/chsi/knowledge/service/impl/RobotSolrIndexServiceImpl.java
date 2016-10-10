package com.chsi.knowledge.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.RobotDAO;
import com.chsi.knowledge.pojo.RobotASetData;
import com.chsi.knowledge.pojo.RobotQSetData;
import com.chsi.knowledge.service.RobotSolrIndexService;
import com.chsi.search.common.indexdata.RobotIndexData;

public class RobotSolrIndexServiceImpl extends BaseDbService implements
        RobotSolrIndexService {
    private RobotDAO robotDAO;
    private SolrServer solrService;
    private int max = 10;

    public void deleteKnowIndexBySolr() {
        // TODO Auto-generated method stub
        try {
            solrService.deleteByQuery("*:*");
            solrService.commit();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void updateRobotIndex(RobotQSetData robotQSetData){
        
        Map<RobotQSetData, List<RobotASetData>> map = robotDAO.getSolrByRQ(robotQSetData);
        List<RobotASetData> listA = map.get(robotQSetData);
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", robotQSetData.getId());
        doc.addField("q", robotQSetData.getQ());
        String[] anser = new String[listA.size()];
        int i=0;
        for(RobotASetData ra :listA){
            anser[i++]=ra.getA();
        }
        doc.addField("a", anser);
        doc.addField("num", 1);
    }

    public void updateAllRobotIndex() {
        // TODO Auto-generated method stub
        List<RobotQSetData> list = robotDAO.allQ();
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for(RobotQSetData robotQSetData:list){
            if(robotQSetData.getQ().startsWith("#")) continue;
            Map<RobotQSetData, List<RobotASetData>> map = robotDAO.getSolrByRQ(robotQSetData);
            List<RobotASetData> listA = map.get(robotQSetData);
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", robotQSetData.getId());
            doc.addField("q", robotQSetData.getQ());
            String[] anser = new String[listA.size()];
            int i=0;
            for(RobotASetData ra :listA){
                anser[i++]=ra.getA();
            }
            doc.addField("a", anser);
            doc.addField("num", robotQSetData.getNum());
            docs.add(doc);
        }
        
        try {
            solrService.add(docs);
            solrService.commit();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("操作成功！");

    }
    @Override
    protected void doCreate() {
        // TODO Auto-generated method stub
        robotDAO = getDAO(ServiceConstants.ROBOT_DAO, RobotDAO.class);

    }

    @Override
    protected void doRemove() {
        // TODO Auto-generated method stub

    }

    public RobotDAO getRobotDAO() {
        return robotDAO;
    }

    public void setRobotDAO(RobotDAO robotDAO) {
        this.robotDAO = robotDAO;
    }

    public SolrServer getSolrService() {
        return solrService;
    }

    public void setSolrService(SolrServer solrService) {
        this.solrService = solrService;
    }

    @Override
    public void ImportDialogue(File file) {
        // TODO Auto-generated method stub
        BufferedReader br = null;
        int index=1;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String txt;
            String question = null;
            String anser = null;
            HashSet set = new HashSet();

            Map<String, RobotIndexData> map = new HashMap<String, RobotIndexData>();
            List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
            while((question=br.readLine())!=null){
                anser = br.readLine();
                txt = br.readLine();
//                System.out.println("第"+index+"对话：\t"+question);
//                System.out.println(anser);
                if(!set.contains(question)){
                    RobotIndexData robotIndexData=new RobotIndexData();
                    robotIndexData.setQ(question);
                    List<String> anserList = new ArrayList<String>();
                    anserList.add(anser);
                    robotIndexData.setA(anserList);
                    map.put(question, robotIndexData);
                    set.add(question);
                    
                }else{
                    RobotIndexData robotIndexData = map.remove(question);
                    List<String> anserList = robotIndexData.getA();
                    if(!anserList.contains(anser)){
                        anserList.add(anser);
                    }
                    map.put(question, robotIndexData);
                }
                //"#############结束################".equals(txt)
                if("#############结束################".equals(txt)){
                    updateSolr( set, map, docs);
                }
                index++;
            }
            
            if(map.size()>0){
                updateSolr( set, map, docs);
            }
            
            
            
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void updateSolr(HashSet set,
            Map<String, RobotIndexData> map, List<SolrInputDocument> docs)
            throws IOException {
        
        for(Map.Entry<String, RobotIndexData> entry : map.entrySet()){

            RobotIndexData robotIndexData = entry.getValue();
            RobotQSetData rqsd = robotDAO.getRobotQSetByQ(robotIndexData.getQ());

            if(rqsd==null){
                rqsd = new RobotQSetData();
                rqsd.setQ(robotIndexData.getQ());
                rqsd.setNum(1);
                robotDAO.save(rqsd);
                
                for(String a :robotIndexData.getA()){
                    RobotASetData rad = new RobotASetData();
                    rad.setqId(rqsd.getId());
                    rad.setA(a);
                    robotDAO.save(rad);
                }
            }else{

                List<RobotASetData> listras = robotDAO.getAByQSet(rqsd);
                List<String> listrasa = new ArrayList<String>();
                for(RobotASetData ra : listras){
                    listrasa.add(ra.getA());
                }
                
                for(String a :robotIndexData.getA()){
                    
                    if(!listrasa.contains(a)){
                        RobotASetData radtest = new RobotASetData();
                        radtest.setqId(rqsd.getId());
                        radtest.setA(a);
                        robotDAO.save(radtest);
                    }
                }
                for(String an : listrasa){
                    if(!robotIndexData.getA().contains(an)){
                        robotIndexData.getA().add(an);
                    }
                }
                
                
            }
            System.out.println("正在处理："+rqsd.getId()+":"+rqsd.getQ()+"问题。");
            
            if(!robotIndexData.getQ().startsWith("#")){
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", rqsd.getId());
                doc.addField("q", robotIndexData.getQ());
                String[] ansers = new String[robotIndexData.getA().size()];
                for(int i=0;i<robotIndexData.getA().size();i++){
                    ansers[i]=robotIndexData.getA().get(i);
                }

                doc.addField("a", ansers);
                doc.addField("num", 1);
                docs.add(doc);
            }
            
            
        }
        try {
            solrService.add(docs);
            solrService.commit();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        docs.clear();
        map.clear();
        set.clear();
    }
    

}
