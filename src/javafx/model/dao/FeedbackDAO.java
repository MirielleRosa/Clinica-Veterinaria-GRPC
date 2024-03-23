package javafx.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.model.domain.Animal;
import javafx.model.domain.Feedback;
import javafx.model.domain.Funcionario;
import javafx.model.domain.Veterinario;

public class FeedbackDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Feedback feedback) {
        String sql = "INSERT INTO feedback (data_feedback, animal, veterinario, nota_avaliacao, comentario) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, (Date) feedback.getDataFeedback());
            stmt.setInt(2, feedback.getAnimal().getIdAnimal());
            stmt.setInt(3, feedback.getVeterinario().getIdVet());
            stmt.setInt(4, feedback.getNotaAvaliacao());
            stmt.setString(5, feedback.getComentario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Feedback feedback) {
        String sql = "UPDATE feedback SET data_feedback=?, animal=?, veterinario=?, nota_avaliacao=?, comentario=? WHERE idFeedback=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, (Date) feedback.getDataFeedback());
            stmt.setInt(2, feedback.getAnimal().getIdAnimal());
            stmt.setInt(3, feedback.getVeterinario().getIdVet());
            stmt.setInt(4, feedback.getNotaAvaliacao());
            stmt.setString(5, feedback.getComentario());
            stmt.setInt(6, feedback.getidFeedBack());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Feedback feedback) {
        String sql = "DELETE FROM feedback WHERE idFeedback=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, feedback.getIdFeedback());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Feedback> listar() {
        String sql = "SELECT * FROM feedback f INNER JOIN animal a ON f.animal = a.idAnimal INNER JOIN veterinario v ON f.veterinario = v.id";
        List<Feedback> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Feedback feedback = new Feedback();
                feedback.setidFeedback(resultado.getInt("idFeedback"));
                feedback.setDataFeedback(resultado.getDate("data_feedback"));
                Animal animal = new Animal();
                animal.setIdAnimal(resultado.getInt("idAnimal"));
                animal.setNome(resultado.getString("nome"));
                animal.setEspecie(resultado.getString("especie"));
                animal.setRaca(resultado.getString("raca"));
                feedback.setAnimal(animal);
                Veterinario veterinario = new Veterinario();
                veterinario.setIdVet(resultado.getInt("id"));
                veterinario.setNome(resultado.getString("nome_vet"));
                veterinario.setCpf(resultado.getString("cpf"));
                veterinario.setTelefone(resultado.getString("telefone"));
                veterinario.setEmail(resultado.getString("email"));
                feedback.setVeterinario(veterinario);
                feedback.setNotaAvaliacao(resultado);



     }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    
    public Map<Integer, ArrayList> listarQuantidadeFeedbackPorMes() {
    String sql = "SELECT EXTRACT(MONTH FROM data_feedback) AS mes, COUNT(*) AS quantidade FROM feedback GROUP BY mes";
    Map<Integer, ArrayList> dados = new HashMap<>();

    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        while (resultado.next()) {
            int mes = resultado.getInt("mes");
            int quantidade = resultado.getInt("quantidade");

            if (dados.containsKey(mes)) {
                ArrayList<Integer> valores = dados.get(mes);
                valores.add(quantidade);
                dados.put(mes, valores);
            } else {
                ArrayList<Integer> valores = new ArrayList<>();
                valores.add(quantidade);
                dados.put(mes, valores);
            }
        }

    } catch (SQLException ex) {
        Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return dados;
}
    
    public Map<Integer, ArrayList> listarQuantidadeFeedbackPorMes2() {
    String sql = "SELECT EXTRACT(MONTH FROM data_feedback) AS mes, COUNT(*) AS quantidade FROM feedback GROUP BY mes";
    Map<Integer, ArrayList> dados = new HashMap<>();

    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();

        while (resultado.next()) {
            int mes = resultado.getInt("mes");
            int quantidade = resultado.getInt("quantidade");

            if (dados.containsKey(mes)) {
                ArrayList<Integer> valores = dados.get(mes);
                valores.add(quantidade);
                dados.put(mes, valores);
            } else {
                ArrayList<Integer> valores = new ArrayList<>();
                valores.add(quantidade);
                dados.put(mes, valores);
            }
        }

    } catch (SQLException ex) {
        Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return dados;
}
}