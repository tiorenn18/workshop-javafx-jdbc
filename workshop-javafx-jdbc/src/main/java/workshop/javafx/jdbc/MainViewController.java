package workshop.javafx.jdbc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import workshop.javafx.jdbc.gui.util.Alerts;
import workshop.javafx.jdbc.model.service.DepartmentService;

public class MainViewController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        loadView("DepartmentList.fxml", (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuItemAboutrAction() {
        loadView("About.fxml", x -> {});
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();

            Scene scene = App.getMaiScene();
            VBox mainVbox = (VBox) ((ScrollPane) scene.getRoot()).getContent();

            Node mainMenu = mainVbox.getChildren().get(0);

            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbox.getChildren());

            T controller = loader.getController();
            initializingAction.accept(controller);
            
        } catch (IOException e) {
            Alerts.showAlerts("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
}
