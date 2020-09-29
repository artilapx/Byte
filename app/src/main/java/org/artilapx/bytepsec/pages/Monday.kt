package org.artilapx.bytepsec.pages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.gson.Gson
import okhttp3.*
import org.artilapx.bytepsec.R
import org.artilapx.bytepsec.adapters.MondayAdapter
import org.artilapx.bytepsec.models.Schedule
import java.io.IOException

class Monday : Fragment(), OnRefreshListener {

    private val path = "https://pgaek.by/wp-content/plugins/shedule/api/getSchedule.php?group=%d"
    private val client = OkHttpClient()
    private var groupID: Int = -1
    private var activityInstance: Activity? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.page_monday, container, false)
        loadSchedule()
        return view
    }

    @SuppressLint("ResourceAsColor")
    private fun initValues() {
        activityInstance = activity
        mSwipeRefreshLayout = view?.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout?.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        mSwipeRefreshLayout?.setOnRefreshListener(this)
        mSwipeRefreshLayout?.setRefreshing(true)
        mSwipeRefreshLayout?.setOnRefreshListener {
            loadSchedule()
        }
    }

    private fun getCurrentConfig() {
        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val group = pref.getString("pref_group", "-1")
        if (group != null) groupID = group.toInt()
    }

    private fun fillList(items: Array<Schedule>) {
        activityInstance?.runOnUiThread {
            view?.findViewById<RecyclerView>(R.id.schedule_list)?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activityInstance)
                adapter =
                        MondayAdapter(items.groupBy { v -> v.date }.map { v -> v.value })
            }
        }
    }

    private fun loadSchedule() {
        getCurrentConfig()

        val url = path.format(groupID)
        val request = Request.Builder().url(url).build()

        val error: LinearLayout? = view?.findViewById(R.id.empty_view)
        val content: RecyclerView? = view?.findViewById(R.id.schedule_list)

        if (error != null) {
            error.visibility = View.GONE
        }
        mSwipeRefreshLayout?.setRefreshing(true)

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activityInstance?.runOnUiThread {
                    if (error != null) {
                        error.visibility = View.VISIBLE
                    }
                    if (content != null) {
                        content.visibility = View.GONE
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val items: Array<Schedule> =
                            Gson().fromJson(response.body?.string(), Array<Schedule>::class.java)
                    fillList(items)
                    activityInstance?.runOnUiThread {
                        if (error != null) {
                            error.visibility = View.GONE
                        }
                        if (content != null) {
                            content.visibility = View.VISIBLE
                        }
                    }
                } catch (e: Exception) {
                    activityInstance?.runOnUiThread {
                        if (error != null) {
                            error.visibility = View.VISIBLE
                        }
                        if (content != null) {
                            content.visibility = View.GONE
                        }
                    }
                }
                activityInstance?.runOnUiThread {
                    mSwipeRefreshLayout?.isRefreshing = false
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): Monday {
            return Monday()
        }
    }

    override fun onRefresh() {
        loadSchedule()
    }
}