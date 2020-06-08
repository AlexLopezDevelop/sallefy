package tk.alexlopez.sallefy.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class SavedTracks {
    @Id
    public long id;
    public long id_song;
    public String name;
    public String urlFile;
    private String thumbnail;

    public SavedTracks(){}
    public SavedTracks(long id_song, String name, String urlFile, String thumbnail) {
        this.id_song = id_song;
        this.name = name;
        this.urlFile = urlFile;
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getId_song() {
        return id_song;
    }

    public void setId_song(long id_song) {
        this.id_song = id_song;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


