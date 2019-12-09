package com.yt.dao;


import com.yt.pojo.Dataset;
import com.yt.pojo.DatasetTwo;
import com.yt.pojo.PaperTopic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 公共功能模块Dao层,使用的是注解SQL的方式
 *
 * @author yt
 * @date 2019/7/17 - 19:24
 */
public interface PublicFunctionMapper {
    /**
     * 更改已失效过期的论文选题状态
     *
     * @param state          状态（是否在有效时间内）
     * @param paper_topic_id 选题id
     * @return
     */
    @Update("UPDATE paper_topic SET state =  #{state} WHERE paper_topic_id = " +
            "#{paper_topic_id}")
    int changeTheStatusOfTheTopic(@Param("state") int state, @Param("paper_topic_id") int paper_topic_id);

    /**
     * 查询出所有论文选题
     *
     * @return
     */
    @Select("select * from paper_topic order by paper_topic_id desc")
    List<PaperTopic> selectAllPaperTopic();

    /**
     * 通过user_id在用户角色表中查询出当前用户所有的角色
     *
     * @param user_id
     * @return
     */
    @Select("SELECT user_role.role_id FROM user_role WHERE user_id = #{user_id}")
    int getUserRoleIds(@Param("user_id") Integer user_id);

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
    @Insert("insert into dataset(data_name,storage_address,upload_date,upload_user_id, " +
            "upload_role_name) values (#{data_name},#{storage_address},#{upload_date}, " +
            "#{upload_user_id},#{upload_role_name})")
    int dataUpload(@Param("data_name") String data_name, @Param("storage_address") String storage_address, @Param("upload_date") String upload_date, @Param("upload_user_id") int upload_user_id, @Param("upload_role_name") String upload_role_name);

    /**
     * 查询当前用户上传的名称资料是否已上传过
     *
     * @param data_name      资料名称(文件的原始名称）
     * @param upload_user_id 该资料名称对应上传用户的ID
     * @return
     */
    @Select("SELECT * FROM dataset where data_name = #{data_name} and upload_user_id = " +
            "#{upload_user_id}")
    Dataset dataToRepeat(@Param("data_name") String data_name, @Param("upload_user_id") int upload_user_id);

    /**
     * 若当前上传的资料已有上传记录，则替换原来的文件，并更新上传日期
     *
     * @param data_name      资料名称(文件的原始名称）
     * @param upload_date    更新时间
     * @param upload_user_id 该资料名称对应上传用户的ID
     * @return
     */
    @Update("update dataset set upload_date=#{upload_date} where data_name = #{data_name} " +
            "and upload_user_id = #{upload_user_id}")
    int updateData(@Param("data_name") String data_name, @Param("upload_date") String upload_date, @Param("upload_user_id") int upload_user_id);

    /**
     * 教师进行资料下载，首先根据当前会话的教师ID查询出该教师所上传的所有资料记录
     * 教师只能下载自己上传的资料和所有管理员上传的资料
     *
     * @param upload_user_id 教师ID
     * @return
     */
    @Select("SELECT d.*, u.user_name, u.real_name FROM dataset d LEFT JOIN `user` u on " +
            "d.upload_user_id = u.user_id WHERE u.user_id = #{upload_user_id} order by " +
            "dataset_id desc")
    List<DatasetTwo> teachersDownload(@Param("upload_user_id") int upload_user_id);

    /**
     * 教师也可以下载所有管理员上传的资料，因此在查询出教师自己上传的资料后还得查询出所有
     * 管理员上传的资料
     *
     * @return
     */
    @Select("select d.*, u.user_name, u.real_name from  dataset d LEFT JOIN `user` u " +
            "on d.upload_user_id= u.user_id WHERE upload_role_name='admin' order by " +
            "dataset_id desc")
    List<DatasetTwo> teachersDownloadTwo();

    /**
     * 管理员下载资料时，管理员权限可下载所有用户上传的资料，故查询出所有上传的资料记录
     *
     * @return
     */
    @Select("select d.*, u.user_name, u.real_name from  dataset d LEFT JOIN `user`" +
            " u on d.upload_user_id= u.user_id  order by dataset_id desc")
    List<DatasetTwo> adminDownload();

    /**
     * 学生或教师资料模糊查询
     *
     * @param data_name      资料名（模糊查询）
     * @param upload_user_id 如果是学生查询，upload_user_id则对应学生导师的ID
     *                       若是教师查询，upload_user_id则是当前教师ID
     * @return
     */
    @Select("select d.*, u.user_name, u.real_name from  dataset d LEFT JOIN `user` u on " +
            "d.upload_user_id= u.user_id where data_name like concat('%',#{data_name},'%')" +
            " and upload_user_id = #{upload_user_id} order by dataset_id desc")
    List<DatasetTwo> informationQuery(@Param("data_name") String data_name, @Param("upload_user_id") int upload_user_id);

    /**
     * 模糊查询出跟data_name相匹配的所有管理员上传的资料
     *
     * @param data_name 资料名（模糊查询）
     * @return
     */
    @Select("select d.*, u.user_name, u.real_name from  dataset d LEFT JOIN `user` u on" +
            " d.upload_user_id= u.user_id where data_name like concat('%',#{data_name},'%')" +
            " and  upload_role_name = 'admin' order by dataset_id desc")
    List<DatasetTwo> adminInformationQuery(@Param("data_name") String data_name);
}
