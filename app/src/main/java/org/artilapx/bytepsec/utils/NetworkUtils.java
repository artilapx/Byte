package org.artilapx.bytepsec.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.artilapx.bytepsec.BuildConfig;
import org.artilapx.bytepsec.common.CookieStore;
import org.artilapx.bytepsec.common.SSLSocketFactoryExtended;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

import info.guardianproject.netcipher.NetCipher;
import info.guardianproject.netcipher.client.StrongOkHttpClientBuilder;
import info.guardianproject.netcipher.proxy.OrbotHelper;
import okhttp3.OkHttpClient;

public class NetworkUtils {

    private static OkHttpClient sHttpClient = null;

    @NonNull
    private static OkHttpClient.Builder getClientBuilder() {
        return new OkHttpClient.Builder()
                /*.connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)*/
                .addInterceptor(CookieStore.getInstance());
    }

    public static void init(Context context, boolean useTor) {
        OkHttpClient.Builder builder = getClientBuilder();
        if (useTor && OrbotHelper.get(context).init()) {
            try {
                StrongOkHttpClientBuilder.forMaxSecurity(context)
                        .applyTo(builder, new Intent()
                                .putExtra(OrbotHelper.EXTRA_STATUS, "ON")); //TODO wtf
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sHttpClient = builder.build();
    }

    public static Document httpGet(@NonNull String url) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        SSLSocketFactoryExtended factory = null;
        try {
            factory = new SSLSocketFactoryExtended();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        final SSLSocketFactoryExtended finalFactory = factory;
        InputStream is = null;
        try {
            HttpURLConnection con = NetCipher.getHttpURLConnection(url);
            if (con instanceof HttpsURLConnection) {
                ((HttpsURLConnection) con).setDefaultSSLSocketFactory(finalFactory);
                con.setRequestProperty("charset", "utf-8");
            }
            con.setConnectTimeout(15000);
            is = con.getInputStream();
            return parseHtml(url, is, con);
        } catch (Exception error) {
            LogUtils.getInstance().report("HTTP", error);
            throw error;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static Document parseHtml(@NonNull final String url, final InputStream is,
                                      final HttpURLConnection con) throws IOException {
        Document document = Jsoup.parse(is, con.getContentEncoding(), url);
        return document;
    }

}
