package BookPOJO;

public class Login {
    private String username;
    private String password;
    
    public void Login() {
        username = ""; 
        password = "";
    }
    public String getUser() {
        return username;
    }
    public String getPass() {
        return password;
    }
    
    public void setUser(String u) {
        username = u;
    }
    public void setPass(String p) {
        password = p;
    }
    
    public boolean NullAnyObject() {
        return (username.length() == 0 || password.length() == 0 || username.length()+password.length() == 0) ;             
    }
    
    public boolean CheckEqualObjects(String userDB, String passDB) {
        return (this.getUser().equals(userDB) && this.getPass().equals(passDB));
    }
}
