package com.scraper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.scraper.Misc.indexOfUnquoted;

public final class Page {
    private final String text;
    private final String protocol;

    public Page(final String url) {
        protocol = url.substring(0, url.indexOf("//"));
        text = getHtml(url);
    }

    private static String getHtml(final String url) {
        try (final InputStream stream = new URL(url).openStream()) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw new UnreadableHTMLException(url);
        }
    }

    private final String openTemplate = "<%s";
    private final String close = ">";

    public void downloadImgs(final String dir, final String tag, final String imgAttr, final String... desiredAttrs) {
        new File(dir).mkdirs();
        final String open = openTemplate.formatted(tag);

        int start;
        int search = 0;
        while ((start = indexOfUnquoted(text, open, search)) != -1) {
            final int stop = indexOfUnquoted(text, close, start + open.length());
            search = stop + close.length();
            final Tag t = new Tag(text.substring(start, search));
            if (t.has(desiredAttrs)) downloadImg(dir, t.get(imgAttr));
        }
    }

    private void downloadImg(final String dir, final String url) {
        final Path path = Path.of("%s/%s".formatted(dir, getFilename(url)));
        try (final InputStream in = new URL(inferProtocol(url)).openStream()) {
            Files.copy(in, path);
        } catch (final IOException e) {
            throw new UndownloadableImageException(path);
        }
    }

    private String inferProtocol(final String url) {
        if (url.startsWith("//")) return protocol + url;
        return url;
    }

    private static String getFilename(final String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}