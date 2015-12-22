package com.chsi.knowledge.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chsi.framework.remote.RemoteCallRs;
import com.chsi.knowledge.Constants;
import com.chsi.lobdb.client.LobDbServiceClient;
import com.chsi.lobdb.client.LobDbServiceClientFactory;
import com.chsi.lobdb.client.vo.LobVo;

public class RemoteCallUtil {
    protected static final Log log = LogFactory.getLog(RemoteCallUtil.class);
    private static LobDbServiceClient lobDbServiceClient = LobDbServiceClientFactory.getLobDbServiceClient();
    
    public static String addFile(byte[] bytes, String contentType) throws Exception {
        /*RemoteCallRs<String> result = lobDbServiceClient.add(bytes, contentType, "");
        if (result.getCallResult() == RemoteCallRs.CALLRESULT_SUCCESS) {
            return result.getValue();
        } else {
            log.error(String.format("{contentType:%s,errMsg:%s}", contentType, result.getErrorMsg()));
            throw new Exception("远程调用addFile失败");
        }*/
        return "";
    }

    public static String addFile(File file, String contentType) throws Exception {
        byte[] bytes = null;
        try {
            bytes = ByteArrayConvertUtil.fileToByteArray(file);
            ;
        } catch (Exception ex) {
            throw ex;
        } finally {
        }
        return addFile(bytes, contentType);
    }
    
    public static LobVo getFile(String id) throws Exception {
        RemoteCallRs<LobVo> result = lobDbServiceClient.getLob(id);
        if (result.getCallResult() == RemoteCallRs.CALLRESULT_SUCCESS) {
            return result.getValue();
        } else {
            log.error(String.format("{id:%s,errMsg:%s}", id, result.getErrorMsg()));
            throw new Exception("远程调用getFile失败");
        }
    }
}
