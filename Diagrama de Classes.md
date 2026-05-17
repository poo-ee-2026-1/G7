<img width="3120" height="6468" alt="image" src="https://github.com/user-attachments/assets/b100476f-4379-409c-8128-6997f888ac2f" />

Classes: Main, Usuario, Transacao, TransacaoFixa, TransacaoVariavel, GerenciadorFinanceiro, RelatorioTexto, Categoria, NaturezaFixa, InterfaceGrafica.

Encapsulamento:

•Usuario: atributos nome, email são private.
•Transacao: atributos id, descricao, valor, categoria, data, metodoPagamento são private.
•GerenciadorFinanceiro: atributos usuario, transacoes são private.
•TransacaoFixa: atributo natureza é private.
•TransacaoVariavel: atributos parcelado, numeroParcelas são private.

Abstração:

•Transacao: Classe abstrata com métodos abstratos getValorLiquido() e getTipo().

Herança:

•TransacaoFixa herda de Transacao.
•TransacaoVariavel herda de Transacao.
Polimorfismo (Sobrescrita):
•getValorLiquido() e getTipo() sobrescritos em TransacaoFixa e TransacaoVariavel.

Polimorfismo (Sobrecarga):

•Construtores de TransacaoVariavel (um completo com parcelamento e um simplificado).

Associação:

•RelatorioTexto → GerenciadorFinanceiro
•InterfaceGrafica → GerenciadorFinanceiro
•Transacao → Categoria
•Main → Usuario, GerenciadorFinanceiro, InterfaceGrafica

Agregação:

•GerenciadorFinanceiro agrega Transacao (lista de transações).

Composição:

•GerenciadorFinanceiro contém Usuario.
•TransacaoFixa contém NaturezaFixa.

Modificadores de Acesso:

•private para atributos, public para métodos, static para contadorId em Transacao e main em Main.

Construtores:

•Transacao (inicializa atributos base).
•TransacaoVariavel (dois construtores).
•GerenciadorFinanceiro (recebe Usuario).
•RelatorioTexto (recebe GerenciadorFinanceiro).
•InterfaceGrafica (recebe GerenciadorFinanceiro).

Destrutores:

•Não se aplicam diretamente em Java (gerenciado pelo Garbage Collector).

Classes Abstratas:

•Transacao.
