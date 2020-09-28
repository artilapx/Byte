package org.artilapx.bytepsec.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.artilapx.bytepsec.models.NewsDetails;
import org.artilapx.bytepsec.models.NewsHeader;
import org.artilapx.bytepsec.utils.NetworkUtils;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public abstract class Aggregator {

    @NonNull
    public abstract ArrayList<NewsHeader> query(@Nullable String search, int page) throws Exception;

    @NonNull
    public abstract NewsDetails getDetails(NewsHeader header) throws Exception;

    protected final Document getPage(String url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        return NetworkUtils.httpGet(url);
    }

}
