package javafx.model.domain;

import java.time.LocalDate;

public class Vacina {

    private int idVacina;
    private String tipo;
    private LocalDate datafabricacao;
    private LocalDate datavalidade;
    private String fabricante;
    private int quantidade;

    public Vacina() {

    }

    public Vacina(String tipo, LocalDate datafabricacao, LocalDate datavalidade, String fabricante, int quantidade) {
        this.tipo = tipo;
        this.datafabricacao = datafabricacao;
        this.datavalidade = datavalidade;
        this.fabricante = fabricante;
        this.quantidade = quantidade;
    }

    // getters e setters para todos os atributos
    public int getIdVacina() {
        return idVacina;
    }

    public void setIdVacina(int idVacina) {
        this.idVacina = idVacina;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataFabricacao() {
        return datafabricacao;
    }

    public void setDataFabricacao(LocalDate datafabricacao) {
        this.datafabricacao = datafabricacao;
    }

    public LocalDate getDataValidade() {
        return datavalidade;
    }

    public void setDataValidade(LocalDate datavalidade) {
        this.datavalidade = datavalidade;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return this.tipo;
    }
}
