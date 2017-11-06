package com.github.haroldjcastillo.btc.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haroldjcastillo.btc.dao.Bitso;
import com.github.haroldjcastillo.btc.dao.Order;
import com.github.haroldjcastillo.btc.dao.OrderPayload;
import com.github.haroldjcastillo.btc.ui.AbstractController;
import com.github.haroldjcastillo.btc.ws.TradeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/**
 *
 * @author harold.castillo
 */
public class TradeConverterTest {

    private static final String ORDERS = "{ \"type\": \"orders\", \"book\": \"btc_mxn\", \"payload\": { \"bids\": [ { \"r\": \"7185\", \"a\": \"0.001343\", \"v\": \"9.64\", \"t\": 1, \"d\": 1455315394039 }, { \"r\": \"7183.01\", \"a\": \"0.007715\", \"v\": \"55.41\", \"t\": 1, \"d\": 1455314938419 }, { \"r\": \"7183\", \"a\": \"1.59667303\", \"v\": \"11468.9\", \"t\": 1, \"d\": 1455314894615 } ], \"asks\": [ { \"r\": \"7251.1\", \"a\": \"0.29437179\", \"v\": \"2134.51\", \"t\": 0, \"d\": 1455315979682 }, { \"r\": \"7251.72\", \"a\": \"1.32057812\", \"v\": \"9576.46\", \"t\": 0, \"d\": 1455303931277 } ] } }";
    private static final String ORDERS_VOID = "{ \"type\": \"orders\" }";

    @RepeatedTest(5)
    public void toOrders() {
        TradeConverter.convert(ORDERS);
    }

    @Test
    @Disabled
    public void toOrdersVoid() {
        TradeConverter.convert(ORDERS_VOID);
    }

    @RepeatedTest(5)
    @Disabled
    public void jacksonTest() {
        final Order order = getOrder(ORDERS);
        assertNotNull(order.getPayload());
    }

    @Test
    @Disabled
    public void sort() {
        final List<OrderPayload> currentOrder = new ArrayList<>();
        final List<OrderPayload> newOrder = getOrder(ORDERS).getPayload().get(Bitso.Order.BIDS.getValue());
        currentOrder.addAll(newOrder);
        Collections.sort(currentOrder, (OrderPayload o1, OrderPayload o2) -> o2.compareTo(o1));
    }

    @Test
    @Disabled
    public void streamSorted() {
        Order order = getOrder(ORDERS);
        ObservableList<OrderPayload> bidsList = AbstractController.BIDS;
        List<OrderPayload> result = 
                Stream.concat(order.getPayload().get(Bitso.Order.BIDS.getValue()).stream(), bidsList.stream())
                    .sorted((o1, o2) -> o2.compareTo(o1))
                    .limit(1)
                    .collect(Collectors.toList());
        print(result);
    }

    private void print(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String json = objectMapper.writeValueAsString(obj);
            System.out.println(json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TradeConverterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Order getOrder(final String json) {
        Order order = null;
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            order = objectMapper.readValue(json, Order.class);
            assertNotNull(order);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return order;
    }
}
