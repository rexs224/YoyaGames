package com.example.app;

import java.util.concurrent.TimeUnit;

public abstract class CountUpTimer {
    private long startTime;
    private boolean isRunning;

    public CountUpTimer() {
        startTime = 0;
        isRunning = false;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        isRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    onTick(elapsedTime);
                    try {
                        Thread.sleep(1000); // Espera 1 segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stop() {
        isRunning = false;
    }

    public abstract void onTick(long elapsedTime);

    public int getSeconds() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        return (int) (TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60);
    }
}

