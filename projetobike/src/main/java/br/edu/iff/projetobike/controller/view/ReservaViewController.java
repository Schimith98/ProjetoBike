package br.edu.iff.projetobike.controller.view;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.projetobike.model.Reserva;
import br.edu.iff.projetobike.service.BicicletaService;
import br.edu.iff.projetobike.service.ClienteService;
import br.edu.iff.projetobike.service.ReservaService;

@Controller
@RequestMapping(path = "/bicicletas/{idBicicletas}/reservas")
public class ReservaViewController {

    @Autowired
    private ReservaService service;
    @Autowired
    private BicicletaService bicicletaService;
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String getAll(@PathVariable("idBicicletas") Long idBicicletas, Model model) {
        model.addAttribute("reservas", service.findAll(idBicicletas));
        model.addAttribute("idBicicletas", idBicicletas);
        return "reservas";
    }

    @GetMapping(path = "/reserva")
    public String cadastro(@PathVariable("idBicicletas") Long idBicicletas, Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("idBicicletas", idBicicletas);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("bicicletas", bicicletaService.findAll());
        return "formReserva";
    }

    @PostMapping(path = "/reserva")
    public String save(@PathVariable("idBicicletas") Long idBicicletas,
            @Valid @ModelAttribute Reserva reserva,
            BindingResult result, Model model) {
        //Valores a serem retornados no model
        model.addAttribute("idBicicletas", idBicicletas);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("bicicletas", bicicletaService.findAll());

        //Elimina erro de dataHora
        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataHora")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formReserva";
        }

        reserva.setReservaID(null);
        try {
            service.save(reserva);
            model.addAttribute("msgSucesso", "Reserva cadastrada com sucesso.");
            model.addAttribute("reserva", new Reserva());
            return "formReserva";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("reserva", e.getMessage()));
            return "formReserva";
        }
    }
    
    @GetMapping(path = "/reserva/{id}")
    public String alterar(@PathVariable("idBicicletas") Long idBicicletas, @PathVariable("id") Long id, Model model) {
        model.addAttribute("reserva", service.findById(id));
        model.addAttribute("idBicicletas", idBicicletas);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("bicicletas", bicicletaService.findAll());
        return "formReserva";
    }

    @PostMapping(path = "/reserva/{id}")
    public String update(@PathVariable("idBicicletas") Long idBicicletas,
            @PathVariable("id") Long id,
            @Valid @ModelAttribute Reserva reserva,
            BindingResult result, Model model) {
        //Valores a serem retornados no model
        model.addAttribute("idBicicletas", idBicicletas);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("bicicletas", bicicletaService.findAll());

        //Elimina erro de dataHora
        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("dataHora")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formReserva";
        }

        reserva.setReservaID(id);
        try {
            service.update(reserva);
            model.addAttribute("msgSucesso", "Reserva cadastrada com sucesso.");
            model.addAttribute("reserva", reserva);
            return "formReserva";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("reserva", e.getMessage()));
            return "formReserva";
        }
    }
    
    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("idBicicletas") Long idBicicletas, @PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/bicicletas/"+idBicicletas+"/reservas";
    }
}
