import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Server_Receive extends Thread{
	
	private double avg_delay;
	private DatagramSocket serSocket;
	private DatagramPacket serPacket;
	private byte[] reData = new byte[1];
	private NextGaussian nxt_g;
	private double AVERAGE_DELAY, STD_DEV;
	
	public Server_Receive(DatagramSocket serSocket, DatagramPacket serPacket, 
			byte[] reData){															//Constructor
		this.serSocket = serSocket;
		this.serPacket = serPacket;
		this.reData = reData;
	}
	
	public void run(){
		AVERAGE_DELAY = 500;															//Average Delay passed to NextGaussian class.
		STD_DEV = 10;																	//Standard Deviation passed to NextGaussian class.
		int LOSS_RATE = 24;																//Loss Rate
		Random rand_loss = new Random(System.currentTimeMillis());						//Random number generator to compare with Loss rate
		String recData = new String(serPacket.getData());								//Received Data
		
		
		InetAddress IP = serPacket.getAddress();										//IP Address of the client.
		echo("Connected to Client");													
		echo("IP of Client: " + IP);
		int port = serPacket.getPort();													//Port of the client.
		echo("Port of Client: " + port);
		echo("Received: " + recData);
		
		int l = rand_loss.nextInt(100) + 1;												//Random number generated.	
		
		if(l>LOSS_RATE){																//If number generated is greater than Loss Rate
			nxt_g = new NextGaussian(AVERAGE_DELAY, STD_DEV);							//Next Gaussian found.
			avg_delay = nxt_g.generateGaussian();
			
			DatagramPacket serverReply = new DatagramPacket(reData, reData.length, IP, port);		//ServerReply packet to be sent		
			try {
				TimeUnit.MILLISECONDS.sleep((long) avg_delay);							//Sleeping for the generated delay.
			} catch (InterruptedException e1) {
				
			}
			echo("AVERAGE_DELAY is: "+String.format("%.3f", avg_delay));
			try {
				serSocket.send(serverReply);											//Sending the packet
			} catch (IOException e) {
							
			}
		}
		else echo("Packet lost");
	}

	public static void echo(String msg){
        System.out.println(msg);
    }
	
}