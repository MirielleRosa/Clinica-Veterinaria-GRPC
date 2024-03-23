
package javafx.model.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Agendamento {

    private int idAgendamento;
    private LocalDate dataConsulta;
    private LocalTime hora;
    private Animal animal;
    private Cliente cliente;
    private String tipoConsulta;
    private String status;
    private String descricao;
    private List<Agendamento> listAgendamento;
    public Object Mensageiro;

    public Agendamento() {
        listAgendamento = new ArrayList<>();
    }

    public Agendamento(int idAgendamento, String tipoConsulta, int idCliente, int idAnimal, LocalDate convertStringToLocalDate, LocalTime convertStringToLocalTime, String status, String descricao) {
        this.idAgendamento = idAgendamento;
        this.dataConsulta = dataConsulta;
        this.hora = hora;
        this.animal = animal;
        this.cliente = cliente;
        this.tipoConsulta = tipoConsulta;
        this.status = status;
        this.descricao = descricao;
        listAgendamento = new ArrayList<>();
    }

   

    

    // getters e setters para todos os atributos
    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public LocalDate getData() {
        return dataConsulta;
    }

    public void setData(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipoConsulta;
    }

    public void setTipo(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
  
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
     public List<Agendamento> GetlistAgendamento() {
        return listAgendamento;
    }

    public void setlistAgendamento(List<Agendamento> listAgendamento) {
        this.listAgendamento = listAgendamento;
    }

}
