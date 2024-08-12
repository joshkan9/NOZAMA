package org.example.model;

import org.example.controller.NozamaDatabase;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class QueryApp {

    static final int QUERY_PANEL_DIMENSIONS = 30;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel queryPanel;

    public QueryApp() {
        createMainFrame();
    }

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

    private void switchToQuery1Panel() {
        queryPanel = new JPanel(new GridBagLayout());
        queryPanel.setBackground(new Color(230, 230, 250));  // Light lavender color

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);

        // Set topPanel background to match queryPanel
        topPanel.setBackground(new Color(230, 230, 250));

        backButton.addActionListener(e -> switchToMainPanel());

        JTable resultTable = executeQuery1AndGetTable();
        styleTable(resultTable);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set preferred size for scroll pane
        scrollPane.setPreferredSize(new Dimension(700, 400));

        // Set scrollPane background
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
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);  // Adds some space below the table

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();
    }



    /**
     * Method that switches the main frame to the Query #2 panel.
     */
    private void switchToQuery2Panel() {
        queryPanel = new JPanel(new BorderLayout());

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);
        backButton.addActionListener(e -> switchToMainPanel());

        // Centered Panel for Input and Submit Button
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Reduce vertical spacing between components
        gbc.insets = new Insets(0, 10, 5, 10); // Small vertical spacing

        // State Label and Dropdown
        JLabel stateLabel = new JLabel("Select State:");
        stateLabel.setFont(new Font("Arial", Font.BOLD, 18));  // Bold and larger font
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(stateLabel, gbc);

        // Dropdown menu for state selection
        String[] states = {
                "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA",
                "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
                "VA", "WA", "WV", "WI", "WY"
        };

        JComboBox<String> stateDropdown = new JComboBox<>(states);
        stateDropdown.setFont(new Font("Arial", Font.PLAIN, 18));
        stateDropdown.setPreferredSize(new Dimension(150, 30));  // Dropdown size
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(stateDropdown, gbc);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 30));  // Increase size of button
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 10, 0, 10); // Add a bit more space above the button
        inputPanel.add(submitButton, gbc);

        // Adding the input panel to the query panel with extra padding on top to move it upwards
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); // 50 pixels top padding
        wrapperPanel.add(inputPanel, BorderLayout.NORTH);

        queryPanel.add(topPanel, BorderLayout.NORTH);
        queryPanel.add(wrapperPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            String selectedState = (String) stateDropdown.getSelectedItem();
            JTable resultTable = executeQuery2AndGetTable(selectedState);
            JScrollPane scrollPane = new JScrollPane(resultTable);
            queryPanel.remove(wrapperPanel);
            queryPanel.add(scrollPane, BorderLayout.CENTER);
            mainFrame.revalidate();
        });

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();
    }

    private void switchToQuery3Panel() {
        queryPanel = new JPanel(new GridBagLayout());
        queryPanel.setBackground(new Color(230, 230, 250));  // Light lavender color

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);

        // Set topPanel background to match queryPanel
        topPanel.setBackground(new Color(230, 230, 250));

        backButton.addActionListener(e -> switchToMainPanel());

        JTable resultTable = executeQuery3AndGetTable();
        styleTable(resultTable);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set preferred size for scroll pane
        scrollPane.setPreferredSize(new Dimension(700, 400));

        // Set scrollPane background
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
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);  // Adds some space below the table

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();
    }


    private void switchToQuery4Panel() {
        queryPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);

        backButton.addActionListener(e -> switchToMainPanel());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel categoryLabel = new JLabel("Select Category:");
        JComboBox<String> categoryComboBox = new JComboBox<>(fetchCategories());
        JLabel priceLabel = new JLabel("Price Less Than:");
        JTextField priceInput = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        inputPanel.add(categoryLabel);
        inputPanel.add(categoryComboBox);
        inputPanel.add(priceLabel);
        inputPanel.add(priceInput);
        inputPanel.add(submitButton);

        queryPanel.add(topPanel, BorderLayout.NORTH);
        queryPanel.add(inputPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            String priceText = priceInput.getText();
            try {
                double price = Double.parseDouble(priceText);
                JTable resultTable = executeQuery4AndGetTable(selectedCategory, price);
                JScrollPane scrollPane = new JScrollPane(resultTable);
                queryPanel.remove(inputPanel);
                queryPanel.add(scrollPane, BorderLayout.CENTER);
                mainFrame.revalidate();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter a valid price.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainFrame.setContentPane(queryPanel);
        mainFrame.revalidate();
    }

    private JTable executeQuery1AndGetTable() {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT p.product_id AS 'Product ID', p.item_name AS 'Item Name', SUM(od.quantity) AS 'Total Quantity Sold', "
                + "SUM(od.quantity * od.price) AS 'Total Revenue'"
                + "FROM products p "
                + "JOIN orderdetails od ON p.product_id = od.product_id "
                + "JOIN orders o ON od.order_id = o.order_id "
                + "WHERE o.status = 'completed' "
                + "GROUP BY p.product_id, p.item_name "
                + "ORDER BY 'Total Quantity Sold' DESC;";

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

    private JTable executeQuery2AndGetTable(String userInput) {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT a.state AS 'State', SUM(od.quantity * od.price) AS 'Total Revenue'"
                + "FROM customers c "
                + "JOIN addresses a ON c.customer_id = a.customer_id "
                + "JOIN orders o ON c.customer_id = o.customer_id "
                + "JOIN orderdetails od ON o.order_id = od.order_id "
                + "WHERE o.status = 'completed' AND a.state = ? "
                + "ORDER BY 'Total Revenue' DESC;";

        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userInput);
            ResultSet rs = pstmt.executeQuery();

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
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(data, columnNames);
    }

    private JTable executeQuery3AndGetTable() {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT c.customer_id, c.first_name, c.last_name, COUNT(od.product_id) AS 'Total Products Purchased', "
                + "SUM(od.quantity * od.price) AS total_revenue "
                + "FROM customers c "
                + "JOIN orders o ON c.customer_id = o.customer_id "
                + "JOIN orderdetails od ON o.order_id = od.order_id "
                + "JOIN products p ON od.product_id = p.product_id "
                + "GROUP BY c.customer_id, c.first_name, c.last_name "
                + "ORDER BY total_revenue DESC;";

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

    private JTable executeQuery4AndGetTable(String category, double price) {
        NozamaDatabase db = new NozamaDatabase();
        Connection connection = db.connect();

        String query = "SELECT p.product_id AS 'Product ID', p.item_name AS 'Item Name', p.price AS 'Item Price' "
                + "FROM products p "
                + "JOIN categories c ON p.category_id = c.category_id "
                + "WHERE c.category_name = ? AND p.price < ?";

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
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JTable(data, columnNames);
    }

    private void switchToMainPanel() {
        mainFrame.setContentPane(mainPanel);
        mainFrame.revalidate();
    }

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

    private void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));

        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // product_id
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // item_name
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // total_quantity_sold
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // total_revenue
    }
}
