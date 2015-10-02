package com.jgraham.httpserver;


public class ServerConfig {

    private static int port;
    private static String directory;

    public static void configureServer(String[] args) {
        port = parsePort(args);
        directory = parseDirectory(args);
    }

    public static int getPort() {
        return port;
    }

    public static String getDirectory() {
        return directory;
    }

    private static int parsePort(String[] args) {
        int port = 5000;
        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-p")) {
                try {
                    port = Integer.parseInt(args[++i]);
                }
                catch (NumberFormatException nfe) {
                    System.out.println("Invalid port entry");
                }
            }
        }
        return port;
    }

    private static String parseDirectory(String[] args) {
        String directory = "/public";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-d")) {
                directory = args[++i];
            }
        }
        return directory;
    }
}