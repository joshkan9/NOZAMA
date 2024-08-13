package org.example.model;

import org.example.controller.NozamaDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

/**
 * Query application window that displays the four queries that can be executed on the Nozama database.
 */
public class QueryApp {

    static final int QUERY_PANEL_DIMENSIONS = 30;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel queryPanel;

    /**
     * Main method to start the application.
     */
    public QueryApp() {
        createMainFrame();
    }

    /**
     * Create the main application frame with the four queries that can be executed on the Nozama database.
     */
    private void createMainFrame() {
        mainFrame = new JFrame("Query Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(192,192,192));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JButton query1Button = styleButton("Query #1");
        JLabel query1Label = new JLabel("Find the best-selling products in the store.", JLabel.CENTER);
        query1Label.setFont(new Font("Arial", Font.BOLD, 17));
        query1Label.setForeground(Color.BLACK);

        JButton query2Button = styleButton("Query #2");
        JLabel query2Label = new JLabel("Get the total revenue breakdown by state.", JLabel.CENTER);
        query2Label.setFont(new Font("Arial", Font.BOLD, 17));
        query2Label.setForeground(Color.BLACK);

        JButton query3Button = styleButton("Query #3");
        JLabel query3Label = new JLabel("Find the top customers by total revenue.", JLabel.CENTER);
        query3Label.setFont(new Font("Arial", Font.BOLD, 17));
        query3Label.setForeground(Color.BLACK);

        JButton query4Button = styleButton("Query #4");
        JLabel query4Label = new JLabel("Search a category for an item below a specific price.", JLabel.CENTER);
        query4Label.setFont(new Font("Arial", Font.BOLD, 17));
        query4Label.setForeground(Color.BLACK);

        query1Button.addActionListener(e -> switchToQuery1Panel());
        query2Button.addActionListener(e -> switchToQuery2Panel());
        query3Button.addActionListener(e -> switchToQuery3Panel());
        query4Button.addActionListener(e -> switchToQuery4Panel());

        gbc.gridy = 0;
        mainPanel.add(query1Button, gbc);
        gbc.gridy++;
        mainPanel.add(query1Label, gbc);

        gbc.gridy++;
        mainPanel.add(query2Button, gbc);
        gbc.gridy++;
        mainPanel.add(query2Label, gbc);

        gbc.gridy++;
        mainPanel.add(query3Button, gbc);
        gbc.gridy++;
        mainPanel.add(query3Label, gbc);

        gbc.gridy++;
        mainPanel.add(query4Button, gbc);
        gbc.gridy++;
        mainPanel.add(query4Label, gbc);

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Method that switches the main frame to the Query #1 panel.
     */
    private void switchToQuery1Panel() {
        queryPanel = new JPanel(new GridBagLayout());
        queryPanel.setBackground(new Color(230, 230, 250));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);

        topPanel.setBackground(new Color(230, 230, 250));

        backButton.addActionListener(e -> switchToMainPanel());

        JTable resultTable = executeQuery1AndGetTable();
        styleTable(resultTable);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane.setPreferredSize(new Dimension(700, 400));

        scrollPane.getViewport().setBackground(new Color(230, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        queryPanel.add(topPanel, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        queryPanel.add(scrollPane, gbc);

        gbc.gridy++;
        gbc.weighty = 0.1;
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();
    }

    /**
     * Method that switches the main frame to the Query #2 panel.
     */
    private void switchToQuery2Panel() {
        queryPanel = new JPanel(new BorderLayout());
        queryPanel.setBackground(new Color(230, 230, 250));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(230, 230, 250));
        topPanel.add(backButton);
        backButton.addActionListener(e -> switchToMainPanel());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(230, 230, 250));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 10, 5, 10);

        JLabel stateLabel = new JLabel("Select State:");
        stateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(stateLabel, gbc);

        String[] states = {
                "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA",
                "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
                "VA", "WA", "WV", "WI", "WY"
        };

        JComboBox<String> stateDropdown = new JComboBox<>(states);
        stateDropdown.setFont(new Font("Arial", Font.PLAIN, 18));
        stateDropdown.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(stateDropdown, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 0, 10);
        inputPanel.add(submitButton, gbc);

        String[] columnNames = {"State", "Total Revenue"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultsTable = new JTable(tableModel);
        styleTable(resultsTable);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.getViewport().setBackground(new Color(230, 230, 250));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        wrapperPanel.setBackground(new Color(230, 230, 250));
        wrapperPanel.add(inputPanel, BorderLayout.NORTH);
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        queryPanel.add(topPanel, BorderLayout.NORTH);
        queryPanel.add(wrapperPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            String selectedState = (String) stateDropdown.getSelectedItem();
            addResultToTable(selectedState, tableModel);
            mainFrame.revalidate();
        });

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();
    }

    /**
     * Method that switches the main frame to the Query #3 panel.
     */
    private void switchToQuery3Panel() {
        queryPanel = new JPanel(new GridBagLayout());
        queryPanel.setBackground(new Color(230, 230, 250));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);

        topPanel.setBackground(new Color(230, 230, 250));

        backButton.addActionListener(e -> switchToMainPanel());

        JTable resultTable = executeQuery3AndGetTable();
        styleTable(resultTable);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane.setPreferredSize(new Dimension(700, 400));

        scrollPane.getViewport().setBackground(new Color(230, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        queryPanel.add(topPanel, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        queryPanel.add(scrollPane, gbc);

        gbc.gridy++;
        gbc.weighty = 0.1;
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();
    }

    /**
     * Method that switches the main frame to the Query #4 panel.
     */
    private void switchToQuery4Panel() {
        queryPanel = new JPanel(new GridBagLayout());
        queryPanel.setBackground(new Color(230, 230, 250));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);

        topPanel.setBackground(new Color(230, 230, 250));

        backButton.addActionListener(e -> switchToMainPanel());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel categoryLabel = new JLabel("Select Category:");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(categoryLabel, gbc);

        JComboBox<String> categoryComboBox = new JComboBox<>(fetchCategories());
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        categoryComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(categoryComboBox, gbc);

        JLabel priceLabel = new JLabel("Price Less Than:");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(priceLabel, gbc);

        JTextField priceInput = new JTextField(10);
        priceInput.setFont(new Font("Arial", Font.PLAIN, 18));
        priceInput.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(priceInput, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(submitButton, gbc);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 1.0;
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        queryPanel.add(topPanel, gbcMain);

        gbcMain.gridy++;
        gbcMain.fill = GridBagConstraints.NONE;
        queryPanel.add(inputPanel, gbcMain);

        gbcMain.gridy++;
        gbcMain.weighty = 1.0;
        gbcMain.fill = GridBagConstraints.BOTH;
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbcMain);

        submitButton.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            String priceText = priceInput.getText();
            try {
                double price = Double.parseDouble(priceText);
                JTable resultTable = executeQuery4AndGetTable(selectedCategory, price);
                styleTable(resultTable);

                JScrollPane scrollPane = new JScrollPane(resultTable);
                scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                scrollPane.setPreferredSize(new Dimension(700, 400));

                scrollPane.getViewport().setBackground(new Color(230, 230, 250));

                queryPanel.remove(inputPanel);
                gbcMain.gridy++;
                queryPanel.add(scrollPane, gbcMain);
                mainFrame.revalidate();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter a valid price.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();
    }

    /**
     * Method that executes Query #1 and returns the result as a JTable.
     * @return JTable containing the result of Query #1
     */
    private JTable executeQuery1AndGetTable() {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT p.product_id AS 'Product ID', p.item_name AS 'Item Name', SUM(od.quantity) AS 'Total Quantity Sold', "
                + "SUM(od.quantity * od.price) AS 'Total Revenue' "
                + "FROM products p "
                + "JOIN orderdetails od ON p.product_id = od.product_id "
                + "JOIN orders o ON od.order_id = o.order_id "
                + "WHERE o.status = 'completed' "
                + "GROUP BY p.product_id, p.item_name "
                + "ORDER BY `Total Quantity Sold` DESC;";


        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnLabel(i)); // Use getColumnLabel instead of getColumnName
            }

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

    /**
     * Method that adds the result of Query #2 to the table.
     * @param userInput The state selected by the user
     * @param tableModel The table model to which the result will be added
     */
    private void addResultToTable(String userInput, DefaultTableModel tableModel) {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT a.state AS 'State', SUM(od.quantity * od.price) AS 'Total Revenue' "
                + "FROM customers c "
                + "JOIN addresses a ON c.customer_id = a.customer_id "
                + "JOIN orders o ON c.customer_id = o.customer_id "
                + "JOIN orderdetails od ON o.order_id = od.order_id "
                + "WHERE o.status = 'completed' AND a.state = ? "
                + "GROUP BY a.state";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userInput);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String state = rs.getString("State");
                double totalRevenue = rs.getDouble("Total Revenue");
                tableModel.addRow(new Object[]{state, String.format("$%.2f", totalRevenue)});
            } else {
                JOptionPane.showMessageDialog(mainFrame, "No result found for this state", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Error occurred while fetching the result.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method that executes Query #3 and returns the result as a JTable.
     * @return JTable containing the result of Query #3
     */
    private JTable executeQuery3AndGetTable() {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT c.customer_id AS 'Customer ID', CONCAT(c.first_name, ' ', c.last_name) AS 'Full Name', COUNT(od.product_id) AS 'Quantity Purchased', "
                + "SUM(od.quantity * od.price) AS 'Total Revenue' "
                + "FROM customers c "
                + "JOIN orders o ON c.customer_id = o.customer_id "
                + "JOIN orderdetails od ON o.order_id = od.order_id "
                + "JOIN products p ON od.product_id = p.product_id "
                + "GROUP BY c.customer_id, c.first_name, c.last_name "
                + "ORDER BY `Total Revenue` DESC;";


        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnLabel(i));
            }

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

    /**
     * Method that fetches all the categories from the database.
     * @return Array of category names
     */
    private String[] fetchCategories() {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT DISTINCT category_name FROM categories";
        Vector<String> categories = new Vector<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories.toArray(new String[0]);
    }

    /**
     * Method that executes Query #4 and returns the result as a JTable.
     * @param category The category selected by the user
     * @param price The price entered by the user
     * @return JTable containing the result of Query #4
     */
    private JTable executeQuery4AndGetTable(String category, double price) {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT p.product_id AS 'Product ID', p.item_name AS 'Item Name', p.price AS 'Item Price' "
                + "FROM products p "
                + "JOIN categories c ON p.category_id = c.category_id "
                + "WHERE c.category_name = ? AND p.price < ? "
                + "ORDER BY p.price DESC";


        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, category);
            pstmt.setDouble(2, price);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnLabel(i));
            }

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

    /**
     * Method that switches the main frame back to the main panel.
     */
    private void switchToMainPanel() {
        mainFrame.setContentPane(mainPanel);
        mainFrame.revalidate();
    }

    /**
     * Method that styles the buttons in the application.
     * @param text The text to be displayed on the button
     * @return JButton styled according to the application's theme
     */
    private JButton styleButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(150, 50));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        return button;
    }

    /**
     * Method that styles the table in the application.
     * @param table The table to be styled
     */
    private void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));

        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // product_id
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // item_name
    }
}
