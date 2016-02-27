package eu.reply.pokerbot;

public interface BotInterface {

	FileWriter getWriter();

	void fold(String table);

	void call(String table);

	void raise(String table, int val);

	void requestGameInfo(String table);

	void requestGameList();

	void registerGame(String game);

	void allIn(String game);

}