package lt.kitm.model;

import java.sql.*;
import java.util.ArrayList;

public class KategorijaDAO {
    private static final String URL = "jdbc:mysql://localhost/db";
    private static final String[] prisijungimas = new String[]{"root", ""};

    public static ArrayList<Kategorija> visosKategorijos() throws SQLException {
        ArrayList<Kategorija> kategorijos = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM kategorijos");
        while (resultSet.next()) {
            kategorijos.add(new Kategorija(resultSet.getInt(1), resultSet.getString(2)));
        }
        statement.close();
        connection.close();
        return kategorijos;
    }

    public static void create(String pavadinimas) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kategorijos (pavadinimas) VALUES (?)");
        preparedStatement.setString(1, pavadinimas);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM kategorijos WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void update(Kategorija kategorija) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE kategorijos SET pavadinimas = ? WHERE ID = ?");
        preparedStatement.setString(1, kategorija.getPavadinimas());
        preparedStatement.setInt(2, kategorija.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static boolean kategorijaTuriKnygu(int id) throws SQLException {
        boolean turi = false;
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM knygos WHERE kategorijos_id = ? LIMIT 1;");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        turi = resultSet.isBeforeFirst();
        preparedStatement.close();
        connection.close();
        return turi;
    }
}
