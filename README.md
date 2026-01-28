# 💰 Sistema de Gerenciamento de Despesas e Reembolsos

> Uma API REST para gestão inteligente de despesas corporativas, reembolsos e controle orçamentário em tempo real.

## 🎯 **O Problema**

Empresas enfrentam desafios críticos no controle financeiro:

- ❌ **Despesas não rastreadas** causam estouro de orçamento
- ❌ **Reembolsos demorados** frustram colaboradores
- ❌ **Falta de visibilidade** sobre gastos por centro de custo
- ❌ **Processos manuais** propensos a erros e fraudes
- ❌ **Limites não respeitados** geram prejuízos inesperados

## 💡 **A Solução**

Sistema backend robusto que automatiza toda a gestão de despesas corporativas:

### **Para o RH/Financeiro:**
✅ Controle de orçamento por centro de custo em tempo real  
✅ Rastreamento completo de todas as despesas  
✅ Validação automática de limites e políticas  
✅ Histórico de aprovações e rejeições  

### **Para os Colaboradores:**
✅ Registro rápido de despesas via API  
✅ Acompanhamento do saldo de reembolso disponível  
✅ Histórico completo de solicitações  

### **Para Gestores:**
✅ Aprovação/rejeição de despesas com justificativa  
✅ Visão consolidada por equipe  
✅ Auditoria completa de decisões  

---

## 🚀 **Funcionalidades Principais**

### **Gestão de Centros de Custo**
- Definição de orçamentos mensais por área
- Controle de saldo disponível em tempo real
- Bloqueio automático de despesas quando saldo insuficiente

### **Controle de Funcionários**
- Cadastro com limite mensal de reembolso
- Saldo disponível atualizado automaticamente
- Operações CRUD completas

### **Registro de Despesas**
- Categorização por tipo (Alimentação, Transporte, Hospedagem, etc)
- Vinculação a funcionário e centro de custo
- Status automático (Pendente, Aprovada, Rejeitada)
- Consulta por funcionário
- Validações de regras de negócio

### **Histórico de Aprovações**
- Registro completo de todas as aprovações/rejeições
- Rastreabilidade de decisões
- Consulta por despesa específica

---

## 🏗️ **Arquitetura**
```
┌─────────────┐
│   Client    │ (Postman, Frontend, Mobile App)
└──────┬──────┘
       │ HTTP/JSON
┌──────▼──────────────────────────────────┐
│         REST API (Spring Boot)          │
│  ┌──────────────────────────────────┐   │
│  │       Controllers Layer          │   │ ← Endpoints REST
│  └────────────┬─────────────────────┘   │
│  ┌────────────▼─────────────────────┐   │
│  │        Services Layer            │   │ ← Lógica de Negócio
│  └────────────┬─────────────────────┘   │
│  ┌────────────▼─────────────────────┐   │
│  │      Repositories Layer          │   │ ← Acesso a Dados
│  └────────────┬─────────────────────┘   │
└───────────────┼─────────────────────────┘
                │ JPA/Hibernate
       ┌────────▼────────┐
       │   PostgreSQL    │
       └─────────────────┘
```

---

## 🛠️ **Stack Tecnológica**

### **Backend**
- **Java 17+** - Linguagem principal
- **Spring Boot 4.0.2** - Framework web e injeção de dependências
- **Spring Data JPA** - Abstração de persistência
- **Hibernate 7.2.1** - ORM e geração automática de schema

### **Banco de Dados**
- **PostgreSQL 18** - Banco relacional
- **HikariCP** - Pool de conexões de alta performance

### **Ferramentas**
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de boilerplate
- **Spring DevTools** - Hot reload em desenvolvimento

---

## 📦 **Instalação e Execução**

### **Pré-requisitos**
- Java 17 ou superior
- PostgreSQL 12+
- Maven 3.6+

### **1. Clone o repositório**
```bash
git clone https://github.com/MarcosAlexx/GerenciamentoDeDespesasEReembolsos.git
cd GerenciamentoDeDespesasEReembolsos
```

### **2. Configure o banco de dados**

Crie o banco no PostgreSQL:
```sql
CREATE DATABASE gerenciamento_despesas;
```

Edite `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciamento_despesas
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### **3. Execute a aplicação**
```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 📚 **Endpoints da API**

### **Centros de Custo**
```http
POST   /centros-de-custo              # Criar centro de custo
```

**Exemplo:**
```json
POST /centros-de-custo
{
  "nome": "TI",
  "orcamentoMensal": 15000.00,
  "saldoDisponivel": 15000.00
}
```

