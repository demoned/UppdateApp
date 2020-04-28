package com.bojun.update;

public interface UpdateDialogListener {

    void onDownloadFinish();

    void onDownloadProgress(int percent);

    void onDownloadError(Throwable e);
}
