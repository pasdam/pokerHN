package eu.reply.pokerbot;

import java.io.InputStream;
import java.util.Properties;

public class IntelligentBotReceiver extends AbstractBotReceiver{
	private CallEvaluationEngine ce = null;
	
	public IntelligentBotReceiver( BotInterface  bot,InputStream is) throws Exception{
		super(bot.getWriter(),bot, is);
		Properties p = new Properties();
		p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		ce = new CallEvaluationEngine(p.getProperty("url","http://10.59.1.171:8800/api/v1/addrecord/1"));
	}

	@Override
	protected BET_TYPE getType(HandEvent event) {
		// TODO Auto-generated method stub
		try{
			BET_TYPE bt = ce.callML(event);
			System.out.println("Received: "+bt);
			return bt;
		}catch(Exception e){
			e.printStackTrace();
			return BET_TYPE.NONE;
		}
	}

	
	
	

}
