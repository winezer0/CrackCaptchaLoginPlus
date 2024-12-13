package com.fuping.CommonUtils;

import com.fuping.LoadDict.UserPassPair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.hutool.core.util.StrUtil.isEmptyIfStr;
import static com.fuping.LoadDict.LoadDictUtils.excludeHistoryPairs;
import static com.fuping.LoadDict.LoadDictUtils.replaceUserMarkInPass;
import static com.fuping.PrintLog.PrintLog.print_debug;

public class Utils {

    public static String regexExtract(String string, String regex) {
        //忽略匹配空值
        if(isEmptyIfStr(string)) return null;
        if(isEmptyIfStr(regex)) return string;

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        // 查找匹配项
        if (matcher.find()) {
            // 返回匹配到的结果
            return matcher.group(0);
        } else {
            return null; // 没有匹配项
        }
    }

    public static boolean isNumber(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static String urlRemoveQuery(String url) {
        // 获取 URL 的无参数部分
        int queryIndex = url.indexOf("?");
        return (queryIndex != -1) ? url.substring(0, queryIndex) : url;
    }

    public static String escapeString(Object Obj){
        if(null==Obj) return "null";
        String string = String.valueOf(Obj);
        return string.replace("\"","\\\"");
    }

    /**
     * 分割字符串并过滤掉空字符串。
     *
     * @param input 输入字符串
     * @param delimiter 分隔符
     * @return 过滤后的 URL 列表
     */
    public static List<String> splitAndFilter(String input, String delimiter) {
        if (input == null || input.isEmpty()) {
            return new ArrayList<>(); // 返回空列表
        }

        List<String> result = new ArrayList<>();
        String[] parts = input.split(delimiter);

        for (String part : parts) {
            String trimmedPart = part.trim();
            if (!trimmedPart.isEmpty()) {
                result.add(trimmedPart);
            }
        }

        return result;
    }

    /**
     * 从给定的 URL 中提取域名，并构造新的域名字符串。
     * @param originalUrl 原始 URL 字符串。
     * @param withHttpPrefix 是否在新域名前加上 "http://"。
     * @return 构造的新域名字符串。
     */
    public static String extractDomain(String originalUrl, boolean withHttpPrefix) {
        try {
            URL url = new URL(originalUrl);
            String protocol = url.getProtocol();
            String host = url.getHost();
            String portPart = url.getPort() > -1 ? ":" + url.getPort() : "";
            String domain = host + portPart;

            //需要考虑 80 443 端口，这两个情况应该不需要配置
            if (url.getPort() == 80 || url.getPort() == 443){
                domain = host;
            }

            // 构造新的域名
            if (withHttpPrefix) {
                return protocol + "://" + domain;
            } else {
                return domain;
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format", e);
        }
    }
}
