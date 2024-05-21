package imat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

public class CategoryListItem extends AnchorPane {
    @FXML private Label Name;
    private ProductCategory category;
    private MainViewController parentController;
    public CategoryListItem(ProductCategory category, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category_list_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        this.category = category;
        this.parentController = parentController;
        if(category == null){ Name.setText("Startsida"); }
        else{ Name.setText(parentController.categoryToString(category)); }
    }

    public void onClick(Event event){
        parentController.setSelectedCategory(this.category);
    }
}
