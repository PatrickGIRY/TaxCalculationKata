Feature: Imprimer la facture détaillée

  Scenario: Imprimer une facture détaillée avec des produits et des produits non taxés
    Given on paase commande de :
     """
     1 livre à 12.49
     1 CD musical à 14.99
     1 barre de chocolat à 0.85
     """
    When on imprime la facture détaillée
    Then on obtient :
      """
      1 livre : 12.49 1 CD musical : 16.49 1 barre de chocolat : 0.85 Montant des taxes : 1.50 Total : 29.83
      """

  Scenario: Imprimer une facture détaillée avec des produits importés
    Given on paase commande de :
     """
      1 boîte de chocolats importée à 10.00
      1 flacon de parfum importé à 47.50
     """
    When on imprime la facture détaillée
    Then on obtient :
      """
      1 boîte de chocolats importée : 10.50 1 flacon de parfum importé : 54.65 Montant des taxes : 7.65 Total : 65.15
      """

  Scenario: Imprimer une facture détaillée avec tous types de produits
    Given on paase commande de :
     """
      1 flacon de parfum importé à 27.99
      1 flacon de parfum à 18.99
      1 boîte de pilules contre la migraine à 9.75
      1 boîte de chocolats importés à 11.25
     """
    When on imprime la facture détaillée
    Then on obtient :
      """
      1 flacon de parfum importé : 32.19 1 flacon de parfum : 20.89 1 boîte de pilules contre la migraine : 9.75 1 boîte de chocolats importés : 11.85 Montant des taxes : 6.70 Total : 74.68
      """
