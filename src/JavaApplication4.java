
import java.io.FileNotFoundException;



public class JavaApplication4 {

 
    
    public static void main(String[] args) throws FileNotFoundException {
        
        
        String path = "";
        String hotelFile = "hotels.csv";
        
        System.out.println(path+hotelFile);
        Controller p1 = new Controller(path, hotelFile);
        
    }
    
}
