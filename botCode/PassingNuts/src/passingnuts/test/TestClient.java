package passingnuts.test;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		Socket socket = new Socket("localhost", 40889);
        PrintWriter os = new PrintWriter(socket.getOutputStream(), true);

		System.out.println("Client attivo");
		System.out.print("# ");
		while(s.hasNextLine()) {
			String line = s.nextLine();
			os.println(line);
			System.out.print("# ");
		}
	}
}
