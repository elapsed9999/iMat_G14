
package imat;

import java.net.URL;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    @FXML private FlowPane ErbjudandenFlowPane;
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
    @FXML private ComboBox cardTypeCombo;
    @FXML private TextField betalningName;
    @FXML private TextField betalningPersonnummer;
    @FXML private TextField betalningCardNumber;
    @FXML private TextField betalningExpiringDate;
    @FXML private TextField betalningCVC;
    @FXML private TextField betalningExpiringDateYear;
    @FXML private ComboBox profilCardTypeCombo;
    @FXML private TextField profilBetalningName;
    @FXML private TextField profilBetalningCardNumber;
    @FXML private TextField profilBetalningExpiringdate;
    @FXML private TextField profilBetalningExpiringdateYear;

    @FXML private TextField profilBetalningCVC;




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
    @FXML private AnchorPane profileAnchor;
    @FXML private AnchorPane endScreen;


    @FXML private StackPane stackPane;

    @FXML private Label SumPriceMain;
    @FXML private Label SumPriceVarukorg;
    @FXML private TextField profileName;
    @FXML private TextField profilePhone;
    @FXML private TextField profileAdress;
    @FXML private TextField profilePostCode;
    @FXML private TextField profileEmail;
    @FXML private TextField profilePostCity;
    private Label SumPrice;

    @FXML private StackPane ProfileStackPane;
    @FXML private AnchorPane EditProfilePane;
    @FXML private AnchorPane TidigareKöpPane;
    @FXML private FlowPane TidigareKöpFlowPane;
    @FXML private AnchorPane OrderDetailPane;
    @FXML private Label ProfileTitle;
    @FXML private AnchorPane ProfileMenuProfile;
    @FXML private AnchorPane ProfileMenuTidigareKöp;
    @FXML private Label OrderDetailSum;
    @FXML private Label OrderDetailDate;
    @FXML private FlowPane OrderDetailVarukorgFlowPane;
    @FXML private AnchorPane profilCreditCardAnchor;
    @FXML private AnchorPane ProfileMenuTidigareKöp1;

    @FXML private Label DetailViewEkologiskt;

    private ProductCategory selectedCategory = null;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private HashMap<Integer,ProductCard> productCardMap = new HashMap<>();

    private HashMap<ProductCategory,String> translationMap = new HashMap<>();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();
        iMatDataHandler.getShoppingCart().addShoppingCartListener(this);

        pathLabel.setText(iMatDirectory);

        setWindowSize();

        VarukorgFlowPane = SmallVarukorgFlowPane;
        SumPrice = SumPriceMain;

        initializeTranslation();
        createCategoryList();
        initializeProductCards();
        setSelectedCategory(null);
        initializeIntegerTextFields();
        fillProfile();
        betalningsMetod();
        fillDeliveryPane();
        fillBetalningPane();

        Customer customer = iMatDataHandler.getCustomer();
        if (customer == null) {
            customer.setFirstName("Default");
            customer.setLastName("User");
            customer.setAddress("Default Address");
            customer.setPostCode("00000");
            customer.setEmail("default@example.com");
            customer.setMobilePhoneNumber("0000000000");
            customer.setPostCode("0000");
        }
        initializeProfileTextFields();

