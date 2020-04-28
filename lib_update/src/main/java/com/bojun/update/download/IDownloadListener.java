package com.bojun.update.download;

import java.io.File;

public interface IDownloadListener {
    void onStart();

    void onProgress(long bytesTransferred, long totalBytes, int percentage);

    void onSuccess(File file);

    void onError(Throwable e);
}
