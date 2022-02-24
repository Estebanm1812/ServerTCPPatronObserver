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

				} else if (msg.length()>=15 && msg.length()<5000) {
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
