package tk.alexlopez.sallefy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.activities.DownloadSongActivity;
import tk.alexlopez.sallefy.activities.PlayDownloadTrackActivity;
import tk.alexlopez.sallefy.activities.PlayTrackActivity;
import tk.alexlopez.sallefy.activities.TracksListActivity;
import tk.alexlopez.sallefy.models.SavedTracks;
import tk.alexlopez.sallefy.models.Track;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.ViewHolder>  {
    private static final String TAG = "DownloadAdapter";
    private ArrayList<SavedTracks> mTracks;
    private Context mContext;

    public DownloadsAdapter(Context context, ArrayList<SavedTracks> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new DownloadsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedTracks track = mTracks.get(position);
        holder.tvTitle.setText(track.getName());

        if (mTracks.get(position).getThumbnail() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(mTracks.get(position).getThumbnail())
                    .into(holder.ivPicture);
        }
        holder.rlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                SavedTracks mTrack = mTracks.get(position);
                Intent intent = new Intent(mContext.getApplicationContext(), PlayDownloadTrackActivity.class);
                bundle.putSerializable("trackData",  mTrack);
                bundle.putSerializable("playlist", mTracks);
                intent.putExtra("trackData",mTrack);
                intent.putExtra("playlist",mTracks);
                //intent.putExtra("id", playlistId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivPicture;
        RelativeLayout rlLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.track_title);
            ivPicture = itemView.findViewById(R.id.track_img);
            rlLayout = itemView.findViewById(R.id.track_layout);
        }
    }
}

