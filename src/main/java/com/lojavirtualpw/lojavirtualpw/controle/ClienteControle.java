package com.lojavirtualpw.lojavirtualpw.controle;

import com.lojavirtualpw.lojavirtualpw.modelos.Cidade;
import com.lojavirtualpw.lojavirtualpw.modelos.Cliente;
import com.lojavirtualpw.lojavirtualpw.repositorios.CidadeRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.ClienteRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.EstadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ClienteControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private CidadeRepositorio cidadeRepositorio;

    @GetMapping("/cliente/cadastrar")
    public ModelAndView cadastrar(Cliente cliente) {
        ModelAndView mv = new ModelAndView("cliente/cadastrar");
        mv.addObject("cliente", cliente);
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

//    @GetMapping("/administrativo/cidades/listar")
//    public ModelAndView listar() {
//        ModelAndView mv = new ModelAndView("administrativo/cidades/lista");
//        mv.addObject("listaCidades", clienteRepositorio.findAll());
//        return mv;
//    }

    @GetMapping("/cliente/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        return cadastrar(cliente.get());

    }

//    @GetMapping("/administrativo/cidades/remover/{id}")
//    public ModelAndView remover(@PathVariable("id") Long id) {
//        Optional<Cidade> cidade = clienteRepositorio.findById(id);
//        clienteRepositorio.delete(cidade.get());
//        return listar();
//
//    }

    @PostMapping("/cliente/salvar")
    public ModelAndView salvar(@Valid Cliente cliente, BindingResult result) {

        if (result.hasErrors()) {
            return cadastrar(cliente);
        }

        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
        clienteRepositorio.saveAndFlush(cliente);

//        return cadastrar(new Estado());
        return cadastrar(new Cliente());
    }
}
