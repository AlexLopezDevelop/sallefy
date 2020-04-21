package tk.alexlopez.sallefy.network.callback;

import java.util.ArrayList;
import java.util.List;

import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Search;

public interface SearchCallback {
    void onTracksReceived(Search tracks);
}
