package com.example.smrp.medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.smrp.R;

public class ViewPagerAdapter extends PagerAdapter {


    private int[] images;
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter(Context context, int []images)
    {
        this.images = images;
        this.context = context;

    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout)o);
    }
    @Override
    public @NonNull Object instantiateItem(@NonNull ViewGroup contatiner, int position)
    {
        inflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View v = inflater.inflate(R.layout.pager, contatiner, false);
        ImageView imageView = v.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        contatiner.addView(v);
        return v;
    }
}