

public class Hotel {
    private double id;
    private String name;
    private double numberOfReviews;
    private double price;
    private double latitude;
    private double longitude;

    public Hotel(double id, String name, double numberOfReviews, double price, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.numberOfReviews = numberOfReviews;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(double numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " numberOfReviews: " + numberOfReviews + " price: " + price + "  latitude: " + latitude + " longitude: " + longitude;
    }
    
    
    
    
    
    
    
}
