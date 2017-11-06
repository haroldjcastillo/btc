package com.github.haroldjcastillo.btc.ui;

import com.github.haroldjcastillo.btc.ws.TradeObserver;
import com.github.haroldjcastillo.rxws.WebSocket;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLController extends AbstractController implements Initializable {

    @FXML
    private JFXTextField top;

    @FXML
    private ImageView closeButton;

    @FXML
    private TableView bidsTableView;

    @FXML
    private TableView asksTableView;

    @FXML
    private void closeeButtonAction(MouseEvent event) {
        try {
            final Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
            WebSocket.getInstance().disconnect();
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private void loadListeners() {
        top.textProperty().addListener((obsservable, oldValue, newValue) -> {
            if (newValue.matches("\\d{1,10}")) {
                BEST.set(Integer.valueOf(newValue));
            } else {
               top.setText(oldValue);
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        top.setText(BEST.toString());
        addOrderPayloadColumn(bidsTableView);
        addOrderPayloadColumn(asksTableView);
        asksTableView.setItems(ASKS);
        bidsTableView.setItems(BIDS);
        loadListeners();
        connectWebsocket();
    }

    public void connectWebsocket() {
        final String[] types = {"trades", "diff-orders", "orders"};
        final String[] channels = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            final String frameMessage = "{ \"action\": \"subscribe\", \"book\": \"btc_mxn\", \"type\": \"" + types[i]
                    + "\" }";
            channels[i] = frameMessage;
        }
        WebSocket.getInstance()
                .connect(Arrays.asList(new TradeObserver())).addChannels(channels);
    }

    public void addOrderPayloadColumn(final TableView table) {
        final TableColumn rate = new TableColumn("Rate");
        rate.setCellValueFactory(new PropertyValueFactory<>("r"));
        rate.prefWidthProperty().bind(table.widthProperty().divide(5));
        final TableColumn amount = new TableColumn("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<>("a"));
        amount.prefWidthProperty().bind(table.widthProperty().divide(5));
        final TableColumn type = new TableColumn("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("t"));
        type.prefWidthProperty().bind(table.widthProperty().divide(5));
        final TableColumn delta = new TableColumn("Delta");
        delta.setCellValueFactory(new PropertyValueFactory<>("d"));
        delta.prefWidthProperty().bind(table.widthProperty().divide(5));
        final TableColumn value = new TableColumn("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("v"));
        value.prefWidthProperty().bind(table.widthProperty().divide(5));
        table.getColumns().addAll(rate, amount, value, type, delta);
    }
}
