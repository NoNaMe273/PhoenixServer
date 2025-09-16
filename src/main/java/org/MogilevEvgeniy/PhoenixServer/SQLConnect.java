package org.MogilevEvgeniy.PhoenixServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;


public class SQLConnect {

  public static Connection connection;
  public static PreparedStatement statement;
  public static ResultSet resultSet;

  public static void connection(String dateBaseName) throws ClassNotFoundException, SQLException {
    connection = null;
    Class.forName("org.sqlite.JDBC");
    connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/" + dateBaseName + ".s3db");
  }

  public static void createDateBase(String request) throws SQLException {
    statement = connection.prepareStatement(request);
    statement.execute();
  }

  public static void writeDateBase(String request) throws SQLException {
    statement = connection.prepareStatement(request);
    statement.execute();
  }

  public static String gettingFromDateBase(String temporaryData, String dateBaseName, String where,
      String type) throws SQLException {
    statement = connection.prepareStatement("SELECT * FROM " + dateBaseName + " WHERE " + where + " = ?");
    statement.setString(1, temporaryData);
    resultSet = statement.executeQuery();
    String result = "";
    if (resultSet.next()) {
      result = resultSet.getString(type);
    }
    return result;
  }

  public static int checkDateBase(String login) throws SQLException {
    statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
    statement.setString(1, login);
    resultSet = statement.executeQuery();
    int loginInDateBase = 1;
    if (resultSet.next()) {
      loginInDateBase = 0;
    }
    return loginInDateBase;
  }

  public static void closeDateBase(int temporaryData) throws SQLException {
    connection.close();
    statement.close();
      if (temporaryData == 2) {
          resultSet.close();
      }
  }

  public static int gettingIdDialog(String from, String to) throws SQLException {
    statement = connection.prepareStatement(
        "SELECT * FROM dialogID WHERE first = ? AND second = ? OR second = ? AND first = ?");
    statement.setString(1, from);
    statement.setString(2, to);
    statement.setString(3, from);
    statement.setString(4, to);
    resultSet = statement.executeQuery();
    int id = 0;
    if (resultSet.next()) {
      id = resultSet.getInt("id");
    }
    return id;
  }

  public static ArrayList<String> gettingDialogNames(String login) throws SQLException {
    ArrayList<String> tempArray = new ArrayList<>();
    statement = connection.prepareStatement("SELECT * FROM dialogID WHERE first = ?");
    statement.setString(1, login);
    resultSet = statement.executeQuery();
    while (resultSet.next()) {
      tempArray.add(resultSet.getString("second"));
    }
    statement = connection.prepareStatement("SELECT * FROM dialogID WHERE second = ?");
    statement.setString(1, login);
    resultSet = statement.executeQuery();
    while (resultSet.next()) {
      tempArray.add(resultSet.getString("first"));
    }
    return tempArray;
  }

  public static ArrayList<String> gettingDialog(int id) throws SQLException {
    ArrayList<String> tempArray = new ArrayList<>();
    statement = connection.prepareStatement("SELECT * FROM dialog WHERE IDDialog = ?");
    statement.setInt(1, id);
    resultSet = statement.executeQuery();
    while (resultSet.next()) {
      tempArray.add(resultSet.getString("datetime"));
      tempArray.add(resultSet.getString("text"));
      tempArray.add(resultSet.getString("from"));
    }
    return tempArray;
  }

  public static String gettingKey(String login, String dateBaseName) throws SQLException {
    statement = connection.prepareStatement("SELECT * FROM " + dateBaseName + " WHERE login = ?");
    statement.setString(1, login);
    resultSet = statement.executeQuery();
    String key = "";
    if (resultSet.next()) {
      key = resultSet.getString("key");
    }
    return key;
  }

}