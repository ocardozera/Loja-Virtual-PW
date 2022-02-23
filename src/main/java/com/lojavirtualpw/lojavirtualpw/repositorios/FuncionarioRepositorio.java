package com.lojavirtualpw.lojavirtualpw.repositorios;

import com.lojavirtualpw.lojavirtualpw.modelos.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {
}
