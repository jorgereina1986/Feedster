package jorgereina1986.c4q.nyc.feedster.models;

import java.util.List;

public class MusicData extends  CardData {

    private List<MusicItemData> musicItemDataList;

    public List<MusicItemData> getMusicItemDataList() {
        return musicItemDataList;
    }

    public void setMusicItemDataList(List<MusicItemData> musicItemDataList) {
        this.musicItemDataList = musicItemDataList;
    }
}