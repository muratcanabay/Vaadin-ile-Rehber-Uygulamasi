package io.github.muratcanabay.uniyaz.db;

import io.github.muratcanabay.uniyaz.domain.Kisi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbOperations {
    final String JDBC_CONNECTION_STR = "jdbc:mysql://127.0.0.1:3306/rehber?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    final String USERNAME = "root";
    final String PASSWORD = "root";

    public void kisiEkle(Kisi kisi) {

        String sql = "insert into kisi (adi, soyadi, telNo) " +
                "values (?, ?, ?) ";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, kisi.getAd());
            preparedStatement.setString(2, kisi.getSoyad());
            preparedStatement.setString(3, kisi.getTelNo());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Kisi> kisiListele() {

        List<Kisi> kisiList = new ArrayList<>();
        String sql = "select * from kisi";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("adi");
                String surname = resultSet.getString("soyadi");
                String phone = resultSet.getString("telNo");

                Kisi kisi = new Kisi();
                kisi.setId(id);
                kisi.setAd(name);
                kisi.setSoyad(surname);
                kisi.setTelNo(phone);
                kisiList.add(kisi);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kisiList;
    }

    public void kisiSil(Kisi kisi) {

        String sql = "delete from kisi where adi = ? and soyadi=?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, kisi.getAd());
            preparedStatement.setString(2, kisi.getSoyad());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Kisi> kisiAra(Kisi kisi) {
        List<Kisi> kisiList = new ArrayList<>();
        String sql = "select * from kisi where adi like ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + kisi.getAd() + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("adi");
                String surname = resultSet.getString("soyadi");
                String phone = resultSet.getString("telNo");

                Kisi kisiYeni = new Kisi();
                kisiYeni.setId(id);
                kisiYeni.setAd(name);
                kisiYeni.setSoyad(surname);
                kisiYeni.setTelNo(phone);
                kisiList.add(kisiYeni);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kisiList;
    }
}
