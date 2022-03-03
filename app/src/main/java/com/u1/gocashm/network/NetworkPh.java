package com.u1.gocashm.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;


import com.blankj.utilcode.util.AppUtils;
import com.u1.gocashm.PhApplication;
import com.u1.gocashm.util.LogUtil;
import com.firstinvest.chlibrary.BuildConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.NonNull;

import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkPh {
    private static final String TAG = "NetworkPh";
    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create(new Gson());
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static HttpServer httpServer;
    private static HttpServer httpServer2;
    public static final int DEFAULT_TIMEOUT = 20;
    public static String able = PhApplication.getContext().getResources().getConfiguration().locale.getCountry();

    public static HttpServer getHttpServer() {
        if (httpServer == null) {
            httpServer = getRetrofit(BuildConfig.END_POINT).create(HttpServer.class);
        }
        return httpServer;
    }

    public static HttpServer getHttpServer2(String url) {
        httpServer2 = getRetrofit(url).create(HttpServer.class);
        return httpServer2;
    }

    @SuppressLint("NewApi")
    private static Retrofit getRetrofit(String baseUrl) {
        Interceptor appInfo = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("app-key", BuildConfig.APP_KEY)
                        .addHeader("locale", "ar-" + able)
//                        .header("accessToken", TokenUtils.getToken(PhApplication.getInstance()))
//                        .removeHeader("User-Agent")//移除旧的
//                        .addHeader("User-Agent", getUserAgent(PhApplication.getInstance()))//添加真正的头部
//                        .header("versionId", String.valueOf(AppUtils.getAppVersionCode()))
//                        .header("requestId", DevicePhUtil.getDeviceUnionID(PhApplication.getInstance()))
//                        .header("deviceId", DevicePhUtil.getDeviceUnionID(PhApplication.getInstance()))
//                        .header("inputChannel", "Upeso_App")
                        .header("client_id", BuildConfig.CLIENT_ID)
//                        .header("platform", "googleplay")
                        .header("app-version", AppUtils.getAppVersionName())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(appInfo)
                .addInterceptor(getLogInterceptor())
                .sslSocketFactory(getSSLSocketFactory(), new CustomTrustManager())
                .hostnameVerifier(getHostnameVerifier())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        onHttps(okHttpClientBuilder);
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

    private static String getUserAgent(Context context) {
        String userAgent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        return userAgent;
    }

    /**
     * 日志拦截
     *
     * @return
     */
    private static Interceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                int printMaxLength = 100000;
                if (message.length() < printMaxLength) {
                    LogUtil.i("OkHttp:", message);
                } else {
                    LogUtil.i("OkHttp:", message.substring(0, printMaxLength));
                }
            }
        });

//        if (!BuildConfig.DEBUG) {
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        } else {
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }

//    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
//        //创建一个不验证证书链的证书信任管理器。
//        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//            @Override
//            public void checkClientTrusted(
//                    java.security.cert.X509Certificate[] chain,
//                    String authType) throws CertificateException {
//            }
//
//            @Override
//            public void checkServerTrusted(
//                    java.security.cert.X509Certificate[] chain,
//                    String authType) throws CertificateException {
//            }
//
//            @Override
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return new java.security.cert.X509Certificate[0];
//            }
//        }};
//
//        // Install the all-trusting trust manager
//        final SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, trustAllCerts,
//                new java.security.SecureRandom());
//        // Create an ssl socket factory with our all-trusting manager
//        return sslContext.getSocketFactory();
//    }


    //使用自定义SSLSocketFactory
    private static void onHttps(OkHttpClient.Builder builder) {
        try {
            builder.sslSocketFactory(getSSLSocketFactory()).hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.i(TAG, "request:" + request.toString());
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.i(TAG, "response body:" + content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    }

    private static SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new CustomTrustManager()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    private static class CustomTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        return hostnameVerifier;
    }
}
