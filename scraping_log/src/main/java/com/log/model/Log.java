package com.log.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Model Log - Utilizado para criações e consultas de logs.")
public class Log implements Serializable {

	private static final long serialVersionUID = -8568681290584675292L;

	public Log(String data, String ip, String request, String status, String description) {
		this.data_log = data;
		this.ip = ip;
		this.request = request;
		this.status = status;
		this.user_agent = description;
	}

	private String data_log;

	private String ip;

	private String request;

	private String status;

	private String user_agent;

}
