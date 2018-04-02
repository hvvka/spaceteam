package com.hania;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface PlayerClient extends Remote {
    /*
    – każda reprezentuje osobny panel, ale powinny być do siebie podobne
  - każda zawiera te same typy przyrządów (dostępne i zablokowane)
  - przyrządy odpowiadają za równe funkcje, o różnych możliwych wartościach
  - realizacja ogólnej reprezentacji panelu, którą można w zależności od aplikacji, dowolnie dostosowywać
  - realizacja przy pomocy zaimplementowanego jednorazowo komponentu Java Beans
  - komponent:
    - nazwa panelu
    - kilka typów przyrządów (suwak, lista, radio buttons, text fields)
    - nazwy przyrządów
  - ziarno posiada:
    - właściwości wszystkich możliwych typów (proste, ograniczone, wiązane)
    - graficzną reprezentację
    - klasę opisową BeanInfo z klasami pomocnicznymi edytorów:
      - uwaga na metody: `getJavaInitializationString` i customizera (`setObject`; zmiana właściwości ziarenka)
     */

    /**
     * @param name  player's name
     * @param panel player's panel
     * @throws RemoteException if registry could not be contacted
     */
    void registerPlayer(String name, Panel panel) throws RemoteException;
}
