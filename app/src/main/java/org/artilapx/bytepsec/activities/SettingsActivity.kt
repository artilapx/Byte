package org.artilapx.bytepsec.activities

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import okhttp3.*
import org.artilapx.bytepsec.R
import org.artilapx.bytepsec.models.Group
import java.io.IOException

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        companion object {
            @JvmStatic
            fun newInstance(): SettingsFragment {
                return SettingsFragment()
            }
        }

        private val getGroupStrTemplate =
                "https://pgaek.by/wp-content/plugins/shedule/api/getgroup.php?spec=%s"
        private val client = OkHttpClient()
        private var activityInstance: Activity? = null
        private var isPrefListenerActivated: Boolean = false

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.options, rootKey)
            activityInstance = super.getActivity()
            loadGroupsList(null)
        }

        private fun updateGroupSummary(groupPref: ListPreference?, n: Any) {
            val pos = groupPref?.entryValues?.indexOf(n)
            if (pos != null && pos >= 0)
                groupPref.summary = groupPref.entries?.get(pos)
            else
                groupPref?.summary = null
        }

        private fun loadGroupsList(group: CharSequence?) {
            val specPref = findPreference("pref_spec") as ListPreference?
            val groupPref = findPreference("pref_group") as ListPreference?

            if (!isPrefListenerActivated) {
                specPref?.onPreferenceChangeListener =
                        Preference.OnPreferenceChangeListener { _, n ->
                            loadGroupsList(specPref?.entries?.get((n as String).toInt()))
                            updateGroupSummary(groupPref, n)
                            true
                        }
                groupPref?.onPreferenceChangeListener =
                        Preference.OnPreferenceChangeListener { _, n ->
                            updateGroupSummary(groupPref, n)
                            true
                        }
                isPrefListenerActivated = true
            }

            groupPref?.entries = arrayOf<String>()
            groupPref?.entryValues = arrayOf<String>()
            activityInstance?.runOnUiThread { groupPref?.isEnabled = true }

            val url = if (group == null)
                getGroupStrTemplate.format(specPref?.entries?.get(specPref.value.toInt()))
            else
                getGroupStrTemplate.format(group)

            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    activityInstance?.runOnUiThread { groupPref?.isEnabled = false }
                }

                override fun onResponse(call: Call, response: Response) {
                    val items: Array<Group> =
                            Gson().fromJson(response.body?.string(), Array<Group>::class.java)
                    groupPref?.entries = items.map { v -> v.group_name }.toTypedArray()
                    groupPref?.entryValues = items.map { v -> v.id_group.toString() }.toTypedArray()
                    val pref: SharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context)
                    val selected_id_group = pref.getString("pref_group", "-1")
                    if (selected_id_group != "-1" && selected_id_group != null) {
                        activityInstance?.runOnUiThread {
                            updateGroupSummary(groupPref, selected_id_group)
                            groupPref?.isEnabled = true
                        }
                    }
                }
            })
        }
    }
}