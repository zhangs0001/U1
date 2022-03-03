package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * @author hewei
 * @date 2018/9/6 0006 下午 2:17
 */
public class BasePhModel<T extends BasePhModel.Body> extends AbstractBaseRespBean<Status, T> {

    public static class Body {

    }
}
