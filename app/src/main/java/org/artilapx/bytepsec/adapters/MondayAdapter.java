package org.artilapx.bytepsec.adapters;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.artilapx.bytepsec.R;
import org.artilapx.bytepsec.callbacks.ScheduleDiffCallback;
import org.artilapx.bytepsec.callbacks.ScheduleListUpdateCallback;
import org.artilapx.bytepsec.listener.ScheduleListUpdateListener;
import org.artilapx.bytepsec.models.Schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MondayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean headerEnabled = false;

    public final static int ITEM_NORMAL = 2;

    private List<Schedule> dataSet = new ArrayList<>();
    private List<Schedule> dataSetCopy = new ArrayList<>();

    private Deque<List<Schedule>> pendingUpdates = new ArrayDeque<>();
    private ScheduleListUpdateListener updateListener;

    public void setListener(ScheduleListUpdateListener listener) {
        this.updateListener = listener;
    }

    private void updateDataInternal(final List<Schedule> newGames) {
        final List<Schedule> oldGames = new ArrayList<>(dataSet);
        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ScheduleDiffCallback(newGames, oldGames));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        applyDiffResult(newGames, diffResult);
                    }
                });
            }
        }).start();
    }

    private void applyDiffResult(List<Schedule> games, DiffUtil.DiffResult diffResult) {
        pendingUpdates.remove(games);
        dispatchUpdates(games, diffResult);
        if (pendingUpdates.size() > 0) {
            final List<Schedule> latest = pendingUpdates.pop();
            pendingUpdates.clear();
            updateDataInternal(latest);
        }
    }
    private void dispatchUpdates(List<Schedule> games, DiffUtil.DiffResult diffResult) {
        diffResult.dispatchUpdatesTo(new ScheduleListUpdateCallback(this, headerEnabled));
        dataSet.clear();
        dataSet.addAll(games);
        if (updateListener != null) {
            updateListener.onScheduleListUpdated();
        }
    }

    public void setData(List<Schedule> scheduleList) {
        dataSet.clear();
        dataSetCopy.clear();
        dataSetCopy.addAll(scheduleList);
        updateData(scheduleList);
    }

    public void updateData(List<Schedule> scheduleList) {
        pendingUpdates.push(scheduleList);
        if (pendingUpdates.size() > 1) {
            return;
        }
        updateDataInternal(scheduleList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_NORMAL) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new VHItem(view);
        }
        throw new IllegalArgumentException("Unknown view type: " + viewType);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final VHItem item = (VHItem) holder;
        final Schedule schedule = dataSet.get(headerEnabled ? position - 1 : position);
        item.title.setText(schedule.title);
        item.teacher.setText(schedule.teacher);
        assert schedule.classroom != null;
        if (!schedule.classroom.equals("0")) {
            item.auditorium.setText(schedule.teacher);
        } else {
            item.auditorium.setVisibility(View.GONE);
        }
        item.time.setText(schedule.timeStart + " - " + schedule.timeEnd);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_NORMAL;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private static class VHItem extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView teacher;
        private TextView auditorium;
        private TextView time;

        private VHItem(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            teacher = itemView.findViewById(R.id.teacher);
            auditorium = itemView.findViewById(R.id.auditorium);
            time = itemView.findViewById(R.id.time);
        }
    }

}
