package io.akhil.bookyourmovie.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.akhil.bookyourmovie.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.City.class.getName() + ".theatres", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Theatre.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Theatre.class.getName() + ".screens", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Booking.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Booking.class.getName() + ".seats", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Screen.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Screen.class.getName() + ".seats", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Screen.class.getName() + ".shows", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Movie.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.SeatType.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Seat.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Seat.class.getName() + ".types", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Show.class.getName(), jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Screen.class.getName() + ".seatTypes", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.SeatType.class.getName() + ".seats", jcacheConfiguration);
            cm.createCache(io.akhil.bookyourmovie.domain.Row.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
