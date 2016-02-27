package eu.reply.pokerbot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.reply.pokerbot.AbstractBotReceiver.BET_TYPE;


public class RandomBotReceiver  extends AbstractBotReceiver {
	
	
	
	@Override
	protected BET_TYPE getType(HandEvent event) {
		// TODO Auto-generated method stub
		double value = Math.random();
		if(value < 0.05){
			return BET_TYPE.FOLD;
		}else if(value < 0.7) {
			return BET_TYPE.CALL;
		}else if(value <0.95) {
			return BET_TYPE.RAISE;
		}
		return null;
	}

	public RandomBotReceiver(InputStream is,final BotInterface bot) {
		super(bot.getWriter(), bot,is );
	}
}
