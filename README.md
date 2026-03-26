# pattern-rpg

1. Instale o Maven no seu SO(No meu que é baseado no debian foi assim):

```cmd
sudo apt update
sudo apt install maven
```

2. Para confirmar se deu tudo certo, digite(Caso SO semelhante ao meu):

```cmd
mvn -version
```

3. Para rodar o projeto:

```cmd
cd ./PatternRPG
mvn clean compile
mvn exec:java
```