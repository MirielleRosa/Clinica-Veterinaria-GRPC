/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.model.dao;

import static io.grpc.InternalChannelz.instance;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.model.domain.Agendamento;
import javafx.model.domain.Vacina;
import static net.sf.jasperreports.engine.util.DigestUtils.instance;
import static net.sf.jasperreports.engine.util.StyleUtil.instance;
import static org.apache.poi.hssf.record.formula.ConcatPtg.instance;
import org.postgresql.core.ConnectionFactory;
import static sun.font.SunLayoutEngine.instance;

public class AgendamentoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Agendamento agendamento) {
        String sql = "INSERT INTO agendamento(cliente, animal, data, hora, tipo, status) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agendamento.getCliente().getIdCliente());
            stmt.setInt(2, agendamento.getAnimal().getIdAnimal());
            stmt.setDate(3, Date.valueOf(agendamento.getData()));
            stmt.setTime(4, Time.valueOf(agendamento.getHora()));
            stmt.setString(5, agendamento.getTipo());
            stmt.setString(6, agendamento.getStatus());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Agendamento agendamento) {
        String sql = "UPDATE agendamento SET cliente=?, animal=?, data=?, hora=?, tipo=? WHERE idAgendamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agendamento.getCliente().getIdCliente());
            stmt.setInt(2, agendamento.getAnimal().getIdAnimal());
            stmt.setDate(3, java.sql.Date.valueOf(agendamento.getData()));
            stmt.setTime(4, java.sql.Time.valueOf(agendamento.getHora()));
            stmt.setString(5, agendamento.getTipo());
            stmt.setInt(6, agendamento.getIdAgendamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
  
    public boolean alterarStatus(Agendamento agendamento) {
    String sql = "UPDATE agendamento SET status=? WHERE idAgendamento=?";
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, agendamento.getStatus());
        stmt.setInt(2, agendamento.getIdAgendamento());
        stmt.executeUpdate();  // Use executeUpdate() para operações de atualização
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}

    public boolean remover(Agendamento agendamento) {
        String sql = "DELETE FROM agendamento WHERE idAgendamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agendamento.getIdAgendamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean atualizarStatusAgendamento(int idAgendamento, String novoStatus) {
    String sql = "UPDATE agendamento SET status=? WHERE idAgendamento=?";
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, novoStatus);
        stmt.setInt(2, idAgendamento);
        stmt.execute();
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}


    public List<Agendamento> listar() {
        String sql = "SELECT agendamento.*, animal.nome AS nomeAnimal, cliente.nome AS nomeCliente\n"
                + "FROM agendamento \n"
                + "INNER JOIN animal ON agendamento.animal = animal.idAnimal\n"
                + "INNER JOIN cliente ON agendamento.cliente = cliente.idCliente";

        List<Agendamento> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {

                Agendamento agendamento = new Agendamento();

                Animal animal = new Animal();
                Cliente cliente = new Cliente();

                agendamento.setIdAgendamento(resultado.getInt("idAgendamento"));
                agendamento.setData(resultado.getDate("data").toLocalDate());
                agendamento.setHora(resultado.getTime("hora").toLocalTime());
                agendamento.setTipo(resultado.getString("tipo"));
                agendamento.setStatus(resultado.getString("status"));
                
                animal.setIdAnimal(resultado.getInt("animal"));
                animal.setNome(resultado.getString("nomeAnimal"));

                cliente.setIdCliente(resultado.getInt("cliente"));
                cliente.setNome(resultado.getString("nomeCliente"));

                agendamento.setAnimal(animal);
                agendamento.setCliente(cliente);

                retorno.add(agendamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
     public List<Agendamento> listarAgendamentosDisponiveis() {
        String sql = "SELECT agendamento.*, animal.nome AS nomeAnimal, cliente.nome AS nomeCliente\n"
                + "FROM agendamento \n"
                + "INNER JOIN animal ON agendamento.animal = animal.idAnimal\n"
                + "INNER JOIN cliente ON agendamento.cliente = cliente.idCliente\n"
                + "WHERE agendamento.tipo IS NULL";

        List<Agendamento> agendamentosDisponiveis = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Agendamento agendamento = new Agendamento();
                Animal animal = new Animal();
                Cliente cliente = new Cliente();

                agendamento.setIdAgendamento(resultado.getInt("idAgendamento"));
                agendamento.setData(resultado.getDate("data").toLocalDate());
                agendamento.setHora(resultado.getTime("hora").toLocalTime());
                agendamento.setTipo(resultado.getString("tipo"));
                agendamento.setStatus(resultado.getString("status"));

                animal.setIdAnimal(resultado.getInt("animal"));
                animal.setNome(resultado.getString("nomeAnimal"));

                cliente.setIdCliente(resultado.getInt("cliente"));
                cliente.setNome(resultado.getString("nomeCliente"));

                agendamento.setAnimal(animal);
                agendamento.setCliente(cliente);

                agendamentosDisponiveis.add(agendamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agendamentosDisponiveis;
    }

    public Agendamento buscar(Agendamento agendamento) {
        String sql = "SELECT * FROM agendamento WHERE idAgendamento=?";
        Agendamento retorno = new Agendamento();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agendamento.getIdAgendamento());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cliente cliente = new Cliente();
                Animal animal = new Animal();

                agendamento.setIdAgendamento(resultado.getInt("idAgendamento"));
                agendamento.setData(resultado.getDate("data").toLocalDate());
                agendamento.setHora(resultado.getTime("hora").toLocalTime());
                animal.setNome(resultado.getString("animal"));
                cliente.setNome(resultado.getString("cliente"));
                agendamento.setTipo(resultado.getString("tipo"));
                agendamento.setStatus(resultado.getString("status"));
                agendamento.setCliente(cliente);
                retorno = agendamento;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;

    }

   public List<Agendamento> contarConsultasDoAnimalNoDia(Animal animal, LocalDate data) {
    List<Agendamento> consultas = new ArrayList<>();
    String sql = "SELECT * FROM agendamento \n"
            + "JOIN animal ON agendamento.animal = animal.idAnimal\n"
            + "WHERE animal.idAnimal = ? AND agendamento.data = ?;";

    try {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, animal.getIdAnimal());
        pstmt.setDate(2, Date.valueOf(data));
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Agendamento agendamento = new Agendamento();
            // Preencha os dados do agendamento com base nos valores do ResultSet
            // Exemplo: agendamento.setId(rs.getInt("id"));
            consultas.add(agendamento);
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println("Erro ao contar consultas do animal no dia: " + e.getMessage());
    }

    return consultas;
}

 
    public int contarConsultasDoAnimalNoDia(String animal, LocalDate data) {
    int numConsultas = 0;
    String sql = "SELECT COUNT(*) FROM agendamento WHERE animal = ? AND DATE(data) = ?";

    try {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, animal);
        pstmt.setDate(2, Date.valueOf(data));
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            numConsultas = rs.getInt(1);
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println("Erro ao contar consultas do animal no dia: " + e.getMessage());
    }

    return numConsultas;
}
    
     public boolean atualizarAgendamento(Agendamento agendamento) {
        String sql = "UPDATE agendamento SET status= WHERE idAgendamento=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, agendamento.getStatus());
            stmt.setInt(2, agendamento.getIdAgendamento());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Agendamento> listarNovosAgendamentos(int ultimoIdConhecido) {
    String sql = "SELECT agendamento.*, animal.nome AS nomeAnimal, cliente.nome AS nomeCliente\n"
            + "FROM agendamento \n"
            + "INNER JOIN animal ON agendamento.animal = animal.idAnimal\n"
            + "INNER JOIN cliente ON agendamento.cliente = cliente.idCliente\n"
            + "WHERE agendamento.idAgendamento > ?"; // Adicionado a cláusula WHERE

    List<Agendamento> novosAgendamentos = new ArrayList<>();
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, ultimoIdConhecido);
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            Agendamento agendamento = new Agendamento();
            Animal animal = new Animal();
            Cliente cliente = new Cliente();

            agendamento.setIdAgendamento(resultado.getInt("idAgendamento"));
            agendamento.setData(resultado.getDate("data").toLocalDate());
            agendamento.setHora(resultado.getTime("hora").toLocalTime());
            agendamento.setTipo(resultado.getString("tipo"));
            agendamento.setStatus(resultado.getString("status"));

            animal.setIdAnimal(resultado.getInt("animal"));
            animal.setNome(resultado.getString("nomeAnimal"));

            cliente.setIdCliente(resultado.getInt("cliente"));
            cliente.setNome(resultado.getString("nomeCliente"));

            agendamento.setAnimal(animal);
            agendamento.setCliente(cliente);

            novosAgendamentos.add(agendamento);
        }
    } catch (SQLException ex) {
        Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return novosAgendamentos;
}

    
public boolean verificarConsultaExistente(Animal animal, LocalDate data, LocalTime horario) {
    boolean consultaExistente = false;
    String sql = "SELECT COUNT(*) FROM agendamento " +
            "WHERE animal = ? AND data = ? AND hora = ?";

    try {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, animal.getIdAnimal());
        pstmt.setDate(2, Date.valueOf(data));
        pstmt.setTime(3, Time.valueOf(horario));
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            consultaExistente = count > 0;
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println("Erro ao verificar consulta existente: " + e.getMessage());
    }

    return consultaExistente;
}


}
