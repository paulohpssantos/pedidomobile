# PedidoMobile 📱

Um aplicativo Android para gerenciamento de pedidos móvel, desenvolvido em Java com interface Material Design.

## 📋 Sobre o Projeto

PedidoMobile é um aplicativo Android que permite criar, gerenciar e acompanhar pedidos de forma prática e intuitiva. O app oferece funcionalidades completas para registro de clientes, produtos e controle de status dos pedidos.

## ✨ Funcionalidades

### 📝 Gestão de Pedidos
- **Criar novos pedidos** com numeração automática
- **Adicionar informações do cliente** (Razão Social, CNPJ, Telefone, Email)
- **Definir data do pedido** com seletor de data integrado
- **Controle de status** (Aberto/Finalizado)

### 🛍️ Gestão de Produtos
- **Adicionar produtos** ao pedido
- **Definir quantidade e preço** por produto
- **Cálculo automático** do total por item
- **Editar ou remover** produtos do pedido
- **Validação de campos** monetários e numéricos

### 📊 Resumo e Relatórios
- **Resumo automático** do pedido (quantidade de produtos, itens, total)
- **Lista de pedidos** com status e informações principais
- **Visualização detalhada** de cada pedido

### 💾 Persistência de Dados
- **Banco de dados local** SQLite com SugarORM
- **Salvamento automático** dos dados
- **Recuperação de pedidos** salvos

## 🚀 Tecnologias Utilizadas

- **Android SDK 28** (API Level 28)
- **Java 8**
- **Material Design Components** 1.3.0
- **SugarORM** 1.5 (Mapeamento Objeto-Relacional)
- **RecyclerView** para listas dinâmicas
- **CardView** para interface moderna
- **ConstraintLayout** para layouts responsivos

## 🛠️ Requisitos do Sistema

### Desenvolvimento
- **Android Studio** 3.0 ou superior
- **Java Development Kit (JDK)** 8 ou superior
- **Android SDK** com API Level 28
- **Gradle** 4.10.1

### Dispositivo
- **Android 6.0** (API Level 23) ou superior
- **Espaço de armazenamento**: 50MB mínimo

## ⚙️ Instalação e Configuração

### 1. Clone o Repositório
```bash
git clone https://github.com/paulohpssantos/pedidomobile.git
cd pedidomobile
```

### 2. Abrir no Android Studio
1. Abra o Android Studio
2. Selecione "Open an existing Android Studio project"
3. Navegue até a pasta do projeto e selecione-a
4. Aguarde o sync do Gradle

### 3. Configurar Emulador ou Dispositivo
- **Emulador**: Crie um AVD com Android 6.0+ no AVD Manager
- **Dispositivo físico**: Ative o modo desenvolvedor e depuração USB

### 4. Executar o Aplicativo
```bash
# Via Android Studio: clique em "Run" ou Shift+F10
# Via linha de comando:
./gradlew assembleDebug
./gradlew installDebug
```

> **Nota**: Para o primeiro build, é necessária conexão com a internet para download das dependências do Gradle e Android.

## 📱 Como Usar

### Tela Principal
- **Novo Pedido**: Inicia a criação de um novo pedido
- **Lista de Pedidos**: Visualiza todos os pedidos salvos

### Criando um Pedido
1. **Preencha os dados do cliente**:
   - Razão Social (obrigatório)
   - CNPJ (com validação automática)
   - Telefone e Email
   
2. **Selecione a data do pedido**

3. **Adicione produtos**:
   - Nome do produto
   - Quantidade
   - Preço unitário
   - Total calculado automaticamente

4. **Salve o pedido** para manter na base local

5. **Finalize o pedido** quando concluído

### Gerenciando Produtos
- **Adicionar**: Use o botão "+" para novos produtos
- **Editar**: Toque no produto na lista para editar
- **Remover**: Use o ícone de lixeira com confirmação

## 🏗️ Arquitetura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)**:

