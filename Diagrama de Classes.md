<img width="3120" height="1356" alt="diagrama_classes_jfinance" src="https://github.com/user-attachments/assets/23f1d7ad-743a-463b-bb84-8bb97e1053ab" />


Classes:

• Main, Usuario, Transacao, TransacaoFixa, TransacaoVariavel, GerenciadorFinanceiro, RelatorioTexto, Categoria, NaturezaFixa, InterfaceGrafica, BancoDadosFinanceiro, ExportadorExcelCSV.

Classes Abstratas:

• Transacao: Atua como a base para todos os lançamentos, definindo atributos comuns e métodos que as subclasses devem obrigatoriamente implementar.

Encapsulamento:

• Usuario: Atributos nome e email são private.
• Transacao: Atributos id, descricao, valor, categoria, data, metodoPagamento são private.
• GerenciadorFinanceiro: Atributos usuario, bancoDados e a lista transacoes são private.
• TransacaoFixa: Atributo natureza é private.
• TransacaoVariavel: Atributos parcelado e numeroParcelas são private.
• BancoDadosFinanceiro: Atributo URL é private static final.

Abstração:

• Transacao: Classe abstrata que define o contrato para transações através dos métodos abstratos getValorLiquido() e getTipo(). A lógica de como o valor é calculado é abstraída para as subclasses.

Herança:

• TransacaoFixa herda de Transacao.
• TransacaoVariavel herda de Transacao.
• InterfaceGrafica herda de JFrame (Swing).

Polimorfismo:

• Sobrescrita (Override): Métodos getValorLiquido() e getTipo() são implementados de formas diferentes em TransacaoFixa e TransacaoVariavel. O método toString() também é sobrescrito em várias classes.

• Sobrecarga (Overload):
• TransacaoVariavel: Possui três construtores (um completo com parcelamento, um com ID para o banco e um simplificado).
• TransacaoFixa: Possui dois construtores (um para criação nova e outro para carregamento do banco com ID).

Associação:

• InterfaceGrafica → GerenciadorFinanceiro: A interface usa o gerenciador para realizar operações.
• RelatorioTexto → GerenciadorFinanceiro: O relatório consulta o gerenciador para obter dados.
• Transacao → Categoria: Cada transação está associada a uma categoria do enum.
• Main → Usuario, GerenciadorFinanceiro, InterfaceGrafica: A classe principal coordena a criação e ligação desses objetos.
• ExportadorExcelCSV → Usuario, Transacao: Depende desses objetos para gerar o arquivo CSV.

Agregação:

• GerenciadorFinanceiro agrega Usuario: O usuário existe independentemente do gerenciador, mas o gerenciador mantém uma referência ao usuário logado.
• GerenciadorFinanceiro agrega Transacao: O gerenciador mantém uma List<Transacao>, onde as transações podem existir e ser manipuladas como uma coleção.

Composição:

• GerenciadorFinanceiro contém BancoDadosFinanceiro: O gerenciador cria e gerencia o ciclo de vida da persistência.
• TransacaoFixa contém NaturezaFixa: A natureza (Receita/Despesa) é um atributo intrínseco e essencial da transação fixa.

Modificadores de Acesso:

• private: Para atributos e métodos auxiliares (ex: conectar() em BancoDadosFinanceiro).
• public: Para métodos de operação e construtores.
• protected: Para o construtor de Transacao, permitindo acesso às subclasses.
• static: Para o contadorId em Transacao e o método main em Main.

Construtores:

• Transacao: Inicializa os atributos base (ID, descrição, valor, etc.).
• TransacaoVariavel: Múltiplos construtores para diferentes cenários de criação.
• GerenciadorFinanceiro: Recebe um Usuario obrigatoriamente no momento da criação.
• InterfaceGrafica: Recebe o GerenciadorFinanceiro para vincular a UI à lógica.
• BancoDadosFinanceiro: Construtor que inicializa a conexão e as tabelas.

Destrutores:

• Não se aplicam diretamente em Java. A memória é gerenciada automaticamente pelo Garbage Collector. O projeto não utiliza o método finalize().
