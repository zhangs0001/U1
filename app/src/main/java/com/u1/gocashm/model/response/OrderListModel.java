package com.u1.gocashm.model.response;



import com.google.gson.annotations.SerializedName;
import com.u1.gocashm.model.AbstractBaseRespBean;
import com.u1.gocashm.model.Status;

import java.util.List;

public class OrderListModel extends AbstractBaseRespBean<Status, OrderListModel.OrderList> {

    public static class  OrderList  {

        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private Object next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        private List<Order> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public String getFirst_page_url() {
            return first_page_url;
        }

        public void setFirst_page_url(String first_page_url) {
            this.first_page_url = first_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getLast_page_url() {
            return last_page_url;
        }

        public void setLast_page_url(String last_page_url) {
            this.last_page_url = last_page_url;
        }

        public Object getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(Object next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Order> getData() {
            return data;
        }

        public void setData(List<Order> data) {
            this.data = data;
        }

        public static class Order {

            public int merchant_id;
            public int app_id;
            public int channel_id;
            public String apply_principal;
            public String updated_at;
            public String app_client;
            public String app_version;
            public int quality;
            public String daily_rate;
            public String overdue_rate;
            public Object system_time;
            public Object manual_time;
            public Object confirm_pay_time;
            public String pay_channel;
            public Object pay_type;
            public Object cancel_time;
            public Object pass_time;
            public Object overdue_time;
            public Object bad_time;
            public int approve_push_status;
            public int manual_check;
            public int call_check;
            public Object reject_time;
            public int manual_result;
            public int call_result;
            public Object auth_process;
            public int nbfc_report_status;
            public String flag;
            public Object reference_no;
            public Object withdraw_no;
            public Object service_charge;
            public Object withdrawal_service_charge;
            public Object refusal_code;
            public Object customer_id;
            public Object business_id;
            public UserData user;
            public List<?> order_details;
            private int id;
            private String order_no;
            private int user_id;
            private String principal;
            private int loan_days;
            private String status;
            private String created_at;
            private String signed_time;
            private String paid_time;
            private String paid_amount;
            private String api_status;
            private Object api_status_text;
            private Object id_text;
            private Object order_no_text;
            private String status_text;
            private String receivable_amount;
            private LastRepaymentPlanBean last_repayment_plan;




            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getSigned_time() {
                return signed_time;
            }

            public void setSigned_time(String signed_time) {
                this.signed_time = signed_time;
            }

            public String getPaid_time() {
                return paid_time;
            }

            public void setPaid_time(String paid_time) {
                this.paid_time = paid_time;
            }

            public String getPaid_amount() {
                return paid_amount;
            }

            public void setPaid_amount(String paid_amount) {
                this.paid_amount = paid_amount;
            }

            public String getApi_status() {
                switch (api_status) {
                    case "PENDING":
                        return "Verifying";
                    case "PAYING":
                        return "Wait for Disbursement";
                    case "REJECTED":
                        return "Application Failed";
                    case "PAID":
                        return "Wait for repayment";
                    case "FINISHED":
                        return "Cleared";
                    case "CANCEL":
                        return "Application Failed";
                }
                return api_status;
            }

            public void setApi_status(String api_status) {
                this.api_status = api_status;
            }

            public Object getApi_status_text() {
                return api_status_text;
            }

            public void setApi_status_text(Object api_status_text) {
                this.api_status_text = api_status_text;
            }

            public Object getId_text() {
                return id_text;
            }

            public void setId_text(Object id_text) {
                this.id_text = id_text;
            }

            public Object getOrder_no_text() {
                return order_no_text;
            }

            public void setOrder_no_text(Object order_no_text) {
                this.order_no_text = order_no_text;
            }

            public String getStatus_text() {
                switch (status_text) {
                    case "PENDING":
                        return "Verifying";
                    case "PAYING":
                        return "Wait for Disbursement";
                    case "REJECTED":
                        return "Application Failed";
                    case "PAID":
                        return "Wait for repayment";
                    case "FINISHED":
                        return "Cleared";
                    case "CANCEL":
                        return "Application Failed";
                }
                return status_text;
            }

            public void setStatus_text(String status_text) {
                this.status_text = status_text;
            }

            public String getReceivable_amount() {
                return receivable_amount;
            }

            public void setReceivable_amount(String receivable_amount) {
                this.receivable_amount = receivable_amount;
            }

            public LastRepaymentPlanBean getLast_repayment_plan() {
                return last_repayment_plan;
            }

            public void setLast_repayment_plan(LastRepaymentPlanBean last_repayment_plan) {
                this.last_repayment_plan = last_repayment_plan;
            }

            public static class LastRepaymentPlanBean {
                /**
                 * id : 185
                 * merchant_id : 2
                 * user_id : 1031
                 * order_id : 640
                 * no : 88731585FD210538E3
                 * status : 1
                 * overdue_days : 1
                 * appointment_paid_time : 2020-12-09 23:59:59
                 * repay_time : null
                 * repay_amount : 0.00
                 * repay_channel :
                 * reduction_fee : 0.00
                 * created_at : 2020-12-10 17:40:59
                 * updated_at : 2020-12-10 18:03:44
                 * reduction_valid_date :
                 * principal : 0.00
                 * interest_fee : 0.00
                 * overdue_fee : 0.00
                 * gst_processing : 0.00
                 * gst_penalty : 0.00
                 * installment_num : 1
                 * repay_proportion : 99.99
                 * repay_days : 7
                 * loan_days : 7
                 * part_repay_amount : null
                 * can_part_repay : null
                 * ost_prncp : null
                 */

                private int id;
                private int merchant_id;
                private int user_id;
                private int order_id;
                private String no;
                private int status;
                private int overdue_days;
                private String appointment_paid_time;
                private Object repay_time;
                private String repay_amount;
                private String repay_channel;
                private String reduction_fee;
                private String created_at;
                private String updated_at;
                private String reduction_valid_date;
                private String principal;
                private String interest_fee;
                private String overdue_fee;
                private String gst_processing;
                private String gst_penalty;
                private int installment_num;
                private String repay_proportion;
                private int repay_days;
                private int loan_days;
                private Object part_repay_amount;
                private Object can_part_repay;
                private Object ost_prncp;

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

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public int getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(int order_id) {
                    this.order_id = order_id;
                }

                public String getNo() {
                    return no;
                }

                public void setNo(String no) {
                    this.no = no;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getOverdue_days() {
                    return overdue_days;
                }

                public void setOverdue_days(int overdue_days) {
                    this.overdue_days = overdue_days;
                }

                public String getAppointment_paid_time() {
                    return appointment_paid_time;
                }

                public void setAppointment_paid_time(String appointment_paid_time) {
                    this.appointment_paid_time = appointment_paid_time;
                }

                public Object getRepay_time() {
                    return repay_time;
                }

                public void setRepay_time(Object repay_time) {
                    this.repay_time = repay_time;
                }

                public String getRepay_amount() {
                    return repay_amount;
                }

                public void setRepay_amount(String repay_amount) {
                    this.repay_amount = repay_amount;
                }

                public String getRepay_channel() {
                    return repay_channel;
                }

                public void setRepay_channel(String repay_channel) {
                    this.repay_channel = repay_channel;
                }

                public String getReduction_fee() {
                    return reduction_fee;
                }

                public void setReduction_fee(String reduction_fee) {
                    this.reduction_fee = reduction_fee;
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

                public String getReduction_valid_date() {
                    return reduction_valid_date;
                }

                public void setReduction_valid_date(String reduction_valid_date) {
                    this.reduction_valid_date = reduction_valid_date;
                }

                public String getPrincipal() {
                    return principal;
                }

                public void setPrincipal(String principal) {
                    this.principal = principal;
                }

                public String getInterest_fee() {
                    return interest_fee;
                }

                public void setInterest_fee(String interest_fee) {
                    this.interest_fee = interest_fee;
                }

                public String getOverdue_fee() {
                    return overdue_fee;
                }

                public void setOverdue_fee(String overdue_fee) {
                    this.overdue_fee = overdue_fee;
                }

                public String getGst_processing() {
                    return gst_processing;
                }

                public void setGst_processing(String gst_processing) {
                    this.gst_processing = gst_processing;
                }

                public String getGst_penalty() {
                    return gst_penalty;
                }

                public void setGst_penalty(String gst_penalty) {
                    this.gst_penalty = gst_penalty;
                }

                public int getInstallment_num() {
                    return installment_num;
                }

                public void setInstallment_num(int installment_num) {
                    this.installment_num = installment_num;
                }

                public String getRepay_proportion() {
                    return repay_proportion;
                }

                public void setRepay_proportion(String repay_proportion) {
                    this.repay_proportion = repay_proportion;
                }

                public int getRepay_days() {
                    return repay_days;
                }

                public void setRepay_days(int repay_days) {
                    this.repay_days = repay_days;
                }

                public int getLoan_days() {
                    return loan_days;
                }

                public void setLoan_days(int loan_days) {
                    this.loan_days = loan_days;
                }

                public Object getPart_repay_amount() {
                    return part_repay_amount;
                }

                public void setPart_repay_amount(Object part_repay_amount) {
                    this.part_repay_amount = part_repay_amount;
                }

                public Object getCan_part_repay() {
                    return can_part_repay;
                }

                public void setCan_part_repay(Object can_part_repay) {
                    this.can_part_repay = can_part_repay;
                }

                public Object getOst_prncp() {
                    return ost_prncp;
                }

                public void setOst_prncp(Object ost_prncp) {
                    this.ost_prncp = ost_prncp;
                }
            }

            public static class UserData {
                @SerializedName("id")
                public int idX;
                public int merchant_id;
                public int app_id;
                public String telephone;
                public String fullname;
                public String firstname;
                public String middlename;
                public String lastname;
                public String card_type;
                public String id_card_no;
                @SerializedName("status")
                public int statusX;
                @SerializedName("created_at")
                public String created_atX;
                public String updated_at;
                public String platform;
                public String client_id;
                public String channel_id;
                public int quality;
                public String quality_time;
                public String app_version;
                public String api_token;
                public String password;
                public Object customer_id;
                @SerializedName("user_id")
                public Object user_idX;
                public Object idcard;
                public BankCardData bank_card;

                public static class BankCardData {
                    @SerializedName("id")
                    public int idX;
                    @SerializedName("user_id")
                    public int user_idX;
                    public String payment_type;
                    public String account_name;
                    public String account_no;
                    public String bank_name;
                    public String instituion_name;
                    public String channel;
                    @SerializedName("status")
                    public int statusX;
                    @SerializedName("created_at")
                    public String created_atX;
                    public String updated_at;
                    public Object customer_id;
                    public Object apply_id;
                }
            }
        }
    }


}
