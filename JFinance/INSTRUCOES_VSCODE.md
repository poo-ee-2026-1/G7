# Instruções para Execução no VS Code

Para garantir que o projeto funcione corretamente no VS Code, siga estes passos:

1.  **Estrutura de Pastas**: O VS Code espera que o código fonte esteja dentro de uma pasta `src`. A estrutura deve ser:
    ```
    projeto/
    └── src/
        ├── model/
        │   └── (arquivos .java)
        ├── persistencia/
        │   └── (arquivos .java)
        ├── relatorio/
        │   └── (arquivos .java)
        └── src/
            ├── Main.java
            └── InterfaceGrafica.java
    ```

2.  **Extensões Recomendadas**:
    *   Instale o "Extension Pack for Java" da Microsoft no VS Code.

3.  **Configuração do Pacote**:
    *   As classes `Main` e `InterfaceGrafica` estão no pacote `src`.
    *   As classes de modelo estão no pacote `model`.
    *   As classes de relatório estão no pacote `relatorio`.
    *   As classes de banco de dados e exportação estão no pacote `persistencia`.

4.  **Dependência SQLite**:
    *   Esta versão usa SQLite para salvar os registros financeiros em um banco local.
    *   O arquivo `lib/sqlite-jdbc-3.51.1.0.jar` deve permanecer na pasta `lib`.
    *   A configuração `.vscode/settings.json` já adiciona automaticamente os arquivos `.jar` da pasta `lib` ao classpath do projeto.

5.  **Como Executar**:
    *   Abra a pasta raiz do projeto no VS Code.
    *   Abra o arquivo `Main.java`.
    *   Clique no botão "Run" (ícone de play) no canto superior direito ou pressione `F5`.

6.  **Persistência por e-mail**:
    *   Ao fazer login, o e-mail informado é usado para carregar as transações já cadastradas anteriormente.
    *   Novos registros e remoções são salvos automaticamente no banco `controle_financeiro.db`.
    *   Se o usuário entrar novamente com o mesmo e-mail, os registros antigos aparecem sem necessidade de recadastro.

7.  **Exportação para Excel**:
    *   A interface possui o botão **Exportar Excel**.
    *   O botão gera um arquivo `.csv` compatível com Excel contendo os lançamentos do usuário logado.

8.  **Dica**: Se o VS Code mostrar erros de "package name does not match", clique na lâmpada (Quick Fix) e selecione a opção para corrigir o nome do pacote ou mover o arquivo para a pasta correspondente.

Para detalhes técnicos da implementação, consulte o arquivo `PERSISTENCIA_E_EXCEL.md`.
