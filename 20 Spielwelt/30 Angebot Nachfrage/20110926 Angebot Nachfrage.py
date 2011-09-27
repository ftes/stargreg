import math

def absatz(eingabe):
    summe = 0
    for i in range(0, len(eingabe)):
        eingabe[i] = 1 / (eingabe[i] ** 3)
        summe += eingabe[i]
    for i in range(0, len(eingabe)):
        eingabe[i] = int(eingabe[i] / summe * 100)
    return eingabe

def preis(basis, delta, nachfrage, konjunktur):
    basis = float(basis)
    delta = float(delta)
    nachfrage = float(nachfrage)
    konjunktur = float(konjunktur)
    temp = delta + delta / ((2 / delta + 1) ** (2 * nachfrage * delta))
    temp = (basis - delta + 2 * delta ** 2 / temp) * konjunktur
    return temp

def preise(basen, deltas, nachfragen, konjunkturen):
    summe = 0.
    for i in nachfragen: summe += i
    durchschnitt = summe / len(nachfragen)
    for i in range(0, len(nachfragen)):
        nachfragen[i] = (nachfragen[i] - durchschnitt) / durchschnitt
    temp = []
    for i in range(0, len(nachfragen)):
        temp.append(preis(basen[i], deltas[i], nachfragen[i], konjunkturen[i]))
    return temp
