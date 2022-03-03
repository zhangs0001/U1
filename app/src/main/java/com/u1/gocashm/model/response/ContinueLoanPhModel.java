package com.u1.gocashm.model.response;


/**
 * @author hewei
 * @date 2018/9/12 0012 下午 1:59
 */
public class ContinueLoanPhModel extends BasePhModel<ContinueLoanPhModel.Body> {

    public static class Body extends BasePhModel.Body {
        private Apply apply;
        private Customer customer;

        public Apply getApply() {
            return apply;
        }

        public void setApply(Apply apply) {
            this.apply = apply;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
    }
}
