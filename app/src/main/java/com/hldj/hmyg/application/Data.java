package com.hldj.hmyg.application;

import android.graphics.Bitmap;

import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.Purchase;
import com.hy.utils.GetServerUrl;
import com.photo.choosephotos.photo.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 14-1-3.
 */

public class Data {

    public static final int STATUS_GRAY = 0xFF999999;//


    public static final int STATUS_ORANGE = 0xFFFF6601;//

    public static final int STATUS_BLUE = 0xFF179BED;
    public static final int STATUS_GREEN = 0xFF4CAF50;
    public static final int STATUS_STROGE_GREEN = 0xFF00843C;
    public static final int STATUS_RED = 0xFFFF0000;

    public static int screenWidth = 0;
    public static ArrayList<HashMap<String, Object>> TYPES = new ArrayList<HashMap<String, Object>>();
    public static long loading_time = 200l;
    public static long refresh_time = 10l;
    public static String ZIYING = "ziying";
    public static String FUWU = "fuwu";
    public static String ZIJINDANBAO = "danbao";
    public static String HEZUOSHANGJIA = "hezuo";
    // 图标
    public static ArrayList<String> collect_ids = new ArrayList<String>();
    // 图标
//    public static ArrayList<paramsData> paramsDatas = new ArrayList<paramsData>();
    // 要上传的图片
    public static ArrayList<String> pics = new ArrayList<String>();

    public static ArrayList<Purchase> purchases = new ArrayList<Purchase>();
    // 要上传的图片
    // 用来显示预览图
    public static ArrayList<Bitmap> microBmList = new ArrayList<Bitmap>();
    // 所选图的信息(主要是路径)
    public static ArrayList<Item> photoList = new ArrayList<Item>();

    public static ArrayList<Pic> pics1 = new ArrayList<Pic>();

    public static int reqWidth = 720;
    public static int reqHeight = 1080;
    // 热门搜索
    public static final String GetHtml = "http://api1.cn2che.com/Banner/Banner.asmx/GetHtml";
    public static final String About01 = "http://chechengtong.cn2che.com";
    public static final String About02 = "http://jiameng.cn2che.com";
    public static final String About03 = "http://www.cn2che.com/pinggushi.html";
    public static final String About04 = "http://chechengtong.cn2che.com";
    public static final String Content_Type = "application/x-www-form-urlencoded;";
    public static final String proxyAssureDesc = GetServerUrl.getUrl() + "h5/page/labelicondesc.html";


    //服务协议  tck  协议
    public static final String LOGIN_TCP = GetServerUrl.getHtmlUrl() + "page/help/serviceagreement.html?isApp=true";

    //关于我们  http://192.168.1.252:8090/page/help/aboutus.html?isApp=true
    public static final String About = GetServerUrl.getHtmlUrl() + "page/help/aboutus.html?isApp=true";

    //分享页面  分享下载链接
    public static final String appDoloadUrl = GetServerUrl.getHtmlUrl() + "page/appdown.html";

    //店铺   详情
    public static final String Store_Page = GetServerUrl.getHtmlUrl() + "page/store/";
    //http://test.api.hmeg.cn/page/store/534c6ef74f1045779f19e17313295017.html

    public static String getStorePageById(String id) {
        return Store_Page + id + ".html";
    }

    //* 用户同意采购协议  http://192.168.1.252:8090/page/protocol.html
    public static final String Protocol_Url = GetServerUrl.getHtmlUrl()+ "page/protocol.html";
//    public static final String Protocol_Url = "http://m.hmeg.cn/" + "page/protocol.html";

    //成交公告
    public static final String noticesUrls = GetServerUrl.getHtmlUrl() + "noticeArticle?isApp=true";

    //成交公告或者新闻详情 单条  http://192.168.1.252:8090/article/detail/a6bd580083d640a3b8e47df9323f408d.html?isApp=true
    public static String getNotices_and_news_url_only_by_id(String id) {
        return Notices_and_news_url_only + id + ".html?isApp=true";
    }

    /**
     * http://192.168.1.252:8090/article/detail/a6bd580083d640a3b8e47df9323f408d.html?isApp=true
     * http://192.168.1.252:8090/article?isHeader=truearticle/detail/10caf1ddc10a4296b0c3c1d23bc63566.html?isApp=true
     */
    private static final String Notices_and_news_url_only = GetServerUrl.getHtmlUrl() + "article/detail/";

    // 苗木分享 http://192.168.1.252:8090/742147bb8a1f4ad98430c04c88b56f0c
    //苗木分享
    private static final String shareUrl = "seedling/detail/";

    public static String getSharePlantUrl(String plantID) {
        return GetServerUrl.getHtmlUrl() + shareUrl + plantID + ".html";
    }

    // 新闻资讯  http://192.168.1.252:8090/article?isApp=true
    public static final String news_urls = GetServerUrl.getHtmlUrl() + "article?isHeader=true";

    //帮助中心
    public static final String helpIndex = GetServerUrl.getHtmlUrl() + "page/help/index.html?isApp=true";


// 新闻资讯  /article?isHeader=true.html
//    http://192.168.1.252:8090/article?isApp=true


//    SString *shareUrl=
//    @"http://m.hmeg.cn/seedling/detail/#{self.plantManageModel.plantManageId}.html";
//    NSString *title=self.plantManageModel.name;


    public static final String weituogou = GetServerUrl.getUrl() + "h5/page/weituogou.html";

    public static final String share = GetServerUrl.getUrl() + "h5/page/share.html";

}
