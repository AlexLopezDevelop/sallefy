package tk.alexlopez.sallefy.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.activities.TracksListActivity;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;

public class MyPlaylistsAdapter extends RecyclerView.Adapter<MyPlaylistsAdapter.ViewHolder>{

    private static final String TAG = "TrackListAdapter";
    private ArrayList<Playlist> mPlaylist;
    private Context mContext;

    public MyPlaylistsAdapter(Context context, ArrayList<Playlist> playlists) {
        mContext = context;
        mPlaylist = playlists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvTitle.setText(mPlaylist.get(position).getName());
        if (mPlaylist.get(position).getThumbnail() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .placeholder(R.drawable.ic_audiotrack)
                    .load(mPlaylist.get(position).getThumbnail())
                    .into(holder.ivPicture);
        }
        holder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                int playlistId = mPlaylist.get(position).getId();
                resultIntent.putExtra("idPlaylist", playlistId);
                ((Activity)mContext).setResult(Activity.RESULT_OK, resultIntent);
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaylist != null ? mPlaylist.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.track_title);
            ivPicture = (ImageView) itemView.findViewById(R.id.track_img);

        }
    }
}
