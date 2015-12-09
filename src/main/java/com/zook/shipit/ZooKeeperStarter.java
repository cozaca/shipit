package com.zook.shipit;

import com.zook.shipit.execmonitor.Executor;

public class ZooKeeperStarter
{

    public static final String DEFAULT_HOST    = "localHost";
    public static final int    DEFAULT_TIMEOUT = 5000;
    private final String znode;
    private final byte[] data;
    private final String fileName;

    public ZooKeeperStarter(String znode, String data, String fileName)
    {
        this.znode = znode;
        this.data = data.getBytes();
        this.fileName = fileName;
    }
    
    public ZooKeeperStarter(String znode, byte[] data, String fileName)
    {
        this.znode = znode;
        this.data = data;
        this.fileName = fileName;
    }
    
    public void start()
    {
        String[] exec = new String[]{"c:/projects/shipit/distributions/count.cmd"};
        new Thread(() -> startExecutorForZnode(exec)).start();
    }
    
    private void startExecutorForZnode(String[] exec)
    {
        try
        {
            new Executor(DEFAULT_HOST, znode, fileName, data, exec).run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
