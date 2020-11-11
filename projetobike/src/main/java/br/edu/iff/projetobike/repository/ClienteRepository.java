package br.edu.iff.projetobike.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.iff.projetobike.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    @Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf OR c.email = :email")
    public List<Cliente> findByCpfOrEmail(@Param("cpf") String cpf, @Param("email") String email);

}
