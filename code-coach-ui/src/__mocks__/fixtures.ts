/**
 * Mock data for development & testing
 */

import { Track, Exercise, User } from '../types/dto';

export const mockUser: User = {
  id: 'user-1',
  email: 'alice@example.com',
  username: 'alice',
  firstName: 'Alice',
  lastName: 'Wonderland',
  role: 'LEARNER',
};

export const mockTracks: Track[] = [
  {
    id: 'track-1',
    title: 'Java Fondamentaux',
    description: 'Les bases de la programmation Java avec les concepts essentiels',
    language: 'Java',
    difficulty: 1,
    isPublished: true,
    exerciseCount: 5,
  },
  {
    id: 'track-2',
    title: 'Programmation Orientée Objet',
    description: 'Classes, héritage, polymorphisme, encapsulation',
    language: 'Java',
    difficulty: 2,
    isPublished: true,
    exerciseCount: 8,
  },
];

export const mockExercises: Exercise[] = [
  {
    id: 'ex-1',
    trackId: 'track-1',
    title: 'Hello World',
    description: 'Votre premier programme Java',
    prompt: 'Écrivez un programme qui affiche "Hello, World!" à la console.',
    language: 'JAVA',
    starterCode: `public class HelloWorld {
  public static void main(String[] args) {
    // Votre code ici
  }
}`,
    difficulty: 1,
    createdAt: '2025-01-01T10:00:00Z',
  },
  {
    id: 'ex-2',
    trackId: 'track-1',
    title: 'Nombres pairs',
    description: 'Déterminer si un nombre est pair',
    prompt: 'Implémentez une méthode isEven(int n) qui retourne true si n est pair, false sinon.\n\nTestez avec: 4 (true), 7 (false), 0 (true)',
    language: 'JAVA',
    starterCode: `public class Solution {
  public static boolean isEven(int n) {
    // Votre code ici
    return false;
  }

  public static void main(String[] args) {
    System.out.println(isEven(4));  // true
    System.out.println(isEven(7));  // false
  }
}`,
    difficulty: 1,
    createdAt: '2025-01-02T10:00:00Z',
  },
];
