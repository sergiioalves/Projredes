# Dado de Somar

<div>
<img src="https://github.com/user-attachments/assets/13060b7a-90f6-4313-bd0e-bb790e2ad46d" width="250px" />
</div>

## Integrantes do grupo
- Sérgio Emmanuel Alves
- Denilson da Silva Pereira
- Lindembergson Carlos Monteiro Moreno
  
## Descrição do Jogo

Dado de Somar é um jogo simples onde os jogadores rolam dados virtuais e somam os valores obtidos buscando alcançar a maior pontuação possível em um número limitado de rodadas. Ganha aquele que obtive primeiro o maior valor da soma.

# Como Executar o Projeto

## Pré-requisitos
Java JDK instalado no seu computador.

## Passos para Executar

Clone o repositório:
  
   <pre><font color="#12488B"><b>https://github.com/sergiioalves/Projredes.git</b></font></pre>
   
Navegue até a pasta do projeto:

  <pre><font color="#12488B"><b>Ex.: C:\Projredes</b></font></pre>

Execute o jogo:

- Abra o terminal na pasta citada acima.<br>
- Verifique se você possui o jdk instalado:
 <pre><font color="#12488B"><b>java -version</b></font></pre> 
 - Caso não, faça o download da versão compativel com seu sistema e instale no link:
 <pre><font color="#12488B"><b>https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html</b></font></pre>
 <br>
 Após os passos anteriores, vamos continuar com o processo de execução:
 <br>

  - Na pasta em que você clonou o projeto: 
  <pre><font color="#12488B"><b>Ex.: C:\Projredes</b></font></pre>
  - Vamos executar o seguinte comando no terminal:
  <pre><font color="#12488B"><b>javac src/jogo/*.java</b></font></pre>
  - Após esse passo, vamos abrir o servidor do jogo. No terminal digitamos o seguinte comando:
  <pre><font color="#12488B"><b>java -cp src jogo.Servidor</b></font></pre>
  <br>
  Para abrir o cliente para os jogadores, faremos os seguintes passos:
  <br>
  - Vamos abrir o terminal na pasta raiz do projeto, e no terminal digitamos o seguinte comando:
  <pre><font color="#12488B"><b>java -cp src jogo.Cliente</b></font></pre>
  Obs.: Para cada cliente, devemos abrir um novo terminal. Não devemos fechar o terminal do servidor, nem os dos outros jogadores.
  

# Detalhes do Jogo
## Início:

Ao entrar no jogo, você verá mensagens de boas-vindas e de espera por outros jogadores. Para que a partida inicie, são necessários 4 jogadores e suas respectivas confirmações.

## Rodadas:
Em cada rodada, o jogador da vez digita ```y``` e rola um dado virtual.

A pontuação total é acumulada ao longo das rodadas.

## Objetivo:

O objetivo é obter a maior pontuação com a soma dos dados após 3 rodadas.

**Obs:** Caso haja empate, o critério de desempate se dará pelo jogador que rolou o dado primeiro.

## Final do Jogo:

Após todas as rodadas, o jogo exibe a pontuação final e informa o se o jogador ganhou ou perdeu.

O jogo também pergunta se o jogador deseja jogar novamente ou sair do jogo.

