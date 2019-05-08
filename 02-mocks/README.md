# Projekt 2 - Arkadiusz Dąbrowski

[![Build Status](https://travis-ci.com/testowanieaplikacjijavaug/projekt2-arek-dabrowski.svg?token=UZTMumSDZ536n7nCVMDM&branch=master)](https://travis-ci.com/testowanieaplikacjijavaug/projekt2-arek-dabrowski)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8210de97c4614d8d9625059877a4465d)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=testowanieaplikacjijavaug/projekt2-arek-dabrowski&amp;utm_campaign=Badge_Grade)

## Projekt 3 (16 pkt)

Rozważmy grę w kółko i krzyżyk (lub czwórki czy statki) z poprzedniego projektu. Teraz dodajmy do tej gry bazę MongoDBz użyciem Jongo (patrz przykład wykorzystany w atrapach) Dopiszmy do niej odpowiednie wymagania:

*   Dodaj opcję umożliwiającą zapisanie posunięcia z numerem kolejki, pozycjami na planszy/mapie oraz ewentualnie symbol gracza (w przypadku gier).
*   Zapisuj każdy ruch w bazie danych i zapewnij to, że utworzenie nowej sesji spowoduje usunięcie starszych danych.

Pod ocenę będą brane pod uwagę następujące elementy:

*   (0.5 pkt) Kompilacja i uruchomienie bezbłędne projektu + konfiguracja TravisCi.
*   (2 pkt) Uwzględnienie powyższych wymagań.
*   (4 pkt) Przypadki testowe (uwzględniające wyjątki).
*   (3 pkt) Przetestowanie przy użyciu ręcznie stworzonych atrap (co najmniej 8 testów, różnych od pozostałych)
*   (2 pkt) Przetestowanie przy użyciu Mockito (co najmniej 8 testów, różnych od pozostałych).
*   (2 pkt) Przetestowanie przy użyciu EasyMock (co najmniej 8 testów, różnych od pozostałych).
*   (0.5 pkt) Pokrycie kodu (w przypadku ręcznie stworzonych atrap).
*   (1 pkt) Użycie różnych rodzaji atrap.
*   (1 pkt) Styl kodu.

Ponadto, jako punkty dodatkowe będą brane następujące elementy:

*   (2 pkt) Inne technologie dotyczące atrap, nie pokazywane na zajęciach (co najmniej po 5 testów każda z nich).
*   (1 pkt) Użycie GitFlow

Ponadto pod ocenę jest brane również: (Brak tych elementów: -1 pkt za podpunkt od obowiązkowej punktacji zadania!)

*   Historia projektu w repozytorium.
*   Różnorodne asercje (co najmniej 5 różnych).
*   Struktura projektu.
