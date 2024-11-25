[:section#critique
 [:h2 "Critique"]
 [:p "On the one hand, making the changelog normalized data lends itself to straightforward anaylsis web display. On the other hand, is it a strategic mistake to require that every change be a member of an enumerated set? Is having a change-kind " [:code ":other"] " a red flag? It's fairly typical for one of my commits be include an update to the source code, accompanined by some additional unit tests, and some kind of change in the documentation. At the moment, I would categorize it as a " [:code ":altered-function"] ", with the implicit understanding that a person reading the changelog would care about that the most, and that the accompanying unit tests and docs are subordinate."]

 [:p "But the rigidity causes concern, and perhaps only extended use will reveal if it's a deal-breaker."]]