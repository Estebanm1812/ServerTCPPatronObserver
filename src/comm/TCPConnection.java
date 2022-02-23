package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import application.App;
import events.OnInterfaceListener;
import events.OnRTTListener;
import events.OnRemoteIpConfigListener;
import events.OnSpeedListener;
import events.OnWhatTimeIsItListener;

public class TCPConnection extends Thread {

	private Socket socket;

	private ServerSocket server;

	private OnInterfaceListener interfaceListener;

	private OnRemoteIpConfigListener ipListener;

	private OnRTTListener rttListener;

	private OnSpeedListener speedListener;

	private OnWhatTimeIsItListener timeListener;

	@Override
	public void run() {
		try {
			server = new ServerSocket(6000);
			while (true) {

				 System.out.println("Esperando en el puerto 6000");
				socket = server.accept();
				 System.out.println("Conectado ...");

				InputStream is = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				OutputStream os = socket.getOutputStream();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

				String msg = br.readLine();
				/*
				 * if(msg ==null) {
				 * 
				 * } else {
				 */
				// String msg2 = " ";
				if (msg.equals("remoteipconfig")) {

					msg = ipListener.onIPConfig();

				} else if (msg.equals("interface")) {

					msg = interfaceListener.onInterface();

				} else if (msg.equals("WhatTimeIsIt")) {

					msg = timeListener.onTime();

				} else if (msg.equals(
						"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit ve")) {

					msg = rttListener.onRTT(msg);
					// System.out.println(msg);

				} else {

					msg = speedListener.onSpeed(msg);

				}
				bw.write(msg + "\n");
				bw.flush();
				
				

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setInterfaceListener(OnInterfaceListener interfaceListener) {
		this.interfaceListener = interfaceListener;
	}

	public void setIpListener(OnRemoteIpConfigListener ipListener) {
		this.ipListener = ipListener;
	}

	public void setRttListener(OnRTTListener rttListener) {
		this.rttListener = rttListener;
	}

	public void setSpeedListener(OnSpeedListener speedListener) {
		this.speedListener = speedListener;
	}

	public void setTimeListener(OnWhatTimeIsItListener timeListener) {
		this.timeListener = timeListener;
	}

}
