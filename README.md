# Тестовое задание для БалтИнфоКом
Разбивает входной CSV файл на группы с одинаковыми значениями в соответствующих колонках.

Для сборки проекта выполните:
```bash
mvn package
```
Для запуска:
```bash
java -jar target/baltinfocom-1.0-SNAPSHOT.jar <csv file> <out file> <number of columns>
```
Например:
```bash
java -jar target/baltinfocom-1.0-SNAPSHOT.jar lng.csv out.txt 3
```
