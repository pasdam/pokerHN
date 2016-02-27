package eu.reply.pokerbot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private static final int NUM_BOT = 1;
	
	private Main(int numBot) throws Exception{
		FileWriter writer = new FileWriter("logHand.txt");
		for(int i =0; i< numBot; i++){
			executor.execute(new IntelligentBot(writer));
		}
	}
	
	public static void main(String[] args) throws Exception{
		int numBot = NUM_BOT;
		if(args.length > 0) {
			numBot = Integer.parseInt(args[0]);
		}
		new Main(numBot);
	}
}
