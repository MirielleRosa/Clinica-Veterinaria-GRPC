/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;

public class ClienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente(nome, cpf, rg, telefone, email, data_nascimento) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getRg());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    public boolean alterar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome=?, cpf=?, rg=?, telefone=?, email=?, data_nascimento=? WHERE idCliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getRg());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            stmt.setInt(7, cliente.getIdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE idCliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getIdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Cliente> listarClientes() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultado.getInt("idCliente"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setRg(resultado.getString("rg"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
                retorno.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Cliente> listar() {
        String sql = "SELECT c.idCliente, c.nome, c.cpf, c.telefone, COUNT(a.idAnimal) AS quantidade_animais "
                + "FROM animal a "
                + "JOIN cliente c ON c.idCliente = a.dono "
                + "GROUP BY c.idCliente, c.nome, c.cpf, c.telefone "
                + "ORDER BY quantidade_animais DESC";

        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultado.getInt("idCliente"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setNumAnimais(resultado.getInt("quantidade_animais"));

                retorno.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public List<Cliente> listarPorCliente() {
        String sql = "SELECT c.idCliente, c.nome, c.cpf, c.telefone, COUNT(a.idAnimal) AS quantidade_animais "
                + "FROM animal a "
                + "JOIN cliente c ON c.idCliente = a.dono "
                + "GROUP BY c.idCliente, c.nome, c.cpf, c.telefone "
                + "ORDER BY quantidade_animais DESC";

        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Cliente dono = new Cliente();
                dono.setIdCliente(resultado.getInt("idCliente"));
                dono.setNome(resultado.getString("nome"));
                dono.setCpf(resultado.getString("cpf"));
                dono.setTelefone(resultado.getString("telefone"));

                dono.setNumAnimais(resultado.getInt("quantidade_animais"));

                retorno.add(dono);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

}
