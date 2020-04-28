package com.bojun.updateapp;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.bojun.update.AppDownloadManager;
import com.bojun.update.DownLoadInfo;
import com.bojun.update.NumberProgressBar;
import com.bojun.update.ScreenUtil;
import com.bojun.update.UpdateDialogListener;

/**
 * 自定义显示升级对话框
 */
public class UpdateDialog extends Dialog implements View.OnClickListener, UpdateDialogListener {
    private Context context;
    private AppDownloadManager appDownloadManager;
    private NumberProgressBar numberProgressBar;

    public UpdateDialog(@NonNull Context context) {
        super(context, R.style.UpdateDialog);
        init(context);
    }

    /**
     * 初始化布局
     */
    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        setContentView(view);
        setWindowSize(context);
        initView(view);
    }

    private void initView(View view) {
        View ibClose = view.findViewById(R.id.ib_close);
        View btnUpdate = view.findViewById(R.id.btn_update);
        numberProgressBar = view.findViewById(R.id.number_progress_bar);
        ibClose.setOnClickListener(this::onClick);
        btnUpdate.setOnClickListener(this::onClick);
    }

    private void setWindowSize(Context context) {
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenUtil.getWith(context) * 0.7f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ib_close) {
            appDownloadManager.downloadCancel();
            dismiss();
        } else if (id == R.id.btn_update) {
            appDownloadManager.download();
            numberProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void setInfo(DownLoadInfo appInfo) {
        appDownloadManager = AppDownloadManager.getInstance(context).setAppInfo(appInfo).setUpdateDialogListener(this);
    }

    @Override
    public void onDownloadFinish() {
        dismiss();
        Log.d("下载进度：", "下载完成！");
    }

    @Override
    public void onDownloadProgress(int percent) {
        Log.d("下载进度：", percent + "");
        if (numberProgressBar.getVisibility() == View.VISIBLE) {
            numberProgressBar.setProgress(percent);
        } else {
            numberProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDownloadError(Throwable e) {
        numberProgressBar.setVisibility(View.GONE);
        Log.d("下载进度：", "下载出错！" + e.getMessage());
    }
}