```
app/src/main/java/com/example/pedidomobile/
├── model/           # Modelos de dados (Entity classes)
│   ├── Cabecalho.java    # Cabeçalho do pedido
│   ├── Produto.java      # Produtos do pedido
│   └── Resumo.java       # Resumo do pedido
├── view/            # Activities (Interface do usuário)
│   ├── MainActivity.java      # Tela principal
│   ├── PedidoActivity.java    # Tela de criação/edição
│   └── ListaPedidosActivity.java  # Lista de pedidos
├── controller/      # Lógica de negócio
│   └── PedidoControler.java   # Controlador principal
├── adapter/         # Adaptadores para RecyclerView
│   ├── ItemListAdapter.java
│   └── PedidoListAdapter.java
└── util/           # Utilitários e helpers
    ├── Utils.java
    ├── MascaraValorMonetario.java
    └── MascaraCampo.java
```

### Modelos de Dados

#### Cabecalho
```java
- Long numPedido
- String data
- String razaoSocial
- String cnpj
- String telefone
- String email
- List<Produto> produtos
- Resumo resumo
```

#### Produto
```java
- Long idPedido
- String produto
- int quantidade
- Double preco
- Double total
```

#### Resumo
```java
- Long idPedido
- String situacao
- int qtdProduto
- int qtdItem
- Double total
```

## 🎨 Interface do Usuário

O aplicativo utiliza **Material Design** com:
- **Cores primárias** definidas no tema
- **Ícones vetoriais** para ações (novo, editar, excluir, salvar, etc.)
- **Cards** para exibição de dados
- **Snackbars** para feedback do usuário
- **Dialogs** para confirmações
- **RecyclerView** para listas eficientes
- **Logo personalizado** na tela principal
- **Background gradiente** para visual moderno

### Elementos Visuais Inclusos
- 🏠 **Logo do aplicativo** (`pedidologo.png`)
- ➕ **Ícone Novo** - Criar pedidos
- 📝 **Ícone Editar** - Modificar dados
- 🗑️ **Ícone Excluir** - Remover itens
- 💾 **Ícone Salvar** - Gravar alterações
- ✅ **Ícone Finalizar** - Concluir pedidos
- 📋 **Ícone Lista** - Visualizar pedidos
- ⬅️ **Ícone Voltar** - Navegação
- ❌ **Ícone Cancelar** - Cancelar ações

## 🔧 Configuração do Banco de Dados

O app utiliza **SugarORM** com configuração automática:
- **Nome do banco**: `pedido_mobile.db`
- **Versão**: 3
- **Localização**: Armazenamento interno do app
- **Log de queries**: Habilitado para debug

## 🧪 Build e Testes

### Build de Debug
```bash
./gradlew assembleDebug
```

### Build de Release
```bash
./gradlew assembleRelease
```

### Executar Testes
```bash
# Testes unitários
./gradlew test

# Testes instrumentados
./gradlew connectedAndroidTest
```

## 📦 Dependências Principais

```gradle
dependencies {
    // UI Components
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'androidx.cardview:cardview:1.0.0'
    
    // Database
    implementation 'com.github.satyan:sugar:1.5'
    
    // Testing
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}
```

## 🔐 Validações Implementadas

- **CNPJ**: Validação matemática completa com dígitos verificadores
- **Email**: Validação de formato usando Patterns do Android
- **Campos monetários**: Formatação e validação de valores
- **Campos obrigatórios**: Verificação antes de salvar
- **Quantidade mínima**: Pelo menos um produto por pedido

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 👨‍💻 Autor

**Paulo Santos** - [paulohpssantos](https://github.com/paulohpssantos)

## 🤝 Contribuição

Contribuições são bem-vindas! Para contribuir:

1. **Fork** o projeto
2. **Crie** uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. **Commit** suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. **Push** para a branch (`git push origin feature/MinhaFeature`)
5. **Abra** um Pull Request

### Diretrizes de Contribuição
- Mantenha o código limpo e bem documentado
- Siga as convenções de naming Java
- Teste suas alterações antes de submeter
- Atualize a documentação quando necessário

## 📞 Suporte

Se você encontrar algum problema ou tiver dúvidas:
- **Issues**: Abra uma issue no GitHub
- **Documentação**: Consulte este README
- **Código**: Analise os comentários no código fonte

---

**Desenvolvido com ❤️ para gestão eficiente de pedidos móveis**