package com.github.sparsick.infra.testing.infratestingdemoapp.scp;

import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class StarWarsLogoClientTest {

     private StarWarsLogoClient clientUnderTest = new StarWarsLogoClient("user", "password", "localhost", 2222);

    @Test
    public void downloadLogo() throws IOException {
        FakeFtpServer fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/starwarslogo.png", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(2222);

        fakeFtpServer.start();
        clientUnderTest.downloadLogo();
        assertThat(new File("/tmp/downloaded.png")).exists();

        fakeFtpServer.stop();
    }
}