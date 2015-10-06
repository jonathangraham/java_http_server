package com.jgraham.httpserver;

public class ServerConfig {

    public int parsePort(String[] args) {
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

    public String parseDirectory(String[] args) {
        String directory = "/src/main/resources";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-d")) {
                directory = args[++i];
            }
        }
        return directory;
    }
}