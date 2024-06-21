# Finance Management Java

User Service API - Api RESTful para gestão de usuários.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=coverage)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=bugs)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-user&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-user)

Transaction Service API - Api RESTful para gestão de transações.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=coverage)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=bugs)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=souluanf_finance-management-java-transaction)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=souluanf_finance-management-java-transaction&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=souluanf_golden-raspberry-awards-api)

## Sumário

- [Funcionalidades](#funcionalidades)
- [Requisitos](#requisitos)
- [Configuração](#configuração)
- [Execução](#execução)
    - [Executando a Aplicação com Maven](#executando-a-aplicação-com-maven)
    - [Executando a Aplicação com Docker Compose](#executando-a-aplicação-com-docker-compose)
- [Acesso ao Banco de Dados](#acesso-ao-banco-de-dados)
    - [Credenciais](#credenciais)
- [Contato](#contato)

## Requisitos

- JDK 21
- Maven 3.6+
- Docker

## Configuração

**Instalação do JDK, Maven e Docker:**

- [Instruções para instalação do JDK](https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html)
- [Instruções para instalação do Maven](https://maven.apache.org/install.html)
- [Instruções para instalação do Docker](https://docs.docker.com/get-docker/)

## Execução

### Executando a Aplicação com Docker Compose

Copie as variáveis utilizadas

```bash
cp .env.docker .env
```

Execute o comando abaixo:

```bash
docker-compose up -d
```

### Acesso à Documentação

#### Postman (preferência)
Ambas as collections estão no diretório `collections`:
[collections](collections)

Importe ambas as collections para o Postman e teste os serviços

#### OpenAPI

- **User OpenApi UI:** [http://localhost:8081/user-service](http://localhost:8081/user-service)
- **Transaction OpenApi UI**: [http://localhost:8082/transaction-service](http://localhost:8082/transaction-service)

### Credenciais

#### Serviços

| **Serviço** |   **URL**   | **Username** | **Password** | **Database** | **Port** |
|:-----------:|:-----------:|:------------:|:------------:|:------------:|:--------:|
|   `MySQL`   | `localhoat` | `finance_db` | `finance_db` | `finance_db` |  `3306`  |
| `Rabbit MQ` | `localhoat` |  `rabbitmq`  |  `rabbitmq`  |  `rabbitmq`  | `15672`  |

#### Auth

| **Username** | **Password** |
|:------------:|:------------:|
|   `admin`    |   `admin`    |

## Desenho da solução

![finance-management-java.png](finance-management-java.png)

## Contato

Para suporte ou feedback:

- **Nome:** Luan Fernandes
- **Email:**  [contact@luanfernandes.dev](mailto:contact@luanfernandes.dev)
- **Website:** [https://luanfernandes.dev](https://luanfernandes.dev)
- **LinkedIn:** [https://linkedin.com/in/souluanf](https://linkedin.com/in/souluanf)