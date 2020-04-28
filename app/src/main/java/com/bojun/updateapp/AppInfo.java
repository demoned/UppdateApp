package com.bojun.updateapp;

import com.bojun.update.UpdaterUtil;

public class AppInfo {
    public String name;
    public String version;
    public String changelog;
    public int updated_at;
    public String versionShort;
    public String build;
    public String installUrl;
    public String install_url;
    public String direct_install_url;
    public String update_url;
    public BinaryBean binary;

    public static class BinaryBean {
        public int fsize;

        @Override
        public String toString() {
            return "BinaryBean{" +
                    "fsize=" + fsize +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AppInfo{\n" +
                "appName=【" + name + "】\n" +
                "versionCode=" + version + "\n" +
                "versionName=" + versionShort + "\n" +
                "size=" + UpdaterUtil.getFileSizeDesc(binary.fsize) + "\n" +
                "changeLog=" + changelog + "\n" +
                "updateUrl=" + update_url + "\n" +
                "installUrl=" + install_url + "\n" +
                "}";
    }
}
