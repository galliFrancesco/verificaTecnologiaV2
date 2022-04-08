package verificaweb2;

/**
 *
 * @author galliFrancesco
 */
public class Utente {
    
    String mail; 
    String password; 
    public Utente(){
        mail= ""; 
        password=""; 
    }
    
    public void setMail(String mail){
        this.mail = mail; 
    }
    public void setPass(String pass){
        this.password = pass; 
    }
}
