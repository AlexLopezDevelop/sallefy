package tk.alexlopez.sallefy.models;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private ArrayList<Track> tracks = null;
    private List<Playlist> playlists = null;
    private List<User> users = null;

    public ArrayList<Track> getTracks() {
        return tracks;
    }
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<User> getUsers() { return users;  }
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
