import java.net.*;
import java.util.*;


public class UDP_Server {
	
	public static void main(String[] args) throws Exception {
		if(args.length==1){
			DatagramSocket serverSocket;         				//The server Socket.
			int rec_port = Integer.parseInt(args[0]);								//The port on which the server is listening.
			serverSocket = new DatagramSocket(rec_port);		
			while(true){
				echo("\n");
				echo("Listening on port: "+ rec_port);
				byte[] receivedData = new byte[1];				//The buffer byte array where the received data is stored.
				DatagramPacket clientRequest = new DatagramPacket(receivedData, receivedData.length);		//Server Packet.
				serverSocket.receive(clientRequest);			//Server listening on specified port.
				Server_Receive cli_Thread = new Server_Receive(serverSocket, clientRequest, receivedData);
				cli_Thread.start();								//Server_Receive thread started.
			}
		}
		else {
			echo("Enter only port. See README");
		}
	}
	
	public static void echo(String msg){
        System.out.println(msg);
    }
}
