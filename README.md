# Projet Spring Boot - Gestion Commerciale

Ce projet est structuré par **services**, chaque service regroupant plusieurs entités.  
Chaque service a sa **branche dédiée** pour faciliter le développement, le test unitaire et l'intégration continue avec Jenkins.

---

## Structure des services et branches

### 1. Fournisseur Service
- **Entités :**
  - `Fournisseur.java`
  - `CategorieFournisseur.java`
  - `DetailFournisseur.java`
  - `SecteurActivite.java`
- **Branche Git :** `feature/fournisseur-service`

### 2. Produit Service
- **Entités :**
  - `Produit.java`
  - `CategorieProduit.java`
- **Branche Git :** `feature/produit-service`

### 3. Facture & Reglement Service
- **Entités :**
  - `Facture.java`
  - `DetailFacture.java`
  - `Reglement.java`
  - `Operateur.java`
- **Branche Git :** `feature/facture-service`

### 4. Stock Service
- **Entités :**
  - `Stock.java`
- **Branche Git :** `feature/stock-service`

---

## Commandes Git pour créer et pousser les branches

```bash
# Assurez-vous d'être sur la branche principale
git checkout main

# Fournisseur Service
git checkout -b feature/fournisseur-service
git push -u origin feature/fournisseur-service

# Produit Service
git checkout main
git checkout -b feature/produit-service
git push -u origin feature/produit-service

# Facture & Reglement Service
git checkout main
git checkout -b feature/facture-service
git push -u origin feature/facture-service

# Stock Service
git checkout main
git checkout -b feature/stock-service
git push -u origin feature/stock-service
