package com.chsi.knowledge.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chsi.contact.client.ContactServiceClient;
import com.chsi.contact.client.ContactServiceClientFactory;
import com.chsi.contact.constant.client.ContactConstants;
import com.chsi.framework.callcontrol.CallInfoHelper;
import com.chsi.framework.remote.RemoteCallRs;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.lobdb.client.LobDbServiceClient;
import com.chsi.lobdb.client.LobDbServiceClientFactory;
import com.chsi.security.client.AuthorityServiceClient;
import com.chsi.security.client.AuthorityServiceClientFactory;

public class RemoteCallUtil {
    protected static final Log log = LogFactory.getLog(RemoteCallUtil.class);
    
    private static String accountProtocol;
    private static String accountServerName;
    private static String picsrvProtocol;
    private static String picsrvServerName;
    private static String contactProtocol;
    private static String contactServerName;// contact域名
    
    private static LobDbServiceClient lobDbServiceClient = LobDbServiceClientFactory.getLobDbServiceClient();
    private static ContactServiceClient contactService = ContactServiceClientFactory.getContactServiceClient();
    private static AuthorityServiceClient authorityServiceClient = AuthorityServiceClientFactory.getAuthorityServiceClient();

    static {
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            log.error("环境变量未配置propertyPath");
        } else {
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                accountProtocol = properties.getProperty("sys.website.account.protocol");
                accountServerName = properties.getProperty("sys.website.account.servername");
                picsrvProtocol = properties.getProperty("sys.website.picsrv.protocol");
                picsrvServerName = properties.getProperty("sys.website.picsrv.servername");
                contactProtocol = properties.getProperty("sys.website.contact.protocol");
                contactServerName = properties.getProperty("sys.website.contact.servername");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
    
    public static String getAccountUrl() {
        return accountProtocol + "://" + accountServerName;
    }
    
    public static String getContactUrl() {
        return contactProtocol + "://" + contactServerName;
    }
    
    public static String getPicsrvUrl() {
        return picsrvProtocol + "://" + picsrvServerName;
    }
    
    /**
     * 当前登录人的通讯录姓名
     * @param userId
     * @return
     */
    public static String getXm() {
        String userId = CallInfoHelper.getCurrentUser();
        if(!ValidatorUtil.isNull(userId)) {
            return contactService.getRealInfoSingleItemValue(userId, ContactConstants.ITEM_NAME_ID);
        }
        return "";
    }
    
    public static String getXmByUserId(String userId) {
        if(!ValidatorUtil.isNull(userId)) {
            return contactService.getRealInfoSingleItemValue(userId, ContactConstants.ITEM_NAME_ID);
        }
        return "";
    }
    
    public static List<String> getAuthsByUserId(String userId) {
        RemoteCallRs<String[]> rcr = authorityServiceClient.getAuthorities(userId);
        String[] strs = rcr.getSuccRsValue();
        return Arrays.asList(strs);
    }
}
