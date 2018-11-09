package com.stocos.redecosmeticos;

import com.stocos.servico.DefaultServicoImpl;

public class RedeCosmeticosService extends DefaultServicoImpl<RedeCosmeticos> {

	public RedeCosmeticosService() {
		super(new RedeCosmeticosDao());
	}

}
