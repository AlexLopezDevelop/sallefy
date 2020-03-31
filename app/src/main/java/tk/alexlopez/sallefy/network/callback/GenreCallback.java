package tk.alexlopez.sallefy.network.callback;

import java.util.ArrayList;

import tk.alexlopez.sallefy.models.Genre;
import tk.alexlopez.sallefy.models.Track;


public interface GenreCallback extends FailureCallback {

    void onGenresReceive(ArrayList<Genre> genres);
    void onTracksByGenre(ArrayList<Track> tracks);
}
