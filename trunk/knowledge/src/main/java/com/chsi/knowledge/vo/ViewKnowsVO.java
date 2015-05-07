package com.chsi.knowledge.vo;

import java.util.List;

import com.chsi.knowledge.util.Navigation;
import com.chsi.knowledge.util.Pagination;

/**
 * 知识列表与标签列表VO
 * @author chenjian
 */
public class ViewKnowsVO extends KnowListVO {

    private List<Navigation> navigations; // 导航
    private List<ViewTagVO> viewTagVOs; // 标签列表

    public ViewKnowsVO(List<Know> knows,List<ViewTagVO> viewTagVOs, List<Navigation> navigations, Pagination pagination) {
        super(knows, pagination);
        this.viewTagVOs = viewTagVOs;
        this.navigations = navigations;
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

}
