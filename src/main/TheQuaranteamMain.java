package main;

import client.TablutClient;

public class TheQuaranteamMain {

	public static void main(String[] args) {
		
		final String name = "TheQuaranteam";
		
		String role = args[0];
		String secTimeout = args[1];
		String serverIP = args[2];
		
		TablutClient client = new TablutClient(role, secTimeout, serverIP);
		
		client.run();
		
	}

}
