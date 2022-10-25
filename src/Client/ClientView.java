package Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static Client.ClientService.fetchResult;

public class ClientView extends JFrame {
    public JTextField inputField = new JTextField();
    public JButton searchButton = new JButton("Search");
    public JList<Integer> resultList = new JList<>(new DefaultListModel<>());
    public JScrollPane scrollPane = new JScrollPane(resultList);
    public static JLabel resultLabel = new JLabel("Result:");
    public JTextField serverAddress = new JTextField("localhost");
    public JTextField serverPort = new JTextField("8080");
    public JLabel roundTripTime = new JLabel("");

    public ClientView() {
        super("Client");
        initViewSettings();
        showUserInputUI();
        showServerResponseUI();
        showServerConnectionUI();
    }

    public void initViewSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setSize(400, 400);
        this.setBounds(100, 100, 600, 400);
        setVisible(true);
    }

    private void showServerConnectionUI() {
        JPanel serverPanel = new JPanel(new GridLayout(3,2));
        this.add(serverPanel, BorderLayout.SOUTH);
        serverPanel.add(new JLabel("Server address:"));
        serverPanel.add(serverAddress);
        serverPanel.add(new JLabel("Server port:"));
        serverPanel.add(serverPort);
        serverPanel.add(roundTripTime);
    }

    private void showServerResponseUI() {
        JPanel resultsPanel = new JPanel(new GridLayout(1,2));
        this.add(resultsPanel);
        resultsPanel.add(resultLabel);
        resultsPanel.add(scrollPane);
    }

    private void showUserInputUI() {
        JPanel inputPanel = new JPanel();
        this.add(inputPanel, BorderLayout.NORTH);
        inputPanel.setLayout(new GridLayout(2,2));
        inputPanel.add(new JLabel("Input a word to search for in the text:"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(inputField);
        inputField.addActionListener(e -> searchButton.doClick()); // Pressing <CR> in the input field -> search
        inputPanel.add(searchButton);
        searchButtonListener();
    }

    private void searchButtonListener() {
        searchButton.addActionListener(e -> {
            String word = inputField.getText();
            long startTime = System.currentTimeMillis(); // Start server round trip timer
            @SuppressWarnings("unchecked") // The result of fetchResult() is always a List<Integer>
            List<Integer> result = (List<Integer>) fetchResult(word, serverAddress.getText(), serverPort.getText());
            long endTime = System.currentTimeMillis(); // End server round trip timer
            roundTripTime.setText("Round trip time: " + (endTime - startTime) + " ms");
            DefaultListModel<Integer> model = (DefaultListModel<Integer>) resultList.getModel();
            model.clear();
            model.addAll(result);
            resultLabel.setText("Result: Found "
                    + result.size()
                    + " lines containing <"
                    + word
                    + ">"
            );
        });
    }

    public static void displayLossOfConnection() {
        resultLabel.setText("No connection to server");
    }
}
