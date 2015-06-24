package com.danmaku.server;

import java.io.IOException;

import org.zstacks.zbus.server.ZbusServer;
import org.zstacks.znet.nio.Dispatcher;

import com.danmaku.conf.ConfManager;
import com.danmaku.conf.DanmakuConfManager;

public class DanmakuServer {

	public static void main(String[] args) throws IOException {
		ConfManager conf = DanmakuConfManager.getInstance();

		int serverPort = Integer.parseInt(conf.getProperty("server.port", "15555"));
		String adminToken = conf.getProperty("server.admin", "");

		Dispatcher dispatcher = new Dispatcher()
				.selectorCount(1)
				.executorCount(16);

		ZbusServer zbus = new ZbusServer(serverPort, dispatcher);
		zbus.setAdminToken(adminToken);
		zbus.setMessageStoreType("dummy");
		zbus.setVerbose(true);

		zbus.start();
	}
}
