package com.example.figma;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewAdapter extends FragmentStateAdapter {

    public int mCount;

    public ViewAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new Frage_Class1a();
        else if(index==1) return new Frage_Class1b();
        else if(index==2) return new Frage_Class2a();
        else if(index==3) return new Frage_Class2b();
        else if(index==4) return new Frage_Class3a();
        else return new Frage_Class3b();
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public int getRealPosition(int position) { return position % mCount; }

}