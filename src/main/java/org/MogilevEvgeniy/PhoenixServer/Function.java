package org.MogilevEvgeniy.PhoenixServer;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class Function {

  public static String createNewUser(String login, String password) {
    String result = "";
    try {
      UUID uuid = UUID.randomUUID();
      String key = String.valueOf(uuid);
      Date date = new Date();
      SQLConnect.writeDateBase(
          "INSERT INTO 'users' ('login', 'password', 'key') VALUES ('" + login + "', '" + password
              + "', '" + key + "'); ");
      result = SQLConnect.gettingFromDateBase(login, "users", "login", "key");
      SQLConnect.writeDateBase(
          "INSERT INTO 'dialogID' ('first', 'second') VALUES ('PHOENIX', '" + login + "'); ");
      int id = SQLConnect.gettingIdDialog("PHOENIX", login);
      SQLConnect.writeDateBase(
          "INSERT INTO 'dialog' ('IDDialog', 'text', 'datetime', 'from') VALUES ('" + id
              + "', 'WELCOME', '" + date + "', 'PHOENIX'); ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  public static String login(String login, String password) throws SQLException {
    String tempPassword = SQLConnect.gettingFromDateBase(login, "users", "login", "password");
    System.out.println(tempPassword);
    String result;
    if (tempPassword.equals(password)) {
      result = SQLConnect.gettingFromDateBase(login, "users", "login", "key");
    } else {
      result = "0";
    }
    return result;
  }

  public static int createNewDialog(String key, String to, String text) throws SQLException {
    int result;
    Date date = new Date();
    String from = SQLConnect.gettingFromDateBase(key, "users", "key", "login");
    result = SQLConnect.gettingIdDialog(from, to);
    if (result == 0) {
      SQLConnect.writeDateBase(
          "INSERT INTO 'dialogID' ('first', 'second') VALUES ('" + from + "', '" + to + "'); ");
      result = SQLConnect.gettingIdDialog(from, to);
    }
    SQLConnect.writeDateBase(
        "INSERT INTO 'dialog' ('IDDialog', 'text', 'datetime', 'from') VALUES ('" + result + "', '"
            + text + "', '" + date + "', '" + from + "'); ");
    return 1;
  }

  public static String getDialogNames(String key) throws SQLException {
    String login = SQLConnect.gettingFromDateBase(key, "users", "key", "login");
    String[] result = SQLConnect.gettingDialogNames(login).toArray(new String[0]);
    StringBuilder tempResult;
    if (result.length > 1) {
      tempResult = new StringBuilder(result[0]);
      for (int n = 1; n < result.length; n++) {
        tempResult.append("//").append(result[n]);
      }
    } else {
      tempResult = new StringBuilder(result[0]);
    }
    return tempResult.toString();
  }

  public static String getMessagesDialog(String key, String to) throws SQLException {
    int id = 0;

    String login = SQLConnect.gettingFromDateBase(key, "users", "key", "login");
    id = SQLConnect.gettingIdDialog(login, to);
    String[] result = SQLConnect.gettingDialog(id).toArray(new String[0]);

    StringBuilder tempResult;
    if (result.length > 1) {
      tempResult = new StringBuilder(result[0]);
      for (int n = 1; n < result.length; n++) {
        tempResult.append("//").append(result[n]);
      }
    } else {
      tempResult = new StringBuilder(result[0]);
    }

    return tempResult.toString();
  }

  public static String getProfileInformation(String key) throws SQLException {
    String login = SQLConnect.gettingFromDateBase(key, "users", "key", "login");
    String id = SQLConnect.gettingFromDateBase(key, "users", "key", "id");

    StringBuilder tempResult;

    tempResult = new StringBuilder(login);
    tempResult.append("//").append(id);

    return tempResult.toString();
  }
}