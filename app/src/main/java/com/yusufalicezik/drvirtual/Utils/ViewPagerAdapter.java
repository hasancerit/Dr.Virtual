package com.yusufalicezik.drvirtual.Utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yusufalicezik.drvirtual.Login.FragmentDoktorGiris;
import com.yusufalicezik.drvirtual.Login.FragmentHastaGiris;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FragmentHastaGiris();
        }else{
            return new FragmentDoktorGiris();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

