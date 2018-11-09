package com.stocos.produto;

import com.stocos.servico.DefaultServicoImpl;

public class ProdutoService extends DefaultServicoImpl<Produto> {

	public ProdutoService() {
		super(new ProdutoDao());
	}

}
