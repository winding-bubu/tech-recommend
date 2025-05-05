package com.tech.recommend.common.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

/**
 * es初始化配置
 *
 * @author winding bubu
 * @since 2025/05/05
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private int port;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // 配置 SSL 上下文（开发环境可跳过证书验证）
        SSLContext sslContext = createSSLContext();
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(username, password)
        );

        // 构建 RestClient
        RestClient restClient = RestClient.builder(new HttpHost(host, port, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder
                            .setSSLContext(sslContext)
                            .setDefaultCredentialsProvider(credentialsProvider)
                            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE); // 禁用主机名验证（仅开发环境）
                    return httpClientBuilder;
                })
                .build();

        // 创建 Transport 和 Client
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    private SSLContext createSSLContext() {
        try {
            return SSLContextBuilder.create().loadTrustMaterial(null, (chain, authType) -> true).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SSL context", e);
        }
    }
}
