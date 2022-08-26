package com.scraper;

import java.util.Scanner;

public final class ThreadScraper {
    private static final Scanner in = new Scanner(System.in);

    public static void main(final String[] args) {
        new Page(ask("url?"))
                .downloadImgs(ask("dir?"), "a", "href", "class=fileThumb");
    }

    private static String ask(final String msg) {
        System.out.println(msg);
        return in.nextLine();
    }
}