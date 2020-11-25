package br.edu.iff.projetobike.controller.view;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.projetobike.model.Bicicleta;
import br.edu.iff.projetobike.model.TipoBicicletaEnum;
import br.edu.iff.projetobike.service.BicicletaService;

@Controller
@RequestMapping(path = "/bicicletas")
public class BicicletaViewController {
    @Autowired
    private BicicletaService service;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("hoteis", service.findAll());
        return "hoteis";
    }

    @GetMapping(path = "/bicicleta")
    public String cadastro(Model model) {
        model.addAttribute("bicicleta", new Bicicleta());
        model.addAttribute("tiposBicicleta", TipoBicicletaEnum.values());
        return "formBicicleta";
    }

    @PostMapping(path = "/bicicleta")
    public String save(@Valid @ModelAttribute Bicicleta bicicleta, BindingResult result, Model model) {
        //valores de retorno padão
        model.addAttribute("tiposQuarto", TipoBicicletaEnum.values());
        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formBicicleta";
        }
        bicicleta.setBicicletaID(null);
        try {
            service.save(bicicleta);
            model.addAttribute("msgSucesso", "Bicicleta cadastrada com sucesso.");
            model.addAttribute("hotel", new Bicicleta());
            return "formHotel";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Bicicleta", e.getMessage()));
            return "formBicicleta";
        }
    }

    @GetMapping(path = "/bicicleta/{id}")
    public String alterar(@PathVariable("id") Long id,Model model) {
        model.addAttribute("bicicleta", service.findById(id));
        model.addAttribute("tiposQuarto", TipoBicicletaEnum.values());
        return "formBicicleta";
    }

    @PostMapping(path = "/bicicleta/{id}")
    public String update(@Valid @ModelAttribute Bicicleta bicicleta, BindingResult result, @PathVariable("id") Long id, Model model) {
        //valores de retorno padão
        model.addAttribute("tiposQuarto", TipoBicicletaEnum.values());
        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formBicicleta";
        }
        bicicleta.setBicicletaID(id);
        try {
            service.update(bicicleta);
            model.addAttribute("msgSucesso", "Bicicleta atualizada com sucesso.");
            model.addAttribute("bicicleta", bicicleta);
            return "formBicicleta";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("Bicicleta", e.getMessage()));
            return "formBicicleta";
        }
    }

    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/bicicletas";
    }

}
