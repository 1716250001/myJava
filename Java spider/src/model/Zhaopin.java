package model;

public class Zhaopin {

	/*
	 * ����������������Ƹҳ��Ĵ�����ȡ��ֻ��ȡְλ���ƺ����ӣ����Ӵ�����һ��������
	 */
	String zhiwei = null;//ְλ
	String gongzi = null;//����
	String didian = null; //�����ص�
	String gongsi = null;//������λ
	String link = null;//ְλ����
	
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
