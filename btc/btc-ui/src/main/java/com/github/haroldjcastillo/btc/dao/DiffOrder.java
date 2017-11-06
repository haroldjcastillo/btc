package com.github.haroldjcastillo.btc.dao;

import java.util.List;

/**
 *
 * @author harold.castillo
 */
public class DiffOrder implements Bitso {

    private String type;
    private String book;
    private String sequence;
    private List<DiffOrderPayload> payload;

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

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public List<DiffOrderPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<DiffOrderPayload> payload) {
        this.payload = payload;
    }
    
}