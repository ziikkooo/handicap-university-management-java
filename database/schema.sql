CREATE DATABASE IF NOT EXISTS handicap_university_db;
USE handicap_university_db;

-- Table dyal users: admin w personne en situation de handicap
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'PERSONNE_HANDICAP') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Information zayda 3la personne en situation de handicap
CREATE TABLE IF NOT EXISTS personnes_handicap (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    telephone VARCHAR(30),
    type_handicap VARCHAR(100),
    filiere VARCHAR(100),
    niveau VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Table dyal demandes
CREATE TABLE IF NOT EXISTS demandes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    type_demande ENUM('AMENAGEMENT_EXAMEN', 'ACCESSIBILITE', 'ACCOMPAGNEMENT', 'AUTRE') NOT NULL,
    description TEXT NOT NULL,
    statut ENUM('EN_COURS', 'ACCEPTEE', 'REFUSEE') DEFAULT 'EN_COURS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Les pieces justificatives dyal chaque demande
CREATE TABLE IF NOT EXISTS pieces_justificatives (
    id INT AUTO_INCREMENT PRIMARY KEY,
    demande_id INT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (demande_id) REFERENCES demandes(id) ON DELETE CASCADE
);

-- Historique dyal demandes bach nb9aw 3arfin chkon bdel statut
CREATE TABLE IF NOT EXISTS historique_demandes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    demande_id INT NOT NULL,
    admin_id INT,
    ancien_statut VARCHAR(50),
    nouveau_statut VARCHAR(50),
    action TEXT,
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (demande_id) REFERENCES demandes(id) ON DELETE CASCADE,
    FOREIGN KEY (admin_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Table dyal reclamations
CREATE TABLE IF NOT EXISTS reclamations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    sujet VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    statut ENUM('EN_COURS', 'TRAITEE', 'REJETEE') DEFAULT 'EN_COURS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Historique dyal reclamations
CREATE TABLE IF NOT EXISTS historique_reclamations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reclamation_id INT NOT NULL,
    admin_id INT,
    ancien_statut VARCHAR(50),
    nouveau_statut VARCHAR(50),
    action TEXT,
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reclamation_id) REFERENCES reclamations(id) ON DELETE CASCADE,
    FOREIGN KEY (admin_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Archive bach nkheliw trace dyal les demandes/reclamations li tsalaw
CREATE TABLE IF NOT EXISTS archives (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reference_id INT NOT NULL,
    type_archive ENUM('DEMANDE', 'RECLAMATION') NOT NULL,
    resume TEXT,
    archived_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
