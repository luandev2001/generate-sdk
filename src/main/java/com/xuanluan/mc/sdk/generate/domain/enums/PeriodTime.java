package com.xuanluan.mc.sdk.generate.domain.enums;

public enum PeriodTime {
    second, minute, hour, day;

    public static long convert(long time, PeriodTime period) {
        switch (period) {
            case minute:
                time *= 60;
                break;
            case hour:
                time *= 3600;
                break;
            case day:
                time *= 3600 * 24;
                break;
        }
        return time;
    }
}
