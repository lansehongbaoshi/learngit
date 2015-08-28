package com.chsi.knowledge.vo;

import java.io.Serializable;

import com.chsi.account.client.UserAccountData;
import com.chsi.account.client.UserOrganizationData;

public class LoginUserVO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1849588007396197389L;
    UserAccountData acc;
    UserOrganizationData org;
    
    public LoginUserVO(UserAccountData acc, UserOrganizationData org) {
        this.acc = acc;
        this.org = org;
    }
    
    public UserAccountData getAcc() {
        return acc;
    }

    public void setAcc(UserAccountData acc) {
        this.acc = acc;
    }

    public UserOrganizationData getOrg() {
        return org;
    }
    public void setOrg(UserOrganizationData org) {
        this.org = org;
    }
}
