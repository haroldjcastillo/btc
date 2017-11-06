package com.github.haroldjcastillo.btc.ui;

import com.github.haroldjcastillo.btc.dao.OrderPayload;
import com.github.haroldjcastillo.btc.http.TickType;
import com.github.haroldjcastillo.business.http.ScheduleHttp;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    public static final AtomicInteger TICKS = new AtomicInteger(0);
    public static final AtomicReference<Double> CURRENT_PRICE = new AtomicReference<Double>(0.0);
    public static final AtomicReference<TickType> TICK_TYPE = new AtomicReference<TickType>(TickType.NEUTRAL);
    
    protected ScheduleHttp schedule;

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
