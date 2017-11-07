package com.github.haroldjcastillo.btc.ws;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;
import com.github.haroldjcastillo.btc.dao.Bitso;
import com.github.haroldjcastillo.btc.dao.Order;
import com.github.haroldjcastillo.btc.dao.OrderPayload;
import com.github.haroldjcastillo.btc.ui.AbstractController;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author harold.castillo
 */
public class OrderConverter {

    private static final Logger LOGGER = LogManager.getLogger(OrderConverter.class);

    public static void convert(final String frame) {
        final JSONObject json = new JSONObject(frame);
        final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper;
        
        if (json.has("type") && json.has("payload")) {
            final String type = json.getString("type");
            if (type.equals(Bitso.Channel.ORDERS.getValue())) {
                try {
                    final Order order = objectMapper.readValue(frame, Order.class);
                    if (order.getPayload() != null) {
                        final List<OrderPayload> bids = order.getPayload().getOrDefault(Bitso.Order.BIDS.getValue(), Collections.emptyList());
                        final List<OrderPayload> asks = order.getPayload().getOrDefault(Bitso.Order.ASKS.getValue(), Collections.emptyList());
                        final List<OrderPayload> copyBids = new ArrayList<>();
                        copyBids.addAll(AbstractController.BIDS);
                        final List<OrderPayload> copyAsks = new ArrayList<>();
                        copyAsks.addAll(AbstractController.ASKS);
                        final List<OrderPayload> orderedBids = Stream.concat(bids.stream(), copyBids.stream())
                                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(OrderPayload::getV))), ArrayList::new))
                                .stream()
                                .sorted((o1, o2) -> o2.compareTo(o1))
                                .limit(AbstractController.BEST.intValue())
                                .collect(Collectors.toList());
                        updateOrderPayload(AbstractController.BIDS, orderedBids);
                        final List<OrderPayload> orderedAsks = Stream.concat(asks.stream(), copyAsks.stream())
                                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(OrderPayload::getV))), ArrayList::new))
                                .stream()
                                .sorted((o1, o2) -> o2.compareTo(o1))
                                .limit(AbstractController.BEST.intValue())
                                .collect(Collectors.toList());
                        updateOrderPayload(AbstractController.ASKS, orderedAsks);
                    }
                } catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
            }
        }
    }
    
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
