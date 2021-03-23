package com.log.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.log.dao.constants.Querys;
import com.log.dao.impl.LogDaoImpl;
import com.log.model.Log;
import com.log.model.PathFile;
import com.log.model.SearchByDate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Chamadas para carregamento e consultas de Log")
@RestController
public class LogController {

	@Autowired
	private LogDaoImpl serviceImpl;

	@ApiOperation(value = "Cria um log no banco de dados.")
	@PostMapping(value = "/insertLog")
	public ResponseEntity<String> insertLog(@RequestBody Log log) {
		return serviceImpl.insertLog(log);
	}

	@ApiOperation(value = "Cria um ou varios logs dependendo da quantidade da lista informada.")
	@PostMapping(value = "/insertLogsInBatch")
	public ResponseEntity<String> insertLogsInBatch(@RequestBody List<Log> listLogs) {
		return serviceImpl.insertLogInBatch(listLogs);
	}

	@ApiOperation(value = "Através do caminho de um arquivo .log faz o carregamento dos dados pela chamada rest.")
	@PostMapping(value = "/insertFileLog")
	public ResponseEntity<?> insertFileLog(@RequestBody PathFile pathFile) throws IOException {
		return serviceImpl.insertFileLog(pathFile);
	}

	@ApiOperation(value = "Deleta um log pelo id/index informado.")
	@DeleteMapping(value = "/deleteLog")
	public ResponseEntity<?> deleteLog(@RequestParam String index) {
		return serviceImpl.deleteLog(index);
	}

	@ApiOperation(value = "Busca todos os logs pelo ip informado.")
	@GetMapping(value = "/getAllLogsByIp")
	public ResponseEntity<List<Log>> findAllLogs(@RequestParam String ip) {
		return serviceImpl.getAllLogsByIp(Querys.SELECT_BY_IP, ip);
	}

	@ApiOperation(value = "Busca todos os logs entre data inicial e data final informadas.")
	@PostMapping(value = "/searchByDate")
	public ResponseEntity<?> findLogByDate(@RequestBody SearchByDate dao) {
		return serviceImpl.findLogByDate(dao);
	}

}
