package workshop.javafx.jdbc;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import workshop.javafx.jdbc.db.DbException;
import workshop.javafx.jdbc.gui.listeners.DataChangeListener;
import workshop.javafx.jdbc.gui.util.Alerts;
import workshop.javafx.jdbc.gui.util.Constraints;
import workshop.javafx.jdbc.gui.util.Utils;
import workshop.javafx.jdbc.model.entities.Seller;
import workshop.javafx.jdbc.model.exceptions.ValidationException;
import workshop.javafx.jdbc.model.service.SellerService;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @SuppressWarnings("exports")
    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    @SuppressWarnings("exports")
    public void setSellerService(SellerService service) {
        this.service = service;
    }

    @SuppressWarnings("exports")
    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    @SuppressWarnings("exports")
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();

        }
        catch (ValidationException e ){
            setErrorMessages(e.getErrors());
        }
        catch (DbException e) {
            Alerts.showAlerts("Error saving object", null, e.getMessage(), AlertType.ERROR);
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    @SuppressWarnings("exports")
    public Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation exception");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "Field can't be empty");
        }

        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        obj.setName(txtName.getText());

        return obj;
    }

    @FXML
    @SuppressWarnings("exports")
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }

}
