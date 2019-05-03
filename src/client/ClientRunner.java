package client;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

import client.view.MainWindow;
import client.controller.MainController_Client;

/**
 * Has two crucial attributes, which are two sockets that 
 * are connected to the server: 
 * <br><br>
 * <strong>socketForGame</strong> (composition): 
 * this socket manages all communications to the server, and responses to those communications if any.
 * <br>
 * <strong>socketForBroadcastFromGame</strong> (composition): 
 * this socket manages all broadcasts from the server.
 * <br><br>
 * Has the entry point (the "main" static method) of the client side.
 * @author prince
 *
 */
public class ClientRunner 
{
	// ATTRIBUTES.

	public Socket socketForGame, socketForBroadcastFromGame;

	// ENTRY POINT.

	/**
	 * Entry point of the client-side. Creates a client- 
	 * runner (type ClientRunner) and runs the client.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater
		(
			new Runnable() 
			{
				public void run() 
				{
					try 
					{	

						ClientRunner clientRunner= new ClientRunner();

						clientRunner.runClient();
					} 
					catch (Exception e) 
					{
						e.getStackTrace();
						JOptionPane.showMessageDialog(null, "An error occured. The program will now exit.");
					}
				}
			}
		);
	}

	// INITIALIZER METHODS.

	/**
	 * Prompts for the address of the server/host, establish connection  
	 * to the host/server and instantiates an object of type ClientRunner.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public ClientRunner() throws IOException, InterruptedException 
	{
		String hostName = JOptionPane.showInputDialog("Please enter the hostname or"
				+ " ip address of the server? Or contine with the default:", "localhost");        
		if (hostName==null)
		{
			System.exit(0);
		}

		int serverPort=7876;
		
		socketForGame = new Socket(hostName, serverPort);
//		System.out.print(new Timestamp(System.currentTimeMillis()));
//		System.out.println(" Connection requested.");

		Thread.sleep(200);

		socketForBroadcastFromGame = new Socket(hostName, serverPort);
//		System.out.print(new Timestamp(System.currentTimeMillis()));
//		System.out.println(" Client connected to server-side broadcaster");
	}

	// OPERATIONAL METHODS.

	/**
	 * Creates the client GUI (the main window). 
	 * Instantiates the main controller (which is for the main window).
	 * Links all the listeners of the main controller to the main window.
	 * Connects the main controller to the server-side broadcaster.
	 * Runs the perpetual listening of the client to the server-side broadcaster via another thread.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void runClient() throws IOException, InterruptedException
	{
		MainWindow mainWindow = new MainWindow(); // GUI is created.

		MainController_Client mainWindowController = 
				new MainController_Client(mainWindow, this.socketForGame, this.socketForBroadcastFromGame); //Controller is created.

		mainWindow.linkAllSourcesToListener(mainWindowController); //GUI sources are linked to Controller.

		/*
		 *  This thread is just to run the method that perpetually 
		 *  listens for broadcasts from the game.
		 */
		Thread threadToListenToGameBroadcast = new Thread(mainWindowController);
		threadToListenToGameBroadcast.start();
	}
}
