
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


public class AnimalDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Animal animal) {
        String sql = "INSERT INTO animal(nome, idade, especie, raca, dono, data_nascimento, peso, genero) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, animal.getNome());
            stmt.setInt(2, animal.getIdade());
            stmt.setString(3, animal.getEspecie());
            stmt.setString(4, animal.getRaca());
            stmt.setInt(5, animal.getDono().getIdCliente());
            stmt.setDate(6, Date.valueOf(animal.getDataNascimento()));
            stmt.setDouble(7, animal.getPeso());
            stmt.setString(8, animal.getGenero());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Animal animal) {
        String sql = "UPDATE animal SET nome=?, idade=?, especie=?, raca=?, dono=?, data_nascimento=?, peso=?, genero=?   WHERE idAnimal=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, animal.getNome());
            stmt.setInt(2, animal.getIdade());
            stmt.setString(3, animal.getEspecie());
            stmt.setString(4, animal.getRaca());
            stmt.setInt(5, animal.getDono().getIdCliente());
            stmt.setDate(6, Date.valueOf(animal.getDataNascimento()));
            stmt.setDouble(7, animal.getPeso());
            stmt.setString(8, animal.getGenero());
            stmt.setInt(9, animal.getIdAnimal());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Animal animal) {
        String sql = "DELETE FROM animal WHERE idAnimal=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, animal.getIdAnimal());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Animal> listar() {
        String sql = "SELECT a.*, c.idCliente AS idDono, c.nome AS nomeDono\n"
                + "FROM animal a\n"
                + "JOIN cliente c ON a.dono = c.idCliente;";

        List<Animal> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt1 = connection.prepareStatement(sql);
            ResultSet resultado1 = stmt1.executeQuery();

            while (resultado1.next()) {
                Animal animal = new Animal();
                Cliente dono = new Cliente(); // create a new Cliente object

                animal.setIdAnimal(resultado1.getInt("idAnimal"));
                animal.setNome(resultado1.getString("nome"));
                animal.setIdade(resultado1.getInt("idade"));
                animal.setEspecie(resultado1.getString("especie"));
                animal.setRaca(resultado1.getString("raca"));
                animal.setGenero(resultado1.getString("genero"));
                animal.setPeso(resultado1.getDouble("peso"));
                animal.setDataNascimento(resultado1.getDate("data_nascimento").toLocalDate());

                dono.setIdCliente(resultado1.getInt("idDono")); // retrieve the owner's ID from the ResultSet
                dono.setNome(resultado1.getString("nomeDono")); // retrieve the owner's name from the ResultSet
                animal.setDono(dono); // set the owner as the animal's owner

                retorno.add(animal);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    public List<Animal> listarAnimalPorCliente(Cliente cliente) {
        String sql = "SELECT a.idAnimal, a.nome\n"
                + "FROM animal a\n"
                + "JOIN cliente c ON c.idCliente = a.dono\n"
                + "WHERE c.idCliente = ?";
        List<Animal> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getIdCliente());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Animal animal = new Animal();
                animal.setIdAnimal(rs.getInt("idAnimal"));
                animal.setNome(rs.getString("nome"));
                lista.add(animal);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Erro ao listar animais por cliente: " + e);
        }
        return lista;
    }

    public List<Animal> listarPorCliente() {
        String sql = "SELECT c.idCliente, c.nome, c.cpf, c.telefone, COUNT(a.idAnimal) AS quantidade_animais "
                + "FROM animal a "
                + "JOIN cliente c ON c.idCliente = a.dono "
                + "GROUP BY c.idCliente, c.nome, c.cpf, c.telefone "
                + "ORDER BY quantidade_animais DESC";

        List<Animal> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Cliente dono = new Cliente();
                dono.setIdCliente(resultado.getInt("idCliente"));
                dono.setNome(resultado.getString("nome"));
                dono.setCpf(resultado.getString("cpf"));
                dono.setTelefone(resultado.getString("telefone"));

                Animal animal = new Animal();
                animal.setDono(dono);

                retorno.add(animal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

}
