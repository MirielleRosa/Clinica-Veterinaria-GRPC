
package javafx.model.domain;

import java.sql.ResultSet;
import java.util.Date;


public class Feedback {
    private Date dataFeedback;
    private Animal animal;
    private Veterinario veterinario;
    private int notaAvaliacao;
    private String comentario;
  

    public Feedback(Date dataFeedback, Animal animal, Veterinario veterinario, int notaAvaliacao, String comentario) {
        this.dataFeedback = dataFeedback;
        this.animal = animal;
        this.veterinario = veterinario;
        this.notaAvaliacao = notaAvaliacao;
        this.comentario = comentario;
    }

    public Feedback() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // getters e setters para todos os atributos
    public Date getDataFeedback() {
        return dataFeedback;
    }

    public void setDataFeedback(Date dataFeedback) {
        this.dataFeedback = dataFeedback;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public int getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(int notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setNotaAvaliacao(ResultSet resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getidFeedBack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdFeedback() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setidFeedback(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdAnimal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdVeterinario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
