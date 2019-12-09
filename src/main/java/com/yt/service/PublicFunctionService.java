package com.yt.service;

import com.yt.pojo.Dataset;
import com.yt.pojo.DatasetTwo;
import com.yt.pojo.PaperTopic;
import com.yt.utils.PageDo;

import java.util.List;

/**
 * @author yt
 * @date 2019/10/13 - 16:17
 */
public interface PublicFunctionService {
    /**
     * 更改已失效过期的论文选题状态
     *
     * @param state          状态（是否在有效时间内）
     * @param paper_topic_id 选题id
     * @return
     */
    int changeTheStatusOfTheTopic(int state, int paper_topic_id);

    /**
     * 查询出所有论文选题
     *
     * @return
     */
    List<PaperTopic> selectAllPaperTopic();

    /**
     * 通过user_id在用户角色表中查询出当前用户所有的角色
     *
     * @param user_id 用户id
     * @return
     */
    int getUserRoleIds(Integer user_id);

    /**
     * 更新所有论文选题状态
     *
     * @return
     */
    int updateAllPaperTopicState();

    /**
     * 教师或管理员进行资料上传
     *
     * @param data_name        资料名称
     * @param storage_address  存储路径
     * @param upload_date      上传日期
     * @param upload_user_id   上传用户ID
     * @param upload_role_name 上传用户角色
     * @return
     */
    int dataUpload(String data_name, String storage_address, String upload_date, int upload_user_id, String upload_role_name);

    /**
     * 查询当前用户上传的名称资料是否已上传过
     *
     * @param data_name      资料名称(文件的原始名称）
     * @param upload_user_id 该资料名称对应上传用户的ID
     * @return
     */
    Dataset dataToRepeat(String data_name, int upload_user_id);

    /**
     * 若当前上传的资料已有上传记录，则替换原来的文件，并更新上传日期
     *
     * @param data_name      资料名称(文件的原始名称）
     * @param upload_date    更新时间
     * @param upload_user_id 该资料名称对应上传用户的ID
     * @return
     */
    int updateData(String data_name, String upload_date, int upload_user_id);

    /**
     * 教师进行资料下载，首先根据当前会话的教师ID查询出该教师所上传的所有资料记录
     * 教师只能下载自己上传的资料和所有管理员上传的资料
     *
     * @param upload_user_id 教师ID
     * @return
     */
    List<DatasetTwo> teachersDownload(int upload_user_id);

    /**
     * 教师也可以下载所有管理员上传的资料，因此在查询出教师自己上传的资料后还得查询出所有
     * 管理员上传的资料
     *
     * @return
     */
    /**
     * 教师也可以下载所有管理员上传的资料，因此在查询出教师自己上传的资料后还得查询出所有
     * 管理员上传的资料
     *
     * @param upload_user_id 上传用户ID
     * @param pageNum        起始页
     * @param pageSize       每页显示数量
     * @return
     */
    PageDo<DatasetTwo> teachersDownloadTwo(int upload_user_id, int pageNum, int pageSize);

    /**
     * 管理员下载资料时，管理员权限可下载所有用户上传的资料，故查询出所有上传的资料记录
     *
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    PageDo<DatasetTwo> adminDownload(int pageNum, int pageSize);


    /**
     * 学生或教师资料模糊查询
     *
     * @param data_name      资料名（模糊查询）
     * @param upload_user_id 如果是学生查询，upload_user_id则对应学生导师的ID
     *                       若是教师查询，upload_user_id则是当前教师ID
     * @param pageNum        起始页
     * @param pageSize       每页显示数量
     * @return
     */
    PageDo<DatasetTwo> informationQuery(String data_name, int upload_user_id, int pageNum, int pageSize);

    /**
     * 模糊查询出跟data_name相匹配的所有管理员上传的资料
     *
     * @param data_name 资料名（模糊查询）
     * @return
     */
    List<DatasetTwo> adminInformationQuery(String data_name);
}
