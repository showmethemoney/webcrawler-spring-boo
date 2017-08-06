package com.mycompany.enttity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity(name = "VIDEO")
@Indexed
@Analyzer(impl = org.apache.lucene.analysis.ja.JapaneseAnalyzer.class)
public class Video implements Serializable
{
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "ID")
	private String id = null;
	@Column(name = "AUTHOR")
	private String author = null;
	@Column(name = "CREAETD_DATE")
	private Date createdDate = null;
	@Column(name = "SERIAL")
	private String serial = null;
	@OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
	private List<Image> images = null;
	@Column(name = "TITLE")
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String title = null;
	@Column(name = "URL")
	private String url = null;

	public Video() {
		super();
	}

	public Video(String author, Date createdDate, String serial, List<Image> images, String title, String url) {
		super();
		this.author = author;
		this.createdDate = createdDate;
		this.serial = serial;
		this.images = images;
		this.title = title;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
