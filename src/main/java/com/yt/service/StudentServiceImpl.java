package com.yt.service;

import com.yt.dao.StudentMapper;
import com.yt.pojo.*;
import com.yt.utils.PageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yt
 * @date 2019/10/13 - 16:04
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentSelectedTopic selectHaveChosenPaperTopic(int student_id) {
        return studentMapper.selectHaveChosenPaperTopic(student_id);
    }

    @Override
    public PaperTopic selectHaveChosenPaperTopicInformation(int paper_topic_id) {
        return studentMapper.selectHaveChosenPaperTopicInformation(paper_topic_id);
    }

    @Override
    public Group selectTeacherID(int student_id) {
        return studentMapper.selectTeacherID(student_id);
    }

    @Override
    public List<PaperTopic> selectPaperTopic(int teacher_id, int state) {
        return studentMapper.selectPaperTopic(teacher_id, state);
    }

    @Override
    public PageDo<PaperTopic> selectPaperTopicPaging(int teacher_id, int state, int pageNum, int pageSize) {
        //查询出该教师发布的所有选题
        List<PaperTopic> list = selectPaperTopic(teacher_id, state);
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
    public int studentChoosingTopics(int student_id, int paper_topic_id) {
        return studentMapper.studentChoosingTopics(student_id, paper_topic_id);
    }

    @Override
    public int paperUpload(String thesis_title, String storage_address, String upload_date, int student_id, String student_name, int paper_topic_id) {
        return studentMapper.paperUpload(thesis_title, storage_address, upload_date, student_id, student_name, paper_topic_id);
    }

    @Override
    public Papers papersToRepeat(int student_id) {
        return studentMapper.papersToRepeat(student_id);
    }

    @Override
    public int permissionUpload(String file_name, String upload_date, String file_type, int student_id, int paper_topic_id) {
        return studentMapper.permissionUpload(file_name, upload_date, file_type, student_id, paper_topic_id);
    }

    @Override
    public Presentation permissionToRepeat(int student_id, String file_type) {
        return studentMapper.permissionToRepeat(student_id, file_type);
    }

    @Override
    public PapersRating achievementQuery(int papers_id) {
        return studentMapper.achievementQuery(papers_id);
    }
}
