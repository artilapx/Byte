package org.artilapx.bytepsec.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.artilapx.bytepsec.R
import org.artilapx.bytepsec.models.Schedule

class MondayAdapter(private val items: List<List<Schedule>>) :
        RecyclerView.Adapter<MondayAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ScheduleViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_card, viewGroup, false)
        return ScheduleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val list: List<Schedule> = items[position]
        for (i in 1..6) {
            if (list.size >= i) {
                when (i) {
                    1 -> {
                        holder.l1Time.text = "%s-%s".format(
                                list[i - 1].from.substring(0, list[i - 1].from.length - 3),
                                list[i - 1].to.substring(0, list[i - 1].to.length - 3)
                        )
                        holder.l1Subj.text = list[i - 1].subject
                        holder.l1Teacher.text = list[i - 1].Teacher
                        holder.l1Place.text = list[i - 1].Cabinet
                        holder.layout1.visibility = View.VISIBLE
                    }
                    2 -> {
                        holder.l2Time.text = "%s-%s".format(
                                list[i - 1].from.substring(0, list[i - 1].from.length - 3),
                                list[i - 1].to.substring(0, list[i - 1].to.length - 3)
                        )
                        holder.l2Subj.text = list[i - 1].subject
                        holder.l2Teacher.text = list[i - 1].Teacher
                        holder.l2Place.text = list[i - 1].Cabinet
                        holder.layout2.visibility = View.VISIBLE
                    }
                    3 -> {
                        holder.l3Time.text = "%s-%s".format(
                                list[i - 1].from.substring(0, list[i - 1].from.length - 3),
                                list[i - 1].to.substring(0, list[i - 1].to.length - 3)
                        )
                        holder.l3Subj.text = list[i - 1].subject
                        holder.l3Teacher.text = list[i - 1].Teacher
                        holder.l3Place.text = list[i - 1].Cabinet
                        holder.layout3.visibility = View.VISIBLE
                    }
                    4 -> {
                        holder.l4Time.text = "%s-%s".format(
                                list[i - 1].from.substring(0, list[i - 1].from.length - 3),
                                list[i - 1].to.substring(0, list[i - 1].to.length - 3)
                        )
                        holder.l4Subj.text = list[i - 1].subject
                        holder.l4Teacher.text = list[i - 1].Teacher
                        holder.l4Place.text = list[i - 1].Cabinet
                        holder.layout4.visibility = View.VISIBLE
                    }
                    5 -> {
                        holder.l5Time.text = "%s-%s".format(
                                list[i - 1].from.substring(0, list[i - 1].from.length - 3),
                                list[i - 1].to.substring(0, list[i - 1].to.length - 3)
                        )
                        holder.l5Subj.text = list[i - 1].subject
                        holder.l5Teacher.text = list[i - 1].Teacher
                        holder.l5Place.text = list[i - 1].Cabinet
                        holder.layout5.visibility = View.VISIBLE
                    }
                    6 -> {
                        holder.l6Time.text = "%s-%s".format(
                                list[i - 1].from.substring(0, list[i - 1].from.length - 3),
                                list[i - 1].to.substring(0, list[i - 1].to.length - 3)
                        )
                        holder.l6Subj.text = list[i - 1].subject
                        holder.l6Teacher.text = list[i - 1].Teacher
                        holder.l6Place.text = list[i - 1].Cabinet
                        holder.layout6.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    open inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val layout1: CardView = itemView.findViewById(R.id.Layout1)
        val layout2: CardView = itemView.findViewById(R.id.Layout2)
        val layout3: CardView = itemView.findViewById(R.id.Layout3)
        val layout4: CardView = itemView.findViewById(R.id.Layout4)
        val layout5: CardView = itemView.findViewById(R.id.Layout5)
        val layout6: CardView = itemView.findViewById(R.id.Layout6)

        val l1Time: TextView = itemView.findViewById(R.id.L1Time) as TextView
        val l2Time: TextView = itemView.findViewById(R.id.L2Time) as TextView
        val l3Time: TextView = itemView.findViewById(R.id.L3Time) as TextView
        val l4Time: TextView = itemView.findViewById(R.id.L4Time) as TextView
        val l5Time: TextView = itemView.findViewById(R.id.L5Time) as TextView
        val l6Time: TextView = itemView.findViewById(R.id.L6Time) as TextView

        val l1Subj: TextView = itemView.findViewById(R.id.L1Subj) as TextView
        val l2Subj: TextView = itemView.findViewById(R.id.L2Subj) as TextView
        val l3Subj: TextView = itemView.findViewById(R.id.L3Subj) as TextView
        val l4Subj: TextView = itemView.findViewById(R.id.L4Subj) as TextView
        val l5Subj: TextView = itemView.findViewById(R.id.L5Subj) as TextView
        val l6Subj: TextView = itemView.findViewById(R.id.L6Subj) as TextView

        val l1Teacher: TextView = itemView.findViewById(R.id.L1Teacher) as TextView
        val l2Teacher: TextView = itemView.findViewById(R.id.L2Teacher) as TextView
        val l3Teacher: TextView = itemView.findViewById(R.id.L3Teacher) as TextView
        val l4Teacher: TextView = itemView.findViewById(R.id.L4Teacher) as TextView
        val l5Teacher: TextView = itemView.findViewById(R.id.L5Teacher) as TextView
        val l6Teacher: TextView = itemView.findViewById(R.id.L6Teacher) as TextView

        val l1Place: TextView = itemView.findViewById(R.id.L1Place) as TextView
        val l2Place: TextView = itemView.findViewById(R.id.L2Place) as TextView
        val l3Place: TextView = itemView.findViewById(R.id.L3Place) as TextView
        val l4Place: TextView = itemView.findViewById(R.id.L4Place) as TextView
        val l5Place: TextView = itemView.findViewById(R.id.L5Place) as TextView
        val l6Place: TextView = itemView.findViewById(R.id.L6Place) as TextView
    }
}