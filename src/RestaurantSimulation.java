import java.io.IOException;

public class RestaurantSimulation {

    public static void main(String[] args) throws IOException {
        Restaurant restaurant = new Restaurant();
        restaurant.configureRestaurant("config.txt");
    }
}