---

### **Funcionários**
```http
POST   /funcionarios                  # Criar funcionário
GET    /funcionarios                  # Listar todos
GET    /funcionarios/{id}             # Buscar por ID
PUT    /funcionarios/{id}             # Atualizar
DELETE /funcionarios/{id}             # Deletar
```

**Exemplo:**
```json
POST /funcionarios
{
  "nome": "João Silva",
  "cargo": "Desenvolvedor",
  "limiteReembolsoMensal": 1000.00,
  "saldoReembolsoDisponivel": 1000.00
}
```

---

### **Despesas**
```http
POST   /despesas                      # Criar despesa
GET    /despesas                      # Listar todas
GET    /despesas/{id}                 # Buscar por ID
GET    /despesas/funcionario/{id}     # Por funcionário
PUT    /despesas/{id}                 # Atualizar
DELETE /despesas/{id}                 # Deletar
```

**Exemplo:**
```json
POST /despesas
{
  "valor": 150.50,
  "tipo": "ALIMENTACAO",
  "descricao": "Almoço com cliente",
  "funcionario": {
    "id": "uuid-do-funcionario"
  },
  "centroDeCusto": {
    "id": "uuid-do-centro-custo"
  }
}
```

---

### **Histórico de Aprovações**
```http
GET    /historico-aprovacoes             # Listar todos
GET    /historico-aprovacoes/{id}        # Buscar por ID
GET    /historico-aprovacoes/despesa/{id} # Por despesa (em desenvolvimento)
```

---

## 🔒 **Validações de Negócio**

✅ **Valor da despesa** deve ser maior que zero  
✅ **Centro de custo** deve ter saldo disponível  
✅ **Funcionário** deve ter saldo de reembolso disponível  
✅ **Tipo de despesa** deve ser válido (enum)  
✅ **Descrição** é obrigatória  
✅ **Relacionamentos** são validados automaticamente (JPA)

---

## 🧪 **Testes**

Testado manualmente via **Postman** com cenários:
- ✅ Criação de registros com sucesso
- ✅ Validação de campos obrigatórios
- ✅ Verificação de relacionamentos JPA
- ✅ Persistência correta no PostgreSQL
- ✅ Operações CRUD completas
- ✅ Consultas por funcionário

---

## 🎓 **Aprendizados Técnicos**

Durante o desenvolvimento, resolvi desafios reais:

### **Optimistic Locking Exception**
**Problema:** Geração manual de IDs causava conflitos no Hibernate  
**Solução:** Removi geração manual e usei `@GeneratedValue` + `@JsonProperty(READ_ONLY)`

### **Serialização JSON**
**Problema:** IDs sendo enviados no POST causavam tentativa de UPDATE  
**Solução:** Configurei `@JsonProperty(access = Access.READ_ONLY)` nos IDs

### **Lazy Loading**
**Problema:** N+1 queries em relacionamentos  
**Solução:** Configurei `fetch = FetchType.LAZY` e busca explícita quando necessário

### **Autenticação PostgreSQL**
**Problema:** Erro de autenticação ao conectar no banco  
**Solução:** Verificação e correção das credenciais no application.properties

---

## 🚀 **Próximas Melhorias**

### **Endpoints adicionais planejados:**
- [ ] GET `/centros-de-custo` - Listar todos centros de custo
- [ ] GET `/centros-de-custo/{id}` - Buscar centro de custo por ID
- [ ] GET `/despesas/centro-custo/{id}` - Despesas por centro de custo
- [ ] GET `/despesas/status/{status}` - Despesas por status
- [ ] GET `/funcionarios/cargo/{cargo}` - Funcionários por cargo

### **Novas funcionalidades:**
- [ ] Sistema de autenticação com JWT
- [ ] Upload de comprovantes (fotos/PDFs)
- [ ] Notificações por email
- [ ] Dashboard com métricas
- [ ] Exportação de relatórios (PDF/Excel)
- [ ] Integração com sistemas de pagamento
- [ ] Testes unitários e de integração

---

## 👨‍💻 **Autor**

**Marcos Alexander**  
Desenvolvedor Backend | Java & Spring Boot  

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/seu-perfil)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/MarcosAlexx)

---

## 📄 **Licença**

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

## 🤝 **Contribuições**

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fork o projeto
2. Criar uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'feat: adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abrir um Pull Request

---

⭐ **Se este projeto te ajudou, deixe uma estrela!**
