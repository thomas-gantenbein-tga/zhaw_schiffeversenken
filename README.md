# Zusammenfassung Projekt „Battleships“ 

Team: Thomas Gantenbein, Ueli Krapf 

## Ausgangslage und Ziel 
Das Entwicklerteam hat keine Erfahrung in Java-Programmierung und hat sich deshalb eine gut eingrenzbare, konkrete Aufgabe gestellt: Entwickelt werden sollte eine Java-Version des Spiels Schiffeversenken. Das Programm soll lokal ausgeführt werden können, ein Mensch soll gegen einen Computergegner spielen können, der Computerspieler soll eine gewisse Intelligenz aufweisen und das Spielfeld soll flexibel gestaltet werden können (Grösse der Felder, Anzahl und Grösse der Schiffe). 

## Spielanleitung
Nach einem Doppelklick auf das JAR öffnet sich ein erstes Fenster. Darauf können die Grössen der Spielfelder eingegeben werden. 

Als Nächstes können die Schiffe auf dem eigenen Spielfeld platziert werden. Mit Klick auf das angezeigte Spielfeld kann das Heck des Schiffs platziert werden. Als Richtung kann aus den vier Himmelsrichtungen ausgewählt werden. Die Grösse des Schiffs wird als Zahl eingegeben. 

Über einen Knopf "Start game" kann das Spiel gestartet werden. Macht der Spieler auf den obigen Bildschirmen Angaben, die nicht vorgesehen oder nicht möglich sind (zum Beispiel ein Spielfeld mit mehr als 900 Quadraten), wird eine entsprechende Meldung ausgegeben. 

Durch Klicken auf das Spielfeld des Computers können Schüsse abgefeuert werden. Nach jedem Schuss gibt auch der Computer einen Schuss auf das Spielfeld des Spielers ab. Die Treffer werden mit einem roten Punkt markiert. Gesunkene Schiffe werden mit einem blauen Kreuz durchgestrichen. 

Wenn der Computer oder der Spieler alle Schiffe versenkt hat, wird eine entsprechende Meldung ausgegeben. Falls der Spieler verliert, werden die unversehrten Schiffsteile des Computers aufgedeckt. Wird das Spielfenster vergrössert oder verkleinert, werden auch die Spielfelder entsprechend skaliert. 

## Design 
Das Game-Objekt (package „model“) repräsentiert die Spiellogik. Es nimmt „Schüsse“ in Form von einem Coordinate-Objekt entgegen. Das Game-Objekt verändert aufgrund der Schüsse den Zustand der PlayField-Objekte (Package „data“), das wiederum Ship-Objekte hält. Objekte, die das Display-Interface implementieren, können sich bei dem Game-Objekt als Observer registrieren und werden vom Game-Objekt benachrichtigt, wenn das Game-Objekt die Schüsse verarbeitet hat. Das Game-Objekt könnte in Zukunft weitere Spielregeln einführen. Das Coordinate-Objekt könnte Extras (wie ein Extra-Leben oder ein Dreifach-Schuss) enthalten. Durch die Trennung von Data und View könnte ein Webinterface auf die Programmlogik im Model- und auf die Daten im Data-Package zugreifen.  

## Eine Auswahl von möglichen Verbesserungen 
Die Grafik der Anwendung lässt zu wünschen übrig. Ein nächstes Mal würde das Entwicklerteam wahrscheinlich auf vorgefertigte Grafiken zurückgreifen, statt die Formen programmatisch aus Ellipsen und Rechtecken zu zeichnen. Eine Speichern- und Ladenfunktion wäre leicht zu implementieren, indem das Game-Objekt serialisiert wird, die Umsetzung im GUI und das Exception-Handling allerdings würde wiederum einige Zeilen Code erfordern. Der Computer könnte seine sondierenden Schüsse intelligent abgeben, indem er prüft, ob das kleinste noch vorhandene Schiff im leeren Bereich überhaupt noch Platz hat. Und schliesslich könnten die Eingabefelder auf dem Start-Screen so programmiert werden, dass sie gar keine falschen Eingaben zulassen und das Feedback gleich neben den Textfeldern ausgeben.  