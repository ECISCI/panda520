package com.panda520.mall.common.core.page;

import com.panda520.mall.common.constant.Constants;
import com.panda520.mall.common.utils.ServletUtils;

/**
 * @author X
 * @描述.表格数据处理
 */
public class TableSupport {
    /**
     * @描述.封装分页对象
     */
    public static PageDomain buildPageRequest() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return pageDomain;
    }
}
