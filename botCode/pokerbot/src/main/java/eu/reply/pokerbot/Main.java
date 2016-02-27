package eu.reply.pokerbot;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private static final int NUM_BOT = 1;
	
	private Main(int numBot, boolean mlBot) throws Exception{
		FileWriter writer = new FileWriter("logHand.txt");
		for(int i =0; i< numBot; i++){
			executor.execute(mlBot?new IntelligentBot(writer):new RandomBot(writer));
		}
	}
	
	public static void main(String[] args) throws Exception{
		int numBot = NUM_BOT;
		Properties p = new Properties();
		p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		numBot = Integer.parseInt(p.getProperty("numBot","1"));
		boolean mlBot = false;
		mlBot = "I".equalsIgnoreCase(p.getProperty("botType"));
		new Main(numBot,mlBot);
	}
}
