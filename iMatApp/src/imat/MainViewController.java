
package imat;

import java.net.URL;
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
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductDetail;

public class MainViewController implements Initializable {

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

    @FXML
    FlowPane ProductFlowPane;



    private Model model;
    private Product product;
    private ProductDetail productDetail;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        pathLabel.setText(iMatDirectory);

        for(int i = 0; i < 10; i++){
            ProductFlowPane.getChildren().add(new ProductCard(new Product(), iMatDataHandler));
        }

    }
    @FXML
    public void openDetailView(){
        detailAnchor.toFront();}
    @FXML
    public void closeDetailView() {
        fullView.toFront();
        System.out.println(ProductFlowPane.getChildren());
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