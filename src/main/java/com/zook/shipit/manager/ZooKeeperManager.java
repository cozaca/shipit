package com.zook.shipit.manager;

import java.io.File;
import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import com.zook.shipit.io.FileManager;

public class ZooKeeperManager
{

    private final ZooKeeper zooKeeper;

    public ZooKeeperManager(ZooKeeper zooKeeper)
    {
        this.zooKeeper = zooKeeper;
    }
    
    public void manage()
    {
        
    }

    public void createZNode(String path, byte[] data) throws KeeperException, InterruptedException
    {
        zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void createZNode(String path, String data) throws KeeperException, InterruptedException
    {

        zooKeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public String readZNode(String path) throws KeeperException, InterruptedException
    {
        return new String(zooKeeper.getData(path, true, zooKeeper.exists(path, true)));
    }

    public void createZnodeWithFileDate(String znode, String pathToFile) throws IOException, KeeperException, InterruptedException
    {
        FileManager fileManager = new FileManager();
        String data = fileManager.readFile(pathToFile);

        createZNode(znode, data);
    }

    public void overrideZnodeWithFileDate(String znode, String pathToFile) throws IOException, KeeperException, InterruptedException
    {
        FileManager fileManager = new FileManager();
        String data = fileManager.readFile(pathToFile);

        Stat stat = new Stat();
        zooKeeper.getData(znode, null, stat);

        zooKeeper.setData(znode, data.getBytes(), stat.getVersion());
    }
    
    public void overrideZnodeWithFileDate(String znode, File file) throws IOException, KeeperException, InterruptedException
    {
        FileManager fileManager = new FileManager();
        String data = fileManager.readFile(file.getPath());

        Stat stat = new Stat();
        zooKeeper.getData(znode, null, stat);

        zooKeeper.setData(znode, data.getBytes(), stat.getVersion());
    }
    
}
