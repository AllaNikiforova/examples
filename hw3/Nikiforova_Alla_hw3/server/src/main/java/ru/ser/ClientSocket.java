package ru.ser;

import java.io.*;
import java.net.Socket;

/**
 * Класс для сокета сервера. Используется для передачи информации (строк и целых чисел)
 */
public class ClientSocket {
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public ClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
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

    public void close() throws IOException {
        clientSocket.close();
        in.close();
        out.close();
    }
}
