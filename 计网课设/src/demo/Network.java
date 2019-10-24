package demo;

import java.util.Enumeration;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

//已完成
public class Network {
	
	public static void shownetwork() throws SocketException {
		
		/*
		 * 本段代码用于打印网卡
		 */
		String name = null;//连接名
		String ip = null;//本地ip
		
		Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
		
		while (enumeration.hasMoreElements()) {
			
			/*
			 * 此段函数为打印ip地址
			 */
			NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
			Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
		    while (addresses.hasMoreElements()) {
		        InetAddress addr = addresses.nextElement();

		        if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
		            name = networkInterface.getName();
		            ip = addr.getHostAddress();
		            System.out.println(name);
		            System.out.println(ip);
		        }
		    }
			
			/*
			 * 此段函数为将MAC地址从乱码转为十六进制表示法
			 */
			StringBuffer stringBuffer = new StringBuffer();
			byte[] bytes = networkInterface.getHardwareAddress();
			if (bytes != null) {
                for (int i = 0; i < bytes.length; i++) {
                    if (i != 0) {
                        stringBuffer.append("-");
                    }
                    int tmp = bytes[i] & 0xff; // 字节转换为整数
                    String str = Integer.toHexString(tmp);
                    if (str.length() == 1) {
                        stringBuffer.append("0" + str);
                    }else{
                        stringBuffer.append(str);
                    }
                }
                String mac = stringBuffer.toString().toUpperCase(); 
                System.out.println(mac);
			}
		}
	}
	
}
