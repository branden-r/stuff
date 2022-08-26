package com.scraper;

public final class UnreadableHTMLException extends MyRuntimeException {
    public UnreadableHTMLException(final String url) {
        super("cannot read html from the following url:\n%s".formatted(url));
    }
}