package com.zook.shipit;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZooKeeperConnector {

	private ZooKeeper zooKeeper;
	private CountDownLatch connSignal = new CountDownLatch(1);
	public static final String DEFAULT_HOST = "localhost";

	public ZooKeeper connect(String host) throws IOException, InterruptedException {
		return connectToZooKeeper(host);
	}

	public ZooKeeper connect() throws IOException, InterruptedException {
		return connectToZooKeeper(DEFAULT_HOST);
	}

	private ZooKeeper connectToZooKeeper(String host) throws IOException, InterruptedException {
		zooKeeper = new ZooKeeper(host, 5000, watcher -> {
			if (watcher.getState() == KeeperState.SyncConnected)
				connSignal.countDown();
		});
		connSignal.await();
		return zooKeeper;
	}

	public void close() throws InterruptedException {
		zooKeeper.close();
	}
}
