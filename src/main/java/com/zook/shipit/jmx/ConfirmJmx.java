package com.zook.shipit.jmx;

public class ConfirmJmx extends JmxClient
{

    public static final  String TYPE        = "confirm-jmx";

    private static final String OBJECT_NAME = "com.ullink.oms.ulconfirm.extension.management:type=PreferenceSync,name=ul-confirm-PreferenceSync";
    private static final String DEFAULT_METHOD = "sync";

    public ConfirmJmx()
    {
        super(TYPE, OBJECT_NAME, DEFAULT_METHOD);
    }
}
