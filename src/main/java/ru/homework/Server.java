package ru.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int port = 13228;
    private static char letter;
    private static String city;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                ) {
                    String answer;
                    if (letter == '\u0000') {
                        out.println(String.format("???"));
                        answer = in.readLine();
                        letter = letterChecker(answer);
                        city = answer;
                        out.println(String.format("OK"));
                    } else {
                        out.println(String.format(city + ". Вам на: " + Character.toUpperCase(letter) + "."));
                        answer = in.readLine();
                        if (answer.toLowerCase().charAt(0) == letter) {
                            letter = letterChecker(answer);
                            city = answer;
                            out.println("OK");
                        } else {
                            out.println("Не OK");
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Не могу стартовать сервер");
                    e.printStackTrace();
                }
            }
        }
    }

    private static char letterChecker(String answer) {
        int count = 1;
        char candidate = answer.toLowerCase().charAt(answer.length() - count);
        while (candidate == 'ь' || candidate == 'ъ') {
            count++;
            candidate = answer.toLowerCase().charAt(answer.length() - count);
        }
        return candidate;
    }
}