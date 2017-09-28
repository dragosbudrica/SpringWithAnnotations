package com.kepler.rominfo.support;

public class ApplicationConstants {
    private ApplicationConstants() {}

    private static class InstanceHolder {
        static final ApplicationConstants INSTANCE = new ApplicationConstants();
    }

    public static final ApplicationConstants getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
