package main;

import java.net.InetAddress;
import java.net.UnknownHostException;

import client.TablutClient;
import enums.PlayerKind;

public class TheQuaranteamMain {

	//		parametri: 
//		"white" or "black"
//		timeout in seconds
//		server ip
	public static void main(String[] args) {

		PlayerKind role=null;
		int timeOut=0;
		InetAddress serverIp=null;
		
		if (args.length < 3) {
			System.out.println("Usage: <white || black> <turn duration in seconds> <server ip>");
			System.exit(-1);
		}
		
		if (args[0].toLowerCase().equals("white") ) {
			role = PlayerKind.WHITE;
		}
		else if (args[0].toLowerCase().equals("black") ) {
			role = PlayerKind.BLACK;
		}
		else {
			System.out.println("Error in the parameters. Usage: <white || black> <turn duration in seconds> <server ip>");
			System.exit(-1);
		}
		
		
		if(Integer.parseInt(args[1]) > 0) {
			timeOut = Integer.parseInt(args[1]);
		}
		else {
			System.out.println("Error in the parameters. Turn duration must be greater than 0 seconds");
		}
		
		try {
			serverIp = InetAddress.getByName(args[2]);
		} catch (UnknownHostException e) {
			System.out.println("problem in the ip address");
			e.printStackTrace();
		}
		
		TablutClient client = new TablutClient(role, timeOut, serverIp);
		client.run();
	}
		
}