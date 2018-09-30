package e.deedcorpsinc.popularmovies.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Video implements Serializable {

    private String id;

    private String iso6391;

    private String iso31661;

    private String key;

    private String name;

    private String site;

    private Integer size;

    private String type;
    private ArrayList<Video> videoList;


    public Video() {
    }

    /**
     * @param site
     * @param iso6391
     * @param id
     * @param iso31661
     * @param name
     * @param type
     * @param key
     * @param size
     */
    public Video(String id, String iso6391, String iso31661, String key, String name, String site, Integer size, String type) {
        super();
        this.id = id;
        this.iso6391 = iso6391;
        this.iso31661 = iso31661;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }


    //Setters and Getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVideos(ArrayList<Video>videos){
        this.videoList= videos;
    }

    public ArrayList<Video> getVideoList() {
        return videoList;
    }
}
