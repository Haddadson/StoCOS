package com.stocos.lote;

import com.stocos.servico.DefaultServicoImpl;

public class LoteService extends DefaultServicoImpl<Lote> {

	public LoteService() {
		super(new LoteDao());
	}

}
