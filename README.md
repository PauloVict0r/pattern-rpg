# Pattern RPG

Um RPG de console desenvolvido em **Java**, com foco no estudo e aplicação de **Padrões de Projeto (Design Patterns)**. O jogador explora uma masmorra em andares progressivos, enfrenta inimigos em combate por turnos, coleta itens e pode salvar o progresso.

---

## Estrutura do Projeto

```
PatternRPG/src/main/java/org/pattern/rpg/
├── application/          # Camada de aplicação (orquestração)
│   ├── GameManager.java      # Fachada principal do jogo
│   └── InventoryManager.java
├── domain/               # Camada de domínio (regras de negócio)
│   ├── battle/               # Template Method de combate
│   ├── builder/              # Builder + Director para criaturas
│   ├── entity/               # Entidades do jogo (Player, Enemy, NullEnemy)
│   ├── factory/              # Factories de inimigos e itens
│   ├── item/                 # Itens usáveis e equipáveis (Decorator)
│   ├── stats/                # Bônus de atributos
│   └── weapon_strategy/      # Estratégias de armas
├── infrastructure/       # Camada de infraestrutura
│   ├── database/             # Singleton de conexão com SQLite
│   └── repository/           # Repositório de saves
└── presentation/         # Camada de apresentação
    ├── menu/                 # Menus do jogo
    └── ui/                   # Interface de console
```

---

## Funcionalidades

- **Combate por turnos** contra 1 a 3 inimigos por andar
- **Progressão de dificuldade**: novos inimigos desbloqueados a partir do andar 6
- **Sistema de inventário** com capacidade de 4 itens
- **Saves persistentes** com SQLite (com suporte a permadeath)
- **Equipamentos**: armaduras que modificam atributos do personagem dinamicamente
- **9 armas** com mecânicas de ataque distintas
- **Ranking** de pontuação (High Scores)

---

## Padrões de Projeto Implementados

### Facade — `GameManager`

**Arquivo:** [`application/GameManager.java`](PatternRPG/src/main/java/org/pattern/rpg/application/GameManager.java)

O `GameManager` implementa o padrão **Facade**, fornecendo uma interface simplificada para todo o subsistema do jogo. Ele esconde a complexidade de coordenar o loop de jogo, criação de personagens, gerenciamento de saves, loot e navegação entre andares, expondo métodos de alto nível como `iniciarAplicacao()`, `orquestrarNovoJogo()` e `continuarJogo()`.

```java
// Exemplo: o GameManager orquestra todos os subsistemas em um único método
public void orquestrarNovoJogo(String nomeJogador, String equipamentoEscolhido) {
    PlayerBuilder builder = new PlayerBuilder(Player::new);
    PlayerDirector director = new PlayerDirector();
    director.basePlayer(builder);
    builder.setName(nomeJogador);
    builder.setWeapon(mapearArma(equipamentoEscolhido));
    Player player = builder.getResult();
    loopDeJogo(player, nomeJogador, equipamentoEscolhido);
}
```

---

### Builder — `CreatureBuilder`, `EnemyBuilder`, `PlayerBuilder`

**Arquivos:** [`domain/builder/`](PatternRPG/src/main/java/org/pattern/rpg/domain/builder/)

O padrão **Builder** é usado para construir criaturas (Player e Enemy) de forma incremental, separando a lógica de construção da representação final. A interface `CreatureBuilder` define os passos de construção. `PlayerBuilder` e `EnemyBuilder` são implementações concretas que aceitam um `Supplier<T>` para decidir qual classe concreta instanciar, tornando o sistema flexível.

```java
// Interface comum de construção
public interface CreatureBuilder {
    void reset();
    void setName(String name);
    void setHP(int hp);
    void setDefense(int defense);
    void setAttack(int attack);
    void setCriticalChance(double criticalChance);
    void setWeapon(WeaponStrategy weapon);
}

// Uso: construindo um Player com nome e arma personalizados
PlayerBuilder builder = new PlayerBuilder(Player::new);
builder.setHP(100);
builder.setAttack(5);
builder.setName("Herói");
Player player = builder.getResult();
```

---

### Director — `EnemyDirector`, `PlayerDirector`

**Arquivos:** [`domain/builder/EnemyDirector.java`](PatternRPG/src/main/java/org/pattern/rpg/domain/builder/EnemyDirector.java), [`domain/builder/PlayerDirector.java`](PatternRPG/src/main/java/org/pattern/rpg/domain/builder/PlayerDirector.java)

O padrão **Director** define "receitas" prontas de construção usando um Builder. `PlayerDirector` configura os atributos base de um novo personagem. `EnemyDirector` define três tipos de inimigo — **Lacaio (Minion)**, **Campeão (Champion)** e **Chefe (Boss)** — aplicando multiplicadores de atributos e armas correspondentes ao tipo.

