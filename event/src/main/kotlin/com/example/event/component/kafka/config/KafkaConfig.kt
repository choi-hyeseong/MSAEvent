package com.example.event.component.kafka.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfig(
    @Value("\${kafka.ip}")
    val kafkaAddress: String
) {

    @Bean
    fun provideProducerFactory(): ProducerFactory<String, String> {
        // broker 연결. key와 value를 문자열로 serialize 함
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaAddress,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun provideConsumerFactory(): ConsumerFactory<String, String> {
        // broker 연결. key와 value를 문자열로 serialize 함
        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaAddress,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ConsumerConfig.GROUP_ID_CONFIG to "occupy_group_1"
        )
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun provideKafkaTemplate(producerFactory: ProducerFactory<String, String>) : KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory)
    }

    //concurrent listener라는데 동시성 제어 해주나~?
    @Bean
    fun provideKafkaListenerFactory(consumerFactory: ConsumerFactory<String, String>) : ConcurrentKafkaListenerContainerFactory<String,String> {
        return ConcurrentKafkaListenerContainerFactory<String, String>().apply { setConsumerFactory(consumerFactory) }
    }
}