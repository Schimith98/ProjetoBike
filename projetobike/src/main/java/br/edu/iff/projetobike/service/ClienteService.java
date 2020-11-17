package br.edu.iff.projetobike.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.iff.projetobike.model.Cliente;
import br.edu.iff.projetobike.repository.ClienteRepository;
import br.edu.iff.projetobike.exception.NotFoundException;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;

    public List<Cliente> findAll(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente findById(Long id){
        Optional<Cliente> result = repo.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado.");
        }
        return result.get();
    }

    public Cliente save(Cliente c) {
        //Verifica se cpf e email já são cadastrados
        verificaCpfEmailCadastrado(c.getCpf(), c.getEmail());
        try {
            return repo.save(c);
        } catch (Exception e) {
            throw new NotFoundException("Falha ao salvar o cliente.");
        }
    }

    public Cliente update(Cliente c){
        //Cliente já existe
        Cliente obj = findById(c.getClienteID());
        try {
            c.setCpf(obj.getCpf());
            c.setCartaoCreditoNo(obj.getCartaoCreditoNo());
            c.setTipoCartaoCredito(obj.getTipoCartaoCredito());
            c.setCodCartaoCredito(obj.getCodCartaoCredito());
            c.setValidCartaoCreditoMes(obj.getValidCartaoCreditoMes());
            c.setValidCartaoCreditoAno(obj.getValidCartaoCreditoAno());
            return repo.save(c);
        } catch (Exception e) {
            throw new NotFoundException("Falha ao atualizar o cliente.");
        }
    }

    public void delete(Long id){
        Cliente obj = findById(id);
        //verificar se há reservas
        verificaExclusaoClienteComReservas(obj);
        try {
            repo.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao excluir o cliente.");
        }
    }

    private void verificaCpfEmailCadastrado(String cpf, String email) {
        List<Cliente> result = repo.findByCpfOrEmail(cpf, email);
        if (!result.isEmpty()) {
            throw new RuntimeException("CPF ou EMAIL já cadastrados.");
        }
    }

    private void verificaExclusaoClienteComReservas(Cliente c) {
        if (!c.getReservas().isEmpty()) {
            throw new RuntimeException("Cliente possui reservas. Não pode ser excluído.");
        }
    }


    
}