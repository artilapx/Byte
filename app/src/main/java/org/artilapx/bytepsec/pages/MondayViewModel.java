package org.artilapx.bytepsec.pages;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.artilapx.bytepsec.App;
import org.artilapx.bytepsec.R;
import org.artilapx.bytepsec.models.Schedule;
import org.artilapx.bytepsec.models.ScheduleResponse;
import org.artilapx.bytepsec.source.WebHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MondayViewModel extends ViewModel {

    private final static String TAG = MondayViewModel.class.getSimpleName();

    private MutableLiveData<List<Schedule>> scheduleList;
    private WebHandler webHandler;

    void init(WebHandler webHandler) {
        this.webHandler = webHandler;
    }

    void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList.setValue(scheduleList);
    }

    LiveData<List<Schedule>> getSchedule() {
        if (scheduleList == null) {
            scheduleList = new MutableLiveData<>();
        }
        return scheduleList;
    }

    void fetchSchedule() {
        Log.i(TAG, "Fetching schedule...");
        webHandler.getSchedule().enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Success!");
                    Toast.makeText(App.getInstance().getApplicationContext(),
                            "ok", Toast.LENGTH_SHORT).show();
                    setScheduleList(response.body().getScheduleList());
                } else {
                    Log.i(TAG, "Got error code: " + response.code());
                    Toast.makeText(App.getInstance().getApplicationContext(),
                            R.string.error_bad_response, Toast.LENGTH_SHORT).show();
                    setScheduleList(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.i(TAG, "Got error", t);
                setScheduleList(new ArrayList<>());
            }
        });
    }

}
