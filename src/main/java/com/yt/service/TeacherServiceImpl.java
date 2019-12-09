package com.yt.service;

import com.yt.dao.StudentMapper;
import com.yt.dao.TeacherMapper;
import com.yt.pojo.*;
import com.yt.utils.Constants;
import com.yt.utils.PageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yt
 * @date 2019/10/11 - 21:23
 */
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int thesisSelection(String paper_topic, String release_date, String closing_date, int teacher_id) {
        return teacherMapper.thesisSelection(paper_topic, release_date, closing_date, teacher_id);
    }

    @Override
    public List<PaperTopic> selectedTopicsHaveBeenPublished(int teacher_id) {
        return teacherMapper.selectedTopicsHaveBeenPublished(teacher_id);
    }

    @Override
    public PageDo<PaperTopic> selectedTopicsHaveBeenPublishedTwo(int teacher_id, int pageNum, int pageSize) {
        //查询出该教师发布的所有选题
        List<PaperTopic> list = selectedTopicsHaveBeenPublished(teacher_id);
        //进行分页
        PageDo<PaperTopic> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<PaperTopic> data = new ArrayList<>();
        if (list.size() > pageSize) {
            for (int i = 0; i < pageSize && startIndex + i < page.getTotalRecord(); i++) {
                data.add(list.get(startIndex + i));
            }
            page.setDatas(data);
        } else {
            page.setDatas(list);
        }
        return page;
    }

    @Override
    public List<SubmissionOfPapers> submissionOfPapers(int paper_topic_id) {
        return teacherMapper.submissionOfPapers(paper_topic_id);
    }

    @Override
    public PageDo<SubmissionOfPapers> submissionOfPapersTwo(int paper_topic_id, int pageNum, int pageSize) {
        List<SubmissionOfPapers> list = submissionOfPapers(paper_topic_id);
        //进行分页
        PageDo<SubmissionOfPapers> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<SubmissionOfPapers> data = new ArrayList<>();
        if (list.size() > pageSize) {
            for (int i = 0; i < pageSize && startIndex + i < page.getTotalRecord(); i++) {
                data.add(list.get(startIndex + i));
            }
            page.setDatas(data);
        } else {
            page.setDatas(list);
        }
        return page;
    }

    @Override
    public List<PresentationTwo> statusOfSubmissionOfReports(int paper_topic_id, String file_type) {
        return teacherMapper.statusOfSubmissionOfReports(paper_topic_id, file_type);
    }

    @Override
    public PageDo<PresentationTwo> statusOfSubmissionOfReportsTwo(int paper_topic_id, String file_type, int pageNum, int pageSize) {
        List<PresentationTwo> list = statusOfSubmissionOfReports(paper_topic_id, file_type);
        //进行分页
        PageDo<PresentationTwo> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<PresentationTwo> data = new ArrayList<>();
        if (list.size() > pageSize) {
            for (int i = 0; i < pageSize && startIndex + i < page.getTotalRecord(); i++) {
                data.add(list.get(startIndex + i));
            }
            page.setDatas(data);
        } else {
            page.setDatas(list);
        }
        return page;
    }

    @Override
    public int changeTheStatusOfTheTopic(int state, int paper_topic_id) {
        return teacherMapper.changeTheStatusOfTheTopic(state, paper_topic_id);
    }

    @Override
    public PapersRating toRepeatScore(int papers_id) {
        return teacherMapper.toRepeatScore(papers_id);
    }

    @Override
    public int paperScoring(int fraction, int papers_id) {
        return teacherMapper.paperScoring(fraction, papers_id);
    }

    @Override
    public int updateScore(int newFraction, int papers_id) {
        return teacherMapper.updateScore(newFraction, papers_id);
    }

    @Override
    public List<Integer> studentIdSet(int teacher_id) {
        return teacherMapper.studentIdSet(teacher_id);
    }

    @Override
    public StudentInformation viewingStudentInformationInGroup(int student_id) {
        return teacherMapper.viewingStudentInformationInGroup(student_id);
    }

    @Override
    public PageDo<StudentInformation> viewingStudentInformationInGroupTwo(int teacher_id, int pageNum, int pageSize) {
        //定义一个集合来存储所有学生的信息
        List<StudentInformation> informationList = new ArrayList<>();
        //利用当前教师id查询出他所有学生的id
        List<Integer> list = studentIdSet(teacher_id);
        for (int i = 0; i < list.size(); i++) {
            //循环遍历查询出组内每个学生的学习
            informationList.add(viewingStudentInformationInGroup(list.get(i)));
        }
        //进行分页
        PageDo<StudentInformation> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<StudentInformation> data = new ArrayList<>();
        if (list.size() > pageSize) {
            for (int i = 0; i < pageSize && startIndex + i < page.getTotalRecord(); i++) {
                data.add(informationList.get(startIndex + i));
            }
            page.setDatas(data);
        } else {
            page.setDatas(informationList);
        }
        return page;
    }

    @Override
    public int deletePresentation(int student_id, String file_type) {
        int row = teacherMapper.deletePresentation(student_id, file_type);
        if (row > 0){
            //插入打回记录
            int ro = insertCallBackTheRecord(student_id, file_type);
            if (ro > 0){
                return Constants.SUCCESS;
            }
            return Constants.ATABASEOPERATION_EXCEPTION;
        }
        return Constants.ATABASEOPERATION_EXCEPTION;
    }

    @Override
    public int deletePapers(int student_id, int papers_id) {
        //查询当前论文是否已评分
        PapersRating papersRating = studentMapper.achievementQuery(papers_id);
        if (papersRating == null){
            int row =  teacherMapper.deletePapers(student_id);
            if (row > 0){
                //插入打回记录
                int ro = insertCallBackTheRecord(student_id, Constants.PAPERS);
                if (ro > 0){
                    return Constants.SUCCESS;
                }
                return Constants.ATABASEOPERATION_EXCEPTION;
            }
            return Constants.ATABASEOPERATION_EXCEPTION;
        }
        return Constants.SCORE_ALREADY;
    }

    @Override
    public int insertCallBackTheRecord(int student_id, String file_type) {
        return teacherMapper.insertCallBackTheRecord(student_id, file_type);
    }

    @Override
    public List<String> selectCallBackTheRecord(int student_id) {
        return teacherMapper.selectCallBackTheRecord(student_id);
    }
}
