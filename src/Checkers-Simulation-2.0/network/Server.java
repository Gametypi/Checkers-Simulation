package network;
import java.io.IOException;

import java.net.Inet4Address;
import java.net.ServerSocket;
/**
 * The class that hosts a game 
 */
public class Server extends Connector {

	public Server(NetworkManager manager,int port, int clientLimit) {
		super(manager);
		try {
			ServerSocket server = new ServerSocket(port);
			manager.console.printInfo("Your IP: " + Inet4Address.getLocalHost().getHostAddress(), "Server");
			establishingConnection(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void establishingConnection(ServerSocket server) throws IOException {
		server.setSoTimeout(600000000);
		try	{
			manager.console.printInfo("Wait for Clients...", "Server");
			connection = server.accept();
			manager.console.printInfo("Connection is established. Now you can write and receive textmessages on the console.", "Server");
			receiveInfo();
			//sendInfo(new Info(InfoType.CONNECTIONINFO, manager.getUser()));
		}
		catch(Exception e) {
			e.printStackTrace();
			manager.console.printInfo("Could not establish a connection", "sERVER");
		}
	}
}