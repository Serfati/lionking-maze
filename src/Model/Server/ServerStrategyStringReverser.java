package Model.Server;

import java.io.*;

public class ServerStrategyStringReverser implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(inFromClient));
        BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(outToClient));

        String clientCommand = fromClient.readLine();
        toClient.write(new StringBuilder(clientCommand).reverse().toString()+"\n");
        toClient.flush();
    }
}
