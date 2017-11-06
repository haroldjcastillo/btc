package com.github.haroldjcastillo.btc.ui;

import com.github.haroldjcastillo.btc.dao.OrderPayload;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author harold.castillo
 */
public class AbstractController {

    public static final ObservableList<OrderPayload> BIDS = FXCollections.observableArrayList();
    public static ObservableList<OrderPayload> ASKS = FXCollections.observableArrayList();
    public static final AtomicInteger BEST = new AtomicInteger(10);

    public static void updateOrderPayload(final ObservableList<OrderPayload> orderPayload, final List<OrderPayload> orderPayloadSorted) {
        new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Platform.runLater(() -> {
                    orderPayload.setAll(orderPayloadSorted);
                });
                return Void.TYPE.newInstance();
            }
        }.run();
    }
}
