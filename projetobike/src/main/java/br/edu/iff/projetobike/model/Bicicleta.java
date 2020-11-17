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
import javax.persistence.ManyToMany;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Bicicleta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bicicletaID;
    @Column(nullable = false)
    @Positive
    private float preco;
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TipoBicicletaEnum tipo;

    @JsonIgnore
    @ManyToMany(mappedBy = "bicicletas")
    private List<Reserva> reservas = new ArrayList<>();

    // GETS E SETS
    public Long getBicicletaID() {
        return bicicletaID;
    }

    public TipoBicicletaEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoBicicletaEnum tipo) {
        this.tipo = tipo;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void setBicicletaID(Long bicicletaID) {
        this.bicicletaID = bicicletaID;
    }



    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }



    @Override
    public int hashCode() {
        int hash = 6;
        hash = 69 * hash + Objects.hashCode(this.bicicletaID);
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
        final Bicicleta other = (Bicicleta) obj;
        if (!Objects.equals(this.bicicletaID, other.bicicletaID)) {
            return false;
        }
        return true;
    }



    // CONSTRUTOR
    public Bicicleta() {
    }
    
}
