package br.edu.iff.projetobike.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.iff.projetobike.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    
    public List<Reserva> findByClienteId(Long clienteID, Pageable page);

    public List<Reserva> findByBicicletaId(Long bicicletaID, Pageable page);

    @Query("SELECT DISTINCT(r) FROM Reserva r WHERE (r.horaInicio BETWEEN :horaInicio AND :horaFim) OR (r.horaFim BETWEEN :horaInicio AND :horaFim)")
    public List<Reserva> findReservasEntreDatas(Calendar horaInicio, Calendar horaFim);
}
