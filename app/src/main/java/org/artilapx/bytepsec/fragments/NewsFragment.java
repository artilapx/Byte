package org.artilapx.bytepsec.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.artilapx.bytepsec.R;
import org.artilapx.bytepsec.adapters.InfiniteAdapter;
import org.artilapx.bytepsec.adapters.NewsAdapter;
import org.artilapx.bytepsec.common.ListWrapper;
import org.artilapx.bytepsec.common.PagedList;
import org.artilapx.bytepsec.models.NewsHeader;
import org.artilapx.bytepsec.views.InfiniteRecyclerView;

public class NewsFragment extends Fragment implements InfiniteRecyclerView.OnLoadMoreListener,
        InfiniteAdapter.OnLoadMoreListener, LoaderManager.LoaderCallbacks<ListWrapper<NewsHeader>> {

    private InfiniteRecyclerView mRecyclerView;
    private View mErrorView;

    private final PagedList<NewsHeader> mDataset = new PagedList<>();
    private NewsAdapter mAdapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnLoadMoreListener(this);
        mErrorView = view.findViewById(R.id.stub_error);

        mAdapter = new NewsAdapter(mDataset, mRecyclerView);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnLoadMoreListener(this);

        //load(); TODO краш
    }

    @Override
    public boolean onLoadMore() {
        load();
        return true;
    }

    /**
     * Лоадер не перезагрузится если
     * Bundle тот же самый с темиже значениями (вроде)
     */
    private void load() {
        mRecyclerView.onLoadingStarted();
        mErrorView.setVisibility(View.GONE);
		/*
	  loader's id
	 */
        int LOADER_ID = 234;
        Loader<ListWrapper<NewsHeader>> loader = getLoaderManager().getLoader(LOADER_ID);
        if (loader == null) {
            loader = getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            loader = getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
        loader.forceLoad();
        mAdapter.setLoaded();
    }

    @NonNull
    @Override
    public Loader<ListWrapper<NewsHeader>> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ListWrapper<NewsHeader>> loader, ListWrapper<NewsHeader> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ListWrapper<NewsHeader>> loader) {

    }
}
