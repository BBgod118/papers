package com.yt.controller;

import com.yt.pojo.*;
import com.yt.service.AdminService;
import com.yt.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员控制器类
 *
 * @author yt
 * @date 2019/10/8 - 16:20
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 管理员添加学生账户
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/adminAddStudentAccounts",
            produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public Result adminAddStudentAccounts(StudentInformation studentInformation) {
        int code = adminService.studentRegister(studentInformation);
        if (code == Constants.ADD_INGSUCCESS_TOSTUDENTS) {
            //添加成功
            return Result.success();
        }
        if (code == Constants.USERNAME_REGISTERED) {
            //用户名已注册
            return Result.failure(ResultCodeEnum.USER_NAMEREGISTERED);
        }
        if (code == Constants.PARAM_IS_BLANK) {
            //入参参数有空值
            return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
    }

    /**
     * 管理员添加教师账户
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/adminAddTeacherAccounts",
            produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public Result adminAddTeacherAccounts(TeacherInformation teacherInformation) {
        int code = adminService.teacherRegister(teacherInformation);
        System.out.println(code);
        if (code == Constants.ADD_EDTEACHER_SUCCESS) {
            //添加成功
            return Result.success();
        }
        if (code == Constants.USERNAME_REGISTERED) {
            //该用户名已注册
            return Result.failure(ResultCodeEnum.USER_NAMEREGISTERED);
        }
        if (code == Constants.PARAM_IS_BLANK) {
            //入参参数有空值
            return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
    }

    /**
     * 管理员使用Excel表批量添加学生账户
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/adminBatchAdditionStudentAccounts",
            produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public Result adminBatchAdditionStudentAccounts(
            @RequestParam("uploadfile") List<MultipartFile> uploadfile,
            HttpServletRequest request) {
        //判断上传文件是否存在
        if (!uploadfile.isEmpty() && uploadfile.size() > 0) {
            //循环输出上传的文件
            for (MultipartFile file : uploadfile) {
                //获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                //设置上传文件的保存地址目录
                String dirPath = request.getServletContext().getRealPath(Constants.
                        USER_REGISTRATION_INFORMATION_SAVING_DIRECTORY);
                File filePath = new File(dirPath);
                //如果保存文件的地址不存在，就先创建目录
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                try {
                    //使用MultipartFile接口的方法完成文件上传到指定位置
                    file.transferTo(new File(dirPath + originalFilename));
                    //定义map来存储导入情况信息
                    Map<String, String> map = new HashMap<>();
                    //获取管理员上传的excel表的路径
                    String address = dirPath + originalFilename;
                    File fileTwo = new File(address);
                    //获取Excel表中的学生信息
                    List<StudentInformation> excelData = StudentPOIExcelUtil.
                            readExcel(fileTwo);
                    for (int i = 0; i < excelData.size(); i++) {
                        StudentInformation studentInformation = new StudentInformation();
                        studentInformation.setUser_name(excelData.get(i).getUser_name());
                        studentInformation.setUser_pwd(Constants.DEFAULTPASSWORD);
                        studentInformation.setReal_name(excelData.get(i).getReal_name());
                        studentInformation.setUser_phone(excelData.get(i).getUser_phone());
                        studentInformation.setGrade(excelData.get(i).getGrade());
                        studentInformation.setDepartment(excelData.get(i).getDepartment());
                        studentInformation.setClasses(excelData.get(i).getClasses());
                        //调用管理员添加学生账户
                        int code = adminService.studentRegister(studentInformation);
                        if (code == Constants.ADD_INGSUCCESS_TOSTUDENTS) {
                            //添加成功，返回添加成功的学生用户名
                            map.put(Constants.ADD_SUCCESS + i, studentInformation.
                                    getUser_name());
                        }
                        if (code == Constants.USERNAME_REGISTERED) {
                            //该用户名已注册，返回已注册的学生用户名
                            map.put(Constants.REGISTERED + i, studentInformation.
                                    getUser_name());
                        }
                        if (code == Constants.ATABASEOPERATION_EXCEPTION) {
                            //数据库操作异常失败，返回添加失败的学生用户名
                            map.put(Constants.DATABASE_OPERATION_EXCEPTION + i,
                                    studentInformation.getUser_name());
                        }
                    }
                    System.out.println(map);
                    //导入完成
                    return Result.failure(ResultCodeEnum.Excel_TABLE_IMPORT_COMLETED, map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.failure(ResultCodeEnum.FILE_DOES_NOT_EXIST);
    }

    /**
     * 管理员使用Excel表批量添加教师账户
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/adminBatchAdditionTeacherAccounts",
            produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public Result adminBatchAdditionTeacherAccounts(
            @RequestParam("uploadfile") List<MultipartFile> uploadfile,
            HttpServletRequest request) {
        //判断上传文件是否存在
        if (!uploadfile.isEmpty() && uploadfile.size() > 0) {
            //循环输出上传的文件
            for (MultipartFile file : uploadfile) {
                //获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                //设置上传文件的保存地址目录
                String dirPath = request.getServletContext().getRealPath(Constants.
                        USER_REGISTRATION_INFORMATION_SAVING_DIRECTORY);
                File filePath = new File(dirPath);
                //如果保存文件的地址不存在，就先创建目录
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                try {
                    //使用MultipartFile接口的方法完成文件上传到指定位置
                    file.transferTo(new File(dirPath + originalFilename));
                    //定义map来存储导入情况信息
                    Map<String, String> map = new HashMap<>();
                    //获取管理员上传的excel文件的目录
                    String address = dirPath + originalFilename;
                    File fileTwo = new File(address);
                    //获取Excel表中的学生信息
                    List<TeacherInformation> excelData = TeacherPOIExcelUtil.
                            readExcel(fileTwo);
                    for (int i = 0; i < excelData.size(); i++) {
                        TeacherInformation teacherInformation = new TeacherInformation();
                        teacherInformation.setUser_name(excelData.get(i).getUser_name());
                        teacherInformation.setUser_pwd(Constants.DEFAULTPASSWORD);
                        teacherInformation.setReal_name(excelData.get(i).getReal_name());
                        teacherInformation.setUser_phone(excelData.get(i).getUser_phone());
                        teacherInformation.setTeacher_title(excelData.get(i).getTeacher_title());
                        //调用管理员添加学生账户
                        int code = adminService.teacherRegister(teacherInformation);
                        if (code == Constants.ADD_EDTEACHER_SUCCESS) {
                            //添加成功
                            //返回添加成功的学生用户名
                            map.put(Constants.ADD_SUCCESS + i, teacherInformation.
                                    getUser_name());
                        }
                        if (code == Constants.USERNAME_REGISTERED) {
                            //该用户名已注册
                            //返回已注册的学生用户名
                            map.put(Constants.REGISTERED + i, teacherInformation.
                                    getUser_name());
                        }
                        if (code == Constants.ATABASEOPERATION_EXCEPTION) {
                            //数据库操作异常失败
                            //返回添加失败的学生用户名
                            map.put(Constants.DATABASE_OPERATION_EXCEPTION + i,
                                    teacherInformation.getUser_name());
                        }
                    }
                    System.out.println(map);
                    //导入完成
                    return Result.failure(ResultCodeEnum.Excel_TABLE_IMPORT_COMLETED, map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.failure(ResultCodeEnum.FILE_DOES_NOT_EXIST);
    }

    /**
     * 管理员对教师学生进行分组
     *
     * @param teacher_name 教师用户名
     * @param student_name 学生用户名
     * @return
     */
    @RequestMapping(value = "/adminGroupStudents",
            produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public Result adminGroupStudents(String teacher_name, String student_name) {
        if (teacher_name != null && !("").equals(teacher_name) && student_name != null
                && !("").equals(student_name)) {
            //进行分组
            int code = adminService.adminGroup(teacher_name, student_name);
            if (code == Constants.GROUP_SUCCESS) {
                //分组成功
                return Result.success();
            }
            if (code == Constants.USER_NOT_REGISTERED) {
                return Result.failure(ResultCodeEnum.USER_NOT_REGISTERED);
            }
            if (code == Constants.TE_STUDENT_HAS_BEEN_GROUPED) {
                //该学生已分组
                return Result.failure(ResultCodeEnum.TE_STUDENT_HAS_BEEN_GROUPED);
            }
            return Result.failure(ResultCodeEnum.Database_Operation_Exception);
        }
        //入参参数有空值
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 管理员通过excel表进行批量分组
     *
     * @param uploadfile
     * @param request
     * @return
     */
    @RequestMapping(value = "/adminBatchGroupStudents",
            produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public Result adminBatchGroupStudents(
            @RequestParam("uploadfile") List<MultipartFile> uploadfile,
            HttpServletRequest request) {
        //判断上传文件是否存在
        if (!uploadfile.isEmpty() && uploadfile.size() > 0) {
            //循环输出上传的文件
            for (MultipartFile file : uploadfile) {
                //获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                //设置上传文件的保存地址目录
                String dirPath = request.getServletContext().getRealPath(Constants.
                        USER_REGISTRATION_INFORMATION_SAVING_DIRECTORY);
                File filePath = new File(dirPath);
                //如果保存文件的地址不存在，就先创建目录
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                try {
                    //使用MultipartFile接口的方法完成文件上传到指定位置
                    file.transferTo(new File(dirPath + originalFilename));
                    //定义map来存储导入情况信息
                    Map<String, String> map = new HashMap<>();
                    //获取管理员上传的excel文件的目录
                    String address = dirPath + originalFilename;
                    File fileTwo = new File(address);
                    //获取Excel表中的学生信息
                    List<GroupExcel> excelData = GroupPOIExcelUtil.readExcel(fileTwo);
                    for (int i = 0; i < excelData.size(); i++) {
                        //调用分组方法
                        int code = adminService.adminGroup(excelData.get(i).
                                getTeacher_name(), excelData.get(i).getStudent_name());
                        if (code == Constants.GROUP_SUCCESS) {
                            //分组成功,返回分组成功的学生用户名
                            map.put(Constants.GROUP_SUCCESS_ONE + i, excelData.
                                    get(i).getStudent_name());
                        }
                        if (code == Constants.USER_NOT_REGISTERED) {
                            //学生或教师用户名未注册
                            map.put(Constants.TEACHER_OR_STUDENT_USERNAME_DO_EXIST + i,
                                    excelData.get(i).getTeacher_name() + " " +
                                            excelData.get(i).getStudent_name());
                        }
                        if (code == Constants.ATABASEOPERATION_EXCEPTION) {
                            //数据库操作异常失败
                            map.put(Constants.DATABASE_OPERATION_EXCEPTION + i,
                                    excelData.get(i).getStudent_name());
                        }
                        if (code == Constants.TE_STUDENT_HAS_BEEN_GROUPED) {
                            //该学生已分组
                            map.put(Constants.TESTAMENT_HASHEEM_GROUPED + i,
                                    excelData.get(i).getStudent_name());
                        }
                    }
                    System.out.println(map);
                    //分组完成
                    return Result.failure(ResultCodeEnum.Excel_TABLE_IMPORT_COMLETED, map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.failure(ResultCodeEnum.FILE_DOES_NOT_EXIST);
    }

    /**
     * 管理员查看系统所有学生信息
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    @RequestMapping(value = "/selectAllStudent", produces = "application/json;charset=utf-8",
            method = RequestMethod.GET)
    public Result selectAllStudent(int pageNum, int pageSize){
        if (pageNum >= 1 && pageSize >= 1) {
            PageDo<AllStudentInformation> studentInformationList =
                    adminService.selectAllStudent(pageNum, pageSize);
            return Result.success(studentInformationList);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 管理员查看系统所有教师信息
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    @RequestMapping(value = "/selectAllTeacher", produces = "application/json;charset=utf-8",
            method = RequestMethod.GET)
    public Result selectAllTeacher(int pageNum, int pageSize){
        if (pageNum >= 1 && pageSize >= 1) {
            PageDo<AllTeacherInformation> teacherInformationList =
                    adminService.selectAllTeacher(pageNum, pageSize);
            return Result.success(teacherInformationList);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 管理员查看系统所有分组信息
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    @RequestMapping(value = "/selectUserInformationTwo", produces = "application/json;charset=utf-8",
            method = RequestMethod.GET)
    public Result selectUserInformationTwo(int pageNum, int pageSize){
        if (pageNum >= 1 && pageSize >= 1) {
            PageDo<AllGroupInformation> list =
                    adminService.selectUserInformationTwo(pageNum, pageSize);
            return Result.success(list);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 管理员进行分组调整
     * @param teacher_name 教师用户名
     * @param student_name 学生用户名
     * @return
     */
    @RequestMapping(value = "/adjustmentGrouping", produces = "application/json;charset=utf-8",
            method = RequestMethod.POST)
    public Result adjustmentGrouping(String teacher_name, String student_name){
        if (teacher_name != null && !("").equals(teacher_name) && student_name != null
                && !("").equals(student_name)) {
            int code = adminService.adjustmentGrouping(teacher_name, student_name);
            if (code == Constants.ADJUSTMENT_GROUPING){
                return Result.success();
            }
            if (code == Constants.USER_NOT_REGISTERED){
                return Result.failure(ResultCodeEnum.USER_NOT_REGISTERED);
            }
            return Result.failure(ResultCodeEnum.Database_Operation_Exception);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 管理员进行账户封禁或解封（账户为封禁状态-0，正常状态-1）
     * @param user_name 用户名
     * @param state 账户状态
     * @return
     */
    @RequestMapping(value = "/updateUserSate", produces = "application/json;charset=utf-8",
            method = RequestMethod.POST)
    public Result updateUserSate(int state, String user_name){
        if (user_name != null && !("").equals(user_name) ) {
            int code = adminService.updateUserSate(state, user_name);
            if (code > 0){
                return Result.success();
            }
            return Result.failure(ResultCodeEnum.Database_Operation_Exception);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 管理员查看所有权限功能路径
     * @return
     */
    @RequestMapping(value = "/selectAllPermission", produces = "application/json;charset=utf-8",
            method = RequestMethod.GET)
    public Result selectAllPermission(){
        List<Permission> list = adminService.selectAllPermission();
        return Result.success(list);
    }

    /**
     * 管理员添加权限功能路径
     * @param perms_name 功能说明
     * @param url        功能路径
     * @return
     */
    @RequestMapping(value = "/insertPermission", produces = "application/json;charset=utf-8",
            method = RequestMethod.POST)
    public Result insertPermission(String perms_name, String url){
        int row = adminService.insertPermission(perms_name, url);
        if (row > 0){
            return Result.success();
        }
        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
    }

    /**
     * 管理员查看所有角色权限对应列表
     * @return
     */
    @RequestMapping(value = "/selectAllRolePermission", produces = "application/json;charset=utf-8",
            method = RequestMethod.GET)
    public Result selectAllRolePermission(){
        List<RolePermissionTwo> list = adminService.selectAllRolePermission();
        return Result.success(list);
    }

    /**
     * 管理员添加权限功能路径
     * @param role_id       角色ID
     * @param permission_id 功能路径ID
     * @return
     */
    @RequestMapping(value = "/insertRolePermission", produces = "application/json;charset=utf-8",
            method = RequestMethod.POST)
    public Result insertRolePermission(int role_id, int permission_id){
        int row = adminService.insertRolePermission(role_id, permission_id);
        if (row > 0){
            return Result.success();
        }
        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
    }
}

