package br.edu.iff.projetobike.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import org.springframework.format.annotation.DateTimeFormat;

import br.edu.iff.projetobike.annotation.DateValidation;

@Entity
@DateValidation
public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservaID;
    @Column(nullable = false)
    @NotBlank(message = "Data de registro obrigatória")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private Calendar horaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private Calendar horaFim;
    @Min(0)
    private float valor;

    
    @ManyToOne
    @NotNull(message = "Cliente Obrigatório")
    private Cliente cliente;
    
    @ManyToMany
    @Size(min = 1, message = "Reserva deve ter no mínimo uma bicicleta")
    private List<Bicicleta> bicicletas = new ArrayList<>();

    public Long getReservaID() {
        return reservaID;
    }

    public List<Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public void setBicicletas(List<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setReservaID(Long reservaID) {
        this.reservaID = reservaID;
    }    

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Calendar getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Calendar horaFim) {
        this.horaFim = horaFim;
    }

    public Calendar getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Calendar horaInicio) {
        this.horaInicio = horaInicio;
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.reservaID);
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
        final Reserva other = (Reserva) obj;
        if (!Objects.equals(this.reservaID, other.reservaID)) {
            return false;
        }
        return true;
    }

    
}
