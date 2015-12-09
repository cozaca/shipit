package com.zook.shipit.jmx;

import java.util.HashMap;
import java.util.Map;

public final class JmxClientFactory
{
    private static final Map<String, JmxClient> jmxClientMap;

    static
    {
        jmxClientMap = new HashMap<String, JmxClient>();
        jmxClientMap.put(ConfirmJmx.TYPE, new ConfirmJmx());
    }

    private JmxClientFactory()
    {
    }

    public static JmxClient get(String jmxClientType)
    {
        JmxClient jmxClient = jmxClientMap.get(jmxClientType);

        if (jmxClient == null)
        {
            throw new UnsupportedOperationException("Cannot create JmxClient of type " + jmxClientType);
        }
        return new ConfirmJmx();

    }
}
