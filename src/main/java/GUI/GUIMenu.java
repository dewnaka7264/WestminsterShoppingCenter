package GUI;

import Model.CartItem;
import Model.Clothing;
import Model.Electronics;
import Model.Product;
import Repo.ShopData;
import serivce.impl.ConsoleMenu;
import serivce.impl.WestminsterShoppingManager;
import utilitites.ShopUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Console;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GUIMenu extends JFrame {

    private DefaultTableModel productTableModel;

    public DefaultTableModel ShoppingCartTable;
    private JTextArea productDetailsTextArea;
    private JCheckBox checkbox = new JCheckBox("Sort");
    private static List<Product> productArrayList = new ArrayList<>();
    Object[][] data;

    private JPanel panel3 = new JPanel();

    private JLabel lblProductId = new JLabel();
    private JLabel lblProductName = new JLabel();
    private JLabel lblProductCategory = new JLabel();
    private JLabel lblProductSize = new JLabel();
    private JLabel lblProductColour = new JLabel();
    private JLabel lblProductNoOfItemsAvailable = new JLabel();
    private JLabel sizeLabel = new JLabel("Size");
    private JLabel colorLabel = new JLabel("Color");
    private DefaultTableModel model;
    private JTable table = new JTable();
    private JCheckBox sortCheckBox;
    private List<CartItem> cartItems = new ArrayList();
    private GUIMenu guiMenu;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIMenu gui = new GUIMenu();
            gui.setVisible(true);
        });
    }

    public void ArrangeTableData (List<Product> Arrangelist) {
        data=new Object[Arrangelist.size()][];
        for (int i = 0; i < Arrangelist.size(); i++) {
            Product product = Arrangelist.get(i);
            if (product instanceof Electronics) {
                data[i] = new Object[]{
                        product.getProductName(),
                        product.getPrice(),
                        product.getProductID(),
                        "Electronics",
                        ((Electronics) product).getBrand() + " brand and " + ((Electronics) product).getWarrantyPeriod() + " Warranty"
                };
            } else {
                data[i] = new Object[]{
                        product.getProductName(),
                        product.getPrice(),
                        product.getProductID(),
                        "Clothing",
                        ((Clothing) product).getColour() + "colour and " + ((Clothing) product).getSize() + " size."
                };
            }

        }

    }
    public GUIMenu() {
        setTitle("Westminster Shopping Manager");
        setSize(800, 600);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiMenu = this;
        productArrayList = (List<Product>) WestminsterShoppingManager.readFromFile();
        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("ProductID");
        productTableModel.addColumn("Category");
        productTableModel.addColumn("Info");


        List<Product> products = WestminsterShoppingManager.readFromFile();
        for (Product product : products) {
            Object[] rowData = null;
            if (product instanceof Electronics) {
                rowData = new Object[]{
                        product.getProductName(),
                        product.getPrice(),
                        product.getProductID(),
                        "Electronics",
                        ((Electronics) product).getBrand() + " brand and " + ((Electronics) product).getWarrantyPeriod() + " Warranty"
                };
            } else {
                rowData = new Object[]{
                        product.getProductName(),
                        product.getPrice(),
                        product.getProductID(),
                        "Clothing",
                        ((Clothing) product).getColour() + "colour and " + ((Clothing) product).getSize() + " size."
                };
            }
            productTableModel.addRow(rowData);
        }

        checkbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<Product> sortedProductList = new ArrayList<>();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    checkbox.setText("Sorted");
                    productArrayList = (ArrayList<Product>) WestminsterShoppingManager.readFromFile();
                    sortedProductList = productArrayList.stream()
                            .sorted(Comparator.comparing(product -> product.getProductName()))
                            .collect(Collectors.toList());
                    data = new Object[sortedProductList.size()][];
                    for (int i = 0; i < sortedProductList.size(); i++) {
                        Product product = sortedProductList.get(i);
                        if (product instanceof Electronics electronics) {
                            data[i] = new Object[]{
                                    electronics.getProductID(),
                                    electronics.getProductName(),
                                    "Electronics",
                                    electronics.getPrice(),
                                    (electronics.getBrand() + ", " + electronics.getWarrantyPeriod() + " weeks warranty")
                            };
                        } else if (product instanceof Clothing clothing) {
                            data[i] = new Object[]{
                                    clothing.getProductID(),
                                    clothing.getProductName(),
                                    "Clothing",
                                    clothing.getPrice(),
                                    (clothing.getSize() + ", " + clothing.getColour()),
                            };
                        }
                    }
                    productTableModel.setDataVector(data, new String[]{"Name", "Price", "ProductID", "Category", "Info"});
                }
            }
        });
        JTable productTable = new JTable(productTableModel);
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = null;
                try {
                    component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    String productId = (String) table.getValueAt(row, 2);
                    var product = ShopUtil.getProduct(productId, productArrayList);

                    // Change the background color of the row based on a condition (e.g., even row number)
                    if (product.getAvailableItems() < 3) {
                        component.setBackground(Color.RED);
                    } else {
                        component.setBackground(table.getBackground());
                    }
                } catch (Exception e) {
                    e.getMessage();
                } finally {
                    return component;
                }
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setEditable(false);

        JComboBox<String> productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productTypeComboBox.setSelectedItem("All");
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) productTypeComboBox.getSelectedItem();
                List<Product> FilteredProducts = WestminsterShoppingManager.readFromFile();
                switch (selectedItem) {
                    case "Electronics":
                        FilteredProducts = FilteredProducts.stream()
                                .filter(product -> product instanceof Electronics)
                                .collect(Collectors.toList());
                        System.out.println("Model.Electronics");
                        break;
                    case "Clothing":
                        FilteredProducts = FilteredProducts.stream()
                                .filter(product -> product instanceof Clothing)
                                .collect(Collectors.toList());
                        System.out.println("Model.Clothing");
                        break;

                }
                System.out.println("Filtered Products: "+FilteredProducts);
                ArrangeTableData(FilteredProducts);
                    productTableModel.setDataVector(data, new String[]{"Name", "Price", "ProductID", "Category", "Info"});
                }
        });




        ListSelectionModel selectionModel = productTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow selecting only one row at a time
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Retrieve data from the selected row
                    String productId = productTable.getValueAt(selectedRow, 2).toString();

                    //showSelectedItem((String) productId);
                    showSelectedItem(productId);
                }
            }
        });
