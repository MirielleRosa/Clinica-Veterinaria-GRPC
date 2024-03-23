package javafx.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Cliente implements Serializable {

    private int idCliente;
    private String nome;
    private String cpf;
    private String rg;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private int qntAnimais;

    public Cliente() {
    }

    public Cliente(int idCliente, String nome, String cpf, String rg, String telefone, String email, LocalDate dataNascimento) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }
    
    public Cliente(int idCliente, String nome) {
     this.idCliente = idCliente;
     this.nome = nome;
    }
     

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
       public int getNumAnimais() {
        return qntAnimais;
    }

    public void setNumAnimais(int qntAnimais) {
        this.qntAnimais = qntAnimais;
    }

   
    
   
}
