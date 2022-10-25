package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Server {
    final static int PORT = 8080;
    public static void main(String[] args) {

        listenForConnections();

        System.out.println("Server shutting down...");
    }

    private static void listenForConnections() {
        boolean serverRunning = true;

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (serverRunning) {
                Socket socket = server.accept();
                System.out.println("Client connected from " + socket.getInetAddress());

                InputStream in = socket.getInputStream();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                int dataLength = in.read();
                byte[] data = new byte[dataLength];
                int read = in.read(data);
                String word = new String(data, StandardCharsets.UTF_8);

                System.out.println("Client [" + socket.getInetAddress() + "] requested word: " + word);
                serverRunning = !word.equals(":q");

                List<Integer> result = searchForWord(word);

                sendResult(out, result);
            }
        } catch (Exception e) {
            System.out.println("Error: Failed to start server");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void sendResult(ObjectOutputStream out, List<Integer> result) throws IOException {
        out.writeObject(result);
    }

    private static List<Integer> searchForWord(String word) {
        WordSearcher searcher = new WordSearcher(new File("src/Server/hamlet.txt"));
        return searcher.search(word);
    }
}