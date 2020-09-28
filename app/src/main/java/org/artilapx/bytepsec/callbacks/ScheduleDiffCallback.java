package org.artilapx.bytepsec.callbacks;

import androidx.recyclerview.widget.DiffUtil;

import org.artilapx.bytepsec.models.Schedule;

import java.util.List;

public class ScheduleDiffCallback extends DiffUtil.Callback {

    private List<Schedule> oldSchedule;
    private List<Schedule> newSchedule;

    public ScheduleDiffCallback(List<Schedule> newSchedule, List<Schedule> oldSchedule) {
        this.newSchedule = newSchedule;
        this.oldSchedule = oldSchedule;
    }

    @Override
    public int getOldListSize() {
        return oldSchedule.size();
    }

    @Override
    public int getNewListSize() {
        return newSchedule.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldSchedule.get(oldItemPosition).getGroup_id() == newSchedule.get(newItemPosition).getGroup_id();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Schedule oldGame = oldSchedule.get(oldItemPosition);
        final Schedule newGame = newSchedule.get(newItemPosition);
        return oldGame.getGroup_id() == newGame.getGroup_id() && oldGame.getSubject() == newGame.getSubject();
    }
}
