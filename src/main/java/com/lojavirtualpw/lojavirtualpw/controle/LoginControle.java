package com.lojavirtualpw.lojavirtualpw.controle;

import com.lojavirtualpw.lojavirtualpw.modelos.Cidade;
import com.lojavirtualpw.lojavirtualpw.repositorios.CidadeRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.EstadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginControle {

    @Autowired
    private CidadeRepositorio cidadeRepositorio;

    @Autowired
    private EstadoRepositorio estadoRepositorio;

    @GetMapping("/login")
    public ModelAndView cadastrar(Cidade cidade) {
        ModelAndView mv = new ModelAndView("/login");
        return mv;
    }
}
