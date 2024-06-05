package net.bonnerlima.hexagonal.core.services;

import net.bonnerlima.hexagonal.core.domain.model.MyEntity;
import net.bonnerlima.hexagonal.core.ports.in.MyServicePort;
import net.bonnerlima.hexagonal.core.ports.out.MyRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyServicePort {
    private final MyRepositoryPort myRepositoryPort;

    @Autowired
    public MyServiceImpl(MyRepositoryPort myRepositoryPort) {
        this.myRepositoryPort = myRepositoryPort;
    }

    @Override
    public void doSomething(Long id) {
        MyEntity entity = myRepositoryPort.findById(id);
    }
}
