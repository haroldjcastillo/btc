package com.github.haroldjcastillo.btc.ui;

import java.util.Arrays;

import com.github.haroldjcastillo.btc.dao.OrderPayload;
import com.github.haroldjcastillo.btc.http.TradeObserver;
import com.github.haroldjcastillo.btc.ws.OrderObserver;
import com.github.haroldjcastillo.business.http.ScheduleHttp;
import com.github.haroldjcastillo.rxws.WebSocket;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLController extends AbstractController {
	
	@FXML
	private TickController tickController;

    @FXML
    private JFXTextField top;

    @FXML
    private ImageView closeButton;

    @FXML
    private TableView<OrderPayload> bidsTableView;

    @FXML
    private TableView<OrderPayload> asksTableView;
    
    @FXML
	private Label tickCount;
	
	@FXML
	private Label descriptionTick;
	
	@FXML
	private ImageView  upImage;
	
	@FXML
	private ImageView downImage;

    @FXML
    private void closeeButtonAction(MouseEvent event) {
        try {
            final Stage stage = (Stage) closeButton.getScene().getWindow();            
            schedule.stop();
            WebSocket.getInstance().disconnect();
            stage.close();
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
    
    @FXML
    private void initialize() {
        top.setText(BEST.toString());
        addOrderPayloadColumn(bidsTableView);
        addOrderPayloadColumn(asksTableView);
        asksTableView.setItems(ASKS);
        bidsTableView.setItems(BIDS);
        loadListeners();
        connectWebsocket();
        startTrades(1);
		tickCount.setText(String.valueOf(TICKS.get()));
		descriptionTick.setText(String.valueOf(TICK_TYPE.get().getValue()));
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
                .connect(Arrays.asList(new OrderObserver())).addChannels(channels);
    }

	public void addOrderPayloadColumn(final TableView<OrderPayload> table) {
        final TableColumn<OrderPayload, String> rate = new TableColumn<>("Rate");
        rate.setCellValueFactory(new PropertyValueFactory<>("r"));
        rate.prefWidthProperty().bind(table.widthProperty().divide(5));
        table.getColumns().add(rate);
        
        final TableColumn<OrderPayload, String> amount = new TableColumn<>("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<>("a"));
        amount.prefWidthProperty().bind(table.widthProperty().divide(5));
        table.getColumns().add(amount);
        
        final TableColumn<OrderPayload, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("t"));
        type.prefWidthProperty().bind(table.widthProperty().divide(5));
        table.getColumns().add(type);
        
        final TableColumn<OrderPayload, String> delta = new TableColumn<>("Delta");
        delta.setCellValueFactory(new PropertyValueFactory<>("d"));
        delta.prefWidthProperty().bind(table.widthProperty().divide(5));
        table.getColumns().add(delta);
        
        final TableColumn<OrderPayload, String> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("v"));
        value.prefWidthProperty().bind(table.widthProperty().divide(5));
        table.getColumns().add(value);
    }
	
	public void startTrades(final long delay) {
		final String url = "https://api.bitso.com/v3/trades/?book=btc_mxn&marker&sort=desc&limit=1";
		final TradeObserver observer = new TradeObserver();
		schedule = new ScheduleHttp(url, delay, Arrays.asList(observer));
		schedule.start();
	}

	public void stopTrades() {
		schedule.stop();
	}

}
