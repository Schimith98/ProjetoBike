package br.edu.iff.projetobike.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.projetobike.model.Bicicleta;
import br.edu.iff.projetobike.model.TipoBicicletaEnum;

@Repository
public interface BicicletaRepository extends JpaRepository<Bicicleta, Long>{
    
    public List<Bicicleta> findByTipo(TipoBicicletaEnum tipo);
}
