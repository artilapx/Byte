package org.artilapx.bytepsec.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.artilapx.bytepsec.R;
import org.artilapx.bytepsec.activities.NewsReadActivity;
import org.artilapx.bytepsec.common.PagedList;
import org.artilapx.bytepsec.common.ThumbSize;
import org.artilapx.bytepsec.models.NewsHeader;
import org.artilapx.bytepsec.utils.ImageUtils;

public class NewsAdapter extends InfiniteAdapter<NewsHeader, NewsAdapter.NewsViewHolder> {

    private ThumbSize mThumbSize;

    public NewsAdapter(PagedList<NewsHeader> dataset, RecyclerView recyclerView) {
        super(dataset, recyclerView);
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateHolder(ViewGroup parent) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false));
    }

    public void setThumbnailsSize(@NonNull ThumbSize size) {
        if (!size.equals(mThumbSize)) {
            mThumbSize = size;
            notifyItemRangeChanged(0, getItemCount());
        }
    }

    @Override
    public void onBindHolder(NewsAdapter.NewsViewHolder viewHolder, NewsHeader data, int position) {
        viewHolder.fill(data, mThumbSize);
    }

    @Override
    public long getItemId(NewsHeader data) {
        return data.id;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView textViewTitle;
        private final TextView textViewSubtitle;
        private final ImageView imageView;
        private NewsHeader mData;

        public NewsViewHolder(final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewSubtitle = itemView.findViewById(R.id.subtitle);
            imageView = itemView.findViewById(R.id.thumb);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public NewsHeader getData() {
            return mData;
        }

        public void fill(NewsHeader data, ThumbSize thumbSize) {
            mData = data;
            textViewTitle.setText(mData.title);
            if (textViewSubtitle != null) {
                if (TextUtils.isEmpty(mData.summary)) {
                    textViewSubtitle.setVisibility(View.GONE);
                } else {
                    textViewSubtitle.setText(mData.summary);
                    textViewSubtitle.setVisibility(View.VISIBLE);
                }
            }
            ImageUtils.setThumbnailWithSize(imageView, data.thumbnail, thumbSize);
        }

        @Override
        public void onClick(View v) {
            NewsHeader newsHeader = getData();
            Context context = v.getContext();
            context.startActivity(new Intent(context.getApplicationContext(), NewsReadActivity.class)
                    .putExtra("news", newsHeader));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
