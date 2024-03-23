/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Cliente;
import javafx.model.domain.Funcionario;
import javafx.model.domain.Veterinario;

/**
 *
 * @author 20211si022
 */
public class FuncionarioDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario(nome, cpf, telefone, email, cargo, salario) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getCargo());
            stmt.setDouble(6, funcionario.getSalario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public boolean inserirLogin(String email, String cargo, String senha) {
        String sql = "INSERT INTO login(email, cargo, senha) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, cargo);
            stmt.setString(3, senha);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, "Erro ao inserir login", ex);
            return false;
        }
    }
     
    public boolean alterarLogin(String email, String cargo, String senha) {
        String sql = "UPDATE login SET email=?, carlo=?, senha=? WHERE email=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, cargo);
            stmt.setString(3, senha);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public boolean removerLogin(Funcionario funcionario) {
        String sql = "DELETE FROM login WHERE email=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, funcionario.getEmail());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome=?, cpf=?, telefone=?, email=?, cargo=?, salario=? WHERE idFunc=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getCargo());
            stmt.setDouble(6, funcionario.getSalario());
            stmt.setInt(7, funcionario.getIdFunc());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Funcionario funcionario) {
        String sql = "DELETE FROM funcionario WHERE idFunc=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, funcionario.getIdFunc());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Funcionario> listar() {
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFunc(resultado.getInt("idFunc"));
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setCpf(resultado.getString("cpf"));
                funcionario.setTelefone(resultado.getString("telefone"));
                funcionario.setEmail(resultado.getString("email"));
                funcionario.setCargo(resultado.getString("cargo"));
                funcionario.setSalario(resultado.getDouble("salario"));
                retorno.add(funcionario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Funcionario buscar(Funcionario funcionario) {
        String sql = "SELECT * FROM funcionario WHERE idFunc=?";
        Funcionario retorno = new Funcionario();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, funcionario.getIdFunc());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setCpf(resultado.getString("cpf"));
                funcionario.setTelefone(resultado.getString("telefone"));
                funcionario.setEmail(resultado.getString("email"));
                funcionario.setCargo(resultado.getString("cargo"));
                funcionario.setSalario(resultado.getDouble("salario"));
                retorno = funcionario;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

}
