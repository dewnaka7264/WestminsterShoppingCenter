package GUI;

import Model.*;
import Repo.ShopData;
import utilitites.FileNames;
import utilitites.ShopUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class ShoppingCart extends JFrame {
    private final String[] columns = new String[]{"Name", "Price","ProductId"};
    private GUIMenu guiMenu = null;
    private java.util.List<Product> productList = ShopData.readFromFile("products.ser");
    private List<CartItem> cartItems=ShopData.readFromFile("cart.ser");
    private Object[][] data;
    private int noOfElectronics;
    private int noOfCloths;
    private List<User> users=ShopData.readFromFile("users.ser");;
    private JPanel summaryPanel = new JPanel(new GridLayout(4, 2));
//    private JPanel summaryRightPanel = new JPanel(new GridLayout(4, 1));

    private JLabel lblTotalPrice = new JLabel();
    private JLabel lblDiscountTenPercent = new JLabel();
    private JLabel lblDiscountTwentyPercent = new JLabel();
    private JLabel lblGrandTotal = new JLabel();

    public ShoppingCart(){}
    public ShoppingCart(GUIMenu guiMenu) {
        this.guiMenu = guiMenu;
        // Create a new JFrame
        JFrame newFrame = new JFrame("Shopping Cart");

        // Set size, default close operation, and other properties of the new JFrame
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        // Assuming ShoppingCartTable is an instance variable
        DefaultTableModel ShoppingCartModel = new DefaultTableModel();


        arrangeTableData();
        ShoppingCartModel.setDataVector(data,columns);
        JTable productTable = new JTable(ShoppingCartModel);
        productTable.setSize(400, 300);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        // Add the tableScrollPane to the content pane of the JFrame
//        add(tableScrollPane, BorderLayout.CENTER);
add(tableScrollPane);
        // Create JLabels for displaying total amounts
//        JLabel totalLabel = new JLabel("Total: ");
//        JLabel discountLabel = new JLabel("First Purchase Discount (10%): ");
//        JLabel finalTotalLabel = new JLabel("Final Total: ");
//
//        // Add the labels to the content pane vertically
//        JPanel labelsPanel = new JPanel();
//        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
//        labelsPanel.add(totalLabel);
//        labelsPanel.add(Box.createVerticalStrut(10)); //adds a gap between Jlabels.
//        labelsPanel.add(discountLabel);
//        labelsPanel.add(Box.createVerticalStrut(10));
//        labelsPanel.add(finalTotalLabel);

//        getContentPane().add(labelsPanel, BorderLayout.SOUTH);

        createCartSummary();

        JButton btnPurchase = new JButton("Purchase");
        btnPurchase.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Do you want to finish checkout?");
            if (result == 0) {
                cartItems.stream()
                        .forEach(cartItem -> {
                            Product product = ShopUtil.getProduct(cartItem.getProductId(), productList);
                            product.purchase(cartItem.getQuantity());
                        });
                cartItems.clear();
                ShopData.SaveToFile(cartItems, FileNames.CART_FILE);
                ShopData.SaveToFile(productList, FileNames.PRODUCTS_FILE);
                ShopUtil.getCurrentUser(users).purchased();
                ShopData.SaveToFile(users, FileNames.USERS_FILE);

                JOptionPane.showMessageDialog(this, "Checkout completed");

                // Recreate the HomeUI to update product quantities after purchase
                guiMenu.dispose();
                new GUIMenu();

                // Close the CartUI after successful purchase
                this.dispose();
            }
        });
        add(btnPurchase);
        setVisible(true);
    }

    public void arrangeTableData(){
        data = new Object[cartItems.size()][];
        for (int i = 0; i < cartItems.size(); i++) {
            Product product = ShopUtil.getProduct(cartItems.get(i).getProductId(), productList);
            CartItem cartItem = cartItems.get(i);
            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                noOfElectronics += cartItem.getQuantity();
                data[i] = new Object[]{
                        (electronics.getProductID() + " \n" +
                                electronics.getProductName() + " \n" +
                                electronics.getBrand() + ", " +
                                electronics.getWarrantyPeriod()
                        ),
                        cartItem.getQuantity(),
                        cartItem.getTotalPrice()
                };
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                noOfCloths += cartItem.getQuantity();

                data[i] = new Object[]{
                        (clothing.getProductID() + " \n" +
                                clothing.getProductName() + " \n" +
                                clothing.getSize() + ", " +
                                clothing.getColour()
                        ),
                        cartItem.getQuantity(),
                        cartItem.getTotalPrice()
                };
            }
        }
    }

    private void generateBill() {
        Optional cartOptional = cartItems.stream()
                .map(product -> product.getTotalPrice())
                .reduce((x, y) -> x + y);
        if (!cartOptional.isPresent()) return;
        float totalPrice = (float) cartOptional.get();
        float tenPercentDiscount = 0;
        if (!ShopUtil.getCurrentUser(users).isPurchased()) {
            tenPercentDiscount = (float) (totalPrice * 0.1);
        }
        float twentyPercentDiscount = noOfCloths >= 3 || noOfElectronics >= 3 ? (float) (totalPrice * 0.2) : 0;

        lblTotalPrice.setText(String.valueOf(totalPrice));
        lblDiscountTenPercent.setText(String.valueOf(tenPercentDiscount));
        lblDiscountTwentyPercent.setText(String.valueOf(twentyPercentDiscount));
        lblGrandTotal.setText(String.valueOf(totalPrice - tenPercentDiscount - twentyPercentDiscount));
    }

    private void createCartSummary() {
//        panel2.setLayout(new GridLayout(1, 2));

        summaryPanel.add(new JLabel("Total"));
        summaryPanel.add(lblTotalPrice);
        summaryPanel.add(new JLabel("First purchase discount (10%)"));
        summaryPanel.add(lblDiscountTenPercent);
        summaryPanel.add(new JLabel("Three items in the same Category Discount (20%)"));
        summaryPanel.add(lblDiscountTwentyPercent);
        summaryPanel.add(new JLabel("Final Total"));
        summaryPanel.add(lblGrandTotal);

        add(summaryPanel);
        generateBill();

    }
}
