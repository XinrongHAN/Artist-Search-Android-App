package com.example.artsyapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
;
    private final ArrayList<Fragment> fragmentslist = new ArrayList<>();
    private final ArrayList<String> fragementstitle = new ArrayList<>();
    int[] imglist = {R.drawable.ic_info,R.drawable.ic_paint};
    private Context mcontext;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior){
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentslist.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void addFragment(Fragment fragment,String title){
        fragmentslist.add(fragment);
        fragementstitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragementstitle.get(position);
    }
}
