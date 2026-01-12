package com.xzit.utils;

import cn.hutool.core.util.StrUtil;

import java.util.UUID;

/**
 * @author 31507
 */
public class FileUtils {
    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return 包含点号的文件扩展名（如：.txt、.jpg等）
     */
    public static String getFileExtension(String fileName){
        return "."+StrUtil.subAfter(fileName, ".", true);
    }

    /**
     * 生成随机文件名
     * @return 不包含连字符的UUID字符串作为文件名
     */
    public static String getFileName(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 根据原始文件名生成新的文件路径（包含新文件名和原扩展名）
     * @param fileName 原始文件名
     * @return 新的文件路径（随机文件名+原始扩展名）
     */
    public static String getFilePath(String fileName){
        return getFileName()+getFileExtension(fileName);
    }


}
