package org.artilapx.bytepsec.callbacks;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import org.artilapx.bytepsec.adapters.MondayAdapter;

public class ScheduleListUpdateCallback implements ListUpdateCallback {

    private MondayAdapter adapter;
    private int offset;

    public ScheduleListUpdateCallback(MondayAdapter adapter, boolean headerEnabled) {
        this.adapter = adapter;
        this.offset = headerEnabled ? 1 : 0;
    }

    @Override
    public void onInserted(int position, int count) {
        adapter.notifyItemRangeInserted(position + offset, count);
    }

    @Override
    public void onRemoved(int position, int count) {
        adapter.notifyItemRangeRemoved(position + offset, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        adapter.notifyItemMoved(fromPosition + offset, toPosition + offset);
    }

    @Override
    public void onChanged(int position, int count, @Nullable Object payload) {
        adapter.notifyItemRangeChanged(position + offset, count, payload);
    }
}
