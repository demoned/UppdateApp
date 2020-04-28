package com.bojun.updateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import com.bojun.update.DownLoadInfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    private AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = getJson("json", MainActivity.this);
        appInfo = new Gson().fromJson(json, AppInfo.class);
    }

    public void down(View view) {
        UpdateDialog updateDialog = new UpdateDialog(this);
        if (!updateDialog.isShowing()) {
            updateDialog.setInfo(new DownLoadInfo(appInfo.name,appInfo.versionShort,appInfo.install_url));
            updateDialog.show();
        }
    }

    /**
     * 获取assert路径下的json 文件
     *
     * @param fileName
     * @param context
     * @return
     */
    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
