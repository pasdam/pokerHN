package eu.reply.pokerbot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {
	private static final Object mutex = new Object();
	private BufferedWriter pw;
	public FileWriter(String filename) throws IOException{
		pw = new BufferedWriter(new java.io.FileWriter(filename,true));
	}
	
	public void writeString(String line) {
		try{
			synchronized (mutex) {
				pw.write(line);
				pw.newLine();
				pw.flush();
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}		
	}
}
