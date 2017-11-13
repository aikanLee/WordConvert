package com.changhongit.util;

import freemarker.template.TemplateException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhen on 2017/11/09 17:57
 */
public class WordUtil
{
    /**
     * 根据指定模板生成word的 input流
     * @param dataMap
     * @param templateName
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static InputStream getWordByTemplate(Map<String, Object> dataMap, String templateName) throws IOException, TemplateException
    {
         //添加时间后缀，防止发生
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String time = new SimpleDateFormat("yyyyMMddhhmmss").format(calendar.getTime());
        String fileName = "/contract" + time + ".doc";
        String filePath = WordUtil.class.getResource("/").getPath() + fileName;
        //传入参数不能为null否则抛出异常。
        if (dataMap != null && dataMap.size() > 0)
        {
            //按照模板生成对应的文件流
            return new ReportWord(dataMap).createDoc(filePath, templateName);
        } else
        {
            throw new RuntimeException("传入参数为空");
        }
    }
    public static void main(String[] args) throws IOException, TemplateException
    {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("SSSS", "over");
        InputStream inputStream = getWordByTemplate(dataMap, "test.ftl");
    }

}
