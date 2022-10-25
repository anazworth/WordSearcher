package Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientView extends JFrame {
    public JTextField inputField = new JTextField();
    public JButton searchButton = new JButton("Search");
    public JList<Integer> resultList = new JList<>(new DefaultListModel<Integer> ());
    public JScrollPane scrollPane = new JScrollPane(resultList);
    public static JLabel resultLabel = new JLabel("Result:");

    public ClientView() {
        super("Client");
        initViewSettings();
        userInterface();
    }

    public void initViewSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setSize(400, 400);
        this.setBounds(100, 100, 600, 400);
        setVisible(true);
    }

    public void userInterface() {
        JPanel inputPanel = new JPanel();
        this.add(inputPanel, BorderLayout.NORTH);
        inputPanel.setLayout(new GridLayout(2,2));
        inputPanel.add(new JLabel("Input a word to search for in the text:"));
        inputPanel.add(new JLabel(""));
        inputPanel.add(inputField);
        inputField.addActionListener(e -> searchButton.doClick()); // Pressing <CR> in the input field -> search
        inputPanel.add(searchButton);
        searchButton.addActionListener(e -> {
            String word = inputField.getText();
            List<Integer> result = (List<Integer>) ClientService.fetchResult(word);
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

        JPanel resultsPanel = new JPanel(new GridLayout(1,2));
        this.add(resultsPanel);
        resultsPanel.add(resultLabel);
        resultsPanel.add(scrollPane);

    }

    public static void displayLossOfConnection() {
        resultLabel.setText("No connection to server");
    }
}
