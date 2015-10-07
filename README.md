# ping
A Server-Client implementation of ping as in Linux in Java

===================================
System Requirements
===================================
1. The System running the Application must have Java 8 installed.

===================================
Compilation
===================================
1. Extract the Assignment_1_ES13B1024.tar.gz file.
2. The contents of the folder will be extracted. One will be zip file that contains the project.
3. Open your favourite Java IDE and import this zip file if you want to run it from there.
4. Run the UDP_Server and UDP_Client either from your IDE.
5. If you are running it from terminal make sure of the directory and compile them using the following commands:
	Server Compile -> javac UDP_Server.java Server_Receive.java NextGaussian.java 
	Server Run     -> java UDP_Server 'port'     //Enter the port on which you want to open your server without quotes.

	Client Compile -> javac UDP_Client.java ReceiverThread.java
	Client run     -> java UDP_Client 'IP' 'port' //Enter the IP of your client and the port 
													of the server which you entered while running server without quotes.


===================================
Output of the Client and Server
===================================

Server:
-------
--The server outputs the IP and port of the connected client and it displays what packet it has received from the server. 
--If it fails to send the packet, Packet lost is outputted for convenience else the Delay for that packet is printed.

Client:
-------
--The Client outputs the IP and port of the server after sending each packet.
--In case it does not receive the packet, it says Packet lost and Timed out, else it displays the RTT for that packet.
--At the end the client prints all the ping statistics. i.e, Max/Min/Avg/SD and Loss Rate.
