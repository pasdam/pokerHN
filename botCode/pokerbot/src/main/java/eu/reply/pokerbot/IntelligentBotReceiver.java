package eu.reply.pokerbot;

import java.io.InputStream;

public class IntelligentBotReceiver extends AbstractBotReceiver{
	private CallEvaluationEngine ce = null;
	
	public IntelligentBotReceiver( BotInterface  bot,InputStream is) {
		super(bot.getWriter(),bot, is);
		ce = new CallEvaluationEngine("http://10.59.1.171:8800/api/v1/addrecord/1");
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
