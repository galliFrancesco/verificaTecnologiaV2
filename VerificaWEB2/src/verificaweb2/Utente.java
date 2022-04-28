package verificaweb2;

/**
 *
 * @author galliFrancesco
 */
public class Utente {
    
    String mail; 
    String password; 
    String token; 
    
    
    public Utente(){
        mail= ""; 
        password=""; 
        token=""; 
    }
    
    public void setMail(String mail){
        this.mail = mail; 
    }
    public void setPass(String pass){
        this.password = pass; 
    }
    public void setToken(String token){
        this.token = token; 
    }
    
    
    public String getToken(){
        return token; 
    }
}
