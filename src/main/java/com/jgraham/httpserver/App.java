package com.jgraham.httpserver;

import java.io.*;

class App {
    public static void main(String args[]) throws IOException {

        ServerConfig config = new ServerConfig();
        RouteHandler route = new RouteHandler();
        route.getResponse(config.parsePort(args), config.parseDirectory(args));
    }
}