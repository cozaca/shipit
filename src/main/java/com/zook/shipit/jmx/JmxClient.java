package com.zook.shipit.jmx;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.sun.tools.attach.spi.AttachProvider;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class JmxClient
{
    private static final String MANAGEMENT_AGENT_LIB_PATH = Paths.get(System.getenv("JAVA_HOME"), "jre", "lib").toString();
    private static final String ORDER_MANAGEMENT_SYSTEM   = "com.ullink.oms.OrderManagementSystem";

    private final String type;
    private final String objectName;
    private final String defaultMethod;

    public JmxClient(String type, String objectName, String defaultMethod)
    {
        this.type = type;
        this.objectName = objectName;
        this.defaultMethod = defaultMethod;
    }

    public String getType()
    {
        return type;
    }

    public void callMethod()
    {
        callMethod(defaultMethod);
    }

    public void callMethod(String method)
    {
        try
        {
            final AttachProvider attachProvider = AttachProvider.providers().get(0);

            VirtualMachineDescriptor descriptor = null;
            for (VirtualMachineDescriptor virtualMachineDescriptor : attachProvider.listVirtualMachines())
            {
                if (pickThisOne(virtualMachineDescriptor))
                {
                    descriptor = virtualMachineDescriptor;
                    break;
                }
            }

            final VirtualMachine virtualMachine = attachProvider.attachVirtualMachine(descriptor);

            virtualMachine.loadAgent(MANAGEMENT_AGENT_LIB_PATH + File.separator + "management-agent.jar", "com.sun.management.jmxremote");
            final Object portObject = virtualMachine.getAgentProperties().get("com.sun.management.jmxremote.localConnectorAddress");

            final JMXServiceURL target = new JMXServiceURL(portObject + "");

            final JMXConnector connector = JMXConnectorFactory.connect(target);
            final MBeanServerConnection remote = connector.getMBeanServerConnection();

            ObjectName object = ObjectName.getInstance(objectName);

            remote.invoke(object, method, null, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static boolean pickThisOne(VirtualMachineDescriptor virtualMachineDescriptor)
    {
        return ORDER_MANAGEMENT_SYSTEM.equals(virtualMachineDescriptor.displayName());
    }

}
