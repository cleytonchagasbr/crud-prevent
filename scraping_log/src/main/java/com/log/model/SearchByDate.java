package com.log.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Model Log - Utilizado para criações e consultas de logs.")
public class SearchByDate implements Serializable {

	private static final long serialVersionUID = -4526418167043459672L;

	private String dataInicial;

	private String dataFinal;

}
