package com.github.sparsick.infra.testing.infratestingdemoapp.scp;

import net.schmizz.sshj.SSHClient;

import java.io.IOException;

public class StarWarsLogoClient {

    private String password;
    private String username;
    private String hostname;
    private int port;

    public StarWarsLogoClient(String username, String password, String hostname, int port) {
        this.password = password;
        this.username = username;
        this.hostname = hostname;
        this.port = port;
    }


    public void downloadLogo() throws IOException {
        SSHClient client = new SSHClient();
        client.connect(hostname, port);
        client.authPassword(username, password);
        client.newSFTPClient().getFileTransfer().download("/data/starwarslogo.png", "/tmp/downloaded.png");
        client.disconnect();
    }
}
