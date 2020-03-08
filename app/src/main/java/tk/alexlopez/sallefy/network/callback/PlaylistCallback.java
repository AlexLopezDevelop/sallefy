package tk.alexlopez.sallefy.network.callback;

import java.util.List;

import tk.alexlopez.sallefy.models.Playlist;

public interface PlaylistCallback extends FailureCallback {
    void onPlaylistReceived(List<Playlist> playlists);
    void onNoPlaylists(Throwable throwable);
    void onPersonalPlaylists(List<Playlist> playlists);
    void onUserPlaylistsReceived(List<Playlist> playlists);
}
