package com.example.pigonair.global.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private int port;

    /**
     * 결제 요청을 보낼 Direct Exchange
     */
    @Bean
    DirectExchange paymentExchange() {
        return new DirectExchange("payment.exchange");
    }

    /**
     * 결제 요청을 받을 큐
     */
    @Bean
    Queue paymentQueue() {
        return new Queue("payment.queue", false);
    }

    /**
     * 결제 요청 큐와 Exchange를 바인딩
     */
    @Bean
    Binding paymentBinding(DirectExchange paymentExchange, Queue paymentQueue) {
        return BindingBuilder.bind(paymentQueue).to(paymentExchange).with("payment.key");
    }

    /**
     * RabbitMQ와의 연결을 위한 ConnectionFactory을 구성합니다.
     */
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * 메시지를 전송하고 수신하기 위한 JSON 타입으로 메시지를 변경합니다.
     */
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 구성한 ConnectionFactory, MessageConverter를 통해 템플릿을 구성합니다.
     */
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // @Bean
    // SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
    //     SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    //     factory.setConnectionFactory(connectionFactory);
    //     factory.setConcurrentConsumers(10); // 병렬 소비자 수
    //     factory.setMaxConcurrentConsumers(10); // 최대 병렬 소비자 수
    //     factory.setPrefetchCount(10); // prefetch count 설정
    //     return factory;
    // }
}
