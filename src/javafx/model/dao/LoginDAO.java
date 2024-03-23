package javafx.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.model.domain.Vacina;
import javafx.model.domain.Vacinacao;
import javafx.model.domain.Login;

public class LoginDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


public Login buscarPorEmailESenha(String email, String senha) {
    String sql = "SELECT * FROM login WHERE email=? AND senha=?";
    Login retorno = null;

    try {
        // Certifique-se de que a conexão esteja aberta
        if (connection != null && !connection.isClosed()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno = new Login();
                retorno.setIdLogin(resultado.getInt("idLogin"));
                retorno.setEmail(resultado.getString("email"));
                retorno.setCargo(resultado.getString("cargo"));
                retorno.setSenha(resultado.getString("senha"));
            }
        } else {
            System.out.println("Conexão nula ou fechada.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (retorno != null) {
        System.out.println("Retorno do buscarPorEmailESenha: " + retorno);
    } else {
        System.out.println("Nenhum usuário encontrado para o email: " + email);
    }

    return retorno;
}

    public List<Login> listar() {
    String sql = "SELECT * FROM login";
    List<Login> retorno = new ArrayList<>();
    try {
        // Certifique-se de que a conexão esteja aberta
        if (connection != null && !connection.isClosed()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Login login = new Login();
                login.setIdLogin(resultado.getInt("idLogin"));
                login.setEmail(resultado.getString("email"));
                login.setSenha(resultado.getString("senha"));
                retorno.add(login);
            }
        } else {
            System.out.println("Conexão nula ou fechada.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return retorno;
}

}
