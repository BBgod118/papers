package com.yt.controller;

import com.yt.pojo.*;
import com.yt.service.PublicFunctionService;
import com.yt.service.StudentService;
import com.yt.service.TeacherService;
import com.yt.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 学生控制器类
 *
 * @author yt
 * @date 2019/10/13 - 16:05
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private PublicFunctionService publicFunctionService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 学生查看论文选题
     *
     * @return
     */
    @RequestMapping(value = "/studentViewingTopics", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result studentViewingTopics(int pageNum, int pageSize) {
        if (pageNum >= 1 && pageSize >= 1) {
            Map<String, Integer> map = new HashMap<>();
            // 更新所有选题的状态
            publicFunctionService.updateAllPaperTopicState();
            // 获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            // 先查询该学生是否已选择选题
            StudentSelectedTopic studentSelectedTopic =
                    studentService.selectHaveChosenPaperTopic(user.getUser_id());
            if (studentSelectedTopic != null) {
                // 若该学生已选择了选题，则将该学生已选择的选题查询出来
                PaperTopic paperTopic =
                        studentService.selectHaveChosenPaperTopicInformation(
                                studentSelectedTopic.getPaper_topic_id());
                // 查询出当前学生是否已提交过论文、开题报告或中期报告
                Papers papers = studentService.papersToRepeat(user.getUser_id());
                Presentation presentation = studentService.permissionToRepeat(
                        user.getUser_id(),
                        Constants.OPENING_QUESTION);
                Presentation presentation2 = studentService.permissionToRepeat(
                        user.getUser_id(),
                        Constants.MEDIUM_TERM);
                //判断当前学生是否上传了
                if (papers != null) {
                    map.put(Constants.HAVE_YOU_UPLOADED_PAPERS, Constants.ALREADY_UPLOADED);
                } else {
                    map.put(Constants.HAVE_YOU_UPLOADED_PAPERS, Constants.NOT_UPLOADED);
                }

                if (presentation != null) {
                    map.put(Constants.UPLOADED_OPENING_REPORT, Constants.ALREADY_UPLOADED);
                } else {
                    map.put(Constants.UPLOADED_OPENING_REPORT, Constants.NOT_UPLOADED);
                }

                if (presentation2 != null) {
                    map.put(Constants.UPLOADED_INTERIM_REPORT, Constants.ALREADY_UPLOADED);
                } else {
                    map.put(Constants.UPLOADED_INTERIM_REPORT, Constants.NOT_UPLOADED);
                }
                //将选题信息和提交信息放入集合中一起返回
                List<Object> list = new ArrayList<>();
                list.add(paperTopic);
                list.add(map);
                return Result.failure(ResultCodeEnum.HAVE_CHOSEN_PAPER_TOPIC, list);
            }
            // 若该学生未选择选题，查询出该学生导师的信息
            Group group = studentService.selectTeacherID(user.getUser_id());
            if (group != null) {
                // 将该学生导师发布的所有有效的选题查询出来并分页
                PageDo<PaperTopic> list =
                        studentService.selectPaperTopicPaging(group.getTeacher_id(),
                                Constants.EFFECTIVE, pageNum, pageSize);
                return Result.failure(ResultCodeEnum.NO_CHOICE_PAPER_TOPIC, list);
            }
            // 该学生还未分组
            return Result.failure(ResultCodeEnum.STUDENT_NOT_GROUP);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 学生选择论文选题
     *
     * @param paper_topic_id 选题ID
     * @return
     */
    @RequestMapping(value = "/studentChoicePaperTopic", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result studentChoicePaperTopic(int paper_topic_id) {
        if (paper_topic_id > 0) {
            // 学生选择选题时先查询该学生是否已选择选题
            // 获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            // 先查询该学生是否已选择选题
            StudentSelectedTopic studentSelectedTopic =
                    studentService.selectHaveChosenPaperTopic(user.getUser_id());
            if (studentSelectedTopic == null) {
                // 若还未现在则进行选择
                int row = studentService.studentChoosingTopics(user.getUser_id(),
                        paper_topic_id);
                if (row > 0) {
                    return Result.success();
                }
                return Result.failure(ResultCodeEnum.Database_Operation_Exception);
            }
            return Result.failure(ResultCodeEnum.THE_STUDENT_HAVE_CHOSEN_PAPER_TOPIC);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 学生上传论文
     *
     * @param uploadfile
     * @param request
     * @param paper_topic_id 学生选择的论文选题ID
     * @return
     */
    @RequestMapping(value = "/studentUploadPapers", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Result studentUploadPapers(
            @RequestParam("uploadfile") List<MultipartFile> uploadfile,
            HttpServletRequest request, int paper_topic_id) {
        // 判断上传文件是否存在
        if (!uploadfile.isEmpty() && uploadfile.size() > 0) {
            // 获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            // 设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // release_date为当前系统时间作为发布时间
            String release_date = df.format(new Date());
            // 判断当前学生是否上传过论文
            Papers papers = studentService.papersToRepeat(user.getUser_id());
            if (papers == null) {
                // 循环输出上传的文件
                for (MultipartFile file : uploadfile) {
                    // 获取上传文件的原始名称
                    String originalFilename = file.getOriginalFilename();
                    // 设置上传文件的保存地址目录
                    String dirPath =
                            request.getServletContext().getRealPath(
                                    Constants.PAPER_STORAGE_DIRECTORY);
                    File filePath = new File(dirPath);
                    // 如果保存文件的地址不存在，就先创建目录
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    try {
                        // 使用用户名加文件名的方式存储文件
                        String newFileName = user.getUser_id() + originalFilename;
                        // 使用MultipartFile接口的方法完成文件上传到指定位置
                        file.transferTo(new File(dirPath + newFileName));
                        // 将该次上传记录存入数据库
                        int row = studentService.paperUpload(originalFilename, dirPath,
                                release_date, user.getUser_id(), user.getUser_name(),
                                paper_topic_id);
                        //给该论文添加索引
                        IndexManager.addDocument(Constants.INDEX_PATH,
                                Constants.PAPER_STORAGE_DIRECTORY_TWO+newFileName);
                        if (row > 0) {
                            // 上传成功
                            return Result.success();
                        }
                        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 该学生已上传过论文，若需重新上传请联系导师打回重新上传
            return Result.failure(ResultCodeEnum.REPEATED_UPLOAD_OF_PAPERS);
        }
        return Result.failure(ResultCodeEnum.FILE_DOES_NOT_EXIST);
    }

    /**
     * 学生上传开题、中期报告
     *
     * @param uploadfile
     * @param request
     * @param paper_topic_id 学生选择的论文选题ID
     * @param file_type      报告类型（opening_question、medium_term）
     * @return
     */
    @RequestMapping(value = "/studentUploadReport", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Result studentUploadReport(
            @RequestParam("uploadfile") List<MultipartFile> uploadfile,
                                      HttpServletRequest request, int paper_topic_id,
                                      String file_type) {
        // 判断上传文件是否存在
        if (!uploadfile.isEmpty() && uploadfile.size() > 0) {
            // 获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            // 设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // release_date为当前系统时间作为发布时间
            String release_date = df.format(new Date());
            // 判断当前学生是否上传过当前类型的报告
            Presentation Presentation = studentService.permissionToRepeat(user.getUser_id(),
                    file_type);
            if (Presentation == null) {
                // 循环输出上传的文件
                for (MultipartFile file : uploadfile) {
                    // 获取上传文件的原始名称
                    String originalFilename = file.getOriginalFilename();
                    // 设置上传文件的保存地址目录
                    String dirPath =
                            request.getServletContext().getRealPath(
                                    Constants.PAPER_STORAGE_DIRECTORY);
                    File filePath = new File(dirPath);
                    // 如果保存文件的地址不存在，就先创建目录
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    try {
                        // 使用用户名加文件名的方式存储文件
                        String newFileName = user.getUser_id() + originalFilename;
                        // 使用MultipartFile接口的方法完成文件上传到指定位置
                        file.transferTo(new File(dirPath + newFileName));
                        // 将该次上传记录存入数据库
                        int row = studentService.permissionUpload(originalFilename,
                                release_date,
                                file_type, user.getUser_id(), paper_topic_id);
                        if (row > 0) {
                            // 上传成功
                            return Result.success();
                        }
                        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 该学生已上传过该类型的报告，若需重新上传请联系导师打回重新上传
            return Result.failure(ResultCodeEnum.THIS_TYPE_OF_REPORT_ALREADY_EXISTS);
        }
        return Result.failure(ResultCodeEnum.FILE_DOES_NOT_EXIST);
    }

    /**
     * 学生查看论文成绩
     *
     * @return
     */
    @RequestMapping(value = "/studentResultInquiry", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result studentResultInquiry() {
        // 获取当前用户的信息
        User user = SubjectGetUserInformation.getUserInformation();
        // 查询出当前学生提交的论文文件ID
        Papers papers = studentService.papersToRepeat(user.getUser_id());
        if (papers != null) {
            // 查询出当前学生的论文成绩
            PapersRating papersRating = studentService.
                    achievementQuery(papers.getPapers_id());
            if (papersRating != null) {
                // 成功并返回学生论文成绩信息
                return Result.success(papersRating);
            }
            return Result.failure(ResultCodeEnum.TEACHER_HAS_NOT_YET_SCORED);
        }
        // 该学生还未上传过论文
        return Result.failure(ResultCodeEnum.STUDENT_HAVA_NOT_UPLOADED_PAPERS);
    }

    /**
     * 提示学生那些论文文件被导师打回了
     * @return
     */
    @RequestMapping(value = "/selectCallBackTheRecord", produces = "application/json;charset=utf-8")
    public Result selectCallBackTheRecord() {
        Map<String, Integer> map = new HashMap<>();
        //获取当前用户的信息
        User user = SubjectGetUserInformation.getUserInformation();
        //查询当前登录用户那些文件被打回了
        List<String> list = teacherService.selectCallBackTheRecord(user.getUser_id());
        System.out.println(list);
        if (!list.equals("[]")) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(Constants.OPENING_QUESTION)){
                    map.put(Constants.OPENING_QUESTION+i, Constants.HAS_BEEN_CALLED_BACK);
                }
                if (list.get(i).equals(Constants.MEDIUM_TERM)){
                    map.put(Constants.MEDIUM_TERM+i, Constants.HAS_BEEN_CALLED_BACK);
                }
                if (list.get(i).equals(Constants.PAPERS)){
                    map.put(Constants.PAPERS+i, Constants.HAS_BEEN_CALLED_BACK);
                }
            }
            return Result.success(map);
        }
        return  Result.failure(ResultCodeEnum.NOT_CALL_BACK);
    }
}
