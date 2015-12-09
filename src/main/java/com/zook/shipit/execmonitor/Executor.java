package com.zook.shipit.execmonitor;

import java.io.IOException;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import com.zook.shipit.manager.ZooKeeperManager;

public class Executor
    implements Watcher, Runnable, DataMonitor.DataMonitorListener
{
    String znode;
    
    byte[] data;

    DataMonitor dm;

    ZooKeeper zk;
    
    ZooKeeperManager zooKeeperManager;

    String filename;

    public Executor(String hostPort, String znode, String filename, byte[] data) throws KeeperException, IOException, InterruptedException {
        this.filename = filename;
        this.data = data;
        zk = new ZooKeeper(hostPort, 5000, this);
        dm = new DataMonitor(zk, znode, null, this);
        zooKeeperManager = new ZooKeeperManager(zk);
        zooKeeperManager.overrideZnodeWithFileDate(znode, filename);
        System.out.println("Executor for node = " + znode);
    }

    public void process(WatchedEvent event) {
        dm.process(event);
    }

    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    System.out.println("wait ....");
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void exists(byte[] data) {
                
                System.out.println("================= call count.cmd");
                //TODO Here we should we call the JMX method
    }
}