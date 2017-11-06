package com.github.haroldjcastillo.btc.dao;

import java.util.List;

/**
 *
 * @author harold.castillo
 */
public class Trade implements Bitso {

    private String type;
    private String book;
    private List<TradePayload> payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public List<TradePayload> getPayload() {
        return payload;
    }

    public void setPayload(List<TradePayload> payload) {
        this.payload = payload;
    }
    
}
