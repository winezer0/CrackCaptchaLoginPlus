package com.fuping.BrowserUtils;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Cookie;
import com.teamdev.jxbrowser.chromium.CustomProxyConfig;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.internal.InputElement;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static com.fuping.LoadConfig.MyConst.BrowserProxySetting;
import static com.fuping.LoadConfig.MyConst.ClearCookiesSetting;
import static com.fuping.PrintLog.PrintLog.print_info;

public class BrowserUitls {

    public static CustomProxyConfig getBrowserProxy(){
        //转换输入的代理格式
        BrowserProxySetting = BrowserProxySetting.replace("://","=");
        print_info(String.format("Proxy Will Setting [%s]", BrowserProxySetting));
        return new CustomProxyConfig(BrowserProxySetting);
    }

    public static void AutoClearAllCookies(Browser browser) {
        //清除cookie
        //参考 JxBrowser之五：清除cache和cookie以及SSL证书处理 https://www.yii666.com/article/677652.html
        if (ClearCookiesSetting){
            browser.getCookieStorage().deleteAll();
            List<Cookie> cookies = browser.getCookieStorage().getAllCookies();
            //print_info(String.format("Auto Clear Browser All Cookies ... %s", cookies.toString()));
        }
    }

    public static void OpenUrlWithLocalBrowser(String url) {
        try {
            URI uri = URI.create(url);
            Desktop dp = Desktop.getDesktop();
            if (dp.isSupported(Desktop.Action.BROWSE))
                dp.browse(uri);
        } catch (NullPointerException localNullPointerException) {
        } catch (Exception e) {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static InputElement findElementByOption(DOMDocument doc, String elementValue, String selectOption ) {
        //输入用户名元素 //需要添加输入XPath|css元素
        InputElement inputElement;
        switch (selectOption) {
            case "id":
                inputElement = (InputElement) doc.findElement(By.id(elementValue));
                break;
            case "name":
                inputElement = (InputElement) doc.findElement(By.name(elementValue));
                break;
            case "class":
                inputElement = (InputElement) doc.findElement(By.className(elementValue));
                break;
            case "css":
                inputElement = (InputElement) doc.findElement(By.cssSelector(elementValue));
                break;
            case "xpath":
            default:
                inputElement = (InputElement) doc.findElement(By.xpath(elementValue));
        }
        return inputElement;
    }
}
