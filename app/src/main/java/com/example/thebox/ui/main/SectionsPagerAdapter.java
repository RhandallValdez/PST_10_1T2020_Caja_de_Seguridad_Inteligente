package com.example.thebox.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.thebox.AccesosFragment;
import com.example.thebox.PermisosFragment;
import com.example.thebox.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment= new AccesosFragment();
                break;
            case 1:
                fragment = new PermisosFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence text="";
        switch(position){
            case 0:
                text= "Ver accesos";
                break;
            case 1:
                text= "Manejar permisos";
                break;
        }
        return text;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}