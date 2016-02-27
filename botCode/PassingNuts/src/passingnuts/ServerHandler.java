package passingnuts;

import java.io.IOException;
import java.net.Socket;

public class ServerHandler extends Handler {

	public ServerHandler(PassManager manager, int num) throws IOException {
		super(manager, new Socket("localhost", 40888), num);
	}

	@Override
	protected boolean isServer() {
		return true;
	}

}
