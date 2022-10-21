package Server.test;

import Server.WordSearcher;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordSearcherTest {

    @org.junit.jupiter.api.Test
    void searchForWordShouldReturnCorrectListOfIntegers() {
        WordSearcher searcher = new WordSearcher(new File("src/Server/hamlet.txt"));
        assertEquals(List.of(1, 10, 33, 116, 263, 316, 320, 337, 391, 394, 588, 816, 869, 916, 943, 966, 1474, 1477, 1575, 1646, 2428, 3167, 3854, 4041, 4109, 4294), searcher.search("denmark"), "Search for 'denmark' failed");
        assertEquals(List.of(13, 15, 26, 34, 264, 325, 554, 614, 1052, 1054, 1243, 1259, 1266, 1452, 1453, 1594, 1760, 1842, 1908, 2017, 2096, 2100, 2148, 2159, 2458, 2524, 2536, 2610, 2619, 2642, 2651, 2857, 2906, 2993, 3008, 3246), searcher.search("polonius"), "Search for 'polonius' failed");
    }

    @org.junit.jupiter.api.Test
    void searchForWordShouldReturnEmptyListIfWordIsNotFound() {
        WordSearcher searcher = new WordSearcher(new File("src/Server/hamlet.txt"));
        assertEquals(List.of(), searcher.search("asdf"), "Search for 'asdf' should have returned an empty list");
        assertEquals(List.of(), searcher.search("lorum"), "Search for 'lorum' should have returned an empty list");
    }
}