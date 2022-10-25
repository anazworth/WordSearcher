package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import static Client.ClientView.displayLossOfConnection;

@SuppressWarnings("ResultOfMethodCallIgnored") // The result of read() is not needed in this case
public class ClientService {

    public static Object fetchResult(String word, String serverAddress, String serverPort) {

        try (Socket serverConnection = new Socket(serverAddress, Integer.parseInt(serverPort))) {
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);

            ObjectInputStream in = new ObjectInputStream(serverConnection.getInputStream());
            OutputStream output = serverConnection.getOutputStream();
            output.write(word.length());
            output.write(word.getBytes());
            in.read();

            return in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            displayLossOfConnection();
            throw new RuntimeException(e);
        }
    }
}