
package imat;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    @FXML private AnchorPane BrowsePane;
    @FXML private AnchorPane CenterStagePane;
    @FXML private Label CenterStageNameLabel;
    @FXML private AnchorPane ErbjudandenPane;

    @FXML private FlowPane ProductFlowPane;
    @FXML private ScrollPane ProductScrollPane;
    @FXML private FlowPane VarukorgFlowPane;
    @FXML private FlowPane CategoryFlowPane;

    @FXML private StackPane stackPane;

    @FXML private Label SumPrice;

    private Model model;
    private Product product;
    private ProductDetail productDetail;
    private ProductCategory selectedCategory = null;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private HashMap<Integer,ProductCard> productCardMap = new HashMap<>();

    private HashMap<ProductCategory,String> translationMap = new HashMap<>();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();
        iMatDataHandler.getShoppingCart().addShoppingCartListener(this);

        pathLabel.setText(iMatDirectory);

        fullView.prefWidthProperty().bind(stackPane.widthProperty());
        fullView.prefHeightProperty().bind(stackPane.heightProperty());
        detailView.prefWidthProperty().bind(stackPane.widthProperty());
        detailView.prefHeightProperty().bind(stackPane.heightProperty());

        initializeTranslation();

        createProductCards();
        createCategoryList();

    }

    private void initializeTranslation(){
        translationMap.put(ProductCategory.POD, "Baljväxter");
        translationMap.put(ProductCategory.BREAD, "Bröd");
        translationMap.put(ProductCategory.BERRY, "Bär");
        translationMap.put(ProductCategory.CITRUS_FRUIT, "Citrusfrukt");
        translationMap.put(ProductCategory.HOT_DRINKS, "Varm dryck");
        translationMap.put(ProductCategory.COLD_DRINKS, "Kall dryck");
        translationMap.put(ProductCategory.EXOTIC_FRUIT, "Exotisk frukt");
        translationMap.put(ProductCategory.FISH, "Fisk");
        translationMap.put(ProductCategory.VEGETABLE_FRUIT, "Grönsaker");
        translationMap.put(ProductCategory.CABBAGE, "Kål");
        translationMap.put(ProductCategory.MEAT, "Kött");
        translationMap.put(ProductCategory.DAIRIES, "Mejeriprodukter");
        translationMap.put(ProductCategory.MELONS, "Melon");
        translationMap.put(ProductCategory.FLOUR_SUGAR_SALT, "Mjöl, socker och salt");
        translationMap.put(ProductCategory.NUTS_AND_SEEDS, "Nötter och frön");
        translationMap.put(ProductCategory.PASTA, "Pasta");
        translationMap.put(ProductCategory.POTATO_RICE, "Potatis och ris");
        translationMap.put(ProductCategory.ROOT_VEGETABLE, "Rotfrukter");
        translationMap.put(ProductCategory.FRUIT, "Frukt");
        translationMap.put(ProductCategory.SWEET, "Sött");
        translationMap.put(ProductCategory.HERB, "Örter");
    }

    public void setSelectedCategory(ProductCategory category) {
        if(selectedCategory == null){
            CenterStagePane.getChildren().remove(ErbjudandenPane);
            AnchorPane.setTopAnchor(BrowsePane,0.0);
        }
        CenterStageNameLabel.setText(categoryToString(category));
        this.selectedCategory = category;
        createProductCards();
    }

    private void createProductCards(){
        ProductFlowPane.getChildren().clear();
        productCardMap.clear();
        List<Product> products;
        if(selectedCategory == null){ products = iMatDataHandler.getProducts(); }
        else{ products = iMatDataHandler.getProducts(selectedCategory); }
        ProductCard pc;
        for(Product product : products){
            pc = new ProductCard(new ShoppingItem(product, 0), this);
            productCardMap.put(product.getProductId(),pc);
            ProductFlowPane.getChildren().add(pc);
        }
        ProductScrollPane.setVvalue(0.0);
    }

    public String categoryToString(ProductCategory category){
        return translationMap.get(category);
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
            if(vi.getShoppingItem().getProduct().getProductId() == shoppingItem.getProduct().getProductId()){
                return;
            }
        }
        VarukorgFlowPane.getChildren().add(new VarukorgItem(shoppingItem,this, false));
    }

    public void updateVarukorgList(ShoppingItem shoppingItem){
        ArrayList<VarukorgItem> newList = new ArrayList<VarukorgItem>();
        for(Node item : VarukorgFlowPane.getChildren()){
            VarukorgItem vi = (VarukorgItem) item;
            if(vi.getShoppingItem().getAmount() <= 0){
                continue;
            }
            if( vi.getShoppingItem().getProduct().getProductId() == shoppingItem.getProduct().getProductId()){
                vi.updateAmount();
                productCardMap.get(vi.getShoppingItem().getProduct().getProductId()).updateAmount();
            }
            newList.add(vi);
        }
        VarukorgFlowPane.getChildren().clear();
        for(VarukorgItem item : newList){ VarukorgFlowPane.getChildren().add(item); }
    }
    public void shoppingCartChanged(CartEvent event){
        String total = String.format("%2f",iMatDataHandler.getShoppingCart().getTotal());
        SumPrice.setText(total);

        if(event.isAddEvent()){
            addVarukorgListItem(event.getShoppingItem());
        }
        updateVarukorgList(event.getShoppingItem());
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