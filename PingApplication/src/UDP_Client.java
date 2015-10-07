import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class UDP_Client {

	public static int flag;												//All static variables defined and initialized if needed.
	public static int count=0;											//Number of packets received
	public static long total = 0;
	public static long max = -1;
	public static long min = 1000000000; 
	public static long t1;
	public static String sequenceNumber;								//Sequence Number
	public static long[] RTT_List = new long[10];						//List of RTT
	
	public static void main(String[] args) throws Exception{
		
		ReceiverThread rt;
		DatagramPacket serverRequest;									//Datagram packet
		DatagramSocket clientSocket;									//Datagram Socket
		
		byte[] toSendData = new byte[1];								//Buffer set
		int i;
		
		clientSocket = new DatagramSocket();									//Input from user
		
		if(args.length==2){
			for(i=0; i<10; i++){	
				echo("\n");
				flag=0;
				InetAddress IP = InetAddress.getByName(args[0]);
				int por = Integer.parseInt(args[1]);
				sequenceNumber = String.valueOf(i);							//Sequence Number set.
				echo("Connecting to " + IP);								
				toSendData = sequenceNumber.getBytes();						
				serverRequest = new DatagramPacket(toSendData, toSendData.length, IP, por);	//Datagram Packet started.
				t1 = System.nanoTime();										//Time before sending packet noted.
				try{
					clientSocket.send(serverRequest);							//Packet Sent.
					rt = new ReceiverThread(clientSocket);						//Receiver Thread started.
					rt.start();
					TimeUnit.SECONDS.sleep(1);									//Main class sleeps for 1 second after thread is started.
					if(flag==0) echo("Timed Out. Packet Lost.");				//Flag to know whether packet is lost or not.
					echo("\n");
				}
				catch(IOException e){
					echo("Couldnt send to server");
				}
			}
			echo("\n");
			clientSocket.close();											//Client Socket closed.
			if(count>0) cal_Parameters();									//Division by zero checked.
			else echo("All packets lost.");
		}
		else {
			echo("Please enter IP and Port. See README");
		}
	}
	
	public static void echo(String msg){
        System.out.println(msg);
    }
	
	public static void cal_Parameters(){										//Calculating the parameters using cal_Parameters() method.
		double tot = total/1000000.0;											//total RTT
		double avg = (total/(count*1000000.0));									//Average RTT
		double mi = min/1000000.0;												//Min RTT
		double ma = max/1000000.0;												//Max RTT
		echo("Ping Statitics");
		System.out.print("Max/Min/Avg/SD: "+String.format("%.3f", ma)+"/"+ String.format("%.3f", mi)+"/"+String.format("%.3f", avg)+"/");
		double sum=0;
		for(int j=0; j<count; j++){
			sum+=(((double)(RTT_List[j])-avg)*((double)(RTT_List[j])-avg));
		}
		sum=sum/count;
		double sd = (Math.sqrt(sum)/10000000.0);
		System.out.print(String.format("%.3f", sd)+" ms");
		//echo("Standard Deviation: "+String.format("%.3f", sd)+" ms");			//Standard Deviation
		
		echo("\nNumber of packets lost: "+(10-count));
		echo("Number of packets sent: 10");
		echo("Loss Rate = "+((10-count)*10)+"%");								//Loss Rate
	}
}
