package net.bonnerlima.hexagonal.adapters.in.web;

import net.bonnerlima.hexagonal.core.ports.in.MyServicePort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MyServicePort myServicePort;

    public MyController(MyServicePort myServicePort) {
        this.myServicePort = myServicePort;
    }

    @GetMapping("/entity/{id}")
    public void getEntity(@PathVariable Long id) {
        myServicePort.doSomething(id);
    }

}
