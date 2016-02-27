package eu.reply.pokerbot;

import java.io.InputStream;

public class RandomBot extends AbstractBot{
	
	
	
	public RandomBot(FileWriter writer) {
		super(writer);
	}

	@Override
	protected AbstractBotReceiver createReceiver(InputStream inputStream, AbstractBot abstractBot) {
		// TODO Auto-generated method stub
		return new RandomBotReceiver(inputStream,abstractBot);
	}
	
	
}
