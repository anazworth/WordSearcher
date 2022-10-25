package Server;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Server {
    final static int PORT = 8080; // Set port number here
    static File file = new File("src/Server/hamlet.txt"); // Set file path here
    public static void main(String[] args) {

        listenForConnections(PORT);

        System.out.println("Server shutting down...");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored") // The result of read() is not needed
    private static void listenForConnections(int port) {
        boolean serverRunning = true;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (serverRunning) {
                Socket socket = server.accept();
                System.out.println("Client connected from " + socket.getInetAddress());

                InputStream in = socket.getInputStream();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                int dataLength = in.read();
                byte[] data = new byte[dataLength];
                in.read(data);
                String word = new String(data, StandardCharsets.UTF_8);

                System.out.println("Client [" + socket.getInetAddress() + "] requested word: " + word);
                serverRunning = !word.equals(":q");

                List<Integer> result = searchForWord(word);

                sendResult(out, result);
            }
        } catch (Exception e) {
            System.out.println("Error: Failed to start server");
            if (e instanceof BindException) {
                System.out.println("Error: Port " + PORT + " is already in use");
                listenForConnections(PORT + 1); // If port 8080 is taken, try the next port recursively
            } else {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }

    private static void sendResult(ObjectOutputStream out, List<Integer> result) throws IOException {
        out.writeObject(result);
    }

    private static List<Integer> searchForWord(String word) {
        WordSearcher searcher = new WordSearcher(file);
        return searcher.search(word);
    }
}