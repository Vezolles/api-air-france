# api-air-france

Ce projet est développé avec:
- OpenJDK 17 (https://jdk.java.net)
- Spring Boot 2.7.18 (https://spring.io/projects/spring-boot)
- Maven 3.9.6 (https://maven.apache.org)

## Pré-requis

Il est nécessaire d'installer OpenJDK sur votre poste et de définir les variables d'environnement `JAVA_HOME`.

Cette variable doit pointer vers votre installation locale d'OpenJDK.

Il est nécessaire d'ajouter à votre variable d'environnement Path une entrée vers `%JAVA_HOME\bin`.

## Génération

Pour packager l’application, on réalise une génération Maven sans profil.

Il n'est pas nécessaire d'installer maven en local, l'outillage est disponible dans les sources.

Cette génération réalise :
-	La compilation de toutes les dépendances des projets
-	La réalisation de tous les tests unitaires 
-	La génération du war final

Pour lancer la génération :
-	Dans une invite de commandes Windows, allez dans le dossier api-air-france
-	Exécutez la commande : `.\mvnw clean install`

Le résultat de la génération se trouve dans le projet api-air-france sous le dossier target.

Dans ce dossier est contenu le fichier war contenant l'applicatif ainsi que sa javadoc associée.

## Couverture de test

Par défaut, les tests unitaires sont lancés avec les commandes maven.

Les résultats de la couverture sont consultables depuis le fichier `target\site\jacoco\index.html`.

La génération de la couverture de test n'est disponible **qu'après une génération**.

## Démarrage de l'application

Le démarrage de l'application se fait via Maven.

Pour lancer le démarrage de l'application :
-	Dans une invite de commandes Windows, allez dans le dossier api-air-france
-	Exécutez la commande : `.\mvnw spring-boot:run`

L'application est disponible après le démarrage Spring Boot.

## Urls du projet

Les apis sont accessibles aux adresses :
-	Consutation d'un utilisateur : `GET http://localhost:8080/user/<nom d'utilisateur recherché>`
-	Création d'un utilisateur : `POST http://localhost:8080/user`, en body le json de l'utilisateur à ajouter
-	Suppression d'un utilisateur : `DELETE http://localhost:8080/user/<nom d'utilisateur à supprimer>`

Il est possible de tester les apis avec la collection Postman (https://www.postman.com/) présente dans les sources du projet.

Cette collection est présente sous le nom `com.airfrance.api.postman_collection.json`.

## Documentation

La documentation du code est disponible dans le dossier target du projet, à l'adresse `target\apidocs\index.html`. Cette documentation n'est disponible **qu'après une génération**.

La documentation des apis est disponible depuis le Swagger associé, accessible à l'adresse: http://localhost:8080/swagger-ui.html. Cette documentation n'est disponible **qu'après le démarrage de l'application**.