package imat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Optional;

public class ProductCard extends VarukorgItemController {
    @FXML private ImageView Image;
    @FXML private Label Name;
    @FXML private Label Price;

    public ProductCard(ShoppingItem shoppingItem, MainViewController parentController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.shoppingItem = shoppingItem;
        this.parentController = parentController;
        Image.setImage(parentController.iMatDataHandler.getFXImage(shoppingItem.getProduct()));
        Name.setText(shoppingItem.getProduct().getName());
        String price_text = String.valueOf(shoppingItem.getProduct().getPrice()) + " " + shoppingItem.getProduct().getUnit();
        Price.setText(price_text);
        updateAmount();
    }
    public void onClick(Event event){
        parentController.openDetailView(shoppingItem.getProduct());
    }
}
