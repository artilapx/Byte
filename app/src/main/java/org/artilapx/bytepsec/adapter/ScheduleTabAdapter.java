package org.artilapx.bytepsec.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.artilapx.bytepsec.fragment.TestFragment;
import org.artilapx.bytepsec.fragment.ScheduleFragment;
import org.artilapx.bytepsec.utils.ConstantUtils;

public class ScheduleTabAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles;

    public ScheduleTabAdapter(FragmentManager fm) {
        super(fm);
        tabTitles = ConstantUtils.ROUTE_TABS;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                TestFragment.newInstance();
            case 1:
                TestFragment.newInstance();
            case 2:
                TestFragment.newInstance();
        }
        return ScheduleFragment.newInstance(i);
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
}