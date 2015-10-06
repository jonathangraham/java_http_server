package com.jgraham.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
                httpResponse = routeDirectory(route);
            }
            else if (new File(route, path).exists()) {
                httpResponse = routeFiles(route, path);
            }
            else {
                httpResponse = route404(path);
            }
            OutputStream output = clientSocket.getOutputStream();
            output.write(httpResponse.getBytes());
        }
    }

    private String routeDirectory(String route) {
        StringBuilder files = buildDirectoryContents(route);
        return "HTTP/1.1 200 OK\r\n\r\n" + files;
    }

    private String routeFiles(String route, String path) throws IOException {
        String file = route + "/" + path;
        BufferedReader in = new BufferedReader(new FileReader(file));
        StringBuilder lines = new StringBuilder();
        String line;
        while((line = in.readLine()) != null)
        {
            lines.append(line);
            lines.append("\n");
        }
        in.close();
        return "HTTP/1.1 200 OK\r\n\r\n" + lines;
    }

    private String route404(String path) {
        return "HTTP/1.1 404 Not Found\r\n\r\n" + path + " not found." ;
    }

    private StringBuilder buildDirectoryContents(String directoryPath) {
        File f = new File(directoryPath);
        String[] files = f.list();

        StringBuilder fileNames = new StringBuilder();
        fileNames.append("<!DOCTYPE html>\n");
        fileNames.append("<!DOCTYPE html>\n<html>\n<body>\n");
        for (String file: files) {
            fileNames.append(("<a href=/" + file + ">" + file + "</a><br>"));
        }
        fileNames.append("</body>\n</html>");
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
