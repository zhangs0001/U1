package com.u1.gocashm.model.response;

import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

/**
 * @author hewei
 * @date 2018/9/7 0007 下午 6:39
 */
public class ApplyModel extends AbstractBaseRespBean<Status, ApplyModel.ApplyBean> {

    public static class ApplyBean {
        private Apply apply;
        private Session session;
        private ProductPhModel.Product product;

        public Apply getApply() {
            return apply;
        }

        public void setApply(Apply apply) {
            this.apply = apply;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public ProductPhModel.Product getProduct() {
            return product;
        }

        public void setProduct(ProductPhModel.Product product) {
            this.product = product;
        }
    }
}