// Add listener to profileName TextField
    }
    private void initializeProfileTextFields(){
        profileName.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setFirstName(newValue);
            profileInformation();
        });
        profilePhone.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setMobilePhoneNumber(newValue);
            profileInformation();
        });
        profileAdress.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setAddress(newValue);
            profileInformation();
        });

        profilePostCode.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setPostCode(newValue);
            profileInformation();
        });
        profileEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setEmail(newValue);
            profileInformation();
        });
        profilePostCity.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCustomer().setPostAddress(newValue);
            profileInformation();
        });
        cardTypeCombo.getItems().addAll("Välj kort", "Mastercard", "Visa", "American Express");
        cardTypeCombo.getSelectionModel().select("Välj kort");
        profilCardTypeCombo.getItems().addAll("Välj kort", "Mastercard", "Visa", "American Express");
        profilCardTypeCombo.getSelectionModel().select("Välj kort");

        profilCardTypeCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setCardType((String) newValue);
            BetalningsMetodInfo();
        });
        profilBetalningCardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setCardNumber(newValue);
            BetalningsMetodInfo();
        });
        profilBetalningCVC.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setVerificationCode(Integer.parseInt(newValue));
            BetalningsMetodInfo();
        });
        profilBetalningName.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setHoldersName(newValue);
            BetalningsMetodInfo();
        });
        profilBetalningExpiringdate.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setValidMonth(Integer.parseInt(newValue));
            BetalningsMetodInfo();
        });
        profilBetalningExpiringdateYear.textProperty().addListener((observable, oldValue, newValue) -> {
            iMatDataHandler.getCreditCard().setValidYear(Integer.parseInt(newValue));
            BetalningsMetodInfo();
        });

    }

    private void setWindowSize() {
        List<AnchorPane> anchorPanes = Arrays.asList(fullView,detailAnchor,leveransAnchor,varukorgAnchor,profileAnchor,
                betalningAnchor,huvudbetalning,endScreen);
        for(AnchorPane pane : anchorPanes){
            pane.prefWidthProperty().bind(stackPane.widthProperty());
            pane.prefHeightProperty().bind(stackPane.heightProperty());
        }
        anchorPanes = Arrays.asList(EditProfilePane,TidigareKöpPane, profilCreditCardAnchor);
        for(AnchorPane pane : anchorPanes){
            pane.prefWidthProperty().bind(ProfileStackPane.widthProperty());
            pane.prefHeightProperty().bind(ProfileStackPane.heightProperty());
        }
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

    private void initializeErbjudanden(){
        Random rand = new Random();
        List<Product> products = iMatDataHandler.getProducts();
        ArrayList<Integer> randNumbers = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            int r = rand.nextInt(products.size());
            while(randNumbers.contains(r)){
                r = rand.nextInt(products.size());
            }
            randNumbers.add(r);
            int id = products.get(r).getProductId();
            ProductCard pc = productCardMap.get(id);
            ErbjudandenFlowPane.getChildren().add(new ProductCardSmall(pc.getShoppingItem(),this));
        }
    }

    private void showErbjudanden(){
        ErbjudandenPane.setVisible(true);
        AnchorPane.setTopAnchor(BrowsePane,null);
        CenterStageNameLabel.setText("ERBJUDANDEN");
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
        CenterStageNameLabel.setText(categoryToString(category).toUpperCase());
        this.selectedCategory = category;
        showProductCards(iMatDataHandler.getProducts(selectedCategory));
    }

    private void initializeProductCards(){
        productCardMap.clear();
        List<ShoppingItem> items = iMatDataHandler.getShoppingCart().getItems();
        ProductCard pc;
        ShoppingItem si;
        outerloop:
        for(Product product : iMatDataHandler.getProducts()){
            si = new ShoppingItem(product,0);
            for(ShoppingItem item : items){
                if(item.getProduct().getProductId() == product.getProductId()){
                    si = item;
                    createProductCard(si);
                    iMatDataHandler.getShoppingCart().fireShoppingCartChanged(si,(si.getAmount()>0));
                    continue outerloop;
                }
            }
            createProductCard(si);
        }
        initializeErbjudanden();
    }

    private void createProductCard(ShoppingItem si){
        ProductCard pc = new ProductCard(si,this);
        productCardMap.put(si.getProduct().getProductId(),pc);
        pc.updateAmount();
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
        String total = String.format("%.2f",iMatDataHandler.getShoppingCart().getTotal());
        SumPrice.setText(total);

        if(event.isAddEvent()){
            addVarukorgListItem(event.getShoppingItem());
        } else if(iMatDataHandler.getShoppingCart().getItems().size() == 0) {
            if(event.getShoppingItem() == null){ VarukorgFlowPane.getChildren().clear(); }
            else{
                openMainView();
            }

        }
        updateVarukorgList(event.getShoppingItem());
    }

    private void setVarukorg(FlowPane Varukorg,Label Sum, Boolean large){
        if(Varukorg == VarukorgFlowPane){ return; }
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
    @FXML public void openProfileBetalning(){
        ProfileTitle.setText("Betalningsinformation");
        ProfileMenuProfile.getStyleClass().clear();
        ProfileMenuProfile.getStyleClass().add("list-item");
        ProfileMenuTidigareKöp.getStyleClass().clear();
        ProfileMenuTidigareKöp.getStyleClass().add("list-item");
        ProfileMenuTidigareKöp1.getStyleClass().clear();
        ProfileMenuTidigareKöp1.getStyleClass().add("green-button");
        betalningsMetod();
        profilCreditCardAnchor.toFront();
    }

    public void openOrderDetailView(Order order){
        populateOrderDetailView(order);
        OrderDetailPane.setVisible(true);
    }
    @FXML public void openProfileView(){
        fillProfile();
        EditProfilePane.toFront();
        ProfileTitle.setText("Profilinformation");
        ProfileMenuProfile.getStyleClass().clear();
        ProfileMenuProfile.getStyleClass().add("green-button");
        ProfileMenuTidigareKöp.getStyleClass().clear();
        ProfileMenuTidigareKöp.getStyleClass().add("list-item");
        ProfileMenuTidigareKöp1.getStyleClass().clear();
        ProfileMenuTidigareKöp1.getStyleClass().add("list-item");
        profileAnchor.toFront();
    }

    @FXML public void openTidigareKöp(){
        TidigareKöpPane.toFront();
        OrderDetailPane.setVisible(false);
        ProfileTitle.setText("Tidigare köp");
        ProfileMenuProfile.getStyleClass().clear();
        ProfileMenuProfile.getStyleClass().add("list-item");
        ProfileMenuTidigareKöp.getStyleClass().clear();
        ProfileMenuTidigareKöp.getStyleClass().add("green-button");
        ProfileMenuTidigareKöp1.getStyleClass().clear();
        ProfileMenuTidigareKöp1.getStyleClass().add("list-item");
        TidigareKöpFlowPane.getChildren().clear();
        ListIterator<Order> orders = iMatDataHandler.getOrders().listIterator(iMatDataHandler.getOrders().size());
        while(orders.hasPrevious()){
            TidigareKöpFlowPane.getChildren().add(new OrderListItem(orders.previous(),this));
        }
    }
    @FXML public void closeDetailView() {
        fullView.toFront();
    }

    @FXML public void varukorgToFront(){
        if(iMatDataHandler.getShoppingCart().getItems().size() == 0){ return; }
        varukorgAnchor.toFront();
        setVarukorg(LargeVarukorgFlowPane,SumPriceVarukorg,true);
    }
    @FXML public void openMainView(){
        fullView.toFront();
        setSelectedCategory(null);
        setVarukorg(SmallVarukorgFlowPane,SumPriceMain,false);
    }
    @FXML public void betalningToFront(){
        fillBetalningPane();
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
        fillDeliveryPane();
        leveransAnchor.toFront();
    }
    @FXML public void endScreenToFront(){
        iMatDataHandler.placeOrder();
        initializeProductCards();
        endScreen.toFront();
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
        if(product.isEcological()){
            DetailViewEkologiskt.setVisible(true);
        }else{DetailViewEkologiskt.setVisible(false);}
    }

    private void populateOrderDetailView(Order order){
        double price = 0;
        for(ShoppingItem item : order.getItems()){
            price += item.getTotal();
        }
        String priceText = String.format("%.2f",price);
        OrderDetailSum.setText(priceText);
        String dateText = order.getDate().toString();
        OrderDetailDate.setText(dateText);
        OrderDetailVarukorgFlowPane.getChildren().clear();
        for(ShoppingItem item : order.getItems()){
            OrderDetailVarukorgFlowPane.getChildren().add(new OrderVarukorgItem(item, this));
        }
    }
    public void profileInformation () {
        iMatDataHandler.getCustomer().setFirstName(profileName.getText());
        iMatDataHandler.getCustomer().setAddress(profileAdress.getText());
        iMatDataHandler.getCustomer().setMobilePhoneNumber(profilePhone.getText());
        iMatDataHandler.getCustomer().setPostCode(profilePostCode.getText());
        iMatDataHandler.getCustomer().setPostAddress(profilePostCity.getText());
        iMatDataHandler.getCustomer().setEmail(profileEmail.getText());
    }
    public void fillDeliveryPane () {
        leveransName.setText(profileName.getText());
        leveransAdress.setText(profileAdress.getText());
        leveransPostCode.setText(profilePostCode.getText());
        leveransEmail.setText(profileEmail.getText());
        leveransNumber.setText(profilePhone.getText());
        leveransCity.setText(profilePostCity.getText());
    }
    public void fillProfile () {
        if (iMatDataHandler.getCustomer() == null) {
            profileName.setText("Default User");
            profileAdress.setText("Default Address");
            profilePostCode.setText("00000");
            profileEmail.setText("default@example.com");
            profilePhone.setText("0000000000");
            profilePostCity.setText("Göteborg");
        } else {
            profileName.setText(iMatDataHandler.getCustomer().getFirstName());
            profileAdress.setText(iMatDataHandler.getCustomer().getAddress());
            profilePostCode.setText(iMatDataHandler.getCustomer().getPostCode());
            profileEmail.setText(iMatDataHandler.getCustomer().getEmail());
            profilePhone.setText(iMatDataHandler.getCustomer().getMobilePhoneNumber());
            profilePostCity.setText(iMatDataHandler.getCustomer().getPostAddress());
        }

    }
    public void betalningsMetod(){
        profilCardTypeCombo.setValue(iMatDataHandler.getCreditCard().getCardType());
        profilBetalningName.setText(iMatDataHandler.getCreditCard().getHoldersName());
        profilBetalningCVC.setText(String.valueOf(iMatDataHandler.getCreditCard().getVerificationCode()));
        profilBetalningExpiringdate.setText(String.valueOf(iMatDataHandler.getCreditCard().getValidMonth()));
        profilBetalningCardNumber.setText(iMatDataHandler.getCreditCard().getCardNumber());
        profilBetalningExpiringdateYear.setText(String.valueOf(iMatDataHandler.getCreditCard().getValidYear()));
    }
    public void BetalningsMetodInfo(){
        iMatDataHandler.getCreditCard().setCardType((String) profilCardTypeCombo.getValue());
        iMatDataHandler.getCreditCard().setCardNumber(profilBetalningCardNumber.getText());
        iMatDataHandler.getCreditCard().setValidYear(Integer.parseInt(profilBetalningExpiringdateYear.getText()));
        iMatDataHandler.getCreditCard().setValidMonth(Integer.parseInt(profilBetalningExpiringdate.getText()));
        iMatDataHandler.getCreditCard().setHoldersName(profilBetalningName.getText());
        iMatDataHandler.getCreditCard().setVerificationCode(Integer.parseInt(profilBetalningCVC.getText()));
    }
    public void fillBetalningPane () {
        cardTypeCombo.setValue(profilCardTypeCombo.getValue());
        betalningName.setText(profilBetalningName.getText());
        betalningCVC.setText(profilBetalningCVC.getText());
        betalningExpiringDate.setText(profilBetalningExpiringdate.getText());
        betalningExpiringDateYear.setText(profilBetalningExpiringdateYear.getText());
        betalningCardNumber.setText(profilBetalningCardNumber.getText());
    }

    private void initializeIntegerTextFields(){
        Map<TextField,Integer> fields = Map.of(
                betalningCardNumber, 16,
                betalningCVC, 3,
                profilePostCode, 5,
                profilePhone, 10,
                betalningExpiringDate, 2,
                betalningExpiringDateYear, 2,
                profilBetalningCardNumber, 16,
                profilBetalningExpiringdate, 2,
                profilBetalningExpiringdateYear, 2,
                profilBetalningCVC, 3);
        for(TextField tf : fields.keySet()) {
            tf.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        tf.setText(newValue.replaceAll("[^\\d]", ""));
                    } if(newValue.length() > fields.get(tf)){
                        tf.setText(newValue.substring(0,fields.get(tf)));
                    }
                }
            });
        }
    }
}