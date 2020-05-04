package tk.alexlopez.sallefy.network.callback;

import java.util.List;

import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;

public interface TrackCallback extends FailureCallback {
    void onTracksReceived(List<Track> tracks);
    void onNoTracks(Throwable throwable);
    void onPersonalTracksReceived(List<Track> tracks);
    void onUserTracksReceived(List<Track> tracks);
    void onCreateTrack();

    void onTrackSelected(Track track);

    void onTrackSelected(int index);
    void onTracksReceivedByPlaylistId(Playlist playlist);

    void onNoLikedTrack(Boolean response);
    void onLikedTrack(Boolean response);
}
