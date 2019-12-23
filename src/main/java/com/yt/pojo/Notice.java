package com.yt.pojo;


import java.util.Date;

/**
 * 公告表
 *
 * @author yt
 * @date 2019/12/14 - 22:42
 */
public class Notice {

	private Integer id;
	private Integer admin_id;
	private String content;
	private String title;
	private String author;
	private Date time;

	public Notice() {
	}

	public Notice(Integer id, Integer admin_id, String content, String title, String author, Date time) {
		this.id = id;
		this.admin_id = admin_id;
		this.content = content;
		this.title = title;
		this.author = author;
		this.time = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Notice{" + "id=" + id + ", admin_id=" + admin_id + ", content='" + content + '\'' + ", title='" + title + '\'' + ", author='" + author + '\'' + ", time=" + time + '}';
	}
}
