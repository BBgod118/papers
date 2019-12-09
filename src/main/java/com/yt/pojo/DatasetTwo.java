package com.yt.pojo;

/**
 * @author yt
 * @date 2019/11/3 - 23:24
 */
public class DatasetTwo {
    private Integer dataset_id;
    /**
     * 资料名称
     */
    private String data_name;
    /**
     * 存储地址
     */
    private String storage_address;
    /**
     * 上次日期
     */
    private String upload_date;
    /**
     * 上传用户ID
     */
    private Integer upload_user_id;
    /**
     * 上传用户角色类型
     */
    private String upload_role_name;
    /**
     * 上传用户名
     */
    private String user_name;
    /**
     * 真实姓名
     */
    private String real_name;

    public DatasetTwo() {
    }

    public DatasetTwo(Integer dataset_id, String data_name, String storage_address,
                      String upload_date, Integer upload_user_id, String upload_role_name,
                      String user_name, String real_name) {
        this.dataset_id = dataset_id;
        this.data_name = data_name;
        this.storage_address = storage_address;
        this.upload_date = upload_date;
        this.upload_user_id = upload_user_id;
        this.upload_role_name = upload_role_name;
        this.user_name = user_name;
        this.real_name = real_name;
    }

    public Integer getDataset_id() {
        return dataset_id;
    }

    public void setDataset_id(Integer dataset_id) {
        this.dataset_id = dataset_id;
    }

    public String getData_name() {
        return data_name;
    }

    public void setData_name(String data_name) {
        this.data_name = data_name;
    }

    public String getStorage_address() {
        return storage_address;
    }

    public void setStorage_address(String storage_address) {
        this.storage_address = storage_address;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public Integer getUpload_user_id() {
        return upload_user_id;
    }

    public void setUpload_user_id(Integer upload_user_id) {
        this.upload_user_id = upload_user_id;
    }

    public String getUpload_role_name() {
        return upload_role_name;
    }

    public void setUpload_role_name(String upload_role_name) {
        this.upload_role_name = upload_role_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    @Override
    public String toString() {
        return "DatasetTwo{" + "dataset_id=" + dataset_id + ", data_name='" + data_name + '\'' +
                ", storage_address='" + storage_address + '\'' + ", upload_date='" + upload_date + '\'' + ", upload_user_id=" + upload_user_id + ", upload_role_name='" + upload_role_name + '\'' + ", user_name='" + user_name + '\'' + ", real_name='" + real_name + '\'' + '}';
    }
}
