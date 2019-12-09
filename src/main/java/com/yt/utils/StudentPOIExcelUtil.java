package com.yt.utils;

import com.yt.pojo.StudentInformation;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 管理员进行excel表批量添加学生账户工具类
 *
 * @author yt
 * @date 2019/7/26 - 0:14
 */
public class StudentPOIExcelUtil {
    private static Logger logger = Logger.getLogger(StudentPOIExcelUtil.class);
    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";

    public static List<StudentInformation> readExcel(File file) throws IOException {
        //账户名（学号）
        String user_name = "";
        //真实姓名
        String real_name = "";
        //用户电话号码
        String user_phone = "";
        //年级
        String grade = "";
        //系别
        String department = "";
        //班级
        String classes = "";
        // 检查文件
        checkFile(file);
        Workbook workBook = getWorkBook(file);
        // 返回对象,每行作为一个数组，放在集合返回
        List<StudentInformation> rowList = new ArrayList<>();
        if (null != workBook) {
            for (int sheetNum = 0; sheetNum < workBook.getNumberOfSheets(); sheetNum++) {
                // 获得当前sheet工作表
                Sheet sheet = workBook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                // 获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                // 获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                // 循环所有行(第一行为标题)
                for (int rowNum = firstRowNum + 2; rowNum <= lastRowNum; rowNum++) {
                    // 获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    // 获得当前行开始的列
                    short firstCellNum = row.getFirstCellNum();
                    // 获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    //String[] cells = new String[row.getPhysicalNumberOfCells()];
                    // 循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        //excel表的第一列为学生账号
                        if (cellNum == 0) {
                            Cell cell = row.getCell(cellNum);
                            user_name = getCellValue(cell);
                            //excel表的第二列为学生姓名
                        } else if (cellNum == 1) {
                            Cell cell = row.getCell(cellNum);
                            real_name = getCellValue(cell);
                            //excel表的第三列为学生电话
                        } else if (cellNum == 2) {
                            Cell cell = row.getCell(cellNum);
                            user_phone = getCellValue(cell);
                            //excel表的第四列为学生年级
                        } else if (cellNum == 3) {
                            Cell cell = row.getCell(cellNum);
                            grade = getCellValue(cell);
                            //excel表的第五列为学生系别
                        } else if (cellNum == 4) {
                            Cell cell = row.getCell(cellNum);
                            department = getCellValue(cell);
                            //excel表的第六列为学生班级
                        } else {
                            Cell cell = row.getCell(cellNum);
                            classes = getCellValue(cell);
                        }

                    }
                    rowList.add(new StudentInformation(user_name, real_name, user_phone,
                            grade, department, classes));
                }
            }
        }
        return rowList;
    }

    /**
     * 取单元格的值
     */
    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cellValue == null) {
            return cellValue;
        }
        // 把数字当成String来读，防止1读成1.0
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            // 数字
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            // 字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            // 布尔
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            // 公式
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            // 空
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            // 错误
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 获得工作簿对象
     */
    private static Workbook getWorkBook(File file) {
        String filename = file.getName();
        Workbook workbook = null;
        try {
            InputStream is = new FileInputStream(file);
            if (filename.endsWith(XLS)) {
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (filename.endsWith(XLSX)) {
                // 2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 检查文件
     */
    private static void checkFile(File file) throws IOException {
        if (null == file) {
            logger.error("文件不存在");
            throw new FileNotFoundException("文件不存在！");
        }
        // 获取文件名
        String filename = file.getName();
        // 判断是否为excel文件
        if (!filename.endsWith(XLS) && !filename.endsWith(XLSX)) {
            logger.error(filename + "不是excel文件");
            throw new IOException(filename + "不是excel文件");
        }
    }
}
