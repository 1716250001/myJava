package demo;

import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;

public class Arp {
	
	/*
	 * ARP数据报类
	 */
	public ARPPacket getArpPacket(byte[] dst_mac, byte[] src_mac, byte[] sender_hardaddr, 
			byte[] sender_protoaddr, byte[] target_hardaddr, byte[] target_protoaddr) {
		
		/*
		 * 构造一个ARP数据报所需要的组件
		 */
		
		//以太网首部
		EthernetPacket ether = new EthernetPacket();
		ether.dst_mac = dst_mac;//以太网目的地址
		ether.src_mac = src_mac;//以太网源地址
		ether.frametype = EthernetPacket.ETHERTYPE_ARP;//帧类型
		
		// ARP部分，静态部分，首部
		ARPPacket arp = new ARPPacket();
		arp.hardtype = ARPPacket.HARDTYPE_ETHER;//硬件类型
		arp.prototype = ARPPacket.PROTOTYPE_IP;//协议类型
		arp.hlen = 6;//硬件地址长度
		arp.plen = 4;//协议地址长度
		arp.operation = ARPPacket.ARP_REQUEST;//操作类型,收或发
		
		// 动态部分
		arp.sender_hardaddr = sender_hardaddr;// 源MAC地址
		arp.sender_protoaddr = sender_protoaddr;// 源IP地址
		arp.target_hardaddr	= target_hardaddr;// 目地MAC地址:广播地址全为1(二进制)
		arp.target_protoaddr = target_protoaddr;// 目地IP地址
		
		// ARP数据包加上以网关头部
		arp.datalink = ether;
		
		return arp;
	}
}
