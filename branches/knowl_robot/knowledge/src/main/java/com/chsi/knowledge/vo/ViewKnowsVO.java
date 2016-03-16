package com.chsi.knowledge.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chsi.knowledge.util.Navigation;
import com.chsi.knowledge.util.Pagination;

/**
 * 知识列表与标签列表VO
 * @author chenjian
 */
public class ViewKnowsVO{

    private List<Navigation> navigations; // 导航
    private List<ViewTagVO> viewTagVOs; // 标签列表
    private KnowListVO<ViewKnowsVO.Know> knowListVO; // 知识VO

    public ViewKnowsVO(List<Know> knows, List<ViewTagVO> viewTagVOs, List<Navigation> navigations, Pagination pagination) {
        knowListVO = new KnowListVO<ViewKnowsVO.Know>(knows, pagination);
        this.viewTagVOs = viewTagVOs;
        this.navigations = navigations;
    }

    public KnowListVO<ViewKnowsVO.Know> getKnowListVO() {
        return knowListVO;
    }

    public void setKnowListVO(KnowListVO<ViewKnowsVO.Know> knowListVO) {
        this.knowListVO = knowListVO;
    }

    public List<Navigation> getNavigations() {
        return navigations;
    }

    public void setNavigations(List<Navigation> navigations) {
        this.navigations = navigations;
    }

    public List<ViewTagVO> getViewTagVOs() {
        return viewTagVOs;
    }

    public void setViewTagVOs(List<ViewTagVO> viewTagVOs) {
        this.viewTagVOs = viewTagVOs;
    }

    public static class Know {
        private String title;
        private Map<String, String> param;

        public Know(String title) {
            this.title = title;
            param = new HashMap<String, String>();
        }

        public void addParam(String name, String param) {
            this.param.put(name, param);
        }

        public Map<String, String> getParam() {
            return param;
        }

        public void setParam(Map<String, String> param) {
            this.param = param;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

}
