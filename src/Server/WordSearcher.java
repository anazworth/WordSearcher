package Server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class WordSearcher {

    List<String> text;

    public WordSearcher(File file) {
        try {
            this.text = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.text.replaceAll(String::toUpperCase);
    }

    public List<Integer> search(String word) {
        List<Integer> result = new ArrayList<>();
        word = word.toUpperCase();
        for (int i = 0; i < this.text.size(); i++) {
            if (this.text.get(i).contains(word)) {
                result.add(i + 1);
            }
        }
        return result;
    }
}
