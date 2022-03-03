package com.u1.gocashm.model.response;

/**
 * @author hewei
 * @date 2018/9/7 0007 下午 6:47
 */
public class User {

    private int id;
    private int merchant_id;
    private int app_id;
    private String telephone;
    private String fullname;
    private String firstname;
    private String middlename;
    private String lastname;
    private String card_type;
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
    private String email;
    public String contactsStatus;
    private BankCardBean bank_card;
    private OrderListModel.OrderList.Order order;

    public int basicDetailStatus;
    public int bankCardStatus;
    public int idCardStatus;


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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
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
    /**
     * 是否是复贷
     * @return
     */
    public boolean isReload() {
        return this.quality == 1;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BankCardBean getBank_card() {
        return bank_card;
    }

    public void setBank_card(BankCardBean bank_card) {
        this.bank_card = bank_card;
    }

    public String getContactsStatus() {
        return contactsStatus;
    }

    public void setContactsStatus(String contactsStatus) {
        this.contactsStatus = contactsStatus;
    }

    public static class BankCardBean {
        /**
         * id : 345
         * user_id : 68783
         * payment_type : bank
         * account_name : xjdj qqq aaap
         * account_no : 6446496446
         * bank_name : RCBC
         * instituion_name :
         * channel :
         * status : 1
         * created_at : 2021-03-07 22:21:06
         * updated_at : 2021-03-07 22:21:06
         */

        private int id;
        private int user_id;
        private String payment_type;
        private String account_name;
        private String account_no;
        private String bank_name;
        private String instituion_name;
        private String channel;
        private int status;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getAccount_no() {
            return account_no;
        }

        public void setAccount_no(String account_no) {
            this.account_no = account_no;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getInstituion_name() {
            return instituion_name;
        }

        public void setInstituion_name(String instituion_name) {
            this.instituion_name = instituion_name;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
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
    }

    public OrderListModel.OrderList.Order getOrder() {
        return order;
    }

    public void setOrder(OrderListModel.OrderList.Order order) {
        this.order = order;
    }
}
