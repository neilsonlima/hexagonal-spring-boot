# Projeto Spring Boot com Arquitetura Hexagonal

Este projeto é um exemplo de aplicação Spring Boot que segue a arquitetura hexagonal, usando um banco de dados H2 para persistência. A arquitetura hexagonal, também conhecida como Ports and Adapters, ajuda a isolar o núcleo da aplicação de detalhes técnicos e frameworks específicos.

## Estrutura do Projeto

A estrutura do projeto é organizada da seguinte maneira:

```
src/main/java/com/example/hexagonal
├── HexagonalApplication.java
├── core
│   ├── domain
│   │   └── model
│   │       └── MyEntity.java
│   ├── ports
│   │   ├── in
│   │   │   └── MyServicePort.java
│   │   └── out
│   │       └── MyRepositoryPort.java
│   └── services
│       └── MyServiceImpl.java
├── adapters
│   ├── in
│   │   └── web
│   │       └── MyController.java
│   └── out
│       └── persistence
│           ├── MyEntityJpaRepository.java
│           └── MyRepositoryImpl.java
└── config
    └── BeanConfiguration.java
```

## Implementação do Projeto

### 1. HexagonalApplication.java

```java
package com.example.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HexagonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);
    }
}
```

### 2. Domínio e Portas

#### 2.1. MyEntity.java

```java
package com.example.hexagonal.core.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Getters and Setters
}
```

#### 2.2. MyServicePort.java

```java
package com.example.hexagonal.core.ports.in;

public interface MyServicePort {
    void doSomething(Long id);
}
```

#### 2.3. MyRepositoryPort.java

```java
package com.example.hexagonal.core.ports.out;

import com.example.hexagonal.core.domain.model.MyEntity;

public interface MyRepositoryPort {
    MyEntity findById(Long id);
}
```

### 3. Implementação dos Serviços

#### 3.1. MyServiceImpl.java

```java
package com.example.hexagonal.core.services;

import com.example.hexagonal.core.domain.model.MyEntity;
import com.example.hexagonal.core.ports.in.MyServicePort;
import com.example.hexagonal.core.ports.out.MyRepositoryPort;
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
        // Business logic here
    }
}
```

### 4. Adapters

#### 4.1. MyController.java

```java
package com.example.hexagonal.adapters.in.web;

import com.example.hexagonal.core.ports.in.MyServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MyServicePort myServicePort;

    @Autowired
    public MyController(MyServicePort myServicePort) {
        this.myServicePort = myServicePort;
    }

    @GetMapping("/entity/{id}")
    public void getEntity(@PathVariable Long id) {
        myServicePort.doSomething(id);
    }
}
```

#### 4.2. MyEntityJpaRepository.java

```java
package com.example.hexagonal.adapters.out.persistence;

import com.example.hexagonal.core.domain.model.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyEntityJpaRepository extends JpaRepository<MyEntity, Long> {
}
```

#### 4.3. MyRepositoryImpl.java

```java
package com.example.hexagonal.adapters.out.persistence;

import com.example.hexagonal.core.domain.model.MyEntity;
import com.example.hexagonal.core.ports.out.MyRepositoryPort;
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
```

### 5. Configuração de Beans

#### BeanConfiguration.java

```java
package com.example.hexagonal.config;

import com.example.hexagonal.adapters.out.persistence.MyRepositoryImpl;
import com.example.hexagonal.core.ports.out.MyRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public MyRepositoryPort myRepositoryPort(MyRepositoryImpl myRepository) {
        return myRepository;
    }
}
```

### 6. Configuração do Banco de Dados H2

#### Dependências no `pom.xml`

Certifique-se de que você tenha as dependências corretas no seu `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Dependencies -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

#### Configuração no `application.properties`

Configuração para um banco de dados H2 em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### 7. Exemplo de Chamada da Aplicação

Para testar a aplicação, você pode usar uma ferramenta como Postman, cURL ou até mesmo o navegador.

#### 7.1. Inicialização do Spring Boot

Execute a aplicação Spring Boot:

```sh
mvn spring-boot:run
```

#### 7.2. Realizar uma Chamada HTTP

Você pode usar cURL para fazer uma solicitação GET ao seu endpoint. Certifique-se de que o servidor está em execução e que você tem dados na tabela `MyEntity`.

Por exemplo, usando cURL:

```sh
curl http://localhost:8080/entity/1
```

Ou use Postman para fazer a requisição GET para `http://localhost:8080/entity/1`.

---

Com essas etapas e exemplos, você tem uma configuração completa para iniciar e testar um projeto Spring Boot seguindo a arquitetura hexagonal, com suporte a um banco de dados H2.
