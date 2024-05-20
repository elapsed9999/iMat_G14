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

import java.io.IOException;
import java.util.Optional;

public class ProductCard extends AnchorPane {
    @FXML private ImageView Image;
    @FXML private Label Name;
    @FXML private Label Price;
    @FXML private Label Amount;
    @FXML private Button MinusButton;
    @FXML private Button PlusButton;

    private Product product;
    private MainViewController parentController;

    public ProductCard(Product product, MainViewController parentController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        this.parentController = parentController;
        Image.setImage(parentController.iMatDataHandler.getFXImage(product));
        Name.setText(product.getName());
        String price_text = String.valueOf(product.getPrice()) + " " + product.getUnit();
        Price.setText(price_text);
        setAmount(0);
    }

    public void setAmount(int amount){
        Amount.setText(String.valueOf(amount) + " " + product.getUnitSuffix());
    }
    public void onClick(Event event){
        parentController.openDetailView(product);
    }

    public void minusButtonClick(Event event) {
        ShoppingCart sc = parentController.iMatDataHandler.getShoppingCart();
        sc.removeProduct(product);
    }

    public void plusButtonClick(Event event) {
        ShoppingCart sc = parentController.iMatDataHandler.getShoppingCart();
        sc.addProduct(product);
    }
}
