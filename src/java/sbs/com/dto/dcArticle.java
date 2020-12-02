package sbs.com.dto;

public class dcArticle {
	public int id;
	public String title;
	public String writer;
	public String RegDate;
	
	public int hit;
	public String ipInfo;
	public String RegTime;
	public int recommand;
	@Override
	public String toString() {
		return "dcArticle [id=" + id + ", title=" + title + ", writer=" + writer + ", RegDate=" + RegDate + ", hit="
				+ hit + ", ipInfo=" + ipInfo + ", RegTime=" + RegTime + ", recommand=" + recommand + "]";
	}
}
