package com.chsi.knowledge.vo;

import java.io.Serializable;
import java.util.List;

import com.chsi.account.client.UserAccountData;
import com.chsi.account.client.UserOrganizationData;

public class LoginUserVO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1849588007396197389L;
    UserAccountData acc;
    UserOrganizationData org;
    List<String> auths;

    public LoginUserVO() {
    }

    public LoginUserVO(UserAccountData acc, UserOrganizationData org, List<String> auths) {
        this.acc = acc;
        this.org = org;
        this.auths = auths;
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

    public List<String> getAuths() {
        return auths;
    }

    public void setAuths(List<String> auths) {
        this.auths = auths;
    }
}
