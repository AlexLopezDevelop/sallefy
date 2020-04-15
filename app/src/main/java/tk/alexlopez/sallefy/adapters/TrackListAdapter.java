package tk.alexlopez.sallefy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.activities.TrackOptionsActivity;
import tk.alexlopez.sallefy.models.Track;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {

    private static final String TAG = "TrackListAdapter";
    private ArrayList<Track> mTracks;
    private Context mContext;


    public TrackListAdapter(Context context, ArrayList<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new TrackListAdapter.ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Track track = mTracks.get(position);

        holder.tvTitle.setText(track.getName());
        holder.tvAuthor.setText(track.getUserLogin());
        if (track.getThumbnail() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    //.placeholder(R.drawable.ic_audiotrack) TODO: Change default image
                    .load(mTracks.get(position).getThumbnail())
                    .into(holder.ivPicture);
        }

        holder.ibOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), TrackOptionsActivity.class);
                // TODO: send song id
                intent.putExtra("id", track.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size():0;
    }

    public void updateTrackLikeStateIcon(int position, boolean isLiked) {
        mTracks.get(position).setLiked(isLiked);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivPicture;
        ImageButton ibOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.track_title);
            tvAuthor = itemView.findViewById(R.id.track_author);
            ivPicture = itemView.findViewById(R.id.track_img);
            ibOptions = itemView.findViewById(R.id.track_options);
        }
    }
}
