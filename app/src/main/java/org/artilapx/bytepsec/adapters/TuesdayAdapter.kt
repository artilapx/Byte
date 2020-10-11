package org.artilapx.bytepsec.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.artilapx.bytepsec.R
import org.artilapx.bytepsec.models.Schedule

class TuesdayAdapter(private val items: Array<Schedule>) :
        RecyclerView.Adapter<TuesdayAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ScheduleViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_lesson, viewGroup, false)
        return ScheduleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val list: Schedule = items[position]
        for (i in 0..6) {
            holder.startTime.text = "%s".format(
                    list.from.substring(0, list.from.length - 3)
            )
            holder.endTime.text = "%s".format(
                    list.to.substring(0, list.from.length - 3)
            )
            holder.subject.text = list.subject
            holder.teacher.text = list.Teacher
            holder.place.text = list.Cabinet
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    open inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val startTime: TextView = itemView.findViewById(R.id.startTime) as TextView
        val endTime: TextView = itemView.findViewById(R.id.endTime) as TextView
        val subject: TextView = itemView.findViewById(R.id.subject) as TextView
        val teacher: TextView = itemView.findViewById(R.id.teacher) as TextView
        val place: TextView = itemView.findViewById(R.id.place) as TextView
    }
}