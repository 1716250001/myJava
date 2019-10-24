package main;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Bigspider {
	
	static String baseurl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html";
	
	public static boolean geturl(String baseurl) {
		
		boolean flag = true;
		
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		
		String responsestr = "";
		try {
			
			HttpGet httpGet = new HttpGet(baseurl);
			httpGet.setConfig(RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(1000).build());
			
			httpClient = HttpClientBuilder.create().build();
			HttpClientContext context = HttpClientContext.create();
			response = httpClient.execute(httpGet,context);
			HttpEntity entity = response.getEntity();
			responsestr = EntityUtils.toString(entity, "utf-8");
			int status = response.getStatusLine().getStatusCode();
			System.out.println(status);
			System.out.println(responsestr);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
		}finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		boolean flag = false;
		flag = Bigspider.geturl(baseurl);
		System.out.println(flag);
	}
}
