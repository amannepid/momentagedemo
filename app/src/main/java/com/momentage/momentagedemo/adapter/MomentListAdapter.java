package com.momentage.momentagedemo.adapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.momentage.momentagedemo.MomentageApplication;
import com.momentage.momentagedemo.R;
import com.momentage.momentagedemo.data.Moment;
import com.momentage.momentagedemo.utils.CircularImageView;

import java.util.List;

public class MomentListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Moment> momentItems;
    ImageLoader imageLoader = MomentageApplication.getAppInstance().getImageLoader();

    public MomentListAdapter(Activity activity, List<Moment> momentItems) {
        this.activity = activity;
        this.momentItems = momentItems;
    }

    @Override
    public int getCount() {
        return momentItems.size();
    }

    @Override
    public Object getItem(int position) {
        return momentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item_moment, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.rlContentHolder = (RelativeLayout) rowView.findViewById(R.id.rlContentHolder);
            viewHolder.ivBackground = (NetworkImageView) rowView.findViewById(R.id.ivBackground);
            viewHolder.ivAvatar = (CircularImageView) rowView.findViewById(R.id.ivAvatar);
            viewHolder.tvUserName = (TextView) rowView.findViewById(R.id.tvUserName);
            viewHolder.tvPhoto = (TextView) rowView.findViewById(R.id.tvPhoto);
            viewHolder.tvVideo = (TextView) rowView.findViewById(R.id.tvVideo);
            viewHolder.tvAudio = (TextView) rowView.findViewById(R.id.tvAudio);
            viewHolder.tvMomentTitle = (TextView) rowView.findViewById(R.id.tvMomentTitle);

            rowView.setTag(viewHolder);
        }

        Moment moment = momentItems.get(position);

        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.rlContentHolder.getLayoutParams().height = getScreenWidth();

        holder.ivAvatar.setImageUrl(moment.getProfilePicURL(), imageLoader);

        holder.ivBackground.getLayoutParams().height = getScreenWidth();
        holder.ivBackground.getLayoutParams().width = getScreenWidth();
        holder.ivBackground.setImageUrl(moment.getBackgroundURL(), imageLoader);

        holder.tvUserName.setText(moment.getUserName().trim());
        holder.tvPhoto.setText(moment.getPhotoCount() + "");
        holder.tvVideo.setText(moment.getVideoCount() + "");
        holder.tvAudio.setText(moment.getAudioCount() + "");
        holder.tvMomentTitle.setText(moment.getMomentTitle());

        return rowView;
    }

    static class ViewHolder {
        NetworkImageView ivBackground;
        CircularImageView ivAvatar;
        RelativeLayout rlContentHolder;
        TextView tvUserName;
        TextView tvPhoto;
        TextView tvVideo;
        TextView tvAudio;
        TextView tvMomentTitle;
    }

    private int getScreenWidth(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }
}
