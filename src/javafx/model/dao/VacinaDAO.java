package javafx.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Vacina;

public class VacinaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Vacina vacina) {
        String sql = "INSERT INTO vacina(tipo, datafabricacao, datavalidade, fabricante, quantidade) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, vacina.getTipo());
            stmt.setDate(2, Date.valueOf(vacina.getDataFabricacao()));
            stmt.setDate(3, Date.valueOf(vacina.getDataValidade()));
            stmt.setString(4, vacina.getFabricante());
            stmt.setInt(5, vacina.getQuantidade());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Vacina vacina) {
        String sql = "UPDATE vacina SET tipo=?, datafabricacao=?, datavalidade=?, fabricante=?, quantidade=? WHERE idVacina=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, vacina.getTipo());
            stmt.setDate(2, Date.valueOf(vacina.getDataFabricacao()));
            stmt.setDate(3, Date.valueOf(vacina.getDataValidade()));
            stmt.setString(4, vacina.getFabricante());
            stmt.setInt(5, vacina.getQuantidade());
            stmt.setInt(6, vacina.getIdVacina());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Vacina vacina) {
        String sql = "DELETE FROM vacina WHERE idVacina=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, vacina.getIdVacina());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Vacina> listar() {
        String sql = "SELECT * FROM vacina";
        List<Vacina> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Vacina vacina = new Vacina();
                vacina.setIdVacina(resultado.getInt("idVacina"));
                vacina.setTipo(resultado.getString("tipo"));
                vacina.setDataFabricacao(resultado.getDate("datafabricacao").toLocalDate());
                vacina.setDataValidade(resultado.getDate("datavalidade").toLocalDate());
                vacina.setFabricante(resultado.getString("fabricante"));
                vacina.setQuantidade(resultado.getInt("quantidade"));
                retorno.add(vacina);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Vacina buscar(Vacina vacina) {
        String sql = "SELECT * FROM vacina WHERE idVacina=?";
        Vacina retorno = new Vacina();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, vacina.getIdVacina());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                vacina.setTipo(resultado.getString("tipo"));
                vacina.setDataFabricacao(resultado.getDate("datafabricacao").toLocalDate());
                vacina.setDataValidade(resultado.getDate("datavalidade").toLocalDate());
                vacina.setFabricante(resultado.getString("fabricante"));
                vacina.setQuantidade(resultado.getInt("quantidade"));

                retorno = vacina;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
