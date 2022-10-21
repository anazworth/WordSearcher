package Server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    final static int PORT = 8080;
    public static void main(String[] args) {

        boolean serverRunning = true;

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (serverRunning) {
                Socket socket = server.accept();
                System.out.println("Client connected from " + socket.getInetAddress());

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                int dataLength = in.read();
                byte[] data = new byte[dataLength];
                String word = new String(data);

                System.out.println("Client requested word: " + word);
                serverRunning = !word.equals(":q");

                List<Integer> result = searchForWord(word);

                sendResult(out, result);

                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Error: Failed to start server");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void sendResult(OutputStream out, List<Integer> result) throws IOException {
        out.write(result.size());
        for (Integer integer : result) {
            out.write(integer);
        }
    }

    private static List<Integer> searchForWord(String word) {
        WordSearcher searcher = new WordSearcher(new File("src/Server/hamlet.txt"));
        return searcher.search(word);
    }
}