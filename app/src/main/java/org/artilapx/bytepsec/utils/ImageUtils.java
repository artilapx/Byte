package org.artilapx.bytepsec.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import org.artilapx.bytepsec.R;
import org.artilapx.bytepsec.common.ThumbSize;
import org.artilapx.bytepsec.common.TransitionDisplayer;

public class ImageUtils {

    private static DisplayImageOptions mOptionsThumb = null;
    private static DisplayImageOptions mOptionsUpdate = null;

    public static void init(Context context) {
        if (!ImageLoader.getInstance().isInited()) {
            int cacheMb = 100;
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .defaultDisplayImageOptions(getImageLoaderOptionsBuilder().build())
                    .diskCacheSize(cacheMb * 1024 * 1024) //100 Mb
                    .diskCacheFileCount(200)
                    .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // 2 Mb
                    .build();

            ImageLoader.getInstance().init(config);
        }
        if (mOptionsThumb == null) {
            Drawable holder = ContextCompat.getDrawable(context, R.drawable.ic_baseline_image_24);
            mOptionsThumb = getImageLoaderOptionsBuilder()
                    .showImageOnFail(R.drawable.ic_baseline_error_outline_24)
                    .showImageForEmptyUri(R.drawable.ic_baseline_broken_image_24)
                    .showImageOnLoading(holder)
                    .build();
        }

        if (mOptionsUpdate == null) {
            mOptionsUpdate = getImageLoaderOptionsBuilder()
                    .displayer(new TransitionDisplayer())
                    .build();
        }
    }

    private static DisplayImageOptions.Builder getImageLoaderOptionsBuilder() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(false)
                .displayer(new FadeInBitmapDisplayer(200, true, true, false));
    }


    public static void setThumbnailWithSize(@NonNull ImageView imageView, String url, @Nullable ThumbSize size) {
        ImageLoader.getInstance().displayImage(
                fixUrl(url),
                new ImageViewAware(imageView),
                mOptionsThumb,
                size != null && imageView.getMeasuredWidth() == 0 ? size.toImageSize() : null,
                null,
                null
        );
    }

    private static String fixUrl(String url) {
        return (!android.text.TextUtils.isEmpty(url) && url.charAt(0) == '/') ? "file://" + url : url;
    }

}
