package com.yt.utils;

/**
 * 返回JSON数据枚举类
 * @author yt
 * @date 2019/10/9 - 11:26
 */
public enum ResultCodeEnum {
    /* 成功状态码*/
    SUCCESS(1,"成功"),
    Excel_TABLE_IMPORT_COMLETED(2,"excel表导入完成，导入清情况如下"),
    HAVE_CHOSEN_PAPER_TOPIC(3,"该学生已选择的论文选题如下"),
    NO_CHOICE_PAPER_TOPIC(4,"该学生未选择选题，该学生导师发布的所有有效的选题如下"),
    SUCCESSFULLY_UPDATED(5,"更新成绩成功"),
    STUDENT_LOGING_SUCCESS(6,"学生登录成功"),
    TEACHER_LOGING_SUCCESS(7,"教师登录成功"),
    ADMIN_LOGING_SUCCESS(8,"管理员登录成功"),
    /* 操作未通过——失败 */
    USER_LOCKED(5001,"该账户已被锁定"),

    /* 参数错误：1001-1999*/
    PARAM_IS_BLANK(1001,"入参参数不合法"),
    /* 用户错误 2001 - 2999*/
    USER_NOT_LOGGED_IN(2001,"用户未登录，请登录"),
    USER_LOGIN_ERROR(2002,"账号不存在或密码错误"),
    USER_Insufficient_Authority(2003,"用户权限不足"),
    Old_Password_Error(2004,"旧密码错误"),
    Verification_Code_Error(2005,"用户输入验证码错误"),
    USER_NAMEREGISTERED(2006,"该用户名已注册"),
    FILE_DOES_NOT_EXIST(2007,"上传文件不存在"),
    USER_NOT_REGISTERED(2008,"教师或学生用户名错误"),
    TE_STUDENT_HAS_BEEN_GROUPED(2009,"该学生已分组"),
    THE_STUDENT_HAVE_CHOSEN_PAPER_TOPIC(2010,"该学生已选择论文选题"),
    STUDENT_NOT_GROUP(2011,"该学生还未分组"),
    REPEATED_UPLOAD_OF_PAPERS(2012,"该学生已上传过论文，若需重新上传请联系导师打回重新上传"),
    THIS_TYPE_OF_REPORT_ALREADY_EXISTS(2013,"该类型的报告已上传过，" +
            "若需重新上传请联系导师打回重新上传"),
    STUDENT_HAVA_NOT_UPLOADED_PAPERS(2014,"该学生还未上传过论文"),
    TEACHER_HAS_NOT_YET_SCORED(2015,"教师还未对该学生的论文进行评分"),
    SCORE_ALREADY(2016, "论文已评分，无法进行打回！"),
    NOT_CALL_BACK(2017,"该学生没有被打回的论文或报告文件"),
    /* 异常：3001-3999 */
    Database_Operation_Exception(3001,"数据库操作异常"),
    /* 邮箱操作：4001-4999 */
    The_Eailbox_Has_Been_Bound(4001,"该邮箱已被绑定"),
    The_Mailbox_Is_Not_Bound(4002,"该邮箱未绑定"),

    ;

    /**
     * 返回状态码码
     */
    private Integer code;

    /**
     * 返回信息描述
     */
    private String message;

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }


    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
