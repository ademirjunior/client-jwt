Implementação de um CRUD uqe possui o token JXT na parte de segurança.

Para gerar o secrete, foi usando o seguinte comento no node
node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"
