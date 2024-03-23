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
import javafx.model.domain.Veterinario;

public class VeterinarioDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Veterinario veterinario) {
        String sql = "INSERT INTO veterinario(nome, crmv, telefone, email, cpf, especialidade, data_nascimento, senha) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getCrmv());
            stmt.setString(3, veterinario.getTelefone());
            stmt.setString(4, veterinario.getEmail());
            stmt.setString(5, veterinario.getCpf());
            stmt.setString(6, veterinario.getEspecialidade());
            stmt.setDate(7, Date.valueOf(veterinario.getDataNascimento()));
            stmt.setString(8, veterinario.getSenha());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, "Erro ao inserir login", ex);
            return false;
        }
    }
      
    public boolean removerLogin(Veterinario veterinario) {
        String sql = "DELETE FROM login WHERE email=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veterinario.getEmail());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }




    public boolean alterar(Veterinario veterinario) {
    String sql = "UPDATE veterinario SET nome=?, crmv=?, telefone=?, email=?, cpf=?, especialidade=?, data_nascimento=?, senha=? WHERE id=?";
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, veterinario.getNome());
        stmt.setString(2, veterinario.getCrmv());
        stmt.setString(3, veterinario.getTelefone());
        stmt.setString(4, veterinario.getEmail());
        stmt.setString(5, veterinario.getCpf());
        stmt.setString(6, veterinario.getEspecialidade());
        stmt.setDate(7, Date.valueOf(veterinario.getDataNascimento()));
        stmt.setString(8, veterinario.getSenha());
        stmt.setInt(9, veterinario.getIdVet());
        stmt.execute();
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}


    public boolean remover(Veterinario veterinario) {
        String sql = "DELETE FROM veterinario WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, veterinario.getIdVet());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Veterinario> listar() {
        String sql = "SELECT * FROM veterinario";
        List<Veterinario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Veterinario veterinario = new Veterinario();
                veterinario.setIdVet(resultado.getInt("id"));
                veterinario.setNome(resultado.getString("nome"));
                veterinario.setCrmv(resultado.getString("crmv"));
                veterinario.setTelefone(resultado.getString("telefone"));
                veterinario.setEmail(resultado.getString("email"));
                veterinario.setCpf(resultado.getString("cpf"));
                veterinario.setEspecialidade(resultado.getString("especialidade"));
                veterinario.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
                veterinario.setSenha(resultado.getString("senha"));
                retorno.add(veterinario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Veterinario buscarPorNomeESenha(String nome, String senha) {
    String sql = "SELECT * FROM veterinario WHERE nome=? AND senha=?";
    Veterinario retorno = null;
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, senha);
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            retorno = new Veterinario();
            retorno.setIdVet(resultado.getInt("id"));
            retorno.setNome(resultado.getString("nome"));
            retorno.setCrmv(resultado.getString("crmv"));
            retorno.setTelefone(resultado.getString("telefone"));
            retorno.setEmail(resultado.getString("email"));
            retorno.setCpf(resultado.getString("cpf"));
            retorno.setEspecialidade(resultado.getString("especialidade"));
            retorno.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
            retorno.setSenha(resultado.getString("senha"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return retorno;
}

    
    public Veterinario buscar(Veterinario veterinario) {
        String sql = "SELECT * FROM veterinario WHERE id=?";
        Veterinario retorno = new Veterinario();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, veterinario.getIdVet());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                veterinario.setNome(resultado.getString("nome"));
                veterinario.setCrmv(resultado.getString("crmv"));
                veterinario.setTelefone(resultado.getString("telefone"));
                veterinario.setEmail(resultado.getString("email"));
                //veterinario.setEndereco(resultado.getString("endereco"));
                veterinario.setEspecialidade(resultado.getString("especialidade"));
                veterinario.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
                veterinario.setSenha(resultado.getString("senha"));
                retorno = veterinario;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Veterinario getVeterinarioMaisVacinou() {
        Veterinario veterinarioMaisVacinou = null;
        String sql = "SELECT v.id, v.nome, COUNT(a.idVacinacao) AS num_vacinacoes\n"
                + "FROM veterinario v\n"
                + "JOIN vacinacao a ON v.id = a.veterinario\n"
                + "GROUP BY v.id, v.nome\n"
                + "ORDER BY num_vacinacoes DESC\n"
                + "LIMIT 1;";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                veterinarioMaisVacinou = new Veterinario();
                Vacinacao vacinacao = new Vacinacao();

                veterinarioMaisVacinou.setIdVet(rs.getInt("id"));
                veterinarioMaisVacinou.setNome(rs.getString("nome"));

                veterinarioMaisVacinou.setNumVacinacoes(rs.getInt("num_vacinacoes"));

            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return veterinarioMaisVacinou;
    }

    public List<Veterinario> getVeterinariosComVacinacoes() {
        List<Veterinario> retorno = new ArrayList<>();
        String sql = "SELECT v.id, v.nome, COUNT(a.idVacinacao) AS num_vacinacoes\n"
                + "FROM veterinario v\n"
                + "JOIN vacinacao a ON v.id = a.veterinario\n"
                + "GROUP BY v.id, v.nome\n"
                + "ORDER BY num_vacinacoes DESC;";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Vacinacao vacinacao = new Vacinacao();
                Veterinario veterinario = new Veterinario();

                veterinario.setIdVet(rs.getInt("id"));
                veterinario.setNome(rs.getString("nome"));

                veterinario.setNumVacinacoes(rs.getInt("num_vacinacoes"));

                retorno.add(veterinario);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Veterinario> getVeterinariosVacinacoes() {
    List<Veterinario> lista = new ArrayList<>();
    String sql = "SELECT v.id, v.nome AS nomeVeterinario, an.nome AS nomeAnimal, c.nome AS nomeCliente, vc.tipo AS tipoVacina, COUNT(a.idVacinacao) AS num_vacinacoes\n"
            + "FROM veterinario v\n"
            + "JOIN vacinacao a ON v.id = a.veterinario\n"
            + "JOIN animal an ON an.idAnimal = a.animal\n"
            + "JOIN cliente c ON c.idCliente = a.cliente\n"
            + "JOIN vacina vc ON vc.idVacina = a.vacina\n"
            + "GROUP BY v.id, v.nome, an.nome, c.nome, vc.tipo\n"
            + "ORDER BY num_vacinacoes DESC;";
    try {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Veterinario veterinario = new Veterinario();
            Animal animal = new Animal();
            Cliente cliente = new Cliente();
            Vacina vacina = new Vacina();
            veterinario.setIdVet(rs.getInt("id"));
            veterinario.setNome(rs.getString("nomeVeterinario"));
            veterinario.setNumVacinacoes(rs.getInt("num_vacinacoes"));
            animal.setNome(rs.getString("nomeAnimal"));
            vacina.setTipo(rs.getString("tipoVacina"));
            cliente.setNome(rs.getString("nomeCliente"));
            
            // Definir qual animal está sendo referenciado
            veterinario.setVacina(vacina);
            veterinario.setCliente(cliente);
            veterinario.setAnimal(animal);

            lista.add(veterinario);
        }
        stmt.close();
        rs.close();
    } catch (SQLException ex) {
        Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lista;
}

}
