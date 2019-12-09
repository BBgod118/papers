package com.yt.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * pdf预览
 *
 * @author yt
 * @date 2019/6/26 - 23:58
 */
public class PdfUtil {
    public static void showPdf(HttpServletResponse response, HttpServletRequest request,
                               String fileNam) throws IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println(fileNam);
        //pdf文件路径
        String path = request.getSession().getServletContext().getRealPath(Constants.
                PAPER_STORAGE_DIRECTORY);
        String downLoadPath = path + fileNam;
        response.setContentType("application/pdf");
        FileInputStream in = new FileInputStream(new File(downLoadPath));
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[512];
        while ((in.read(b)) != -1) {
            out.write(b);
        }
        out.flush();
        in.close();
        out.close();
    }
}
