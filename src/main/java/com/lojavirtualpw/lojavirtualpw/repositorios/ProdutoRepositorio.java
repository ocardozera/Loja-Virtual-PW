package com.lojavirtualpw.lojavirtualpw.repositorios;

import com.lojavirtualpw.lojavirtualpw.modelos.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
}
