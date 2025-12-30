-- ===================================
-- DONNÉES D'EXEMPLE & FIXTURES
-- À insérer après les migrations Flyway
-- ===================================

-- 1. UTILISATEURS
INSERT INTO cc_user (email, username, password_hash, first_name, last_name, role, is_active, created_at, updated_at)
VALUES
  ('alice@example.com', 'alice', '$2a$12$..hash1..', 'Alice', 'Wonderland', 'LEARNER', true, NOW(), NOW()),
  ('bob@example.com', 'bob', '$2a$12$..hash2..', 'Bob', 'Builder', 'LEARNER', true, NOW(), NOW()),
  ('instructor@example.com', 'mr_smith', '$2a$12$..hash3..', 'Mr', 'Smith', 'INSTRUCTOR', true, NOW(), NOW());

-- 2. TRACKS
INSERT INTO cc_track (title, description, programming_language, difficulty, is_published, created_by_user_id, created_at, updated_at)
VALUES
  ('Java Fundamentals', 'Appendre les bases de Java', 'java', 1, true, 3, NOW(), NOW()),
  ('OOP Mastery', 'Programmation Orientée Objet en profondeur', 'java', 2, true, 3, NOW(), NOW()),
  ('Collections & Streams', 'Lists, Sets, Maps, Streams API', 'java', 3, true, 3, NOW(), NOW());

-- 3. EXERCICES - Track 1 (Java Fundamentals)
INSERT INTO cc_exercise (track_id, title, description, starter_code, test_code, order_in_track, difficulty, timeout_millis, memory_limit_mb, is_published, created_at, updated_at)
VALUES
  (1, 'Écrire une méthode isEven',
   'Implémente une méthode qui retourne true si le nombre est pair, false sinon',
   'public class Solution {
     public static boolean isEven(int n) {
       // TODO: Votre implémentation
     }
   }',
   'public class SolutionTest {
     @Test
     void testIsEven() {
       assert Solution.isEven(4) == true;
       assert Solution.isEven(3) == false;
       assert Solution.isEven(0) == true;
     }
   }',
   1, 1, 5000, 256, true, NOW(), NOW()),

  (1, 'Calculer la somme de 1 à N',
   'Implémente une méthode sum(int n) qui retourne 1+2+3+...+n',
   'public class Solution {
     public static int sum(int n) {
       // TODO: Votre implémentation
     }
   }',
   'public class SolutionTest {
     @Test
     void testSum() {
       assert Solution.sum(5) == 15;
       assert Solution.sum(10) == 55;
       assert Solution.sum(1) == 1;
     }
   }',
   2, 1, 5000, 256, true, NOW(), NOW());

-- 4. EXERCICES - Track 2 (OOP Mastery)
INSERT INTO cc_exercise (track_id, title, description, starter_code, test_code, order_in_track, difficulty, timeout_millis, memory_limit_mb, is_published, created_at, updated_at)
VALUES
  (2, 'Créer une classe Person avec encapsulation',
   'Créer une classe Person avec fields privés (name, age) et getters/setters appropriés',
   'public class Person {
     // TODO: Ajouter fields et méthodes
   }',
   'public class PersonTest {
     @Test
     void testPerson() {
       Person p = new Person("Alice", 30);
       assert p.getName().equals("Alice");
       assert p.getAge() == 30;
     }
   }',
   1, 2, 5000, 256, true, NOW(), NOW());

-- 5. SOUMISSIONS (Alice a essayé l'exercice 1 plusieurs fois)
INSERT INTO cc_submission (exercise_id, user_id, code, status, attempt_number, created_at)
VALUES
  (1, 1, 'public class Solution { public static boolean isEven(int n) { return n % 2 == 0; } }',
   'SUCCESS', 1, NOW() - INTERVAL '2 days'),
  (1, 1, 'public class Solution { public static boolean isEven(int n) { return true; } }',
   'FAILED', 2, NOW() - INTERVAL '1 day');

-- 6. RÉSULTATS DE SOUMISSIONS
INSERT INTO cc_submission_result (submission_id, compilation_error, runtime_error, tests_passed, tests_failed, failure_details, execution_time_ms, memory_used_mb, created_at)
VALUES
  (1, NULL, NULL, 3, 0, NULL, 245, 45, NOW() - INTERVAL '2 days'),
  (2, NULL, NULL, 1, 2, '{"failed": [{"test": "isEven(3)", "expected": "false", "got": "true"}]}', 198, 42, NOW() - INTERVAL '1 day');

-- 7. PROGRESSION DES SKILLS
INSERT INTO cc_skill_progress (user_id, exercise_id, successful_attempts, total_attempts, is_mastered, last_attempt_at, mastered_at, created_at)
VALUES
  (1, 1, 1, 2, true, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', NOW() - INTERVAL '2 days'),
  (1, 2, 0, 0, false, NULL, NULL, NOW()),
  (2, 1, 0, 0, false, NULL, NULL, NOW());

-- ===================================
-- NOTE : Hashes BCrypt générés avec :
-- password: "Password123!"
-- rounds: 12
-- hash: $2a$12$abcdefghijklmnopqrstuvwxyz... (exemple)
-- ===================================
