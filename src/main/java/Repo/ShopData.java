package Repo;

import Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopData {
    private static User currentUser;

    public static void SaveToFile(List savelist, String Savestring ){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(Savestring))) {
            outputStream.writeObject(savelist);
            System.out.println("File save successful");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static List readFromFile(String saveString) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(saveString))) {
            List loadedProductList = (List) inputStream.readObject();
            System.out.println("Loaded product list: " + loadedProductList);
            return loadedProductList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
//    public static User getCurrentUser(List<User> users) {
//        User currentUser = ShopData.getCurrentUser();
//        Optional<User> first = users.stream()
//                .filter(user -> user.getUserName().equals(currentUser.getUserName()))
//                .findFirst();
//        return first.get();
//    }
}
