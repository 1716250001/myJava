package model;

public class Zhaopin {

	/*
	 * 这个类用来定义对招聘页面的粗略爬取，只爬取职位名称和链接，链接传给下一个爬虫类
	 */
	String zhiwei = null;//职位
	String gongzi = null;//工资
	String didian = null; //工作地点
	String gongsi = null;//工作单位
	String link = null;//职位链接
	
	static String baseurl = "https://job.csdn.net";
	
	public void set_zhiwei(String zhiwei) {
		this.zhiwei = zhiwei;
	}
	
	public void set_gongzi(String gongzi) {
		this.gongsi = gongzi;
	}
	
	public void set_didian(String didian) {
		this.didian = didian;
	}
	
	public void set_link(String link) {
		this.link = link;
	}
	
	public String get_gongzi() {
		return gongzi;
	}
	
	public String get_didian() {
		return didian;
	}
	
	public String get_gongsi() {
		return gongsi;
	}
	
	public String get_link() {
		return link;
	}
	
	
}
