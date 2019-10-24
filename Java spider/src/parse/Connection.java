package parse;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Connection {

	public  Document geturl(String url) throws IOException {
		
		try {
			
			return Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)").timeout(5000).get();
			
		} catch (SocketTimeoutException s) {
			
			// 超时就重复抓取，jsoup的connect方法有可能出问题
			return Jsoup.connect(url).
					userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)").timeout(5000).get();
		}
		
	}
	
}
