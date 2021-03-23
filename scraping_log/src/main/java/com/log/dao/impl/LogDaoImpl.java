package com.log.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.log.dao.ILogDao;
import com.log.dao.constants.Messagens;
import com.log.dao.constants.Querys;
import com.log.model.Error;
import com.log.model.Log;
import com.log.model.PathFile;
import com.log.model.SearchByDate;

/**
 * Test class.
 * 
 * @author Cleyton Chagas
 * @since 22/03/2021
 *
 */

@Repository
public class LogDaoImpl extends JdbcDaoSupport implements ILogDao {

	private static final Logger logger = LoggerFactory.getLogger(LogDaoImpl.class);

	@Autowired
	DataSource dataSource;

	private BufferedReader arquivoBuffer;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public ResponseEntity<String> insertLog(Log log) {

		try {

			String sql = Querys.INSERT_LOG;
			int retorno = getJdbcTemplate().update(sql, new Object[] { log.getData_log(), log.getIp(), log.getRequest(),
					log.getStatus(), log.getUser_agent() });
			logger.info(Messagens.MSG_SUCCESS_INSERT, retorno);

			return new ResponseEntity<String>(Messagens.MSG_SUCCESS_INSERT, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<String>(Messagens.MSG_ERROR_INSERT + e.getMessage() + e.getCause(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info(Messagens.MSG_FINALLY_INSERT);
		}
	}

	@Override
	public ResponseEntity<String> insertLogInBatch(List<Log> listLog) {

		try {

			String query = Querys.INSERT_LOG_IN_BATCH;
			List<Log> listAllLog = listLog;

			getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i) throws SQLException {

					Log log = listAllLog.get(i);
					Timestamp tt = Timestamp.valueOf(log.getData_log());
					ps.setTimestamp(1, tt);
					ps.setString(2, log.getIp());
					ps.setString(3, log.getRequest());
					ps.setString(4, log.getStatus());
					ps.setString(5, log.getUser_agent());
				}

				@Override
				public int getBatchSize() {
					return listLog.size();
				}
			});

			logger.info(Messagens.MSG_SUCCESS_INSERT_BATCH);
			return new ResponseEntity<String>(Messagens.MSG_SUCCESS_INSERT_BATCH, HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error(Messagens.MSG_ERROR_INSERT_BATCH + e.getMessage(), e.getCause());
			return new ResponseEntity<String>(Messagens.MSG_ERROR_INSERT_BATCH + e.getMessage() + e.getCause(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info(Messagens.MSG_FINALLY_INSERT_BATCH);
		}
	}

	@Override
	public ResponseEntity<String> insertFileLog(PathFile pathFile) throws IOException {
		try {

			File file = new File(pathFile.getPathFile());
			String[] value = null;
			List<String> listLogBuildingParameters = new ArrayList<String>();
			List<Log> listLogs = new ArrayList<Log>();

			FileReader arquivo = new FileReader(file);
			arquivoBuffer = new BufferedReader(arquivo);

			String line = arquivoBuffer.readLine();

			while (line != null) {
				value = line.split("\\|");

				value[2] = value[2].replace("\"", "");
				value[3] = value[3].replace("\"", "");
				value[4] = value[4].replace("\"", "");

				listLogBuildingParameters.add(value[0]);
				listLogBuildingParameters.add(value[1]);
				listLogBuildingParameters.add(value[2]);
				listLogBuildingParameters.add(value[3]);
				listLogBuildingParameters.add(value[4]);

				Log log = returnLogWithData(listLogBuildingParameters);
				listLogs.add(log);
				line = arquivoBuffer.readLine();
			}

			insertLogInBatch(listLogs);

			logger.info(Messagens.MSG_SUCCESS_INSERT_FILE);
			return new ResponseEntity<String>(Messagens.MSG_SUCCESS_INSERT_FILE, HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error(Messagens.MSG_ERRO_INSERT_FILE);
			return new ResponseEntity<String>(Messagens.MSG_ERRO_INSERT_FILE + e.getMessage() + e.getCause(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info(Messagens.MSG_FINALLY_INSERT_FILE);
		}

	}

	@Override
	public ResponseEntity<List<Log>> getAllLogsByIp(String querySelectAll, String param) {

		String query = querySelectAll.concat("\'" + param + "\'");

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
		List<Log> listResponse = new ArrayList<Log>();

		for (Map<?, ?> row : rows) {
			Log log = new Log(row.get("data_log").toString(), row.get("ip").toString(), row.get("request").toString(),
					row.get("status").toString(), row.get("user_agent").toString());

			listResponse.add(log);
		}

		return new ResponseEntity<List<Log>>(listResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteLog(String index) {
		try {
			String query = Querys.DELETE_BY_ID.concat("\'" + index + "\'");
			getJdbcTemplate().batchUpdate(query);

			logger.info(Messagens.MSG_SUCCESS_DELETE_LOG + index);
			return new ResponseEntity<String>(Messagens.MSG_SUCCESS_DELETE_LOG + index, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(Messagens.MSG_ERRO_DELETE_LOG + index, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			logger.info(Messagens.MSG_FINALLY_DELETE_LOG);
		}

	}

	@Override
	public ResponseEntity<?> findLogByDate(SearchByDate dto) {

		List<Log> listResponse = new ArrayList<Log>();

		try {

			if (dto.getDataInicial().isEmpty()) {
				return new ResponseEntity<String>(Messagens.MSG_NOT_SATISFIABLE_DATA_INICIAL,
						HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
			} else if (dto.getDataFinal().isEmpty()) {
				return new ResponseEntity<String>(Messagens.MSG_NOT_SATISFIABLE_DATA_FINAL,
						HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
			}

			String teste = "select * from log where data_log between to_timestamp('" + dto.getDataInicial()
					+ "', 'YYYY/MM/dd HS:MI:SS:MS') and to_timestamp('" + dto.getDataFinal()
					+ "', 'YYYY/MM/dd HS:MI:SS:MS')";

			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(teste);

			for (Map<?, ?> row : rows) {
				Log log = new Log(row.get("data_log").toString(), row.get("ip").toString(),
						row.get("request").toString(), row.get("status").toString(), row.get("user_agent").toString());
				listResponse.add(log);
			}

			return new ResponseEntity<List<Log>>(listResponse, HttpStatus.OK);
		} catch (Exception e) {
			Error error = new Error(e.getMessage());
			return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public Log returnLogWithData(List<String> values) {
		Log log = new Log(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4));
		return log;
	}

}
