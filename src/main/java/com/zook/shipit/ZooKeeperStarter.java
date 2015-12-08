package com.zook.shipit;

import com.zook.shipit.execmonitor.Executor;

public class ZooKeeperStarter
{

    public static final String DEFAULT_HOST    = "localHost";
    public static final int    DEFAULT_TIMEOUT = 5000;

    public static void main(String args[])
    {
        String[] znodes = {"/ul-confirm.ini", "/zookeeper.ini", "/middle.ini", "/stuff.ini"};
        for (int i = 0; i < znodes.length; i++)
        {
            String[] exec = new String[]{"c:/projects/shipit/distributions/count.cmd"};
            initExecutors(znodes, i, exec);
        }
    }

    private static void initExecutors(String[] znodes, int i, String[] exec)
    {
        new Thread(() ->  startExecutorForZnode(znodes[i], znodes[i].replaceFirst("/", ""), exec)).start();
    }

    public static void startExecutorForZnode(String znode, String fileName, String[] exec)
    {
        try
        {
            new Executor(DEFAULT_HOST, znode, fileName, exec).run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
