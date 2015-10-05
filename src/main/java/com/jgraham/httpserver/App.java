package com.jgraham.httpserver;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;

class App {
    public static void main(String args[]) throws IOException {

        ServerConfig config = new ServerConfig();
        int port = config.parsePort(args);
        String directory = config.parseDirectory(args);

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Listening for connection on port " + port);

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                Date today = new Date();
                InputStream input = clientSocket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                String request = br.readLine();
                String httpResponse =   "HTTP/1.1 200 OK\r\n\r\n"
                        + "date: " + today
                        + "\r\nport: " + port
                        + "\r\ndirectory: " + directory
                        + "\r\nrequest: " + request;
                OutputStream output = clientSocket.getOutputStream();
                output.write(httpResponse.getBytes());
            }
        }
    }
}