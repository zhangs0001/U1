package com.u1.gocashm.model.response;



import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

/**
 * @author hewei
 * @date 2018/9/7 0007 下午 5:00
 */
public class ConfirmLoanModel extends AbstractBaseRespBean<Status, ConfirmLoanModel.ConfirmLoan> {

    public static class ConfirmLoan {

        private String status;
        private int quality;
        private String app_client;
        private int merchant_id;
        private int app_id;
        private String order_no;
        private int user_id;
        private int principal;
        private int loan_days;
        private double daily_rate;
        private String created_at;
        private String updated_at;
        private double overdue_rate;
        private String auth_process;
        private int nbfc_report_status;
        private int id;
        private UserBean user;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }

        public String getApp_client() {
            return app_client;
        }

        public void setApp_client(String app_client) {
            this.app_client = app_client;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public int getApp_id() {
            return app_id;
        }

        public void setApp_id(int app_id) {
            this.app_id = app_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getPrincipal() {
            return principal;
        }

        public void setPrincipal(int principal) {
            this.principal = principal;
        }

        public int getLoan_days() {
            return loan_days;
        }

        public void setLoan_days(int loan_days) {
            this.loan_days = loan_days;
        }

        public double getDaily_rate() {
            return daily_rate;
        }

        public void setDaily_rate(double daily_rate) {
            this.daily_rate = daily_rate;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public double getOverdue_rate() {
            return overdue_rate;
        }

        public void setOverdue_rate(double overdue_rate) {
            this.overdue_rate = overdue_rate;
        }

        public String getAuth_process() {
            return auth_process;
        }

        public void setAuth_process(String auth_process) {
            this.auth_process = auth_process;
        }

        public int getNbfc_report_status() {
            return nbfc_report_status;
        }

        public void setNbfc_report_status(int nbfc_report_status) {
            this.nbfc_report_status = nbfc_report_status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 733
             * merchant_id : 2
             * app_id : 2
             * telephone : 9471500500
             * fullname : Simran
             * id_card_no :
             * status : 1
             * created_at : 2020-07-21 19:09:56
             * updated_at : 2020-08-06 17:12:45
             * platform :
             * client_id :
             * channel_id :
             * quality : 0
             * quality_time : null
             * app_version :
             * api_token :
             * password : 51ac108e3609807c339ef91e9ce5ab5b
             * orders : [{"id":397,"merchant_id":2,"app_id":2,"order_no":"88661865F3201AF6D2","user_id":733,"principal":"3000.00","loan_days":14,"status":"wait_system_approve","created_at":"2020-08-11 07:55:51","updated_at":"2020-08-11 07:55:51","app_client":"android","app_version":"","quality":0,"daily_rate":"0.00100","overdue_rate":"0.00500","signed_time":null,"system_time":null,"manual_time":null,"paid_time":null,"paid_amount":null,"pay_channel":"","cancel_time":null,"pass_time":null,"overdue_time":null,"bad_time":null,"approve_push_status":0,"manual_check":0,"call_check":0,"reject_time":null,"manual_result":0,"call_result":0,"auth_process":"ekyc","nbfc_report_status":0,"flag":"","last_repayment_plan":{"order_id":397},"order_details":[]}]
             */

            private int id;
            private int merchant_id;
            private int app_id;
            private String telephone;
            private String fullname;
            private String id_card_no;
            private int status;
            private String created_at;
            private String updated_at;
            private String platform;
            private String client_id;
            private String channel_id;
            private int quality;
            private Object quality_time;
            private String app_version;
            private String api_token;
            private String password;
            private List<OrdersBean> orders;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(int merchant_id) {
                this.merchant_id = merchant_id;
            }

            public int getApp_id() {
                return app_id;
            }

            public void setApp_id(int app_id) {
                this.app_id = app_id;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getFullname() {
                return fullname;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }

            public String getId_card_no() {
                return id_card_no;
            }

            public void setId_card_no(String id_card_no) {
                this.id_card_no = id_card_no;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getClient_id() {
                return client_id;
            }

            public void setClient_id(String client_id) {
                this.client_id = client_id;
            }

            public String getChannel_id() {
                return channel_id;
            }

            public void setChannel_id(String channel_id) {
                this.channel_id = channel_id;
            }

            public int getQuality() {
                return quality;
            }

            public void setQuality(int quality) {
                this.quality = quality;
            }

            public Object getQuality_time() {
                return quality_time;
            }

            public void setQuality_time(Object quality_time) {
                this.quality_time = quality_time;
            }

            public String getApp_version() {
                return app_version;
            }

            public void setApp_version(String app_version) {
                this.app_version = app_version;
            }

            public String getApi_token() {
                return api_token;
            }

            public void setApi_token(String api_token) {
                this.api_token = api_token;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public List<OrdersBean> getOrders() {
                return orders;
            }

            public void setOrders(List<OrdersBean> orders) {
                this.orders = orders;
            }

            public static class OrdersBean {

                private int id;
                private int merchant_id;
                private int app_id;
                private String order_no;
                private int user_id;
                private String principal;
                private int loan_days;
                private String status;
                private String created_at;
                private String updated_at;
                private String app_client;
                private String app_version;
                private int quality;
                private String daily_rate;
                private String overdue_rate;
                private Object signed_time;
                private Object system_time;
                private Object manual_time;
                private Object paid_time;
                private Object paid_amount;
                private String pay_channel;
                private Object cancel_time;
                private Object pass_time;
                private Object overdue_time;
                private Object bad_time;
                private int approve_push_status;
                private int manual_check;
                private int call_check;
                private Object reject_time;
                private int manual_result;
                private int call_result;
                private String auth_process;
                private int nbfc_report_status;
                private String flag;
                private LastRepaymentPlanBean last_repayment_plan;
                private List<?> order_details;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMerchant_id() {
                    return merchant_id;
                }

                public void setMerchant_id(int merchant_id) {
                    this.merchant_id = merchant_id;
                }

                public int getApp_id() {
                    return app_id;
                }

                public void setApp_id(int app_id) {
                    this.app_id = app_id;
                }

                public String getOrder_no() {
                    return order_no;
                }

                public void setOrder_no(String order_no) {
                    this.order_no = order_no;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getPrincipal() {
                    return principal;
                }

                public void setPrincipal(String principal) {
                    this.principal = principal;
                }

                public int getLoan_days() {
                    return loan_days;
                }

                public void setLoan_days(int loan_days) {
                    this.loan_days = loan_days;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }

                public String getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(String updated_at) {
                    this.updated_at = updated_at;
                }

                public String getApp_client() {
                    return app_client;
                }

                public void setApp_client(String app_client) {
                    this.app_client = app_client;
                }

                public String getApp_version() {
                    return app_version;
                }

                public void setApp_version(String app_version) {
                    this.app_version = app_version;
                }

                public int getQuality() {
                    return quality;
                }

                public void setQuality(int quality) {
                    this.quality = quality;
                }

                public String getDaily_rate() {
                    return daily_rate;
                }

                public void setDaily_rate(String daily_rate) {
                    this.daily_rate = daily_rate;
                }

                public String getOverdue_rate() {
                    return overdue_rate;
                }

                public void setOverdue_rate(String overdue_rate) {
                    this.overdue_rate = overdue_rate;
                }

                public Object getSigned_time() {
                    return signed_time;
                }

                public void setSigned_time(Object signed_time) {
                    this.signed_time = signed_time;
                }

                public Object getSystem_time() {
                    return system_time;
                }

                public void setSystem_time(Object system_time) {
                    this.system_time = system_time;
                }

                public Object getManual_time() {
                    return manual_time;
                }

                public void setManual_time(Object manual_time) {
                    this.manual_time = manual_time;
                }

                public Object getPaid_time() {
                    return paid_time;
                }

                public void setPaid_time(Object paid_time) {
                    this.paid_time = paid_time;
                }

                public Object getPaid_amount() {
                    return paid_amount;
                }

                public void setPaid_amount(Object paid_amount) {
                    this.paid_amount = paid_amount;
                }

                public String getPay_channel() {
                    return pay_channel;
                }

                public void setPay_channel(String pay_channel) {
                    this.pay_channel = pay_channel;
                }

                public Object getCancel_time() {
                    return cancel_time;
                }

                public void setCancel_time(Object cancel_time) {
                    this.cancel_time = cancel_time;
                }

                public Object getPass_time() {
                    return pass_time;
                }

                public void setPass_time(Object pass_time) {
                    this.pass_time = pass_time;
                }

                public Object getOverdue_time() {
                    return overdue_time;
                }

                public void setOverdue_time(Object overdue_time) {
                    this.overdue_time = overdue_time;
                }

                public Object getBad_time() {
                    return bad_time;
                }

                public void setBad_time(Object bad_time) {
                    this.bad_time = bad_time;
                }

                public int getApprove_push_status() {
                    return approve_push_status;
                }

                public void setApprove_push_status(int approve_push_status) {
                    this.approve_push_status = approve_push_status;
                }

                public int getManual_check() {
                    return manual_check;
                }

                public void setManual_check(int manual_check) {
                    this.manual_check = manual_check;
                }

                public int getCall_check() {
                    return call_check;
                }

                public void setCall_check(int call_check) {
                    this.call_check = call_check;
                }

                public Object getReject_time() {
                    return reject_time;
                }

                public void setReject_time(Object reject_time) {
                    this.reject_time = reject_time;
                }

                public int getManual_result() {
                    return manual_result;
                }

                public void setManual_result(int manual_result) {
                    this.manual_result = manual_result;
                }

                public int getCall_result() {
                    return call_result;
                }

                public void setCall_result(int call_result) {
                    this.call_result = call_result;
                }

                public String getAuth_process() {
                    return auth_process;
                }

                public void setAuth_process(String auth_process) {
                    this.auth_process = auth_process;
                }

                public int getNbfc_report_status() {
                    return nbfc_report_status;
                }

                public void setNbfc_report_status(int nbfc_report_status) {
                    this.nbfc_report_status = nbfc_report_status;
                }

                public String getFlag() {
                    return flag;
                }

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public LastRepaymentPlanBean getLast_repayment_plan() {
                    return last_repayment_plan;
                }

                public void setLast_repayment_plan(LastRepaymentPlanBean last_repayment_plan) {
                    this.last_repayment_plan = last_repayment_plan;
                }

                public List<?> getOrder_details() {
                    return order_details;
                }

                public void setOrder_details(List<?> order_details) {
                    this.order_details = order_details;
                }

                public static class LastRepaymentPlanBean {
                    /**
                     * order_id : 397
                     */

                    private int order_id;

                    public int getOrder_id() {
                        return order_id;
                    }

                    public void setOrder_id(int order_id) {
                        this.order_id = order_id;
                    }
                }
            }
        }
    }
}
