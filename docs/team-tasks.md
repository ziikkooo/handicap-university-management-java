# Team Task Distribution

Project: Java Desktop application for managing requests and complaints of students in situation of disability inside a university.

Technologies:
- Java
- Swing
- MySQL
- JDBC
- MVC architecture

Important rule:
- Do not code on main.
- Do not create another project.
- Do not rename folders.
- Do not change another person's files.
- Each person works only in his branch.

---

## Person 1 — Database + JDBC + Project Base XXXXXXXXXXXXXXXXXXXXXX ZAKARIA

Branch:
feature/database

Main job:
Prepare the base of the project so everyone else can work.

Files to work on:
- database/schema.sql
- database/seed.sql
- src/main/java/com/university/handicap/config/DatabaseConnection.java

What to do:
1. Create the MySQL database.
2. Create all tables.
3. Create relationships between tables.
4. Add test data.
5. Create the JDBC connection class.
6. Test if Java can connect to MySQL.

Tables to prepare:
- users
- personnes_handicap
- demandes
- pieces_justificatives
- historique_demandes
- reclamations
- historique_reclamations
- archives

Do not touch:
- authentication service
- demandes service
- reclamations service
- dashboard service

Final result:
The database works and the Java app can connect to MySQL.

---

## Person 2 — Authentication + Roles

Branch:
feature/auth

Main job:
Create login system and role management.

Files to create/work on:
- src/main/java/com/university/handicap/models/User.java
- src/main/java/com/university/handicap/models/Role.java
- src/main/java/com/university/handicap/dao/UserDAO.java
- src/main/java/com/university/handicap/services/AuthService.java
- src/main/java/com/university/handicap/services/SessionService.java
- src/main/java/com/university/handicap/controllers/AuthController.java

What to do:
1. Create User model.
2. Create Role enum.
3. Create login method.
4. Create logout method.
5. Store the connected user.
6. Check if user is ADMIN or PERSONNE_HANDICAP.
7. Prevent normal users from accessing admin actions.

Roles:
- ADMIN
- PERSONNE_HANDICAP

Important methods:
- login(email, password)
- logout()
- getCurrentUser()
- isAdmin()
- isPersonneHandicap()

Do not touch:
- demandes
- reclamations
- dashboard
- archive

Final result:
The app can know who is connected and what role he has.

---

## Person 3 — Users / Profiles XXXXXXXXXXXXXXXX ADAM

Branch:
feature/users

Main job:
Manage disabled student profiles.

Files to create/work on:
- src/main/java/com/university/handicap/models/PersonneHandicap.java
- src/main/java/com/university/handicap/dao/PersonneHandicapDAO.java
- src/main/java/com/university/handicap/services/UserManagementService.java
- src/main/java/com/university/handicap/controllers/UserController.java

What to do:
1. Add a disabled student profile.
2. Edit a profile.
3. Delete a profile.
4. Search profiles.
5. Link each profile to a User account.

Profile information:
- id
- userId
- nom
- prenom
- email
- telephone
- typeHandicap
- filiere
- niveau

Important methods:
- addPersonne()
- updatePersonne()
- deletePersonne()
- getPersonneById()
- getAllPersonnes()
- searchPersonnes()

Do not touch:
- login logic
- demandes logic
- reclamations logic

Final result:
The app can manage student/person profiles.

---

## Person 4 — Demandes CRUD + Classification

Branch:
feature/demandes

Main job:
Manage requests/demandes.

Files to create/work on:
- src/main/java/com/university/handicap/models/Demande.java
- src/main/java/com/university/handicap/models/TypeDemande.java
- src/main/java/com/university/handicap/models/StatutDemande.java
- src/main/java/com/university/handicap/dao/DemandeDAO.java
- src/main/java/com/university/handicap/services/DemandeService.java
- src/main/java/com/university/handicap/controllers/DemandeController.java

What to do:
1. Create a new demande.
2. Modify a demande.
3. Delete a demande.
4. Show all demandes.
5. Show demandes by user.
6. Classify demandes by type.

Types of demande:
- AMENAGEMENT_EXAMEN
- ACCESSIBILITE
- ACCOMPAGNEMENT
- AUTRE

Status:
- EN_COURS
- ACCEPTEE
- REFUSEE

Important methods:
- createDemande()
- updateDemande()
- deleteDemande()
- getAllDemandes()
- getDemandeById()
- getDemandesByUser()
- getDemandesByType()

Do not touch:
- file upload
- history
- reclamations
- dashboard

Final result:
The app can create, update, delete, and list demandes.

---

## Person 5 — Demande Status + Files + History

Branch:
feature/demande-workflow

Main job:
Complete the demande treatment process.

