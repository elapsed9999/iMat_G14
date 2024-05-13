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
    private IMatDataHandler handler;

    public ProductCard(Product product, IMatDataHandler handler){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        this.handler = handler;
        //Image.setImage(handler.getFXImage(product));
        //Name.setText(product.getName());
        //String price_text = String.valueOf(product.getPrice()) + product.getUnit();
        //Price.setText(price_text);
    }
}
