package com.yt.controller;

import com.yt.pojo.*;
import com.yt.service.PublicFunctionService;
import com.yt.service.TeacherService;
import com.yt.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 教师控制器类
 *
 * @author yt
 * @date 2019/10/11 - 21:23
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private PublicFunctionService publicFunctionService;

    /**
     * 教师给自己对应的学生发布论文选题
     *
     * @param paper_topic  要发布的选题名称
     * @param closing_date 选题截止时间
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/teacherPublishThesisTopic", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Result teacherPublishThesisTopic(String paper_topic, String closing_date) throws ParseException {
        if (paper_topic != null && !("").equals(paper_topic) && closing_date != null && !("").equals(closing_date)) {
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //release_date为当前系统时间作为发布时间
            String release_date = df.format(new Date());
            //将截至日期yyyyMMdd HH mm ss格式转换为yyyy-MM-dd HH:mm:ss
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    parse(closing_date);
            //endTimeStr截至时间
            String endTimeStr = df.format(date);
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //将该教师当前发布的论文选题存入
            int row = teacherService.thesisSelection(paper_topic, release_date, endTimeStr, user.getUser_id());
            if (row > 0) {
                return Result.success();
            }
            return Result.failure(ResultCodeEnum.Database_Operation_Exception);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 教师查看已发布的论文选题，包括有效和过期的并分页
     *
     * @param pageNum  起始页
     * @param pageSize 每页显示条数
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/teacherViewPublishedTopics", produces = "application/json;charset=utf-8")
    public Result teacherViewPublishedTopics(int pageNum, int pageSize) {
        if (pageNum >= 1 && pageSize >= 1) {
            //更新所有选题状态
            publicFunctionService.updateAllPaperTopicState();
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //分页查询出更新状态后的所有选题记录
            PageDo<PaperTopic> page = teacherService.selectedTopicsHaveBeenPublishedTwo(user.getUser_id(), pageNum, pageSize);
            //将该教师所有已发布的选题返回
            return Result.success(page);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 教师查看出对应选题的论文提交情况并分页
     *
     * @param paper_topic_id 选题ID
     * @param pageNum        起始页
     * @param pageSize       每页显示数量
     * @return
     */
    @RequestMapping(value = "/teacherViewPaperSubmissionStatus", produces = "application/json; charset=utf-8")
    public Result teacherViewPaperSubmissionStatus(int paper_topic_id, int pageNum, int pageSize) {

        if (paper_topic_id > 0 && pageNum >= 1 && pageSize >= 1) {
            //分页查询出论文的提交情况
            PageDo<SubmissionOfPapers> pageDo = teacherService.submissionOfPapersTwo(paper_topic_id, pageNum, pageSize);
            return Result.success(pageDo);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 教师查询对应选题的开题或中期报告的提交情况并分页
     *
     * @param paper_topic_id 选题DI
     * @param file_type      文件类型 （开题、中期报告）
     * @param pageNum        起始页
     * @param pageSize       每页显示数量
     * @return
     */
    @RequestMapping(value = "/teacherViewReportSubmissions", produces = "application/json;charset=utf-8")
    public Result teacherViewReportSubmissions(int paper_topic_id, String file_type, int pageNum, int pageSize) {
        if (paper_topic_id > 0 && pageNum >= 1 && pageSize >= 1 && file_type != null && !("").equals(file_type)) {

            if (file_type.equals(Constants.OPENING_QUESTION)) {
                //分页查询出开题报告的提交情况
                PageDo<PresentationTwo> pageDo = teacherService.
                        statusOfSubmissionOfReportsTwo(paper_topic_id, Constants.
                                OPENING_QUESTION, pageNum, pageSize);
                return Result.success(pageDo);
            }
            //分页查询出中期报告的提交情况
            PageDo<PresentationTwo> pageDo = teacherService.statusOfSubmissionOfReportsTwo(paper_topic_id, Constants.MEDIUM_TERM, pageNum, pageSize);
            return Result.success(pageDo);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 教师对学生提交的论文进行评分
     *
     * @param fraction  分数
     * @param papers_id 论文文件ID
     * @return
     */
    @RequestMapping(value = "/teacherTeacherPaperRating", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Result teacherTeacherPaperRating(int fraction, int papers_id) {
        if (fraction > 0 && papers_id >= 0) {
            //先查询该论文是否已进行评分
            PapersRating papersRating = teacherService.toRepeatScore(papers_id);
            if (papersRating == null) {
                //若未评分过，则添加评分记录
                int row = teacherService.paperScoring(fraction, papers_id);
                if (row > 0) {
                    return Result.success();
                }
                return Result.failure(ResultCodeEnum.Database_Operation_Exception);
            }
            //若已评分过则更新评分记录
            int row = teacherService.updateScore(fraction, papers_id);
            if (row > 0) {
                return Result.failure(ResultCodeEnum.SUCCESSFULLY_UPDATED);
            }
            return Result.failure(ResultCodeEnum.Database_Operation_Exception);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 教师查看自己组内的所有学生信息
     *
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    @RequestMapping(value = "/teacherViewStudentInformation", produces = "application/json;charset=utf-8")
    public Result teacherViewStudentInformation(int pageNum, int pageSize) {
        if (pageNum >= 1 && pageSize >= 1) {
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //分页查询出当前教师组内所有学生的信息
            PageDo<StudentInformation> pageDo = teacherService.
                    viewingStudentInformationInGroupTwo(user.getUser_id(), pageNum, pageSize);
            return Result.success(pageDo);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 教师打回指定学生开题或中期报告
     *
     * @param student_id 学生ID
     * @param file_type  报告类型
     * @return
     */
    @RequestMapping(value = "/deletePresentation", produces = "application/json;charset=utf-8")
    public Result deletePresentation(int student_id, String file_type) {
        int row = teacherService.deletePresentation(student_id, file_type);
        if (row == Constants.SUCCESS) {
            return Result.success();
        }
        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
    }

    /**
     * 教师打回指定学生的论文
     *
     * @param student_id 学生ID
     * @param papers_id  论文ID
     * @return
     */
    @RequestMapping(value = "/deletePapers", produces = "application/json;charset=utf-8")
    public Result deletePapers(int student_id, int papers_id) {
        int row = teacherService.deletePapers(student_id, papers_id);
        if (row != Constants.SCORE_ALREADY) {
            if (row == Constants.SUCCESS) {
                return Result.success();
            }
            return Result.failure(ResultCodeEnum.Database_Operation_Exception);
        }
        return Result.failure(ResultCodeEnum.SCORE_ALREADY);
    }
}
