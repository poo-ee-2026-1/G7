package model;

/**
 * Categorias disponíveis para classificar transações.
 * Basta adicionar novos valores aqui para criar novas categorias.
 */
public enum Categoria {
    SALARIO("Salário"),
    FREELANCER("Freelancer"),
    ALIMENTACAO("Alimentação"),
    TRANSPORTE("Transporte"),
    SAUDE("Saúde"),
    LAZER("Lazer"),
    EDUCACAO("Educação"),
    MORADIA("Moradia"),
    DESPESAS("Despesas"),
    OUTROS("Outros");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
