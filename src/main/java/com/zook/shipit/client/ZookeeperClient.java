package com.zook.shipit.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookeeperClient {

	public void init() throws Exception {

		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework curatorZookeeperClient = CuratorFrameworkFactory
				.newClient("localhost:2181", retryPolicy);
		curatorZookeeperClient.start();
		boolean status = curatorZookeeperClient.getZookeeperClient().blockUntilConnectedOrTimedOut();
		System.out.println("status" + status);
		String znodePath = "/test_node";
		
		try {

		String originalData = new String(curatorZookeeperClient.getData().forPath(znodePath));
		System.out.println("original data = " + originalData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception while fetching properties from zookeeper ZNode, reason " + e.getCause());
		}

		/* Zookeeper NodeCache service to get properties from ZNode */
		final NodeCache nodeCache = new NodeCache(curatorZookeeperClient, znodePath);
		nodeCache.getListenable().addListener(new NodeCacheListener() {

			public void nodeChanged() throws Exception {
				// TODO Auto-generated method stub
				ChildData dataFromZNode = nodeCache.getCurrentData();
				String newData = new String(nodeCache.getCurrentData().getData());
				System.out.println("new child node " + newData);
				
			}
		});
		nodeCache.start();
	}

	public static void main(String args[]) throws Exception{
		new ZookeeperClient().init();
	}
}
