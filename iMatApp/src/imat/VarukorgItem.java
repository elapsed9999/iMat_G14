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
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Optional;

public class VarukorgItem extends AnchorPane {
    @FXML private Label Name;
    @FXML private Label Amount;
    @FXML private Button MinusButton;
    @FXML private Button PlusButton;

    private ShoppingItem shoppingItem;
    private MainViewController parentController;
    private double amount = 1;

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
        setAmount(shoppingItem.getAmount());
    }

    public ShoppingItem getShoppingItem(){ return shoppingItem; }

    public void setAmount(double amount){
        Amount.setText(String.valueOf(amount) + " " + shoppingItem.getProduct().getUnitSuffix());
    }

    public void plusOne(){
        amount += 1;
        setAmount(amount);
    }
}
