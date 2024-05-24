package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class OrderVarukorgItem {
    @FXML private Label Name;
    @FXML private Label Price;
    @FXML private ImageView Image;
    private ShoppingItem shoppingItem;
    private MainViewController parentController;
    public OrderVarukorgItem(ShoppingItem shoppingItem, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order_list_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = parentController;
        this.shoppingItem = shoppingItem;
        Name.setText(shoppingItem.getProduct().getName());
        String priceText = String.format("%.2f",shoppingItem.getTotal());
        Price.setText(priceText);
        Image.setImage(parentController.iMatDataHandler.getFXImage(shoppingItem.getProduct()));
    }
}
