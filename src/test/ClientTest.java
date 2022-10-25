package test;

import Client.ClientService;
import Server.Server;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ClientTest {

    @BeforeAll
    static void setup() {
        new Thread(() -> {
            try {
                Server.main(null);
            } catch (Exception e) {
                System.out.println("Error: Failed to start server");
                e.printStackTrace();
            }
            try {
                ClientService.fetchResult("denmark");
            } catch (Exception e) {
                System.out.println("Error: Failed to connect to server");
                e.printStackTrace();
            }
        }).start();
    }

    @org.junit.jupiter.api.Test
    void fetchResultShouldReturnCorrectListOfIntegers() {
        assertEquals(List.of(1, 10, 33, 116, 263, 316, 320, 337, 391, 394, 588, 816, 869, 916, 943, 966, 1474, 1477, 1575, 1646, 2428, 3167, 3854, 4041, 4109, 4294), ClientService.fetchResult("denmark"), "Search for 'denmark' failed");
        assertEquals(List.of(13, 15, 26, 34, 264, 325, 554, 614, 1052, 1054, 1243, 1259, 1266, 1452, 1453, 1594, 1760, 1842, 1908, 2017, 2096, 2100, 2148, 2159, 2458, 2524, 2536, 2610, 2619, 2642, 2651, 2857, 2906, 2993, 3008, 3246), ClientService.fetchResult("polonius"), "Search for 'polonius' failed");
    }

    @org.junit.jupiter.api.Test
    void fetchResultShouldReturnEmptyListIfWordIsNotFound() {
        assertEquals(List.of(), ClientService.fetchResult("asdf"), "Search for 'asdf' should have returned an empty list");
        assertEquals(List.of(), ClientService.fetchResult("lorum"), "Search for 'lorum' should have returned an empty list");
    }
}