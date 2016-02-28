package eu.reply.pokerbot;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

public abstract class AbstractBot implements Runnable, BotInterface {
	private final FileWriter writer;
	private PrintWriter os;
	
	public AbstractBot(FileWriter writer) {
		this.writer = writer;
	}

	public void run() {
		try{
			Properties p = new Properties();
			p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			Scanner s = new Scanner(System.in);
			Socket socket = new Socket(p.getProperty("pokerServer"), new Integer(p.getProperty("pokerServerPort")));
	        os = new PrintWriter(socket.getOutputStream(), true);
	
			System.out.println("Client attivo");
			
			AbstractBotReceiver receiver = createReceiver(socket.getInputStream(),this);
			
			//login
			String uid = UUID.randomUUID().toString();
			String login = String.format("PCLIENT 5 %1$s%nINFO \"name:%1$s\" \"location:\"", uid);
			os.println(login);
			receiver.run();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected abstract AbstractBotReceiver createReceiver(InputStream inputStream, AbstractBot abstractBot);
	
	@Override
	public FileWriter getWriter() {
		return writer;
	}

	@Override
	public void fold(String table) {
		System.out.printf("Received fold on table %s%n",table);
		os.printf("ACTION %s fold%n", table);
	}

	@Override
	public void call(String table) {
		System.out.printf("Received call on table %s%n",table);
		os.printf("ACTION %s call 0%n", table);
	}

	@Override
	public void raise(String table, int val) {
		System.out.printf("Received raise %d on table %s%n",val,table);
		os.printf("ACTION %s raise %d%n", table, val);
	}

	@Override
	public void requestGameInfo(String table) {
		os.println("REQUEST gameinfo "+table);
	}

	@Override
	public void requestGameList() {
		os.println("REQUEST gamelist");
	}

	@Override
	public void registerGame(String game) {
		os.println("REGISTER "+game);
	}

	@Override
	public void allIn(String game) {
		System.out.printf("Received allin on table %s%n",game);
		os.printf("ACTION %s allin 0%n", game);
	}

}