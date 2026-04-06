<div align="center">

# Finance System API

### *Uma API de Transações Financeiras Completa e Segura*

<p align="center">
  <img src="https://img.icons8.com/color/150/000000/bank-cards.png" alt="Finance System" width="150" />
</p>

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![H2 Database](https://img.shields.io/badge/H2-003B57?style=for-the-badge&logo=database&logoColor=white)](https://h2database.com/)
[![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Glossary/HTML5)
[![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)

**Finance System** é uma API transacional desenvolvida em Spring Boot focada na segurança e integridade de transferências financeiras. Inclui controle de idempotência, validação de transações e uma interface frontend integrada.

[Features](#features) • [Arquitetura](#arquitetura) • [Instalação](#instalação) • [Endpoints](#endpoints) • [Licença](#licença)

</div>

---

## Features

- **Back-end Robusto**: Desenvolvido com Java 21 e Spring Boot.
- **Idempotência**: Controle rígido de chaves de idempotência para evitar transações duplicadas.
- **Interface Gráfica Intuitiva**: Frontend integrado via MVC usando HTML, CSS e external JavaScript na rota base.
- **Banco de Dados em Memória**: Configurado com banco H2 para facilitar testes e avaliações rápidas.

> **💡 Observação de Arquitetura (Treinamento):** Por se tratar de um projeto focado em estudo e treinamento, o controle de chaves de idempotência foi implementado de forma simplificada em memória, utilizando um `Set` (através de `ConcurrentHashMap.newKeySet()`) nativo do Java. Em um cenário real de produção, a boa prática seria utilizar um banco de dados em cache como o **Redis**, que permitiria escalar a aplicação (evitando problemas com múltiplas instâncias) e aplicar limpeza automática de chaves antigas através de tempo de expiração (TTL).

---

## Arquitetura (MVC Spring)

```text
┌─────────────────────────────────────────────┐
│                                             │
│              Usuário (Navegador)            │
│                                             │
└──────────────────┬──────────────────────────┘
                   │
                   │  Requisições HTTP (JSON/REST)
                   │
        ┌──────────▼─────────────┐
        │                        │
        │    Controllers         │
        │    (PaymentController) │
        │                        │
        │  • Recebe requisições  │
        │  • Valida DTOs         │
        │                        │
        └──────────┬─────────────┘
                   │
                   │  Lógica de Negócio
                   │
        ┌──────────▼─────────────┐
        │                        │
        │    Services            │
        │    (Payment, Auth)     │
        │                        │
        │  • Controle Idempotência│
        │  • Transferência Segura │
        │                        │
        └──────────┬─────────────┘
                   │
                   │  JPA / Hibernate
                   │
        ┌──────────▼─────────────┐
        │                        │
        │   Banco de Dados       │
        │       (H2)             │
        │                        │
        └────────────────────────┘
```

---

## Instalação

### Pré-requisitos

- **Java 21+**: [Instalar Java](https://adoptium.net/)
- **Maven**: (Ou utilize o `mvnw` incluído no projeto)

### Clone o Repositório

```bash
git clone https://github.com/seu-usuario/FinanceSystem.git
cd FinanceSystem
```

### Build e Execução

No Linux / macOS:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

No Windows:
```cmd
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

Acesse a aplicação (Frontend) em: **`http://localhost:8080/`**

---

## Endpoints Principais

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/seed` | Inicializa o banco de dados com usuários de teste (ID 1 e 2 com R$100 cada). |
| `POST` | `/transaction` | Realiza uma transferência financeira com chave de idempotência. |
| `POST` | `/list` | Lista as transações filtradas por categoria. |

---

## Organização do Projeto

Abaixo a divisão principal dos diretórios:
- **`src/main/java/.../Config/`**: Configurações MVC que expõe arquivos estáticos do frontend.
- **`src/main/java/.../Controllers/`**: Endpoints REST da API.
- **`src/main/java/.../Services/`**: Lógica de negócio e serviço de controle Idempotente (`AuthService`).
- **`src/main/resources/templates/`**: Frontend em HTML (`index.html`) usando estilos em `/web/style.css` e scripts em `/web/js.js`.

---

## Licença

MIT License

---

<div align="center">

**Construindo sistemas transacionais seguros e focados no usuário.**

[⬆ Voltar ao topo](#finance-system-api)

</div>

