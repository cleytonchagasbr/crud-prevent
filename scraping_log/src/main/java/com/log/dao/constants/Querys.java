package com.log.dao.constants;

public final class Querys {

	public static final String INSERT_LOG_IN_BATCH = "INSERT INTO log (data_log, ip, request, status, user_agent) VALUES (?, ?, ?, ?, ?)";

	public static final String INSERT_LOG = "INSERT INTO log (data_log, ip, request, status, user_agent) VALUES (to_timestamp(?, 'YYYY/MM/dd HS:MI:SS:MS'), ?, ?, ?, ?)";

	public static final String SELECT_ALL = "SELECT * FROM log";

	public static final String SELECT_BY_IP = "SELECT * FROM log WHERE ip = ";

	public static final String DELETE_BY_ID = "DELETE from log where id_log = ";

}
