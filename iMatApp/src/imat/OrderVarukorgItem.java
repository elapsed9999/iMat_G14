package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class OrderVarukorgItem extends AnchorPane {
    @FXML private Label Name2;
    @FXML private Label Price;
    @FXML private ImageView Image;
    @FXML private Label Amount;
    private ShoppingItem shoppingItem;
    private MainViewController parentController;
    public OrderVarukorgItem(ShoppingItem shoppingItem, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order_varukorg_item.fxml"));
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
        Name2.setText(shoppingItem.getProduct().getName());
        String priceText = String.format("%.2f",shoppingItem.getTotal());
        Price.setText(priceText);
        Image.setImage(parentController.iMatDataHandler.getFXImage(shoppingItem.getProduct()));
        Amount.setText(String.valueOf((int)shoppingItem.getAmount()) + " " + shoppingItem.getProduct().getUnitSuffix());
    }
}
