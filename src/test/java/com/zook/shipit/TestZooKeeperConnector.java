package com.zook.shipit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

public class TestZooKeeperConnector {

	@Test
	public void shouldConnectToZookeeper() throws IOException, InterruptedException, KeeperException {
		List<String> znodes = new ArrayList<String>();
		ZooKeeperConnector zooKeeperConnector = new ZooKeeperConnector();
		ZooKeeper zooKeeper = zooKeeperConnector.connect("localhost");

		znodes = zooKeeper.getChildren("/", true);

		for (String znode : znodes) {
			System.out.println(znode);
		}
	}
}
