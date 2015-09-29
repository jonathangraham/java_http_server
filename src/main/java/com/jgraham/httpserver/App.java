package com.jgraham.httpserver;

import com.jgraham.httpserver.ServerConfig;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;


//class App {
//    public static void main(String args[]) throws IOException {
//
//        int port = 0;
//
//        try {
//            port = Integer.parseInt(args[0]);
//        }
//        catch (NumberFormatException nfe) {
//            port = 5000;
//        }
//
//        ServerSocket server = new ServerSocket(port);
//        System.out.println("Listening for connection on port " + port);
//        while (true) {
//            try (Socket socket = server.accept()) {
//                Date today = new Date();
//                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
//                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
//            }
//        }
//    }
//}

class App {
    public static void main(String args[]) throws IOException {

        ServerConfig.configureServer(args);

        int port = ServerConfig.getPort();
        String directory = ServerConfig.getDirectory();

        ServerSocket server = new ServerSocket(port);
        System.out.println("Listening for connection on port " + port);

        while (true) {
            try (Socket socket = server.accept()) {
                Date today = new Date();
                String httpResponse =   "HTTP/1.1 200 OK\r\n\r\n"
                                        + "date: " + today
                                        + "\r\nport: " + port
                                        + "\r\ndirectory: " + directory;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            }
        }
    }
}
