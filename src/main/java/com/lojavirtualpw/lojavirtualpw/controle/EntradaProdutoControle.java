package com.lojavirtualpw.lojavirtualpw.controle;

import com.lojavirtualpw.lojavirtualpw.modelos.EntradaItens;
import com.lojavirtualpw.lojavirtualpw.modelos.EntradaProduto;
import com.lojavirtualpw.lojavirtualpw.modelos.Estado;
import com.lojavirtualpw.lojavirtualpw.modelos.Produto;
import com.lojavirtualpw.lojavirtualpw.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaProdutoControle {

    private List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();

    @Autowired
    private EntradaProdutoRepositorio entradaProdutoRepositorio;

    @Autowired
    private EntradaItensRepositorio entradaItensRepositorio;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @GetMapping("/administrativo/entrada/cadastrar")
    public ModelAndView cadastrar(EntradaProduto entrada,
                                  EntradaItens entradaItens) {
        ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
        mv.addObject("entrada", entrada);
        mv.addObject("listaEntradaItens", this.listaEntrada);
        mv.addObject("entradaItens", entradaItens);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

//    @GetMapping("/administrativo/estados/listar")
//    public ModelAndView listar() {
//        ModelAndView mv = new ModelAndView("administrativo/estados/lista");
//        mv.addObject("listaEstados", estadoRepositorio.findAll());
//        return mv;
//    }

//    @GetMapping("/administrativo/estados/editar/{id}")
//    public ModelAndView editar(@PathVariable("id") Long id) {
//        Optional<Estado> funcionario = estadoRepositorio.findById(id);
//        return cadastrar(funcionario.get());
//
//    }

//    @GetMapping("/administrativo/estados/remover/{id}")
//    public ModelAndView remover(@PathVariable("id") Long id) {
//        Optional<Estado> estado = estadoRepositorio.findById(id);
//        estadoRepositorio.delete(estado.get());
//        return listar();
//
//    }

    @PostMapping("/administrativo/entrada/salvar")
    public ModelAndView salvar(String acao, EntradaProduto entrada,
                               EntradaItens entradaItens ) {
        if (acao.equals("itens")) {
            this.listaEntrada.add(entradaItens);
        } else if (acao.equals("salvar")) {
            entradaProdutoRepositorio.saveAndFlush(entrada);

            for (EntradaItens item : listaEntrada) {
                item.setEntrada(entrada);
                entradaItensRepositorio.saveAndFlush(item);

                Optional<Produto> prod = produtoRepositorio.findById(item.getProduto().getId());
                Produto produto = prod.get();

                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
                produto.setValorVenda(item.getValorVenda());
                produtoRepositorio.saveAndFlush(produto);

                this.listaEntrada = new ArrayList<>();

            }

            return cadastrar(new EntradaProduto(), new EntradaItens());
        }

        System.out.println(this.listaEntrada.size());

        return cadastrar(entrada, new EntradaItens());
    }
}
