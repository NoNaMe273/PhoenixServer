package org.MogilevEvgeniy.PhoenixServer;

import java.sql.SQLException;
import java.util.Date;

public class Function {

    public static int createNewUser(String login, String password) {
        int result;
        try {
            SQLConnect.createDateBase("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'password' text);");
            SQLConnect.writeDateBase("INSERT INTO 'users' ('login', 'password') VALUES ('" + login + "', '" + password + "'); ");
            result = 1;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String login(String login, String password) throws ClassNotFoundException, SQLException {
        String tempPassword = SQLConnect.readDateBase(login, "users");
        int result;
        if (tempPassword.equals(password)) result = 1;
        else result = 0;
        return String.valueOf(result);
    }

    public static int createNewDialog(String from, String to, String text) throws SQLException, ClassNotFoundException {
        int result;
        Date date = new Date();
        SQLConnect.createDateBase("CREATE TABLE if not exists 'dialogID' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'first' text, 'second' text);");
        result = SQLConnect.gettingIdDialog(from, to);
        if (result == 0) {
            SQLConnect.writeDateBase("INSERT INTO 'dialogID' ('first', 'second') VALUES ('" + from + "', '" + to + "'); ");
        }
        result = SQLConnect.gettingIdDialog(from, to);
        SQLConnect.createDateBase("CREATE TABLE if not exists 'dialog' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'IDDialog' int, 'datetime' NOW, 'text' text, 'from' text);");
        SQLConnect.writeDateBase("INSERT INTO 'dialog' ('IDDialog', 'text', 'datetime', 'from') VALUES ('" + result + "', '" + text + "', '" + date + "', '" + from + "'); ");
        return 1;
    }

    public static String getDialogNames(String login) throws SQLException, ClassNotFoundException {
        SQLConnect.createDateBase("CREATE TABLE if not exists 'dialogID' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'first' text, 'second' text);");
        String[] result = SQLConnect.gettingDialogNames(login).toArray(new String[0]);
        StringBuilder tempResult;
        if (result.length > 1) {
            tempResult = new StringBuilder(result[0]);
            for (int n = 1; n < result.length; n++) {
                tempResult.append("//").append(result[n]);
            }
        } else tempResult = new StringBuilder(result[0]);
        return tempResult.toString();
    }

    public static String getMessagesDialog(String login, String to) throws SQLException, ClassNotFoundException {
        int id = 0;

        SQLConnect.createDateBase("CREATE TABLE if not exists 'dialogID' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'first' text, 'second' text);");
        id = SQLConnect.gettingIdDialog(login, to);
        SQLConnect.createDateBase("CREATE TABLE if not exists 'dialog' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'IDDialog' int, 'datetime' NOW, 'text' text, 'from' text);");
        String[] result = SQLConnect.gettingDialog(id).toArray(new String[0]);

        StringBuilder tempResult;
        if (result.length > 1) {
            tempResult = new StringBuilder(result[0]);
            for (int n = 1; n < result.length; n++) {
                tempResult.append("//").append(result[n]);
            }
        } else tempResult = new StringBuilder(result[0]);

        return tempResult.toString();
    }
}