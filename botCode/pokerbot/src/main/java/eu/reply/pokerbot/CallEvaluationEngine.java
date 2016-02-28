package eu.reply.pokerbot;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import eu.reply.pokerbot.AbstractBotReceiver.BET_TYPE;

public class CallEvaluationEngine {
	private final String url;
	private final Gson gson = new Gson();
	
	public CallEvaluationEngine(String url){
		this.url = url;
	}
	
	public BET_TYPE callML(HandEvent event) throws Exception{
		try(CloseableHttpClient httpClient = HttpClients.createDefault();){
			 HttpPost postRequest = new HttpPost(url);
			 postRequest.setHeader("content-type", ContentType.APPLICATION_JSON.getMimeType());
			 StringEntity entity = new StringEntity(
					 gson.toJson(event), ContentType.APPLICATION_JSON);
			 postRequest.setEntity(entity);
			 ResponseHandler<String> handler = new BasicResponseHandler();
			 String response = httpClient.execute(postRequest,handler);
			 System.out.println(response);
			 return BET_TYPE.values()[new Integer(response)+1];
		}
	}
	
	public static void main(String[] args) throws Exception{
		CallEvaluationEngine ce = new CallEvaluationEngine("http://192.168.0.190:8800");
		ce.callML(new HandEvent());
	}
}
