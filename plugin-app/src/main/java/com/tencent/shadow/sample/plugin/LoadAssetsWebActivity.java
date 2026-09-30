package com.tencent.shadow.sample.plugin;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LoadAssetsWebActivity extends BaseActivity{

    // 登录签名共享密钥，只在本地算签名用，不能放进请求
    private static final String SECRET_KEY = "a7Fk29LmQx8Zs3VtRn6Yw1BdC4EjHpGu";

    private WebView webw;

    public static void launch(Context context){
        Intent intent=new Intent(context,LoadAssetsWebActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_web);
        webw = findViewById(R.id.web);
        initWebView();

        // 基础URL
        String baseUrl = "http://60.16.5.211:27070/app/";
        // 初始化loadUrl，确保在finally中能访问到
        String loadUrl = baseUrl;

        try {
            Intent intent = getIntent();
            String workparms = intent.getStringExtra("workparms");
            String name = intent.getStringExtra("name");
            String code = intent.getStringExtra("code");
            String token = intent.getStringExtra("token");

            // 构建参数拼接的StringBuilder
            StringBuilder paramsBuilder = new StringBuilder();

            // 处理原有workparms参数
            if (workparms != null && !workparms.isEmpty()) {
                String decoded = URLDecoder.decode(workparms, "UTF-8");
                // 如果包含id=，则先拼接这个参数
                if (decoded.contains("id=")) {
                    paramsBuilder.append(decoded);
                }
            }

            // 拼接name参数（非空才拼接）
            if (name != null && !name.isEmpty()) {
                if (paramsBuilder.length() > 0) {
                    paramsBuilder.append("&"); // 已有参数，用&分隔
                }
                paramsBuilder.append("name=").append(URLEncoder.encode(name, "UTF-8"));
            }

            // 拼接code参数（非空才拼接）
            if (code != null && !code.isEmpty()) {
                if (paramsBuilder.length() > 0) {
                    paramsBuilder.append("&"); // 已有参数，用&分隔
                }
                paramsBuilder.append("code=").append(URLEncoder.encode(code, "UTF-8"));
            }

            // 拼接token参数（非空才拼接）
            if (token != null && !token.isEmpty()) {
                if (paramsBuilder.length() > 0) {
                    paramsBuilder.append("&"); // 已有参数，用&分隔
                }
                paramsBuilder.append("token=").append(URLEncoder.encode(token, "UTF-8"));
            }

            // 拼接timestamp和sign参数（登录签名，H5侧用它调登录接口换token）
            // 签名用的username就是code参数的值，即intent里的code
            String signUsername = (code != null) ? code : "";
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String sign = buildSign(signUsername, timestamp);
            if (paramsBuilder.length() > 0) {
                paramsBuilder.append("&");
            }
            paramsBuilder.append("timestamp=").append(timestamp);
            paramsBuilder.append("&sign=").append(URLEncoder.encode(sign, "UTF-8"));

            // 最终拼接URL：如果有参数则加?，再拼接参数
            if (paramsBuilder.length() > 0) {
                loadUrl = baseUrl + "?" + paramsBuilder.toString();
            }

        } catch (UnsupportedEncodingException e) {
            // 更友好的异常处理，避免直接抛出RuntimeException导致崩溃
            e.printStackTrace();
            // 可以添加Toast提示用户加载失败
            // Toast.makeText(this, "参数解析失败", Toast.LENGTH_SHORT).show();
        } finally {
            // 加载最终拼接好的URL
            webw.loadUrl(loadUrl);
        }
    }

    /**
     * 计算登录签名：HMAC-SHA256(secretKey, username + "&" + timestamp)，小写hex，再encodeURIComponent
     */
    private static String buildSign(String username, String timestamp) {
        try {
            String content = username + "&" + timestamp;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "HmacSHA256"));
            byte[] bytes = mac.doFinal(content.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder();
            for (byte b : bytes) {
                hex.append(String.format("%02x", b));
            }
            // encodeURIComponent：URLSearchParams 风格编码，空格转%20
            return URLEncoder.encode(hex.toString(), "UTF-8")
                    .replace("+", "%20");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 初始化webView
     */
    private void initWebView(){

        // 调试模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // 配置 WebView 设置
        WebSettings webSettings = webw.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用 JavaScript
        webSettings.setDomStorageEnabled(true); // 启用 DOM 存储
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 使用缓存
        webSettings.setBuiltInZoomControls(true); // 启用缩放控件
        webSettings.setDisplayZoomControls(false); // 隐藏缩放控件
        webSettings.setAllowFileAccess(true); // 允许访问文件数据
        webSettings.setAllowContentAccess(true); // 允许访问文件数据
        webSettings.setUseWideViewPort(false);
        webSettings.setAllowFileAccessFromFileURLs(true);// 允许 file:// 访问 file://
        webSettings.setAllowUniversalAccessFromFileURLs(true);// 允许 file:// 访问 http:// 或 https://

        // 设置 WebViewClient 处理链接
        webw.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 让 WebView 自己处理跳转
                view.loadUrl(url);
                return true; // 返回 true 表示拦截系统默认处理
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 忽略 SSL 错误
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });


        webw.addJavascriptInterface(new AndroidInterface(this), "Android");
      
    }

    // 定义一个 AndroidInterface 类，里面是要暴露给 JS 的方法
    public class AndroidInterface {

        public Context mContext;

        public AndroidInterface(Context context) {
            this.mContext =context;
        }

        @JavascriptInterface
        public void finishThisActivity(){
           finish();
        }


    }

}
