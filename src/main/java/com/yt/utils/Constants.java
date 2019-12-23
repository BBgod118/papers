package com.yt.utils;

/**
 * 常量工具类，定义为接口的的方式，默认为final修饰
 *
 * @author yt
 * @date 2019/9/24 - 2:51
 */
public interface Constants {
    /**
     * 将登录用户信息放入session中的key
     */
    String SESSION_USER = "SESSION_USER";
    String DATABASE_OPERATION_EXCEPTION = "数据库操作异常失败";
    String ADD_SUCCESS = "添加成功";
    String REGISTERED = "该用户名已注册";
    String GROUP_SUCCESS_ONE = "分组成功";
    String TESTAMENT_HASHEEM_GROUPED = "该学生已分组";
    String TEACHER_OR_STUDENT_USERNAME_DO_EXIST = "教师或学生用户名不存在";
    /**
     * 开题报告
     */
    String OPENING_QUESTION = "opening_question";
    /**
     * 中期报告
     */
    String MEDIUM_TERM = "medium_term";
    /**
     * 论文
     */
    String PAPERS = "papers";
    /**
     * 已打回
     */
    Integer HAS_BEEN_CALLED_BACK = 0;
    /**
     * 未打回
     */
    Integer NOT_RETURNED = 1;
    /**
     * 学生角色id
     */
    Integer STUDENT_ROLE = 1;
    /**
     * 教师角色id
     */
    Integer TEACHER_ROLE = 2;
    /**
     * 管理员角色id
     */
    Integer ADMIN_ROLE = 3;
    /**
     * 论文选题状态 1有效
     */
    Integer EFFECTIVE = 1;
    /**
     * 论文选题状态 0无效过期
     */
    Integer INVALID = 0;
    /**
     * 用户名已注册
     */
    Integer USERNAME_REGISTERED = 1001;
    /**
     * 数据库操作异常
     */
    Integer ATABASEOPERATION_EXCEPTION = 1002;
    /**
     * 添加学生成功
     */
    Integer ADD_INGSUCCESS_TOSTUDENTS = 1003;
    /**
     * 添加教师成功
     */
    Integer ADD_EDTEACHER_SUCCESS = 1004;
    /**
     * 用户名不存在
     */
    Integer USER_NOT_REGISTERED = 1005;
    /**
     * 分组成功
     */
    Integer GROUP_SUCCESS = 1006;
    /**
     * 该学生已分组
     */
    Integer TE_STUDENT_HAS_BEEN_GROUPED = 1007;
    /**
     * 入参参数有空值
     */
    Integer PARAM_IS_BLANK = 1008;
    /**
     * 论文选题状态更改成功
     */
    Integer PAPER_TOPIC_SUCCESSFUL_STATUS_UPDATE = 1009;

    /**
     * 管理员调整分组成功
     */
    Integer ADJUSTMENT_GROUPING = 1010;
    /**
     * 用户注册默认密码
     */
    String DEFAULTPASSWORD = "123";
    /**
     * excel文件存放位置
     */
    String USER_REGISTRATION_INFORMATION_SAVING_DIRECTORY = "/upload/excel/";
    /**
     * 论文文件存放位置
     */
    String PAPER_STORAGE_DIRECTORY = "/upload/papers/";
    /**
     * 索引库路径
     */
    String INDEX_PATH = "E:/Spring/papers/src/main/webapp/Indexes";
    /**
     * 论文文件存放位置
     */
    String PAPER_STORAGE_DIRECTORY_TWO = "E:/Spring/papers/target/papers-1.0-SNAPSHOT/upload/papers/";
    /**
     * 是否上传过论文
     */
    String HAVE_YOU_UPLOADED_PAPERS = "HAVE_YOU_UPLOADED_PAPERS";
    /**
     * 是否上传过开题报告
     */
    String UPLOADED_OPENING_REPORT = "UPLOADED_OPENING_REPORT";
    /**
     * 是否上传过中期报告
     */
    String UPLOADED_INTERIM_REPORT = "UPLOADED_INTERIM_REPORT";
    /**
     * 已上传
     */
    Integer ALREADY_UPLOADED = 1;
    /**
     * 未上传
     */
    Integer NOT_UPLOADED = 0;

    /**
     * 论文已评分
     */
    Integer SCORE_ALREADY = 001;

    /**
     * 成功
     */
    Integer SUCCESS = 100;
    /**
     * 图片路径
     */
    String url = "../papers/images/";
}
