package ua.kiev.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter your login: ");
            String login = scanner.nextLine();
            System.out.println("Enter your password: ");
            String passwd = scanner.nextLine();
            User user = new User();
            user.setLogin(login);
            user.setPass(passwd);
            int result = user.send(Utils.getURL() + "/authorize");
            if (result != 200) {
                System.out.println("HTTP error occured: " + result);
                return;
            }

            Thread th = new Thread(new GetThread());
            th.setDaemon(true);
            th.start();

            int res = 0;
            while (true) {
                System.out.println("Menu: 1 - Send message, 2 - Send Private message, 3 - Show all users ");
                String text = scanner.nextLine();

                if (text.equals("1")) {
                    System.out.println("Enter your message: ");
                    String mes = scanner.nextLine();
                    Message m = new Message(login, mes);
                    res = m.send(Utils.getURL() + "/add");

                    if (res != 200) { // 200 OK
                        System.out.println("HTTP error occured: " + res);
                        return;
                    }
                } else if (text.equals("2")) {
                    System.out.println("Type user name, who you want to send message to:");
                    String ToUser = scanner.nextLine();
                    System.out.println("Enter your private message: ");
                    String privateText = scanner.nextLine();
                    Message privateMessage = new Message(user.getLogin(), ToUser, privateText);
                    res = privateMessage.send(Utils.getURL() + "/add");
                    if (res != 200) {
                        System.out.println("HTTP error occured: " + res);
                        return;
                    }
                } else if (text.equals("3")) {
                    user.getAllUsers();
                } else {//(text.isEmpty()) {
                    Message m = new Message(user.getLogin());
                    res = m.send(Utils.getURL() + "/logout");
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
