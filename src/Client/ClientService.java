package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import static Client.ClientView.displayLossOfConnection;

public class ClientService {

    public static Object fetchResult(String word) {
        try (Socket serverConnection = new Socket("localhost", 8080)) {
            System.out.println("Connected to server");

            ObjectInputStream in = new ObjectInputStream(serverConnection.getInputStream());
            OutputStream output = serverConnection.getOutputStream();

            output.write(word.length());
            output.write(word.getBytes());

            int resultLength = in.read();
            Object result = in.readObject();

            System.out.println("Server response: " + result);
            return result;

        } catch (IOException | ClassNotFoundException e) {
            displayLossOfConnection();
            throw new RuntimeException(e);
        }
    }
}