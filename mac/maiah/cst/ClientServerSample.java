package mac.maiah.cst;

import mac.maiah.cst.server.HttpServer;

public class ClientServerSample {

	public static void main(String[] args) {
		HttpServer httpServer = new HttpServer(7000);
		httpServer.start();
	}

}
