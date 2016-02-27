package passingnuts;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Handler {
	private final ServerHandler sh;
	
    public ClientHandler(PassManager manager, Socket socket, int num) throws IOException {
    	super(manager, socket, num);
    	sh = new ServerHandler(manager, num);
    	sh.start();
		setBuddy(sh);
    }
    
    
    @Override
    public void run() {
    	try {
    		super.run();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
    			sh.terminateConnection();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	
    }

	@Override
	protected boolean isServer() {
		return false;
	}

}
