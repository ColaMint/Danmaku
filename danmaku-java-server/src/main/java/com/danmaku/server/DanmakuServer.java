package com.danmaku.server;

import java.io.IOException;

import org.zstacks.zbus.client.Broker;
import org.zstacks.zbus.client.MqAdmin;
import org.zstacks.zbus.client.MqConfig;
import org.zstacks.zbus.client.broker.SingleBroker;
import org.zstacks.zbus.client.broker.SingleBrokerConfig;
import org.zstacks.zbus.protocol.MessageMode;
import org.zstacks.zbus.protocol.Proto;
import org.zstacks.zbus.server.ZbusServer;
import org.zstacks.znet.Message;
import org.zstacks.znet.nio.Dispatcher;
import com.danmaku.conf.ConfManager;
import com.danmaku.conf.DanmakuConfManager;
import com.danmaku.model.DanmakuModel;

public class DanmakuServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		ConfManager conf = DanmakuConfManager.getInstance();

		int port = Integer.parseInt(conf.getProperty("server.port", "15555"));
		String mq = conf.getProperty("server.mq", "danmaku");

		Dispatcher dispatcher = new Dispatcher()
				.selectorCount(1)
				.executorCount(16);

		ZbusServer zbus = new ZbusServer(port, dispatcher);
		zbus.setMessageStoreType("dummy");
		zbus.setVerbose(true);

		/* 启动服务器 */
		zbus.start();

		SingleBrokerConfig brokerConfig = new SingleBrokerConfig();
		brokerConfig.setBrokerAddress("127.0.0.1:" + port);
		brokerConfig.setDispatcher(dispatcher);
		Broker broker = new SingleBroker(brokerConfig);

		MqConfig config = new MqConfig();
		config.setBroker(broker);
		config.setMq(mq);
		config.setMode(MessageMode.PubSub.intValue());
		MqAdmin admin = new MqAdmin(config);

		/* 创建队列 */
		admin.createMQ();
		broker.close();

	}
}
