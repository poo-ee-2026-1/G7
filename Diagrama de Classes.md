<img width="1005" height="1840" alt="diagrama_classes" src="https://github.com/user-attachments/assets/cb9b4ebb-fcaa-4596-b848-b50ef79d7cc9" />



Classes: Main, Usuario, Transacao, TransacaoFixa, TransacaoVariavel, GerenciadorFinanceiro, RelatorioTexto, RelatorioCsv, Categoria, NaturezaFixa

Encapsulamento: Usuario (nome, email), Transacao (id, descricao, valor, categoria, data), GerenciadorFinanceiro (usuario, transacoes), TransacaoFixa (natureza), TransacaoVariavel (parcelado, numeroParcelas)

Abstração: Transacao (métodos abstratos getValorLiquido() e getTipo())

Herança: TransacaoFixa herda de Transacao, TransacaoVariavel herda de Transacao

Polimorfismo (sobrescrita): getValorLiquido() em TransacaoFixa e TransacaoVariavel, getTipo() em TransacaoFixa e TransacaoVariavel

Polimorfismo (sobrecarga): Construtores de TransacaoVariavel (construtor completo com parcelamento e construtor simplificado à vista)

Associação: RelatorioTexto → GerenciadorFinanceiro, RelatorioCsv → GerenciadorFinanceiro, Transacao → Categoria, Main → GerenciadorFinanceiro, Main → RelatorioTexto, Main → RelatorioCsv

Agregação: GerenciadorFinanceiro agrega Transacao (lista de transações)

Composição: GerenciadorFinanceiro compõe Usuario, TransacaoFixa compõe NaturezaFixa

Modificadores de acesso: private (todos os atributos), public (todos os métodos), static (contadorId em Transacao, sc em Main)

Construtores: Transacao (valida valor), TransacaoVariavel (dois construtores), GerenciadorFinanceiro (recebe Usuario), RelatorioTexto (recebe GerenciadorFinanceiro), RelatorioCsv (recebe GerenciadorFinanceiro)

Destrutores: Não se aplicam em Java — memória gerenciada pelo Garbage Collector. Recursos externos liberados via try-with-resources em RelatorioCsv

Classes abstratas: Transacao
