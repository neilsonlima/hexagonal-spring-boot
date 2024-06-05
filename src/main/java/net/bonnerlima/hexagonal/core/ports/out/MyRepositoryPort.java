package net.bonnerlima.hexagonal.core.ports.out;

import net.bonnerlima.hexagonal.core.domain.model.MyEntity;

public interface MyRepositoryPort {
    MyEntity findById(Long id);
}
