# How to start playing

You need Java Runtime Environment (JRE), and probably already have it if you've ever gotten the famous Java Update prompt in your machine. Get the JRE installed first if you don't already have it. 

1. Download the two .jar files: "TicTacToeServer.jar" and "TicTacToeClient.jar".

2. Run the file "TicTacToeServer.jar" in order to start the server.

3. Run the file "TicTacToeClient.jar" in order to open a game.**

4. Enjoy the game. Remember that you can select one of three levels of the computer player to play in your place.

** This can be in the same machine as the server or on a different machine. If in a different machine, you need to provide the network address of the machine on which the server is running (see further explanation below). If in the same machine as the server, then use "localhost" (or 127.0.0.1) as the network address.

# TicTacToe (client-server architecture)

There are two applications here, hence the two .jar files. One runs a server that hosts the game, the server side; the other runs a GUI for a user to play with, the client side. The structure is a very simple client-server architecture.

A tictactoe match must involve two opponents, i.e. two GUIs, 
for X and O players, and both are connected to a single server-side game.

The two players will be able to play from seperate machines or locations so long as they stay connected to the server.

The server can host up to 5 separate tictactoe games, for a total of 10 players (2 per game).

## Connecting to the server

The client must know the network address of the machine running the server. If both the client and server are in the same machine, the default hostname of the machine, "localhost" (which resolves to the loopback address 127.0.0.1), can be used. If the server is on a different machine, you need to provide the network address of that machine before you can run the client.

To find the network address of a machine, see [this short tutorial](https://kb.netgear.com/20878/Finding-your-IP-address-without-using-the-command-prompt) or Google "Finding ip adress of network".

**Note**: The firewall or an antivirus program of the machine running the server can interfere with connections to the server. Consider temporarily displaying them.
