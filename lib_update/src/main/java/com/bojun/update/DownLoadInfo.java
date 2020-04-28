package com.bojun.update;

public class DownLoadInfo {
    private String appName;
    private String versionInfo;
    private String downloadUrl;


    public DownLoadInfo() {
    }

    public DownLoadInfo(String appName, String versionInfo, String downloadUrl) {
        this.appName = appName;
        this.versionInfo = versionInfo;
        this.downloadUrl = downloadUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
