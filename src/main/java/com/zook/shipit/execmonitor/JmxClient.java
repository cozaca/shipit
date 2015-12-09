package com.zook.shipit.execmonitor;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.sun.tools.attach.spi.AttachProvider;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxClient
{

    public void callJmxMethod()
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
            virtualMachine.loadAgent("C:\\Program Files (x86)\\Java\\jre1.8.0_45\\lib\\management-agent.jar", "com.sun.management.jmxremote");
            final Object portObject = virtualMachine.getAgentProperties().get("com.sun.management.jmxremote.localConnectorAddress");

            final JMXServiceURL target = new JMXServiceURL(portObject + "");

            final JMXConnector connector = JMXConnectorFactory.connect(target);
            final MBeanServerConnection remote = connector.getMBeanServerConnection();

            ObjectName objectName = ObjectName.getInstance("com.ullink.oms.ulconfirm.extension.management:type=PreferenceShync,name=ul-confirm-PreferenceShync");

            remote.invoke(objectName, "shync", null, null);
        }
        catch (Exception e)
        {

        }
    }

    private static boolean pickThisOne(VirtualMachineDescriptor virtualMachineDescriptor)
    {
        return "com.ullink.oms.OrderManagementSystem".equals(virtualMachineDescriptor.displayName());
    }

}
