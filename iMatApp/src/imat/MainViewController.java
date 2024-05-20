
package imat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    @FXML private FlowPane CategoryFlowPane;

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
        createCategoryList();

    }

    public void setSelectedCategory(ProductCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
        createProductCards();
    }

    private void createProductCards(){
        ProductFlowPane.getChildren().clear();
        List<Product> products;
        if(selectedCategory == null){ products = iMatDataHandler.getProducts(); }
        else{ products = iMatDataHandler.getProducts(selectedCategory); }
        for(Product product : products){
            ProductFlowPane.getChildren().add(new ProductCard(product, this));
        }
    }

    public String categoryToString(ProductCategory category){
        return String.valueOf(category).replaceAll("_"," ");
    }

    private void createCategoryList(){
        CategoryFlowPane.getChildren().clear();
        for(ProductCategory category : ProductCategory.values()){
            CategoryFlowPane.getChildren().add(new CategoryListItem(category, this));
        }
    }

    private void addVarukorgListItem(ShoppingItem shoppingItem){
        for(Node item : VarukorgFlowPane.getChildren()){
            VarukorgItem vi = (VarukorgItem) item;
            if(vi.getShoppingItem() == shoppingItem){
                vi.plusOne();
                return;
            }
        }
        VarukorgFlowPane.getChildren().add(new VarukorgItem(shoppingItem,this, false));
    }
    public void shoppingCartChanged(CartEvent event){
        String total = String.format("%2f",iMatDataHandler.getShoppingCart().getTotal());
        SumPrice.setText(total);

        if(event.isAddEvent()){
            addVarukorgListItem(event.getShoppingItem());
        }
    }
    @FXML
    public void openDetailView(Product product){
        populateDetailView(product);
        detailAnchor.toFront();
    }
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
    private void populateDetailView(Product product){
        detailProductLabel.setText(product.getName());
        detailProductImage.setImage(iMatDataHandler.getFXImage(product));
        detailPriceLabel.setText(String.valueOf(product.getPrice() + " " + product.getUnit()));
        detailCategoriLabel.setText(categoryToString(product.getCategory()));
        detailArea.setText(iMatDataHandler.getDetail(product).getDescription());
        detailAreaContent.setText(iMatDataHandler.getDetail(product).getContents());


    }

}