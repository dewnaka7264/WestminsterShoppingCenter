package Model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private boolean isPurchased;


    public boolean isPurchased(){
        return isPurchased;
    }


    public void purchase(){
        isPurchased = true;
    }

    public User(String userName, String password, boolean b){
        this.userName=userName;
        this.password=password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void purchased() {
        isPurchased = true;
    }
}

