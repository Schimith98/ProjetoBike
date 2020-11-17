package br.edu.iff.projetobike.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.edu.iff.projetobike.annotation.EmailValidation;

@Entity
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteID;
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Nome obrigatório")
    @Length(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    private String nome;
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Sobrenome obrigatório")
    @Length(max = 50, message = "Sobrenome deve ter no máximo 50 caracteres")
    private String sobrenome;
    @Column(nullable = false, unique = true, length = 11)
    @NotBlank(message = "CPF obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;
    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "Email obrigatório")
    @EmailValidation
    private String email;
    @Column( nullable = false, length = 10)
    @NotBlank(message = "Senha obrigatório")
    @Length(max = 10, min = 5, message = "Sobrenome deve ter entre 5 e 10 caracteres")
    private String senha;
    @Column(nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private TipoCartaoCreditoEnum tipoCartaoCredito;
    @Column(nullable = false, length = 16)
    @NotBlank(message = "Cartão de crédito obrigatório")
    @Length(max = 16, min = 16,  message = "Número do cartão deve ter 16 caracteres")
    private String cartaoCreditoNo;
    @Column(nullable = false)
    @Min(0)
    @Max(12)
    private int validCartaoCreditoMes;
    @Column(nullable = false)
    private int validCartaoCreditoAno;
    @Column(nullable = false, length = 3)
    @Min(001)
    @Max(999)
    private int codCartaoCredito;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Reserva> reservas = new ArrayList<>();

    public Long getClienteID() {
        return clienteID;
    }

    public TipoCartaoCreditoEnum getTipoCartaoCredito() {
        return tipoCartaoCredito;
    }

    public void setTipoCartaoCredito(TipoCartaoCreditoEnum tipoCartaoCredito) {
        this.tipoCartaoCredito = tipoCartaoCredito;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    public String getNome() {
        return nome;
    }

    public int getCodCartaoCredito() {
        return codCartaoCredito;
    }

    public void setCodCartaoCredito(int codCartaoCredito) {
        this.codCartaoCredito = codCartaoCredito;
    }

    public int getValidCartaoCreditoAno() {
        return validCartaoCreditoAno;
    }

    public void setValidCartaoCreditoAno(int validCartaoCreditoAno) {
        this.validCartaoCreditoAno = validCartaoCreditoAno;
    }

    public int getValidCartaoCreditoMes() {
        return validCartaoCreditoMes;
    }

    public void setValidCartaoCreditoMes(int validCartaoCreditoMes) {
        this.validCartaoCreditoMes = validCartaoCreditoMes;
    }

    public String getCartaoCreditoNo() {
        return cartaoCreditoNo;
    }

    public void setCartaoCreditoNo(String cartaoCreditoNo) {
        this.cartaoCreditoNo = cartaoCreditoNo;
    }

    public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }




    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.clienteID);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.clienteID, other.clienteID)) {
            return false;
        }
        return true;
    }

    
    // CONSTRUTOR
    public Cliente() {
    }


}
