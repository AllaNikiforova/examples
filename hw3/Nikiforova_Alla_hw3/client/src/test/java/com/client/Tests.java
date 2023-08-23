package com.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class Tests {
    private Player player;

    @BeforeEach
    public void setup() throws IOException {
        player = new Player();
        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream(new byte[0]);
        clientSocket = new ClientSocket();
    }

    @Test
    public void testGetCharactersTyped() {
        player.increaseCharactersTyped(100);
        Assertions.assertEquals(100, player.getCharactersTyped());
    }

    @Test
    public void testIncreaseCharactersTyped() {
        player.increaseCharactersTyped(100);
        player.increaseCharactersTyped(50);
        Assertions.assertEquals(150, player.getCharactersTyped());
    }

    @Test
    public void testGetErrorsMade() {
        player.increaseErrorsMaid(3);
        Assertions.assertEquals(3, player.getErrorsMade());
    }

    @Test
    public void testIncreaseErrorsMaid() {
        player.increaseErrorsMaid(3);
        player.increaseErrorsMaid(2);
        Assertions.assertEquals(5, player.getErrorsMade());
    }

    @Test
    public void testIsOver() {
        player.setOver(true);
        Assertions.assertTrue(player.isOver());
    }

    private ClientSocket clientSocket;
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;

    @Test
    public void testIsClosed() {
        Assertions.assertFalse(clientSocket.isClosed());
    }

    @Test
    public void testClose() throws IOException {
        clientSocket.close();
        Assertions.assertTrue(clientSocket.isClosed());
    }
}