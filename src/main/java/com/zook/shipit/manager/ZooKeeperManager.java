package com.zook.shipit.manager;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperManager {

	private final ZooKeeper zooKeeper;

	public ZooKeeperManager(ZooKeeper zooKeeper) {
		this.zooKeeper = zooKeeper;
	}

	public void createZNode(String path, byte[] data) throws KeeperException, InterruptedException {
		zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	public void createZNode(String path, String data) throws KeeperException, InterruptedException {

		zooKeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	public String readZNode(String path) throws KeeperException, InterruptedException {
		return new String(zooKeeper.getData(path, true, zooKeeper.exists(path, true)));
	}
}
