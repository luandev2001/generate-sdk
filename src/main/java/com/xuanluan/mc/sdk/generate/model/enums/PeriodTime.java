package com.xuanluan.mc.sdk.generate.model.enums;

public enum PeriodTime {
    SECOND, MINUTE, HOUR, DAY;

    public static long convert(long time, PeriodTime period) {
        switch (period) {
            case MINUTE:
                time *= 60;
                break;
            case HOUR:
                time *= 3600;
                break;
            case DAY:
                time *= 3600 * 24;
                break;
        }
        return time;
    }
}