//        public void showSelectedItem((String) products) {
//            // Customize this method based on what you want to do with the selected item
//            System.out.println("Selected Model.Product ID: " + products);
//
//            // For demonstration purposes, you might want to display a dialog or perform other actions
//            JOptionPane.showMessageDialog(null, "Selected Model.Product ID: " + products, "Selected Model.Product", JOptionPane.INFORMATION_MESSAGE);
//        }


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
                if(ShopData.getCurrentUser()==null) {
                    System.out.println("BTN cart login frameclicked");
                    new LoginFrame();
                }
                else{
                    System.out.println("BTN cart clicked");
                    new ShoppingCart(guiMenu);
                }
            }
        });

        JPanel controlsPanel = new JPanel(new FlowLayout());
        controlsPanel.add(new JLabel("Select Model.Product Type:"));
        controlsPanel.add(productTypeComboBox);
        controlsPanel.add(addToCartButton);

        controlsPanel.add(viewCartButton);
        controlsPanel.add(checkbox);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(controlsPanel, BorderLayout.NORTH);

        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(productDetailsTextArea, BorderLayout.SOUTH);
        panel3.setLayout(new GridLayout(7, 1));
        panel3.add(new JLabel("Selected Product -"));
        panel3.add(new JLabel("Details"));
        panel3.add(new JLabel("Product ID"));
        panel3.add(lblProductId);
        panel3.add(new JLabel("Product Category"));
        panel3.add(lblProductCategory);
        panel3.add(new JLabel("Product Name"));
        panel3.add(lblProductName);
        panel3.add(sizeLabel);
        panel3.add(lblProductSize);
        panel3.add(colorLabel);
        panel3.add(lblProductColour);
        panel3.add(new JLabel("Items available"));
        panel3.add(lblProductNoOfItemsAvailable);
        add(mainPanel);
        add(panel3);
        //initializeProductList();
    }


    private void updateProductTable(String selectedProductType) {
        // Implement this method to update the product table based on the selected product type
        // You need to filter the product list based on the selected type and update the table accordingly
    }

    private void initializeProductList() {
        // Implement this method to populate the product list with your actual data
        // productTableModel.addRow(new Object[]{"Model.Product Name", 10.99});
    }

    private void addToCart() {
        // Implement this method to add the selected product to the shopping cart
        cartItems = ShopData.readFromFile("cart.ser");
        String productId = lblProductId.getText();

        int quantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the quantity"));
        if (quantity == -1) {
            JOptionPane.showMessageDialog(this, "Quantity is invalid !!!");
        }
        Product product = productArrayList.stream()
                .filter(x -> x.getProductID().equals(productId))
                .findFirst()
                .get();

        Optional<CartItem> first = cartItems.stream()
                .filter(new Predicate<CartItem>() {
                    @Override
                    public boolean test(CartItem c) {
                        return c.getProductId().equals(productId);
                    }
                })
                .findFirst();
        if(first.isPresent()) {
            CartItem cartItem = first.get();
        }

        try {
            CartItem cartItem = first
                    .get();
            if (cartItem != null) {
                //Check to see whether the product already exists in the cart
                cartItem.addQuantity((float) quantity, (float) product.getPrice());
            }
        } catch (Exception e) {
            // Add the product as a new item to cart because the product isn't in the cart
            CartItem cartItem = new CartItem(
                    product.getProductID(),
                    product.getProductName(),
                    quantity,
                    (float) (quantity * product.getPrice())
            );
            cartItems.add(cartItem);
            e.printStackTrace();
        }finally{
            ShopData.SaveToFile(cartItems, "cart.ser");
        }
    }

    private void showSelectedItem(String productId) {
        Optional<Product> opt = productArrayList.stream()
                .filter(x -> x.getProductID().equals(productId))
                .findFirst();

        if(opt.isEmpty()) return;

        Product product = opt.get();

        lblProductId.setText(product.getProductID());
        lblProductName.setText(product.getProductName());
        lblProductCategory.setText(product.getClass().getSimpleName());
        lblProductNoOfItemsAvailable.setText(String.valueOf(product.getAvailableItems()));
        // Check the Class of the instance and set the value in the UI according to the instance
        if (product instanceof Electronics) {
            sizeLabel.setText("Brand");
            colorLabel.setText("Warranty period (week)");
            lblProductSize.setText(((Electronics) product).getBrand());
            lblProductColour.setText(Integer.toString(((Electronics) product).getWarrantyPeriod()));

        } else if (product instanceof Clothing) {
            sizeLabel.setText("Size");
            colorLabel.setText("Color");
            lblProductColour.setText(((Clothing) product).getColour());
            lblProductSize.setText(((Clothing) product).getSize() + "");
        }
    }

}
