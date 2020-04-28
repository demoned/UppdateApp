package com.bojun.update;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.bojun.update.download.IDownloadListener;

import java.io.File;

/**
 * 下载管理器
 */
public class AppDownloadManager {
    private static final String TAG = AppDownloadManager.class.getCanonicalName();
    private volatile static AppDownloadManager mInstance;
    private Context mContext;
    private String mApkPath;
    private String mApkName;
    private DownLoadInfo appInfo;

    private UpdateDialogListener updateDialogListener;
    private UpdaterDownloader mDownloader;
    private int mCurrProgress;
    private UpdaterNotification mNotification;

    public static AppDownloadManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new AppDownloadManager(context);
                }
            }
        }
        return mInstance;
    }

    private AppDownloadManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public AppDownloadManager setApkPath(String apkPath) {
        this.mApkPath = apkPath;
        return this;
    }

    public AppDownloadManager setApkName(String apkName) {
        this.mApkName = apkName;
        return this;
    }

    public AppDownloadManager setAppInfo(DownLoadInfo appInfo) {
        this.appInfo = appInfo;
        return this;
    }

    public AppDownloadManager setUpdateDialogListener(UpdateDialogListener updateDialogListener) {
        this.updateDialogListener = updateDialogListener;
        return this;
    }

    /**
     * 后台下载
     */
    public void backgroundDownload() {
        mNotification = new UpdaterNotification(mContext, appInfo.getAppName());
        mNotification.setProgress(mCurrProgress);
    }

    /**
     * 取消下载
     */
    public void downloadCancel() {
        if (null != mDownloader) {
            mDownloader.cancel();
        }
    }

    /**
     * 开始下载
     */
    public void download() {
        if (TextUtils.isEmpty(mApkName)) {
            mApkName = appInfo.getAppName() + "-V" + appInfo.getVersionInfo() + ".apk";
        }
        if (TextUtils.isEmpty(mApkPath)) {
            mApkPath = Environment.getExternalStorageDirectory() + File.separator;
        }
        String apkPathName = mApkPath + mApkName;
        mDownloader = new UpdaterDownloader();
        mDownloader.download(appInfo.getDownloadUrl(), apkPathName, new IDownloadListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(long bytesTransferred, long totalBytes, int percentage) {
                mCurrProgress = percentage;
                updateDialogListener.onDownloadProgress(percentage);
                if (mNotification != null) {
                    mNotification.setProgress(percentage);
                }
            }

            @Override
            public void onSuccess(File file) {
                updateDialogListener.onDownloadFinish();
                if (mNotification != null) {
                    mNotification.cancel();
                }
                UpdaterUtil.installApk(mContext, apkPathName);
            }

            @Override
            public void onError(Throwable e) {
                updateDialogListener.onDownloadError(e);
                if (mNotification != null) {
                    mNotification.cancel();
                }
                Toast.makeText(mContext, "下载失败，请稍后重试！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
