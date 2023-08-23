package com.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Класс для клиентского сокета. Существует, чтобы удобнее использовать Socket, DataOutputStream, DataInputStream
 */
public class ClientSocket {
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public ClientSocket() throws IOException {
        clientSocket = new Socket("localhost", 8888);
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void writeUTF(String str) throws IOException {
        out.writeUTF(str);
    }

    public String readUTF() throws IOException {
        return in.readUTF();
    }

    public void writeInt(int x) throws IOException {
        out.writeInt(x);
    }

    public int readInt() throws IOException {
        return in.readInt();
    }

    public boolean isClosed() {
        return clientSocket.isClosed();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
