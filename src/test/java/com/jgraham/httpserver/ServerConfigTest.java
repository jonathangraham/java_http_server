package com.jgraham.httpserver;

import junit.framework.TestCase;

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
        assertEquals("/public", server.parseDirectory(args));
    }
}