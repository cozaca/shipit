package com.zook.shipit;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZookeeperConnector {

	private ZooKeeper zooKeeper;
	private CountDownLatch connSignal = new CountDownLatch(1);

	public ZooKeeper connect(String host) throws IOException, InterruptedException {
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
