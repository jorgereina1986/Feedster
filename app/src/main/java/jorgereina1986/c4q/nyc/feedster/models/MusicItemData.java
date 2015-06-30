package jorgereina1986.c4q.nyc.feedster.models;


/**
 * Created by c4q-jorgereina on 6/30/15.
 */
public class MusicItemData {

    private String title;
    private String thumbnailUrl;
    private String artist;



    public MusicItemData() {
    }

    public MusicItemData(String name, String thumbnailUrl, String artist) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


}

