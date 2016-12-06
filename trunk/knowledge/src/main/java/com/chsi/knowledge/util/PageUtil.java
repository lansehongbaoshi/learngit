package com.chsi.knowledge.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.chsi.framework.page.Page;

public class PageUtil extends com.chsi.framework.page.PageUtil {
    public static Page getPage(Session session, int start, int pageSize, String hql, Object... args) {
        String countHql = "select count(*) " + hql.substring(hql.indexOf("from"));
        Query query = session.createQuery(countHql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
        int totalRecord = Integer.valueOf(query.uniqueResult() + "");
        query = session.createQuery(hql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        List datas = query.list();
        return PageUtil.getPage(datas.iterator(), start, pageSize, totalRecord);
    }

    public static Page getPage(Session session, int start, int pageSize, String countHql, String queryHql, Object... args) {
        Query query = session.createQuery(countHql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
        int totalRecord = Integer.valueOf(query.uniqueResult() + "");
        query = session.createQuery(queryHql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        List datas = query.list();
        return PageUtil.getPage(datas.iterator(), start, pageSize, totalRecord);
    }
}
