package application;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import comm.TCPConnection;
import events.OnInterfaceListener;
import events.OnRTTListener;
import events.OnRemoteIpConfigListener;
import events.OnSpeedListener;
import events.OnWhatTimeIsItListener;

public class App implements OnInterfaceListener, OnRemoteIpConfigListener, OnRTTListener, OnSpeedListener, OnWhatTimeIsItListener {

	
	private TCPConnection connection;
	
	
	
	public App() {	
		
		
		connection = new TCPConnection();
		
		connection.setInterfaceListener(this);
		
		connection.setIpListener(this);
		
		connection.setRttListener(this);
		
		connection.setSpeedListener(this);
		
		connection.setTimeListener(this);
		
	}
	
	public void init() {
		connection.run();
		
		while(true) {
			
		}
	}

	@Override
	public String onTime() {
		
		
		  Timestamp ts = Timestamp.from(ZonedDateTime.now().toInstant());
		  
		  
		 
		  //Instant instant = ts.toInstant();
		  
		  String msg = ts.toString();
		  
		  System.out.println(msg);
		  
		return msg;
	}

	@Override
	public String onSpeed(String msg) {
		
		if(msg.length()==8192) {
			
			return msg;
		}else {
			return "El Mensaje no cumple con los 8192 bytes requerbidos";
		}
		
		
	}

	@Override
	public String onRTT(String msg) {
		
		if(msg.length()==1024) {
			
			return msg;
		}else {
			return "El Mensaje no cumple con los 1024 bytes requerridos";
		}
		
	}

	@Override
	public String onIPConfig() {
	
		String ips = " ";
		try {
			ips = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		Enumeration<NetworkInterface> interfaces;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
			while(interfaces.hasMoreElements()) {
				 NetworkInterface netw = interfaces.nextElement();
  
				 
				 List<InterfaceAddress> list =  netw.getInterfaceAddresses();
				 for(int i=0; i < list.size();i++) {
					 
					 ips = list.get(i) + " \n";
					 
				 
				 }
				
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return ips;
		
	}

	@Override
	public String onInterface() {
		
		
		String it = "";
		try {
			
			Enumeration<NetworkInterface> interfaces;
			
			interfaces = NetworkInterface.getNetworkInterfaces();
			while(interfaces.hasMoreElements()) {
				NetworkInterface netN = interfaces.nextElement();
				
				if(netN.isUp()) {
					it += netN.getName();
					it += "\n";
					if(netN.getHardwareAddress()!=null) {
						
						String mac = new BigInteger(1,netN.getHardwareAddress()).toString(16);
						it += mac;
						it += "\n";
						
					}
				}
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Tiene que imprimir esto: " + it);
		return it;	
	}
	
	
	
	
}
