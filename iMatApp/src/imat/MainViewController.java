
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
import javafx.scene.control.TextField;
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

    @FXML private TextField SearchBar;
    @FXML private ImageView SearchImage;

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
        initializeProductCards();

        setSelectedCategory(null);
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

    private void showErbjudanden(){
        ErbjudandenPane.setVisible(true);
        AnchorPane.setTopAnchor(BrowsePane,null);
        CenterStageNameLabel.setText("Erbjudanden");
    }
    private void hideErbjudanden(){
        ErbjudandenPane.setVisible(false);
        AnchorPane.setTopAnchor(BrowsePane, 0.0);
    }

    public void setSelectedCategory(ProductCategory category) {
        if(category == null){
            showErbjudanden();
            showProductCards(iMatDataHandler.getProducts());
            return;
        }
        else{ hideErbjudanden(); }
        CenterStageNameLabel.setText(categoryToString(category));
        this.selectedCategory = category;
        showProductCards(iMatDataHandler.getProducts(selectedCategory));
    }

    private void initializeProductCards(){
        List<ShoppingItem> items = iMatDataHandler.getShoppingCart().getItems();
        ProductCard pc;
        ShoppingItem si;
        for(Product product : iMatDataHandler.getProducts()){
            si = new ShoppingItem(product,0);
            for(ShoppingItem item : items){
                if(item.getProduct().getProductId() == product.getProductId()){
                    si = item;
                    iMatDataHandler.getShoppingCart().fireShoppingCartChanged(si,(si.getAmount()>0));
                }
            }
            pc = new ProductCard(si,this);
            productCardMap.put(product.getProductId(),pc);
        }
    }

    private void showProductCards(List<Product> products){
        ProductFlowPane.getChildren().clear();
        for(Product product : products){
            ProductFlowPane.getChildren().add(productCardMap.get(product.getProductId()));
        }
        ProductScrollPane.setVvalue(0.0);
    }

    public String categoryToString(ProductCategory category){
        return translationMap.get(category);
    }

    private void createCategoryList(){
        CategoryFlowPane.getChildren().clear();
        CategoryFlowPane.getChildren().add(new CategoryListItem(null,this));
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
            if( vi.getShoppingItem().getProduct().getProductId() == shoppingItem.getProduct().getProductId()){
                vi.updateAmount();
                productCardMap.get(vi.getShoppingItem().getProduct().getProductId()).updateAmount();
            }
            if(vi.getShoppingItem().getAmount() > 0){
                newList.add(vi);
            }
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
    public void searchImageClick(Event event){
        if(SearchBar.getText() == ""){ return; }
        hideErbjudanden();
        CenterStageNameLabel.setText("\""+SearchBar.getText()+"\"");
        showProductCards((iMatDataHandler.findProducts(SearchBar.getText())));
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