package net.bonnerlima.hexagonal.adapters.out.persistence;

import net.bonnerlima.hexagonal.core.domain.model.MyEntity;
import net.bonnerlima.hexagonal.core.ports.out.MyRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRepositoryImpl implements MyRepositoryPort {
    private final MyEntityJpaRepository myEntityJpaRepository;

    @Autowired
    public MyRepositoryImpl(MyEntityJpaRepository myEntityJpaRepository) {
        this.myEntityJpaRepository = myEntityJpaRepository;
    }

    @Override
    public MyEntity findById(Long id) {
        return myEntityJpaRepository.findById(id).orElse(null);
    }
}
