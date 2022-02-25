package com.lojavirtualpw.lojavirtualpw.controle;

import com.lojavirtualpw.lojavirtualpw.modelos.Cidade;
import com.lojavirtualpw.lojavirtualpw.repositorios.CidadeRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.EstadoRepositorio;
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
public class NegadoControle {

    @GetMapping("/negadoAdministrativo")
    public ModelAndView negadoAdministrativo() {
        ModelAndView mv =  new ModelAndView("/negadoAdministrativo");

        return mv;
    }

    @GetMapping("/negadoCliente")
    public ModelAndView negadoCliente() {
        ModelAndView mv =  new ModelAndView("/negadoCliente");

        return mv;
    }
}
