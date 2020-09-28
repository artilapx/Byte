package org.artilapx.bytepsec.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.artilapx.bytepsec.R;
import org.artilapx.bytepsec.adapters.MondayAdapter;
import org.artilapx.bytepsec.listener.ScheduleListUpdateListener;
import org.artilapx.bytepsec.models.Schedule;
import org.artilapx.bytepsec.source.WebHandler;

import java.util.ArrayList;
import java.util.List;

public class Monday extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ScheduleListUpdateListener {

    private MondayViewModel viewModel;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MondayAdapter adapter;
    private LinearLayoutManager layoutManager;
    private LinearLayout emptyView;

    public static Monday newInstance() {
        return new Monday();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MondayViewModel.class);
        viewModel.init(WebHandler.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.page_monday, container, false);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);

        recyclerView = view.findViewById(R.id.schedule_list);
        adapter = new MondayAdapter();
        adapter.setListener(this);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        emptyView = view.findViewById(R.id.empty_view);

        viewModel.getSchedule().observe(this, new Observer<List<Schedule>>() {
            @Override
            public void onChanged(@Nullable List<Schedule> scheduleList) {
                setSchedule(scheduleList);
            }
        });

        return view;
    }

    /**
     * Update schedule list
     * @param scheduleList the list of games
     */
    private void setSchedule(List<Schedule> scheduleList) {
        adapter.setData(scheduleList);
        emptyView.setVisibility(scheduleList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void fetchSchedule() {
        refreshLayout.setRefreshing(true);
        viewModel.fetchSchedule();
    }

    @Override
    public void onRefresh() {
        fetchSchedule();
    }

    @Override
    public void onScheduleListUpdated() {
        // Scroll to top
        recyclerView.scrollToPosition(0);
        refreshLayout.setRefreshing(false);
    }
}
