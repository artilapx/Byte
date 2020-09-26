package org.artilapx.bytepsec.common;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Response;

public final class CookieStore implements Interceptor {

    @Nullable
    private static CookieStore sInstance = null;

    @NonNull
    public static CookieStore getInstance() {
        if (sInstance == null) {
            sInstance = new CookieStore();
        }
        return sInstance;
    }

    private final HashMap<String,String> mCookies;

    private CookieStore() {
        mCookies = new HashMap<>();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String cookie = mCookies.get(chain.request().url().host());
        if (cookie != null) {
            return chain.proceed(chain.request().newBuilder().addHeader("Cookie", cookie).build());
        } else {
            return chain.proceed(chain.request());
        }
    }

    public void put(String domain, String cookie) {
        mCookies.put(domain, cookie);
    }

    @Nullable
    public String get(String domain) {
        return mCookies.get(domain);
    }
}
