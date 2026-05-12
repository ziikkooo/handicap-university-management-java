# Team Task Distribution

## Person 1 — Database + JDBC + Project Structure

Folders/classes:
- src/main/java/com/university/handicap/config/DatabaseConnection.java
- database/schema.sql
- database/seed.sql

Job:
- Create MySQL database
- Create all tables
- Create JDBC connection
- Prepare test data
- Maintain the shared project structure

## Person 2 — Authentication + Roles

Folders/classes:
- models/User.java
- models/Role.java
- dao/UserDAO.java
- services/AuthService.java
- services/SessionService.java

Job:
- Login
- Logout
- Admin role
- Personne en situation de handicap role
- Current user session
- Protect admin/user screens

## Person 3 — Users / Profiles

Folders/classes:
- models/PersonneHandicap.java
- dao/PersonneHandicapDAO.java
- services/UserManagementService.java

Job:
- Add user profile
- Edit user profile
- Delete user profile
- Search users
- Link profile with login account

## Person 4 — Demandes CRUD + Classification

Folders/classes:
- models/Demande.java
- models/TypeDemande.java
- models/StatutDemande.java
- dao/DemandeDAO.java
- services/DemandeService.java

Job:
- Add demande
- Modify demande
- Delete demande
- List demandes
- Classify demande by type

## Person 5 — Demande Status + Files + History

Folders/classes:
- models/PieceJustificative.java
- models/HistoriqueDemande.java
- dao/PieceJustificativeDAO.java
- dao/HistoriqueDemandeDAO.java
- services/DemandeWorkflowService.java
- services/FileStorageService.java

Job:
- Add supporting documents
- Change demande status
- Save history of actions
- Track which admin changed the status

## Person 6 — Reclamations

Folders/classes:
- models/Reclamation.java
- models/StatutReclamation.java
- models/HistoriqueReclamation.java
- dao/ReclamationDAO.java
- services/ReclamationService.java

Job:
- Add reclamation
- Modify reclamation
- Delete reclamation
- Follow treatment status
- Save history

## Person 7 — Dashboard + Statistics

Folders/classes:
- models/DashboardStats.java
- dao/StatistiqueDAO.java
- services/DashboardService.java

Job:
- Count demandes by status
- Count demandes by type
- Count reclamations by status
- Filter by date
- Annual statistics

## Person 8 — Archive + Search + Integration

Folders/classes:
- models/SearchCriteria.java
- models/ArchiveRecord.java
- dao/ArchiveDAO.java
- services/ArchiveService.java
- services/SearchService.java

Job:
- Multicriteria search
- Complete history
- Archive closed demandes/reclamations
- Test integration between all modules
