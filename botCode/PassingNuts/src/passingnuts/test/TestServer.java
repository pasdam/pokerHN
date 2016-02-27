package passingnuts.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer extends Thread {
	private Socket s;
    private BufferedReader is;
    private BufferedWriter os;
	
    public static void main(String args[]) throws IOException {
        @SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(40888);
        System.out.println ("Test server running");
        while(true) {
            Socket s = server.accept();
            System.out.println ("Test connection");
            new TestServer(s).start();
        }
    }
    
    public TestServer(Socket s) {
        try {
        	this.s = s;
        	is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    public void run() {
    	try {
            while (true) {
                String msg = is.readLine();
                if (msg != null) {
                	System.out.println("Ricevuto: "+msg);
                	os.write("Eco dal server: "+msg);
                	os.newLine();
                	os.flush();
                } else 
                	break;
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                s.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
