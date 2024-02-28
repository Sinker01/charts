## Ziel der Software

Ziel des Projektes soll es sein, den Fortschritt eines Prozesses grafisch darzustellen.
Dafür werden zwei Funktionen implementiert. Zum einen kann der Fortschritt in einem Graphen dargestellt werden.
Außerdem kann er in einer Flasche dargestellt werden, wo der bereits erzielte und der gewünschte Fortschritt verglichen wird und in Form eines Füllstandes dargestellt wird.

## Installation

Zur Installation sind zwei .jar-Dateien bereitgestellt, zu finden unter [out/artifacts](out/artifacts). Dort ist die [Graph.jar](out/artifacts/Graph.jar) und die [Bottle.jar](out/artifacts/Bottle.jar) in das gewünschte Verzeichnis zu kopieren.

Beim Starten ist es erforderlich, dass java installiert ist.

Beim Starten des Programms sucht das Programm nach einer config.ini. Dort sollten folgende Angaben gemacht werden:
```
Berg: [Name und Pfad der Datei für den Graph, relativ zur config.ini]
Flasche: [Name und Pfad der Datei für die Flasche, relativ zur config.ini]
Start: [Startzeitpunkt für den Graph, in dem Format hh:mm]
Ende: [Endzeitpunkt für den Graph, in dem Format hh:mm]
Intervall: [Zeit t in ms. Alle t ms wird der Graph oder die Flasche anhand der oben angegebenen Daten aktualisiert]
```

Die Datei für den Berg sollte in folgendem Format angegeben werden:
```
[1. Zeitpunkt hh:mm];[Wert zum Zeitpunkt]
...
[n. Zeitpunkt1 hh:mm];[Wert zum Zeitpunkt]
```

Die Datei für die Flasche sollte in folgendem Format angegeben werden:
```
[soll-Wert];[ist-Wert]
```