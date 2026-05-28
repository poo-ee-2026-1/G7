# Persistência por e-mail e integração com Excel

Este projeto foi atualizado para que os registros financeiros deixem de existir apenas em memória. A partir desta versão, o sistema utiliza **SQLite** como banco de dados local e associa cada transação ao **e-mail informado no login**. Dessa forma, quando o usuário entra novamente com o mesmo e-mail, seus gastos e ganhos anteriores são carregados automaticamente.

## Visão geral da solução

A aplicação continua sendo uma aplicação Java/Swing simples, executável pelo VS Code. A diferença principal é que agora existe uma camada de persistência separada das telas e das classes de modelo. O banco fica salvo no arquivo `controle_financeiro.db`, criado automaticamente na pasta em que o programa é executado.

| Recurso | Implementação adotada | Motivo |
|---|---|---|
| Banco de dados local | SQLite com JDBC | É leve, salva tudo em um único arquivo `.db` e não exige servidor separado. |
| Identificação do usuário | E-mail digitado no login | Permite recuperar os registros já cadastrados pelo mesmo usuário. |
| Tabelas criadas | `usuarios` e `transacoes` | Separa os dados de login dos lançamentos financeiros. |
| Integração com Excel | Exportação CSV com separador `;` | O Excel abre CSV diretamente, sem exigir bibliotecas grandes como Apache POI. |

## Estrutura do banco de dados

O banco possui duas tabelas. A tabela `usuarios` guarda o e-mail e o nome. A tabela `transacoes` guarda os dados de cada lançamento e inclui a coluna `email_usuario`, que funciona como vínculo com o usuário logado.

| Tabela | Campo principal | Função |
|---|---|---|
| `usuarios` | `email` | Identifica cada usuário do sistema. |
| `transacoes` | `id` | Identifica cada transação para listagem e remoção. |
| `transacoes` | `email_usuario` | Garante que cada usuário carregue apenas os próprios registros. |
| `transacoes` | `tipo`, `natureza`, `parcelado`, `numero_parcelas` | Permite reconstruir corretamente receitas fixas, despesas fixas e despesas variáveis parceladas. |

## Classes adicionadas ou alteradas

A classe `persistencia.BancoDadosFinanceiro` concentra a comunicação com o SQLite. Ela cria as tabelas automaticamente, salva ou atualiza o usuário, carrega as transações pelo e-mail, salva novas transações e remove transações existentes.

A classe `persistencia.ExportadorExcelCSV` gera um arquivo `.csv` compatível com Excel. O arquivo exportado contém e-mail, nome, ID, data, tipo, descrição, categoria, método de pagamento, valor original, valor líquido, parcelamento e número de parcelas.

| Classe | Alteração realizada |
|---|---|
| `GerenciadorFinanceiro` | Agora inicializa o banco, carrega transações pelo e-mail e persiste inclusões e remoções automaticamente. |
| `Transacao` | Recebeu um construtor protegido para recriar registros vindos do banco mantendo o mesmo ID. |
| `TransacaoFixa` e `TransacaoVariavel` | Receberam construtores com ID para reconstrução dos dados persistidos. |
| `InterfaceGrafica` | Recebeu o botão **Exportar Excel**, que salva os registros do usuário em CSV. |

## Como executar no VS Code

O projeto agora depende do arquivo `lib/sqlite-jdbc-3.51.1.0.jar`, que já foi incluído na pasta `lib`. Se estiver usando o VS Code com a extensão Java, mantenha a configuração `.vscode/settings.json`, pois ela informa ao editor que o JAR deve entrar no classpath.

Também é possível compilar manualmente pelo terminal na raiz do projeto:

```bash
javac -encoding UTF-8 -cp "lib/sqlite-jdbc-3.51.1.0.jar" -d out $(find src -name '*.java')
```

Para executar no Linux ou macOS:

```bash
java -cp "out:lib/sqlite-jdbc-3.51.1.0.jar" src.Main
```

Para executar no Windows, troque `:` por `;` no classpath:

```bash
java -cp "out;lib/sqlite-jdbc-3.51.1.0.jar" src.Main
```

## Como usar na aplicação

Ao abrir o sistema, informe nome e e-mail. Se for a primeira vez com aquele e-mail, a lista aparecerá vazia. Depois de cadastrar receitas e despesas, os dados serão salvos automaticamente no banco. Ao fechar e abrir o programa novamente usando o mesmo e-mail, os registros anteriores serão exibidos sem necessidade de recadastramento.

Para exportar os dados para Excel, clique em **Exportar Excel**. O sistema abrirá uma janela para escolher onde salvar o arquivo `.csv`. Depois, basta abrir esse arquivo no Excel.

## Observações importantes

Esta implementação identifica o usuário apenas pelo e-mail, pois o projeto original não possui senha nem autenticação avançada. Em um sistema real, seria recomendável adicionar senha com criptografia, recuperação de conta e validação de e-mail. Para o objetivo acadêmico do projeto, entretanto, o e-mail é suficiente para separar os registros de cada usuário.

A exportação em CSV foi escolhida por ser mais simples e adequada ao projeto atual. Caso o requisito seja gerar planilhas `.xlsx` com abas, fórmulas e estilos, a evolução natural seria adicionar Apache POI e transformar o projeto em Maven ou Gradle para gerenciar as dependências.
