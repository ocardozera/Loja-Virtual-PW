package com.lojavirtualpw.lojavirtualpw.controle;

import com.lojavirtualpw.lojavirtualpw.modelos.Cidade;
import com.lojavirtualpw.lojavirtualpw.modelos.Permissao;
import com.lojavirtualpw.lojavirtualpw.repositorios.EstadoRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.FuncionarioRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.PapelRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.PermissaoRepositorio;
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
public class PermissaoControle {

    @Autowired
    private PermissaoRepositorio permissaoRepositorio;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private PapelRepositorio papelRepositorio;

    @GetMapping("/administrativo/permissoes/cadastrar")
    public ModelAndView cadastrar(Permissao permissao) {
        ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro");
        mv.addObject("permissao", permissao);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaPapeis", papelRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/permissoes/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/permissoes/lista");
        mv.addObject("listaPermissoes", permissaoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/permissoes/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Permissao> permissao = permissaoRepositorio.findById(id);
        return cadastrar(permissao.get());

    }

    @GetMapping("/administrativo/permissoes/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Permissao> permissao = permissaoRepositorio.findById(id);
        permissaoRepositorio.delete(permissao.get());
        return listar();

    }

    @PostMapping("/administrativo/permissoes/salvar")
    public ModelAndView salvar(@Valid Permissao permissao, BindingResult result) {

        if (result.hasErrors()) {
            return cadastrar(permissao);
        }

        permissaoRepositorio.saveAndFlush(permissao);

//        return cadastrar(new Estado());
        return listar();
    }
}
