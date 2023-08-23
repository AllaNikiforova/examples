package ru.ser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static final List<String> RESOURCES = List.of("1.txt", "2.txt", "3.txt", "4.txt", "5.txt");

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            int counter = 0;
            Set<Group> groups = new HashSet<>();

            String[] srts = new String[5];
            String[] strAr2 = {"Ani", "Sam", " Joe"};

            Cloneable a = strAr2;
            CharSequence b = strAr2;
            CharSequence п = "lkn";

            StringBuilder[] str = strAr2;
            CharSequence[] str2 = strAr2;



            while (true) {
                Socket clientSocket2 = serverSocket.accept();
                clientSocket2.setKeepAlive(true);
                ClientSocket clientSocket = new ClientSocket(clientSocket2);
                System.out.println("Server: Клиент " + counter + " подключился");

                Player newPlayer = new Player(clientSocket);
                System.out.println("Server: Создали нового игрока");
                Group groupOfPlayer = null;
                boolean flagAddPlayer = false;
                for (Group group : groups) {
                    if (group.addPlayer(newPlayer)) {
                        flagAddPlayer = true;
                        groupOfPlayer = group;
                        break;
                    }
                }

                if (!flagAddPlayer) {
                    System.out.println("Server: Создали новую группу");
                    Group newGroup = new Group(groups.size());
                    groupOfPlayer = newGroup;
                    newGroup.addPlayer(newPlayer);
                    groups.add(newGroup);

                    File file = new File("/Users/allanikiforova/Desktop/java/контрольные дз/hw3/Nikiforova_Alla_hw3/server/src/main/resources/" + RESOURCES.get(counter % 5));
                    try (FileReader fileReader = new FileReader(file);
                         BufferedReader reader = new BufferedReader(fileReader)) {
                        String line = "";
                        line += reader.readLine() + "\n";
                        newGroup.setText(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Server: Новый игрок подключился к группе " + groupOfPlayer.getNumberGroup());

                ServerClientThread serverClientThread = new ServerClientThread(clientSocket2, groupOfPlayer, newPlayer, groupOfPlayer.getTimeSecondsBeforeStart());
                serverClientThread.start();
                System.out.println("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


