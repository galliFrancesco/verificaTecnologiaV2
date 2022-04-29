package verificaweb2;

/**
 *
 * @author galliFrancesco
 */
public class Place {
    
    String town; 
    String county;
    String postCode;
    
    float lat; 
    float longi; 
    
    public Place(){
        town = ""; 
        county = ""; 
        postCode=""; 
    }
    
    // SET
    public void setName(String name){
        // Name è generico, che sia town o city
        town = name; 
    }
    public void setCounty(String state){
        this.county = state; 
    }
    public void setPostCode(String postCode){
        this.postCode = postCode; 
    }
    
    public void setPos(float lat, float longi){
        // ne faccio uno solo, tanto poi saranno recuperati assieme
        this.lat = lat; 
        this.longi = longi; 
    }
    
    
    // GET
    public String getTown(){
        return town;
    }
    public String getState(){
        return county; 
    }
    public String getPostCode(){
        return postCode; 
    }
    
    public float getLat(){
        return lat; 
    }
    public float getLongi(){
        return longi; 
    }
}
