package com.example.lx.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ExcelUtil {


//向excel中追加数据.@excelPath:excel所在路径,list:数据集合.(第2,3,4,5,6.....次写入

    public static void addExcel(String excelPath, List<? extends Object> list) {
        try {


            FileInputStream fs = new FileInputStream(excelPath);//获取excel


            POIFSFileSystem ps = new POIFSFileSystem(fs);//获取excel信息


            HSSFWorkbook wb = new HSSFWorkbook(ps);


            HSSFSheet sheet = wb.getSheetAt(0);//获取工作表


            HSSFRow row = sheet.getRow(0);//获取第一行(即:字段列头,便于赋值)


            System.out.println(sheet.getLastRowNum() + "空" + row.getLastCellNum());//分别得到最后一行行号,和一条记录的最后一个单元格


            FileOutputStream out = new FileOutputStream(excelPath);//向excel中添加数据


            Object o = list.get(0);
            Class<? extends Object> clazz = o.getClass();
            String className = clazz.getSimpleName();
            Field[] fields = clazz.getDeclaredFields();    //这里通过反射获取字段数组


            for (Object obj : list) {
                int i = 0;  //列
                row = sheet.createRow(sheet.getLastRowNum() + 1);//在现有行号后追加数据
                for (Field f : fields) {
                    Object temp = getFieldValueByName(f.getName(), obj);
                    String strTemp = "";
                    if (temp != null) {
                        strTemp = temp.toString();
                    }
                    row.createCell(i).setCellValue(strTemp);//设置单元格的数据
                    i++;
                }
            }

            out.flush();

            wb.write(out);
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取属性值
     *
     * @param fieldName 字段名称
     * @param o         对象
     * @return Object
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);    //获取方法名
            Method method = o.getClass().getMethod(getter, new Class[]{});  //获取方法对象
            Object value = method.invoke(o, new Object[]{});    //用invoke调用此对象的get字段方法
            return value;  //返回值
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
