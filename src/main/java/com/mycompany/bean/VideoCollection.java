package com.mycompany.bean;

import java.io.Serializable;
import java.util.List;

import com.mycompany.enttity.Video;

public class VideoCollection implements Serializable
{
	private List<Video> videos = null;

	public VideoCollection() {
		super();
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
}
