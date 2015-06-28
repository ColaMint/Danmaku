package com.danmaku.zbus;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zstacks.zbus.client.Broker;
import org.zstacks.zbus.client.MqConfig;
import org.zstacks.zbus.client.broker.SingleBroker;
import org.zstacks.zbus.client.broker.SingleBrokerConfig;
import org.zstacks.zbus.protocol.MessageMode;

public class DanmakuZbus {
	private final static Logger logger = LoggerFactory.getLogger(DanmakuZbus.class);

	public static String HOST = "127.0.0.1";
	public static String PORT = "15555";
	public static String MQ = "danmaku";
	public static String TOKEN = "";
	public static String TOPIC = "danmaku";

	public static void setZbusConfig(String host, String port, String mq, String token, String topic) {
		DanmakuZbus.HOST = host;
		DanmakuZbus.PORT = port;
		DanmakuZbus.MQ = mq;
		DanmakuZbus.TOKEN = token;
		DanmakuZbus.TOPIC = topic;

		logger.info("HOST : {}", DanmakuZbus.HOST);
		logger.info("PORT : {}", DanmakuZbus.PORT);
		logger.info("MQ : {}", DanmakuZbus.MQ);
		logger.info("TOKEN : {}", DanmakuZbus.TOKEN);
		logger.info("TOPIC : {}", DanmakuZbus.TOPIC);
	}

	public static DanmakuFetcher createFetcher() throws IOException {
		SingleBrokerConfig brokerConfig = new SingleBrokerConfig();
		brokerConfig.setBrokerAddress(DanmakuZbus.HOST + ":" + DanmakuZbus.PORT);
		Broker broker = new SingleBroker(brokerConfig);

		MqConfig config = new MqConfig();
		config.setBroker(broker);
		config.setMq(DanmakuZbus.MQ);
		config.setAccessToken(DanmakuZbus.TOKEN);
		config.setTopic(DanmakuZbus.TOPIC);
		config.setMode(MessageMode.PubSub.intValue());

		DanmakuFetcher fetcher = new DanmakuFetcher(config);
		return fetcher;
	}
}
