package com.lojavirtualpw.lojavirtualpw.controle;

import com.lojavirtualpw.lojavirtualpw.modelos.Estado;
import com.lojavirtualpw.lojavirtualpw.modelos.Papel;
import com.lojavirtualpw.lojavirtualpw.repositorios.EstadoRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.PapelRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PapelControle {

    @Autowired
    private PapelRepositorio papelRepositorio;

    @GetMapping("/administrativo/papeis/cadastrar")
    public ModelAndView cadastrar(Papel papel) {
        ModelAndView mv = new ModelAndView("administrativo/papeis/cadastro");
        mv.addObject("papel", papel);
        return mv;
    }

    @GetMapping("/administrativo/papeis/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/papeis/lista");
        mv.addObject("listaPapeis", papelRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/papeis/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Papel> papel = papelRepositorio.findById(id);
        return cadastrar(papel.get());

    }

    @GetMapping("/administrativo/papeis/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Papel> papel = papelRepositorio.findById(id);
        papelRepositorio.delete(papel.get());
        return listar();

    }

    @PostMapping("/administrativo/papeis/salvar")
    public ModelAndView salvar(@Valid Papel papel, BindingResult result) {

        if (result.hasErrors()) {
            return cadastrar(papel);
        }

        papelRepositorio.saveAndFlush(papel);

//        return cadastrar(new Estado());
        return listar();
    }
}
