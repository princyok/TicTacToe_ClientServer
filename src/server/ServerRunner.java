package server;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import server.MainWindow;
import server.controller.ServerController;
import server.model.Game;

public class ServerRunner 
{

	public ServerSocket gameServerSocket;

	public Socket socketForClient1, socketForBroadcastingToClient1, 
	socketForClient2, socketForBroadcastingToClient2;

	public ServerController gameController;
	
	MainWindow serverWindow;


	//INITIALIZER METHODS.

	public ServerRunner() throws IOException
	{
		gameServerSocket = new ServerSocket(7876);
		
		EventQueue.invokeLater
		(
			new Runnable() 
			{	@Override
				public void run() 
				{
					try
					{
						serverWindow = new MainWindow();
					}
					catch (Exception e) 
					{
						JOptionPane.showMessageDialog(null, "An error occured. The program will now exit.");
					}
				}
			}
		);
	}

	/**
	 * Instantiates the game and the controller for the game,
	 * and establishes the connections between the controller and the clients.
	 * The instances complete the initialization of the ServerRunner object.
	 * This method blocks until connections are established with two clients.
	 * @throws IOException
	 */
	public void startAGame() throws IOException
	{
		Game theGame = new Game();

		for (int i=0; i<2;++i)
		{
			if (i==0)
			{
				socketForClient1= acceptConnectionForClient(i);
				socketForBroadcastingToClient1 = acceptConnectionForBroadcastingToClient(i);
			}
			if (i==1)
			{
				socketForClient2= acceptConnectionForClient(i);
				socketForBroadcastingToClient2 = acceptConnectionForBroadcastingToClient(i);
			}
		}

		gameController=new ServerController(theGame, 
				socketForClient1, socketForBroadcastingToClient1, socketForClient2,
				socketForBroadcastingToClient2);
	}

	// ENTRY-POINT.

	public static void main(String[] args) 
	{		
		try
		{
			ServerRunner ServerRunner = new ServerRunner();
			ExecutorService myThreadPool = Executors.newFixedThreadPool(3);

//			System.out.print(new Timestamp(System.currentTimeMillis()));
//			System.out.println(" Sever is running.");

			while(true)
			{
				ServerRunner.startAGame();
				myThreadPool.execute(ServerRunner.gameController);
			}	
		}
		catch(IOException e) 
		{
			e.getStackTrace();
			JOptionPane.showMessageDialog(null, "An Input-Output error occured. The program will now exit.");
		}
	}

	//HELPER METHODS.

	/**
	 * Accepts connection from the client. This connection is dedicated for 
	 * communications that are meant for only the client.
	 * @param i
	 * @return a socket that is used for communications.
	 * @throws IOException
	 */
	private  Socket acceptConnectionForClient(int i) throws IOException
	{
		Socket socketForClient= gameServerSocket.accept();
		
		serverWindow.printToDisplay((new Timestamp(System.currentTimeMillis())).toString());
		serverWindow.printToDisplay(" Client"+i+" Accepted.");

		return socketForClient;
	}

	/**
	 * Accepts connection from the client. This connection is dedicated for 
	 * communications that are meant for all clients.
	 * @param i
	 * @return a socket that is used for communications.
	 * @throws IOException
	 */
	private Socket acceptConnectionForBroadcastingToClient(int i) throws IOException
	{
		Socket socketForBroadcastingToClient= gameServerSocket.accept();
		serverWindow.printToDisplay((new Timestamp(System.currentTimeMillis())).toString());
		serverWindow.printToDisplay(" Client Broadcaster"+i+" accepted connection.");
		return socketForBroadcastingToClient;
	}
}
