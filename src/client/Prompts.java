package client;

public interface Prompts 
{
	static final String CELL_ALREADY_PLAYED = "That cell has been played. Please try another cell.";
	static final String GENERIC_ERROR = "An error occured. Please try again.";
	static final String PLEASE_REGISTER = "Please register players before starting a game.";
	static final String NOT_CURRENT_PLAYER = "You are not the current player";
	static final String START_GAME_BEFORE_PLAYING = "Please start the game before playing.";
	static final String X_ALREADY_TAKEN =  "The Player Mark X has been registered.";
	static final String O_ALREADY_TAKEN =  "The Player Mark O has been registered.";
	static final String PROVIDE_USERNAME = "Please enter a username.";
	static final String TWO_CLIENTS_NEEDED = "Two players are needed to run the game. Please run an additional Tictactoe window.";
}
