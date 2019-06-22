package com.aamer.assigment.heady.atopstories.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aamer.assigment.heady.atopstories.R;
import com.aamer.assigment.heady.atopstories.pojoclass.Stories;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private ArrayList<Stories> mItemsArrayList;


    public StoryAdapter(Context context, ArrayList<Stories> itemsList) {

        mContext = context;
        mItemsArrayList = itemsList;

    }
    @Override
    public int getItemCount() {
        return mItemsArrayList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storiesitems, parent, false);
        return new StoryAdapter.StoryHolder(view);
    }
    private class StoryHolder extends RecyclerView.ViewHolder {

        private TextView title,description;
        private ImageView url;


        StoryHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.txtTitle);
            description=itemView.findViewById(R.id.txtDescription);
            url=itemView.findViewById(R.id.story_image);

        }

        void bind(Stories rowStories) {

            Glide.with(mContext)
                    .load(rowStories.getUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .into(url);
            title.setText(rowStories.getTitle());
            description.setText(rowStories.getDescription());


        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Stories storyModel = mItemsArrayList.get(position);

        ((StoryAdapter.StoryHolder) holder).bind(storyModel);

    }
    public void insertNewLoadedStory(ArrayList<Stories> newStoryList) {
        int newListCount = newStoryList.size();
        int NewListItem = 0;
        int startOfNextIndex = mItemsArrayList.size();


        int loopSize = newListCount + mItemsArrayList.size();

        for (int i = startOfNextIndex; i < loopSize; i++) {

            if (NewListItem < newStoryList.size()) {
                mItemsArrayList.add(i, newStoryList.get(NewListItem));
                NewListItem++;
            }

        }
        notifyDataSetChanged();
    }
}