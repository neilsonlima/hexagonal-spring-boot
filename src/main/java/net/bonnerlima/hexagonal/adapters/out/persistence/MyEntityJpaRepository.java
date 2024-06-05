package net.bonnerlima.hexagonal.adapters.out.persistence;


import net.bonnerlima.hexagonal.core.domain.model.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyEntityJpaRepository extends JpaRepository<MyEntity, Long> {
}
