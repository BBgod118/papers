package com.yt.service;

import com.yt.dao.PublicFunctionMapper;
import com.yt.pojo.Dataset;
import com.yt.pojo.DatasetTwo;
import com.yt.pojo.PaperTopic;
import com.yt.utils.Constants;
import com.yt.utils.PageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yt
 * @date 2019/10/13 - 16:27
 */
@Service("publicFunctionService")
public class PublicFunctionServiceImpl implements PublicFunctionService {
    @Autowired
    private PublicFunctionMapper publicFunctionMapper;

    @Override
    public int changeTheStatusOfTheTopic(int state, int paper_topic_id) {
        return publicFunctionMapper.changeTheStatusOfTheTopic(state, paper_topic_id);
    }

    @Override
    public List<PaperTopic> selectAllPaperTopic() {
        return publicFunctionMapper.selectAllPaperTopic();
    }

    @Override
    public int getUserRoleIds(Integer user_id) {
        return publicFunctionMapper.getUserRoleIds(user_id);
    }

    @Override
    public int updateAllPaperTopicState() {
        try {
            //先查询出所有的选题
            List<PaperTopic> list = selectAllPaperTopic();
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //遍历所有记录，对已过期的选题进行状态更改
            for (int i = 0; i < list.size(); i++) {
                //获取当前时间release_date
                String release_date = df.format(new Date());
                Date startTime = df.parse(release_date);
                //查询出截至日期
                String endTimeStr = df.format(df.parse(list.get(i).getClosing_date()));
                Date endTime = df.parse(endTimeStr);
                //将当前日期与截至日期进行比较
                if (startTime.getTime() <= endTime.getTime()) {
                    //若在有效期内则不对状态进行操作
                } else {
                    //若不再有效期内则对状态进行更改
                    changeTheStatusOfTheTopic(Constants.INVALID, list.get(i).getPaper_topic_id());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.PAPER_TOPIC_SUCCESSFUL_STATUS_UPDATE;
    }

    @Override
    public int dataUpload(String data_name, String storage_address, String upload_date, int upload_user_id, String upload_role_name) {
        return publicFunctionMapper.dataUpload(data_name, storage_address, upload_date, upload_user_id, upload_role_name);
    }

    @Override
    public Dataset dataToRepeat(String data_name, int upload_user_id) {
        return publicFunctionMapper.dataToRepeat(data_name, upload_user_id);
    }

    @Override
    public int updateData(String data_name, String upload_date, int upload_user_id) {
        return publicFunctionMapper.updateData(data_name, upload_date, upload_user_id);
    }

    @Override
    public List<DatasetTwo> teachersDownload(int upload_user_id) {
        return publicFunctionMapper.teachersDownload(upload_user_id);
    }

    @Override
    public PageDo<DatasetTwo> teachersDownloadTwo(int upload_user_id, int pageNum, int pageSize) {
        //先查询出教师上传的所有资料
        List<DatasetTwo> tList = teachersDownload(upload_user_id);
        //再查询出所有管理员上传的资料
        List<DatasetTwo> list = publicFunctionMapper.teachersDownloadTwo();
        list.addAll(tList);
        //进行分页
        PageDo<DatasetTwo> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<DatasetTwo> data = new ArrayList<>();
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
    public PageDo<DatasetTwo> adminDownload(int pageNum, int pageSize) {
        //询出所有资料
        List<DatasetTwo> list = publicFunctionMapper.adminDownload();
        //进行分页
        PageDo<DatasetTwo> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<DatasetTwo> data = new ArrayList<>();
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
    public PageDo<DatasetTwo> informationQuery(String data_name, int upload_user_id, int pageNum, int pageSize) {
        //先模糊查询出管理员上传的所有资料中符合查询条件的
        List<DatasetTwo> tList = adminInformationQuery(data_name);
        //再查询当前教师或当前学生导师上传所有资料中符合条件的
        List<DatasetTwo> list = publicFunctionMapper.informationQuery(data_name, upload_user_id);
        list.addAll(tList);
        //进行分页
        PageDo<DatasetTwo> page = new PageDo<>(pageNum, pageSize, list.size());
        int startIndex = page.getStartIndex();
        List<DatasetTwo> data = new ArrayList<>();
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
    public List<DatasetTwo> adminInformationQuery(String data_name) {
        return publicFunctionMapper.adminInformationQuery(data_name);
    }
}
