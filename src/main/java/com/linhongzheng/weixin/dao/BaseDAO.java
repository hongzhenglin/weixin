package com.linhongzheng.weixin.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linhongzheng.weixin.utils.ConfigUtil;

public class BaseDAO<T> {

	private static final String JDBC_CONFIG_FILE = "jdbc.properties";
	private Class clazz;
	private String url;
	private String user;
	private String password;
	private ConfigUtil configUtil;

	public BaseDAO() {

		configUtil = new ConfigUtil(JDBC_CONFIG_FILE);
		url = configUtil.getValue("jdbc.url");
		user = configUtil.getValue("jdbc.username");
		password = configUtil.getValue("jdbc.password");
		String dirverClassName = configUtil.getValue("jdbc.driverClassName");
		DbUtils.loadDriver(dirverClassName);
	}

	public Connection createConnection() {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public Integer queryByCount(String sql, Object... params) {
		ResultSetHandler<Integer> rsh = new ScalarHandler<Integer>();

		Connection conn = createConnection();
		QueryRunner qr = new QueryRunner();

		int count = 0;
		try {
			count = (Integer) qr.query(conn, sql, rsh, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			DbUtils.closeQuietly(conn);
		}
		return count;
	}

	public List<T> query(String sql, Object... params) {
		clazz = (Class) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		Connection conn = createConnection();
		QueryRunner qr = new QueryRunner();
		BeanListHandler blh = new BeanListHandler(clazz);
		List<T> list = null;
		try {
			list = (List<T>) qr.query(conn, sql, blh, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			DbUtils.closeQuietly(conn);
		}
		return list;

	}

	public void update(String sql, Object... params) {
		QueryRunner qr = new QueryRunner();
		Connection conn = createConnection();
		try {
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			DbUtils.closeQuietly(conn);
		}
	}
}
