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
		// Ѱ���ʺϵ������豸
		loop: for (NetworkInterface d : devices) {
			for (NetworkInterfaceAddress addr : d.addresses) {
				if (!(addr.address instanceof Inet4Address)) {
					continue;
				}
				byte[] bip = targetIp.getAddress();//Ŀ��ip
				byte[] subnet = addr.subnet.getAddress();//�������룬����������
				byte[] bif = addr.address.getAddress();//����ip
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
			System.out.println("���Ǳ��ص�ַ");
		}
		
		InetAddress srcip = null;
		for (NetworkInterfaceAddress addr : device.addresses) {
			if (addr.address instanceof Inet4Address) {
				srcip = addr.address;
				break;
			}
		}
		
		
		// ���й㲥���ݱ���MAC��ַ
		byte[] broadcast = new byte[] { (byte) 255, (byte) 255, (byte) 255,
				(byte) 255, (byte) 255, (byte) 255 };
		
		//��װһ��׼�����͵�ARP���ݱ�
		Arp arp = new Arp();
		ARPPacket arp1 = arp.getArpPacket(broadcast, device.mac_address, device.mac_address, 
				srcip.getAddress(), broadcast, targetIp.getAddress());
		
		// ��һ������ץ����
		JpcapCaptor captor = null;
		try {
			captor = JpcapCaptor.openDevice(device, 2000, false, 300);
			// ֻ����ARP���������˳�ARP���ݱ�
			captor.setFilter("arp", true);
		} catch (Exception e) {

		}
		
		// ��÷������ݰ���ʵ��
		JpcapSender sender = captor.getJpcapSenderInstance();
		// ��������㲥ARP�������ݱ�
		sender.sendPacket(arp1);

		// ����Ŀ������Ĵ�ӦARP���ݱ�
		while (true) {
			ARPPacket p = (ARPPacket) captor.getPacket();// ���շ��ذ�
			if (p == null) {
				System.out.println(targetIp + "���Ǳ��ؾ�������IP��");
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
				System.out.println("Դ MAC ��ַ��" + arpP.getSenderHardwareAddress());
				System.out.println("Դ IP ��ַ ��" + arpP.getSenderProtocolAddress());
				System.out.println("Ŀ�� MAC ��ַ    " + arpP.getTargetHardwareAddress());
				System.out.println("Ŀ�� IP ��ַ     " + arpP.getTargetProtocolAddress());
				System.out.println("===================================");
			}
		}		
	}
}
