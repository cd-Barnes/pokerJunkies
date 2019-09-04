package user;

import java.io.Serializable;


public class User implements Serializable {
    
    //
    private String name;
    private String password;
    private int balance;
    
    
    //
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.balance = 0;
    }
    
    
    //
    public void setName(String name) {
        this.name = name;
    }
    
    
    //
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    //
    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    
    //
    public String getName() {
        return name;
    }
    
    
    //
    public int getBalance() {
        return balance;
    }
    
    
    //
    public boolean isPassword(String password) {
        return this.password.equals(password);
    }
}
