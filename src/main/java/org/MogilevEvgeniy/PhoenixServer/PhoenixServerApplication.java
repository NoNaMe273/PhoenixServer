package org.MogilevEvgeniy.PhoenixServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class PhoenixServerApplication {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SQLConnect.connection("DB");
		SQLConnect.createDateBase("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'password' text);");
		SQLConnect.createDateBase("CREATE TABLE if not exists 'dialogID' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'first' text, 'second' text);");
		SQLConnect.createDateBase("CREATE TABLE if not exists 'dialog' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'IDDialog' int, 'datetime' text, 'text' text, 'from' text);");
		SpringApplication.run(PhoenixServerApplication.class, args);
	}
}