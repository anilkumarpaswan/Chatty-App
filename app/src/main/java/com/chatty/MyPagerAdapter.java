package com.chatty;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Deepak Rattan on 31-Dec-16.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public MyPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentRegisterUser tab1 = new FragmentRegisterUser();
                return tab1;
            case 1:
                FragmentAddFriend tab2 = new FragmentAddFriend();
                return tab2;
            case 2:
                FragmentFriends tab3 = new FragmentFriends ();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
