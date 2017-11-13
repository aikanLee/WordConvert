package com.changhongit.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * Created by lizhen on 2017/09/13 10:07
 */
public class ReportWord
{
    private Configuration configuration = null;
    private Map<String, Object> dataMap = null;
    private static String templatePaht = "/templet";

    /**
     * 初始化参数
     */
    public ReportWord(Map<String, Object> dataMap)
    {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        this.dataMap = dataMap;
    }


    /**
     * @param filePath
     * @param template
     * @throws IOException
     * @throws TemplateException
     */
    public InputStream createDoc(String filePath, String template) throws IOException, TemplateException
    {
        Template t = null;
        Writer out = null;
        File outFile = null;
        InputStream inputStream1 = null;
        InputStream inputStream2 = null;

        // 配置模板所在的包名
        configuration.setClassForTemplateLoading(ReportWord.class, templatePaht);
        // 加载模板
        t = configuration.getTemplate(template);
        t.setEncoding("utf-8");
        outFile = new File(filePath);
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
        t.process(dataMap, out);
        out.close();
        inputStream1 = new FileInputStream(outFile);
        //复制输入流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream1.read(buffer)) > -1)
        {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        // 打开一个新的输入流
        inputStream2 = new ByteArrayInputStream(baos.toByteArray());
        inputStream1.close();
        //删除临时文件
        deleteDoc(outFile);
        return inputStream2;

    }

    public static boolean deleteDoc(File file)
    {
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile())
        {
            if (file.delete())
            {

                return true;
            } else
            {

                return false;
            }
        } else
        {
            return false;
        }
    }
}
