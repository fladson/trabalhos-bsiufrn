require 'socket'									# biblioteca para sockets do ruby
require 'net/http'                # biblioteca para protocolo HTTP dp ruby

remote_host, remote_port = ARGV		# possibilidade de passar o ip e porta do servidor via linha de comando

# endereço ip e porta do servidor caso não tenha passado nenhum por linha de comando
if ARGV.empty? then remote_host, remote_port = '10.142.0.244', 8084  end  

listen_port = 8081                									# porta que o proxy está escutando

proxy = TCPServer.open(listen_port) 								# abrindo o servidor proxy para receber as requisições do cliente
puts "Servidor proxy aberto esperando por conexoes..."
loop {                              								# loop infinito
	
	cliente = proxy.accept           									# aceitando o cliente
  puts "Um cliente se conectou"       	            # informando ao proxy que um cliente se conectou

  cliente.puts "Conexao estabelecida..."						# informando ao cliente que a conexão com o servidor foi estabelecida
	webserver =  Net::HTTP.new(remote_host, remote_port)						# abrindo uma conexão HTTP com o servidor
	
  cliente.print "HTTP/1.1 200/OK\r\nContent-type:text/html\r\n\r\n" # mensagem de status para o cliente

  # recebendo as requisições do cliente, Métodos HTTP
  file_name, host_name = ""
  loop do
    linha1 = cliente.gets   # recebendo parametros do terminal do cliente
    # uso de expressão regular que deixa apenas o arquivo a ser requisitado
    trimmedrequest = linha1.gsub(/GET/, '').gsub(/\ HTTP.*/, '')    # GET /meuip.php HTTP/1.1 => /meuip.php
    file_name = trimmedrequest.chomp                                # retira o '\n\r' da string se houver
    
    linha2 = cliente.gets
    # uso de expressão regular para retirar o host dos parametros do terminal do cliente  
    host_name = linha2.gsub(/GET(.*)/,'').gsub(/Host:\ /,'') # GET /meuip.php HTTP/1.1 => /meuip.php
                                                             # Host: 10.142.0.244 => 10.142.0.244
    
    linha3 = cliente.gets    
    break if linha3 == "\r\n" # sair no \r\n
  end									
  
  cliente.puts("Voce requisitou o arquivo: #{file_name}") # informando o arquivo requisitado ao cliente
  
  request = Net::HTTP::Get.new("#{file_name}") # fazendo a requisição do arquivo
  response = webserver.request(request)        # recebendo o arquivo do servidor
  cliente.puts(response)                       # mostrando o arquivo ao cliente
  puts "arquivo #{file_name} enviado.\n"       # mensagem de status
  cliente.close                                # fechando a conexão com o cliente
}