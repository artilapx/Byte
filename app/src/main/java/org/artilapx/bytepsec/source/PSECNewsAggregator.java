package org.artilapx.bytepsec.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.artilapx.bytepsec.models.NewsDetails;
import org.artilapx.bytepsec.models.NewsHeader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;

public class PSECNewsAggregator extends Aggregator {

    @NonNull
    @Override
    public ArrayList<NewsHeader> query(@Nullable String search, int page) throws Exception {
        return getList(page);
    }

    @NonNull
    @Override
    public NewsDetails getDetails(NewsHeader header) throws Exception {
        return null;
    }

    protected ArrayList<NewsHeader> getList(int page) throws Exception {
        Document document = getPage(String.format("https://pgaek.by/%d0%bd%d0%be%d0%b2%d0%be%d1%81%d1%82%d0%b8/page/%s", page + 1));
        Elements elements = document.body().select("article.blog_post.clearfix");
        final ArrayList<NewsHeader> list = new ArrayList<>(elements.size());
        for (Element o : elements) {
            list.add(new NewsHeader(
                    o.select("h3").text(),
                    o.select("p").text(),
                    o.select("img").first().attr("src"),
                    o.attr("href")
            ));
        }
        return list;
    }

}
