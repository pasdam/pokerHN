package passingnuts;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

public class PassManager {
	
	private PrintWriter w;

    public PassManager() throws IOException {
    	GregorianCalendar now = new GregorianCalendar();
    	File f = new File(String.format("logs/%1$tF %1$tH.%1$tM.%1$tS.log", now));
    	System.out.println(f.getAbsolutePath());
    	f.getParentFile().mkdirs();
    	w = new PrintWriter(f, "UTF-8");
    	w.printf("%1$tF %1$tT%n", now);
    	
    	try {
    		int n=0;
	        @SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(40889);
	        System.out.println ("Accepting connections...");
	        while(true) {
	        	n++;
	            Socket client = server.accept();
	            System.out.println ("Accepted " + n);
	            new ClientHandler(this, client, n).start();
	        }
    	} finally {
    		synchronized (w) {
    			w.close();
			}
    	}
    }
    
    public void log(boolean client, int num, String message) {
    	message = (client ? "C " : "S ") + num + ":\t" + message;
    	synchronized (w) {
    		w.println(message);
    		w.flush();
    		System.out.println(message);
		}
    }

    public static void main(String args[]) throws IOException {
    	if (args.length == 0) {
    		JOptionPane.showMessageDialog(null, "Impostare un argomento qualsiasi da linea di comando");
    		return;
    	}
        new PassManager();
    }

}
