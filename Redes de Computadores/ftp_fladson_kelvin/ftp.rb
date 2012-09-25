# importando a biblioteca Higline
require File.expand_path(File.dirname(__FILE__) + '/highline/lib/highline/import')   
require 'net/ftp'         # biblioteca ftp do ruby
require 'socket'          # biblioteca socket do ruby, necessário para traduzir o dominio em endereço ip

remote_host,ftp_porta = ARGV		# o host ftp deve ser passado na chamada do arquivo

if ARGV[1].empty? then ftp_porta = 21 end # se nao for informado porta, será 21 por padrão

# informa erro caso nenhum parametro seja informado
if ARGV.empty? then puts "Nao foi possivel executar o arquivo.\r\nInforme um dominio correto." end

ftp = Net::FTP.new(remote_host) # criando a conexão http no host informado, quando tentei passar a porta como parametro deu erro
ip_address =  Socket::getaddrinfo(remote_host, 'ftp', nil, Socket::SOCK_STREAM)[0][3] # pegando o ip do dominio passado

# mensagens de status
puts "Conectado ao servidor #{remote_host} #{(ip_address)} na porta #{ftp_porta}
220 #{remote_host} FTP server ready."  # não encontrei forma de mostrar mensagens de status antes de informar login e senha

ftp.debug_mode = true # habilita impressão de todas as mensagens de status

user = ask ("Login: ") # recebendo o usuario do terminal
puts "---> USER #{user} \n331 Password required for #{user}."

pass = ask("Password: ") { |q| q.echo = false } # recebendo o pass do terminal escondendo os caracteres =D

ftp.login(user,pass) # fazendo o login com user e pass informados

loop do # loop infinito, sai no comando quit
	comando = ask( "ftp> ",lambda { |str| str.split(/ \s*/) } ) # separa cada palavra e coloca no array
	break if comando[0] == "quit"
	ftp.passive = true # entrando em modo passivo	
	case comando[0]
		when "ls"
			puts ftp.list('*')
		when "put" 
			ftp.put(comando[1])
		when "get"
			ftp.get(comando[1])
	end
end
puts "---> QUIT\n221 Goodbye."
ftp.close