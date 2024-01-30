package serivce.impl;

import GUI.GUIMenu;
import Model.Clothing;
import Model.Electronics;
import Model.Product;

import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class ConsoleMenu {

    public static ArrayList<Product> productArrayList = new ArrayList<>();
    boolean exit;
    public static int MaxProductCount;


    public static void main(String[] args) {
        while (true) {
            printHeader();
            printMenu();
            Scanner scanner = new Scanner(System.in);
            try {
                int choice = scanner.nextInt();
                switchChoice(choice);
            }catch (InputMismatchException e){
                System.out.println("Invalid Input! Try again.");
                scanner.next();//Consume invalid input
                continue;
            }

            System.out.println("If you want to continue,press 'Y' Else 'Q' to Exit:");
            String YesOrNo = scanner.next().toUpperCase();
            if (YesOrNo.equals("Y"))
                continue;
            else {
                break;
            }

        }
    }


    private static void printMenu() {
        System.out.println("Please enter your choice: ");
        System.out.println("1) Add a new product to the system");
        System.out.println("2) Delete a product");
        System.out.println("3) Print the list of the products in the system");
        System.out.println("4) Save in the file the list of products");
        System.out.println("5) Read the File's save information ");
        System.out.println("6) Open Graphical Model.User Interface");
        System.out.println("0) Exit");
        System.out.println("Enter your choice..");
    }

    public static void printHeader() {
        System.out.println("+------------------------------------------+");
        System.out.println("|               Welcome to                 |");
        System.out.println("|      Westminster Shopping Center         |");
        System.out.println("+------------------------------------------+");
    }

    public static void switchChoice(int choice) {
        switch (choice) {
            case 1:
                WestminsterShoppingManager.addProductToSystem();
                break;
            case 2:
                WestminsterShoppingManager.deleteProductFromSystem();
                break;
            case 3:
                WestminsterShoppingManager.printProductsList();
                break;
            case 4:
                WestminsterShoppingManager.saveProductsToFile();
                break;
            case 5:
                WestminsterShoppingManager.readFromFile();
                break;
            case 6:
                System.out.println("Opening GUI! ");
                openGUI();
                break;
            case 0:
                System.out.println("Exiting the Centre.Thank you||");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid manu choice");
        }
    }

//    private static void addProductToSystem() {
//        System.out.println("Adding a new product to the system");
//        if (MaxProductCount == 50) {
//            System.out.println("System has reached the maximum number of 50 products");
//        } else {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Enter productID:");
//            String productID;
//            while (true) {
//                productID = scanner.next();
//                if (!isProductIDDuplicate(productID)) {
//                    break; // Valid input, exit the loop
//                } else {
//                    System.out.println("Model.Product with ID " + productID + " already exists. Please enter a different product ID.");
//                }
//            }
//            scanner.nextLine();
//            System.out.println("Enter product name:");
//            String productName = scanner.nextLine();
//            //scanner.nextLine();
//            System.out.println("Enter price:");
//            double price = scanner.nextDouble();
//            System.out.println("Number of available items;");
////            int availableItems = scanner.nextInt();
//            int availableItems;
//            while (true) {
//                try {
//                    availableItems = scanner.nextInt();
//                    if (availableItems <= 50-MaxProductCount && availableItems > 0) {
//                        break; // Valid input, exit the loop
//                    } else {
//                        System.out.println("Invalid input. Please enter a number between 1 and " + (50-MaxProductCount) + ".");
//                    }
//                } catch (InputMismatchException e) {
//                    System.out.println("Invalid input. Please enter a valid integer.");
//                    scanner.next(); // Consume the invalid input to avoid an infinite loop
//                }}
//            System.out.println("Enter product Type (Model.Electronics or Model.Clothing) - E or C:");
//            String product_Type;
//            while (true) {
//                product_Type = scanner.next().toUpperCase(); // Convert input to uppercase for case-insensitive comparison
//                if ("E".equalsIgnoreCase(product_Type) || "C".equalsIgnoreCase(product_Type)) {
//                    break; // Valid input, exit the loop
//                } else {
//                    System.out.println("Invalid input. Please enter 'E' for Model.Electronics or 'C' for Model.Clothing.");
//                }
//            }
//            if ("E".equals(product_Type)) {
//                System.out.println("Enter product brand: ");
//                String brand = scanner.next();
//                System.out.println("Enter warranty period(in weeks): ");
//                int warrantyPeriod = scanner.nextInt();
//                Product newproduct = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
//                productArrayList.add(newproduct);
//                MaxProductCount=MaxProductCount+availableItems;
//
//                System.out.println("New Model.Electronics products added to the system.");
//
//
//            } else if ("C".equals(product_Type)) {
//                System.out.println("Enter cloth size: ");
//                String size = scanner.next();
//                System.out.println("Enter cloth colour: ");
//                String colour = scanner.next();
//
//                Product newproduct = new Clothing(productID, productName, availableItems, price, size, colour);
//                productArrayList.add(newproduct);
//                MaxProductCount=MaxProductCount+availableItems;
//
//                System.out.println("New clothing products added to the system.");
//
//            }
//        }
//    }
    public static boolean isProductIDDuplicate(String productID) {
        for (int i = 0; i < productArrayList.size(); i++) {
            if (productArrayList.get(i).getProductID().equals(productID)) {
                return true;
            }
        }
        return false;
    }//checks whether the system has similar prouductIDs.


//    private static void printProductList() {
//        System.out.println("Printing the product List");
//        System.out.println("---------------------------------------");
//        if (productArrayList.size() > 0) {
//            Collections.sort(productArrayList, (p1, p2) -> p1.getProductID().compareTo(p2.getProductID()));
//            for (Product product : productArrayList) {
//                System.out.println(product.toString());
//                System.out.println("---------------------------------------");
//            }
//        }else{
//            System.out.println("Model.Product List is empty.");
//        }
//
//    }

    public static Product getProductById(ArrayList<Product> arrayList, String productId) {
        for (Product product : arrayList) {
            if (product.getProductID().equals(productId)) {
                return product;
            }
        }
        return null;
    }

//    private static void deleteProductFromSystem() {
//        System.out.println("Deleting a product by ID:");
//        if (productArrayList.size() > 0) {
//            System.out.println("Enter product ID: ");
//            Scanner newscanner = new Scanner(System.in);
//            String productIDtoDelete = newscanner.next();
//            newscanner.nextLine();
//
//            // Check if the product with the specified ID exists
//            Product productToDelete = getProductById(productArrayList, productIDtoDelete);
//
//            if (productToDelete != null) {
//                int deletedProductCount = productToDelete.getAvailableItems();
//                MaxProductCount = MaxProductCount - deletedProductCount;
//                productArrayList.remove(productToDelete);
//                System.out.println("This product has been deleted from the system.");
//            } else {
//                System.out.println("Model.Product not found with ID: " + productIDtoDelete);
//            }
//        } else {
//            System.out.println("No existing products in the system.");
//        }
//    }


//    static void saveProductsToFile() {
//        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("products.ser"))) {
//            outputStream.writeObject(productArrayList);
//            System.out.println("Model.Product list saved to " + "Products.ser");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public static List<Product> readFromFile() {
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("products.ser"))) {
//            List<Product> loadedProductList = (List<Product>) inputStream.readObject();
//            System.out.println("Loaded product list: " + loadedProductList);
//            return loadedProductList;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static void openGUI() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIMenu().setVisible(true);
            }
        });

    }
}

