package com.danmaku.server;

import java.io.IOException;

import org.zstacks.zbus.server.ZbusServer;
import org.zstacks.znet.nio.Dispatcher;
import com.danmaku.conf.ConfManager;
import com.danmaku.conf.DanmakuConfManager;

public class DanmakuServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		ConfManager conf = DanmakuConfManager.getInstance();

		int port = Integer.parseInt(conf.getProperty("server.port", "15555"));
		String token = conf.getProperty("server.token", "");
		Dispatcher dispatcher = new Dispatcher()
				.selectorCount(1)
				.executorCount(16);

		ZbusServer zbus = new ZbusServer(port, dispatcher);
		zbus.setAdminToken(token);
		zbus.setMessageStoreType("dummy");
		zbus.setVerbose(true);

		zbus.start();
	}
}
