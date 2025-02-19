# Projet Etudiant Openclassrooms n°8 – Développez une solution en microservices pour votre client

<img src="/preview.png" alt="Logo de l'application">

<h1 align="center">Medilabo</h1>


## Fonctionnalités

**En tant qu'organisateur** :
- Ajout et modification des informations des patients
- Consultation des informations des patients

**En tant que praticien** :
- Ajout de notes sur les patients
- Consultation des informations, des notes et du rapport de risque de diabète des patients


## Technologies Utilisées

Ce projet utilise les technologies suivantes :

- **Java** : Le langage de programmation principal pour les services backend.
- **Spring Boot** : Un framework pour construire des applications basées sur Java.
- **Gradle** : Un outil d'automatisation de build utilisé pour gérer les dépendances et construire le projet.
- **NPM** : Node Package Manager, utilisé pour gérer le framework UI Tailwind CSS.
- **Postgres** : Utilisé pour la gestion des bases de données.
- **MongoDB** : Utilisé pour la gestion des bases de données NoSQL.
- **Docker** : Utilisé pour la conteneurisation des services.


## Installation

Pour configurer et exécuter le projet localement, suivez ces étapes :

1. **Clonez le dépôt GitHub** :

```bash
git clone https://github.com/Xenophee/Medilabo-Thymeleaf.git
```

2. **Configurez les variables d'environnement dans le fichier `.env` (facultatif)** :

```ini
POSTGRES_DB=your_database_name
POSTGRES_USER=your_database_user
POSTGRES_PASSWORD=your_database_password
```

3. **Exécutez les conteneurs Docker** :
Assurez-vous que Docker est installé et en cours d'exécution sur votre machine. Ensuite, démarrez les services en utilisant Docker Compose :
```sh
docker compose up
```

4. **Connectez-vous au shell de MongoDB pour mettre en place la base de données par défaut** :
```sh
docker exec -it medilabo-note-db-1 mongosh
```

5. **Exécutez le script pour initialiser les données de MongoDB** :
```sh
load('/docker-entrypoint-initdb.d/mongo-init.js')
```


5. **Accédez à l'application** :
   L'application sera accessible à l'adresse suivante : [http://localhost:8081](http://localhost:8081)


6. **S'authentifier** :
   Utilisez les identifiants (hautement sécurisés) suivants pour vous connecter à l'application :
   - **Organisateur** : 
     - Identifiant : `organisateur`
     - Mot de passe : `ok`
   - **Praticien** :
     - Identifiant : `praticien`
     - Mot de passe : `ok`


<br>
<hr>

## Point sur le Green Code

### 1. Qu'est-ce que le Green Code ?

Le Green Code ou éco-conception logicielle est un ensemble de bonnes pratiques à adopter pour réduire l'impact environnemental des applications numériques en optimisant leur conception, leur développement et leur utilisation. Il s'agit de réduire la consommation d'énergie et les ressources nécessaires liées à l'utilisation des applications, sans pour autant compromettre les performances.

### 2. Principes généraux du Green Code

- **Optimisation des algorithmes** : Réduire la complexité des calculs pour limiter la consommation CPU et RAM.
- **Optimisation des requêtes** : Réduire le nombre de requêtes serveur pour limiter la consommation de bande passante.
- **Modularité** : Éviter la redondance de code et favoriser le réemploi de composants logiciels.
- **Choix des infrastructures** : Privilégier les serveurs et data centers écologiques.

### 3. Exemples pratiques de Green Code

- **Optimisation des images et des vidéos** : Réduire la taille des images pour limiter la consommation de bande passante, utiliser des formats compressés et des techniques de lazy loading.
- **Optimisation des requêtes http** : Minifier et regrouper le code JavaScript et CSS, mettre en cache les ressources statiques pour éviter de les télécharger à chaque visite.
- **Optimisation des bases de données** : Limiter la duplication des données pour réduire l'espace de stockage, optimiser les requêtes et les index pour améliorer les performances.
- **Choix des technologies** : Privilégier les langages de bas niveau pour les tâches lourdes et éviter de recourir à des bibliothèques lourdes quand on peut se contenter d'un simple script en natif.

