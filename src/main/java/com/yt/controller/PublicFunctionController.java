package com.yt.controller;

import com.yt.pojo.Dataset;
import com.yt.pojo.DatasetTwo;
import com.yt.pojo.Group;
import com.yt.pojo.User;
import com.yt.service.PublicFunctionService;
import com.yt.service.StudentService;
import com.yt.utils.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共功能控制器类
 *
 * @author yt
 * @date 2019/10/15 - 16:17
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class PublicFunctionController {
    @Autowired
    private PublicFunctionService publicFunctionService;
    @Autowired
    private StudentService studentService;

    /**
     * 教师或管理员上传资料
     *
     * @param uploadfile
     * @param request
     * @return
     */
    @RequestMapping(value = "/adminOrTeacherDataUpload",
            method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result adminOrTeacherDataUpload(
            @RequestParam("uploadfile") List<MultipartFile> uploadfile,
            HttpServletRequest request) {
        //判断上传文件是否存在
        if (!uploadfile.isEmpty() && uploadfile.size() > 0) {
            String upload_role_name = "";
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //查询当前用户的角色
            int userRole = publicFunctionService.getUserRoleIds(user.getUser_id());
            if (userRole == Constants.TEACHER_ROLE) {
                upload_role_name = "teacher";
            }
            if (userRole == Constants.ADMIN_ROLE) {
                upload_role_name = "admin";
            }
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //release_date为当前系统时间作为上传时间
            String release_date = df.format(new Date());
            //循环输出上传的文件
            for (MultipartFile file : uploadfile) {
                //获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                //设置上传文件的保存地址目录
                String dirPath = request.getServletContext().getRealPath(Constants.
                        PAPER_STORAGE_DIRECTORY);
                File filePath = new File(dirPath);
                //如果保存文件的地址不存在，就先创建目录
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                try {
                    //使用用户名加文件名的方式存储文件
                    String newFileName = user.getUser_id() + originalFilename;
                    //使用MultipartFile接口的方法完成文件上传到指定位置
                    file.transferTo(new File(dirPath + newFileName));
                    //判断当前资料名是否上传过
                    Dataset dataset = publicFunctionService.dataToRepeat(originalFilename,
                            user.getUser_id());
                    if (dataset == null) {
                        //若没有上传过，则新增上传记录
                        int row = publicFunctionService.dataUpload(originalFilename,
                                dirPath,
                                release_date, user.getUser_id(), upload_role_name);
                        if (row > 0) {
                            return Result.success();
                        }
                        return Result.failure(ResultCodeEnum.Database_Operation_Exception);
                    }
                    //若有上传记录则更新上传时间
                    int rowTwo = publicFunctionService.updateData(originalFilename,
                            release_date,
                            user.getUser_id());
                    if (rowTwo > 0) {
                        return Result.success();
                    }
                    return Result.failure(ResultCodeEnum.Database_Operation_Exception);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.failure(ResultCodeEnum.FILE_DOES_NOT_EXIST);
    }

    /**
     * 查询出学生能够操作的所有资料并分页
     *
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    @RequestMapping(value = "/studentCanOperateAllInformation", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result studentCanOperateAllInformation(int pageNum, int pageSize) {
        if (pageNum >= 1 && pageSize >= 1) {
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //查询当前学导师的id
            Group group = studentService.selectTeacherID(user.getUser_id());
            if (group != null) {
                //分页查询出学生能操作的所有资料
                PageDo<DatasetTwo> pageDo =
                        publicFunctionService.teachersDownloadTwo(group.getTeacher_id(),
                                pageNum, pageSize);
                System.out.println(pageDo);
                return Result.success(pageDo);
            }
            return Result.failure(ResultCodeEnum.STUDENT_NOT_GROUP);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 查询出教师能操作的所有资料并分页
     *
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    @RequestMapping(value = "/teacherCanOperateAllInformation", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result teacherCanOperateAllInformation(int pageNum, int pageSize) {
        if (pageNum >= 1 && pageSize >= 1) {
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //分页查询出当前教师能操作的所有资料
            PageDo<DatasetTwo> pageDo =
                    publicFunctionService.teachersDownloadTwo(user.getUser_id(),
                            pageNum, pageSize);
            return Result.success(pageDo);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 查询管理员能操作的所有资料
     *
     * @param pageNum  起始页
     * @param pageSize 每页显示数量
     * @return
     */
    @RequestMapping(value = "/adminCanOperateAllInformation", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result adminCanOperateAllInformation(int pageNum, int pageSize) {
        if (pageNum >= 1 && pageSize >= 1) {
            //分页查询出当前管理员能操作的所有资料
            PageDo<DatasetTwo> pageDo = publicFunctionService.
                    adminDownload(pageNum, pageSize);
            return Result.success(pageDo);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 学生模糊查询资料
     *
     * @param data_name 查询关键字
     * @param pageNum   起始页
     * @param pageSize  每页显示数量
     * @return
     */
    @RequestMapping(value = "/studentFuzzyQuery", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result studentFuzzyQuery(String data_name, int pageNum, int pageSize) {
        if (data_name != null && !("").equals(data_name) && pageNum >= 1 && pageSize >= 1) {
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //查询当前学导师的id
            Group group = studentService.selectTeacherID(user.getUser_id());
            //分页模糊查询出当前学生要查询的资料
            PageDo<DatasetTwo> pageDo = publicFunctionService.informationQuery(data_name,
                    group.getTeacher_id(), pageNum, pageSize);
            return Result.success(pageDo);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 教师模糊查询资料
     *
     * @param data_name 查询关键字
     * @param pageNum   起始页
     * @param pageSize  每页显示数量
     * @return
     */
    @RequestMapping(value = "/teacherFuzzyQuery", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public Result teacherFuzzyQuery(String data_name, int pageNum, int pageSize) {
        if (data_name != null && !("").equals(data_name) && pageNum >= 1 && pageSize >= 1) {
            //获取当前用户的信息
            User user = SubjectGetUserInformation.getUserInformation();
            //分页模糊查询出当前教师要查询的资料
            PageDo<DatasetTwo> pageDo = publicFunctionService.informationQuery(data_name,
                    user.getUser_id(), pageNum, pageSize);
            return Result.success(pageDo);
        }
        return Result.failure(ResultCodeEnum.PARAM_IS_BLANK);
    }

    /**
     * 学生、教师或管理员进行资料下载
     *
     * @param request
     * @param data_name      要下载的文件名
     * @param upload_user_id 上传该文件用户ID
     * @throws Exception
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> fileDownload(String data_name, int upload_user_id,
                                               HttpServletRequest request) throws Exception {
        //拼接文件名
        String fileName = upload_user_id + data_name;
        //设置下载目录
        String path = request.getServletContext().getRealPath(Constants.
                PAPER_STORAGE_DIRECTORY);
        //创建文件对象
        File file = new File(path + File.separator + fileName);
        //对文件名编码，防止中文文件乱码
        fileName = this.getFilename(request, fileName);
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        //通知浏览器以下载的方式打开文件
        headers.setContentDispositionFormData("attachment", fileName);
        //定义以流的形式下载返回文件数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //使用Spring MVC框架的ResponseEntity对象封装返回下载数据
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,
                HttpStatus.OK);
    }


    /**
     * 根据不同的浏览器设置不同的编码格式
     *
     * @param request
     * @param fileName
     * @return 返回编码后的文件名
     * @throws Exception
     */
    public String getFilename(HttpServletRequest request, String fileName) throws Exception {
        //IE不同版本User-Agent中出现的关键词
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        //获取头请求代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : IEBrowserKeyWords) {
            if (userAgent.contains(keyWord)) {
                //IE内核浏览器，统一为UTF-8编码显示
                return URLEncoder.encode(fileName, "UTF-8");
            }
        }
        //火狐等浏览器统一为ISO-8859-1编码显示
        return new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
    }

    /**
     * world转pdf并实现在线预览
     *
     * @param response
     * @param request
     * @param fileName 要转换并预览的文件名
     */
    @RequestMapping("/docToPdf")
    public void docToPdf(HttpServletResponse response, HttpServletRequest request,
                         int upload_user_id, String fileName) {
        String worldPath = request.getServletContext().getRealPath(Constants.
                PAPER_STORAGE_DIRECTORY);
        String fileNameTwo = upload_user_id + fileName;
        //要转换的world路径加文件名
        String inPath = worldPath + fileNameTwo;
        //截取后缀名之前的文件名
        String newFileName = fileNameTwo.substring(0, fileName.lastIndexOf("."));
        //将文件名拼接为pdf格式
        newFileName = newFileName + ".pdf";
        //将转换好的pdf存放的路径加文件名
        String outPath = worldPath + newFileName;
        try {
            System.out.println("执行了");
            //调用转换pdf
            WTP.docToPdf(inPath, outPath);
            //预览
            PdfUtil.showPdf(response, request, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 论文查重
     *
     * @param data_name      要下载的文件名
     * @param upload_user_id 上传该文件用户ID
     */
    @RequestMapping("/paperChecking")
    public Result paperChecking(String data_name, int upload_user_id) throws Exception {
        //拼接文件名
        String fileName = upload_user_id + data_name;
        System.out.println(fileName);
        //设置论文路径
        String path = Constants.PAPER_STORAGE_DIRECTORY_TWO+fileName;
        Map<String, Double> outMap = new HashMap<>();
        //查重
        Map<String,Double> map = PaperChecking.search(path);
        System.out.println(map);
        for (Map.Entry<String, Double> m : map.entrySet()) {
            if (!m.getKey().equals(fileName)){
                if (m.getValue() > 0.4){
                    outMap.put(m.getKey(), m.getValue());
                }
            }
        }
        return Result.success(outMap);
    }

}
