# Bitso cryptocurrency exchange client

This project has as objective shows how to connect to the websocket and web service of Bitso cryptocurrency exchange using rxjava as a implementation of observer pattern and manipulates the data in real time.

The first window that you can see it's of the loading, works as a splash screen, while the app start.

![1](https://raw.githubusercontent.com/haroldjcastillo/btc/master/docs/loading.png?raw=true)

After load, the app shows the next window

![1](https://raw.githubusercontent.com/haroldjcastillo/btc/master/docs/parts.png?raw=true)

The UI is composed for 5 important parts.

 1. Header, where we can to stop all the connections and close the application

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/header.png?raw=true)

 2. Ticks, allows to see the actual upticks and downticks

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/ticks.png?raw=true)

 3. The best orders values (Bids and Asks)

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/orders.png?raw=true)

 4. The buy and sell of BTC into the application (simulation)

![4](https://github.com/haroldjcastillo/btc/blob/master/docs/buydsell.png?raw=true)

 5. Recent trades

![5](https://github.com/haroldjcastillo/btc/blob/master/docs/recent.png?raw=true)

## Configurations 

The <b>X</b> best bids, best asks and recent trades

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/MaxX.png?raw=true)

Consecutive <b>M</b> upticks and <b>N</b> downticks

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/MaxMN.png?raw=true)

build it! the project with maven :+1:

```shell
git clone https://github.com/haroldjcastillo/btc.git
cd btc
mvn clean install && java -jar btc-ui/target/btc-ui-0.0.1.jar
```
### HTTP Configuration

It's posible to configure the HTTP connection, request and socket timeout in the file */btc/config/pooling.json*

```json
{
	"connectTimeout": 15000,
	"connectionRequestTimeout": 30000,
	"defaultMaxPerRoute": 20,
	"httpHost": [
		{
			"hostName": "api.bitso.com",
			"port": 808
		}
	],
	"maxTotal": 100,
	"socketTimeout": 15000
}
```

> **Additional notes:**
> - The http request for query the trades it's called each 1 second.
> - In each Sell or Buy the ticks counter it's restarted.
> - By default the X value it's 10 and M and N it's 2 

## Checklist

|Feature| File name | Method name |
 ----------------- | ---------------------------- | ------------------
|Schedule the polling of trades over REST.|[ScheduleHttp](https://github.com/haroldjcastillo/btc/blob/master/btc/btc-business/src/main/java/com/github/haroldjcastillo/business/http/ScheduleHttp.java)|[start()](https://github.com/haroldjcastillo/btc/blob/8c512119591d0b1b56d55c6571c5e7a98ed6e5b2/btc/btc-business/src/main/java/com/github/haroldjcastillo/business/http/ScheduleHttp.java#L38)|
|Request a book snapshot over REST.|[OrderController](https://github.com/haroldjcastillo/btc/blob/master/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ui/OrderController.java)|[loadCurrentOrders()](https://github.com/haroldjcastillo/btc/blob/1c0a5c7943595bbd7c89fbb1857ecdd9e71dedd5/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ui/OrderController.java#L87)|
|Listen for diff-orders over websocket.|[OrderObserver](https://github.com/haroldjcastillo/btc/blob/master/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ws/OrderObserver.java)|[onNext(java.lang.String)](https://github.com/haroldjcastillo/btc/blob/1c0a5c7943595bbd7c89fbb1857ecdd9e71dedd5/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ws/OrderObserver.java#L23)|
|Replay diff-orders.|[OrderManager](https://github.com/haroldjcastillo/btc/blob/master/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ws/OrderManager.java)|[diffOrder(java.lang.String)](https://github.com/haroldjcastillo/btc/blob/1c0a5c7943595bbd7c89fbb1857ecdd9e71dedd5/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ws/OrderManager.java#L44)|
|Use config option X to request  recent trades.|[TradeManager](https://github.com/haroldjcastillo/btc/blob/master/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/http/TradeManager.java)|[addRecentTrade(com.github.haroldjcastillo.btc.dao.TradePayloadResponse)](https://github.com/haroldjcastillo/btc/blob/1c0a5c7943595bbd7c89fbb1857ecdd9e71dedd5/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/http/TradeManager.java#L47)|
|Use config option X to limit number of ASKs displayed in UI.|[OrderManager](https://github.com/haroldjcastillo/btc/blob/master/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ws/OrderManager.java)|[toSortBook(javafx.collections.ObservableList, List)](https://github.com/haroldjcastillo/btc/blob/1c0a5c7943595bbd7c89fbb1857ecdd9e71dedd5/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ws/OrderManager.java#L91)|
|The loop that causes the trading algorithm to reevaluate.|[OrderManager](https://github.com/haroldjcastillo/btc/blob/master/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ws/OrderManager.java)|[diffOrder(java.lang.String)](https://github.com/haroldjcastillo/btc/blob/1c0a5c7943595bbd7c89fbb1857ecdd9e71dedd5/btc/btc-ui/src/main/java/com/github/haroldjcastillo/btc/ws/OrderManager.java#L44)|
