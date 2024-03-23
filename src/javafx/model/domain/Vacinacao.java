package javafx.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vacinacao {

    private int idVacinacao;
    private Cliente dono;
    private Animal animal;
    private Veterinario veterinario;
    private Vacina vacina;
    private LocalDate dataAplicacao;
    private List<Vacinacao> listVacinacao;

    public Vacinacao() {
        listVacinacao = new ArrayList<>();
    }

    public Vacinacao(int idVacinacao, Cliente dono, Animal animal, Veterinario veterinario, Vacina vacina, LocalDate dataAplicacao) {
        this.idVacinacao = idVacinacao;
        this.dataAplicacao = dataAplicacao;
        this.veterinario = veterinario;
        this.animal = animal;
        this.vacina = vacina;
        this.dono = dono;
        listVacinacao = new ArrayList<>();

    }

    public int getIdVacinacao() {
        return idVacinacao;
    }

    public void setIdVacinacao(int idVacinacao) {
        this.idVacinacao = idVacinacao;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
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

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public List<Vacinacao> GetlistVacinacao() {
        return listVacinacao;
    }

    public void setlistVacinacao(List<Vacinacao> listVacinacao) {
        this.listVacinacao = listVacinacao;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }
}
