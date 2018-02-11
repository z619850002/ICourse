package com.example.dell.coursetable.webutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Kyrie
 * @version 1.0
 * @description 网络操作组件类
 * @date 2018/1/28
 */
public class HttpUtil {


    static public String host="http://192.168.0.101:8081";

    static public List<Cookie> formerCookie=new ArrayList<Cookie>();

    static private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();



    /**
     @author Kyrie
     @description   get请求
     @param          url 链接
     @return         String 返回请求内容
     @throws         IOException
     @throws         InterruptedException
     @throws         ExecutionException
     */
    static public String sendGetRequest(final String url ) throws IOException , InterruptedException , ExecutionException{
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws IOException {
                OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url2, List<Cookie> cookies) {
                        cookieStore.put(url2.host(), cookies);
                        formerCookie=cookies;
                    }
                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url2) {
                        List<Cookie> cookies = cookieStore.get(url2.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                        .build();
                Request request = new Request.Builder()
                        .addHeader("User-Agent","Mozilla/5.0 (Linux; U; Android 6.0; zh-CN; CAM-TL00 Build/HONORCAM-TL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.108 UCBrowser/11.8.6.966 Mobile Safari/537.36")
                        .addHeader("X-UCBrowser-UA","dv(CAM-TL00);pr(UCBrowser/11.8.6.966);ov(Android 6.0);ss(360*604);pi(720*1208);bt(YZ);pm(0);bv(1);nm(0);im(0);sr(0);nt(2);")
                        .addHeader("Content-Type","application/x-www-form-urlencoded")
                        .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .url(url)
                        .get().build();
                Call call = client.newCall(request);
                Response response = call.execute();
                return response.body().string();
            }
        });

        futureTask.run();
        String response=futureTask.get();
        return response;
    }




    /**
     @author Kyrie
     @description   post请求
     @param          url 链接
     @return         String 返回请求内容
     @throws         IOException
     @throws         InterruptedException
     @throws         ExecutionException
     */

    static public String sendPostRequest(final String url , final RequestBody data ) throws IOException , InterruptedException , ExecutionException
    {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws IOException {
                OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url2, List<Cookie> cookies) {
                        cookieStore.put(url2.host(), cookies);
                    }
                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url2) {
                        List<Cookie> cookies = cookieStore.get(url2.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                        .build();
                Request request = new Request.Builder()
                        .addHeader("User-Agent","Mozilla/5.0 (Linux; U; Android 6.0; zh-CN; CAM-TL00 Build/HONORCAM-TL00) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.108 UCBrowser/11.8.6.966 Mobile Safari/537.36")
                        .addHeader("X-UCBrowser-UA","dv(CAM-TL00);pr(UCBrowser/11.8.6.966);ov(Android 6.0);ss(360*604);pi(720*1208);bt(YZ);pm(0);bv(1);nm(0);im(0);sr(0);nt(2);")
                        .addHeader("Content-Type","application/x-www-form-urlencoded")
                        .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .url(url)
                        .post(data)
                        .build();
                Call call = client.newCall(request);
                Response response = call.execute();
                return response.body().string();
            }
        });

        futureTask.run();
        String response=futureTask.get();
        return response;
    }
}