```java
public class EnemyDirector {
    public void makeMinion(CreatureBuilder builder) {
        applyModifier(builder, "(Lacaio)", 0.8, new ShortSwordStrategy());
    }
    public void makeChampion(CreatureBuilder builder) {
        applyModifier(builder, "(Campeão)", 1.5, new LongSwordStrategy());
    }
    public void makeBoss(CreatureBuilder builder) {
        applyModifier(builder, "(Chefe)", 3.0, new DragonBladeStrategy());
    }
}
```

---

### Factory — `EnemyFactory`, `ItemFactory`

**Arquivos:** [`domain/factory/EnemyFactory.java`](PatternRPG/src/main/java/org/pattern/rpg/domain/factory/EnemyFactory.java), [`domain/factory/ItemFactory.java`](PatternRPG/src/main/java/org/pattern/rpg/domain/factory/ItemFactory.java)

O padrão **Factory** centraliza a criação de objetos. `EnemyFactory.createEnemy(String type)` instancia o inimigo correto com base em uma string (com suporte a nomes em pt-BR e en-US), delegando ao `EnemyBuilder` e ao `EnemyDirector` para definir o tipo (minion/champion/boss) de forma aleatória. `ItemFactory.createItem(String type)` faz o mesmo para itens, retornando um `NullItem` em caso de tipo inválido.

```java
// EnemyFactory integra Builder + Director internamente
private static Enemy createWithBuilder(Supplier<Enemy> supplier) {
    EnemyBuilder builder = new EnemyBuilder(supplier);
    double chance = Math.random();
    EnemyDirector director = new EnemyDirector();
    if      (chance <= 0.05) director.makeBoss(builder);
    else if (chance <= 0.20) director.makeChampion(builder);
    else if (chance <= 0.50) director.makeMinion(builder);
    return builder.getResult();
}
```

**Inimigos disponíveis:** Goblin, Esqueleto, Lobo, Hollow, Vampiro, Dragão  
**Itens disponíveis:** Poção de Vida, Poção de Força, Poção de Defesa

---

### Decorator — `WearableDecorator`

**Arquivo:** [`domain/item/wearable/WearableDecorator.java`](PatternRPG/src/main/java/org/pattern/rpg/domain/item/wearable/WearableDecorator.java)

O padrão **Decorator** é usado para equipar armaduras no personagem sem alterar sua classe base. `WearableDecorator` implementa `Entity` e envolve outra `Entity`, somando bônus de atributos (`StatsBonus`) de forma transparente. Múltiplos decorators podem ser empilhados.

```java
public abstract class WearableDecorator implements Entity, Item {
    private final Entity wrappedEntity;
    private final StatsBonus bonus;

    @Override
    public int getDefense() {
        return wrappedEntity.getDefense() + bonus.defense(); // bônus somado dinamicamente
    }
}

// Armaduras concretas: IronArmor, GoldenArmor, ShadowArmor, DragonArmor, NormalArmor, ShadowArmor...
Entity playerComArmadura = new IronArmor(playerBase);
```

---

### Singleton — `ConnectionDB`

**Arquivo:** [`infrastructure/database/ConnectionDB.java`](PatternRPG/src/main/java/org/pattern/rpg/infrastructure/database/ConnectionDB.java)

O padrão **Singleton** garante que exista apenas uma instância da conexão com o banco de dados SQLite durante toda a execução do jogo. A instância é criada de forma preguiçosa (lazy) e o método `getInstance()` é sincronizado para segurança em ambientes multithread.

```java
public class ConnectionDB {
    private static ConnectionDB instance;

    private ConnectionDB() { /* inicializa driver JDBC */ }

    public static synchronized ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }
}
```

---

### Template Method — `Battle` e `TurnBattle`

**Arquivos:** [`domain/battle/Battle.java`](PatternRPG/src/main/java/org/pattern/rpg/domain/battle/Battle.java), [`domain/battle/TurnBattle.java`](PatternRPG/src/main/java/org/pattern/rpg/domain/battle/TurnBattle.java)

O padrão **Template Method** define o esqueleto do algoritmo de combate na classe abstrata `Battle`, deixando os detalhes para a subclasse `TurnBattle`. O método `startBattle()` é `final` e não pode ser sobrescrito — apenas os passos internos (`setup`, `nextTurn`, `executeTurn`, `isOver`, `finish`) são implementados pela subclasse.

```java
// Classe abstrata: define o algoritmo fixo de combate
public abstract class Battle {
    public final void startBattle() {   // template method — não pode ser sobrescrito
        setup();
        while (!isOver()) {
            Entity current = nextTurn();
            executeTurn(current);
        }
        finish();
    }

    protected abstract void setup();
    protected abstract Entity nextTurn();
    protected abstract void executeTurn(Entity creature);
    protected abstract boolean isOver();
    protected abstract void finish();
}
```

