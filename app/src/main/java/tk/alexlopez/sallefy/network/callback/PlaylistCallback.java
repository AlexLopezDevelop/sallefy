package tk.alexlopez.sallefy.network.callback;

import java.util.List;

import tk.alexlopez.sallefy.models.Playlist;

public interface PlaylistCallback {

    void onPlaylistCreated();
    void onPlaylistReceived(List<Playlist> tracks);

}
