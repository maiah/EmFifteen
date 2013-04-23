package mac.maiah.cst.server;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private int port;

	public HttpServer(int port) {
		this.port = port;
	}

	public void start() {
		ServerSocket serverSocket = createServerSocket();
		listenToIncomingConnections(serverSocket);
	}

	private ServerSocket createServerSocket() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return serverSocket;
	}

	private void listenToIncomingConnections(ServerSocket serverSocket) {
		while (true) {
			Socket socket = null;
			BufferedWriter bw = null;

			try {
				socket = serverSocket.accept();
				OutputStream os = socket.getOutputStream();
				bw = new BufferedWriter(new OutputStreamWriter(os));
				processIncomingConnection(bw);
				bw.flush();

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeSocketAndWriter(socket, bw);
			}			
		}
	}

	private void processIncomingConnection(BufferedWriter bw) throws IOException {
		bw.write("Hello Web");
	}

	private void closeSocketAndWriter(Socket socket, BufferedWriter bw) {
		closeIo(socket);
		closeIo(bw);
	}

	private void closeIo(Closeable ioResource) {
		if (ioResource != null) {
			try {
				ioResource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
