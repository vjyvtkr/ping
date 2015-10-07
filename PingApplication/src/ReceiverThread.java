import java.io.*;
import java.net.*;

public class ReceiverThread extends Thread{
	
	private DatagramSocket dataSocket;
	
	public ReceiverThread(DatagramSocket dataSocket){									//Constructor
		this.dataSocket = dataSocket;
	}
	
	public void run(){
		long now = System.currentTimeMillis();
		while(System.currentTimeMillis() - now < 1000){										//While loop for 1 sec.
			byte[] serverData = new byte[1]; 
			DatagramPacket serverReply = new DatagramPacket(serverData,serverData.length);  //Server Reply Packet.
			try{
				 dataSocket.receive(serverReply);											//Listening to server.
				 long end = System.nanoTime();												//Noting received time to calculate RTT.
				 String match = new String(serverReply.getData());
				 if(match.equals(UDP_Client.sequenceNumber)){								//If received packet matches the sequence number, accepted.
					 echo("Connected");														
					 UDP_Client.flag=1;														//Global flag is set.
					 String mod = new String("RTT: " + String.format("%.3f", ((end-UDP_Client.t1)/1000000.0)) + " ms");
					 echo(mod);
					 UDP_Client.RTT_List[UDP_Client.count] = ((end-UDP_Client.t1));
					 UDP_Client.count++;
					 UDP_Client.total+= (end-UDP_Client.t1);
					 if((end-UDP_Client.t1)>UDP_Client.max) UDP_Client.max = (end-UDP_Client.t1);
					 if((end-UDP_Client.t1)<UDP_Client.min) UDP_Client.min = (end-UDP_Client.t1);
				 }
				 else{
					 echo("Earlier Packet cannot be received");								//If not earlier packet cannot be received.
				 }
			}
			catch (IOException e) {
				
			}
		}
	}
	
	public static void echo(String msg){
        System.out.println(msg);
    }
}