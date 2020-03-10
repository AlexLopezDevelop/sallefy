package tk.alexlopez.sallefy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.Inet4Address;
import java.util.ArrayList;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.activities.AdvancedListActivity;
import tk.alexlopez.sallefy.activities.TrackListActivity;
import tk.alexlopez.sallefy.models.Playlist;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.ViewHolder> {

    private static final String TAG = "PlaylistsAdapter";
    private ArrayList<Playlist> mPlaylists;
    private Context mContext;

    public PlaylistsAdapter(Context context, ArrayList<Playlist> playlists) {
        mContext = context;
        mPlaylists = playlists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new PlaylistsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(mPlaylists.get(position).getName());
        holder.tvAuthor.setText(mPlaylists.get(position).getOwner().getFirstName());
        holder.idPlaylist = mPlaylists.get(position).getId();
        if (mPlaylists.get(position).getThumbnail() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .placeholder(R.drawable.ic_audiotrack)
                    .load(mPlaylists.get(position).getThumbnail())
                    .into(holder.ivPicture);
        }
    }

    @Override
    public int getItemCount() {
        return mPlaylists != null ? mPlaylists.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivPicture;
        int idPlaylist;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.playlist_title);
            tvAuthor = itemView.findViewById(R.id.playlist_author);
            ivPicture = itemView.findViewById(R.id.playlist_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AdvancedListActivity.class);
                    intent.putExtra("id", idPlaylist);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
