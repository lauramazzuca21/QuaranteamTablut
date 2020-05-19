package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Function;

import com.google.gson.Gson;

import enums.GameState;
import enums.PlayerKind;
import utils.StreamUtils;
import domain.AiPlayer;
import domain.Move;
import domain.Pair;
import domain.Player;
import domain.State;

import ai.IterativeDeepeningSearch;

public class TablutClient extends Client implements Runnable {
	private final int MY_PORT = 9595;
	public static int WHITEPORT = 5800;
	public static int BLACKPORT = 5801;
	PlayerKind role;
	int timeOut;
	InetAddress ip;
	State state;
	Player player;
	Gson gson;
	Socket playerSocket;
	DataOutputStream out;
	DataInputStream in;

	
	public TablutClient(PlayerKind role, int timeOut, InetAddress ip) {
		this.role = role;
		this.timeOut = timeOut;
		this.ip = ip;
			
		try {
			playerSocket = new Socket(InetAddress.getLocalHost(), MY_PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			out = new DataOutputStream(playerSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in = new DataInputStream(playerSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void run() {
		
		Function<Pair<Long, Long>, Boolean> f = pair -> {
			if((pair.getFirst() - pair.getSecond()) >= timeOut) 
				return true; 
			else return false;
		};
		if (role == PlayerKind.WHITE) {
			player = new AiPlayer("TheQuaranteam", PlayerKind.WHITE, null, new IterativeDeepeningSearch(3, 11, f));
		}
		else if (role == PlayerKind.BLACK){
			player = new AiPlayer("TheQuaranteam", PlayerKind.BLACK, null, new IterativeDeepeningSearch(3, 11, f));
		}
		else {
			System.exit(2);
		}
		
		
		while(true) {
			try {
				state = readState(in);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			if(state.getGameState()==GameState.PLAYING) {
				System.out.println(state.toString());
				Move newMove = player.getNextMove(state);
				
				sendMove(newMove, out);
			}
			else if(state.getGameState()==GameState.LOSE && state.getTurnOf() != role){ //VINTO
				System.out.println(state.toString());
				System.out.println("I Won!");
			}
			else if(state.getGameState()==GameState.LOSE && state.getTurnOf() == role){ //PERSO
				System.out.println(state.toString());
				System.out.println("I Lost!");	
			}
			else if(state.getGameState()==GameState.DRAW){
				System.out.println(state.toString());
				System.out.println("I Drawed!");
			}
		}
	}
	
	
	
	private void sendMove(Move moveToSend, DataOutputStream out) {
		try {
			StreamUtils.writeString(out, moveToSend.toJson());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public State readState(DataInputStream in) throws ClassNotFoundException, IOException {
		String inputString = StreamUtils.readString(in);
		gson = new Gson();
		State myState = gson.fromJson(inputString, State.class);
		return myState;
	}


}