Files to create/work on:
- src/main/java/com/university/handicap/models/PieceJustificative.java
- src/main/java/com/university/handicap/models/HistoriqueDemande.java
- src/main/java/com/university/handicap/dao/PieceJustificativeDAO.java
- src/main/java/com/university/handicap/dao/HistoriqueDemandeDAO.java
- src/main/java/com/university/handicap/services/DemandeWorkflowService.java
- src/main/java/com/university/handicap/services/FileStorageService.java

What to do:
1. Add supporting documents to a demande.
2. Store file name and file path.
3. Change demande status.
4. Save every status change in history.
5. Save which admin changed the status.

Example:
A demande starts as EN_COURS.
Admin changes it to ACCEPTEE.
The app saves this action in historique_demandes.

Important methods:
- addPieceJustificative()
- getPiecesByDemande()
- changeStatut()
- getHistoriqueDemande()
- saveActionHistory()

Do not touch:
- creating demandes
- reclamations
- dashboard

Final result:
Demandes have files, status tracking, and history.

---

## Person 6 — Reclamations

Branch:
feature/reclamations

Main job:
Manage complaints/reclamations.

Files to create/work on:
- src/main/java/com/university/handicap/models/Reclamation.java
- src/main/java/com/university/handicap/models/StatutReclamation.java
- src/main/java/com/university/handicap/models/HistoriqueReclamation.java
- src/main/java/com/university/handicap/dao/ReclamationDAO.java
- src/main/java/com/university/handicap/dao/HistoriqueReclamationDAO.java
- src/main/java/com/university/handicap/services/ReclamationService.java
- src/main/java/com/university/handicap/controllers/ReclamationController.java

What to do:
1. Create a reclamation.
2. Modify a reclamation.
3. Delete a reclamation.
4. Show all reclamations.
5. Show reclamations by user.
6. Change reclamation status.
7. Save history of actions.

Status:
- EN_COURS
- TRAITEE
- REJETEE

Important methods:
- createReclamation()
- updateReclamation()
- deleteReclamation()
- getAllReclamations()
- getReclamationsByUser()
- changeStatut()
- getHistoriqueReclamation()

Do not touch:
- demandes
- dashboard
- archive

Final result:
The app can manage reclamations and track their treatment.

---

## Person 7 — Dashboard + Statistics XXXXXXXXXXXXXX ALI

Branch:
feature/dashboard

Main job:
Create statistics for the admin dashboard.

Files to create/work on:
- src/main/java/com/university/handicap/models/DashboardStats.java
- src/main/java/com/university/handicap/dao/StatistiqueDAO.java
- src/main/java/com/university/handicap/services/DashboardService.java
- src/main/java/com/university/handicap/controllers/DashboardController.java

What to do:
1. Count demandes by status.
2. Count demandes by type.
3. Count reclamations by status.
4. Filter demandes by date.
5. Filter demandes by type.
6. Create annual statistics.

Statistics needed:
- number of demandes EN_COURS
- number of demandes ACCEPTEE
- number of demandes REFUSEE
- number of reclamations EN_COURS
- number of reclamations TRAITEE
- number of reclamations REJETEE
- annual statistics

Important methods:
- countDemandesByStatut()
- countDemandesByType()
- countReclamationsByStatut()
- getDemandesBetweenDates()
- getAnnualStats()

Do not touch:
- creating demandes
- creating reclamations
- user management

Final result:
The admin can see useful numbers for the dashboard.

---

## Person 8 — Archive + Search + Integration

Branch:
feature/archive-search

Main job:
Manage archive, search, and help integrate all parts.

Files to create/work on:
- src/main/java/com/university/handicap/models/SearchCriteria.java
- src/main/java/com/university/handicap/models/ArchiveRecord.java
- src/main/java/com/university/handicap/dao/ArchiveDAO.java
- src/main/java/com/university/handicap/services/ArchiveService.java
- src/main/java/com/university/handicap/services/SearchService.java
- src/main/java/com/university/handicap/controllers/ArchiveController.java

What to do:
1. Search demandes by multiple criteria.
2. Search reclamations by multiple criteria.
3. Archive closed demandes.
4. Archive closed reclamations.
5. Show complete history.
6. Help test if all modules work together.

Search criteria:
- name
- email
- type
- status
- date
- keyword

Important methods:
- searchDemandes()
- searchReclamations()
- getCompleteHistoryByUser()
- archiveClosedDemande()
- archiveClosedReclamation()

Extra job:
This person helps check that the work of all members can run together.

Final result:
The app has archive, search, and integration checking.

---

# Final Branch Summary

Person 1:
feature/database

Person 2:
feature/auth

Person 3:
feature/users

Person 4:
feature/demandes

Person 5:
feature/demande-workflow

Person 6:
feature/reclamations

Person 7:
feature/dashboard

Person 8:
feature/archive-search

---

# Team Rules

1. Nobody works on main.
2. Nobody creates another project.
3. Nobody renames packages.
4. Nobody deletes another person's files.
5. Everyone works only in his branch.
6. Everyone commits small changes.
7. Everyone writes clear commit messages.
8. The integrator checks before merging to main.
