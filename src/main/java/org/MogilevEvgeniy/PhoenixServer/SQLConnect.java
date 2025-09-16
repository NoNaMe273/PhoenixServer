package org.MogilevEvgeniy.PhoenixServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;


public class SQLConnect {

  public static Connection conn;
  public static PreparedStatement statmt;
  public static ResultSet resSet;

  public static void connection(String dateBaseName) throws ClassNotFoundException, SQLException {
    conn = null;
    Class.forName("org.sqlite.JDBC");
    conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/" + dateBaseName + ".s3db");
  }

  public static void createDateBase(String request) throws SQLException {
    statmt = conn.prepareStatement(request);
    statmt.execute();
  }

  public static void writeDateBase(String request) throws SQLException {
    statmt = conn.prepareStatement(request);
    statmt.execute();
  }

  public static String gettingFromDateBase(String temp, String dateBaseName, String where,
      String type) throws SQLException {
    statmt = conn.prepareStatement("SELECT * FROM " + dateBaseName + " WHERE " + where + " = ?");
    statmt.setString(1, temp);
    resSet = statmt.executeQuery();
    String result = "";
    if (resSet.next()) {
      result = resSet.getString(type);
    }
    return result;
  }

  public static int checkDateBase(String login) throws SQLException {
    statmt = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
    statmt.setString(1, login);
    resSet = statmt.executeQuery();
    int loginInDateBase = 1;
    if (resSet.next()) {
      loginInDateBase = 0;
    }
    return loginInDateBase;
  }

  public static void closeDateBase(int temp) throws SQLException {
    conn.close();
    statmt.close();
      if (temp == 2) {
          resSet.close();
      }
  }

  public static int gettingIdDialog(String from, String to) throws SQLException {
    statmt = conn.prepareStatement(
        "SELECT * FROM dialogID WHERE first = ? AND second = ? OR second = ? AND first = ?");
    statmt.setString(1, from);
    statmt.setString(2, to);
    statmt.setString(3, from);
    statmt.setString(4, to);
    resSet = statmt.executeQuery();
    int id = 0;
    if (resSet.next()) {
      id = resSet.getInt("id");
    }
    return id;
  }

  public static ArrayList<String> gettingDialogNames(String login) throws SQLException {
    ArrayList<String> tempArray = new ArrayList<>();
    statmt = conn.prepareStatement("SELECT * FROM dialogID WHERE first = ?");
    statmt.setString(1, login);
    resSet = statmt.executeQuery();
    while (resSet.next()) {
      tempArray.add(resSet.getString("second"));
    }
    statmt = conn.prepareStatement("SELECT * FROM dialogID WHERE second = ?");
    statmt.setString(1, login);
    resSet = statmt.executeQuery();
    while (resSet.next()) {
      tempArray.add(resSet.getString("first"));
    }
    return tempArray;
  }

  public static ArrayList<String> gettingDialog(int id) throws SQLException {
    ArrayList<String> tempArray = new ArrayList<>();
    statmt = conn.prepareStatement("SELECT * FROM dialog WHERE IDDialog = ?");
    statmt.setInt(1, id);
    resSet = statmt.executeQuery();
    while (resSet.next()) {
      tempArray.add(resSet.getString("datetime"));
      tempArray.add(resSet.getString("text"));
      tempArray.add(resSet.getString("from"));
    }
    return tempArray;
  }

  public static String gettingKey(String login, String dateBaseName) throws SQLException {
    statmt = conn.prepareStatement("SELECT * FROM " + dateBaseName + " WHERE login = ?");
    statmt.setString(1, login);
    resSet = statmt.executeQuery();
    String key = "";
    if (resSet.next()) {
      key = resSet.getString("key");
    }
    return key;
  }

}