package com.project.httpserver.pages;

import com.project.httpserver.models.Request;
import com.project.httpserver.models.Status;
import com.project.httpserver.server.HttpServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.Map;

public final class StatusPage {

    public static ByteBuf getContent(Status status) {
        StringBuilder htmlText = new StringBuilder();

        htmlText.append("<html><head><title>Status page</title>")
                .append("<style>table, td, th {border: 1px solid #000000;} table{margin: 5px;} td, th {padding: 3px;}</style>")
                .append("</head><body>")
                .append("Total requests: ").append(status.getTotalCount()).append("<br>")
                .append("Unique IP requests : ").append(status.getUniqueCount()).append("<br>")
                .append("Connections: ").append(HttpServerHandler.getConnectionsQuantity()).append("<br><br>");

        htmlText.append("<div style='float: left;'><table><thead><tr><th>IP</th><th>Count</th><th>Last request time</th></tr></thead>")
                .append("IP statistic:<br>");

        for(Map<String, String> ip : status.getIpRequests()){
            htmlText.append("<tr><td>").append(ip.get("src_ip")).append("</td><td>").append(ip.get("count")).append("</td><td>").append(ip.get("time")).append("</td></tr>");
        }

        htmlText.append("</table><br>")
                .append("Redirect statistic:<br>")
                .append("<table><thead><tr><th>Redirect url</th><th>Count</th></tr></thead>");

        for(Map<String, String> url : status.getUrlRedirects()){
            htmlText.append("<tr><td>").append(url.get("url_redirects")).append("</td><td>").append(url.get("count")).append("</td></tr>");
        }

        htmlText.append("</table></div>")
                .append("<div style='float: left;'>Last request statistic:<br>")
                .append("<table><thead><tr><th>src_ip</th><th>URI</th><th>timestamp</th>")
                .append("<th>sent_bytes</th><th>received_bytes</th><th>speed (bytes/sec)</th></tr></thead>");

        for(Request req : status.getLastRequests()) {
            htmlText.append("<tr>")
                    .append("<td>").append(req.getIp()).append("</td>")
                    .append("<td>").append(req.getUri()).append("</td>")
                    .append("<td>").append(req.getTimestamp()).append("</td>")
                    .append("<td>").append(req.getSentBytes()).append("</td>")
                    .append("<td>").append(req.getReceivedBytes()).append("</td>")
                    .append("<td>").append(req.getSpeed()).append("</td>")
                    .append("</tr>");
        }

        htmlText.append("</table></div></body></html>");

        return Unpooled.copiedBuffer(htmlText, CharsetUtil.UTF_8);
    }

}