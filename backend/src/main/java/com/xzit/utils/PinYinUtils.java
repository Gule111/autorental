package com.xzit.utils;

import cn.hutool.extra.pinyin.PinyinUtil;

/**
 * @author 31507
 */
public class PinYinUtils {

    public static String getPinYin(String str) {
        return PinyinUtil.getFirstLetter(str,"").toUpperCase();
    }

    public static void main(String[] args) {
        String z = getPinYin("张三");
        System.out.println("张三 = " + z);
    }
}
