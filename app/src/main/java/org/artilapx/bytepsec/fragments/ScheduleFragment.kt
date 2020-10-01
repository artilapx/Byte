package org.artilapx.bytepsec.fragments

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
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import okhttp3.*
import org.artilapx.bytepsec.R
import org.artilapx.bytepsec.adapters.MondayAdapter
import org.artilapx.bytepsec.adapters.ScheduleTabAdapter
import org.artilapx.bytepsec.models.Schedule
import org.artilapx.bytepsec.pages.Monday
import org.artilapx.bytepsec.utils.NetworkUtils
import java.io.IOException
import java.util.concurrent.TimeUnit

class ScheduleFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val TIMEOUT_SECS = 5
    private val path = "https://pgaek.by/wp-content/plugins/shedule/api/getSchedule.php?group=%d"
    private val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECS.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECS.toLong(), TimeUnit.SECONDS)
            .build()
    private var noInternetConnection: LinearLayout? = null
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

    private fun initValues() {
        activityInstance = activity
        noInternetConnection = view?.findViewById(R.id.no_network_view)
        mSwipeRefreshLayout = view?.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)
        mSwipeRefreshLayout?.setOnRefreshListener(this)
        if (NetworkUtils.isNetworkAvailable(context)) {
            mSwipeRefreshLayout?.setRefreshing(true)
        } else {
            noInternetConnection?.visibility = View.VISIBLE
            mSwipeRefreshLayout?.setRefreshing(false)
        }
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

        val notFound: LinearLayout? = view?.findViewById(R.id.not_found_view)
        val timeout: LinearLayout? = view?.findViewById(R.id.timeout)
        val content: RecyclerView? = view?.findViewById(R.id.schedule_list)

        notFound?.visibility = View.GONE
        noInternetConnection?.visibility = View.GONE
        timeout?.visibility = View.GONE
        mSwipeRefreshLayout?.setRefreshing(true)

        if (NetworkUtils.isNetworkAvailable(context)) {

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    activityInstance?.runOnUiThread {
                        mSwipeRefreshLayout?.isRefreshing = false
                        timeout?.visibility = View.VISIBLE
                        notFound?.visibility = View.GONE
                        content?.visibility = View.GONE
                        noInternetConnection?.visibility = View.GONE
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val items: Array<Schedule> =
                                Gson().fromJson(response.body?.string(), Array<Schedule>::class.java)
                        fillList(items)
                        activityInstance?.runOnUiThread {
                            mSwipeRefreshLayout?.isRefreshing = false
                            timeout?.visibility = View.GONE
                            notFound?.visibility = View.GONE
                            content?.visibility = View.VISIBLE
                            noInternetConnection?.visibility = View.GONE
                        }
                    } catch (e: Exception) {
                        activityInstance?.runOnUiThread {
                            mSwipeRefreshLayout?.isRefreshing = false
                            timeout?.visibility = View.GONE
                            notFound?.visibility = View.VISIBLE
                            content?.visibility = View.GONE
                            noInternetConnection?.visibility = View.GONE
                        }
                    }
                }
            })
        } else {
            noInternetConnection?.visibility = View.VISIBLE
            timeout?.visibility = View.GONE
            notFound?.visibility = View.GONE
            content?.visibility = View.GONE
            mSwipeRefreshLayout?.isRefreshing = false
        }
    }

    override fun onRefresh() {
        loadSchedule()
    }

    companion object {
        const val ARG_PAGE = "ARG_PAGE"
        @JvmStatic
        fun newInstance(page: Int): ScheduleFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = ScheduleFragment()
            fragment.arguments = args
            return fragment
        }
    }
}