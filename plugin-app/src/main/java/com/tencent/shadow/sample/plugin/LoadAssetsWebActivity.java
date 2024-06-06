package com.tencent.shadow.sample.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;

public class LoadAssetsWebActivity extends BaseActivity{

    private WebView webw;

    public static void launch(Context context){
        Intent intent=new Intent(context,LoadAssetsWebActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_web);
        webw=findViewById(R.id.web);

        webw.loadUrl("file:///android_asset/test.html");
    }
}
