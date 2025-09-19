# ecommerce-microservices-java-spring-rabbitmq
Projeto consiste em dois microsserviços principais — warehouse (armazém) e storefront (vitrine) — que se comunicam tanto de forma síncrona (via HTTP) quanto assíncrona (via RabbitMQ).

## Melhorias na Resiliência e Tolerância a Falhas

**1. Circuit Breaker com RabbitMQ:**

Para lidar com a indisponibilidade temporária do microsserviço `storefront`, implementado um Circuit Breaker utilizando o RabbitMQ.  Quando o `warehouse` tenta criar um produto e o `storefront` está indisponível, em vez de falhar, o `warehouse` publica uma mensagem na fila do RabbitMQ. Quando o `storefront` retorna, ele consome a mensagem e processa o produto.  Isso garante que a criação de produtos não seja bloqueada por interrupções temporárias no `storefront`.

**2. Dead-Letter Queue (DLQ):**

Configurada uma Dead-Letter Queue (DLQ) para capturar mensagens que não puderam ser processadas pelo consumidor do `storefront`, mesmo após o número máximo de tentativas. Isso evita a perda de mensagens e permite que sejam analisadas e tratadas posteriormente.  A DLQ fornece informações valiosas para depuração e monitoramento, permitindo identificar a causa raiz dos erros e tomar as medidas corretivas necessárias.

**4. Idempotência:**

Para garantir a consistência dos dados em cenários de retentativas ou mensagens duplicadas, implementada a idempotência no consumidor do `storefront`.  Isso significa que, mesmo que a mesma mensagem seja processada várias vezes, o resultado final será o mesmo.  No caso da criação de produto, verificamos se o produto já existe pelo ID antes de tentar salvá-lo novamente, evitando a criação de registros duplicados.


**Benefícios:**

* **Maior disponibilidade do sistema:** O sistema continua funcionando mesmo com falhas temporárias em componentes individuais.
* **Consistência de dados:** A idempotência e a DLQ garantem a consistência dos dados, mesmo em cenários de erro.
* **Melhor experiência do usuário:**  As operações continuam funcionando sem interrupções para o usuário, mesmo com falha em outro microsserviço.
* **Facilidade de depuração e monitoramento:** A DLQ e os logs facilitam a identificação e resolução de problemas.
