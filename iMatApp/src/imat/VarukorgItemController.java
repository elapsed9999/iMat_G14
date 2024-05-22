package imat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class VarukorgItemController extends AnchorPane {
    protected ShoppingItem shoppingItem;
    protected MainViewController parentController;
    @FXML protected Label Amount;
    @FXML protected Button MinusButton;
    @FXML protected Button PlusButton;
    public void updateAmount(){
        String amount_string;
        if(shoppingItem.getAmount() == (int)shoppingItem.getAmount()){
            amount_string = String.valueOf((int)shoppingItem.getAmount());
        }else{
            amount_string = String.valueOf(shoppingItem.getAmount());
        }
        Amount.setText(amount_string + " " + shoppingItem.getProduct().getUnitSuffix());
    }

    public void minusButtonClick(Event event){
        ShoppingCart sc = parentController.iMatDataHandler.getShoppingCart();
        shoppingItem.setAmount(shoppingItem.getAmount()-1);
        if(shoppingItem.getAmount() <= 0){
            sc.removeItem(shoppingItem);
            shoppingItem.setAmount(0);
        }else{ sc.fireShoppingCartChanged(shoppingItem,false); }
    }
    public void plusButtonClick(Event event){
        ShoppingCart sc = parentController.iMatDataHandler.getShoppingCart();
        shoppingItem.setAmount(shoppingItem.getAmount()+1);
        if(shoppingItem.getAmount() <= 1){
            sc.addItem(shoppingItem);
        }else{ sc.fireShoppingCartChanged(shoppingItem,false); }
    }

    public ShoppingItem getShoppingItem(){ return shoppingItem; }
}
