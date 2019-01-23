package sample;

public class Product {
    String id;
    String name;
    String description;
    String category;
    String rating;
    String price;
    String Quantity;

    public Product(String id, String name, String description, String category, String rating, String price, String quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.rating = rating;
        this.price = price;
        Quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
