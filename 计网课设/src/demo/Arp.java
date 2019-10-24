package demo;

import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;

public class Arp {
	
	/*
	 * ARP���ݱ���
	 */
	public ARPPacket getArpPacket(byte[] dst_mac, byte[] src_mac, byte[] sender_hardaddr, 
			byte[] sender_protoaddr, byte[] target_hardaddr, byte[] target_protoaddr) {
		
		/*
		 * ����һ��ARP���ݱ�����Ҫ�����
		 */
		
		//��̫���ײ�
		EthernetPacket ether = new EthernetPacket();
		ether.dst_mac = dst_mac;//��̫��Ŀ�ĵ�ַ
		ether.src_mac = src_mac;//��̫��Դ��ַ
		ether.frametype = EthernetPacket.ETHERTYPE_ARP;//֡����
		
		// ARP���֣���̬���֣��ײ�
		ARPPacket arp = new ARPPacket();
		arp.hardtype = ARPPacket.HARDTYPE_ETHER;//Ӳ������
		arp.prototype = ARPPacket.PROTOTYPE_IP;//Э������
		arp.hlen = 6;//Ӳ����ַ����
		arp.plen = 4;//Э���ַ����
		arp.operation = ARPPacket.ARP_REQUEST;//��������,�ջ�
		
		// ��̬����
		arp.sender_hardaddr = sender_hardaddr;// ԴMAC��ַ
		arp.sender_protoaddr = sender_protoaddr;// ԴIP��ַ
		arp.target_hardaddr	= target_hardaddr;// Ŀ��MAC��ַ:�㲥��ַȫΪ1(������)
		arp.target_protoaddr = target_protoaddr;// Ŀ��IP��ַ
		
		// ARP���ݰ�����������ͷ��
		arp.datalink = ether;
		
		return arp;
	}
}
