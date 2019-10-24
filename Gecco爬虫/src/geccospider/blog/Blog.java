package geccospider.blog;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = "https://github.com/search?q=gecco",pipelines = "consolePipeline")
public class Blog implements HtmlBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Request
	private HttpRequest request;

	@Text(own=false)
	@HtmlField(cssPath="div.col-12 h3 a")
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void set_request(HttpRequest request) {
		this.request = request;
	}
	
	public HttpRequest get_request() {
		return request;
	}
	
	public static void main(String[] args) {
		
		GeccoEngine.create().classpath("geccospider.blog")
		.start("https://github.com/search?q=gecco").thread(2).interval(2000).start();
		
	}
}












