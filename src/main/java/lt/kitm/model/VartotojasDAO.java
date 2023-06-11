package lt.kitm.model;

import java.sql.*;

public class VartotojasDAO {
    private static final String URL = "jdbc:mysql://localhost/db";
    private static final String[] prisijungimas = new String[]{"root", ""};

    public static void create(Vartotojas vartotojas) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO vartotojai (email, vardas, pavarde, slaptazodis, tipas) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, vartotojas.getEmail());
        preparedStatement.setString(2, vartotojas.getVardas());
        preparedStatement.setString(3, vartotojas.getPavarde());
        preparedStatement.setString(4, vartotojas.getSlaptazodis());
        preparedStatement.setString(5, vartotojas.getTipas());
        preparedStatement.executeUpdate();
        connection.close();
    }

    public static Vartotojas read(String email) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vartotojai WHERE email = ?");
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        Vartotojas vartotojas = null;
        if (resultSet.isBeforeFirst()) {
            resultSet.next();
            vartotojas = new Vartotojas(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
        }
        return vartotojas;
    }
}
