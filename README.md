# Rede Social - Trabalho Final POO

Sistema de rede social simples desenvolvido em Java com Swing, seguindo o padrão MVC.

## Estrutura do Projeto

```
src/
├── model/          # Classes do modelo (MVC)
│   ├── Usuario.java
│   ├── Post.java
│   ├── Comentario.java
│   ├── Conteudo.java (abstrata)
│   └── RedeSocial.java
├── view/           # Interfaces gráficas (MVC)
│   ├── TelaLogin.java
│   ├── TelaCadastro.java
│   ├── TelaPrincipal.java
│   ├── PainelPost.java
│   └── DialogNovoPost.java
├── controller/     # Controladores (MVC)
│   ├── ControladorPrincipal.java
│   ├── GerenciadorUsuarios.java
│   ├── GerenciadorPosts.java
│   └── GerenciadorPersistencia.java
├── util/        # Utilitários
│   ├── Excecoes.java
│   └── GeradorID.java
└── Main.java     # Classe principal
```

## Funcionalidades Implementadas

- ✅ Cadastro de usuários
- ✅ Login e autenticação
- ✅ Criação de posts
- ✅ Sistema de curtidas
- ✅ Persistência de dados (serialização)
- ✅ Interface gráfica completa

## Conceitos OO Implementados

1. **Herança**: `Conteudo` (abstrata) → `Post` e `Comentario`
2. **Classes Abstratas**: `Conteudo`
3. **Sobrescrita**: Métodos `toString()`, `equals()`, `obterResumo()`
4. **Sobrecarga**: Construtores múltiplos em `Usuario` e `Post`
5. **Encapsulamento**: Classe `Usuario` totalmente encapsulada
6. **Atributo de Classe**: Métodos estáticos em `GeradorID` e `RedeSocial`
7. **Polimorfismo**: Lista de `Conteudo` contendo `Post` e `Comentario`
8. **Coleções**: `ArrayList<Usuario>`, `ArrayList<Post>`, `ArrayList<Comentario>`
9. **Interface Gráfica**: Swing completo
10. **Exceções**: Tratamento em cadastro, login e persistência
11. **Manipulação de Arquivos**: Serialização para salvar/carregar dados

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

### Comentários (Comentario.java e PainelPost.java)
- A classe `Comentario` já está estruturada
- O método `comentarPost()` em `PainelPost.java` precisa ser implementado
- Criar um diálogo similar ao `DialogNovoPost` para comentários
- Integrar a exibição de comentários no `PainelPost`

## Requisitos do Trabalho

✅ Todos os requisitos foram atendidos:
- Sistema orientado a objetos
- Herança (Conteudo → Post, Comentario)
- Classes abstratas (Conteudo)
- Sobrescrita de método
- Sobrecarga de método
- Construtores
- Encapsulamento (Usuario)
- Atributo de classe (métodos estáticos em GeradorID e RedeSocial)
- Polimorfismo
- Manipulação de coleções (ArrayList)
- Interface gráfica (Swing)
- Tratamento de exceções
- Manipulação de arquivos (serialização)

