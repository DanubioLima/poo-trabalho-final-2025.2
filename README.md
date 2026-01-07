# Rede Social - Trabalho Final POO

Sistema de rede social simples desenvolvido em Java com Swing, seguindo o padrão MVC.

## Funcionalidades Implementadas

- ✅ Cadastro de usuários
- ✅ Login e autenticação
- ✅ Criação de posts e comentários
- ✅ Sistema de curtidas
- ✅ Persistência de dados (serialização)
- ✅ Interface gráfica

## Como Executar

1. Compile o projeto:
```bash
javac -d bin -sourcepath src src/*.java src/**/*.java
```

2. Execute a aplicação:
```bash
java -cp bin Main
```

Ou use uma IDE como Eclipse ou IntelliJ IDEA.

## Persistência de Dados

Os dados são salvos automaticamente na pasta `dados/`:
- `usuarios.ser` - Lista de usuários
- `posts.ser` - Lista de posts

**Nota:** O sistema utiliza UUIDs para identificação única, eliminando a necessidade de salvar contadores. Cada entidade recebe um identificador único universal (UUID) automaticamente.