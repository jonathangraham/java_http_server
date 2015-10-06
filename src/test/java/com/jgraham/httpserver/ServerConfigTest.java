package com.jgraham.httpserver;

import junit.framework.TestCase;

import java.io.File;

public class ServerConfigTest
        extends TestCase
{
    public void testServerConfigExists() {
        assertNotNull(new ServerConfig());
    }

    public void testParsesPort() {
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "8000";
        ServerConfig server = new ServerConfig();
        assertEquals(8000, server.parsePort(args));
    }

    public void testDefaultPortNoArguments() {
        String[] args = new String[0];
        ServerConfig server = new ServerConfig();
        assertEquals(5000, server.parsePort(args));
    }

    public void testDefaultPortWithInvalidPort() {
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "eight";
        ServerConfig server = new ServerConfig();
        assertEquals(5000, server.parsePort(args));
    }

    public void testParsesDirectory() {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = "/pub/dir";
        ServerConfig server = new ServerConfig();
        assertEquals("/pub/dir", server.parseDirectory(args));
    }

    public void testDefaultDirectoryNoArguments() {
        String[] args = new String[0];
        ServerConfig server = new ServerConfig();
        assertEquals("/src/main/resources", server.parseDirectory(args));
    }

    public void testRootExists() {
        String directory = (System.getProperty("user.dir")) + "/src/main/resources";
        String file = "";
        boolean check = new File(directory, file).exists();
        assertEquals(true, check);
    }

    public void testFileExists() {
        String directory = (System.getProperty("user.dir")) + "/src/main/resources";
        String string = "GET /file1 OK";
        String[] split = string.split("\\s+");
        String file = split[1];
        boolean check = new File(directory, file).exists();
        assertEquals(true, check);
    }

    public void testFileNotExists() {
        String directory = (System.getProperty("user.dir")) + "/src/main/resources";
        String string = "GET /foobar OK";
        String[] split = string.split("\\s+");
        String file = split[1];
        boolean check = new File(directory, file).exists();
        assertEquals(false, check);
    }



}