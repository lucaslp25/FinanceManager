# Bem-vindo(a) ao Finance Manager! 

**Esta é uma aplicação funcional de gerenciamento de finanças, onde você se cadastra, recebe uma carteira e coloca seus ganhos e suas despesas com o tempo, com direito a extrato, dashboards e definição de metas para melhorar sua organização financeira.**

## Stacks usadas 🛠️
### Backend
- Java 21 LTS
- Spring Boot 3.5.8
- Maven
- Spring security & JWT
- Lombok
- Java Mail Sender
- Flyway 
  
### Frontend
- Angular 20.1.0
- Bootstrap Icons

### Infra 
- Docker
- Nginx
- H2 & Postgres Database

<br>
<br>

# 🚀 Funcionalidades
## 🔐 Segurança & Autenticação
*Cadastro de Usuário: Com validação de e-mail obrigatória (Token via link).*
*Login Seguro: Autenticação via JWT (JSON Web Token).*
*Recuperação de Senha: Fluxo completo de "Esqueci minha senha" com token temporário e expiração de segurança.*
*Proteção de Rotas: Frontend protegido via Guards do Angular.*

## 💸 Gestão Financeira
*Carteira (Wallet): Criação automática de carteira ao registrar.*
*Transações: Registro de Entradas e Saídas.*
*Categorias: Classificação de gastos.*
*Metas Financeiras: Definição e acompanhamento de objetivos.*

## ⚙️ Infraestrutura & DevOps
*Docker Compose: Orquestração completa (API, Frontend, Banco, Mailpit e PgAdmin).*
*Flyway: Versionamento de banco de dados (Migrations).*
*Mailpit: Servidor SMTP fake para capturar e-mails em ambiente de desenvolvimento sem expor credenciais reais.*
*Perfis do Spring: Configuração dinâmica para dev e prod.*

<br>
<br>

# 📦 Como Rodar o Projeto

## Pré-requisitos
*Docker e Docker Compose instalados.*

**Essa é uma aplicação feita totalmente Dockerizada, você não precisa instalar Java, nem node, nem nenhuma stack usada, exceto o Docker**
**Se quiser desenvolver em cima do projeto, dai necessitará de todas as stacks para rodar no modo Dev**

## Primeiro Passo
- Instalar o Docker na sua maquina.

## Segundo Passo
- Clonar o projeto em sua maquina, e duplicar o arquivo .env.example que está na raiz do projeto - Copie o arquivo e deixe deixe o nome dele como `.env` sem o .example -> e Coloque suas credenciais conforme o passo a passo do arquivo de exemplo.

## Terceiro passo
- Abra o terminal na raiz do projeto (mesmo lugar que está o arquivo .compose.yaml)

## Quarto passo
- Com o terminal aberto, digite --> `docker compose up --build`

## Quinto passo
- Espere o Docker fazer todo o build, na primeira vez é mais demorado pois está baixando todas dependências, depois que inicializar todos os container, vá até o seu navegador e digite apenas `localhost`

## Sexto passo
- Pronto --> Divirta-se usando a aplicação

<br>
<br>

# Úteis 💡
- Acesse `localhost:5050` para ver o pgadmin, coloque as credencias que estão no arquivo .env que você criou e registre um servidor para ver o banco de dados da aplicação *Os dados para cadastrar um servidor estão todos no arquivo .env e no compose.yaml também*
- Acesse `localhost:8025` para ver a caixa de entrada do MailPit
- Acesse `localhost:4200` para ver a aplicação quando estiver em modo de desenvolvimento.

<br>
<br>

# 🤝 Contribuição
## Contribuições e sugestões são sempre bem-vindas!

- Faça um Fork do projeto.

- Crie uma Branch para sua Feature (git checkout -b feature/MinhaFeature).

- Faça o Commit (git commit -m 'Adicionando nova feature').

- Faça o Push (git push origin feature/MinhaFeature).

- Abra um Pull Request.

<br>
<br>

👨‍💻 Autor
Feito com 💚 por Lucas da Silveira Lopes.
