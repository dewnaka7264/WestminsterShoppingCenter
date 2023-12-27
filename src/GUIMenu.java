
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMenu extends JFrame {

    private DefaultTableModel productTableModel;
    private JTextArea productDetailsTextArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIMenu gui = new GUIMenu();
            gui.setVisible(true);
        });
    }

    public GUIMenu() {
        setTitle("Westminster Shopping Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("ProductID");
        productTableModel.addColumn("Category");
        productTableModel.addColumn("Info");

        JTable productTable = new JTable(productTableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setEditable(false);

        JComboBox<String> productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        productTypeComboBox.setSelectedItem("All");
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductTable((String) productTypeComboBox.getSelectedItem());
            }
        });

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        JButton viewCartButton = new JButton("Shopping Cart");
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showShoppingCart();
            }
        });

        JPanel controlsPanel = new JPanel(new FlowLayout());
        controlsPanel.add(new JLabel("Select Product Type:"));
        controlsPanel.add(productTypeComboBox);
        controlsPanel.add(addToCartButton);
        controlsPanel.add(viewCartButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(controlsPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(productDetailsTextArea, BorderLayout.SOUTH);

        add(mainPanel);
        initializeProductList();
    }

    private void updateProductTable(String selectedProductType) {
        // Implement this method to update the product table based on the selected product type
        // You need to filter the product list based on the selected type and update the table accordingly
    }

    private void initializeProductList() {
        // Implement this method to populate the product list with your actual data
        // productTableModel.addRow(new Object[]{"Product Name", 10.99});
    }

    private void addToCart() {
        // Implement this method to add the selected product to the shopping cart
    }

    private void showShoppingCart() {
        // Implement this method to show the contents and final price of the shopping cart
    }
}
