package com.yt.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * world转pdf
 *
 * @author yt
 * @date 2019/6/26 - 23:58
 */
public class WTP {
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = Test.class.getClassLoader().
                    getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void docToPdf(@RequestParam("inPath") String inPath,
                                @RequestParam("outPath") String outPath) {

        if (!getLicense()) {
            // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        System.out.println("路径" + inPath + " " + outPath);
        File wordFile = new File(inPath);
        if (!wordFile.exists()) {
            System.out.println("源文件不存在:{}" + inPath);
            return;
        }
        try {
            System.out.println("PDF转换开始");
            //开始时间
            long old = System.currentTimeMillis();
            //获取文件
            File file = new File(outPath);
            //获取文件流
            FileOutputStream os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF
            doc.save(os, SaveFormat.PDF);
            //结束时间
            long now = System.currentTimeMillis();
            System.out.println("PDF转换结束 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

