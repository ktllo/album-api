package org.leolo.album.model;

import java.util.HashMap;
import java.util.Map;

public class Album {

	public Album(String albumId) {
		this.albumId = albumId;
	}
	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Album.class);
	
	private String albumId;
	private String ownerName;
	private String name;
	private String description;
	private String thumbnail;
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getAlbumId() {
		return albumId;
	}
	
	public Map<String, Object> toJSONMap(){
		Map<String, Object> map = new HashMap<>();
		map.put("id", albumId);
		map.put("owner", ownerName);
		map.put("name", name);
		map.put("description", description);
		map.put("thumbnailId", thumbnail);
		return map;
	}
}
