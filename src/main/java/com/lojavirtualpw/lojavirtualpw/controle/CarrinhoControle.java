package com.lojavirtualpw.lojavirtualpw.controle;

import com.lojavirtualpw.lojavirtualpw.modelos.Cliente;
import com.lojavirtualpw.lojavirtualpw.modelos.Compra;
import com.lojavirtualpw.lojavirtualpw.modelos.ItensCompra;
import com.lojavirtualpw.lojavirtualpw.modelos.Produto;
import com.lojavirtualpw.lojavirtualpw.repositorios.ClienteRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.CompraRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.ItensCompraRepositorio;
import com.lojavirtualpw.lojavirtualpw.repositorios.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarrinhoControle {

    private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
    private Compra compra = new Compra();
    private Cliente cliente;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private CompraRepositorio compraRepositorio;

    @Autowired
    private ItensCompraRepositorio itensCompraRepositorio;

    private void calcularTotal() {
        compra.setValorTotal(0.0);
        for (ItensCompra it : itensCompra) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }
    }

    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho() {
        ModelAndView mv = new ModelAndView("cliente/carrinho");

        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompra);
        return mv;
    }

    private void buscarUsuarioLogado() {
        Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
        if (!(autenticado instanceof AnonymousAuthenticationToken)) {
            String email = autenticado.getName();
            cliente = clienteRepositorio.buscarClienteEmail(email).get(0);
        }
    }

    @PostMapping("/finalizar/confirmar")
    public ModelAndView confirmarCompra(String formaPagamento) {
        ModelAndView mv = new ModelAndView("cliente/mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(formaPagamento);
        compraRepositorio.saveAndFlush(compra);

        for (ItensCompra c : itensCompra) {
            c.setCompra(compra);
            itensCompraRepositorio.saveAndFlush(c);
        }
        itensCompra = new ArrayList<>();
        compra = new Compra();
        return mv;
    }


    @GetMapping("/finalizar")
    public ModelAndView finalizarCompra() {
        buscarUsuarioLogado();
        ModelAndView mv = new ModelAndView("cliente/finalizar");
        calcularTotal();
        // System.out.println(compra.getValorTotal());
        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompra);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {

        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(id)) {

                if (acao.equals(1)) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(0.0);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                } else if (acao == 0 && it.getQuantidade() >= 1) {
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(0.0);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                }

                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/removerProduto/{id}")
    public String removerProdutoCarrinho(@PathVariable Long id) {
        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(id)) {
                itensCompra.remove(it);
                break;
            }
        }

        return "redirect:/carrinho";
    }


    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id) {

        Optional<Produto> prod = produtoRepositorio.findById(id);
        Produto produto = prod.get();

        int controle = 0;
        for (ItensCompra it : itensCompra) {
            if (it.getProduto().getId().equals(produto.getId())) {
                it.setQuantidade(it.getQuantidade() + 1);
                it.setValorTotal(0.0);
                it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                controle = 1;
                break;
            }
        }

        if (controle == 0) {
            ItensCompra item = new ItensCompra();
            item.setProduto(produto);
            item.setValorUnitario(produto.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));
            itensCompra.add(item);
        }

        return "redirect:/carrinho";
    }
}
