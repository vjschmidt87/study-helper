package com.portfolio.studyhelper.seeder;

import com.portfolio.studyhelper.entity.*;
import com.portfolio.studyhelper.entity.Module;
import com.portfolio.studyhelper.enums.ResourceType;
import com.portfolio.studyhelper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final ResourceRepository resourceRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (moduleRepository.count() > 0) return;
        seedDefaultUser();
        seedModules();
    }

    private void seedDefaultUser() {
        User user = User.builder()
                .username("default")
                .email("default@studyhelper.com")
                .password("noop")
                .build();
        userRepository.save(user);
    }

    private void seedModules() {
        seedModule1();
        seedModule2();
        seedModule3();
        seedModule4();
        seedModule5();
        seedModule6();
    }

    // ========== MODULE 1: Data Product Architecture ==========
    private void seedModule1() {
        Module m = createModule(1,
                "Data Product Architecture",
                "Arquitetura de Data Products",
                "Understand the pillars of data product architecture, including batch vs. streaming, Medallion Architecture, data contracts and governance.",
                "Entender os pilares da arquitetura de data products, incluindo batch vs. streaming, Medallion Architecture, data contracts e governança.",
                "Understand the pillars of data product architecture, including batch vs. streaming, Medallion Architecture, data contracts and governance.",
                "Entender os pilares da arquitetura de data products, incluindo batch vs. streaming, Medallion Architecture, data contracts e governança.",
                1, 2, 1);

        // Topic 1.1 - Batch vs. Streaming
        Topic t1 = createTopic(m, "1.1", "Batch vs. Streaming", "Batch vs. Streaming",
                "Compare batch processing and real-time streaming, focusing on Kafka as a streaming solution and batch tools (e.g., Spark, Airflow).",
                "Comparar processamento em lote (batch) e em tempo real (streaming), com foco em Kafka como solução de streaming e ferramentas de batch (ex: Spark, Airflow).", 1);
        createResource(t1, "Batch Processing Explained (Databricks)", "Batch Processing Explained (Databricks)", ResourceType.ARTICLE, 1);
        createResource(t1, "Apache Spark Documentation", "Apache Spark Documentation", ResourceType.DOCUMENTATION, 2);
        createResource(t1, "Airflow Documentation", "Airflow Documentation", ResourceType.DOCUMENTATION, 3);
        createResource(t1, "Kafka Documentation", "Kafka Documentation", ResourceType.DOCUMENTATION, 4);
        createResource(t1, "Streaming 101 - Martin Kleppmann", "Streaming 101 - Martin Kleppmann", ResourceType.ARTICLE, 5);
        createResource(t1, "Kafka vs. Spark Streaming (Confluent)", "Kafka vs. Spark Streaming (Confluent)", ResourceType.ARTICLE, 6);
        createExercise(t1, "Set up a local environment with Kafka and produce/consume messages.",
                "Configurar um ambiente local com Kafka e produzir/consumir mensagens.", 1);
        createExercise(t1, "Simulate a batch pipeline with Spark (e.g., read data from a CSV and save to Snowflake).",
                "Simular um pipeline batch com Spark (ex: ler dados de um CSV e salvar no Snowflake).", 2);

        // Topic 1.2 - Medallion Architecture
        Topic t2 = createTopic(m, "1.2", "Medallion Architecture (Bronze/Silver/Gold)", "Medallion Architecture (Bronze/Silver/Gold)",
                "Progressive data refinement architecture, common in data lakes/data meshes.",
                "Arquitetura de refinamento progressivo de dados, comum em data lakes/data meshes.", 2);
        createResource(t2, "Medallion Architecture (Databricks)", "Medallion Architecture (Databricks)", ResourceType.ARTICLE, 1);
        createResource(t2, "dbt + Medallion Tutorial", "dbt + Medallion Tutorial", ResourceType.TUTORIAL, 2);
        createExercise(t2, "Create a minimal example with 3 layers (Bronze → Silver → Gold) using CSV data for Bronze, dbt or Spark for Silver, and a final table in Snowflake for Gold.",
                "Criar um exemplo mínimo com 3 camadas (Bronze → Silver → Gold) usando dados em CSV para Bronze, transformação com dbt ou Spark para Silver, e tabela final no Snowflake para Gold.", 1);

        // Topic 1.3 - Data Contracts & Schema Evolution
        Topic t3 = createTopic(m, "1.3", "Data Contracts & Schema Evolution", "Data Contracts & Schema Evolution",
                "Ensure consistency and versioning of schemas in APIs, Kafka messages, and databases.",
                "Garantir consistência e versionamento de schemas em APIs, mensagens Kafka e bancos de dados.", 3);
        createResource(t3, "Data Contracts - Martin Fowler", "Data Contracts - Martin Fowler", ResourceType.ARTICLE, 1);
        createResource(t3, "OpenAPI Specification", "OpenAPI Specification", ResourceType.DOCUMENTATION, 2);
        createResource(t3, "Schema Evolution in Kafka (Confluent)", "Schema Evolution in Kafka (Confluent)", ResourceType.ARTICLE, 3);
        createResource(t3, "Apache Avro", "Apache Avro", ResourceType.DOCUMENTATION, 4);
        createResource(t3, "Protocol Buffers", "Protocol Buffers", ResourceType.DOCUMENTATION, 5);
        createResource(t3, "JSON Schema", "JSON Schema", ResourceType.DOCUMENTATION, 6);
        createExercise(t3, "Define an Avro schema for Kafka messages and simulate an evolution (e.g., add an optional field).",
                "Definir um schema Avro para mensagens Kafka e simular uma evolução (ex: adicionar um campo opcional).", 1);
        createExercise(t3, "Use Confluent Schema Registry to manage versions.",
                "Usar o Confluent Schema Registry para gerenciar versões.", 2);

        // Topic 1.4 - Governance & Traceability
        Topic t4 = createTopic(m, "1.4", "Governance & Traceability", "Governance & Traceability",
                "Ensure transparency, auditability, and quality control in data.",
                "Garantir transparência, auditabilidade e controle de qualidade nos dados.", 4);
        createResource(t4, "Data Governance Framework (TDAN)", "Data Governance Framework (TDAN)", ResourceType.ARTICLE, 1);
        createResource(t4, "Great Expectations Documentation", "Great Expectations Documentation", ResourceType.DOCUMENTATION, 2);
        createResource(t4, "Apache Atlas", "Apache Atlas", ResourceType.DOCUMENTATION, 3);
        createResource(t4, "OpenLineage", "OpenLineage", ResourceType.DOCUMENTATION, 4);
        createExercise(t4, "Configure Great Expectations to validate a dataset and generate quality reports.",
                "Configurar o Great Expectations para validar um dataset e gerar relatórios de qualidade.", 1);
        createExercise(t4, "Explore OpenLineage to trace a simple pipeline.",
                "Explorar o OpenLineage para rastrear um pipeline simples.", 2);

        // Topic 1.5 - Human-in-the-Loop
        Topic t5 = createTopic(m, "1.5", "Human-in-the-Loop", "Human-in-the-Loop",
                "Integrate human approvals into automated processes to optimize quality and time.",
                "Integrar aprovações humanas em processos automatizados para otimizar qualidade e tempo.", 5);
        createResource(t5, "Human-in-the-Loop in AI (Towards Data Science)", "Human-in-the-Loop in AI (Towards Data Science)", ResourceType.ARTICLE, 1);
        createResource(t5, "Temporal Workflows", "Temporal Workflows", ResourceType.DOCUMENTATION, 2);
        createExercise(t5, "Create a flow in Airflow that: 1) Generates a report with LLM, 2) Sends for approval via Slack/Jira, 3) Proceeds only after approval.",
                "Criar um fluxo no Airflow que: 1) Gera um relatório com LLM, 2) Envia para aprovação via Slack/Jira, 3) Prossegue apenas após aprovação.", 1);

        // Topic 1.6 - Deterministic vs. Probabilistic
        Topic t6 = createTopic(m, "1.6", "Deterministic vs. Probabilistic", "Deterministic vs. Probabilistic",
                "Balance precision (deterministic) and flexibility (probabilistic) in pipelines.",
                "Equilibrar precisão (determinístico) e flexibilidade (probabilístico) em pipelines.", 6);
        createResource(t6, "Deterministic vs. Probabilistic Systems - Martin Kleppmann", "Deterministic vs. Probabilistic Systems - Martin Kleppmann", ResourceType.ARTICLE, 1);
        createResource(t6, "LLM Reliability (Towards Data Science)", "LLM Reliability (Towards Data Science)", ResourceType.ARTICLE, 2);
        createExercise(t6, "Compare two pipelines: 1) Deterministic: SQL transformation of sales data, 2) Probabilistic: Product description generation with LLM (Claude). Measure time, cost, and result quality.",
                "Comparar dois pipelines: 1) Determinístico: Transformação SQL de dados de vendas, 2) Probabilístico: Geração de descrições de produtos com LLM (Claude). Medir tempo, custo e qualidade dos resultados.", 1);
    }

    // ========== MODULE 2: Backend (Core Stack) ==========
    private void seedModule2() {
        Module m = createModule(2,
                "Backend (Core Stack)",
                "Backend (Core Stack)",
                "Master backend technologies, APIs, and authentication, focusing on Java/Spring, Python, and OpenAPI.",
                "Dominar as tecnologias de backend, APIs e autenticação, com foco em Java/Spring, Python e OpenAPI.",
                "Master backend technologies, APIs, and authentication, focusing on Java/Spring, Python, and OpenAPI.",
                "Dominar as tecnologias de backend, APIs e autenticação, com foco em Java/Spring, Python e OpenAPI.",
                3, 4, 2);

        // Topic 2.1 - Java + Spring
        Topic t1 = createTopic(m, "2.1", "Java + Spring (or Python Alternative)", "Java + Spring (ou Alternativa Python)",
                "Understand the Spring ecosystem (or Python alternatives) to build robust APIs.",
                "Entender o ecossistema Spring (ou alternativas em Python) para construir APIs robustas.", 1);
        createResource(t1, "Spring Boot Documentation", "Spring Boot Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t1, "Spring Security + Keycloak", "Spring Security + Keycloak", ResourceType.TUTORIAL, 2);
        createResource(t1, "Spring Boot + Kafka Tutorial", "Spring Boot + Kafka Tutorial", ResourceType.TUTORIAL, 3);
        createResource(t1, "FastAPI Documentation", "FastAPI Documentation", ResourceType.DOCUMENTATION, 4);
        createResource(t1, "FastAPI + Kafka", "FastAPI + Kafka", ResourceType.TUTORIAL, 5);
        createExercise(t1, "Create a REST API with Spring Boot or FastAPI that: receives data via Kafka, validates schemas with Pydantic (Python) or Spring Validation, authenticates with Keycloak.",
                "Criar uma API REST com Spring Boot ou FastAPI que: recebe dados via Kafka, valida schemas com Pydantic (Python) ou Spring Validation, autentica com Keycloak.", 1);

        // Topic 2.2 - REST / OpenAPI-First
        Topic t2 = createTopic(m, "2.2", "REST / OpenAPI-First", "REST / OpenAPI-First",
                "Design APIs using the contract-first (OpenAPI) pattern to ensure consistency.",
                "Projetar APIs usando o padrão contract-first (OpenAPI) para garantir consistência.", 2);
        createResource(t2, "Swagger Editor", "Swagger Editor", ResourceType.DOCUMENTATION, 1);
        createResource(t2, "Redoc", "Redoc", ResourceType.DOCUMENTATION, 2);
        createResource(t2, "OpenAPI + Spring Boot", "OpenAPI + Spring Boot", ResourceType.TUTORIAL, 3);
        createResource(t2, "OpenAPI Generator", "OpenAPI Generator", ResourceType.DOCUMENTATION, 4);
        createExercise(t2, "Create an OpenAPI file (openapi.yaml) for a products API. Generate code automatically with Spring Boot or Python OpenAPI Generator.",
                "Criar um arquivo OpenAPI (openapi.yaml) para uma API de produtos. Gerar código automaticamente com Spring Boot ou Python OpenAPI Generator.", 1);

        // Topic 2.3 - Keycloak / OIDC
        Topic t3 = createTopic(m, "2.3", "Keycloak / OIDC", "Keycloak / OIDC",
                "Implement authentication and authorization with OpenID Connect (OIDC) using Keycloak.",
                "Implementar autenticação e autorização com OpenID Connect (OIDC) usando Keycloak.", 3);
        createResource(t3, "Keycloak Documentation", "Keycloak Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t3, "Spring Security + Keycloak", "Spring Security + Keycloak", ResourceType.TUTORIAL, 2);
        createResource(t3, "FastAPI + OIDC", "FastAPI + OIDC", ResourceType.TUTORIAL, 3);
        createExercise(t3, "Configure a realm in Keycloak with: a client for a Spring Boot/FastAPI API, roles and test users. Protect API endpoints with JWT tokens.",
                "Configurar um realm no Keycloak com: um client para uma API Spring Boot/FastAPI, roles e usuários de teste. Proteger endpoints da API com tokens JWT.", 1);
    }

    // ========== MODULE 3: Frontend & Data Visualization ==========
    private void seedModule3() {
        Module m = createModule(3,
                "Frontend & Data Visualization",
                "Frontend & Visualização de Dados",
                "Master Angular and AgGrid to create interactive interfaces and data visualizations.",
                "Dominar Angular e AgGrid para criar interfaces interativas e visualizações de dados.",
                "Master Angular and AgGrid to create interactive interfaces and data visualizations.",
                "Dominar Angular e AgGrid para criar interfaces interativas e visualizações de dados.",
                5, 6, 3);

        // Topic 3.1 - Angular
        Topic t1 = createTopic(m, "3.1", "Angular", "Angular",
                "Frontend framework for building SPAs (Single Page Applications) with TypeScript.",
                "Framework frontend para construir SPAs (Single Page Applications) com TypeScript.", 1);
        createResource(t1, "Angular Documentation", "Angular Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t1, "Angular + RxJS", "Angular + RxJS", ResourceType.TUTORIAL, 2);
        createResource(t1, "NgRx Documentation", "NgRx Documentation", ResourceType.DOCUMENTATION, 3);
        createExercise(t1, "Create an Angular dashboard that: consumes an API (e.g., backend products), displays data in a table with AgGrid, allows filtering and sorting.",
                "Criar um dashboard em Angular que: consome uma API (ex: produtos do backend), exibe dados em uma tabela com AgGrid, permite filtros e ordenação.", 1);

        // Topic 3.2 - AgGrid
        Topic t2 = createTopic(m, "3.2", "AgGrid", "AgGrid",
                "Advanced grid component for displaying and manipulating large data volumes.",
                "Componente avançado de grid para exibir e manipular grandes volumes de dados.", 2);
        createResource(t2, "AgGrid Documentation", "AgGrid Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t2, "AgGrid + Angular Tutorial", "AgGrid + Angular Tutorial", ResourceType.TUTORIAL, 2);
        createExercise(t2, "Create an AgGrid table that: loads data from an API, allows inline editing and CSV export, implements virtual scrolling for 10,000+ rows.",
                "Criar uma tabela com AgGrid que: carrega dados de uma API, permite edição inline e exportação para CSV, implementa virtual scrolling para 10.000+ linhas.", 1);
    }

    // ========== MODULE 4: Data & Pipelines ==========
    private void seedModule4() {
        Module m = createModule(4,
                "Data & Pipelines",
                "Data & Pipelines",
                "Master tools for data processing, orchestration, and LLM integration.",
                "Dominar ferramentas para processamento de dados, orquestração e integração com LLM.",
                "Master tools for data processing, orchestration, and LLM integration.",
                "Dominar ferramentas para processamento de dados, orquestração e integração com LLM.",
                7, 8, 4);

        // Topic 4.1 - Python for Data
        Topic t1 = createTopic(m, "4.1", "Python for Data", "Python para Dados",
                "Use Python for data manipulation, ETL, and LLM integration.",
                "Usar Python para manipulação de dados, ETL e integração com LLM.", 1);
        createResource(t1, "Pandas Documentation", "Pandas Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t1, "PySpark Documentation", "PySpark Documentation", ResourceType.DOCUMENTATION, 2);
        createResource(t1, "Dask Documentation", "Dask Documentation", ResourceType.DOCUMENTATION, 3);
        createExercise(t1, "Create a Python script that: 1) Reads a CSV with sales data, 2) Aggregates with Pandas, 3) Saves result in Parquet, 4) Exposes data via a FastAPI API.",
                "Criar um script Python que: 1) Lê um CSV com dados de vendas, 2) Faz agregações com Pandas, 3) Salva o resultado em Parquet, 4) Exponha os dados via uma API FastAPI.", 1);

        // Topic 4.2 - Claude via Amazon Bedrock
        Topic t2 = createTopic(m, "4.2", "Claude via Amazon Bedrock", "Claude via Amazon Bedrock",
                "Integrate LLMs (Claude) into pipelines for insight generation or automation.",
                "Integrar LLMs (Claude) em pipelines para geração de insights ou automação.", 2);
        createResource(t2, "Amazon Bedrock Documentation", "Amazon Bedrock Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t2, "Claude API (Anthropic)", "Claude API (Anthropic)", ResourceType.DOCUMENTATION, 2);
        createExercise(t2, "Create a script that: 1) Reads a product dataset (CSV/JSON), 2) Sends each product to Claude via Bedrock to generate a description, 3) Saves results to a new file.",
                "Criar um script que: 1) Lê um dataset de produtos (CSV/JSON), 2) Envia cada produto para o Claude via Bedrock para gerar uma descrição, 3) Salva os resultados em um novo arquivo.", 1);

        // Topic 4.3 - Snowflake
        Topic t3 = createTopic(m, "4.3", "Snowflake", "Snowflake",
                "Master the analytical layer of the data product, focusing on SQL, optimization, and integration.",
                "Dominar a camada analítica do data product, com foco em SQL, otimização e integração.", 3);
        createResource(t3, "Snowflake Documentation", "Snowflake Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t3, "dbt + Snowflake", "dbt + Snowflake", ResourceType.TUTORIAL, 2);
        createExercise(t3, "Create a pipeline that: 1) Loads sales data (Bronze) to Snowflake, 2) Transforms (Silver) with SQL, 3) Generates a Gold table for consumption (e.g., dashboard).",
                "Criar um pipeline que: 1) Carrega dados de vendas (Bronze) para o Snowflake, 2) Faz transformações (Silver) com SQL, 3) Gera uma tabela Gold para consumo (ex: dashboard).", 1);

        // Topic 4.4 - Vector Databases
        Topic t4 = createTopic(m, "4.4", "Vector Databases", "Vector Databases",
                "Understand how vector databases work for semantic search and embeddings.",
                "Entender como funcionam bases vetoriais para busca semântica e embeddings.", 4);
        createResource(t4, "Vector Databases Explained (Towards Data Science)", "Vector Databases Explained (Towards Data Science)", ResourceType.ARTICLE, 1);
        createResource(t4, "Pinecone Documentation", "Pinecone Documentation", ResourceType.DOCUMENTATION, 2);
        createResource(t4, "Weaviate Documentation", "Weaviate Documentation", ResourceType.DOCUMENTATION, 3);
        createResource(t4, "FAISS (Facebook)", "FAISS (Facebook)", ResourceType.DOCUMENTATION, 4);
        createExercise(t4, "Create a prototype with FAISS or Pinecone that: 1) Generates product embeddings using an LLM model, 2) Allows semantic search (e.g., 'products like this').",
                "Criar um protótipo com FAISS ou Pinecone que: 1) Gera embeddings de produtos usando um modelo de LLM, 2) Permite busca semântica (ex: 'produtos como este').", 1);

        // Topic 4.5 - Flow Orchestration
        Topic t5 = createTopic(m, "4.5", "Flow Orchestration", "Orquestração de Fluxos",
                "Manage complex pipelines with orchestration tools.",
                "Gerenciar pipelines complexos com ferramentas de orquestração.", 5);
        createResource(t5, "Airflow Documentation", "Airflow Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t5, "AWS Step Functions Documentation", "AWS Step Functions Documentation", ResourceType.DOCUMENTATION, 2);
        createResource(t5, "Prefect Documentation", "Prefect Documentation", ResourceType.DOCUMENTATION, 3);
        createResource(t5, "Dagster Documentation", "Dagster Documentation", ResourceType.DOCUMENTATION, 4);
        createExercise(t5, "Create a DAG in Airflow that: 1) Extracts data from an API (e.g., SeaRatesFx), 2) Transforms with Pandas/Spark, 3) Loads into Snowflake, 4) Sends a Slack notification on failure.",
                "Criar um DAG no Airflow que: 1) Extrair dados de uma API (ex: SeaRatesFx), 2) Transforma com Pandas/Spark, 3) Carrega no Snowflake, 4) Envia notificação no Slack em caso de falha.", 1);

        // Topic 4.6 - Kafka
        Topic t6 = createTopic(m, "4.6", "Kafka", "Kafka",
                "Master Kafka for system integration and stream processing.",
                "Dominar Kafka para integração entre sistemas e processamento de streams.", 6);
        createResource(t6, "Kafka Documentation", "Kafka Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t6, "Confluent Kafka Python Client", "Confluent Kafka Python Client", ResourceType.DOCUMENTATION, 2);
        createResource(t6, "Kafka Connect Documentation", "Kafka Connect Documentation", ResourceType.DOCUMENTATION, 3);
        createExercise(t6, "Create a Kafka producer/consumer in Python that: 1) Produces messages with Avro schema, 2) Consumes and validates messages with Schema Registry, 3) Saves processed data to Snowflake.",
                "Criar um produtor/consumidor Kafka em Python que: 1) Produz mensagens com schema Avro, 2) Consome e valida as mensagens com o Schema Registry, 3) Salva os dados processados no Snowflake.", 1);
    }

    // ========== MODULE 5: Infra & DevOps ==========
    private void seedModule5() {
        Module m = createModule(5,
                "Infra & DevOps",
                "Infra & DevOps",
                "Understand AWS infrastructure, Kubernetes, CI/CD, and testing for data product deployment.",
                "Entender a infraestrutura AWS, Kubernetes, CI/CD e testes para deploy de data products.",
                "Understand AWS infrastructure, Kubernetes, CI/CD, and testing for data product deployment.",
                "Entender a infraestrutura AWS, Kubernetes, CI/CD e testes para deploy de data products.",
                9, 10, 5);

        // Topic 5.1 - AWS
        Topic t1 = createTopic(m, "5.1", "AWS (S3, EKS, ECR)", "AWS (S3, EKS, ECR)",
                "Master essential AWS services for data products.",
                "Dominar os serviços AWS essenciais para data products.", 1);
        createResource(t1, "AWS S3 Documentation", "AWS S3 Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t1, "EKS Documentation", "EKS Documentation", ResourceType.DOCUMENTATION, 2);
        createResource(t1, "Helm Documentation", "Helm Documentation", ResourceType.DOCUMENTATION, 3);
        createExercise(t1, "Create an S3 bucket to store sample data. Deploy a Spring Boot/FastAPI application on EKS using Helm.",
                "Criar um bucket S3 para armazenar dados de exemplo. Deployar uma aplicação Spring Boot/FastAPI no EKS usando Helm.", 1);

        // Topic 5.2 - Kubernetes
        Topic t2 = createTopic(m, "5.2", "Kubernetes (EKS)", "Kubernetes (EKS)",
                "Manage containers in production with Kubernetes.",
                "Gerenciar containers em produção com Kubernetes.", 2);
        createResource(t2, "Kubernetes Documentation", "Kubernetes Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t2, "Helm Best Practices", "Helm Best Practices", ResourceType.ARTICLE, 2);
        createExercise(t2, "Create a Deployment on EKS for a Python/FastAPI API. Configure a Service and Ingress to expose the API. Use Helm to package the application.",
                "Criar um Deployment no EKS para uma API Python/FastAPI. Configurar um Service e Ingress para expor a API. Usar Helm para empacotar a aplicação.", 1);

        // Topic 5.3 - GitLab CI
        Topic t3 = createTopic(m, "5.3", "GitLab CI", "GitLab CI",
                "Automate build, test, and deploy pipelines with GitLab CI.",
                "Automatizar pipelines de build, test e deploy com GitLab CI.", 3);
        createResource(t3, "GitLab CI Documentation", "GitLab CI Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t3, "GitLab CI Examples", "GitLab CI Examples", ResourceType.TUTORIAL, 2);
        createExercise(t3, "Create a .gitlab-ci.yml file that: 1) Builds a Docker image, 2) Runs unit and integration tests, 3) Deploys to EKS on success.",
                "Criar um arquivo .gitlab-ci.yml que: 1) Faz build de uma imagem Docker, 2) Roda testes unitários e de integração, 3) Faz deploy no EKS em caso de sucesso.", 1);

        // Topic 5.4 - Testcontainers
        Topic t4 = createTopic(m, "5.4", "Testcontainers", "Testcontainers",
                "Use containers for deterministic integration testing.",
                "Usar containers para testes de integração determinísticos.", 4);
        createResource(t4, "Testcontainers Documentation", "Testcontainers Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t4, "Testcontainers + Python", "Testcontainers + Python", ResourceType.TUTORIAL, 2);
        createExercise(t4, "Create an integration test for an API that: 1) Starts a Postgres container, 2) Performs database operations, 3) Validates results.",
                "Criar um teste de integração para uma API que: 1) Inicia um container com Postgres, 2) Faz operações no banco, 3) Valida os resultados.", 1);

        // Topic 5.5 - Test Suite
        Topic t5 = createTopic(m, "5.5", "Test Suite", "Test Suite",
                "Implement a comprehensive test suite to ensure quality.",
                "Implementar uma suíte completa de testes para garantir qualidade.", 5);
        createResource(t5, "Testing in Python (Real Python)", "Testing in Python (Real Python)", ResourceType.ARTICLE, 1);
        createResource(t5, "JUnit 5 Documentation", "JUnit 5 Documentation", ResourceType.DOCUMENTATION, 2);
        createExercise(t5, "Create a test suite for an API that includes: 1) Unit tests (mock dependencies), 2) Integration tests (real database), 3) E2E tests (simulate a user), 4) Performance test (simulate 100 requests).",
                "Criar uma suíte de testes para uma API que inclua: 1) Testes unitários (mock de dependências), 2) Testes de integração (banco de dados real), 3) Testes E2E (simular um usuário), 4) Teste de performance (simular 100 requisições).", 1);
    }

    // ========== MODULE 6: Processes & Development Tools ==========
    private void seedModule6() {
        Module m = createModule(6,
                "Processes & Development Tools",
                "Processos & Ferramentas de Desenvolvimento",
                "Adopt best practices for agile development, task management, and AI usage.",
                "Adotar boas práticas de desenvolvimento ágil, gestão de tarefas e uso de IA.",
                "Adopt best practices for agile development, task management, and AI usage.",
                "Adotar boas práticas de desenvolvimento ágil, gestão de tarefas e uso de IA.",
                11, 12, 6);

        // Topic 6.1 - Kanban
        Topic t1 = createTopic(m, "6.1", "Kanban (vs. Scrum)", "Kanban (vs. Scrum)",
                "Understand agile methodologies and how to apply them in daily work.",
                "Entender metodologias ágeis e como aplicá-las no dia a dia.", 1);
        createResource(t1, "Kanban Guide", "Kanban Guide", ResourceType.ARTICLE, 1);
        createResource(t1, "Jira Documentation", "Jira Documentation", ResourceType.DOCUMENTATION, 2);
        createExercise(t1, "Create a Kanban board in Jira for a fictional project. Simulate a workflow with WIP limits.",
                "Criar um board Kanban no Jira para um projeto fictício. Simular um fluxo de trabalho com WIP limits.", 1);

        // Topic 6.2 - Jira & Confluence
        Topic t2 = createTopic(m, "6.2", "Jira & Confluence", "Jira & Confluence",
                "Master project management and documentation tools.",
                "Dominar as ferramentas de gestão de projetos e documentação da KN.", 2);
        createResource(t2, "Jira Tutorial", "Jira Tutorial", ResourceType.TUTORIAL, 1);
        createResource(t2, "Confluence Documentation", "Confluence Documentation", ResourceType.DOCUMENTATION, 2);
        createExercise(t2, "Create documentation in Confluence for a fictional project. Link Jira issues to the documentation.",
                "Criar uma documentação no Confluence para um projeto fictício. Linkar issues do Jira à documentação.", 1);

        // Topic 6.3 - Claude Code & AI
        Topic t3 = createTopic(m, "6.3", "Claude Code & AI in the Process", "Claude Code & IA no Processo",
                "Use AI to optimize development, code review, and documentation.",
                "Usar IA para otimizar desenvolvimento, revisão de código e documentação.", 3);
        createResource(t3, "Claude Code Documentation", "Claude Code Documentation", ResourceType.DOCUMENTATION, 1);
        createResource(t3, "GitHub Copilot", "GitHub Copilot", ResourceType.DOCUMENTATION, 2);
        createExercise(t3, "Use Claude Code to: 1) Generate a Spring Boot/FastAPI project skeleton, 2) Review a code snippet and suggest improvements, 3) Automatically create unit tests.",
                "Usar o Claude Code para: 1) Gerar um esqueleto de projeto Spring Boot/FastAPI, 2) Revisar um trecho de código e sugerir melhorias, 3) Criar testes unitários automaticamente.", 1);
    }

    // ========== Helper Methods ==========

    private Module createModule(int number, String titleEn, String titlePt,
                                String descEn, String descPt,
                                String objEn, String objPt,
                                int weekStart, int weekEnd, int position) {
        Module module = Module.builder()
                .number(number)
                .titleEn(titleEn)
                .titlePt(titlePt)
                .descriptionEn(descEn)
                .descriptionPt(descPt)
                .objectiveEn(objEn)
                .objectivePt(objPt)
                .weekStart(weekStart)
                .weekEnd(weekEnd)
                .position(position)
                .build();
        return moduleRepository.save(module);
    }

    private Topic createTopic(Module module, String number, String titleEn, String titlePt,
                              String conceptEn, String conceptPt, int position) {
        Topic topic = Topic.builder()
                .module(module)
                .number(number)
                .titleEn(titleEn)
                .titlePt(titlePt)
                .conceptEn(conceptEn)
                .conceptPt(conceptPt)
                .position(position)
                .build();
        return topicRepository.save(topic);
    }

    private void createResource(Topic topic, String titleEn, String titlePt,
                                ResourceType type, int position) {
        Resource resource = Resource.builder()
                .topic(topic)
                .titleEn(titleEn)
                .titlePt(titlePt)
                .type(type)
                .position(position)
                .build();
        resourceRepository.save(resource);
    }

    private void createExercise(Topic topic, String descEn, String descPt, int position) {
        Exercise exercise = Exercise.builder()
                .topic(topic)
                .descriptionEn(descEn)
                .descriptionPt(descPt)
                .position(position)
                .build();
        exerciseRepository.save(exercise);
    }
}
