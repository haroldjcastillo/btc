package com.github.haroldjcastillo.btc.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;
import com.github.haroldjcastillo.btc.dao.Bitso;
import com.github.haroldjcastillo.btc.dao.Order;
import com.github.haroldjcastillo.btc.dao.OrderPayload;
import com.github.haroldjcastillo.btc.ui.AbstractController;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Comparator.comparing;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

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
                        final List<OrderPayload> copyBids = new ArrayList<>(AbstractController.BIDS);
                        Collections.copy(copyBids, AbstractController.BIDS);
                        final List<OrderPayload> copyAsks = new ArrayList<>(AbstractController.ASKS);
                        Collections.copy(copyAsks, AbstractController.ASKS);
                        final List<OrderPayload> orderedBids = Stream.concat(bids.stream(), copyBids.stream())
                                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(OrderPayload::getV))), ArrayList::new))
                                .stream()
                                .sorted((o1, o2) -> o2.compareTo(o1))
                                .limit(AbstractController.BEST.intValue())
                                .collect(Collectors.toList());
                        AbstractController.updateOrderPayload(AbstractController.BIDS, orderedBids);
                        final List<OrderPayload> orderedAsks = Stream.concat(asks.stream(), copyAsks.stream())
                                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(OrderPayload::getV))), ArrayList::new))
                                .stream()
                                .sorted((o1, o2) -> o2.compareTo(o1))
                                .limit(AbstractController.BEST.intValue())
                                .collect(Collectors.toList());
                        AbstractController.updateOrderPayload(AbstractController.ASKS, orderedAsks);
                    }
                } catch (Exception e) {
                    System.out.println(frame);
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
}
