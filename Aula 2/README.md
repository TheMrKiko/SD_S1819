GRUPO 14

O protocolo que escolhemos consiste em 4 linhas:

1. "SD2019"
2. inteiro (mensagem)
3. data atual
4. linha vazia a dizer que acabou a mensagem

O cliente envia o inteiro, inicialmente 49;
O servidor recebe e responde com 49 + 1 (=50);
O cliente recebe o 50 e termina a ligação;
o servidor termina a ligação;
