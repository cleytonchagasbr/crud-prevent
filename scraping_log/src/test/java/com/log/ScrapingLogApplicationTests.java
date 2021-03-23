package com.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import com.log.dao.constants.Messagens;
import com.log.dao.constants.Querys;
import com.log.dao.impl.LogDaoImpl;
import com.log.model.Error;
import com.log.model.Log;
import com.log.model.PathFile;
import com.log.model.SearchByDate;

@SpringBootTest
class ScrapingLogApplicationTests {

	List<String> mockCamposDoLog = Arrays.asList("2020-01-01 00:00:01", "127.0.0.1", "GET / HTTP/1.1", "200", "");

	@Autowired
	DataSource dataSource;

	@Autowired
	LogDaoImpl logDaoImpl;

	@Autowired
	JdbcTemplate JdbcTemplate;

	@Test
	void testaConexaoComBancoDeDadosSucesso() throws SQLException {
		Connection connection = dataSource.getConnection();
		assertNotNull(connection);
	}

	@Test
	void testaFindLogByDateComSucesso() {
		logDaoImpl.insertLog(logDaoImpl.returnLogWithData(mockCamposDoLog));

		SearchByDate search = new SearchByDate();
		search.setDataInicial("2020-01-01 00:00:01");
		search.setDataFinal("2020-01-01 00:01:00");
		assertEquals(HttpStatus.OK, logDaoImpl.findLogByDate(search).getStatusCode());
	}

	@Test
	void testaInsertFileLogComSucesso() throws IOException {
		PathFile path = new PathFile();
		path.setPathFile("C:/access.log");
		assertEquals(HttpStatus.CREATED, logDaoImpl.insertFileLog(path).getStatusCode());
	}

	@Test
	void testaInsertUmLogComSuccesso() {
		assertEquals(HttpStatus.CREATED,
				logDaoImpl.insertLog(logDaoImpl.returnLogWithData(mockCamposDoLog)).getStatusCode());
		assertEquals(Messagens.MSG_SUCCESS_INSERT,
				logDaoImpl.insertLog(logDaoImpl.returnLogWithData(mockCamposDoLog)).getBody());
	}

	@Test
	void testaInsertLogEmLoteComSucesso() {
		List<Log> listMockLogs = new ArrayList<Log>();
		Log mockLog = logDaoImpl.returnLogWithData(mockCamposDoLog);
		listMockLogs.add(mockLog);
		listMockLogs.add(mockLog);
		assertEquals(HttpStatus.CREATED, logDaoImpl.insertLogInBatch(listMockLogs).getStatusCode());
	}

	@Test
	void testaGetAllLogsByIpComSucesso() {
		logDaoImpl.insertLog(logDaoImpl.returnLogWithData(mockCamposDoLog));
		assertEquals(HttpStatus.OK, logDaoImpl.getAllLogsByIp(Querys.SELECT_BY_IP, "127.0.0.1").getStatusCode());
	}

	@Test
	void testaBuildLogModel() {
		List<String> camposDoLog = Arrays.asList("2020-01-01 00:00:01", "127.0.0.1", "GET / HTTP/1.1", "200", "");
		assertNotEquals(Log.class, logDaoImpl.returnLogWithData(camposDoLog));
		logDaoImpl.returnLogWithData(camposDoLog);
	}

	@Test
	void testaDeleteLogComSucesso() {
		assertEquals(HttpStatus.OK, logDaoImpl.deleteLog("1").getStatusCode());
	}

	@Test
	void buildError() {
		Error erro = new Error("Mensagem de erro aqui.");
		assertNotEquals(Error.class, erro);
	}

}
