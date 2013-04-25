package mac.maiah.cst.server;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	private ServerSocket serverSocket;

	@Override
	protected void finalize() throws Throwable {
		serverSocket.close();
		serverSocket = null;
	}

	public HttpServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void start() throws IOException {
		listenToIncomingConnections(serverSocket);
	}

	private void listenToIncomingConnections(ServerSocket serverSocket) throws IOException {
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
				throw e;
			} finally {
				closeSocketAndWriter(socket, bw);
				socket = null;
				bw = null;
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
