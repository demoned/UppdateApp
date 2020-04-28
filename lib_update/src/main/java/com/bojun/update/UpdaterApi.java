package com.bojun.update;

import com.bojun.update.download.DownloadInterceptor;
import com.bojun.update.download.DownloadService;
import com.bojun.update.download.IDownloadListener;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdaterApi {
    private static OkHttpClient newOkHttpClient(Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC));

        if (interceptors != null && interceptors.length > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }

    private static Retrofit newRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(UpdaterService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    static DownloadService getDownloadService(IDownloadListener downloadListener) {
        return newRetrofit(newOkHttpClient(new DownloadInterceptor(downloadListener))).create(DownloadService.class);
    }
}
