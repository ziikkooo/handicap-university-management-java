# Fiche Base de Données

## 1. Smiya dyal database
Database: `handicap_university_db`

## 2. User dyal MySQL li katsta3mlo Java
Username: `handicap_user`  
Password: `Handicap@12345`  
Database: `handicap_university_db`

NB: had user ghir local/testing, machi production.

## 3. Comptes test
Admin:
- Email: `admin@gmail.com`
- Password: `admin123`
- Role: `ADMIN`

Etudiant:
- Email: `student@gmail.com`
- Password: `student123`
- Role: `PERSONNE_HANDICAP`

NB: passwords hna simple hit ghir test. F real app khas password hashing.

## 4. Tables w chno fihom

### users
Katkhzen comptes dyal login.
Colonnes:
`id`, `nom`, `prenom`, `email`, `password`, `role`, `created_at`

Roles:
`ADMIN`, `PERSONNE_HANDICAP`

### personnes_handicap
Katkhzen infos zayda 3la l'etudiant.
Colonnes:
`id`, `user_id`, `telephone`, `type_handicap`, `filiere`, `niveau`

Relation:
`user_id` → `users.id`

### demandes
Katkhzen les demandes dyal les etudiants.
Colonnes:
`id`, `user_id`, `type_demande`, `description`, `statut`, `created_at`, `updated_at`

Types:
`AMENAGEMENT_EXAMEN`, `ACCESSIBILITE`, `ACCOMPAGNEMENT`, `AUTRE`

Statuts:
`EN_COURS`, `ACCEPTEE`, `REFUSEE`

Relation:
`user_id` → `users.id`

### pieces_justificatives
Katkhzen infos dyal les fichiers li mattachyin m3a demande.
Colonnes:
`id`, `demande_id`, `file_name`, `file_path`, `uploaded_at`

Relation:
`demande_id` → `demandes.id`

NB: table makatkhzench fichier b rassou, katkhzen ghir smiya w path.

### historique_demandes
Katkhzen trace dyal changement statut f demandes.
Colonnes:
`id`, `demande_id`, `admin_id`, `ancien_statut`, `nouveau_statut`, `action`, `action_date`

Relations:
`demande_id` → `demandes.id`  
`admin_id` → `users.id`

### reclamations
Katkhzen les reclamations.
Colonnes:
`id`, `user_id`, `sujet`, `description`, `statut`, `created_at`, `updated_at`

Statuts:
`EN_COURS`, `TRAITEE`, `REJETEE`

Relation:
`user_id` → `users.id`

### historique_reclamations
Katkhzen trace dyal traitement dyal reclamations.
Colonnes:
`id`, `reclamation_id`, `admin_id`, `ancien_statut`, `nouveau_statut`, `action`, `action_date`

Relations:
`reclamation_id` → `reclamations.id`  
`admin_id` → `users.id`

### archives
Katkhzen les demandes/reclamations li tsalaw.
Colonnes:
`id`, `reference_id`, `type_archive`, `resume`, `archived_at`

Types:
`DEMANDE`, `RECLAMATION`

## 5. Relation b darija
`users` hiya table principale.  
`personnes_handicap`, `demandes`, `reclamations` kaytconnectaw m3a `users`.  
`pieces_justificatives` kaytconnecta m3a `demandes`.  
`historique_demandes` kaytconnecta m3a `demandes` w `users`.  
`historique_reclamations` kaytconnecta m3a `reclamations` w `users`.

## 6. Commands dyal test

Afficher tables:
`sudo mysql -D handicap_university_db -e "SHOW TABLES;"`

Afficher users:
`sudo mysql -D handicap_university_db -e "SELECT id, email, role FROM users;"`

Tester Java connection:
`mvn exec:java -Dexec.mainClass=com.university.handicap.Main`

Resultat li khas yban:
`Connection m3a database khdama.`
