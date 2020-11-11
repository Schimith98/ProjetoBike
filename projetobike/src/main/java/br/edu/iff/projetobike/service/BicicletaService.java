package br.edu.iff.projetobike.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.iff.projetobike.model.Bicicleta;
import br.edu.iff.projetobike.model.Reserva;
import br.edu.iff.projetobike.repository.BicicletaRepository;


@Service
public class BicicletaService {
    @Autowired
    private BicicletaRepository repo;

    public List<Bicicleta> findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }

    public List<Bicicleta> findAll(){
        return repo.findAll();
    }

    public Bicicleta findById(long id){
        Optional<Bicicleta> result = repo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException("Bicicleta não encontrada.");
        }
        return result.get();
    }

    public Bicicleta save(Bicicleta b){
        try{
            return repo.save(b);
        }catch(Exception e){
            throw new RuntimeException("Falha ao salvar a bicicleta");
        }
    }
    
    public void delete(Long id){
        Bicicleta obj = findById(id);
        verificaExclusaoBicicletasComReservas(obj.getReservas());
        try{
            repo.delete(obj);
        }catch(Exception e){
            throw new RuntimeException("Falha ao deletar a bicicleta.");
        }
    }

    public Bicicleta update(Bicicleta b){
        //Bicicleta já existe
        Bicicleta obj = findById(b.getBicicletaID());
        try {
            b.setPreco(obj.getPreco());
            b.setTipo(obj.getTipo());
            return repo.save(b);
        } catch (Exception e) {
            throw new RunTimeException("Falha ao atualizar o cliente.");
        }
    }

    private void verificaExclusaoBicicletasComReservas(List<Reserva> reservas){
        for(Reserva r : reservas){
            if(!r.getBicicletas().isEmpty()){
                throw new RuntimeException("Não é possível excluir bicicletas com reservas.");
            }
        }
    }  
    
}
