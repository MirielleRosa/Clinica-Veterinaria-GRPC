package javafx.model.domain;

import java.time.LocalDate;

public class Animal {

    private int idAnimal;
    private Cliente dono;
    private String nome;
    private int idade;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private double peso;
    private String genero;

    public Animal() {
    }

    public Animal(int idAnimal, String nome, int idade, String especie, String raca, Cliente dono, LocalDate dataNascimento, double peso, String genero) {
        this.idAnimal = idAnimal;
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.raca = raca;
        this.dono = dono;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.genero = genero;

    }
    
     public Animal(int idAnimal, String nome) {
     this.idAnimal = idAnimal;
       this.nome = nome;
     }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int cdAnimal) {
        this.idAnimal = cdAnimal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