---

### 8. ⚔️ Strategy — `WeaponStrategy`

**Arquivos:** [`domain/weapon_strategy/`](PatternRPG/src/main/java/org/pattern/rpg/domain/weapon_strategy/)

O padrão **Strategy** permite trocar o algoritmo de ataque do personagem em tempo de execução. A interface `WeaponStrategy` define o contrato, e cada arma concreta implementa sua própria lógica de dano, crítico e efeitos especiais.

```java
public interface WeaponStrategy {
    int attack(Entity target, int damageStat, double critStat);
    int weaponDamage();
}
```

**Armas implementadas:**

| Arma | Classe | Características |
|---|---|---|
| Espada Comum | `SwordStrategy` | Balanceada, dano + crítico |
| Espada Longa | `LongSwordStrategy` | Alto dano base |
| Espada Curta | `ShortSwordStrategy` | Rápida, dano menor |
| Machado de Batalha | `AxeStrategy` | Alto dano, sem crítico especial |
| Arco Longo | `BowStrategy` | Dano à distância |
| Arco Forte | `StrongBowStrategy` | Dano elevado |
| Adaga Furtiva | `DaggerStrategy` | Alta chance de crítico |
| Cajado Mágico | `StaffStrategy` | Dano mágico |
| Lâmina do Dragão | `DragonBladeStrategy` | Arma mais poderosa |
| Soco | `PunchStrategy` | Sem arma (fallback) |

---

### Null Object — `NullEnemy`

**Arquivo:** [`domain/entity/enemy/NullEnemy.java`](PatternRPG/src/main/java/org/pattern/rpg/domain/entity/enemy/NullEnemy.java)

O padrão **Null Object** substitui o uso de `null` por um objeto com comportamento neutro. `NullEnemy` é retornado pela `EnemyFactory` quando o tipo solicitado é inválido ou desconhecido, evitando `NullPointerException` e dispensando verificações de nulo em toda a lógica de combate. O mesmo princípio é aplicado com `NullItem` na `ItemFactory`.

```java
public class NullEnemy extends Enemy {
    public NullEnemy() {
        this.setName("Inimigo Inexistente");
        this.setHp(0);
        this.setAttack(0);
        this.setDefense(0);
        this.setCriticalChance(0.0);
    }
}

// Na EnemyFactory:
public static Enemy createEnemy(String type) {
    if (type == null) return new NullEnemy(); // Null Object — sem NPE
    switch (type.toLowerCase()) { ... }
}
```

---

## Diagrama de Interação dos Padrões

```
┌─────────────────────────────────────────┐
│           GameManager (Facade)          │
│   orquestra todo o fluxo do jogo        │
└──────────┬──────────────────────────────┘
           │
     ┌─────▼──────┐     ┌─────────────────┐
     │ PlayerDir. │     │  EnemyFactory   │
     │ (Director) │     │   (Factory)     │──┐
     └─────┬──────┘     └────────┬────────┘  │
           │                     │           │
     ┌─────▼──────┐     ┌────────▼────────┐  │ usa
     │PlayerBuilder│    │  EnemyBuilder   │◄─┘
     │  (Builder) │     │   (Builder)     │
     └─────┬──────┘     └────────┬────────┘
           │                     │ tipo definido por
     ┌─────▼──────┐     ┌────────▼────────┐
     │   Player   │     │  EnemyDirector  │
     │+ WeaponStrat│    │   (Director)    │
     │ (Strategy) │     └─────────────────┘
     └─────┬──────┘
           │ equipado com
     ┌─────▼──────┐     ┌─────────────────┐
     │ IronArmor/ │     │  ConnectionDB   │
     │ GoldenArmor│     │  (Singleton)    │
     │ (Decorator)│     └─────────────────┘
     └────────────┘
           │
     ┌─────▼──────────────────────────────┐
     │       Battle (Template Method)      │
     │  setup → nextTurn → executeTurn    │
     │       → isOver → finish            │
     └─────────────────────────────────────┘
                    NullEnemy (Null Object)
                 retornado quando tipo inválido
```

---

## Tecnologias

- **Java 17+**
- **SQLite** (via JDBC) — persistência de saves e ranking
- **Maven** — gerenciamento de dependências

## ▶️ Como Executar

```bash
# Na raiz do projeto PatternRPG
mvn compile
mvn exec:java -Dexec.mainClass="org.pattern.rpg.Main"
```

Ou importe o projeto em uma IDE como **IntelliJ IDEA** e execute a classe `Main.java`.