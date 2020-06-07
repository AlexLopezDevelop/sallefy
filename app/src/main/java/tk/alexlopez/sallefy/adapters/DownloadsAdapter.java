package tk.alexlopez.sallefy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.SavedTracks;
import tk.alexlopez.sallefy.models.Track;

public class DownloadsAdapter {
    private static final String TAG = "TrackListAdapter";
    private ArrayList<SavedTracks> mTracks;
    private Context mContext;


    public DownloadsAdapter(Context context, ArrayList<SavedTracks> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new DownloadsAdapter.ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        SavedTracks track = mTracks.get(position);

        holder.tvTitle.setText(track.getName());
        if (mTracks.get(position).getThumbnail() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    //.placeholder(R.drawable.ic_audiotrack) TODO: Change default image
                    .load(mTracks.get(position).getThumbnail())
                    .into(holder.ivPicture);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.track_title);
            tvAuthor = itemView.findViewById(R.id.track_author);
            ivPicture = itemView.findViewById(R.id.track_img);
        }
    }
}

