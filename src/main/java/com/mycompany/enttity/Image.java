package com.mycompany.enttity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "IMAGE")
public class Image implements Serializable
{
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "ID")
	private String id = null;
	@Column(name = "HEIGH")
	private int height = 0;
	@Column(name = "THUNMBNAMIL_URL")
	private String thumbnailUrl = null;
	@Column(name = "URL")
	private String url = null;
	@Column(name = "WIDTH")
	private int width = 0;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VIDEO_ID")
	private Video video = null;

	public Image() {
		super();
	}

	public Image(int height, String thumbnailUrl, String url, int width) {
		super();
		this.height = height;
		this.thumbnailUrl = thumbnailUrl;
		this.url = url;
		this.width = width;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
