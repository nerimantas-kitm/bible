package lt.kitm.model;

import lt.kitm.dto.KnygaDTO;

import java.sql.*;
import java.util.ArrayList;

public class KnygaDAO {
    private static final String URL = "jdbc:mysql://localhost/db";
    private static final String[] prisijungimas = new String[]{"root", ""};

    public static ArrayList<KnygaDTO> visosKnygos() throws SQLException {
        ArrayList<KnygaDTO> knygos = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM knygos JOIN kategorijos ON kategorijos_id = kategorijos.id");
        while (resultSet.next()) {
            knygos.add(new KnygaDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getInt(7),
                    resultSet.getString(9)
                    ));
        }
        statement.close();
        connection.close();
        return knygos;
    }

    public static void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM knygos WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void create(Knyga knyga) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO knygos (isbn, pavadinimas, santrauka, nuotrauka, puslapiu_skaicius, kategorijos_id) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, knyga.getIsbn());
        preparedStatement.setString(2, knyga.getPavadinimas());
        preparedStatement.setString(3, knyga.getSantrauka());
        preparedStatement.setString(4, knyga.getNuotrauka());
        preparedStatement.setInt(5, knyga.getPuslapiuSkaicius());
        preparedStatement.setInt(6, knyga.getKategorijosId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void update(Knyga knyga) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE knygos SET isbn = ?, pavadinimas = ?, santrauka = ?, nuotrauka = ?, puslapiu_skaicius = ?, kategorijos_id = ? WHERE id = ?");
        preparedStatement.setString(1, knyga.getIsbn());
        preparedStatement.setString(2, knyga.getPavadinimas());
        preparedStatement.setString(3, knyga.getSantrauka());
        preparedStatement.setString(4, knyga.getNuotrauka());
        preparedStatement.setInt(5, knyga.getPuslapiuSkaicius());
        preparedStatement.setInt(6, knyga.getKategorijosId());
        preparedStatement.setInt(7, knyga.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void pamegtiKnyga(int vartotojoId, int knygosId) throws SQLException {
        //TODO: neleisti pamegti daugiau nei viena kartą
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO pamegtos_knygos VALUES(?, ?)");
        preparedStatement.setInt(1, vartotojoId);
        preparedStatement.setInt(2, knygosId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static ArrayList<KnygaDTO> gautiPamegtasKnygas(int vartotojoId) throws SQLException {
        ArrayList<KnygaDTO> knygos = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM knygos JOIN kategorijos ON kategorijos_id = kategorijos.id JOIN pamegtos_knygos ON knygos.id = pamegtos_knygos.knygos_id WHERE pamegtos_knygos.vartotojo_id = ?");
        preparedStatement.setInt(1, vartotojoId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            knygos.add(new KnygaDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getInt(7),
                    resultSet.getString(9)
            ));
        }
        preparedStatement.close();
        connection.close();
        return knygos;
    }

    public static void rezervuotiKnyga(int vartotojoId, int knygosId) throws SQLException {
        //TODO: neleisti rezervuoti daugiau nei viena kartą
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO rezervacijos VALUES(?, ?)");
        preparedStatement.setInt(1, vartotojoId);
        preparedStatement.setInt(2, knygosId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static ArrayList<KnygaDTO> gautiRezervuotasKnygas(int vartotojoId) throws SQLException {
        ArrayList<KnygaDTO> knygos = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM knygos JOIN kategorijos ON kategorijos_id = kategorijos.id JOIN rezervacijos ON knygos.id = rezervacijos.knygos_id WHERE rezervacijos.vartotojo_id = ?");
        preparedStatement.setInt(1, vartotojoId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            knygos.add(new KnygaDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getInt(7),
                    resultSet.getString(9)
            ));
        }
        preparedStatement.close();
        connection.close();
        return knygos;
    }

    public static boolean knygaRezervuota(int knygosId) throws SQLException {
        boolean knygaUzimta = false;
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rezervacijos WHERE knygos_id = ?");
        preparedStatement.setInt(1, knygosId);
        ResultSet resultSet = preparedStatement.executeQuery();
        knygaUzimta = resultSet.isBeforeFirst();
        preparedStatement.close();
        connection.close();
        return knygaUzimta;
    }

    public static void nebemegtiKnygos(int vartotojoId, int knygosId) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pamegtos_knygos WHERE vartotojo_id = ? AND knygos_id = ?");
        preparedStatement.setInt(1, vartotojoId);
        preparedStatement.setInt(2, knygosId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void atsauktiRezervacija(int vartotojoId, int knygosId) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM rezervacijos WHERE vartotojo_id = ? AND knygos_id = ?");
        preparedStatement.setInt(1, vartotojoId);
        preparedStatement.setInt(2, knygosId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static boolean arVartotojasPamegesKnyga(int vartotojoId, int knygosId) throws SQLException {
        boolean knygaVartotojoPamygta = false;
        Connection connection = DriverManager.getConnection(URL, prisijungimas[0], prisijungimas[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pamegtos_knygos WHERE vartotojo_id = ? AND knygos_id = ?");
        preparedStatement.setInt(1, vartotojoId);
        preparedStatement.setInt(2, knygosId);
        ResultSet resultSet = preparedStatement.executeQuery();
        knygaVartotojoPamygta = resultSet.isBeforeFirst();
        preparedStatement.close();
        connection.close();
        return knygaVartotojoPamygta;
    }
}
