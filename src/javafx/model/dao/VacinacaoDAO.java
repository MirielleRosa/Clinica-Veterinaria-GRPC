package javafx.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.model.domain.Vacina;
import javafx.model.domain.Vacinacao;
import javafx.model.domain.Veterinario;

public class VacinacaoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Vacinacao vacinacao) {
        String sql = "INSERT INTO vacinacao(data_aplicacao, vacina, animal, cliente, veterinario) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(vacinacao.getDataAplicacao()));
            stmt.setInt(2, vacinacao.getVacina().getIdVacina());
            stmt.setInt(3, vacinacao.getAnimal().getIdAnimal());
            stmt.setInt(4, vacinacao.getDono().getIdCliente());
            stmt.setInt(5, vacinacao.getVeterinario().getIdVet());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VacinacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(Vacinacao vacinacao) {
    String sql = "UPDATE vacinacao SET data_aplicacao=?, vacina=?, animal=?, cliente=?, veterinario=? WHERE idVacinacao=?";
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setDate(1, Date.valueOf(vacinacao.getDataAplicacao()));
        stmt.setInt(2, vacinacao.getVacina().getIdVacina());
        stmt.setInt(3, vacinacao.getAnimal().getIdAnimal());
        stmt.setInt(4, vacinacao.getDono().getIdCliente());
        stmt.setInt(5, vacinacao.getVeterinario().getIdVet());
        stmt.setInt(6, vacinacao.getIdVacinacao());
        stmt.execute();
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(VeterinarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}


    public boolean remover(Vacinacao vacinacao) {
        String sql = "DELETE FROM vacinacao WHERE idVacinacao=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, vacinacao.getIdVacinacao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VacinacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Vacinacao> listar() {
        String sql = "SELECT v.*, a.nome AS nomeAnimal, c.nome AS nomeCliente, vc.tipo AS tipoVacina, ve.nome AS nomeVeterinario\n"
                + "FROM vacinacao v\n"
                + "JOIN animal a ON a.idAnimal = v.animal\n"
                + "JOIN cliente c ON c.idCliente = v.cliente\n"
                + "JOIN vacina vc ON vc.idVacina = v.vacina\n"
                + "JOIN veterinario ve ON ve.id = v.veterinario;";

        List<Vacinacao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Vacinacao vacinacao = new Vacinacao();
                Animal animal = new Animal();
                Cliente cliente = new Cliente();
                Vacina vacina = new Vacina();
                Veterinario veterinario = new Veterinario();

                vacinacao.setIdVacinacao(resultado.getInt("idVacinacao"));
                vacinacao.setDataAplicacao(resultado.getDate("data_aplicacao").toLocalDate());

                animal.setIdAnimal(resultado.getInt("animal"));
                animal.setNome(resultado.getString("nomeAnimal"));

                cliente.setIdCliente(resultado.getInt("cliente"));
                cliente.setNome(resultado.getString("nomeCliente"));

                vacina.setIdVacina(resultado.getInt("vacina"));
                vacina.setTipo(resultado.getString("tipoVacina"));

                veterinario.setIdVet(resultado.getInt("veterinario"));
                veterinario.setNome(resultado.getString("nomeVeterinario"));

                vacinacao.setAnimal(animal);
                vacinacao.setDono(cliente);
                vacinacao.setVacina(vacina);
                vacinacao.setVeterinario(veterinario);

                retorno.add(vacinacao);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VacinacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Vacinacao> buscar(Animal animal) {
    String sql = "SELECT v.*, c.idCliente, c.nome, vc.tipo, ve.nome\n"
            + "FROM vacinacao v\n"
            + "JOIN animal a ON a.idAnimal = v.animal\n"
            + "JOIN cliente c ON c.idCliente = v.cliente\n"
            + "JOIN vacina vc ON vc.idVacina = v.vacina\n"
            + "JOIN veterinario ve ON ve.id = v.veterinario\n"
            + "WHERE a.idAnimal = ?;";

    List<Vacinacao> vacinacoes = new ArrayList<>();
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, animal.getIdAnimal());
        ResultSet resultado = stmt.executeQuery();
        while (resultado.next()) {
            Vacinacao vacinacao = new Vacinacao();
            Cliente dono = new Cliente();
            Vacina vacina = new Vacina();
            Veterinario veterinario = new Veterinario();
            dono.setIdCliente(resultado.getInt("idCliente"));
            dono.setNome(resultado.getString("dono"));
            vacina.setTipo(resultado.getString("tipoVacina"));
            veterinario.setNome(resultado.getString("nomeVeterinario"));
            animal.setNome(resultado.getString("nomeAnimal"));
            animal.setIdAnimal(resultado.getInt("idAnimal"));
            vacinacao.setDono(dono);
            vacinacao.setVacina(vacina);
            vacinacao.setVeterinario(veterinario);
            vacinacao.setAnimal(animal);
            vacinacao.setDataAplicacao(resultado.getDate("data_aplicacao").toLocalDate());
            vacinacoes.add(vacinacao);
        }
    } catch (SQLException ex) {
        Logger.getLogger(VacinacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return vacinacoes;
}

    public Map<Integer, ArrayList> listarQuantidadeVacinacaoPorMes() {
        String sql = "SELECT COUNT(idVacinacao), EXTRACT(YEAR FROM data_aplicacao) AS ano, EXTRACT(MONTH FROM data_aplicacao) AS mes\n"
                + "FROM vacinacao\n"
                + "GROUP BY ano, mes\n"
                + "ORDER BY ano, mes;";
        Map<Integer, ArrayList> retorno = new HashMap();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano"))) {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                } else {
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public int contarVacinacoesPorAnimalEData(Animal animal, LocalDate data) {
    String sql = "SELECT COUNT(*) FROM vacinacao WHERE animal = ? AND data_aplicacao = ?";

    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, animal.getIdAnimal());
        stmt.setDate(2, Date.valueOf(data));
        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            return resultado.getInt(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(VacinacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 0;
}


}
