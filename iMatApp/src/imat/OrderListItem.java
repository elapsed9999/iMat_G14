package imat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class OrderListItem extends AnchorPane {
    @FXML private Label Date;
    @FXML private Label Price;
    private Order order;
    private MainViewController parentController;
    public OrderListItem(Order order, MainViewController parentController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order_list_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
        fxmlLoader.load();
        } catch (IOException exception) {
        throw new RuntimeException(exception);
        }
        this.order = order;
        this.parentController = parentController;
        double price = 0;
        for(ShoppingItem item : order.getItems()){
            price += item.getTotal();
        }
        String priceText = String.format("%.2f",price);
        Price.setText(priceText);
        String dateText = order.getDate().toString();
        Date.setText(dateText);
    }
    @FXML public void onClick(Event event){ parentController.openOrderDetailView(order); }
}
