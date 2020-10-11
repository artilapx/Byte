package org.artilapx.bytepsec.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.artilapx.bytepsec.fragments.ScheduleFragment;
import org.artilapx.bytepsec.pages.Friday;
import org.artilapx.bytepsec.pages.Monday;
import org.artilapx.bytepsec.pages.Saturday;
import org.artilapx.bytepsec.pages.Thursday;
import org.artilapx.bytepsec.pages.Tuesday;
import org.artilapx.bytepsec.pages.Wednesday;
import org.artilapx.bytepsec.utils.ConstantUtils;

import java.util.Calendar;

public class ScheduleTabAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles;

    public ScheduleTabAdapter(FragmentManager fm) {
        super(fm);
        tabTitles = ConstantUtils.ROUTE_TABS;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        Calendar cal = Calendar.getInstance();
        switch (i) {
            case 0:
                return Monday.newInstance();
            case 1:
                return Tuesday.newInstance();
            case 2:
                return Wednesday.newInstance();
            case 3:
                return Thursday.newInstance();
            case 4:
                return Friday.newInstance();
            case 5:
                return Saturday.newInstance();
        }
        return ScheduleFragment.newInstance(i + 1);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public Fragment setTabItemPerWeekDay(int position) {
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < position; i++) {
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                return Monday.newInstance();
            } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                return Tuesday.newInstance();
            }
        }
        return ScheduleFragment.newInstance(position + 1);
    }

}