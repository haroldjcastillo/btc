# Bitso cryptocurrency exchange client

This project has as objective shows how to connect to the websocket and web service of Bitso cryptocurrency exchange using rxjava as a implementation of observer pattern and manipulates the data in real time.

## Checklist

|Feature| File name | Method name |
 ----------------- | ---------------------------- | ------------------
|Schedule the polling of trades over REST.|||
|Request a book snapshot over REST.|||
|Listen for diff-orders over websocket.|||
|Replay diff-orders.|||
|Use config option X to request  recent trades.|||
|Use config option X to limit number of ASKs displayed in UI.|||
|The loop that causes the trading algorithm to reevaluate.|||


![1](https://raw.githubusercontent.com/haroldjcastillo/btc/master/docs/parts.png)

The UI is composed for 4 important parts.

 1. Header, where we can to stop all the connections and close the application

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/header.png?raw=true)

 2. Ticks, allows to see the actual upticks and downticks

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/ticks.png?raw=true)

 3. The best orders values (Bids and Asks)

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/orders.png?raw=true)

 4. The buy and sell of BTC into the application (simulation)

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/buydsell.png?raw=true)

## Configurations 

The <b>X</b> best bids and <b>X</b> best asks 

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/MaxX.png?raw=true)

Consecutive <b>M</b> upticks and <b>N</b> downticks

![1](https://github.com/haroldjcastillo/btc/blob/master/docs/MaxMN.png?raw=true)

In the folder <b>dist</b> you can download the jar of the application, run it! :+1:

```shell
java -jar btc.jar
```

Or build the project with maven

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
> - The http request for query the trades it's called each 2 seconds.
> - In each Sell or Buy the ticks counter it's restarted.
