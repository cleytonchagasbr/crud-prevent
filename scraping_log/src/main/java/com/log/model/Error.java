package com.log.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(description = "Model Error - Utilizado para criar json com a mensagem de erro da exception.")
public class Error implements Serializable {

	private static final long serialVersionUID = 4842186272678069287L;

	private String message;

}
