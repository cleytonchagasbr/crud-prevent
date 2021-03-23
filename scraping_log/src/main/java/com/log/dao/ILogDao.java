package com.log.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.log.model.Log;
import com.log.model.PathFile;
import com.log.model.SearchByDate;

public interface ILogDao {
	
	ResponseEntity<String> insertLog(Log log);
	
	ResponseEntity<String> insertLogInBatch(List<Log> listLog);
	
	ResponseEntity<String> insertFileLog(PathFile pathFile) throws IOException;
	
	ResponseEntity<?> deleteLog(String index);
	
	ResponseEntity<List<Log>> getAllLogsByIp(String querySelect, String param);
	
	ResponseEntity<?> findLogByDate(SearchByDate dao);
	

}
