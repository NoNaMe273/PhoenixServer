package org.MogilevEvgeniy.PhoenixServer;

import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Function {


    public static void qwe(String[] args) throws ClassNotFoundException, SQLException {
        SQLConnect.Conn("123");
        SQLConnect.CreateDB("123");
        SQLConnect.WriteDB("123");
        SQLConnect.CloseDB(1);
    }


    public static int NewUser(String log, String pass) {
        int i;
        try {
            SQLConnect.Conn("usersDB");
            SQLConnect.CreateDB("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'password' text);");
            SQLConnect.WriteDB("INSERT INTO 'users' ('login', 'password') VALUES ('"+log+"', '"+pass+"'); ");
            SQLConnect.CloseDB(0);
            i = 1;
        }  catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(i);
        return i;
    }
    public static int Login(String log, String pass) throws ClassNotFoundException, SQLException {
        SQLConnect.Conn("usersDB");
        String pas = SQLConnect.ReadDB(log, "users");
        SQLConnect.CloseDB(2);
        int i;
        if (pas.equals(pass)) i = 1;
        else i = 0;
        return i;
    }


    public static int NewMess(String from, String to, String text) throws SQLException, ClassNotFoundException {
        int i = 0;
        java.util.Date date = new java.util.Date();

        SQLConnect.Conn("Message");
        SQLConnect.CreateDB("CREATE TABLE if not exists 'dialogID' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'first' text, 'second' text);");
        i = SQLConnect.CheckIDMessage(from, to);
        if (i==0) {
            SQLConnect.WriteDB("INSERT INTO 'dialogID' ('first', 'second') VALUES ('" + from + "', '" + to + "'); ");
        }
        i = SQLConnect.CheckIDMessage(from, to);
        SQLConnect.CloseDB(1);
        SQLConnect.Conn("Message");
        SQLConnect.CreateDB("CREATE TABLE if not exists 'dialog' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'IDDialog' int, 'datetime' NOW, 'text' text, 'from' text);");
        SQLConnect.WriteDB("INSERT INTO 'dialog' ('IDDialog', 'text', 'datetime', 'from') VALUES ('"+i+"', '"+text+"', '"+date+"', '"+from+"'); ");
        SQLConnect.CloseDB(1);
        return 1;
    }

    public static String AllDialog(String log) throws SQLException, ClassNotFoundException {
        SQLConnect.Conn("Message");
        SQLConnect.CreateDB("CREATE TABLE if not exists 'dialogID' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'first' text, 'second' text);");
        String[] i = SQLConnect.AllDialog(log).toArray(new String[0]);
        SQLConnect.CloseDB(2);
        String tmp = "";
        if (i.length > 1){
            tmp = i[0];
            for(int n = 1; n < i.length; n++){
                tmp = tmp + "//" + i[n];
            }
        }
        else tmp = i[0];
        return tmp;
    }



    public static int Cookie(String log, String pass) throws SQLException, ClassNotFoundException{
        SQLConnect.Conn("usersDB");
        log = crypto.decrypt(log, "NjWvgGwFXPJBxWQYFwOlehVhxzEgRMJFESSuUjPwoIfGBoHuRdsyeLImKrGpVSkLgSniiyzAFvNceZXUlTkJHOvfcKiUSMqQnpDFaPgfZubVqnlLHDWvGaUvashpWEzj");
        pass = crypto.decrypt(pass, "NjWvgGwFXPJBxWQYFwOlehVhxzEgRMJFESSuUjPwoIfGBoHuRdsyeLImKrGpVSkLgSniiyzAFvNceZXUlTkJHOvfcKiUSMqQnpDFaPgfZubVqnlLHDWvGaUvashpWEzj");
        String pas = SQLConnect.ReadDB(log, "users");
        SQLConnect.CloseDB(2);
        int i = 0;
        String line = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("serverSQL/src/BD/users.txt"))) {
            line = reader.readLine(); // Прочитает всю первую строку
            System.out.println("Прочитанная строка: " + line);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        pass = crypto.encrypt(pass, line);
        if (pas.equals(pass)) i = 1;
        else i = 0;
        return i;
    }

    public static String Dialog(String log, String to) throws SQLException, ClassNotFoundException {
        int ID = 0;

        SQLConnect.Conn("Message");
        SQLConnect.CreateDB("CREATE TABLE if not exists 'dialogID' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'first' text, 'second' text);");
        ID = SQLConnect.CheckIDMessage(log, to);
        SQLConnect.CloseDB(1);
        SQLConnect.Conn("Message");
        SQLConnect.CreateDB("CREATE TABLE if not exists 'dialog' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'IDDialog' int, 'datetime' NOW, 'text' text, 'from' text);");
        String[] i = SQLConnect.GetDialog(ID).toArray(new String[0]);
        SQLConnect.CloseDB(2);

        String tmp = "";
        if (i.length > 1){
            tmp = i[0];
            for(int n = 1; n < i.length; n++){
                tmp = tmp + "//" + i[n];
            }
        }
        else tmp = i[0];

        return tmp;
    }
}