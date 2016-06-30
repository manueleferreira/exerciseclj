# Reward system
Clojure library modelada para gerenciar um sistema de recompensa.

Funcionalidades:
* Adição de novos convites 
 * *URL* http://localhost:3000/ranking/x/y , x and y são inteiros
* Adição de novos convites com leitura de arquivo
* Listagem do ranking de recompensa 
 * *URL* http://localhost:3000/ranking

## Arquitetura
Arquitetura baseada na linguagem Clojure

Tecnologias:
* Clojure: Linguagem base de desenvolvimento
* Libraries: 
 * Leiningen: Gerenciador de dependências
 * Ring: Web Application Library 
 * Compojure: Library de rotas para Ring
 * Http-kit: Servidor HTTP

## Usage
* Configurar projeto
 * [Instalar Leiningen](https://github.com/technomancy/leiningen)
 * Acessar raiz do diretório do projeto
 * Executar lein run
 
* Executar testes unitários
 * lein test 
