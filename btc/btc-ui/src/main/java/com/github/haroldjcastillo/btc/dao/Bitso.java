/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.haroldjcastillo.btc.dao;

/**
 *
 * @author harold.castillo
 */
public interface Bitso {

    public enum Order {
        BIDS("bids"), ASKS("asks");
        
        private final String value;

        private Order(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Channel {
        TRADES("trades"), DIFF_ORDERS("diff-orders"), ORDERS("orders");

        private final String value;

        private Channel(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
