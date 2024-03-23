
package javafx.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Login {

    private int idLogin;
    private String email;
    private String cargo;
    private String senha;
    

    public Login() {
    }

    public Login(String email, String cargo, String senha) {
        this.email = email;
        this.cargo = cargo;
        this.senha = senha;
    }

    // getters e setters para todos os atributos
    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return this.email;
    }

  

}
