# How to start playing

1. Download the two .jar files: "TicTacToeServer.jar" and "TicTacToeClient.jar".

2. Run the file "TicTacToeServer.jar" in order to start the server.

3. Run the file "TicTacToeClient.jar" in order to open the game.**

4. Enjoy the game.

** This can be in the same machine as the server or on a different machine. If a different machine, you need to provide the network address of the machine on which the server is running. To find the network address in Windows, run "ipconfig" in Command Prompt (filename: cmd.exe). If the same machine as the server, then use "localhost" as the network address.

# TicTacToe (ClientServer)

There are two applications here, hence the two .jar files. One runs a server that hosts the game, the server side. The other runs a GUI for a user to play with, the client side. The structure is a very simple client-server architecture.

A tictactoe match must involve two opponents, i.e. two GUIs, 
for X and O players, and both are connected to a single server-side game.

The two players will be able to play from seperate machines or locations so long as they stay connected to the server.

The server can host up to 5 separate tictactoe games, for a total of 10 players (2 per game).

## Connecting to the server

The client must know the address of the server. If it is in the same machine, the default "localhost" can be used.

## Choosing the computer to play for you

A client can select one of three levels of the computer player to play in their place.
