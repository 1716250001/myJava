package demo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.ARPPacket;

public class Search {
	
	public ARPPacket make_packet(InetAddress targetIp) {
		
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		NetworkInterface device = null;
		// 寻找适合的网络设备
		loop: for (NetworkInterface d : devices) {
			for (NetworkInterfaceAddress addr : d.addresses) {
				if (!(addr.address instanceof Inet4Address)) {
					continue;
				}
				byte[] bip = targetIp.getAddress();//目的ip
				byte[] subnet = addr.subnet.getAddress();//子网掩码，等下运算用
				byte[] bif = addr.address.getAddress();//本地ip
				for (int i = 0; i < 4; i++) {
					bip[i] = (byte) (bip[i] & subnet[i]);
					bif[i] = (byte) (bif[i] & subnet[i]);
				}
				if (Arrays.equals(bip, bif)) {
					device = d;
					break loop;
				}
			}
		}
		if (device == null) {
			System.out.println("不是本地地址");
		}
		
		InetAddress srcip = null;
		for (NetworkInterfaceAddress addr : device.addresses) {
			if (addr.address instanceof Inet4Address) {
				srcip = addr.address;
				break;
			}
		}
		
		
		// 进行广播数据报的MAC地址
		byte[] broadcast = new byte[] { (byte) 255, (byte) 255, (byte) 255,
				(byte) 255, (byte) 255, (byte) 255 };
		
		//封装一个准备发送的ARP数据报
		Arp arp = new Arp();
		ARPPacket arp1 = arp.getArpPacket(broadcast, device.mac_address, device.mac_address, 
				srcip.getAddress(), broadcast, targetIp.getAddress());
		
		// 打开一个网络抓包类
		JpcapCaptor captor = null;
		try {
			captor = JpcapCaptor.openDevice(device, 2000, false, 300);
			// 只接收ARP数包，过滤出ARP数据报
			captor.setFilter("arp", true);
		} catch (Exception e) {

		}
		
		// 获得发送数据包的实例
		JpcapSender sender = captor.getJpcapSenderInstance();
		// 向局域网广播ARP请求数据报
		sender.sendPacket(arp1);

		// 接收目标主面的答应ARP数据报
		while (true) {
			ARPPacket p = (ARPPacket) captor.getPacket();// 接收返回包
			if (p == null) {
				System.out.println(targetIp + "不是本地局域网的IP号");
				return p;
			}else if(Arrays.equals(p.target_protoaddr, srcip.getAddress())) {
				return p;
			}
		}
		
	}
	
	public static void main(String[] args) throws SocketException, UnknownHostException {
		Network.shownetwork();
		System.out.println("***********************************");
		String s = "192.168.43.";
		
		for(int n = 1; n <= 254; n++) {
			Search ser = new Search();
			ARPPacket arpP = ser.make_packet(InetAddress.getByName(s+n));
			if (arpP == null) {
				
			}else {
				System.out.println("===================================");
				System.out.println("源 MAC 地址：" + arpP.getSenderHardwareAddress());
				System.out.println("源 IP 地址 ：" + arpP.getSenderProtocolAddress());
				System.out.println("目标 MAC 地址    " + arpP.getTargetHardwareAddress());
				System.out.println("目标 IP 地址     " + arpP.getTargetProtocolAddress());
				System.out.println("===================================");
			}
		}		
	}
}
