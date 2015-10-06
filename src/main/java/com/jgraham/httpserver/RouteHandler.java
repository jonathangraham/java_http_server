package com.jgraham.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class RouteHandler {
    public void getResponse (int port, String directory) throws IOException {
        String route = (System.getProperty("user.dir")) + directory;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Listening for connection on port " + port);
        getResponse(serverSocket, route);
    }

    private void getResponse (ServerSocket serverSocket, String route) throws IOException {
        String httpResponse;

        while (true) try (Socket clientSocket = serverSocket.accept()) {
            String path = getPath(clientSocket);
            if ("/".equals(path)) {
                List files = buildDirectoryContents(route);
                httpResponse = "HTTP/1.1 200 OK\r\n\r\n"
                        + "\r\nfile directory: " + files;
            }
            else if (new File(route, path).exists()) {
                httpResponse = "HTTP/1.1 200 OK\r\n\r\n"
                        + "\r\nrequest: " + path;
            }
            else {
                httpResponse = "HTTP/1.1 404 Not Found\r\n\r\n"
                        + "\r\n404 error: " + path;
            }
            OutputStream output = clientSocket.getOutputStream();
            output.write(httpResponse.getBytes());
        }
    }

    private List<String> buildDirectoryContents(String directoryPath) {
        File f = new File(directoryPath);
        File[] files = f.listFiles();
        List<String> fileNames = new ArrayList<>();

        for (File file : files) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    private String getPath(Socket clientSocket) throws IOException {
        InputStream input = clientSocket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String request = br.readLine();
        String[] requestArray = request.split("\\s+");
        return requestArray[1];
    }
}
