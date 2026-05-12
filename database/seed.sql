USE handicap_university_db;

INSERT INTO users (nom, prenom, email, password, role)
VALUES
('Admin', 'Principal', 'admin@gmail.com', 'admin123', 'ADMIN'),
('Etudiant', 'Test', 'student@gmail.com', 'student123', 'PERSONNE_HANDICAP');

INSERT INTO personnes_handicap (user_id, telephone, type_handicap, filiere, niveau)
VALUES
(2, '0600000000', 'Moteur', 'Cyber Security', '3eme annee');
