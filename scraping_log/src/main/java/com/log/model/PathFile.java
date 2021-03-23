package com.log.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Model PathFile - Utilizado para especificar o caminho/path onde se encontra o arquivo.log.")
public class PathFile implements Serializable {

	private static final long serialVersionUID = 8489512806898005372L;

	private String pathFile;

}
