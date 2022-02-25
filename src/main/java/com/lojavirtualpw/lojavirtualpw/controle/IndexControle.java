package com.lojavirtualpw.lojavirtualpw.controle;

import com.lojavirtualpw.lojavirtualpw.modelos.Cidade;
import com.lojavirtualpw.lojavirtualpw.repositorios.CidadeRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.EstadoRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexControle {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @GetMapping("/")
    public ModelAndView index () {
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }
}
