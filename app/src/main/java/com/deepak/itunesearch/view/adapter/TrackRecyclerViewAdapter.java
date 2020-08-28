package com.deepak.itunesearch.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.itunesearch.R;
import com.deepak.itunesearch.databinding.ItemListBinding;
import com.deepak.itunesearch.model.Track;
import com.deepak.itunesearch.model.TrackEvent;
import com.deepak.itunesearch.utility.UpdateCartCount;
import com.deepak.itunesearch.utility.Utility;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;


public class TrackRecyclerViewAdapter extends RecyclerView.Adapter<TrackRecyclerViewAdapter.TrackViewHolder> {

    private final Activity mActivity;
    private final ArrayList<Track> mValues;

    public TrackRecyclerViewAdapter(Activity activity, ArrayList<Track> items) {
        mActivity = activity;
        mValues = items;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding itemListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_list, parent, false);
        return new TrackViewHolder(itemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrackViewHolder holder, final int position) {
        Track track = mValues.get(position);

        // Bind value of track to this adapter UI
        holder.itemListBinding.setTrack(track);

        holder.itemListBinding.layoutItem.setOnClickListener(view -> {
            HashMap<Integer, Track> cartList = Utility.getTrackList();
            Log.d("TrackRecycler",""+cartList.size());
            if(cartList.containsKey(track.getTrackId())){
                cartList.remove(track.getTrackId());
                view.setBackgroundColor(Color.WHITE);
            }
            else{
                cartList.put(track.getTrackId(),track);
                view.setBackgroundColor(Color.LTGRAY);
            }

            Utility.setTrackList(cartList);
            ((UpdateCartCount)(mActivity)).updateCount(cartList.size());
            });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    class TrackViewHolder extends RecyclerView.ViewHolder {

        private ItemListBinding itemListBinding;

        public TrackViewHolder(@NonNull ItemListBinding mItemListBinding) {
            super(mItemListBinding.getRoot());

            this.itemListBinding = mItemListBinding;
        }
    }


}
