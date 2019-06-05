package Model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Model.Server.Configurations.getNumberOfThreads;
import static Model.Server.Configurations.runConf;

/**
 * Server class
 */
public class Server {
    private int port;
    private int listeningInterval; //ms
    private IServerStrategy serverStrategy;
    private boolean stopped;
    private ExecutorService threadPool;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        runConf();
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        this.threadPool = Executors.newFixedThreadPool(getNumberOfThreads());
    }

    public void start() {
        new Thread(this::runServer).start();
    }

    private void runServer() {
        try {
            System.out.println("Server: runServer");
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            System.out.println((String.format("Server started! (port: %s)", port)));
            if (!stopped) do {
                Socket clientSocket = server.accept(); // blocking call
                System.out.println((String.format("Client excepted: %s", clientSocket.toString())));
                System.out.println(String.format("Server: Client accepted: %s", clientSocket.toString()));
                threadPool.execute(() -> handleClient(clientSocket));
            }
            while(!stopped);
            threadPool.shutdown();
            server.close();
        } catch(IOException e) {
            System.out.println("SocketTimeout - No clients pending! "+e.getMessage());
        }
    }

    public void stop() {
        System.out.println("Stopping the Server!");
        stopped = true;
    }

    private void handleClient(Socket aClient) {
        try {
            System.out.println((String.format("Handling client with socket: %s", aClient.toString())));
            serverStrategy.serverStrategy(aClient.getInputStream(), aClient.getOutputStream());
            aClient.getInputStream().close();
            aClient.getOutputStream().close();
        } catch(IOException ignored) {
        }
    }
}