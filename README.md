# Dado de Somar

<div>
<img src="https://github.com/user-attachments/assets/13060b7a-90f6-4313-bd0e-bb790e2ad46d" width="250px" />
</div>

## Integrantes do grupo
- Sérgio
- Denilson
- Lindembergson
  
## Descrição do Jogo

Dado de Somar é um jogo simples onde os jogadores rolam dados virtuais e somam os valores obtidos buscando alcançar a maior pontuação possível em um número limitado de rodadas. 

# Como Executar o Projeto

## Pré-requisitos
Java instalado no seu computador.

## Passos para Executar

Clone o repositório:
  
   <pre><font color="#12488B"><b>https://github.com/sergiioalves/Projredes.git</b></font></pre>
   
Navegue até a pasta do projeto:

  <pre><font color="#12488B"><b>Projredes/src/jogo</b></font></pre>

Execute o jogo:
- Abra o terminal na pasta citada acima<br>
Windows: Para abrir o terminal na pasta no caso o PowerShell, clica shift e o botão direito do mouse, depois clica em "Abrir janela do PowerShell aqui".<br>

Observação: Você precisará abrir 5 abas do terminal/PowerShell

1. Ao abrir o primeiro terminal digite: ```java Servidor.java```
2. Após iniciado o servidor no passo anterior, vamos abrir um cliente/aba do terminal para cada jogador. Em cada terminal vamos digitar: ```java Cliente.java```
3. Após isso o jogo irá pedir a confirmação de todos os jogadores para poder começar a partida

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

O jogador pode decidir jogar novamente ou sair do jogo.

