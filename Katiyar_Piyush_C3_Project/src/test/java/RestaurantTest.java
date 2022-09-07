import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    Restaurant spyRestaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
@BeforeEach
   public void setup()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Noodles", 100);
        restaurant.addToMenu("Manchurian", 200);
        spyRestaurant = Mockito.spy(restaurant);

    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("11:00:00"));
        assertThat(spyRestaurant.isRestaurantOpen(),equalTo(true));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:00:00"));
        assertThat(spyRestaurant.isRestaurantOpen(),equalTo(false));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void getPrice_of_the_Item(){
      Item first = new Item("Noodles", 100);
      int price = first.getPrice();
      assertEquals(100,price);
    }

    @Test //toString
    public void toString_of_the_Item(){
        Item first = new Item("Noodles", 100);
        assertEquals("Noodles:100\n",first.toString());
    }
    @Test
    public void calculateOrderValue_of_two_menu_items()
    {
        List<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Noodles");
        selectedItems.add("Manchurian");
        int cost = restaurant.calculateOrderValue(selectedItems);
        assertEquals(300,cost);
    }
    @Test
    public void calculateOrderValue_of_zero_menu_items()
    {
        List<String> selectedItems = new ArrayList<String>();
        int cost = restaurant.calculateOrderValue(selectedItems);
        assertEquals(0,cost);
    }
    @Test
    public void displayDetails_of_the_restaurant_when_invokedPrintln_thenOutputCaptorSuccess()
    {
        //String restaurantDetails = restaurant.displayDetails();
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        restaurant.displayDetails();

        assertEquals("Restaurant:Amelie's cafe\n" +
                "Location:Chennai\n" +
                "Opening time:10:30\n" +
                "Closing time:22:00\n" +
                "Menu:\n" +
                "[Sweet corn soup:119\n" +
                ", Vegetable lasagne:269\n" +
                ", Noodles:100\n" +
                ", Manchurian:200\n]", outputStreamCaptor.toString()
                .trim());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}