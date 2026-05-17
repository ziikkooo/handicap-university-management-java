USE handicap_university_db;

-- Users bach njarbo bihom l'application
INSERT INTO users (nom, prenom, email, password, role)
VALUES
('Admin', 'Principal', 'admin@gmail.com', 'admin123', 'ADMIN'),
('Etudiant', 'Test', 'student@gmail.com', 'student123', 'PERSONNE_HANDICAP');

-- Profile dyal etudiant test
INSERT INTO personnes_handicap (user_id, telephone, type_handicap, filiere, niveau)
VALUES
(2, '0600000000', 'Moteur', 'Cyber Security', '3eme annee');

-- Demande test
INSERT INTO demandes (user_id, type_demande, description, statut)
VALUES
(2, 'AMENAGEMENT_EXAMEN', 'Besoin d un temps supplementaire pendant l examen.', 'EN_COURS');

-- Reclamation test
INSERT INTO reclamations (user_id, sujet, description, statut)
VALUES
(2, 'Probleme accessibilite', 'La salle n est pas facilement accessible.', 'EN_COURS');
