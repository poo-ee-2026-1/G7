# Instruções para Execução no VS Code

Para garantir que o projeto funcione corretamente no VS Code, siga estes passos:

1.  **Estrutura de Pastas**: O VS Code espera que o código fonte esteja dentro de uma pasta `src`. A estrutura deve ser:
    ```
    projeto/
    └── src/
        ├── model/
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

4.  **Como Executar**:
    *   Abra a pasta `src` (ou a pasta raiz que contém `src`) no VS Code.
    *   Abra o arquivo `Main.java`.
    *   Clique no botão "Run" (ícone de play) no canto superior direito ou pressione `F5`.

5.  **Dica**: Se o VS Code mostrar erros de "package name does not match", clique na lâmpada (Quick Fix) e selecione a opção para corrigir o nome do pacote ou mover o arquivo para a pasta correspondente.
