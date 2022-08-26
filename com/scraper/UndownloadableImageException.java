package com.scraper;

import java.nio.file.Path;

public final class UndownloadableImageException extends MyRuntimeException {
    public UndownloadableImageException(final Path path) {
        super("could not save the following:\n%s".formatted(path));
    }
}