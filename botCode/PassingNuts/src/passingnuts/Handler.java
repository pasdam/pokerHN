package passingnuts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Handler extends Thread {
	private Socket socket;
    private PassManager manager;
    private BufferedReader is;
    private PrintWriter os;
    private int num;
    private Handler buddy;

    
    public Handler(PassManager manager, Socket socket, int num) throws IOException {
        this.socket = socket;
        this.manager = manager;
        this.num = num;
        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        os = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public void setBuddy(Handler buddy) {
    	this.buddy = buddy;
    	buddy.buddy = this;
    }
    
    protected abstract boolean isServer();

    @Override
	public void run() {

        try {
            while (true) {
                String msg = is.readLine();
                if (msg != null) buddy.send(msg);
                else break;
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void send(String message) {
    	manager.log(isServer(), num, message);
    	os.println(message);
    }
    
    public void terminateConnection() throws IOException{
    	socket.close();
    }
}
