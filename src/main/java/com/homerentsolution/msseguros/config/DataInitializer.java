package com.homerentsolution.msseguros.config;

import com.homerentsolution.msseguros.model.Seguro;

import com.homerentsolution.msseguros.repository.SeguroRepository;

import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DataInitializer {

    private static final Logger log =
            LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(
            SeguroRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                Seguro seguro1 =
                        new Seguro();

                seguro1.setTipo(
                        "basico"
                );

                seguro1.setCobertura(
                        500000.0
                );

                seguro1.setIdReserva(
                        1L
                );

                repository.save(seguro1);

                Seguro seguro2 =
                        new Seguro();

                seguro2.setTipo(
                        "premium"
                );

                seguro2.setCobertura(
                        1500000.0
                );

                seguro2.setIdReserva(
                        2L
                );

                repository.save(seguro2);

                log.info("Datos iniciales de seguros cargados");
            }
        };
    }
}
