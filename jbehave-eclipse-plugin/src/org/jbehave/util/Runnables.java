package org.jbehave.util;

public class Runnables {

    public static Runnable noop() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}
