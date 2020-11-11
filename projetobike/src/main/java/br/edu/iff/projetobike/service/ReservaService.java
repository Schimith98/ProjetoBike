package br.edu.iff.projetobike.service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.edu.iff.projetobike.model.Bicicleta;
import br.edu.iff.projetobike.model.Reserva;
import br.edu.iff.projetobike.repository.ReservaRepository;
import javassist.NotFoundException;

public class ReservaService {
    @Autowired
    private ReservaRepository repo;

    public List<Reserva> findAll() {
        return repo.findAll();
    }
    
    public List<Reserva> findAll(Long ClienteId) {
        return repo.findByClienteId(ClienteId, null);
    }

    public List<Reserva> findAll(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }

    public Reserva findById(Long id) {
        Optional<Reserva> obj = repo.findById(id);
        if (obj.isEmpty()) {
            throw new NotFoundException("Reserva não encontrada.");
        }
        return obj.get();
    }

    public Reserva save(Reserva r) {
        //Verificar se a data de inicio é anterior a data de fim
        verificarDataInicioETermino(r);
        //Verificar se as Bicicletas já estão reservadas.
        verificarBicicletasReservadas(r);

        try {
            r.setHoraInicio(Calendar.getInstance());
            return repo.save(r);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao salvar a Reserva.");
        }
    }

    public Reserva update(Reserva r) {
        //Verificar se já existe
        Reserva obj = findById(r.getReservaID());
        //Verificar se a data de inicio é anterior a data de fim
        verificarDataInicioETermino(r);
        //Verificar se bicicletas já estão reservadas.
        verificarBicicletasReservadas(r);
        try {
            r.setHoraInicio(Calendar.getInstance());
            return repo.save(r);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar a Reserva");
        }
    }

    public void delete(Long id) {
        Reserva obj = findById(id);
        try {
            repo.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar a Reserva.");
        }
    }

    private void verificarBicicletasReservadas(Reserva r) {
        List<Reserva> reservas = repo.findReservasEntreDatas(r.getHoraInicio(), r.getHoraFim());
        for (Reserva reserva : reservas) {
            for (Bicicleta b : reserva.getBicicletas()) {
                if ((r.getBicicletas().contains(b)) && (!Objects.equals(r, reserva))) {
                    throw new RuntimeException("A bicicleta já esta reservada para o período.");
                }
            }
        }
    }

    private void verificarDataInicioETermino(Reserva r) {
        if (r.getHoraInicio().compareTo(r.getHoraFim()) >= 0) {
            throw new RuntimeException("Data de início deve ser anterior a data de término.");
        }
    }
}
