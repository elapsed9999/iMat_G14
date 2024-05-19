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

public class VarukorgItem extends AnchorPane {
    @FXML private Label Name;
    @FXML private Label Amount;
    @FXML private Button MinusButton;
    @FXML private Button PlusButton;

    private Product product;
    private IMatDataHandler handler;

    public VarukorgItem(Product product, IMatDataHandler handler, boolean large){
        product.setName("Vara");
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
        this.product = product;
        this.handler = handler;
        Name.setText(product.getName());
    }
}
