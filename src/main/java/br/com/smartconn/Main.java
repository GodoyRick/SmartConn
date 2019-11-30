package br.com.smartconn;

import br.com.smartconn.connection.*;

import java.io.IOException;

/**
 * @author Henrique Nascimento
 *
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] args) {

        //credenciais do facebook
        FacebookConnection fb = new FacebookConnection("", "");

        try {
            Profile pf = fb.connectAndRetrieveProfile();

            System.out.print("Nome : ");
            System.out.println(pf.getName());

            System.out.println("Notificações : " + (pf.getNotifications().size() - 1));

            pf.getNotifications().forEach( n -> {
                System.out.println();
                int j = pf.getNotifications().indexOf(n) + 1;
                System.out.print(j + " : ");
                System.out.println(n.getBody());
                System.out.println(n.getWhen());
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
