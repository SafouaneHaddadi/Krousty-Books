## Krousty-Books - Bibliothèque Full Stack


### changement de Rachid

##  Objectif du projet

Cette application permet de :
- Consulter les livres disponibles,
- Emprunter et rendre des livres,
- Gérer les stocks,
- Administrer la bibliothèque (ajout, modification, suppression des livres).

Les rôles utilisateurs sont gérés via Auth0 (admin / utilisateur simple).

## Prérequis

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows/Mac/Linux)
- Git
- PowerShell / Terminal
- Compte GitHub (pour CI/CD)

## Lancement 

- git clone https://github.com/SafouaneHaddadi/Krousty-Book.git 
- cd Krousty-Books
- docker compose up --build -d

L’application sera disponible sur :

- Frontend : http://localhost:4200
- Backend API : http://localhost:8082
- PostgreSQL : localhost:5432

## Structure du projet

- Krousty-Books/
- │
- ├── backend/  # API Spring Boot (Java 21)   
- ├── frontend/ # Application Angular
- ├── .dockerignore 
- ├── .env          
- ├── .gitignore
- ├── docker-compose.yml
- └── README.md

## Commandes utiles
- docker compose up --build -d # démarrer tout
- docker compose down --volumes 
- docker compose logs -f backend
- docker compose logs -f frontend
- docker compose up --build -d
- docker compose ps
- docker builder prune -f # nettoyer le cache Docker (si bug)

## Flux Git 
- main → version stable
- develop → intégration continue
- feature/... → développement de fonctionnalités

 Note :  Les branches main et develop sont protégées.
Les PR doivent être approuvées avant merge.

## Critères de qualité (DoD)

- Code fonctionnel et compilé
- Tests unitaires passés
- API testée avec Postman
- Front connecté au backend
- PR approuvée et mergée dans develop

## Méthodologie

- Méthode : Agile / Scrum allégée
- Durée : ? sprints (? semaines)
- Livrable par sprint : fonctionnalité complète testée (DoD)
- Outil de gestion : GitHub Projects

## CI/CD

Une pipeline GitHub Actions sera exécutée à chaque Pull Request :
- Lancement des tests unitaires
- ...
  
PR bloquée si échec des test unitaires