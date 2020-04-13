package tk.alexlopez.sallefy.network.callback;

import java.util.List;

import tk.alexlopez.sallefy.models.Playlist;

public interface MeCallback {

    void myPlaylistReceived(List<Playlist> tracks);
    void myFollowingPlaylistReceived(List<Playlist> tracks);

}
