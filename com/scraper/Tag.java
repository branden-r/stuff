package com.scraper;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.Map;

public final class Tag {
    private final String type;
    private final Map<String, String> attrs;

    public Tag(final String text) {
        this.type = parseType(text);
        this.attrs = parseAttrs(text);
    }

    private static final Pattern typePtrn = Pattern.compile("<(.+?) ");

    private String parseType(final String text) {
        final Matcher m = typePtrn.matcher(text);
        m.find();
        return m.group(1);
    }

    private static final String keyPtrnStr = "([^ =]+)";
    private static final String valPtrnStr = "([^\"]+)";
    private static final Pattern htmlAttrPtrn = Pattern.compile(" %s=\"%s\"".formatted(keyPtrnStr, valPtrnStr));
    private static final Pattern givenAttrPtrn = Pattern.compile("%s=%s".formatted(keyPtrnStr, valPtrnStr));

    private Map<String, String> parseAttrs(final String text) {
        final Map<String, String> attrs = new HashMap<>();
        final Matcher m = htmlAttrPtrn.matcher(text);
        while (m.find()) attrs.put(m.group(1), m.group(2));
        return attrs;
    }

    public boolean has(final String[] attrs) {
        for (final String a : attrs) {
            final Matcher m = givenAttrPtrn.matcher(a);
            m.find();
            final String k = m.group(1);
            final String v = m.group(2);
            if (!v.equals(this.attrs.get(k))) return false;
        }
        return true;
    }

    public String get(final String k) {
        return attrs.get(k);
    }

    @Override
    public String toString() {
        final StringBuilder r = new StringBuilder("%s\n".formatted(type));
        attrs.forEach((k, v) -> r.append("\t%s = %s\n".formatted(k, v)));
        return r.toString();
    }
}