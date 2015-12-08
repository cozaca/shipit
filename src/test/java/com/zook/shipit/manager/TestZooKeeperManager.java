package com.zook.shipit.manager;

import java.io.IOException;
import java.util.List;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;
import com.zook.shipit.ZooKeeperConnector;

public class TestZooKeeperManager {

	ZooKeeperConnector zooKeeperConnector = new ZooKeeperConnector();
	ZooKeeperManager zooManager;
	ZooKeeper zooKeeper;

	@Before
	public void setUp() throws IOException, InterruptedException {
		zooKeeper = zooKeeperConnector.connect();
		zooManager = new ZooKeeperManager(zooKeeper);
	}

	@Test
	public void shouldCreateZNode() throws KeeperException, InterruptedException {
		String path = "/middle.ini";
		byte[] data = "workflow = standard offline".getBytes();

		zooManager.createZNode(path, data);

		List<String> znodes = zooKeeper.getChildren("/", true);

		for (String znode : znodes) {

			System.out.println(znode);
		}
	}

	@Test
	public void shouldReadZNode() throws KeeperException, InterruptedException {
		String path = "/ul-middle.ini";
		String result = zooManager.readZNode(path);
		
		System.out.println(result);
	}
	
    @Test
    public void shouldCreateZNodeWithFileData() throws KeeperException, InterruptedException, IOException
    {
        String path = "/ul-middle.ini";

        zooManager.createZnodeWithFileDate(path, "g:/Platforms/ionut_last_platform/MiddleHEADOds372/servers/ulodisys/conf/extensions/ul-confirm/ul-middle.ini");
        List<String> znodes = zooKeeper.getChildren("/", true);

        for (String znode : znodes)
        {

            System.out.println(znode);
        }
    }
    
    @Test
    public void shouldOverrideZNodeWithFileData() throws KeeperException, InterruptedException, IOException
    {
        String path = "/ul-confirm.ini";

        zooManager.overrideZnodeWithFileDate(path, "g:/Platforms/ionut_last_platform/MiddleHEADOds372/servers/ulodisys/conf/extensions/ul-confirm/ul-confirm.ini");
        List<String> znodes = zooKeeper.getChildren("/", true);

        for (String znode : znodes)
        {

            System.out.println(znode);
        }
    }
}
