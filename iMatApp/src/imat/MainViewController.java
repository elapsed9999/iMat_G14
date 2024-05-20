
package imat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;

public class MainViewController implements Initializable, ShoppingCartListener {

    @FXML Label pathLabel;
    @FXML AnchorPane detailAnchor;
    @FXML AnchorPane detailView;
    @FXML ImageView closeDetailView;
    @FXML AnchorPane fullView;
    @FXML ImageView detailProductImage;
    @FXML TextArea detailArea;
    @FXML TextArea detailAreaContent;
    @FXML Label detailProductLabel;
    @FXML Label detailPriceLabel;
    @FXML Label detailCategoriLabel;

    @FXML private FlowPane ProductFlowPane;
    @FXML private FlowPane VarukorgFlowPane;

    @FXML private StackPane stackPane;

    @FXML private Label SumPrice;

    private Model model;
    private Product product;
    private ProductDetail productDetail;
    private ProductCategory selectedCategory = null;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();
        iMatDataHandler.getShoppingCart().addShoppingCartListener(this);

        pathLabel.setText(iMatDirectory);

        fullView.prefWidthProperty().bind(stackPane.widthProperty());
        fullView.prefHeightProperty().bind(stackPane.heightProperty());
        detailView.prefWidthProperty().bind(stackPane.widthProperty());
        detailView.prefHeightProperty().bind(stackPane.heightProperty());

        createProductCards();

        //for(int i = 0; i < 100; i++){
        //    ProductFlowPane.getChildren().add(new ProductCard(new Product(), iMatDataHandler));
        //}
        for(int i = 0; i < 6; i++){
            VarukorgFlowPane.getChildren().add(new VarukorgItem(new Product(), iMatDataHandler, false));
        }

    }
    private void createProductCards(){
        List<Product> products;
        if(selectedCategory == null){ products = iMatDataHandler.getProducts(); }
        else{ products = iMatDataHandler.getProducts(selectedCategory); }
        for(Product product : products){
            ProductFlowPane.getChildren().add(new ProductCard(new Product(), iMatDataHandler));
        }
    }

    public void shoppingCartChanged(CartEvent event){
        SumPrice.setText(String.valueOf(iMatDataHandler.getShoppingCart().getTotal()));
    }
    @FXML
    public void openDetailView(){
        detailAnchor.toFront();}
    @FXML
    public void closeDetailView() {
        fullView.toFront();
    }
    @FXML
    public void closeImageMouseEntered(){
        closeDetailView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/icon_close_hover.png")));
    }

    @FXML
    public void closeImageMousePressed(){
        closeDetailView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/icon_close_pressed.png")));
    }

    @FXML
    public void closeImageMouseExited(){
        closeDetailView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/icon_close.png")));
    }
    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }
    private void populateDetailView(IMatDataHandler iMatDataHandler){
        detailProductLabel.setText(model.getProductName());
        detailProductImage.setImage(iMatDataHandler.getFXImage(model.getProduct(product.getProductId())));
        detailPriceLabel.setText(String.valueOf(product.getPrice() + "kr"));
        detailCategoriLabel.setText(String.valueOf(product.getCategory()));
        detailArea.setText(productDetail.getDescription());
        detailAreaContent.setText(productDetail.getContents());
        detailCategoriLabel.setText(String.valueOf(product.getCategory()));


    }

}