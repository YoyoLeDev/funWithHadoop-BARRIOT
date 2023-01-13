## Connexion à la VM
```bash
ssh student@192.168.17.121
```
MDP : Welcome

## Envoi de donnée vers la VM
```bash
scp FunWithMapReduce.jar student@192.168.17.121:~/
```

## Execution du fichier jar dans la VM
```bash
start-all.sh
hdfs dfs -put games.csv .
hadoop jar FunWithMapReduce-1.0-SNAPSHOT.jar org.edu.reduce.map.fun.david.App games.csv out
```

## Vérifier si le programmer a fonctionné
```bash
hdfs dfs -cat out/part-r-00000
```

# COMPRENDRE

- Mapper =>  