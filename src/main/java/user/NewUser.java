package user;


public class NewUser {
    
    //
    private final int startingBalance = 1000;
    
    private String name;
    private String password;
    private String repeatPassword;
    
    
    //
    public NewUser(String name, String password, String repeatPassword) {
        this.name = name;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }
    
    
    //
    public String getName() {
        return name;
    }
    
    
    //
    public boolean isNameValid() {
        return !name.isEmpty();
    }
    
    //
    public boolean passwordsMatch() {
        return password.equals(repeatPassword);
    }
    
    
    //
    public User generateUser() {
        
        User user = new User(name, password);
        user.setBalance(startingBalance);
        
        return user;
    }
    
}
