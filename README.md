# Reward system
Clojure library modelada para gerenciar um sistema de recompensa.

Funcionalidades:
* Adição de novos convites
* Adição de novos convites com leitura de arquivo
* Listagem do ranking de recompensa

## Arquitetura
Arquitetura baseada na linguagem Clojure

Tecnologias:
* Clojure: Linguagem base de desenvolvimento
* Libraries: 
 * Leiningen: Gerenciador de dependências
 * Ring: Web Application Library 
 * Compojure: Library de rotas para Ring
 * Http-kit: Servidor HTTP
* Datomic Database

## Usage
* Configurar Datomic Database (Versão Free)
 * Pré-requisito: Java 7 e Maven instalados e variáveis de ambiente configuradas
 * Passos:
  * Realizar o download da versão [0.9.5372](https://my.datomic.com/downloads/free)
  * Ir a pasta datomic-free-0.9.5372 e executar comando mvn install:install-file -DgroupId=com.datomic -DartifactId=datomic-free -Dfile=datomic-free-0.9.5372.jar -DpomFile=pom.xml
  * Criando transação no DB (Linux) 
   * cp config/samples/free-transactor-template.properties mw.propreties
   * bin/transactor mw.propreties
   
* Configurar projeto
 * [Instalar Leiningen](https://github.com/technomancy/leiningen)
 * Acessar raiz do diretório do projeto
 * Executar lein run
 
