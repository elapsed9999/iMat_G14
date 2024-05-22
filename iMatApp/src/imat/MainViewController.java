
package imat;

import java.net.URL;
import java.util.*;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;

public class MainViewController implements Initializable, ShoppingCartListener {

    @FXML private Label pathLabel;
    @FXML private AnchorPane detailAnchor;
    @FXML private AnchorPane detailView;
    @FXML private ImageView closeDetailView;
    @FXML private AnchorPane varukorgAnchor;
    @FXML private AnchorPane leveransAnchor;
    @FXML private AnchorPane betalningAnchor;
    @FXML private AnchorPane fullView;
    @FXML private ImageView detailProductImage;
    @FXML private TextArea detailArea;
    @FXML private TextArea detailAreaContent;
    @FXML private Label detailProductLabel;
    @FXML private Label detailPriceLabel;
    @FXML private Label detailCategoriLabel;
    @FXML private AnchorPane BrowsePane;
    @FXML private AnchorPane CenterStagePane;
    @FXML private Label CenterStageNameLabel;
    @FXML private AnchorPane ErbjudandenPane;
    @FXML private Button ReturnButton1;
    @FXML private Button ContinueButton1;
    @FXML private Button ContinueButton;
    @FXML private Button ReturnButton;
    @FXML private Button ContinueShoppingButton;
    @FXML private Label kortBetalning;
    @FXML private AnchorPane huvudbetalning;
    @FXML private TextField leveransName;
    @FXML private TextField leveransEmail;
    @FXML private TextField leveransAdress;
    @FXML private TextField leveransPostCode;
    @FXML private TextField leveransCity;
    @FXML private TextField leveransNumber;

    @FXML private Label swishBetalning;
    @FXML private Label klarnaBetalning;
    @FXML private AnchorPane kortBetalningAnchor;
    @FXML private AnchorPane swishBetalningAnchor;
    @FXML private AnchorPane klarnaBetalningAnchor;

    @FXML private Button returnButton2;
    @FXML private Button ContinueButton2;

    @FXML private TextField SearchBar;
    @FXML private ImageView SearchImage;

    @FXML private FlowPane ProductFlowPane;
    @FXML private ScrollPane ProductScrollPane;
    @FXML private FlowPane SmallVarukorgFlowPane;
    @FXML private FlowPane LargeVarukorgFlowPane;
    private FlowPane VarukorgFlowPane;
    @FXML private FlowPane CategoryFlowPane;

    @FXML private StackPane stackPane;

    @FXML private Label SumPriceMain;
    @FXML private Label SumPriceVarukorg;
    private Label SumPrice;

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
        leveransAnchor.prefHeightProperty().bind(stackPane.heightProperty());
        leveransAnchor.prefHeightProperty().bind(stackPane.heightProperty());
        varukorgAnchor.prefHeightProperty().bind(stackPane.heightProperty());
        varukorgAnchor.prefHeightProperty().bind(stackPane.heightProperty());
        betalningAnchor.prefHeightProperty().bind(stackPane.heightProperty());
        betalningAnchor.prefHeightProperty().bind(stackPane.heightProperty());

        VarukorgFlowPane = SmallVarukorgFlowPane;
        SumPrice = SumPriceMain;

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
        hideErbjudanden();
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

    private void setVarukorg(FlowPane Varukorg,Label Sum, Boolean large){
        String text = SumPrice.getText();
        SumPrice = Sum;
        SumPrice.setText(text);
        for(Node item : VarukorgFlowPane.getChildren()){
            VarukorgItem vi = (VarukorgItem) item;
            Varukorg.getChildren().add(new VarukorgItem(vi.getShoppingItem(),this,large));
        }
        VarukorgFlowPane.getChildren().clear();
        VarukorgFlowPane = Varukorg;
    }
    @FXML public void openDetailView(Product product){
        populateDetailView(product);
        detailAnchor.toFront();
    }
    @FXML public void closeDetailView() {
        fullView.toFront();
    }

    @FXML public void varukorgToFront(){
        varukorgAnchor.toFront();
        setVarukorg(LargeVarukorgFlowPane,SumPriceVarukorg,true);
    }
    @FXML public void openMainView(){
        fullView.toFront();
        showErbjudanden();
        setVarukorg(SmallVarukorgFlowPane,SumPriceMain,false);
    }
    @FXML public void betalningToFront(){
        betalningAnchor.toFront();
    }
    @FXML public void openKortbetalning(){
        kortBetalningAnchor.toFront();
    }
    @FXML public void openSwishbetalning(){
        swishBetalningAnchor.toFront();
    }
    @FXML public void openKlarnabetalning(){
        klarnaBetalningAnchor.toFront();
    }

    @FXML public void leveransToFront(){
        leveransAnchor.toFront();
    }

    @FXML public void returnCheckoutView5(){
        huvudbetalning.toFront();
    }
    @FXML public void closeImageMouseEntered(){
        closeDetailView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/icon_close_hover.png")));
    }

    @FXML public void closeImageMousePressed(){
        closeDetailView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/icon_close_pressed.png")));
    }

    @FXML public void closeImageMouseExited(){
        closeDetailView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/icon_close.png")));
    }

    @FXML public void searchImageClick(Event event){
        if(SearchBar.getText() == ""){ return; }
        hideErbjudanden();
        CenterStageNameLabel.setText("\""+SearchBar.getText()+"\"");
        showProductCards((iMatDataHandler.findProducts(SearchBar.getText())));
    }
    @FXML public void mouseTrap(Event event){
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