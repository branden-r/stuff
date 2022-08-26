package com.scraper;

public abstract class MyRuntimeException extends RuntimeException {
    private final String info;

    protected MyRuntimeException(final String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "\n%s".formatted(info);
    }
}