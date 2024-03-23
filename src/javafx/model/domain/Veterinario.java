
package javafx.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Veterinario {

    private int idVet;
    private String nome;
    private String crmv;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private String especialidade;
    private LocalDate dataNascimento;
    private String senha;
    int numVacinacoes;
    private Animal animal;
    private Cliente cliente;
    private Vacina vacina;
    

    public Veterinario() {
    }

    public Veterinario(String nome, String crmv, String cpf, String telefone, String email, String endereco, String especialidade, LocalDate dataNascimento, String senha) {
        this.nome = nome;
        this.crmv = crmv;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.especialidade = especialidade;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
    }

    // getters e setters para todos os atributos
    public int getIdVet() {
        return idVet;
    }

    public void setIdVet(int idVet) {
        this.idVet = idVet;
    }

    public int getNumVacinacoes() {
        return numVacinacoes;
    }

    public void setNumVacinacoes(int numVacinacoes) {
        this.numVacinacoes = numVacinacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
      public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
    
      public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    @Override
    public String toString() {
        return this.nome;
    }

  

}
