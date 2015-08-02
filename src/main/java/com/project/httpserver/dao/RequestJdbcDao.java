package com.project.httpserver.dao;

import com.project.httpserver.models.Request;
import com.project.httpserver.models.Status;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestJdbcDao {

    private static final String COUNT_REQUESTS =  "SELECT COUNT(id) as count_total, COUNT(DISTINCT(src_ip)) AS count_unique FROM requests";

    private static final String IP_REQUESTS =     "SELECT src_ip, COUNT(id) as count, MAX(time) AS time FROM requests GROUP BY src_ip";

    private static final String URL_REDIRECTS =   "SELECT url_redirects, COUNT(id) AS count FROM requests WHERE url_redirects != 'empty' GROUP BY url_redirects";

    private static final String LAST_REQUESTS =   "SELECT src_ip, uri, time_stamp, sent_bytes, received_bytes from requests ORDER BY time DESC LIMIT 16";

    private static final String INSERT_REQUEST =  "INSERT INTO requests(src_ip, uri, time, sent_bytes, received_bytes, time_stamp, url_redirects) VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static DataSource dataSource;

    public RequestJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Status getStatus() {
        Status status = new Status();

        Map<String, Integer> countMap = this.getCount();

        status.setTotalCount(countMap.get("totalCount"));
        status.setUniqueCount(countMap.get("uniqueCount"));
        status.setIpRequests(this.getIpRequests());
        status.setUrlRedirects(this.getUrlRedirects());
        status.setLastRequests(this.getLastRequests());

        return status;
    }

    public Map<String, Integer> getCount() {
        Map<String, Integer> result = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_REQUESTS)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result.put("totalCount", resultSet.getInt("count_total"));
                    result.put("uniqueCount", resultSet.getInt("count_unique"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Map<String, String>> getIpRequests(){
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(IP_REQUESTS)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, String> record = new HashMap<>();

                    record.put("src_ip", resultSet.getString("src_ip"));
                    record.put("count", resultSet.getString("count"));
                    record.put("time", resultSet.getString("time"));

                    result.add(record);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Map<String, String>> getUrlRedirects() {
        List<Map<String, String>> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(URL_REDIRECTS)) {

            try (ResultSet resultSet = statement.executeQuery(URL_REDIRECTS)) {
                while (resultSet.next()) {
                    Map<String, String> record = new HashMap<>();

                    record.put("url_redirects", resultSet.getString("url_redirects"));
                    record.put("count", resultSet.getString("count"));

                    result.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Request> getLastRequests() {
        List<Request> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(LAST_REQUESTS)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Request request = new Request();

                    request.setIp(resultSet.getString("src_ip"));
                    request.setUri(resultSet.getString("uri"));
                    request.setTimestamp(resultSet.getTimestamp("time_stamp"));
                    request.setReceivedBytes(resultSet.getInt("received_bytes"));
                    request.setSentBytes(resultSet.getInt("sent_bytes"));

                    result.add(request);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void insertRequest(Request request) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_REQUEST)) {

            statement.setObject(1, request.getIp());
            statement.setObject(2, request.getUri());
            statement.setObject(3, request.getTime());
            statement.setObject(4, request.getReceivedBytes());
            statement.setObject(5, request.getSentBytes());
            statement.setObject(6, request.getTimestamp());
            statement.setObject(7, request.getUrlRedirect());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
