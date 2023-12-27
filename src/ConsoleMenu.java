import java.util.ArrayList;
import java.util.*;
import java.io.*;
public class ConsoleMenu {

    static ArrayList<Product> productArrayList = new ArrayList<>();
    boolean exit;

    public static void main(String[] args) {
        while (true) {
            printHeader();
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switchChoice(choice);
            System.out.println("If you want to continue,press 'Y' Else 'Q' to Exit:");
            String YesOrNo = scanner.next();
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
        System.out.println("6) Open Graphical User Interface");
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
                addProductToSystem();
                break;
            case 2:
                deleteProductFromSystem();
                break;
            case 3:
                printProductList();
                break;
            case 4:
                saveProductsToFile();
                break;
            case 5:
                readFromFile();
                break;
            case 6:
                System.out.println("Opening GUI! ");
                //GUIMenu();
                break;
            case 0:
                System.out.println("Exiting the Centre.Thank you||");
                System.exit(0);
                break;
        }
    }

    private static void addProductToSystem() {
        System.out.println("Adding a new product to the system");
        if (productArrayList.size() == 50) {
            System.out.println("System has reached the maximum number of 50 products");
        } else {
            Scanner scanner = new Scanner(System.in);
            //System.out.println("Enter you product type(1 for electronics and 2 for clothing):");
            //int productType=scanner.nextInt();
            System.out.println("Enter productID:");
            String productID = scanner.next();
            scanner.nextLine();
            System.out.println("Enter product name:");
            String productName = scanner.nextLine();
            //scanner.nextLine();
            System.out.println("Enter price:");
            double price = scanner.nextDouble();
            System.out.println("Number of available items;");
            int availableItems = scanner.nextInt();

            System.out.println("Enter product Type(Electronics or Clothing):");
            String product_Type = scanner.next();
            if ("Electronics".equals(product_Type)) {
                System.out.println("Enter product brand: ");
                String brand = scanner.next();
                System.out.println("Enter warranty period(in weeks): ");
                int warrantyPeriod = scanner.nextInt();
                Product newproduct = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
                productArrayList.add(newproduct);
                System.out.println("New Electronics product added to the system.");


            } else if ("Clothing".equals(product_Type)) {
                System.out.println("Enter cloth size: ");
                String size = scanner.next();
                System.out.println("Enter cloth colour: ");
                String colour = scanner.next();
                Product newproduct = new Clothing(productID, productName, availableItems, price, size, colour);
                productArrayList.add(newproduct);
                System.out.println("New clothing product added to the system.");

            }
        }
    }

    private static void printProductList() {
        System.out.println("Printing the product List");
        if (productArrayList.size() > 0) {
            for (Product product : productArrayList) {
                System.out.println(product.toString());
                System.out.println("---------------------------------------");
            }
        } //else if () {

         else {
            System.out.println("Product List is empty.");
        }

    }

    public static Product getProductById(ArrayList<Product> arrayList, String productId) {
        for (Product product : arrayList) {
            if (product.getProductID().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    private static void deleteProductFromSystem() {
        System.out.println("deleting a product by ID:");
        if (productArrayList.size() > 0) {
            System.out.println("Enter product ID: ");
            Scanner newscanner = new Scanner(System.in);
            String productIDtoDelete = newscanner.next();
            newscanner.nextLine();
            productArrayList.remove(getProductById(productArrayList, productIDtoDelete));
            System.out.println("This product has been deleted from the system.");
            // ().toString(); print the deleted product and reaminig data
        } else {
            System.out.println("No existing products in the system.");
        }
    }

    static String filePath = "products.ser";

     static void saveProductsToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(productArrayList);
            System.out.println("Product list saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static void readFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Product> loadedProductList = (List<Product>) inputStream.readObject();
            System.out.println("Loaded product list: " + loadedProductList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

