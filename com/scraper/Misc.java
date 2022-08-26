package com.scraper;

public final class Misc {
    public static int indexOfUnquoted(final String body, final String query, final int start) {
        boolean quoted = false;
        int m = 0;

        final int l = body.length();
        for (int i = start; i < l; i++) {
            final char c = body.charAt(i);
            if (c != query.charAt(m)) {
                m = 0;
                if (c == '"') quoted = !quoted;
            } else if (!quoted) {
                m++;
                if (m == query.length()) return i + 1 - query.length();
            }
        }
        return -1;
    }
}