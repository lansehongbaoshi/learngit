package com.chsi.knowledge.index.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chsi.contact.client.ContactServiceClient;
import com.chsi.contact.client.ContactServiceClientFactory;
import com.chsi.contact.constant.client.ContactConstants;
import com.chsi.framework.service.BaseDbService;
import com.chsi.knowledge.ServiceConstants;
import com.chsi.knowledge.dao.KnowTagRelationDataDAO;
import com.chsi.knowledge.dao.KnowledgeDataDAO;
import com.chsi.knowledge.dao.LogOperDataDAO;
import com.chsi.knowledge.index.service.LogOperService;
import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.knowledge.pojo.LogOperData;
import com.chsi.knowledge.util.Pagination;
import com.chsi.knowledge.vo.LogOperListVO;
import com.chsi.knowledge.vo.LogOperVO;

public class LogOperServiceImpl extends BaseDbService implements LogOperService {
    
    private LogOperDataDAO logOperDAO;
    private KnowledgeDataDAO knowledgeDataDAO;

    @Override
    protected void doCreate() {
        // TODO Auto-generated method stub
        logOperDAO = getDAO(ServiceConstants.LogOperData_DAO, LogOperDataDAO.class);
        knowledgeDataDAO = getDAO(ServiceConstants.KNOWLEDGEDATA_DAO, KnowledgeDataDAO.class);
    }

    @Override
    protected void doRemove() {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(LogOperData logOper) {
        // TODO Auto-generated method stub
        logOperDAO.save(logOper);
    }

    @Override
    public List<LogOperData> getLogOperByKeyId(String keyId) {
        // TODO Auto-generated method stub
        return logOperDAO.getLogOperByKeyId(keyId);
    }

    @Override
    public LogOperListVO<LogOperVO> searchLogOper(String startDate,
            String endDate, int curPage) throws Exception {
        // TODO Auto-generated method stub
        int pageSize = 10;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        
        List<LogOperData> list = logOperDAO.getLogOpersByDate(start,end,curPage,pageSize);
        int totalCount = logOperDAO.getLogOpersCountByDate(start,end);
        Pagination pagination = new Pagination(totalCount, pageSize, curPage);
        List<LogOperVO> listVO = new ArrayList<LogOperVO>();
        ContactServiceClient contactService = ContactServiceClientFactory.getContactServiceClient();
        for(LogOperData logOper : list){
            LogOperVO logOperVO = new  LogOperVO(logOper);
            logOperVO.setUserId(contactService.getRealInfoSingleItemValue(logOper.getUserId(), ContactConstants.ITEM_NAME_ID));
            logOperVO.setOper(logOper.getOper()+"--"+logOper.getMessage()+":"+logOper.getKeyId());
            listVO.add(logOperVO);
        }
        LogOperListVO result = new LogOperListVO<LogOperVO>(listVO, pagination);
        return result;
    }

    

}
