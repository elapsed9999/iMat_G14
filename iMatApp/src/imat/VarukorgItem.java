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

public class VarukorgItem extends VarukorgItemController {
    @FXML private Label Name;
    @FXML private ImageView Image;
    @FXML private Label Price;


    public VarukorgItem(ShoppingItem shoppingItem, MainViewController parentController, boolean large){
        String fxml;
        if(large){ fxml = "varukorg_item_large.fxml"; }
        else{ fxml = "varukorg_item_small.fxml"; }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = parentController;
        this.shoppingItem = shoppingItem;
        Name.setText(shoppingItem.getProduct().getName());
        if(large){
            Image.setImage(parentController.iMatDataHandler.getFXImage(shoppingItem.getProduct()));
            String priceText = String.valueOf(shoppingItem.getTotal());
            Price.setText(priceText);
        }
        updateAmount();
    }

    public ShoppingItem getShoppingItem(){ return shoppingItem; }

}
