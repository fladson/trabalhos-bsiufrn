Para rodar o código é preciso ter o Ruby devidamente instalado.

Apenas entre em algum terminal e entre no diretório onde o arquivo se encontra.
Dentro do diretório apenas faça:

ruby proxy.rb 10.142.0.143 8080 ou

Lembrando que esse endereço e porta são fictícios, você deve colocar o host do servidor e a porta que 
ele está escutando por conexões HTTP. 

Se preferir, você pode editar o código colocar o host e porta na seguinte linha:

if ARGV.empty? then remote_host, remote_port = 'HOST_SERVIDOR_AQUI', PORTA_SERVIDOR  end 

Após devidamente editado, se o host e porta forem válidos apenas faça:

ruby proxy.rb

by Fladson Thiago and Kelvin Kirk