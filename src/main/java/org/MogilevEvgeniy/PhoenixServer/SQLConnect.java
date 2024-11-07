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

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn(String DBName) throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/"+DBName+".s3db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB(String REQ) throws ClassNotFoundException, SQLException
    {
        statmt = conn.prepareStatement(REQ);
        statmt.execute();

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB(String REQ) throws SQLException
    {
        statmt = conn.prepareStatement(REQ);
        statmt.execute();

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static String ReadDB(String log, String DBName) throws ClassNotFoundException, SQLException
    {
        statmt = conn.prepareStatement("SELECT * FROM "+DBName+" WHERE login = ?");
        statmt.setString(1, log);
        resSet = statmt.executeQuery();

        String pas = "";

        if (resSet.next()) {
                pas = resSet.getString("password");
                System.out.println( "password = " + pas );
                System.out.println();
        }



        System.out.println("Таблица выведена");
        return pas;
    }

    public static int CheckDB(String log) throws ClassNotFoundException, SQLException
    {
        statmt = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
        statmt.setString(1, log);
        resSet = statmt.executeQuery();

        int logInDB = 1;

        if (resSet.next()) {
            logInDB = 0;
        }

        return logInDB;
    }

    // --------Закрытие--------
    public static void CloseDB(int i) throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        if(i == 2) resSet.close();

        System.out.println("Соединения закрыты");
    }

    public static int CheckIDMessage(String from, String to) throws ClassNotFoundException, SQLException
    {
        statmt = conn.prepareStatement("SELECT * FROM dialogID WHERE first = ? AND second = ? OR second = ? AND first = ?");
        statmt.setString(1, from);
        statmt.setString(2, to);
        statmt.setString(3, from);
        statmt.setString(4, to);
        resSet = statmt.executeQuery();

        int i = 0;

        if (resSet.next()) {
            i = resSet.getInt("id");
            System.out.println( "ID = " + i );
            System.out.println();
        }

        return i;
    }

    public static ArrayList<String> AllDialog(String log) throws ClassNotFoundException, SQLException{
        ArrayList<String> i = new ArrayList<>();
        statmt = conn.prepareStatement("SELECT * FROM dialogID WHERE first = ?");
        statmt.setString(1, log);
        resSet = statmt.executeQuery();
        while (resSet.next()) {
            i.add(resSet.getString("second"));
        }
        statmt = conn.prepareStatement("SELECT * FROM dialogID WHERE second = ?");
        statmt.setString(1, log);
        resSet = statmt.executeQuery();
        while (resSet.next()) {
            i.add(resSet.getString("first"));
        }
        System.out.println(i);
        return i;
    }

    public static ArrayList<String> GetDialog(int ID) throws ClassNotFoundException, SQLException{
        ArrayList<String> i = new ArrayList<>();
        statmt = conn.prepareStatement("SELECT * FROM dialog WHERE IDDialog = ?");
        statmt.setInt(1, ID);
        resSet = statmt.executeQuery();
        while (resSet.next()) {
            i.add(resSet.getString("datetime"));
            i.add(resSet.getString("text"));
            i.add(resSet.getString("from"));
        }
        return i;
    }

}