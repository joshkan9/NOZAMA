package org.example.model;

import org.example.controller.NozamaDatabase;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class QueryApp {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel queryPanel;

    public QueryApp() {
        createMainFrame();
    }

    private void createMainFrame() {
        mainFrame = new JFrame("Query Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 800);  // Set the main frame to a larger size

        // Create a panel with a GridBagLayout for centering the buttons
        mainPanel = new JPanel(new GridBagLayout());

        // Create a GridBagConstraints object to position the buttons in the center
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around the buttons
        gbc.anchor = GridBagConstraints.CENTER;

        // Create the buttons
        JButton query1Button = new JButton("Query #1");
        JButton query2Button = new JButton("Query #2");
        JButton query3Button = new JButton("Query #3");
        JButton query4Button = new JButton("Query #4");

        // Add action listeners to switch panels
        query1Button.addActionListener(e -> switchToQuery1Panel());
        query2Button.addActionListener(e -> switchToQuery2Panel());
        query3Button.addActionListener(e -> switchToQueryPanel("Query #3"));
        query4Button.addActionListener(e -> switchToQueryPanel("Query #4"));

        // Add the buttons to the main panel
        mainPanel.add(query1Button, gbc);
        mainPanel.add(query2Button, gbc);
        mainPanel.add(query3Button, gbc);
        mainPanel.add(query4Button, gbc);

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void switchToQuery1Panel() {
        queryPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30)); // Set a smaller size for the back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout to align left
        topPanel.add(backButton);

        backButton.addActionListener(e -> switchToMainPanel());

        JTable resultTable = executeQuery1AndGetTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);

        queryPanel.add(topPanel, BorderLayout.NORTH);
        queryPanel.add(scrollPane, BorderLayout.CENTER);

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();  // Refresh the frame to show the new content
    }

    private void switchToQuery2Panel() {
        queryPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30)); // Set a smaller size for the back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout to align left
        topPanel.add(backButton);

        backButton.addActionListener(e -> switchToMainPanel());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel stateLabel = new JLabel("Enter State:");
        JTextField stateInput = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        inputPanel.add(stateLabel);
        inputPanel.add(stateInput);
        inputPanel.add(submitButton);

        queryPanel.add(topPanel, BorderLayout.NORTH);
        queryPanel.add(inputPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            String userInput = stateInput.getText();
            JTable resultTable = executeQuery2AndGetTable(userInput);
            JScrollPane scrollPane = new JScrollPane(resultTable);
            queryPanel.remove(inputPanel); // Remove input panel after submission
            queryPanel.add(scrollPane, BorderLayout.CENTER);
            mainFrame.revalidate(); // Refresh the frame to show the new content
        });

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();  // Refresh the frame to show the new content
    }

    private JTable executeQuery1AndGetTable() {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT p.product_id, p.item_name, SUM(od.quantity) AS total_quantity_sold, "
                + "SUM(od.quantity * od.price) AS total_revenue "
                + "FROM products p "
                + "JOIN orderdetails od ON p.product_id = od.product_id "
                + "JOIN orders o ON od.order_id = o.order_id "
                + "WHERE o.status = 'completed' "
                + "GROUP BY p.product_id, p.item_name "
                + "ORDER BY total_revenue DESC;";

        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Column names
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Data rows
            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(rs.getObject(i));
                }
                data.add(vector);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(data, columnNames);
    }

    private JTable executeQuery2AndGetTable(String userInput) {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT a.state, SUM(od.quantity * od.price) AS total_revenue "
                + "FROM customers c "
                + "JOIN addresses a ON c.customer_id = a.customer_id "
                + "JOIN orders o ON c.customer_id = o.customer_id "
                + "JOIN orderdetails od ON o.order_id = od.order_id "
                + "WHERE o.status = 'completed' AND a.state = ? "
                + "ORDER BY total_revenue DESC;";

        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userInput);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Column names
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Data rows
            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(rs.getObject(i));
                }
                data.add(vector);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(data, columnNames);
    }

    private void switchToQueryPanel(String queryTitle) {
        queryPanel = new JPanel(new BorderLayout());

        JLabel queryLabel = new JLabel(queryTitle, SwingConstants.CENTER);
        JButton backButton = new JButton("Back");

        // Make the back button small and place it at the top left corner
        backButton.setPreferredSize(new Dimension(75, 30)); // Set a smaller size for the back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout to align left
        topPanel.add(backButton);

        backButton.addActionListener(e -> switchToMainPanel());

        queryPanel.add(topPanel, BorderLayout.NORTH);
        queryPanel.add(queryLabel, BorderLayout.CENTER);

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();  // Refresh the frame to show the new content
    }

    private void switchToMainPanel() {
        mainFrame.setContentPane(mainPanel);
        mainFrame.revalidate();  // Refresh the frame to show the main content
    }
}
