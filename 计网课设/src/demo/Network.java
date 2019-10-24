package demo;

import java.util.Enumeration;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

//�����
public class Network {
	
	public static void shownetwork() throws SocketException {
		
		/*
		 * ���δ������ڴ�ӡ����
		 */
		String name = null;//������
		String ip = null;//����ip
		
		Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
		
		while (enumeration.hasMoreElements()) {
			
			/*
			 * �˶κ���Ϊ��ӡip��ַ
			 */
			NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
			Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
		    while (addresses.hasMoreElements()) {
		        InetAddress addr = addresses.nextElement();

		        if (addr instanceof Inet4Address) { // ֻ���� IPv4 ��ַ
		            name = networkInterface.getName();
		            ip = addr.getHostAddress();
		            System.out.println(name);
		            System.out.println(ip);
		        }
		    }
			
			/*
			 * �˶κ���Ϊ��MAC��ַ������תΪʮ�����Ʊ�ʾ��
			 */
			StringBuffer stringBuffer = new StringBuffer();
			byte[] bytes = networkInterface.getHardwareAddress();
			if (bytes != null) {
                for (int i = 0; i < bytes.length; i++) {
                    if (i != 0) {
                        stringBuffer.append("-");
                    }
                    int tmp = bytes[i] & 0xff; // �ֽ�ת��Ϊ����
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
