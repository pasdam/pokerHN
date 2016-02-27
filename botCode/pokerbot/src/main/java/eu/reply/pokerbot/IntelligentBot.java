package eu.reply.pokerbot;

import java.io.InputStream;

import org.w3c.dom.views.AbstractView;

public class IntelligentBot extends AbstractBot{

	
	public IntelligentBot(FileWriter writer) {
		super(writer);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected AbstractBotReceiver createReceiver(InputStream inputStream, AbstractBot bot) {
		// TODO Auto-generated method stub
		return new IntelligentBotReceiver(bot,inputStream);
	}

	
}
