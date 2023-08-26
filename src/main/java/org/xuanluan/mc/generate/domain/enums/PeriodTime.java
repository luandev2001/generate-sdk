package org.xuanluan.mc.generate.domain.enums;

public enum PeriodTime {
    second, minute, hour, day;

    public static long convert(long time, PeriodTime period) {
        switch (period) {
            case minute:
                time *= 60;
            case hour:
                time *= 3600;
            case day:
                time *= 3600 * 24;
        }
        return time;
    }
}
