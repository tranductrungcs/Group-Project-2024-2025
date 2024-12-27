package com.example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class VideoViewPagerAdapter extends FragmentStatePagerAdapter {
    public VideoViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default: return new VideoAppleFragment();
            case 1: return new VideoSamsungFragment();
            case 2: return new VideoHuaweiFragment();
            case 3: return new VideoXiaomiFragment();
            case 4: return new VideoMicrosoftFragment();
            case 5: return new VideoAsusFragment();
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            default: return "Apple";
            case 1: return "Samsung";
            case 2: return "Huawei";
            case 3: return "Xiaomi";
            case 4: return "Microsoft";
            case 5: return "Asus";
        }
    }
}
