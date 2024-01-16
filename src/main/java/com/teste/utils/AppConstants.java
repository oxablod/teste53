package com.teste.utils;

import java.time.ZoneId;

public class AppConstants {

    public static final ZoneId ZONE_ID_AMERICA_SAO_PAULO = ZoneId.of("America/Sao_Paulo");
    public static final boolean IS_DEBUG_OR_IDE = java.lang.management.ManagementFactory.getRuntimeMXBean()
            .getInputArguments()
            .toString()
            .contains("-agentlib:jdwp");

    private AppConstants(){}
}
