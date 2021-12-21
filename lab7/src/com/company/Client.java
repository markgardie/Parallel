package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int PORT = 8283;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    Client() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Введите IP для подключения к серверу.");
        System.out.println("Формат: xxx.xxx.xxx.xxx");

        String ip = scan.nextLine();

        try{
            Socket socket = new Socket(ip, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Введите свой ник:");
            out.println(scan.nextLine());

            Resender resend = new Resender();
            resend.start();

            String str = "";
            while (!str.equals("exit")) {
                str = scan.nextLine();
                out.println(str);
            }
            resend.setStop();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }



    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            System.err.println("Ошибка!");
        }
    }

    private class Resender extends Thread {
        private boolean stoped;

        public void setStop() {
            stoped = true;
        }
        @Override
        public void run() {
            try {
                while (!stoped) {
                    String str = in.readLine();
                    System.out.println(str);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при получении сообщения.");
                e.printStackTrace();
            }
        }
    }

}
